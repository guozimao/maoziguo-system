package com.ruoyi.salesmanleader.service.impl;


import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderTask;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderTaskDetail;
import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderOrderRespDto;
import com.ruoyi.salesmanleader.domain.request.SalesmanLeaderOrderReqDto;
import com.ruoyi.salesmanleader.mapper.SalesmanLeaderTaskMapper;
import com.ruoyi.salesmanleader.mapper.SalesmanLeaderOrderMapper;
import com.ruoyi.salesmanleader.service.ISalesmanLeaderOrderService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
public class SalesmanLeaderOrderServiceImpl implements ISalesmanLeaderOrderService
{
    private static final Logger log = LoggerFactory.getLogger(SalesmanLeaderOrderServiceImpl.class);
    @Autowired
    private SalesmanLeaderOrderMapper salesmanLeaderOrderMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SalesmanLeaderTaskMapper salesmanLeaderTaskMapper;

    @Override
    public List<SalesmanLeaderOrderRespDto> selectSalesmanLeaderTaskDtoList(SalesmanLeaderOrderReqDto salesmanLeaderOrderReqDto, SysUser user) {
        vaildSalesmanleader(user);
        salesmanLeaderOrderReqDto.setSalesmanLeaderUserId(user.getUserId());
        List<SalesmanLeaderOrderRespDto> orders = salesmanLeaderOrderMapper.selectSalesmanLeaderOrderList(salesmanLeaderOrderReqDto);
        if(orders.isEmpty()){
            return Collections.emptyList();
        }
        List<Long> userIds = orders.stream().map(SalesmanLeaderOrderRespDto::getSalesmanUserId).collect(Collectors.toList());
        List<SysUser> sysUsers = sysUserMapper.selectUserListByIds(userIds);
        Map<Long,String> userIdAndName = sysUsers.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getUserName, (key1, key2) -> key2));
        for(SalesmanLeaderOrderRespDto item:orders){
            item.setSalesmanUserName(userIdAndName.get(item.getSalesmanUserId()));
            item.setPictureUrl(OssClientUtils.getPictureUrlByOssParam(item.getPictureUrl()));
            item.setSalesmanCommitUrl(OssClientUtils.getPictureUrlByOssParam(item.getSalesmanCommitUrl()));
        }
        return orders;
    }

    @Override
    public int updateDtsalesmanLeaderTaskDetail(SalesmanLeaderTaskDetail salesmanLeaderTaskDetail) {
        return salesmanLeaderTaskMapper.updateSalesmanLeaderTaskDetail(salesmanLeaderTaskDetail);
    }

    @Override
    public int stopOrder(String ids) {
        Long[] idlist = Convert.toLongArray(ids);
        List<SalesmanLeaderTaskDetail> dtBusinessTaskDetails = salesmanLeaderTaskMapper.selectSalesmanLeaderTaskDetailListByIds(idlist);
        List<String> statusList = dtBusinessTaskDetails.stream().map(SalesmanLeaderTaskDetail::getStatus).collect(Collectors.toList());
        if(statusList.contains("0")){
            throw new BusinessException("有订单存在已完成，坏不了单");
        }
        Integer num = 0;
        for(Long id : idlist){
            SalesmanLeaderTaskDetail detail = new SalesmanLeaderTaskDetail();
            detail.setId(id);
            detail.setStatus("2");
            num = num + salesmanLeaderTaskMapper.updateSalesmanLeaderTaskDetail(detail);
        }
        return num;
    }

    @Override
    public int recoverOrder(String ids) {
        Long[] idlist = Convert.toLongArray(ids);
        List<SalesmanLeaderTaskDetail> dtBusinessTaskDetails = salesmanLeaderTaskMapper.selectSalesmanLeaderTaskDetailListByIds(idlist);
        List<Long> taskIds = dtBusinessTaskDetails.stream().map(SalesmanLeaderTaskDetail::getTaskId).collect(Collectors.toList());
        List<SalesmanLeaderTask> dtBusinessTasks = salesmanLeaderTaskMapper.selectSalesmanLeaderTaskByIds(taskIds);
        List<String> statusList = dtBusinessTaskDetails.stream().map(SalesmanLeaderTaskDetail::getStatus).collect(Collectors.toList());
        List<String> orderStatusList = dtBusinessTasks.stream().map(SalesmanLeaderTask::getOrderStatus).collect(Collectors.toList());
        if(statusList.contains("0") || statusList.contains("1")){
            throw new BusinessException("存在订单是非停单状态的，修复不了订单,请勿选非停单状态下的订单");
        }
        if(orderStatusList.contains("0")){
            throw new BusinessException("有任务存在已完成，修复不了订单");
        }
        Integer num = 0;
        for(Long id : idlist){
            SalesmanLeaderTaskDetail detail = new SalesmanLeaderTaskDetail();
            detail.setId(id);
            detail.setStatus("1");
            num = num + salesmanLeaderTaskMapper.updateSalesmanLeaderTaskDetail(detail);
        }
        return num;
    }

    @Override
    public SysUser getUserBySalesManUserName(String salesmanUserName) {
        return sysUserMapper.selectUserByUserName(salesmanUserName);
    }

    private void vaildSalesmanleader(SysUser user) {
        if(StringUtils.isNull(user.getDept())){
            throw new BusinessException("本账号还没设置部门/团队，无法查看数据");
        }else if(!user.getDept().getLeaderId().equals(user.getUserId())){
            throw new BusinessException("本账号不是部门/团队的负责人，无法查看数据");
        }
    }
}
