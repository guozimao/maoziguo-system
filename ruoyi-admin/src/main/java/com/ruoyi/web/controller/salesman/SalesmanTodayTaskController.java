package com.ruoyi.web.controller.salesman;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.salesman.domain.SalesmanTask;
import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.SalesmanTaskRespDto;
import com.ruoyi.salesman.domain.request.SalesmanTaskReqDto;
import com.ruoyi.salesman.service.ISalesmanTaskService;
import com.ruoyi.salesmanleader.domain.request.SalesmanLeaderTaskReqDto;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 商业任务信息Controller
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Controller
@RequestMapping("/salesman/todaytask")
public class SalesmanTodayTaskController extends BaseController
{
    private String prefix = "salesman/todaytask";

    @Autowired
    private ISalesmanTaskService salesmanTaskService;

    @Autowired
    private ISysDeptService sysDeptService;

    @RequiresPermissions("salesman:todaytask:view")
    @GetMapping()
    public String todaytask()
    {
        return prefix + "/task";
    }

    /**
     * 查询商业任务信息列表
     */
    @RequiresPermissions("salesman:todaytask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SalesmanTaskReqDto salesmanTaskReqDto)
    {
        SysUser user = ShiroUtils.getSysUser();
        Set<Long> ids = salesmanTaskService.selectTaskIdsBySalesmanUserId(user.getUserId());
        setTodayDate(salesmanTaskReqDto);
        startPage();
        List<SalesmanTaskRespDto> list = salesmanTaskService.selectSalesmanTaskDtoList(salesmanTaskReqDto,user,ids);
        return getDataTable(list);
    }

    /**
     * 修改商业任务信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SalesmanTask salesmanTask = salesmanTaskService.selectSalesmanTaskById(id);
        mmap.put("dtBusinessTask", salesmanTask);
        return prefix + "/edit";
    }

    /**
     * 修改商业任务明细信息
     */
    @PostMapping("/detailEdit")
    @ResponseBody
    public AjaxResult detailEdit(SalesmanTaskDetail salesmanTaskDetail)
    {
        return toAjax(salesmanTaskService.updateSalesmanTaskDetail(salesmanTaskDetail));
    }

    /**
     * 修改保存商业任务信息
     */
    @RequiresPermissions("salesman:todaytask:edit")
    @Log(title = "商业任务信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SalesmanTask salesmanTask)
    {
        return toAjax(salesmanTaskService.updateSalesmanTask(salesmanTask));
    }

    /**
     * 查看任务信息明细
     */
    @GetMapping("/queryDetailPopup/{id}")
    public String queryDetail(@PathVariable("id") Long id, ModelMap mmap)
    {
        SalesmanTask salesmanTask = salesmanTaskService.selectSalesmanTaskById(id);
        List<SalesmanTaskDetail> salesmanTaskDetails = salesmanTaskService.selectSalesmanTaskDetailList(id);

        SysDept sysDept = sysDeptService.selectDeptById(salesmanTask.getDeptId());
        if(StringUtils.isNotNull(sysDept)){
            mmap.put("deptName",sysDept.getDeptName());
        }
        mmap.put("salesmanTask", salesmanTask);
        mmap.put("salesmanTaskDetail", salesmanTaskDetails);
        mmap.put("orderNumber",salesmanTaskDetails.size());
        return prefix + "/queryDetailPopup";
    }

    private void setTodayDate(SalesmanTaskReqDto salesmanTaskReqDto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            salesmanTaskReqDto.setRequiredCompletionDate(sdf.parse(sdf.format(new Date())));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

}
