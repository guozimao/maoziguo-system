package com.ruoyi.groupcompany.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ruoyi.businessteam.domain.MerchantShopRelation;
import com.ruoyi.businessteam.mapper.DtSalesmanMapper;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskDetailRespDto;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskRespDto;
import com.ruoyi.groupcompany.domain.request.AssginReqDto;
import com.ruoyi.groupcompany.domain.request.DtGroupBusinessTaskReqDto;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.DtMerchantMapper;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.mapper.DtBusinessTaskMapper;
import com.ruoyi.groupcompany.domain.DtBusinessTask;
import com.ruoyi.groupcompany.service.IDtBusinessTaskService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商业任务信息Service业务层处理
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Service
public class DtBusinessTaskServiceImpl implements IDtBusinessTaskService
{
    private static final Logger log = LoggerFactory.getLogger(DtBusinessTaskServiceImpl.class);
    @Autowired
    private DtBusinessTaskMapper dtBusinessTaskMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private DtSalesmanMapper dtSalesmanMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private DtMerchantMapper dtMerchantMapper;

    /**
     * 查询商业任务信息
     * 
     * @param id 商业任务信息ID
     * @return 商业任务信息
     */
    @Override
    public DtBusinessTask selectDtBusinessTaskById(Long id)
    {
        DtBusinessTask dtBusinessTask = dtBusinessTaskMapper.selectDtBusinessTaskById(id);

        doProcessUrl4BusinessTask(dtBusinessTask);

        return dtBusinessTask;
    }

    private void doProcessUrl4BusinessTask(DtBusinessTask dtBusinessTask) {
        if(StringUtils.isNotEmpty(dtBusinessTask.getFeedbackPictureUrl1())){
            dtBusinessTask.setFeedbackPictureUrl1(OssClientUtils.getPictureUrlByOssParam(dtBusinessTask.getFeedbackPictureUrl1()));
        }
        if(StringUtils.isNotEmpty(dtBusinessTask.getFeedbackPictureUrl2())){
            dtBusinessTask.setFeedbackPictureUrl2(OssClientUtils.getPictureUrlByOssParam(dtBusinessTask.getFeedbackPictureUrl2()));
        }
        if(StringUtils.isNotEmpty(dtBusinessTask.getFeedbackPictureUrl3())){
            dtBusinessTask.setFeedbackPictureUrl3(OssClientUtils.getPictureUrlByOssParam(dtBusinessTask.getFeedbackPictureUrl3()));
        }
        if(StringUtils.isNotEmpty(dtBusinessTask.getFeedbackPictureUrl4())){
            dtBusinessTask.setFeedbackPictureUrl4(OssClientUtils.getPictureUrlByOssParam(dtBusinessTask.getFeedbackPictureUrl4()));
        }
        if(StringUtils.isNotEmpty(dtBusinessTask.getFeedbackPictureUrl5())){
            dtBusinessTask.setFeedbackPictureUrl5(OssClientUtils.getPictureUrlByOssParam(dtBusinessTask.getFeedbackPictureUrl5()));
        }

    }

    @Override
    public List<DtBusinessTask> selectDtBusinessTaskList(DtBusinessTask dtBusinessTask) {
        return dtBusinessTaskMapper.selectDtBusinessTaskList(dtBusinessTask);
    }

    /**
     * 查询商业任务信息列表
     * 
     * @param dtGroupBusinessTaskReqDto 商业任务信息
     * @return 商业任务信息
     */
    @Override
    public List<DtGroupBusinessTaskRespDto> selectGroupDtBusinessTaskDtoList(DtGroupBusinessTaskReqDto dtGroupBusinessTaskReqDto)
    {
        List<DtGroupBusinessTaskRespDto> resultList = dtBusinessTaskMapper.selectGroupDtBusinessTaskDtoList(dtGroupBusinessTaskReqDto);

        doProcessDetail4GroupBusinessTaskRespDto(resultList);

        List<Long> deptIds = resultList.stream().map(DtGroupBusinessTaskRespDto::getDeptId).collect(Collectors.toList());

        List<SysDept> depts = deptIds.size() > 0 ? sysDeptMapper.selectDeptListByIds(deptIds) : Collections.emptyList();

        Map<Long,String> deptIdAndNameMap = depts.stream().collect(Collectors.toMap(SysDept::getDeptId, SysDept::getDeptName, (key1, key2) -> key2));

        formatDto4DtGroupBusinessTaskRespDto(resultList,deptIdAndNameMap);

        return resultList;
    }

    private void doProcessDetail4GroupBusinessTaskRespDto(List<DtGroupBusinessTaskRespDto> resultList) {
        List<Long> taskIds =  resultList.stream().map(DtGroupBusinessTaskRespDto::getId).collect(Collectors.toList());
        if(taskIds.size() == 0){
            return;
        }
        List<DtGroupBusinessTaskDetailRespDto> details = dtBusinessTaskMapper.selectGroupBusinessTaskDetailDtoByTaskIds(taskIds);
        MultiValuedMap<Long,DtGroupBusinessTaskDetailRespDto> taskIdAndDetailListMap = new ArrayListValuedHashMap<>();
        for(DtGroupBusinessTaskDetailRespDto detail:details){
            taskIdAndDetailListMap.put(detail.getTaskId(),detail);
        }
        for(DtGroupBusinessTaskRespDto dto:resultList){
            dto.setDtGroupBusinessTaskDetailRespDtoList((List<DtGroupBusinessTaskDetailRespDto>)taskIdAndDetailListMap.get(dto.getId()));
        }
    }

    @Override
    public int assginDept(AssginReqDto assginReqDto) {
        Long[] taskIds = Convert.toLongArray(assginReqDto.getIds());
        int count = 0;
        SysDept dept = sysDeptMapper.selectDeptById(assginReqDto.getDeptId());
        if(StringUtils.isNull(dept.getLeaderId())){
            throw new BusinessException("该团队没组长，无法分配");
        }

        List<DtBusinessTaskDetail> details = dtBusinessTaskMapper.selectDtBusinessTaskDetailListByTaskIds(taskIds);
        List<DtBusinessTaskDetail> hasGavelTaskDetailList = details.stream().filter(t -> t.getHasNicknameVerification().equals("0")).collect(Collectors.toList());
        if(hasGavelTaskDetailList.size() > 0){
            throw new BusinessException("存在呢称验证完成的订单，无法重新调配任务");
        }

        //清空业务组长分配后的数据
        List<DtBusinessTask> dtBusinessTasks = dtBusinessTaskMapper.selectDtBusinessTaskListWithAllocateTimeByIds(taskIds);
        if(dtBusinessTasks.size() > 0){
            List<Long> setNullTaskList = dtBusinessTasks.stream().map(DtBusinessTask::getId).collect(Collectors.toList());
            dtBusinessTaskMapper.clearAllocateTimeByIds(setNullTaskList);
            List<DtBusinessTaskDetail> dtBusinessTaskDetails = dtBusinessTaskMapper.selectDtBusinessTaskDetailListByTaskIds(setNullTaskList.toArray(new Long[setNullTaskList.size()]));
            List<Long> setNullTaskDetailList = dtBusinessTaskDetails.stream().map(DtBusinessTaskDetail::getId).collect(Collectors.toList());
            dtBusinessTaskMapper.clearSalesmanInfo(setNullTaskDetailList);
        }

        for(DtBusinessTaskDetail detail: details){
            detail.setSalesmanLeaderUserId(dept.getLeaderId());
            dtBusinessTaskMapper.updateDtBusinessTaskDetail(detail);
        }

        for(Long taskId:taskIds){
            DtBusinessTask dtBusinessTask = new DtBusinessTask();
            dtBusinessTask.setDeptId(assginReqDto.getDeptId());
            dtBusinessTask.setId(taskId);
            dtBusinessTask.setGroupAllocateTime(new Date());
            count = count + dtBusinessTaskMapper.updateDtBusinessTask(dtBusinessTask);
        }
        return count;
    }

    @Override
    @Transactional
    public String batchInsertTask(List<List<DtBusinessTaskDetail>> list, List<DtBusinessTaskDetail> vaildList) {
        vaildBusinessTaskDetailList(vaildList);
        int successNum = 0;
        int failureNum = 0;

        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (List<DtBusinessTaskDetail> taskDetailList : list)
        {
            try
            {
                doProcessDetail4MerchantId(taskDetailList);
                DtBusinessTask dtBusinessTask = new DtBusinessTask();
                dtBusinessTask.setOrderStatus("1");
                dtBusinessTask.setRequiredCompletionDate(vaildList.get(0).getOrderDate());
                dtBusinessTaskMapper.insertDtBusinessTask(dtBusinessTask);
                dtBusinessTaskMapper.batchInsertDtBusinessTaskDetail(dtBusinessTask.getId(),taskDetailList);
                successNum++;
                successMsg.append("<br/>" + successNum + "、任务编码：" + dtBusinessTask.getId() +" 产生，导入成功");

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "导入失败！";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    private void doProcessDetail4MerchantId(List<DtBusinessTaskDetail> taskDetailList) {
        Set<String> shopNames = taskDetailList.stream().map(DtBusinessTaskDetail::getShopName).collect(Collectors.toSet());
        if(taskDetailList.size() != shopNames.size()){
            throw new BusinessException("<br/>任务中的店铺名，存在重复");
        }
        List<MerchantShopRelation> merchantShopRelations = dtMerchantMapper.selectMerchantShopRelationByShopNames(shopNames);
        if(taskDetailList.size() != merchantShopRelations.size()){
            throw new BusinessException("<br/>存在导入的店铺名没有绑定商家，所以无法导入该任务");
        }
        Map<String,Long> shopnameAndMerchantUserIdMap = merchantShopRelations.stream().collect(Collectors.toMap(MerchantShopRelation::getShopName,MerchantShopRelation::getMerchantUserId,(k1,k2) -> k2));
        for(DtBusinessTaskDetail detail:taskDetailList){
            detail.setMerchantUserId(shopnameAndMerchantUserIdMap.get(detail.getShopName()));
        }
    }

    @Override
    public List<DtBusinessTaskDetail> selectDtBusinessTaskDetailList(Long id) {
        List<DtBusinessTaskDetail> resultList = dtBusinessTaskMapper.selectDtBusinessTaskDetailListByTaskId(id);
        Set<Long> merchantUserIds = resultList.stream().map(DtBusinessTaskDetail::getMerchantUserId).collect(Collectors.toSet());
        Set<Long> salesmanUserIds = resultList.stream().map(DtBusinessTaskDetail::getSalesmanLeaderUserId).collect(Collectors.toSet());
        salesmanUserIds.addAll(merchantUserIds);
        List<Long> userIds = salesmanUserIds.stream().collect(Collectors.toList());
        List<SysUser> sysUsers = sysUserMapper.selectUserListByIds(userIds);
        Map<Long,String> userIdAndNameMap = sysUsers.stream().collect(Collectors.toMap(SysUser::getUserId,SysUser::getUserName,(k1,k2) -> k2));
        for (DtBusinessTaskDetail detail:resultList){
            detail.setSalesmanLeaderUserName(userIdAndNameMap.get(detail.getSalesmanLeaderUserId()));
            detail.setMerchatUserName(userIdAndNameMap.get(detail.getMerchantUserId()));
            detail.setPictureUrl(OssClientUtils.getPictureUrlByOssParam(detail.getPictureUrl()));
            detail.setSalesmanCommitUrl(OssClientUtils.getPictureUrlByOssParam(detail.getSalesmanCommitUrl()));
        }
        return resultList;
    }

    @Override
    public int updateDtBusinessTaskDetail(DtBusinessTaskDetail dtBusinessTaskDetail) {
        if(!DateUtils.isSameDay(new Date(),dtBusinessTaskDetail.getOrderDate())){
            throw new BusinessException("能操作的数据日期是" + DateUtils.getDate());
        }
        return dtBusinessTaskMapper.updateDtBusinessTaskDetail(dtBusinessTaskDetail);
    }

    private void vaildBusinessTaskDetailList(List<DtBusinessTaskDetail> vaildList) {
        if (StringUtils.isNull(vaildList) || vaildList.size() == 0)
        {
            throw new BusinessException("导入的任务数据不能为空！");
        }
        StringBuffer vailedMsg = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Set<String> dateSet = new HashSet<>();
        for(DtBusinessTaskDetail dtBusinessTaskDetail:vaildList){
            if(StringUtils.isEmpty(dtBusinessTaskDetail.getTaskNo())){
                vailedMsg.append("<br/>" + dtBusinessTaskDetail.getShopName()).append("任务代码不能为空");
            }
            if(StringUtils.isNull(dtBusinessTaskDetail.getOrderDate())){
                vailedMsg.append("<br/>" + dtBusinessTaskDetail.getShopName()).append("日期不能为空");
            }
            if(StringUtils.isEmpty(dtBusinessTaskDetail.getShopName())){
                vailedMsg.append("<br/>" + dtBusinessTaskDetail.getTaskNo()).append("店铺名不能为空");
            }
            if(StringUtils.isNull(dtBusinessTaskDetail.getUnitPrice())){
                vailedMsg.append("<br/>" + dtBusinessTaskDetail.getShopName()).append("单价不能为空");
            }
            dateSet.add(sdf.format(dtBusinessTaskDetail.getOrderDate()));
        }
        if(dateSet.size() >1){
            vailedMsg.insert(0,"存在多个订单日期"+ dateSet.toString() + "日期");
        }
        String msg = vailedMsg.toString();
        if(StringUtils.isNotEmpty(msg)){
            throw new BusinessException(msg);
        }
    }

    private void formatDto4DtGroupBusinessTaskRespDto(List<DtGroupBusinessTaskRespDto> resultList,
                                                      Map<Long,String> deptIdAndNameMap) {
        for(DtGroupBusinessTaskRespDto dto: resultList){
            dto.setDeptName(deptIdAndNameMap.get(dto.getDeptId()));
            List<DtGroupBusinessTaskDetailRespDto> detailRespDto = dto.getDtGroupBusinessTaskDetailRespDtoList();
            dto.setOrderNumber(detailRespDto.size());
            List<BigDecimal> principalList = detailRespDto.stream().map(DtGroupBusinessTaskDetailRespDto::getUnitPrice).collect(Collectors.toList());
            dto.setTotalPrincipal(principalList.stream().reduce(new BigDecimal(0),(acc,item) -> {
                BigDecimal result = acc.add(item);
                return result;
            }));
            List<BigDecimal> actualPrincipalList = detailRespDto.stream().map(DtGroupBusinessTaskDetailRespDto::getPromotersModifyUnitPrice).collect(Collectors.toList());
            dto.setActualTotalPrincipal(actualPrincipalList.stream().reduce(new BigDecimal(0),(acc,item) ->{
                BigDecimal result = acc.add(item);
                return result;
            }));

        }
    }

    /**
     * 新增商业任务信息
     * 
     * @param dtBusinessTask 商业任务信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertDtBusinessTask(DtBusinessTask dtBusinessTask)
    {
        /*dtBusinessTask.setCreateTime(DateUtils.getNowDate());
        int rows = dtBusinessTaskMapper.insertDtBusinessTask(dtBusinessTask);
        insertDtBusinessTaskDetail(dtBusinessTask);
        return rows;*/
        return 0;
    }

    /**
     * 修改商业任务信息
     * 
     * @param dtBusinessTask 商业任务信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateDtBusinessTask(DtBusinessTask dtBusinessTask)
    {
        dtBusinessTaskMapper.deleteDtBusinessTaskDetailByTaskId(dtBusinessTask.getId());
        insertDtBusinessTaskDetail(dtBusinessTask);
        return dtBusinessTaskMapper.updateDtBusinessTask(dtBusinessTask);
    }

    /**
     * 删除商业任务信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDtBusinessTaskByIds(String ids)
    {
        dtBusinessTaskMapper.deleteDtBusinessTaskDetailByTaskIds(Convert.toStrArray(ids));
        return dtBusinessTaskMapper.deleteDtBusinessTaskByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除商业任务信息信息
     * 
     * @param id 商业任务信息ID
     * @return 结果
     */
    @Override
    public int deleteDtBusinessTaskById(Long id)
    {
        dtBusinessTaskMapper.deleteDtBusinessTaskDetailByTaskId(id);
        return dtBusinessTaskMapper.deleteDtBusinessTaskById(id);
    }

    /**
     * 新增商业任务信息明细信息
     * 
     * @param dtBusinessTask 商业任务信息对象
     */
    public void insertDtBusinessTaskDetail(DtBusinessTask dtBusinessTask)
    {
        List<DtBusinessTaskDetail> dtBusinessTaskDetailList = dtBusinessTask.getDtBusinessTaskDetailList();
        Long id = dtBusinessTask.getId();
        if (StringUtils.isNotNull(dtBusinessTaskDetailList))
        {
            List<DtBusinessTaskDetail> list = new ArrayList<DtBusinessTaskDetail>();
            for (DtBusinessTaskDetail dtBusinessTaskDetail : dtBusinessTaskDetailList)
            {
                dtBusinessTaskDetail.setId(id);
                list.add(dtBusinessTaskDetail);
            }
            if (list.size() > 0)
            {
                dtBusinessTaskMapper.batchDtBusinessTaskDetail(list);
            }
        }
    }
}
