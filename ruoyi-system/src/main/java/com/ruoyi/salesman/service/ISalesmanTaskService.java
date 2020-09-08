package com.ruoyi.salesman.service;

import com.ruoyi.salesman.domain.SalesmanTask;
import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.SalesmanTaskRespDto;
import com.ruoyi.salesman.domain.request.SalesmanTaskReqDto;
import com.ruoyi.system.domain.SysUser;

import java.util.List;
import java.util.Set;

/**
 * 商业任务信息Service接口
 * 
 * @author zimao
 * @date 2020-08-27
 */
public interface ISalesmanTaskService
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


    public List<SalesmanTaskRespDto> selectSalesmanTaskDtoList(SalesmanTaskReqDto dtGroupBusinessTaskReqDto, SysUser user, Set<Long> salesmanUserIds);


    List<SalesmanTaskDetail> selectSalesmanTaskDetailList(Long id);

    int updateSalesmanTaskDetail(SalesmanTaskDetail dtBusinessTaskDetail);

    Set<Long> selectTaskIdsBySalesmanUserId(Long userId);
}
