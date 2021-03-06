package com.ruoyi.web.controller.groupcompany;

import java.math.BigDecimal;
import java.util.*;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.GroupOrder;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskRespDto;
import com.ruoyi.groupcompany.domain.reponse.GroupOrderRespDto;
import com.ruoyi.groupcompany.domain.request.AssginReqDto;
import com.ruoyi.groupcompany.domain.request.DtGroupBusinessTaskReqDto;
import com.ruoyi.groupcompany.domain.request.SupplementOrderReqDto;
import com.ruoyi.groupcompany.service.IGroupOrderService;
import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.service.ISalesmanTaskService;
import com.ruoyi.salesmanleader.domain.request.AssginSalesmanReqDto;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.groupcompany.domain.DtBusinessTask;
import com.ruoyi.groupcompany.service.IDtBusinessTaskService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商业任务信息Controller
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Controller
@RequestMapping("/groupcompany/task")
public class DtBusinessTaskController extends BaseController
{
    private String prefix = "groupcompany/task";

    @Autowired
    private IDtBusinessTaskService dtBusinessTaskService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private IGroupOrderService groupOrderService;

    @Autowired
    private ISalesmanTaskService salesmanTaskService;

    @RequiresPermissions("groupcompany:task:view")
    @GetMapping()
    public String task()
    {
        return prefix + "/task";
    }

    /**
     * 查询商业任务信息列表
     */
    @RequiresPermissions("groupcompany:task:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DtGroupBusinessTaskReqDto DtGroupBusinessTaskReqDto)
    {
        startPage();
        List<DtGroupBusinessTaskRespDto> list = dtBusinessTaskService.selectGroupDtBusinessTaskDtoList(DtGroupBusinessTaskReqDto);
        return getDataTable(list);
    }

    /**
     * 导出商业任务信息列表
     */
    @RequiresPermissions("groupcompany:task:export")
    @Log(title = "商业任务信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DtBusinessTask dtBusinessTask)
    {
        List<DtBusinessTask> list = dtBusinessTaskService.selectDtBusinessTaskList(dtBusinessTask);
        ExcelUtil<DtBusinessTask> util = new ExcelUtil<DtBusinessTask>(DtBusinessTask.class);
        return util.exportExcel(list, "task");
    }

    /**
     * 新增商业任务信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存商业任务信息
     */
    @RequiresPermissions("groupcompany:task:add")
    @Log(title = "商业任务信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DtBusinessTask dtBusinessTask)
    {
        return toAjax(dtBusinessTaskService.insertDtBusinessTask(dtBusinessTask));
    }

    /**
     * 修改商业任务信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DtBusinessTask dtBusinessTask = dtBusinessTaskService.selectDtBusinessTaskById(id);
        mmap.put("dtBusinessTask", dtBusinessTask);
        return prefix + "/edit";
    }

    /**
     * 修改商业任务明细信息
     */
    @PostMapping("/detailEdit")
    @ResponseBody
    public AjaxResult detailEdit(DtBusinessTaskDetail dtBusinessTaskDetail)
    {
        if(groupOrderService.hasNoSamePlatformNickname4DB(dtBusinessTaskDetail)){
            //查复购
            salesmanTaskService.queryRepurchase(dtBusinessTaskDetail.getPlatformNickname());
        }
        return toAjax(dtBusinessTaskService.updateDtBusinessTaskDetail(dtBusinessTaskDetail));
    }

    /**
     * 修改保存商业任务信息
     */
    @RequiresPermissions("groupcompany:task:edit")
    @Log(title = "商业任务信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DtBusinessTask dtBusinessTask)
    {
        return toAjax(dtBusinessTaskService.updateDtBusinessTask(dtBusinessTask));
    }

    /**
     * 删除商业任务信息
     */
    @RequiresPermissions("groupcompany:task:remove")
    @Log(title = "商业任务信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(dtBusinessTaskService.deleteDtBusinessTaskByIds(ids));
    }

    @PostMapping("/assgin")
    @ResponseBody
    public AjaxResult assgin(AssginReqDto assginReqDto){
        return toAjax(dtBusinessTaskService.assginDept(assginReqDto));
    }

    @GetMapping("/importData")
    public String importData(){
        return prefix + "/importDataPopup";
    }


    @RequestMapping("/upload/insert")
    @ResponseBody
    public AjaxResult insert(@RequestParam("file") MultipartFile[] file) throws Exception{
        ExcelUtil<DtBusinessTaskDetail> util = new ExcelUtil<DtBusinessTaskDetail>(DtBusinessTaskDetail.class);
        List<List<DtBusinessTaskDetail>> list = new ArrayList<>();
        List<DtBusinessTaskDetail> vaildList = new LinkedList<>();
        for (MultipartFile f : file){
            List<DtBusinessTaskDetail> businessTaskList = util.importExcel(f.getInputStream());
            doProcessEmptyOrder4Excel(businessTaskList);
            list.add(businessTaskList);
            vaildList.addAll(businessTaskList);
        }
        String message = dtBusinessTaskService.batchInsertTask(list,vaildList);
        return AjaxResult.success(message);
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

    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<DtBusinessTaskDetail> util = new ExcelUtil<DtBusinessTaskDetail>(DtBusinessTaskDetail.class);
        return util.importTemplateExcel("任务明细数据");
    }

    /**
     * 查看任务信息明细
     */
    @GetMapping("/queryDetailPopup/{id}")
    public String queryDetail(@PathVariable("id") Long id, ModelMap mmap)
    {
        DtBusinessTask dtBusinessTask = dtBusinessTaskService.selectDtBusinessTaskById(id);

        SysDept sysDept = sysDeptService.selectDeptById(dtBusinessTask.getDeptId());
        if(StringUtils.isNotNull(sysDept)){
            mmap.put("deptName",sysDept.getDeptName());
        }
        mmap.put("dtBusinessTask", dtBusinessTask);
        return prefix + "/queryDetailPopup";
    }

    /**
     * 撤单
     */
    @PostMapping( "/withdraw")
    @ResponseBody
    public AjaxResult withdraw(Long id)
    {
        return toAjax(groupOrderService.withdraw(id));
    }

    /**
     * 查看任务信息明细
     */
    @GetMapping("/taskDetailByTaskId/{id}")
    @ResponseBody
    public TableDataInfo taskDetailByTaskId(@PathVariable("id") Long id)
    {
        startPage();
        List<DtBusinessTaskDetail> dtBusinessTaskDetails = dtBusinessTaskService.selectDtBusinessTaskDetailList(id);
        return getDataTable(dtBusinessTaskDetails);
    }

    /**
     * 查询今日的补单列表
     */
    @PostMapping("/supplementOrderList")
    @ResponseBody
    public TableDataInfo supplementOrderList(SupplementOrderReqDto reqDto)
    {
        startPage();
        List<GroupOrderRespDto> list = groupOrderService.selectGroupOrderRespDtoListWithSupplementOrder(reqDto);
        return getDataTable(list);
    }

    @GetMapping("/supplementOrderPopupList")
    public String supplementOrderPopupList()
    {
        return prefix + "/supplementOrderPopup";
    }

    @PostMapping("/supplementOrder")
    @ResponseBody
    public AjaxResult supplementOrder(SupplementOrderReqDto supplementOrderReqDto){
        return toAjax(groupOrderService.supplementOrder(supplementOrderReqDto));
    }

}
