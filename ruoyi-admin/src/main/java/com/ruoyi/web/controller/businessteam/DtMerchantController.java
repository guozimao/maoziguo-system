package com.ruoyi.web.controller.businessteam;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.businessteam.domain.MerchantShopRelation;
import com.ruoyi.common.constant.QueryParaConstants;
import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.system.domain.DtMerchant;
import com.ruoyi.system.service.IDtMerchantService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商家信息Controller
 * 
 * @author zimao
 * @date 2020-09-01
 */
@Controller
@RequestMapping("/businessteam/merchant")
public class DtMerchantController extends BaseController
{
    private String prefix = "businessteam/merchant";

    @Autowired
    private IDtMerchantService dtMerchantService;

    @RequiresPermissions("businessteam:merchant:view")
    @GetMapping()
    public String merchant()
    {
        return prefix + "/merchant";
    }

    /**
     * 查询商家信息列表
     */
    @RequiresPermissions("businessteam:merchant:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DtMerchant dtMerchant)
    {
        doProcessParam(dtMerchant);
        startPage();
        List<DtMerchant> list = dtMerchantService.selectDtMerchantList(dtMerchant);
        return getDataTable(list);
    }

    private void doProcessParam(DtMerchant dtMerchant) {
        if(StringUtils.isNotEmpty(dtMerchant.getUserName())){
            SysUser user = dtMerchantService.getUserIdByName(dtMerchant.getUserName());
            if(StringUtils.isNotNull(user)){
                dtMerchant.setUserId(user.getUserId());
            }else {
                dtMerchant.setUserId(QueryParaConstants.PARAM_NULL);
            }
        }
        if(StringUtils.isNotEmpty(dtMerchant.getShopName())){
            MerchantShopRelation relation = dtMerchantService.getMerchantUserIdByShopName(dtMerchant.getShopName());
            if(StringUtils.isNotNull(relation)){
                dtMerchant.setUserId(relation.getMerchantUserId());
            }else {
                dtMerchant.setUserId(QueryParaConstants.PARAM_NULL);
            }
        }
    }

    /**
     * 导出商家信息列表
     */
    @RequiresPermissions("businessteam:merchant:export")
    @Log(title = "商家信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DtMerchant dtMerchant)
    {
        List<DtMerchant> list = dtMerchantService.selectDtMerchantList(dtMerchant);
        ExcelUtil<DtMerchant> util = new ExcelUtil<DtMerchant>(DtMerchant.class);
        return util.exportExcel(list, "merchant");
    }

    /**
     * 新增商家信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存商家信息
     */
    @RequiresPermissions("businessteam:merchant:add")
    @Log(title = "商家信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DtMerchant dtMerchant)
    {
        return toAjax(dtMerchantService.insertDtMerchant(dtMerchant));
    }

    /**
     * 修改商家信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DtMerchant dtMerchant = dtMerchantService.selectDtMerchantById(id);
        mmap.put("dtMerchant", dtMerchant);
        List<MerchantShopRelation> merchantShopRelations = dtMerchantService.getMerchantShopRelationByUserId(dtMerchant.getUserId());
        mmap.put("association",merchantShopRelations);
        return prefix + "/edit";
    }

    /**
     * 修改保存商家信息
     */
    @RequiresPermissions("businessteam:merchant:edit")
    @Log(title = "商家信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DtMerchant dtMerchant)
    {
        return toAjax(dtMerchantService.updateDtMerchant(dtMerchant));
    }

    /**
     * 删除商家信息
     */
    @RequiresPermissions("businessteam:merchant:remove")
    @Log(title = "商家信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(dtMerchantService.deleteDtMerchantByIds(ids));
    }
}
