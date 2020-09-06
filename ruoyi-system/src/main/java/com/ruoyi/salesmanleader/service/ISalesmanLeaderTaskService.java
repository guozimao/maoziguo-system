package com.ruoyi.salesmanleader.service;

import com.ruoyi.salesmanleader.domain.SalesmanLeaderTask;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderTaskDetail;
import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderTaskRespDto;
import com.ruoyi.salesmanleader.domain.request.SalesmanAssginReqDto;
import com.ruoyi.salesmanleader.domain.request.SalesmanLeaderTaskReqDto;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

/**
 * 商业任务信息Service接口
 * 
 * @author zimao
 * @date 2020-08-27
 */
public interface ISalesmanLeaderTaskService
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
     * @param dtBusinessTask 商业任务信息
     * @return 商业任务信息集合
     */
    public List<SalesmanLeaderTask> selectSalesmanLeaderTaskList(SalesmanLeaderTask dtBusinessTask);

    /**
     * 新增商业任务信息
     * 
     * @param dtBusinessTask 商业任务信息
     * @return 结果
     */
    public int insertSalesmanLeaderTask(SalesmanLeaderTask dtBusinessTask);

    /**
     * 修改商业任务信息
     * 
     * @param dtBusinessTask 商业任务信息
     * @return 结果
     */
    public int updateSalesmanLeaderTask(SalesmanLeaderTask dtBusinessTask);

    /**
     * 批量删除商业任务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSalesmanLeaderTaskByIds(String ids);

    /**
     * 删除商业任务信息信息
     * 
     * @param id 商业任务信息ID
     * @return 结果
     */
    public int deleteSalesmanLeaderTaskById(Long id);


    public List<SalesmanLeaderTaskRespDto> selectSalesmanLeaderTaskDtoList(SalesmanLeaderTaskReqDto SalesmanLeaderTaskReqDto, SysUser user);


    int assginDept(SalesmanAssginReqDto assginReqDto);


    String batchInsertTask(List<List<SalesmanLeaderTaskDetail>> list, List<SalesmanLeaderTaskDetail> vaildList);

    List<SalesmanLeaderTaskDetail> selectSalesmanLeaderTaskDetailList(Long id);

    int updateSalesmanLeaderTaskDetail(SalesmanLeaderTaskDetail dtBusinessTaskDetail);

    SysUser getSalesmanBySalesManUserName(String salesmanUserName);
}
