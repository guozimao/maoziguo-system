package com.ruoyi.web.controller.salesman;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.reponse.GroupOrderRespDto;
import com.ruoyi.groupcompany.domain.request.GroupOrderReqDto;
import com.ruoyi.salesman.domain.SalesmanTask;
import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.SalesmanOrderRespDto;
import com.ruoyi.salesman.domain.request.SalesmanOrderReqDto;
import com.ruoyi.salesman.service.ISalesmanOrderService;
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
 * 集团公司订单 信息操作处理
 *
 * @author zimao
 */
@Controller
@RequestMapping("/salesman/order")
public class SalesmanOrderController extends BaseController {

    @Autowired
    private ISalesmanOrderService salesmanOrderService;

    private String prefix = "salesman/order";

    @RequiresPermissions("salesman:order:view")
    @GetMapping()
    public String order()
    {
        return prefix + "/order";
    }

    /**
     * 查询商业任务信息列表
     */
    @RequiresPermissions("salesman:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SalesmanOrderReqDto salesmanOrderReqDto)
    {
        startPage();
        salesmanOrderReqDto.setSalesmanUserId(ShiroUtils.getSysUser().getUserId());
        List<SalesmanOrderRespDto> list = salesmanOrderService.selectSalesmanTaskDtoList(salesmanOrderReqDto);
        return getDataTable(list);
    }

}
