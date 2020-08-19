package com.ruoyi.web.controller.Repurchase;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.repurchase.domain.MemberConsumptionTrack;
import com.ruoyi.repurchase.service.IRepurchaseIndexService;
import com.ruoyi.repurchase.domain.dto.request.MemberConsumptionTrackRequest;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 复购 信息操作处理
 *
 * @author zimao
 */
@Controller
@RequestMapping("/repurchase/index")
public class RepurchaseIndexController extends BaseController {
    private String prefix = "repurchase/index";

    @Autowired
    private IRepurchaseIndexService repurchaseIndexService;

    //@RequiresPermissions("repurchase:index:view")
    @GetMapping()
    public String repurchase()
    {
        return prefix + "/repurchase";
    }

    /**
     * 查询复购列表
     */
    @RequiresPermissions("repurchase:index:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MemberConsumptionTrackRequest memberConsumptionTrackReq)
    {
        startPage();
        List<MemberConsumptionTrack> list = repurchaseIndexService.selectList(memberConsumptionTrackReq);
        return getDataTable(list);
    }

    /**
     * 复购数据导入
     */
    @Log(title = "复购管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("repurchase:index:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<MemberConsumptionTrack> util = new ExcelUtil<MemberConsumptionTrack>(MemberConsumptionTrack.class);
        List<MemberConsumptionTrack> consumptionList = util.importExcel(file.getInputStream());
        String message = repurchaseIndexService.importConsumption(consumptionList, updateSupport);
        return AjaxResult.success(message);
    }

}
