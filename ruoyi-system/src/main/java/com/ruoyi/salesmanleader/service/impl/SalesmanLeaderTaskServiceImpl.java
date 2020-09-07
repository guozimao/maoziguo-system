package com.ruoyi.salesmanleader.service.impl;

import com.ruoyi.businessteam.mapper.DtSalesmanMapper;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderTask;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderTaskDetail;
import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderTaskDetailRespDto;
import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderTaskRespDto;
import com.ruoyi.salesmanleader.domain.request.AssginSalesmanReqDto;
import com.ruoyi.salesmanleader.domain.request.SalesmanLeaderTaskReqDto;
import com.ruoyi.salesmanleader.mapper.SalesmanLeaderTaskMapper;
import com.ruoyi.salesmanleader.service.ISalesmanLeaderTaskService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商业任务信息Service业务层处理
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Service
public class SalesmanLeaderTaskServiceImpl implements ISalesmanLeaderTaskService
{
    private static final Logger log = LoggerFactory.getLogger(SalesmanLeaderTaskServiceImpl.class);
    @Autowired
    private SalesmanLeaderTaskMapper salesmanLeaderTaskMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private DtSalesmanMapper dtSalesmanMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询商业任务信息
     * 
     * @param id 商业任务信息ID
     * @return 商业任务信息
     */
    @Override
    public SalesmanLeaderTask selectSalesmanLeaderTaskById(Long id)
    {
        SalesmanLeaderTask dtBusinessTask = salesmanLeaderTaskMapper.selectSalesmanLeaderTaskById(id);

        doProcessUrl4SalesmanLeaderTask(dtBusinessTask);

        return dtBusinessTask;
    }

    private void doProcessUrl4SalesmanLeaderTask(SalesmanLeaderTask dtBusinessTask) {
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
    public List<SalesmanLeaderTask> selectSalesmanLeaderTaskList(SalesmanLeaderTask dtBusinessTask) {
        return salesmanLeaderTaskMapper.selectSalesmanLeaderTaskList(dtBusinessTask);
    }

    /**
     * 查询任务信息列表
     * 
     * @param salesmanLeaderTaskReqDto 任务信息
     * @return 任务信息
     */
    @Override
    public List<SalesmanLeaderTaskRespDto> selectSalesmanLeaderTaskDtoList(SalesmanLeaderTaskReqDto salesmanLeaderTaskReqDto, SysUser user)
    {
        vaildSalesmanleader(user);

        salesmanLeaderTaskReqDto.setDeptId(user.getDeptId());

        List<SalesmanLeaderTaskRespDto> resultList = salesmanLeaderTaskMapper.selectSalesmanLeaderTaskDtoList(salesmanLeaderTaskReqDto);

        doProcessDetail4SalesmanLeaderTaskRespDto(resultList);

        formatDto4SalesmanLeaderTaskRespDto(resultList);

        return resultList;
    }

    private void vaildSalesmanleader(SysUser user) {
        if(StringUtils.isNull(user.getDept())){
            throw new BusinessException("本账号还没设置部门/团队，无法查看数据");
        }else if(!user.getDept().getLeaderId().equals(user.getUserId())){
            throw new BusinessException("本账号不是部门/团队的负责人，无法查看数据");
        }
    }

    private void doProcessDetail4SalesmanLeaderTaskRespDto(List<SalesmanLeaderTaskRespDto> resultList) {
        List<Long> taskIds =  resultList.stream().map(SalesmanLeaderTaskRespDto::getId).collect(Collectors.toList());
        if(taskIds.size() == 0){
            return;
        }
        List<SalesmanLeaderTaskDetailRespDto> details = salesmanLeaderTaskMapper.selectSalesmanLeaderTaskDetailDtoByTaskIds(taskIds);
        MultiValuedMap<Long, SalesmanLeaderTaskDetailRespDto> taskIdAndDetailListMap = new ArrayListValuedHashMap<>();
        Map<Long,Long> taskIdSalesmanUserIdMap = new HashMap<>();
        for(SalesmanLeaderTaskDetailRespDto detail:details){
            taskIdAndDetailListMap.put(detail.getTaskId(),detail);
            taskIdSalesmanUserIdMap.put(detail.getTaskId(),detail.getSalesmanUserId());
        }
        List<SysUser> users = sysUserMapper.selectUserListByIds(taskIdSalesmanUserIdMap.values().stream().collect(Collectors.toList()));
        Map<Long,String> userIdAndNameMap = users.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getUserName,(k1,k2) -> k2));
        for(SalesmanLeaderTaskRespDto dto:resultList){
            dto.setSalesmanLeaderTaskDetailRespDtoList((List<SalesmanLeaderTaskDetailRespDto>)taskIdAndDetailListMap.get(dto.getId()));
            dto.setSalesmanName(userIdAndNameMap.get(taskIdSalesmanUserIdMap.get(dto.getId())));
        }

    }

    @Override
    @Transactional
    public String batchInsertTask(List<List<SalesmanLeaderTaskDetail>> list, List<SalesmanLeaderTaskDetail> vaildList) {
        vaildSalesmanLeaderTaskDetailList(vaildList);
        int successNum = 0;
        int failureNum = 0;

        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (List<SalesmanLeaderTaskDetail> taskDetailList : list)
        {
            try
            {
                SalesmanLeaderTask dtBusinessTask = new SalesmanLeaderTask();
                dtBusinessTask.setOrderStatus("1");
                dtBusinessTask.setRequiredCompletionDate(vaildList.get(0).getOrderDate());
                salesmanLeaderTaskMapper.insertSalesmanLeaderTask(dtBusinessTask);
                salesmanLeaderTaskMapper.batchInsertSalesmanLeaderTaskDetail(dtBusinessTask.getId(),taskDetailList);
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

    @Override
    public List<SalesmanLeaderTaskDetail> selectSalesmanLeaderTaskDetailList(Long id) {
        List<SalesmanLeaderTaskDetail> resultList = salesmanLeaderTaskMapper.selectSalesmanLeaderTaskDetailListByTaskId(id);
        for (SalesmanLeaderTaskDetail detail:resultList){
            detail.setPictureUrl(OssClientUtils.getPictureUrlByOssParam(detail.getPictureUrl()));
            detail.setSalesmanCommitUrl(OssClientUtils.getPictureUrlByOssParam(detail.getSalesmanCommitUrl()));
        }
        return resultList;
    }

    @Override
    public int updateSalesmanLeaderTaskDetail(SalesmanLeaderTaskDetail dtBusinessTaskDetail) {
        return salesmanLeaderTaskMapper.updateSalesmanLeaderTaskDetail(dtBusinessTaskDetail);
    }

    @Override
    public SysUser getSalesmanBySalesManUserName(String salesmanUserName) {
        return sysUserMapper.selectUserByUserName(salesmanUserName);
    }

    @Override
    @Transactional
    public int assginSalesman(AssginSalesmanReqDto assginReqDto) {
        Long[] taskIds = Convert.toLongArray(assginReqDto.getIds());
        List<SalesmanLeaderTask> salesmanLeaderTasks = salesmanLeaderTaskMapper.selectSalesmanLeaderTaskByIds(Arrays.asList(taskIds));
        List<SalesmanLeaderTask> salesmanLeaderTasksWithFinishedStatus = salesmanLeaderTasks.stream().filter(item -> item.getOrderStatus().equals("0")).collect(Collectors.toList());
        if(salesmanLeaderTasksWithFinishedStatus.size() > 0){
            throw new BusinessException("存在任务是完成的，无法重新分配");
        }
        salesmanLeaderTaskMapper.updateSalesmanLeaderTaskAllocateTimeByIds(taskIds);
        return salesmanLeaderTaskMapper.updateSalesmanLeaderTaskDetailByNameAndIds(assginReqDto.getSalesmanUserId(),taskIds);
    }

    private void vaildSalesmanLeaderTaskDetailList(List<SalesmanLeaderTaskDetail> vaildList) {
        if (StringUtils.isNull(vaildList) || vaildList.size() == 0)
        {
            throw new BusinessException("导入的任务数据不能为空！");
        }
        StringBuffer vailedMsg = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Set<String> dateSet = new HashSet<>();
        for(SalesmanLeaderTaskDetail dtBusinessTaskDetail:vaildList){
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

    private void formatDto4SalesmanLeaderTaskRespDto(List<SalesmanLeaderTaskRespDto> resultList) {
        for(SalesmanLeaderTaskRespDto dto: resultList){
            List<SalesmanLeaderTaskDetailRespDto> detailRespDto = dto.getSalesmanLeaderTaskDetailRespDtoList();
            dto.setOrderNumber(detailRespDto.size());
            List<BigDecimal> principalList = detailRespDto.stream().map(SalesmanLeaderTaskDetailRespDto::getUnitPrice).collect(Collectors.toList());
            dto.setTotalPrincipal(principalList.stream().reduce(new BigDecimal(0),(acc,item) -> {
                BigDecimal result = acc.add(item);
                return result;
            }));
            List<BigDecimal> actualPrincipalList = detailRespDto.stream().map(SalesmanLeaderTaskDetailRespDto::getPromotersModifyUnitPrice).collect(Collectors.toList());
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
    public int insertSalesmanLeaderTask(SalesmanLeaderTask dtBusinessTask)
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
    public int updateSalesmanLeaderTask(SalesmanLeaderTask dtBusinessTask)
    {
        salesmanLeaderTaskMapper.deleteSalesmanLeaderTaskDetailByTaskId(dtBusinessTask.getId());
        insertSalesmanLeaderTaskDetail(dtBusinessTask);
        return salesmanLeaderTaskMapper.updateSalesmanLeaderTask(dtBusinessTask);
    }

    /**
     * 删除商业任务信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSalesmanLeaderTaskByIds(String ids)
    {
        salesmanLeaderTaskMapper.deleteSalesmanLeaderTaskDetailByTaskIds(Convert.toStrArray(ids));
        return salesmanLeaderTaskMapper.deleteSalesmanLeaderTaskByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除商业任务信息信息
     * 
     * @param id 商业任务信息ID
     * @return 结果
     */
    @Override
    public int deleteSalesmanLeaderTaskById(Long id)
    {
        salesmanLeaderTaskMapper.deleteSalesmanLeaderTaskDetailByTaskId(id);
        return salesmanLeaderTaskMapper.deleteSalesmanLeaderTaskById(id);
    }

    /**
     * 新增商业任务信息明细信息
     * 
     * @param dtBusinessTask 商业任务信息对象
     */
    public void insertSalesmanLeaderTaskDetail(SalesmanLeaderTask dtBusinessTask)
    {
        List<SalesmanLeaderTaskDetail> dtBusinessTaskDetailList = dtBusinessTask.getDtBusinessTaskDetailList();
        Long id = dtBusinessTask.getId();
        if (StringUtils.isNotNull(dtBusinessTaskDetailList))
        {
            List<SalesmanLeaderTaskDetail> list = new ArrayList<SalesmanLeaderTaskDetail>();
            for (SalesmanLeaderTaskDetail dtBusinessTaskDetail : dtBusinessTaskDetailList)
            {
                dtBusinessTaskDetail.setId(id);
                list.add(dtBusinessTaskDetail);
            }
            if (list.size() > 0)
            {
                salesmanLeaderTaskMapper.batchSalesmanLeaderTaskDetail(list);
            }
        }
    }
}
