package com.ruoyi.web.controller.groupcompany;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskRespDto;
import com.ruoyi.groupcompany.domain.request.DtGroupBusinessTaskReqDto;
import com.ruoyi.groupcompany.service.IDtBusinessTaskService;
import com.ruoyi.groupcompany.service.IGroupOrderService;
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
    @RequiresPermissions("groupcompany:task:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DtBusinessTaskDetail dtBusinessTaskDetail)
    {
        startPage();
        List<DtBusinessTaskDetail> list = groupOrderService.selectGroupDtBusinessTaskDtoList(dtBusinessTaskDetail);
        return getDataTable(list);
    }
}
