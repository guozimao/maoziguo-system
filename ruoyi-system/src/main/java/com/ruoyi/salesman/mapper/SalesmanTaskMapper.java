package com.ruoyi.salesman.mapper;

import com.ruoyi.salesman.domain.SalesmanTask;
import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.SalesmanTaskDetailRespDto;
import com.ruoyi.salesman.domain.reponse.SalesmanTaskRespDto;
import com.ruoyi.salesman.domain.request.SalesmanTaskReqDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 商业任务信息Mapper接口
 * 
 * @author zimao
 * @date 2020-08-27
 */
public interface SalesmanTaskMapper
{
    /**
     * 查询商业任务信息
     * 
     * @param id 商业任务信息ID
     * @return 商业任务信息
     */
    public SalesmanTask selectSalesmanTaskById(Long id);

    /**
     * 查询商业任务信息列表
     * 
     * @param dtBusinessTask 商业任务信息
     * @return 商业任务信息集合
     */
    public List<SalesmanTask> selectSalesmanTaskList(SalesmanTask dtBusinessTask);


    /**
     * 修改商业任务信息
     * 
     * @param dtBusinessTask 商业任务信息
     * @return 结果
     */
    public int updateSalesmanTask(SalesmanTask dtBusinessTask);

    
    /**
     * 批量新增商业任务信息明细
     * 
     * @param dtBusinessTaskDetailList 商业任务信息明细列表
     * @return 结果
     */
    public int batchSalesmanTaskDetail(List<SalesmanTaskDetail> dtBusinessTaskDetailList);
    

    /**
     * 通过商业任务信息ID删除商业任务信息明细信息
     * 
     * @param id 角色ID
     * @return 结果
     */
    public int deleteSalesmanTaskDetailByTaskId(Long id);

    /**
     * 查询商业任务列表
     *
     * @param salesmanTaskReqDto 商业任务请求
     * @return 结果
     */
    List<SalesmanTaskRespDto> selectSalesmanTaskDtoList(SalesmanTaskReqDto salesmanTaskReqDto);

    List<SalesmanTaskDetailRespDto> selectSalesmanTaskDetailDtoByTaskIds(@Param("array") List<Long> taskIds);

    List<SalesmanTaskDetail> selectSalesmanTaskDetailListByTaskId(Long id);

    int updateSalesmanTaskDetail(SalesmanTaskDetail salesmanTaskDetail);

    List<SalesmanTaskDetail> selectSalesmanTaskDetailList(SalesmanTaskDetail salesmanTaskDetail);

    List<SalesmanTaskDetail> selectSalesmanTaskDetailListByIds(@Param("array") Long[] idlist);

    List<SalesmanTask> selectSalesmanTaskByIds(@Param("array") List<Long> taskIds);

    Set<Long> selectTaskIdsBySalesmanUserId(Long userId);

    List<SalesmanTaskRespDto> selectSalesmanTaskDtoListByIds(@Param("salesmanTaskReqDto") SalesmanTaskReqDto salesmanTaskReqDto,@Param("set") Set<Long> salesmanUserIds);
}
