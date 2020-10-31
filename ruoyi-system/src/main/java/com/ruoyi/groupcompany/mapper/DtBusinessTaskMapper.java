package com.ruoyi.groupcompany.mapper;

import java.util.List;
import com.ruoyi.groupcompany.domain.DtBusinessTask;
import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskDetailRespDto;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskRespDto;
import com.ruoyi.groupcompany.domain.request.DtGroupBusinessTaskReqDto;
import org.apache.ibatis.annotations.Param;

/**
 * 商业任务信息Mapper接口
 * 
 * @author zimao
 * @date 2020-08-27
 */
public interface DtBusinessTaskMapper 
{
    /**
     * 查询商业任务信息
     * 
     * @param id 商业任务信息ID
     * @return 商业任务信息
     */
    public DtBusinessTask selectDtBusinessTaskById(Long id);

    /**
     * 查询商业任务信息列表
     * 
     * @param dtBusinessTask 商业任务信息
     * @return 商业任务信息集合
     */
    public List<DtBusinessTask> selectDtBusinessTaskList(DtBusinessTask dtBusinessTask);

    /**
     * 新增商业任务信息
     * 
     * @param dtBusinessTask 商业任务信息
     * @return 结果
     */
    public Long insertDtBusinessTask(DtBusinessTask dtBusinessTask);

    /**
     * 修改商业任务信息
     * 
     * @param dtBusinessTask 商业任务信息
     * @return 结果
     */
    public int updateDtBusinessTask(DtBusinessTask dtBusinessTask);

    /**
     * 删除商业任务信息
     * 
     * @param id 商业任务信息ID
     * @return 结果
     */
    public int deleteDtBusinessTaskById(Long id);

    /**
     * 批量删除商业任务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDtBusinessTaskByIds(String[] ids);

    /**
     * 批量删除商业任务信息明细
     * 
     * @param customerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDtBusinessTaskDetailByTaskIds(String[] ids);
    
    /**
     * 批量新增商业任务信息明细
     * 
     * @param dtBusinessTaskDetailList 商业任务信息明细列表
     * @return 结果
     */
    public int batchDtBusinessTaskDetail(List<DtBusinessTaskDetail> dtBusinessTaskDetailList);
    

    /**
     * 通过商业任务信息ID删除商业任务信息明细信息
     * 
     * @param id 角色ID
     * @return 结果
     */
    public int deleteDtBusinessTaskDetailByTaskId(Long id);

    /**
     * 查询商业任务列表
     *
     * @param dtGroupBusinessTaskReqDto 商业任务请求
     * @return 结果
     */
    List<DtGroupBusinessTaskRespDto> selectGroupDtBusinessTaskDtoList(DtGroupBusinessTaskReqDto dtGroupBusinessTaskReqDto);


    List<DtBusinessTask> selectDtBusinessTaskListWithAllocateTimeByIds(@Param("array")Long[] taskIds);

    void batchInsertDtBusinessTaskDetail(@Param("taskId") Long taskId, @Param("list") List<DtBusinessTaskDetail> taskDetailList);

    List<DtGroupBusinessTaskDetailRespDto> selectGroupBusinessTaskDetailDtoByTaskIds(@Param("array") List<Long> taskIds);

    List<DtBusinessTaskDetail> selectDtBusinessTaskDetailListByTaskId(Long id);

    int updateDtBusinessTaskDetail(DtBusinessTaskDetail dtBusinessTaskDetail);

    List<DtBusinessTaskDetail> selectDtBusinessTaskDetailListByTaskIds(@Param("array") Long[] taskIds);

    List<DtBusinessTaskDetail> selectDtBusinessTaskDetailList(DtBusinessTaskDetail dtBusinessTaskDetail);

    List<DtBusinessTaskDetail> selectDtBusinessTaskDetailListByIds(@Param("array") Long[] idlist);

    List<DtBusinessTask> selectDtBusinessTaskByIds(@Param("array") List<Long> taskIds);

    int clearAllocateTimeByIds(@Param("list") List<Long> setNullTaskList);

    int clearSalesmanInfo(@Param("list")List<Long> setNullTaskDetailList);
}
