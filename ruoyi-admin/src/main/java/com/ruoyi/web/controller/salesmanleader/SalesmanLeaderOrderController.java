package com.ruoyi.web.controller.salesmanleader;

import com.ruoyi.common.constant.QueryParaConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderTaskDetail;
import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderOrderRespDto;
import com.ruoyi.salesmanleader.domain.request.SalesmanLeaderOrderReqDto;
import com.ruoyi.salesmanleader.service.ISalesmanLeaderOrderService;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 业务组长订单 信息操作处理
 *
 * @author zimao
 */
@Controller
@RequestMapping("/salesmanleader/order")
public class SalesmanLeaderOrderController extends BaseController {

    @Autowired
    private ISalesmanLeaderOrderService salesmanLeaderOrderService;

    private String prefix = "salesmanleader/order";

    @RequiresPermissions("salesmanleader:order:view")
    @GetMapping()
    public String order()
    {
        return prefix + "/order";
    }

    /**
     * 查询商业任务信息列表
     */
    @RequiresPermissions("salesmanleader:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SalesmanLeaderOrderReqDto salesmanLeaderOrderReqDto)
    {
        doProcessParam(salesmanLeaderOrderReqDto);
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        List<SalesmanLeaderOrderRespDto> list = salesmanLeaderOrderService.selectSalesmanLeaderTaskDtoList(salesmanLeaderOrderReqDto,user);
        return getDataTable(list);
    }

    private void doProcessParam(SalesmanLeaderOrderReqDto salesmanLeaderOrderReqDto) {
        if(StringUtils.isNotEmpty(salesmanLeaderOrderReqDto.getSalesmanUserName())){
            SysUser user = salesmanLeaderOrderService.getUserBySalesManUserName(salesmanLeaderOrderReqDto.getSalesmanUserName());
            if(StringUtils.isNotNull(user)){
                salesmanLeaderOrderReqDto.setSalesmanUserId(user.getUserId());
            }else {
                salesmanLeaderOrderReqDto.setSalesmanUserId(QueryParaConstants.PARAM_NULL);
            }
        }
    }

    /**
     * 修改商业任务明细信息
     */
    @PostMapping("/detailEdit")
    @ResponseBody
    public AjaxResult detailEdit(SalesmanLeaderTaskDetail salesmanLeaderTaskDetail)
    {
        return toAjax(salesmanLeaderOrderService.updateDtsalesmanLeaderTaskDetail(salesmanLeaderTaskDetail));
    }

    /**
     * 停掉商业订单
     */
   /* @PostMapping("/stopOrder")
    @ResponseBody
    public AjaxResult stopOrder(String ids)
    {
        return toAjax(salesmanLeaderOrderService.stopOrder(ids));
    }*/

    /**
     * 修复停掉的商业订单
     */
    /*@PostMapping("/recoverOrder")
    @ResponseBody
    public AjaxResult recoverOrder(String ids)
    {
        return toAjax(salesmanLeaderOrderService.recoverOrder(ids));
    }*/

    /**
     * 撤掉商业订单
     */
    /*@PostMapping("/retreatOrder")
    @ResponseBody
    public AjaxResult retreatOrder(String ids)
    {
        return toAjax(salesmanLeaderOrderService.retreatOrder(Convert.toLongArray(ids)));
    }*/
}
