package com.ruoyi.web.controller.groupcompany;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.reponse.GroupOrderRespDto;
import com.ruoyi.groupcompany.domain.request.GroupOrderReqDto;
import com.ruoyi.groupcompany.service.IGroupOrderService;
import com.ruoyi.merchant.domain.MerchantOrder;
import com.ruoyi.salesman.domain.reponse.CommitOrder;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderOrder;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

/**
 * 集团公司订单 信息操作处理
 *
 * @author zimao
 */
@Controller
@RequestMapping("/groupcompany/order")
public class GroupOrderController extends BaseController {

    @Autowired
    private IGroupOrderService groupOrderService;

    private String prefix = "groupcompany/order";

    @RequiresPermissions("groupcompany:order:view")
    @GetMapping()
    public String order()
    {
        return prefix + "/order";
    }

    /**
     * 查询商业任务信息列表
     */
    @RequiresPermissions("groupcompany:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GroupOrderReqDto groupOrderReqDto)
    {
        groupOrderService.doProcessReqParam(groupOrderReqDto);
        startPage();
        List<GroupOrderRespDto> list = groupOrderService.selectGroupDtBusinessTaskDtoList(groupOrderReqDto);
        return getDataTable(list);
    }

    /**
     * 修改商业任务明细信息
     */
    @PostMapping("/detailEdit")
    @ResponseBody
    public AjaxResult detailEdit(DtBusinessTaskDetail dtBusinessTaskDetail)
    {
        return toAjax(groupOrderService.updateDtBusinessTaskDetail(dtBusinessTaskDetail));
    }

    /**
     * 停掉商业订单
     */
    @PostMapping("/stopOrder")
    @ResponseBody
    public AjaxResult stopOrder(String ids)
    {
        return toAjax(groupOrderService.stopOrder(ids));
    }

    /**
     * 修复停掉的商业订单
     */
    @PostMapping("/recoverOrder")
    @ResponseBody
    public AjaxResult recoverOrder(String ids)
    {
        return toAjax(groupOrderService.recoverOrder(ids));
    }


    @Log(title = "集团导出", businessType = BusinessType.EXPORT)
    @RequiresPermissions("groupcompany:order:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GroupOrderReqDto groupOrderReqDto)
    {
        groupOrderService.doProcessReqParam(groupOrderReqDto);
        if(groupOrderReqDto.getExportType().equals("03")){
            List<MerchantOrder> list = groupOrderService.selectMerchantOrder(groupOrderReqDto);
            ExcelUtil<MerchantOrder> util = new ExcelUtil<MerchantOrder>(MerchantOrder.class);
            return util.exportExcel(list, "商家订单数据");
        }else if(groupOrderReqDto.getExportType().equals("01")){
            List<SalesmanLeaderOrder> list = groupOrderService.selectSalesmanLedaderOrder(groupOrderReqDto);
            ExcelUtil<SalesmanLeaderOrder> util = new ExcelUtil<SalesmanLeaderOrder>(SalesmanLeaderOrder.class);
            return util.exportExcel(list, "地推员订单数据");
        }
        return error();
    }

    /**
     * 订单提交信息
     */
    @PostMapping("/editPicture")
    @ResponseBody
    public AjaxResult editPicture(@RequestParam("fileinput") MultipartFile[] file, MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        if(StringUtils.isEmpty(multipartHttpServletRequest.getParameter("id"))){
            throw new BusinessException("id不为空");
        }
        Long id = Long.valueOf(multipartHttpServletRequest.getParameter("id"));
        URL url = OssClientUtils.picOSS(file[0].getBytes());
        if(StringUtils.isNull(url)){
            return error("上传图片失败");
        }
        return toAjax(groupOrderService.editPicture(id,OssClientUtils.URL2OssParam(url)));
    }
}
