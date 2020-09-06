package com.ruoyi.web.controller.salesmanleader;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.QueryParaConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.groupcompany.domain.DtBusinessTask;
import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskRespDto;
import com.ruoyi.groupcompany.domain.request.AssginReqDto;
import com.ruoyi.groupcompany.domain.request.DtGroupBusinessTaskReqDto;
import com.ruoyi.groupcompany.service.IDtBusinessTaskService;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderTask;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderTaskDetail;
import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderTaskRespDto;
import com.ruoyi.salesmanleader.domain.request.AssginSalesmanReqDto;
import com.ruoyi.salesmanleader.domain.request.SalesmanLeaderTaskReqDto;
import com.ruoyi.salesmanleader.service.ISalesmanLeaderTaskService;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 业务组长任务信息Controller
 * 
 * @author zimao
 * @date 2020-09-05
 */
@Controller
@RequestMapping("/salesmanLeader/todaytask")
public class SalesmanLeaderTodayTaskController extends BaseController
{
    private String prefix = "salesmanLeader/todaytask";

    @Autowired
    private ISalesmanLeaderTaskService salesmanLeaderTaskService;

    @Autowired
    private ISysDeptService sysDeptService;

    @RequiresPermissions("salesmanLeader:todaytask:view")
    @GetMapping()
    public String todaytask()
    {
        return prefix + "/task";
    }

    /**
     * 查询商业任务信息列表
     */
    @RequiresPermissions("salesmanLeader:todaytask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SalesmanLeaderTaskReqDto salesmanLeaderTaskReqDto)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        List<SalesmanLeaderTaskRespDto> list = salesmanLeaderTaskService.selectSalesmanLeaderTaskDtoList(salesmanLeaderTaskReqDto,user);
        return getDataTable(list);
    }

    /**
     * 导出商业任务信息列表
     */
    @RequiresPermissions("salesmanLeader:todaytask:export")
    @Log(title = "商业任务信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SalesmanLeaderTask salesmanLeaderTask)
    {
        List<SalesmanLeaderTask> list = salesmanLeaderTaskService.selectSalesmanLeaderTaskList(salesmanLeaderTask);
        ExcelUtil<SalesmanLeaderTask> util = new ExcelUtil<SalesmanLeaderTask>(SalesmanLeaderTask.class);
        return util.exportExcel(list, "todaytask");
    }

    @PostMapping("/assgin")
    @ResponseBody
    public AjaxResult assgin(AssginSalesmanReqDto assginReqDto){
        return toAjax(salesmanLeaderTaskService.assginSalesman(assginReqDto));
    }

    private void doProcessEmptyOrder4Excel(List<DtBusinessTaskDetail> businessTaskList) {
        Iterator<DtBusinessTaskDetail> dtBusinessTaskDetailIterator = businessTaskList.iterator();
        while (dtBusinessTaskDetailIterator.hasNext()){
            DtBusinessTaskDetail detail = dtBusinessTaskDetailIterator.next();
            if(StringUtils.isEmpty(detail.getTaskNo())){
                dtBusinessTaskDetailIterator.remove();
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
        return toAjax(salesmanLeaderTaskService.updateSalesmanLeaderTaskDetail(salesmanLeaderTaskDetail));
    }

    /**
     * 查看任务信息明细
     */
    @GetMapping("/queryDetailPopup/{id}")
    public String queryDetail(@PathVariable("id") Long id, ModelMap mmap)
    {
        BigDecimal totalPrincipal = new BigDecimal(0);
        BigDecimal actualTotalPrincipal = new BigDecimal(0);
        SalesmanLeaderTask salesmanLeaderTask = salesmanLeaderTaskService.selectSalesmanLeaderTaskById(id);
        List<SalesmanLeaderTaskDetail> salesmanLeaderTaskDetails = salesmanLeaderTaskService.selectSalesmanLeaderTaskDetailList(id);

        for(SalesmanLeaderTaskDetail detail: salesmanLeaderTaskDetails){
            totalPrincipal = totalPrincipal.add(detail.getUnitPrice());
            actualTotalPrincipal = actualTotalPrincipal.add(detail.getPromotersModifyUnitPrice());
        }
        SysDept sysDept = sysDeptService.selectDeptById(salesmanLeaderTask.getDeptId());
        if(StringUtils.isNotNull(sysDept)){
            mmap.put("deptName",sysDept.getDeptName());
        }
        mmap.put("dtBusinessTask", salesmanLeaderTask);
        mmap.put("dtBusinessTaskDetail", salesmanLeaderTaskDetails);
        mmap.put("orderNumber",salesmanLeaderTaskDetails.size());
        mmap.put("totalPrincipal", totalPrincipal);
        mmap.put("actualTotalPrincipal", actualTotalPrincipal);
        return prefix + "/queryDetailPopup";
    }

    @GetMapping("/salesmanlist")
    public String salesmanlist()
    {
        return prefix + "/salesmanPopup";
    }
}
