package com.ruoyi.salesman.service.impl;


import com.ruoyi.common.constant.QueryParaConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.salesman.domain.SalesmanTask;
import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.SalesmanOrderRespDto;
import com.ruoyi.salesman.domain.request.SalesmanOrderReqDto;
import com.ruoyi.salesman.mapper.SalesmanTaskMapper;
import com.ruoyi.salesman.mapper.SalesmanOrderMapper;
import com.ruoyi.salesman.service.ISalesmanOrderService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商业订单信息Service业务层处理
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Service
public class SalesmanOrderServiceImpl implements ISalesmanOrderService
{
    private static final Logger log = LoggerFactory.getLogger(SalesmanOrderServiceImpl.class);
    @Autowired
    private SalesmanOrderMapper salesmanOrderMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SalesmanTaskMapper dtBusinessTaskMapper;

    @Override
    public List<SalesmanOrderRespDto> selectSalesmanTaskDtoList(SalesmanOrderReqDto salesmanOrderReqDto) {
        List<SalesmanOrderRespDto> orders = salesmanOrderMapper.selectSalesmanOrderList(salesmanOrderReqDto);
        List<Long> userIds = orders.stream().map(SalesmanOrderRespDto::getSalesmanLeaderUserId).collect(Collectors.toList());
        userIds.addAll(orders.stream().map(SalesmanOrderRespDto::getMerchantUserId).collect(Collectors.toList()));
        List<SysUser> sysUsers = sysUserMapper.selectUserListByIds(userIds);
        Map<Long,String> userIdAndName = sysUsers.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getUserName, (key1, key2) -> key2));
        for(SalesmanOrderRespDto item:orders){
            item.setSalesmanLeaderName(userIdAndName.get(item.getSalesmanLeaderUserId()));
            item.setMerchantUserName(userIdAndName.get(item.getMerchantUserId()));
            item.setPictureUrl(OssClientUtils.getPictureUrlByOssParam(item.getPictureUrl()));
            item.setSalesmanCommitUrl(OssClientUtils.getPictureUrlByOssParam(item.getSalesmanCommitUrl()));
        }
        return orders;
    }

    @Override
    public int updateSalesmanTaskDetail(SalesmanTaskDetail salesmanTaskDetail) {
        return dtBusinessTaskMapper.updateSalesmanTaskDetail(salesmanTaskDetail);
    }

    @Override
    public int stopOrder(String ids) {
        Long[] idlist = Convert.toLongArray(ids);
        List<SalesmanTaskDetail> dtSalesmanTaskDetails = dtBusinessTaskMapper.selectSalesmanTaskDetailListByIds(idlist);
        List<String> statusList = dtSalesmanTaskDetails.stream().map(SalesmanTaskDetail::getStatus).collect(Collectors.toList());
        if(statusList.contains("0")){
            throw new BusinessException("有订单存在已完成，坏不了单");
        }
        Integer num = 0;
        for(Long id : idlist){
            SalesmanTaskDetail detail = new SalesmanTaskDetail();
            detail.setId(id);
            detail.setStatus("2");
            num = num + dtBusinessTaskMapper.updateSalesmanTaskDetail(detail);
        }
        return num;
    }

    @Override
    public int recoverOrder(String ids) {
        Long[] idlist = Convert.toLongArray(ids);
        List<SalesmanTaskDetail> dtBusinessTaskDetails = dtBusinessTaskMapper.selectSalesmanTaskDetailListByIds(idlist);
        List<Long> taskIds = dtBusinessTaskDetails.stream().map(SalesmanTaskDetail::getTaskId).collect(Collectors.toList());
        List<SalesmanTask> dtBusinessTasks = dtBusinessTaskMapper.selectSalesmanTaskByIds(taskIds);
        List<String> statusList = dtBusinessTaskDetails.stream().map(SalesmanTaskDetail::getStatus).collect(Collectors.toList());
        List<String> orderStatusList = dtBusinessTasks.stream().map(SalesmanTask::getOrderStatus).collect(Collectors.toList());
        if(statusList.contains("0") || statusList.contains("1")){
            throw new BusinessException("存在订单是非停单状态的，修复不了订单,请勿选非停单状态下的订单");
        }
        if(orderStatusList.contains("0")){
            throw new BusinessException("有任务存在已完成，修复不了订单");
        }
        Integer num = 0;
        for(Long id : idlist){
            SalesmanTaskDetail detail = new SalesmanTaskDetail();
            detail.setId(id);
            detail.setStatus("1");
            num = num + dtBusinessTaskMapper.updateSalesmanTaskDetail(detail);
        }
        return num;
    }

    @Override
    public void doProcessReqParam(SalesmanOrderReqDto groupOrderReqDto) {
        if(StringUtils.isNotEmpty(groupOrderReqDto.getSalesmanLeaderName())){
            SysUser user = sysUserMapper.selectUserByUserName(groupOrderReqDto.getSalesmanLeaderName());
            if(user != null){
                groupOrderReqDto.setSalesmanLeaderUserId(user.getUserId());
            }else {
                groupOrderReqDto.setSalesmanLeaderUserId(QueryParaConstants.PARAM_NULL);
            }
        }
        if(StringUtils.isNotEmpty(groupOrderReqDto.getMerchantName())){
            SysUser user = sysUserMapper.selectUserByUserName(groupOrderReqDto.getMerchantName());
            if(user != null){
                groupOrderReqDto.setMerchantUserId(user.getUserId());
            }else{
                groupOrderReqDto.setMerchantUserId(QueryParaConstants.PARAM_NULL);
            }
        }
    }

}
