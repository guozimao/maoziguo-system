package com.ruoyi.web.controller.businessteam;

import java.util.List;

import com.ruoyi.businessteam.domain.dto.request.SalesManReqDto;
import com.ruoyi.businessteam.domain.dto.response.SalesManRespDto;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
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
    public TableDataInfo list(SalesManReqDto salesManReqDto)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        hasNoPermission(user);
        List<SalesManRespDto> list = dtSalesmanService.selectDtSalesmanList(salesManReqDto,user.getDept().getLeaderId(),user.getDeptId());
        return getDataTable(list);
    }

    private void hasNoPermission(SysUser user) {
        if(StringUtils.isNull(user.getDept())){
            error("本用户还没设置团队");
        }else if(StringUtils.isNull(user.getDept().getLeaderId())){
            error("团队里还没有设置负责人");
        }else if(!user.getUserId().equals(user.getDept().getLeaderId())){
            error("对不起，没权限，您不是团队里的负责人");
        }
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
       /* List<DtSalesman> list = dtSalesmanService.selectDtSalesmanList(dtSalesman);
        ExcelUtil<DtSalesman> util = new ExcelUtil<DtSalesman>(DtSalesman.class);
        return util.exportExcel(list, "salesman");*/
        return null;
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
        SalesManRespDto dtSalesman = dtSalesmanService.selectSalesManReqDtoById(id);
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
    public AjaxResult editSave(SalesManReqDto salesManReqDto)
    {
        return toAjax(dtSalesmanService.updateDtSalesmanReq(salesManReqDto));
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
        return toAjax(dtSalesmanService.deleteDtSalesmanDeptByIds(ids));
    }

    /**
     * 申请关联
     */
    @GetMapping("/application")
    public String application()
    {
        return prefix + "/applicationPopup";
    }

    /**
     * 申请关联
     */
    @GetMapping("/checkVail4Id")
    @ResponseBody
    public AjaxResult checkVail4Id(Long id)
    {
        SysUser sysUser = ShiroUtils.getSysUser();
        if(StringUtils.isNull(sysUser.getDeptId())) {
            return error("当前账号没有设置团队，是无法成功申请关联");
        }else if(StringUtils.isNull(sysUser.getDept().getLeaderId())){
            return error("当前账号没有设置团队负责人，是无法成功申请关联");
        }else if(!sysUser.getDept().getLeaderId().equals(sysUser.getUserId())){
            return error("当前账号不是团队负责人，是无法成功申请关联");
        }else if(dtSalesmanService.hasNoSalesMan(id)){
            return error("不存在该业务员，是无法成功申请关联");
        }else if(dtSalesmanService.hasNoNormalStatus(id)){
            return error("该业务员已被停用，是无法成功申请关联");
        } else if(dtSalesmanService.hasAssociationWithTeam(id,sysUser.getDeptId())){
            return error("该业务员已经是其它团队的，是无法成功申请关联");
        }else{
            return success("该业务员可以申请关联");
        }
    }
}
