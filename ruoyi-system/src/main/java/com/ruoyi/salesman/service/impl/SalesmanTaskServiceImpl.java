package com.ruoyi.salesman.service.impl;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.salesman.domain.SalesmanTask;
import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.CommitOrder;
import com.ruoyi.salesman.domain.reponse.SalesmanTaskDetailRespDto;
import com.ruoyi.salesman.domain.reponse.SalesmanTaskRespDto;
import com.ruoyi.salesman.domain.request.SalesmanTaskReqDto;
import com.ruoyi.salesman.mapper.SalesmanOrderMapper;
import com.ruoyi.salesman.mapper.SalesmanTaskMapper;
import com.ruoyi.salesman.service.ISalesmanTaskService;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 业务员任务信息Service业务层处理
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Service
public class SalesmanTaskServiceImpl implements ISalesmanTaskService
{
    private static final Logger log = LoggerFactory.getLogger(SalesmanTaskServiceImpl.class);
    @Autowired
    private SalesmanTaskMapper salesmanTaskMapper;
    @Autowired
    private SalesmanOrderMapper salesmanOrderMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysUserMapper sysUserMapper;


    /**
     * 查询商业任务信息
     * 
     * @param id 商业任务信息ID
     * @return 商业任务信息
     */
    @Override
    public SalesmanTask selectSalesmanTaskById(Long id)
    {
        SalesmanTask salesmanTask = salesmanTaskMapper.selectSalesmanTaskById(id);

        doProcessUrl4SalesmanTask(salesmanTask);

        return salesmanTask;
    }

    private void doProcessUrl4SalesmanTask(SalesmanTask salesmanTask) {
        if(StringUtils.isNotEmpty(salesmanTask.getFeedbackPictureUrl1())){
            salesmanTask.setFeedbackPictureUrl1(OssClientUtils.getPictureUrlByOssParam(salesmanTask.getFeedbackPictureUrl1()));
        }
        if(StringUtils.isNotEmpty(salesmanTask.getFeedbackPictureUrl2())){
            salesmanTask.setFeedbackPictureUrl2(OssClientUtils.getPictureUrlByOssParam(salesmanTask.getFeedbackPictureUrl2()));
        }
        if(StringUtils.isNotEmpty(salesmanTask.getFeedbackPictureUrl3())){
            salesmanTask.setFeedbackPictureUrl3(OssClientUtils.getPictureUrlByOssParam(salesmanTask.getFeedbackPictureUrl3()));
        }
        if(StringUtils.isNotEmpty(salesmanTask.getFeedbackPictureUrl4())){
            salesmanTask.setFeedbackPictureUrl4(OssClientUtils.getPictureUrlByOssParam(salesmanTask.getFeedbackPictureUrl4()));
        }
        if(StringUtils.isNotEmpty(salesmanTask.getFeedbackPictureUrl5())){
            salesmanTask.setFeedbackPictureUrl5(OssClientUtils.getPictureUrlByOssParam(salesmanTask.getFeedbackPictureUrl5()));
        }

    }

    @Override
    public List<SalesmanTask> selectSalesmanTaskList(SalesmanTask salesmanTask) {
        return salesmanTaskMapper.selectSalesmanTaskList(salesmanTask);
    }

    /**
     * 查询商业任务信息列表
     * 
     * @param salesmanTaskReqDto 商业任务信息
     * @return 商业任务信息
     */
    @Override
    public List<SalesmanTaskRespDto> selectSalesmanTaskDtoList(SalesmanTaskReqDto salesmanTaskReqDto,SysUser user, Set<Long> salesmanUserIds)
    {
        if(salesmanUserIds.isEmpty()){
            return Collections.emptyList();
        }

        vaildSalesman(user);

        salesmanTaskReqDto.setDeptId(user.getDeptId());

        List<SalesmanTaskRespDto> resultList = salesmanTaskMapper.selectSalesmanTaskDtoListByIds(salesmanTaskReqDto,salesmanUserIds);

        doProcessDetail4SalesmanTaskRespDto(resultList);

        List<Long> deptIds = resultList.stream().map(SalesmanTaskRespDto::getDeptId).collect(Collectors.toList());

        List<SysDept> depts = deptIds.size() > 0 ? sysDeptMapper.selectDeptListByIds(deptIds) : Collections.emptyList();

        Map<Long,String> deptIdAndNameMap = depts.stream().collect(Collectors.toMap(SysDept::getDeptId, SysDept::getDeptName, (key1, key2) -> key2));

        formatDto4SalesmanTaskRespDto(resultList,deptIdAndNameMap);

        return resultList;
    }

    private void doProcessDetail4SalesmanTaskRespDto(List<SalesmanTaskRespDto> resultList) {
        List<Long> taskIds =  resultList.stream().map(SalesmanTaskRespDto::getId).collect(Collectors.toList());
        if(taskIds.size() == 0){
            return;
        }
        List<SalesmanTaskDetailRespDto> details = salesmanTaskMapper.selectSalesmanTaskDetailDtoByTaskIds(taskIds);
        MultiValuedMap<Long, SalesmanTaskDetailRespDto> taskIdAndDetailListMap = new ArrayListValuedHashMap<>();
        for(SalesmanTaskDetailRespDto detail:details){
            taskIdAndDetailListMap.put(detail.getTaskId(),detail);
        }
        for(SalesmanTaskRespDto dto:resultList){
            dto.setSalesmanTaskDetailRespDtos((List<SalesmanTaskDetailRespDto>)taskIdAndDetailListMap.get(dto.getId()));
        }
    }

    @Override
    public List<SalesmanTaskDetail> selectSalesmanTaskDetailList(Long id) {
        List<SalesmanTaskDetail> resultList = salesmanTaskMapper.selectSalesmanTaskDetailListByTaskId(id);
        List<Long> merchantUserIds = resultList.stream().map(SalesmanTaskDetail::getMerchantUserId).collect(Collectors.toList());
        List<SysUser> sysUsers = sysUserMapper.selectUserListByIds(merchantUserIds);
        Map<Long,String> userIdAndNameMap = sysUsers.stream().collect(Collectors.toMap(SysUser::getUserId,SysUser::getUserName,(k1,k2) -> k2));
        for (SalesmanTaskDetail detail:resultList){
            detail.setMerchatUserName(userIdAndNameMap.get(detail.getMerchantUserId()));
            detail.setPictureUrl(OssClientUtils.getPictureUrlByOssParam(detail.getPictureUrl()));
            detail.setSalesmanCommitUrl(OssClientUtils.getPictureUrlByOssParam(detail.getSalesmanCommitUrl()));
        }
        return resultList;
    }

    @Override
    public int updateSalesmanTaskDetail(SalesmanTaskDetail salesmanTaskDetail) {
        checkoutStatus(salesmanTaskDetail.getId());
        queryRepurchase(salesmanTaskDetail.getPlatformNickname());
        return salesmanTaskMapper.updateSalesmanTaskDetail(salesmanTaskDetail);
    }

    @Override
    public void checkoutStatus(Long id) {
        SalesmanTaskDetail salesmanTaskDetail = salesmanTaskMapper.selectSalesmanTaskDetailById(id);

        if(salesmanTaskDetail.getStatus().equals("0")){
            throw new BusinessException("已经提交过订单了");
        }
        if(salesmanTaskDetail.getStatus().equals("2")){
            throw new BusinessException("该订单已经停用了");
        }
    }

    @Override
    public void queryRepurchase(String platformNickname) {
        if(StringUtils.isEmpty(platformNickname)){
            throw new BusinessException("买家昵称不为空");
        }
        List<SalesmanTaskDetail> salesmanTaskDetails = salesmanOrderMapper.selectSalesmanOrderListByPlatformNicknameWithRepurchase(platformNickname);
        if(salesmanTaskDetails.size() > 0){
            throw new BusinessException("该买家呢称在最近一个月内使用过，请更换！");
        }
    }

    @Override
    public Set<Long> selectTaskIdsBySalesmanUserId(Long userId) {
        return salesmanTaskMapper.selectTaskIdsBySalesmanUserId(userId);
    }

    @Override
    public int commitOrder(CommitOrder commitOrder) {
        return salesmanOrderMapper.updateSalesmanOrder(commitOrder);
    }

    private void formatDto4SalesmanTaskRespDto(List<SalesmanTaskRespDto> resultList,
                                               Map<Long,String> deptIdAndNameMap) {
        for(SalesmanTaskRespDto dto: resultList){
            dto.setDeptName(deptIdAndNameMap.get(dto.getDeptId()));
            List<SalesmanTaskDetailRespDto> detailRespDto = dto.getSalesmanTaskDetailRespDtos();
            dto.setOrderNumber(detailRespDto.size());
            List<BigDecimal> principalList = detailRespDto.stream().map(SalesmanTaskDetailRespDto::getUnitPrice).collect(Collectors.toList());
            dto.setTotalPrincipal(principalList.stream().reduce(new BigDecimal(0),(acc,item) -> {
                BigDecimal result = acc.add(item);
                return result;
            }));
            List<BigDecimal> actualPrincipalList = detailRespDto.stream().map(SalesmanTaskDetailRespDto::getPromotersModifyUnitPrice).collect(Collectors.toList());
            dto.setActualTotalPrincipal(actualPrincipalList.stream().reduce(new BigDecimal(0),(acc,item) ->{
                BigDecimal result = acc.add(item);
                return result;
            }));

        }
    }


    /**
     * 修改商业任务信息
     * 
     * @param salesmanTask 商业任务信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSalesmanTask(SalesmanTask salesmanTask)
    {
        salesmanTaskMapper.deleteSalesmanTaskDetailByTaskId(salesmanTask.getId());
        insertSalesmanTaskDetail(salesmanTask);
        return salesmanTaskMapper.updateSalesmanTask(salesmanTask);
    }


    /**
     * 新增商业任务信息明细信息
     *
     * @param salesmanTask 商业任务信息对象
     */
    public void insertSalesmanTaskDetail(SalesmanTask salesmanTask)
    {
        List<SalesmanTaskDetail> salesmanTaskDetails = salesmanTask.getSalesmanTaskDetails();
        Long id = salesmanTask.getId();
        if (StringUtils.isNotNull(salesmanTaskDetails))
        {
            List<SalesmanTaskDetail> list = new ArrayList<SalesmanTaskDetail>();
            for (SalesmanTaskDetail salesmanTaskDetail : salesmanTaskDetails)
            {
                salesmanTaskDetail.setId(id);
                list.add(salesmanTaskDetail);
            }
            if (list.size() > 0)
            {
                salesmanTaskMapper.batchSalesmanTaskDetail(list);
            }
        }
    }

    private void vaildSalesman(SysUser user) {
        if(StringUtils.isNull(user.getDept())){
            throw new BusinessException("本账号还没设置部门/团队，无法查看数据");
        }
    }
}
