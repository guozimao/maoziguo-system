package com.ruoyi.groupcompany.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.ruoyi.businessteam.domain.DtSalesman;
import com.ruoyi.businessteam.mapper.DtSalesmanMapper;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskDetailRespDto;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskRespDto;
import com.ruoyi.groupcompany.domain.request.AssginReqDto;
import com.ruoyi.groupcompany.domain.request.DtGroupBusinessTaskReqDto;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysUserMapper;
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

        List<Long> deptIds = resultList.stream().map(DtGroupBusinessTaskRespDto::getDeptId).collect(Collectors.toList());

        List<Long> salesmanIds = resultList.stream().map(DtGroupBusinessTaskRespDto::getSalesmanId).collect(Collectors.toList());

        List<SysDept> depts = deptIds.size() > 0 ? sysDeptMapper.selectDeptListByIds(deptIds) : Collections.emptyList();

        List<DtSalesman> dtSalesmans = salesmanIds.size() > 0 ? dtSalesmanMapper.selectDtSalesmanByIds(salesmanIds) : Collections.emptyList();

        Map<Long,String> deptIdAndNameMap = depts.stream().collect(Collectors.toMap(SysDept::getDeptId, SysDept::getDeptName, (key1, key2) -> key2));

        Map<Long,String> salesmanIdAndUserName = getsalesmanIdAndUserName4DtGroupBusinessTaskRespDto(dtSalesmans);

        formatDto4DtGroupBusinessTaskRespDto(resultList,deptIdAndNameMap,salesmanIdAndUserName);

        return resultList;
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

    private void formatDto4DtGroupBusinessTaskRespDto(List<DtGroupBusinessTaskRespDto> resultList,
                                                      Map<Long,String> deptIdAndNameMap,
                                                      Map<Long,String> salesmanIdAndUserName) {
        for(DtGroupBusinessTaskRespDto dto: resultList){
            dto.setDeptName(deptIdAndNameMap.get(dto.getDeptId()));
            dto.setSalesmanName(salesmanIdAndUserName.get(dto.getSalesmanId()));
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

    private Map<Long, String> getsalesmanIdAndUserName4DtGroupBusinessTaskRespDto(List<DtSalesman> dtSalesmans) {
        Map<Long,Long> salesmanIdAndUserIdMap = dtSalesmans.stream().collect(Collectors.toMap(DtSalesman::getId, DtSalesman::getUserId, (key1, key2) -> key2));
        List<Long> userIds = salesmanIdAndUserIdMap.entrySet().stream().map(x -> x.getValue()).collect(Collectors.toList());
        List<SysUser> users = sysUserMapper.selectUserListByIds(userIds);
        Map<Long,String> userIdAndNameMap = users.stream().collect(Collectors.toMap(SysUser::getUserId,SysUser::getUserName,(key1,key2) -> key2));
        Map<Long,String> resultMap = new HashMap<>();
        for(Map.Entry<Long,Long> entry:salesmanIdAndUserIdMap.entrySet()){
            resultMap.put(entry.getKey(),userIdAndNameMap.get(entry.getValue()));
        }
        return resultMap;
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
        dtBusinessTask.setCreateTime(DateUtils.getNowDate());
        int rows = dtBusinessTaskMapper.insertDtBusinessTask(dtBusinessTask);
        insertDtBusinessTaskDetail(dtBusinessTask);
        return rows;
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
