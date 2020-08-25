package com.ruoyi.web.controller.promoters;

import java.util.List;

import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.promoters.application.domain.dto.request.ApplicationReqDto;
import com.ruoyi.promoters.application.domain.dto.response.ApplicationRespDto;
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
import com.ruoyi.promoters.application.domain.TeamAssociationApplication;
import com.ruoyi.promoters.application.service.ITeamAssociationApplicationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 团队关联申请Controller
 * 
 * @author ruoyi
 * @date 2020-08-23
 */
@Controller
@RequestMapping("/promoters/application")
public class TeamAssociationApplicationController extends BaseController
{
    private String prefix = "promoters/application";

    @Autowired
    private ITeamAssociationApplicationService teamAssociationApplicationService;

    @RequiresPermissions("promoters:application:view")
    @GetMapping()
    public String application()
    {
        return prefix + "/application";
    }

    /**
     * 查询团队关联申请列表
     */
    @RequiresPermissions("promoters:application:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ApplicationReqDto applicationReqDto)
    {
        startPage();
        List<ApplicationRespDto> list = teamAssociationApplicationService.selectTeamAssociationApplicationList(applicationReqDto,ShiroUtils.getUserId());
        return getDataTable(list);
    }

    /**
     * 导出团队关联申请列表
     */
    @RequiresPermissions("promoters:application:export")
    @Log(title = "团队关联申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TeamAssociationApplication teamAssociationApplication)
    {
        //List<TeamAssociationApplication> list = teamAssociationApplicationService.selectTeamAssociationApplicationList(teamAssociationApplication);
        //ExcelUtil<TeamAssociationApplication> util = new ExcelUtil<TeamAssociationApplication>(TeamAssociationApplication.class);
        //return util.exportExcel(list, "application");
        return null;
    }

    /**
     * 新增团队关联申请
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存团队关联申请
     */
    @RequiresPermissions("promoters:application:add")
    @Log(title = "团队关联申请", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TeamAssociationApplication teamAssociationApplication)
    {
        return toAjax(teamAssociationApplicationService.insertTeamAssociationApplication(teamAssociationApplication));
    }

    /**
     * 修改团队关联申请
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TeamAssociationApplication teamAssociationApplication = teamAssociationApplicationService.selectTeamAssociationApplicationById(id);
        mmap.put("teamAssociationApplication", teamAssociationApplication);
        return prefix + "/edit";
    }

    /**
     * 修改保存团队关联申请
     */
    @RequiresPermissions("promoters:application:edit")
    @Log(title = "团队关联申请", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TeamAssociationApplication teamAssociationApplication)
    {
        return toAjax(teamAssociationApplicationService.updateTeamAssociationApplication(teamAssociationApplication));
    }

    /**
     * 删除团队关联申请
     */
    @RequiresPermissions("promoters:application:remove")
    @Log(title = "团队关联申请", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(teamAssociationApplicationService.deleteTeamAssociationApplicationByIds(ids));
    }
}
