package com.ruoyi.groupcompany.service;

import java.util.List;
import com.ruoyi.groupcompany.domain.DtBusinessTask;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskRespDto;
import com.ruoyi.groupcompany.domain.request.AssginReqDto;
import com.ruoyi.groupcompany.domain.request.DtGroupBusinessTaskReqDto;

/**
 * 商业任务信息Service接口
 * 
 * @author zimao
 * @date 2020-08-27
 */
public interface IDtBusinessTaskService 
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
    public int insertDtBusinessTask(DtBusinessTask dtBusinessTask);

    /**
     * 修改商业任务信息
     * 
     * @param dtBusinessTask 商业任务信息
     * @return 结果
     */
    public int updateDtBusinessTask(DtBusinessTask dtBusinessTask);

    /**
     * 批量删除商业任务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDtBusinessTaskByIds(String ids);

    /**
     * 删除商业任务信息信息
     * 
     * @param id 商业任务信息ID
     * @return 结果
     */
    public int deleteDtBusinessTaskById(Long id);


    public List<DtGroupBusinessTaskRespDto> selectGroupDtBusinessTaskDtoList(DtGroupBusinessTaskReqDto dtGroupBusinessTaskReqDto);


    int assginDept(AssginReqDto assginReqDto);
}
