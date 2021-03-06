package com.ruoyi.web.controller.salesman;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.salesman.domain.SalesmanTask;
import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.CommitOrder;
import com.ruoyi.salesman.domain.reponse.CommitTask;
import com.ruoyi.salesman.domain.reponse.SalesmanTaskRespDto;
import com.ruoyi.salesman.domain.request.SalesmanTaskReqDto;
import com.ruoyi.salesman.domain.request.validateNicknameReqDto;
import com.ruoyi.salesman.service.ISalesmanTaskService;

import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 商业任务信息Controller
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Controller
@RequestMapping("/salesman/todaytask")
public class SalesmanTodayTaskController extends BaseController
{
    private String prefix = "salesman/todaytask";

    @Autowired
    private ISalesmanTaskService salesmanTaskService;

    @Autowired
    private ISysDeptService sysDeptService;

    @RequiresPermissions("salesman:todaytask:view")
    @GetMapping()
    public String todaytask()
    {
        return prefix + "/task";
    }

    /**
     * 查询商业任务信息列表
     */
    @RequiresPermissions("salesman:todaytask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SalesmanTaskReqDto salesmanTaskReqDto)
    {
        SysUser user = ShiroUtils.getSysUser();
        setTodayDate(salesmanTaskReqDto);
        Set<Long> ids = salesmanTaskService.selectTaskIdsBySalesmanUserIdAndDate(user.getUserId(),salesmanTaskReqDto.getRequiredCompletionDate());
        startPage();
        List<SalesmanTaskRespDto> list = salesmanTaskService.selectSalesmanTaskDtoList(salesmanTaskReqDto,user,ids);
        return getDataTable(list);
    }

    /**
     * 修改商业任务信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SalesmanTask salesmanTask = salesmanTaskService.selectSalesmanTaskById(id);
        mmap.put("dtBusinessTask", salesmanTask);
        return prefix + "/edit";
    }

    /**
     * 修改商业任务明细信息
     */
    @PostMapping("/detailEdit")
    @ResponseBody
    public AjaxResult detailEdit(SalesmanTaskDetail salesmanTaskDetail)
    {
        return toAjax(salesmanTaskService.updateSalesmanTaskDetail(salesmanTaskDetail));
    }

    /**
     * 验证昵称
     */
    @PostMapping("/validateNickName")
    @ResponseBody
    public AjaxResult validateNickName(validateNicknameReqDto dto)
    {
        salesmanTaskService.queryRepurchase(dto.getPlatformNickname());
        return toAjax(salesmanTaskService.nicknameVerification2PassAndSet(dto.getId(),dto.getPlatformNickname()));
    }

    /**
     * 修改保存商业任务信息
     */
    @RequiresPermissions("salesman:todaytask:edit")
    @Log(title = "商业任务信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SalesmanTask salesmanTask)
    {
        return toAjax(salesmanTaskService.updateSalesmanTask(salesmanTask));
    }

    /**
     * 查看任务信息明细
     */
    @GetMapping("/queryDetailPopup/{id}")
    public String queryDetail(@PathVariable("id") Long id, ModelMap mmap)
    {
        SalesmanTask salesmanTask = salesmanTaskService.selectSalesmanTaskById(id);
        SysDept sysDept = sysDeptService.selectDeptById(salesmanTask.getDeptId());
        if(StringUtils.isNotNull(sysDept)){
            mmap.put("deptName",sysDept.getDeptName());
        }
        mmap.put("salesmanTask", salesmanTask);
        return prefix + "/queryDetailPopup";
    }

    /**
     * 查看任务信息明细
     */
    @GetMapping("/todaytaskDetailByTaskId/{id}")
    @ResponseBody
    public TableDataInfo todaytaskDetailByTaskId(@PathVariable("id") Long id)
    {
        startPage();
        List<SalesmanTaskDetail> salesmanTaskDetails = salesmanTaskService.selectSalesmanTaskDetailList(id);
        return getDataTable(salesmanTaskDetails);
    }

    private void setTodayDate(SalesmanTaskReqDto salesmanTaskReqDto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            salesmanTaskReqDto.setRequiredCompletionDate(sdf.parse(sdf.format(new Date())));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 订单提交信息
     */
    @PostMapping("/commitOrder")
    @ResponseBody
    public AjaxResult commitOrder(@RequestParam("fileinput") MultipartFile[] file, MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        CommitOrder commitOrder = new CommitOrder();
        commitOrder.setId(Long.valueOf(multipartHttpServletRequest.getParameter("id")));
        commitOrder.setPromotersUnitPriceRemark(multipartHttpServletRequest.getParameter("promotersUnitPriceRemark"));
        commitOrder.setPromotersModifyUnitPrice(new BigDecimal(multipartHttpServletRequest.getParameter("promotersModifyUnitPrice")));
        commitOrder.setCommitOrderPicBase64(multipartHttpServletRequest.getParameter("commitOrderPicBase64"));
        vaildateParam4CommitOrder(commitOrder);
        URL url = OssClientUtils.picOSS(OssClientUtils.convertBase642Bytes(commitOrder.getCommitOrderPicBase64()));
        if(StringUtils.isNull(url)){
            return error("上传图片失败");
        }
        commitOrder.setImg(OssClientUtils.URL2OssParam(url));
        return toAjax(salesmanTaskService.commitOrder(commitOrder));
    }

    private void vaildateParam4CommitOrder(CommitOrder commitOrder) {
        if(StringUtils.isEmpty(commitOrder.getCommitOrderPicBase64())){
            throw new BusinessException("必须上传图片");
        }
        if(StringUtils.isNull(commitOrder.getPromotersModifyUnitPrice())){
            throw new BusinessException("必须输入价格");
        }
        salesmanTaskService.checkoutStatus(commitOrder.getId());
    }

    /**
     * 修改保存商业任务信息
     */
    @PostMapping("/commitTask")
    @ResponseBody
    public AjaxResult commitTask(@RequestParam("taskfileinput") MultipartFile[] file, MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        CommitTask commitTask = new CommitTask();
        commitTask.setId(Long.valueOf(multipartHttpServletRequest.getParameter("id")));
        commitTask.setRemark(multipartHttpServletRequest.getParameter("remark"));
        commitTask.setCommitTaskPic1Base64(multipartHttpServletRequest.getParameter("commitTaskPic1Base64"));
        commitTask.setCommitTaskPic2Base64(multipartHttpServletRequest.getParameter("commitTaskPic2Base64"));
        commitTask.setCommitTaskPic3Base64(multipartHttpServletRequest.getParameter("commitTaskPic3Base64"));
        commitTask.setCommitTaskPic4Base64(multipartHttpServletRequest.getParameter("commitTaskPic4Base64"));
        commitTask.setCommitTaskPic5Base64(multipartHttpServletRequest.getParameter("commitTaskPic5Base64"));
        vaildateCommitTask(commitTask);
        Integer pictureLength = getPictureLength4CommitTask(commitTask);
        for(int i=1 ; i < pictureLength + 1;i++){
            URL url = null;
            if(i == 1){
                url = OssClientUtils.picOSS(OssClientUtils.convertBase642Bytes(commitTask.getCommitTaskPic1Base64()));
                commitTask.setFeedbackPictureUrl1(OssClientUtils.URL2OssParam(url));
            }else if(i == 2){
                url = OssClientUtils.picOSS(OssClientUtils.convertBase642Bytes(commitTask.getCommitTaskPic2Base64()));
                commitTask.setFeedbackPictureUrl2(OssClientUtils.URL2OssParam(url));
            }else if(i == 3){
                url = OssClientUtils.picOSS(OssClientUtils.convertBase642Bytes(commitTask.getCommitTaskPic3Base64()));
                commitTask.setFeedbackPictureUrl3(OssClientUtils.URL2OssParam(url));
            }else if(i == 4){
                url = OssClientUtils.picOSS(OssClientUtils.convertBase642Bytes(commitTask.getCommitTaskPic4Base64()));
                commitTask.setFeedbackPictureUrl4(OssClientUtils.URL2OssParam(url));
            }else if(i == 5){
                url = OssClientUtils.picOSS(OssClientUtils.convertBase642Bytes(commitTask.getCommitTaskPic5Base64()));
                commitTask.setFeedbackPictureUrl5(OssClientUtils.URL2OssParam(url));
            }
        }
        return toAjax(salesmanTaskService.commitTask(commitTask));
    }

    private Integer getPictureLength4CommitTask(CommitTask commitTask) {
        Integer count = 0;
        if(StringUtils.isNotEmpty(commitTask.getCommitTaskPic1Base64())){
            count ++;
        }
        if(StringUtils.isNotEmpty(commitTask.getCommitTaskPic2Base64())){
            count ++;
        }
        if(StringUtils.isNotEmpty(commitTask.getCommitTaskPic3Base64())){
            count ++;
        }
        if(StringUtils.isNotEmpty(commitTask.getCommitTaskPic4Base64())){
            count ++;
        }
        if(StringUtils.isNotEmpty(commitTask.getCommitTaskPic5Base64())){
            count ++;
        }
        return count;
    }

    private void vaildateCommitTask(CommitTask commitTask) {
        salesmanTaskService.vaildateStatus(commitTask.getId());
        salesmanTaskService.vaildateFinishOrder(commitTask.getId());
    }

}
