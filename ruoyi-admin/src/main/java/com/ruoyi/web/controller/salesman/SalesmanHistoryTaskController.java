package com.ruoyi.web.controller.salesman;


import com.ruoyi.common.core.controller.BaseController;

import com.ruoyi.common.core.page.TableDataInfo;

import com.ruoyi.common.utils.StringUtils;

import com.ruoyi.framework.util.ShiroUtils;

import com.ruoyi.salesman.domain.SalesmanTask;
import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.SalesmanTaskRespDto;
import com.ruoyi.salesman.domain.request.SalesmanTaskReqDto;
import com.ruoyi.salesman.service.ISalesmanTaskService;

import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;

import java.util.*;

/**
 * 商业任务信息Controller
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Controller
@RequestMapping("/salesman/historytask")
public class SalesmanHistoryTaskController extends BaseController
{
    private String prefix = "salesman/historytask";

    @Autowired
    private ISalesmanTaskService salesmanTaskService;

    @Autowired
    private ISysDeptService sysDeptService;

    @RequiresPermissions("salesman:historytask:view")
    @GetMapping()
    public String task()
    {
        return prefix + "/task";
    }

    /**
     * 查询商业任务信息列表
     */
    @RequiresPermissions("salesman:historytask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SalesmanTaskReqDto salesmanTaskReqDto)
    {
        SysUser user = ShiroUtils.getSysUser();
        Set<Long> ids = salesmanTaskService.selectTaskIdsBySalesmanUserIdAndDate(user.getUserId(),salesmanTaskReqDto.getRequiredCompletionDate());
        startPage();
        List<SalesmanTaskRespDto> list = salesmanTaskService.selectSalesmanTaskDtoList(salesmanTaskReqDto,user,ids);
        return getDataTable(list);
    }


    /**
     * 查看任务信息明细
     */
    @GetMapping("/queryDetailPopup/{id}")
    public String queryDetail(@PathVariable("id") Long id, ModelMap mmap)
    {
        BigDecimal totalPrincipal = new BigDecimal(0);
        BigDecimal actualTotalPrincipal = new BigDecimal(0);
        SalesmanTask salesmanTask = salesmanTaskService.selectSalesmanTaskById(id);
        List<SalesmanTaskDetail> salesmanTaskDetails = salesmanTaskService.selectSalesmanTaskDetailList(id);
        SysDept sysDept = sysDeptService.selectDeptById(salesmanTask.getDeptId());
        if(StringUtils.isNotNull(sysDept)){
            mmap.put("deptName",sysDept.getDeptName());
        }
        for(SalesmanTaskDetail detail: salesmanTaskDetails){
            totalPrincipal = totalPrincipal.add(detail.getUnitPrice());
            actualTotalPrincipal = actualTotalPrincipal.add(detail.getPromotersModifyUnitPrice());
        }
        mmap.put("salesmanTask", salesmanTask);
        mmap.put("salesmanTaskDetail", salesmanTaskDetails);
        mmap.put("orderNumber",salesmanTaskDetails.size());
        mmap.put("totalPrincipal", totalPrincipal);
        mmap.put("actualTotalPrincipal", actualTotalPrincipal);
        return prefix + "/queryDetailPopup";
    }

}
