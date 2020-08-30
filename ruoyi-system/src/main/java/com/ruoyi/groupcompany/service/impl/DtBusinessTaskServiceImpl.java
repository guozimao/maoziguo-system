package com.ruoyi.groupcompany.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.ruoyi.businessteam.mapper.DtSalesmanMapper;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskDetailRespDto;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskRespDto;
import com.ruoyi.groupcompany.domain.request.AssginReqDto;
import com.ruoyi.groupcompany.domain.request.DtGroupBusinessTaskReqDto;
import com.ruoyi.system.domain.SysDept;
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

    /**
     * 查询商业任务信息
     * 
     * @param id 商业任务信息ID
     * @return 商业任务信息
     */
    @Override
    public DtBusinessTask selectDtBusinessTaskById(Long id)
    {
        return dtBusinessTaskMapper.selectDtBusinessTaskById(id);
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
        List<DtBusinessTask> dtBusinessTasks = dtBusinessTaskMapper.selectDtBusinessTaskListWithAllocateTimeByIds(taskIds);
        if(dtBusinessTasks.size() > 0){
            throw new BusinessException("团队组长已经分配任务了，无法重新分配");
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
                DtBusinessTask dtBusinessTask = new DtBusinessTask();
                dtBusinessTask.setOrderStatus("1");
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

    private void vaildBusinessTaskDetailList(List<DtBusinessTaskDetail> vaildList) {
        if (StringUtils.isNull(vaildList) || vaildList.size() == 0)
        {
            throw new BusinessException("导入的任务数据不能为空！");
        }
        StringBuffer vailedMsg = new StringBuffer();
        for(DtBusinessTaskDetail dtBusinessTaskDetail:vaildList){
            if(StringUtils.isEmpty(dtBusinessTaskDetail.getTaskNo())){
                vailedMsg.append("<br/>" + dtBusinessTaskDetail.getShopName()).append("任务代码不能为空");
            }
            if(StringUtils.isEmpty(dtBusinessTaskDetail.getShopName())){
                vailedMsg.append("<br/>" + dtBusinessTaskDetail.getTaskNo()).append("店铺名不能为空");
            }
            if(StringUtils.isNull(dtBusinessTaskDetail.getUnitPrice())){
                vailedMsg.append("<br/>" + dtBusinessTaskDetail.getShopName()).append("单价不能为空");
            }
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
