package com.ruoyi.web.controller.businessteam;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.businessteam.domain.DtSalesman;
import com.ruoyi.businessteam.service.IDtSalesmanService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 业务人员信息Controller
 * 
 * @author ruoyi
 * @date 2020-08-22
 */
@Controller
@RequestMapping("/businessteam/salesman")
public class DtSalesmanController extends BaseController
{
    private String prefix = "businessteam/salesman";

    @Autowired
    private IDtSalesmanService dtSalesmanService;

    @RequiresPermissions("system:salesman:view")
    @GetMapping()
    public String salesman()
    {
        return prefix + "/salesman";
    }

    /**
     * 查询业务人员信息列表
     */
    @RequiresPermissions("businessteam:salesman:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DtSalesman dtSalesman)
    {
        startPage();
        List<DtSalesman> list = dtSalesmanService.selectDtSalesmanList(dtSalesman);
        return getDataTable(list);
    }

    /**
     * 导出业务人员信息列表
     */
    @RequiresPermissions("businessteam:salesman:export")
    @Log(title = "业务人员信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DtSalesman dtSalesman)
    {
        List<DtSalesman> list = dtSalesmanService.selectDtSalesmanList(dtSalesman);
        ExcelUtil<DtSalesman> util = new ExcelUtil<DtSalesman>(DtSalesman.class);
        return util.exportExcel(list, "salesman");
    }

    /**
     * 新增业务人员信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存业务人员信息
     */
    @RequiresPermissions("businessteam:salesman:add")
    @Log(title = "业务人员信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DtSalesman dtSalesman)
    {
        return toAjax(dtSalesmanService.insertDtSalesman(dtSalesman));
    }

    /**
     * 修改业务人员信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DtSalesman dtSalesman = dtSalesmanService.selectDtSalesmanById(id);
        mmap.put("dtSalesman", dtSalesman);
        return prefix + "/edit";
    }

    /**
     * 修改保存业务人员信息
     */
    @RequiresPermissions("businessteam:salesman:edit")
    @Log(title = "业务人员信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DtSalesman dtSalesman)
    {
        return toAjax(dtSalesmanService.updateDtSalesman(dtSalesman));
    }

    /**
     * 删除业务人员信息
     */
    @RequiresPermissions("businessteam:salesman:remove")
    @Log(title = "业务人员信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(dtSalesmanService.deleteDtSalesmanByIds(ids));
    }
}
