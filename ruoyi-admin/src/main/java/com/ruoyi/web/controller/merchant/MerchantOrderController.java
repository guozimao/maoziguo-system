package com.ruoyi.web.controller.merchant;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.reponse.GroupOrderRespDto;
import com.ruoyi.groupcompany.domain.request.GroupOrderReqDto;
import com.ruoyi.merchant.domain.reponse.MerchantOrderRespDto;
import com.ruoyi.merchant.domain.request.MerchantOrderReqDto;
import com.ruoyi.merchant.service.IMerchantOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商家订单 信息操作处理
 *
 * @author zimao
 */
@Controller
@RequestMapping("/merchant/order")
public class MerchantOrderController extends BaseController {

    @Autowired
    private IMerchantOrderService merchantOrderService;

    private String prefix = "merchant/order";

    @RequiresPermissions("merchant:order:view")
    @GetMapping()
    public String order()
    {
        return prefix + "/order";
    }

    /**
     * 查询商业任务信息列表
     */
    @RequiresPermissions("merchant:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MerchantOrderReqDto merchantOrderReqDto)
    {
        startPage();
        merchantOrderReqDto.setMerchantUserId(ShiroUtils.getSysUser().getUserId());
        List<MerchantOrderRespDto> list = merchantOrderService.selectMerchantTaskDtoList(merchantOrderReqDto);
        return getDataTable(list);
    }

}
