package com.ruoyi.salesmanleader.mapper;

import com.ruoyi.salesmanleader.domain.SalesmanLeaderTask;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderTaskDetail;
import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderTaskDetailRespDto;
import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderTaskRespDto;
import com.ruoyi.salesmanleader.domain.request.SalesmanLeaderTaskReqDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商业任务信息Mapper接口
 * 
 * @author zimao
 * @date 2020-08-27
 */
public interface SalesmanLeaderTaskMapper
{
    /**
     * 查询商业任务信息
     * 
     * @param id 商业任务信息ID
     * @return 商业任务信息
     */
    public SalesmanLeaderTask selectSalesmanLeaderTaskById(Long id);

    /**
     * 查询商业任务信息列表
     * 
     * @param salesmanLeaderTask 商业任务信息
     * @return 商业任务信息集合
     */
    public List<SalesmanLeaderTask> selectSalesmanLeaderTaskList(SalesmanLeaderTask salesmanLeaderTask);

    /**
     * 新增商业任务信息
     * 
     * @param salesmanLeaderTask 商业任务信息
     * @return 结果
     */
    public Long insertSalesmanLeaderTask(SalesmanLeaderTask salesmanLeaderTask);

    /**
     * 修改商业任务信息
     * 
     * @param salesmanLeaderTask 商业任务信息
     * @return 结果
     */
    public int updateSalesmanLeaderTask(SalesmanLeaderTask salesmanLeaderTask);

    /**
     * 删除商业任务信息
     * 
     * @param id 商业任务信息ID
     * @return 结果
     */
    public int deleteSalesmanLeaderTaskById(Long id);

    /**
     * 批量删除商业任务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSalesmanLeaderTaskByIds(String[] ids);

    /**
     * 批量删除商业任务信息明细
     * 
     * @param customerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSalesmanLeaderTaskDetailByTaskIds(String[] ids);
    
    /**
     * 批量新增商业任务信息明细
     * 
     * @param dtBusinessTaskDetailList 商业任务信息明细列表
     * @return 结果
     */
    public int batchSalesmanLeaderTaskDetail(List<SalesmanLeaderTaskDetail> salesmanLeaderTaskDetailList);
    

    /**
     * 通过商业任务信息ID删除商业任务信息明细信息
     * 
     * @param id 角色ID
     * @return 结果
     */
    public int deleteSalesmanLeaderTaskDetailByTaskId(Long id);

    /**
     * 查询商业任务列表
     *
     * @param salesmanLeaderTaskReqDto 商业任务请求
     * @return 结果
     */
    List<SalesmanLeaderTaskRespDto> selectSalesmanLeaderTaskDtoList(SalesmanLeaderTaskReqDto salesmanLeaderTaskReqDto);


    List<SalesmanLeaderTask> selectSalesmanLeaderTaskListWithAllocateTimeByIds(@Param("array")Long[] taskIds);

    void batchInsertSalesmanLeaderTaskDetail(@Param("taskId") Long taskId, @Param("list") List<SalesmanLeaderTaskDetail> taskDetailList);

    List<SalesmanLeaderTaskDetailRespDto> selectSalesmanLeaderTaskDetailDtoByTaskIds(@Param("array") List<Long> taskIds);

    List<SalesmanLeaderTaskDetail> selectSalesmanLeaderTaskDetailListByTaskId(Long id);

    int updateSalesmanLeaderTaskDetail(SalesmanLeaderTaskDetail salesmanLeaderTaskDetail);

    List<SalesmanLeaderTaskDetail> selectSalesmanLeaderTaskDetailListByTaskIds(@Param("array") Long[] taskIds);

    List<SalesmanLeaderTaskDetail> selectSalesmanLeaderTaskDetailList(SalesmanLeaderTaskDetail salesmanLeaderTaskDetail);

    List<SalesmanLeaderTaskDetail> selectSalesmanLeaderTaskDetailListByIds(@Param("array") Long[] idlist);

    List<SalesmanLeaderTask> selectSalesmanLeaderTaskByIds(@Param("array") List<Long> taskIds);

    int updateSalesmanLeaderTaskDetailByNameAndIds(@Param("salesmanUserId") Long salesmanUserId,@Param("array") Long[] taskIds);

    void updateSalesmanLeaderTaskAllocateTimeByIds(@Param("array") Long[] taskIds);
}
