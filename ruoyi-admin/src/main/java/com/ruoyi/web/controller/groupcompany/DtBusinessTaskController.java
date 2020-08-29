package com.ruoyi.web.controller.groupcompany;

import java.util.List;

import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskRespDto;
import com.ruoyi.groupcompany.domain.request.AssginReqDto;
import com.ruoyi.groupcompany.domain.request.DtGroupBusinessTaskReqDto;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.groupcompany.domain.DtBusinessTask;
import com.ruoyi.groupcompany.service.IDtBusinessTaskService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商业任务信息Controller
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Controller
@RequestMapping("/groupcompany/task")
public class DtBusinessTaskController extends BaseController
{
    private String prefix = "groupcompany/task";

    @Autowired
    private IDtBusinessTaskService dtBusinessTaskService;

    @RequiresPermissions("groupcompany:task:view")
    @GetMapping()
    public String task()
    {
        return prefix + "/task";
    }

    /**
     * 查询商业任务信息列表
     */
    @RequiresPermissions("groupcompany:task:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DtGroupBusinessTaskReqDto DtGroupBusinessTaskReqDto)
    {
        startPage();
        List<DtGroupBusinessTaskRespDto> list = dtBusinessTaskService.selectGroupDtBusinessTaskDtoList(DtGroupBusinessTaskReqDto);
        return getDataTable(list);
    }

    /**
     * 导出商业任务信息列表
     */
    @RequiresPermissions("groupcompany:task:export")
    @Log(title = "商业任务信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DtBusinessTask dtBusinessTask)
    {
        List<DtBusinessTask> list = dtBusinessTaskService.selectDtBusinessTaskList(dtBusinessTask);
        ExcelUtil<DtBusinessTask> util = new ExcelUtil<DtBusinessTask>(DtBusinessTask.class);
        return util.exportExcel(list, "task");
    }

    /**
     * 新增商业任务信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存商业任务信息
     */
    @RequiresPermissions("groupcompany:task:add")
    @Log(title = "商业任务信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DtBusinessTask dtBusinessTask)
    {
        return toAjax(dtBusinessTaskService.insertDtBusinessTask(dtBusinessTask));
    }

    /**
     * 修改商业任务信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DtBusinessTask dtBusinessTask = dtBusinessTaskService.selectDtBusinessTaskById(id);
        mmap.put("dtBusinessTask", dtBusinessTask);
        return prefix + "/edit";
    }

    /**
     * 修改保存商业任务信息
     */
    @RequiresPermissions("groupcompany:task:edit")
    @Log(title = "商业任务信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DtBusinessTask dtBusinessTask)
    {
        return toAjax(dtBusinessTaskService.updateDtBusinessTask(dtBusinessTask));
    }

    /**
     * 删除商业任务信息
     */
    @RequiresPermissions("groupcompany:task:remove")
    @Log(title = "商业任务信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(dtBusinessTaskService.deleteDtBusinessTaskByIds(ids));
    }

    @PostMapping("/assgin")
    @ResponseBody
    public AjaxResult assgin(AssginReqDto assginReqDto){
        return toAjax(dtBusinessTaskService.assginDept(assginReqDto));
    }

    @GetMapping("/importData")
    public String importData(){
        return prefix + "/importDataPopup";
    }


    @RequestMapping("/upload/insert")
    @ResponseBody
    public AjaxResult insert(HttpServletRequest request, HttpServletResponse response
            , @RequestParam("file") MultipartFile[] file) throws Exception{

        return success();
    }

}
