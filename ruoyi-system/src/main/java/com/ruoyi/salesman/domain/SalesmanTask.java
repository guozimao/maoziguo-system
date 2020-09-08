package com.ruoyi.salesman.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 商业任务信息对象 dt_business_task
 * 
 * @author zimao
 * @date 2020-08-27
 */
public class SalesmanTask
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 集团分配时间 */
    @Excel(name = "集团分配时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date groupAllocateTime;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 订单状态（0 完成 1 待完成） */
    @Excel(name = "订单状态", readConverterExp = "0=,完=成,1=,待=完成")
    private String orderStatus;

    /** 分配时间 */
    @Excel(name = "分配时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date allocateTime;

    /** 反馈图片url1 */
    @Excel(name = "反馈图片url1")
    private String feedbackPictureUrl1;

    /** 反馈图片url2 */
    @Excel(name = "反馈图片url2")
    private String feedbackPictureUrl2;

    /** 反馈图片url3 */
    @Excel(name = "反馈图片url3")
    private String feedbackPictureUrl3;

    /** 反馈图片url4 */
    @Excel(name = "反馈图片url4")
    private String feedbackPictureUrl4;

    /** 反馈图片url5 */
    @Excel(name = "反馈图片url5")
    private String feedbackPictureUrl5;

    /** 完成时间 */
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completionTime;

    /** 生成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Date requiredCompletionDate;

    private String remark;

    /** 商业任务信息明细信息 */
    private List<SalesmanTaskDetail> salesmanTaskDetails;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGroupAllocateTime(Date groupAllocateTime) 
    {
        this.groupAllocateTime = groupAllocateTime;
    }

    public Date getGroupAllocateTime() 
    {
        return groupAllocateTime;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setOrderStatus(String orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() 
    {
        return orderStatus;
    }
    public void setAllocateTime(Date allocateTime) 
    {
        this.allocateTime = allocateTime;
    }

    public Date getAllocateTime() {
        return allocateTime;
    }

    public void setFeedbackPictureUrl1(String feedbackPictureUrl1)
    {
        this.feedbackPictureUrl1 = feedbackPictureUrl1;
    }

    public String getFeedbackPictureUrl1() 
    {
        return feedbackPictureUrl1;
    }
    public void setFeedbackPictureUrl2(String feedbackPictureUrl2) 
    {
        this.feedbackPictureUrl2 = feedbackPictureUrl2;
    }

    public String getFeedbackPictureUrl2() 
    {
        return feedbackPictureUrl2;
    }
    public void setFeedbackPictureUrl3(String feedbackPictureUrl3) 
    {
        this.feedbackPictureUrl3 = feedbackPictureUrl3;
    }

    public String getFeedbackPictureUrl3() 
    {
        return feedbackPictureUrl3;
    }
    public void setFeedbackPictureUrl4(String feedbackPictureUrl4) 
    {
        this.feedbackPictureUrl4 = feedbackPictureUrl4;
    }

    public String getFeedbackPictureUrl4() 
    {
        return feedbackPictureUrl4;
    }
    public void setFeedbackPictureUrl5(String feedbackPictureUrl5) 
    {
        this.feedbackPictureUrl5 = feedbackPictureUrl5;
    }

    public String getFeedbackPictureUrl5() 
    {
        return feedbackPictureUrl5;
    }
    public void setCompletionTime(Date completionTime) 
    {
        this.completionTime = completionTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCompletionTime()
    {
        return completionTime;
    }

    public List<SalesmanTaskDetail> getSalesmanTaskDetails()
    {
        return salesmanTaskDetails;
    }

    public void setSalesmanTaskDetails(List<SalesmanTaskDetail> salesmanTaskDetails)
    {
        this.salesmanTaskDetails = salesmanTaskDetails;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRequiredCompletionDate() {
        return requiredCompletionDate;
    }

    public void setRequiredCompletionDate(Date requiredCompletionDate) {
        this.requiredCompletionDate = requiredCompletionDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupAllocateTime", getGroupAllocateTime())
            .append("deptId", getDeptId())
            .append("orderStatus", getOrderStatus())
            .append("allocateTime", getAllocateTime())
            .append("feedbackPictureUrl1", getFeedbackPictureUrl1())
            .append("feedbackPictureUrl2", getFeedbackPictureUrl2())
            .append("feedbackPictureUrl3", getFeedbackPictureUrl3())
            .append("feedbackPictureUrl4", getFeedbackPictureUrl4())
            .append("feedbackPictureUrl5", getFeedbackPictureUrl5())
            .append("completionTime", getCompletionTime())
            .append("createTime", getCreateTime())
            .append("dtBusinessTaskDetailList", getSalesmanTaskDetails())
            .toString();
    }
}
