package com.ruoyi.promoters.application.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 团队关联申请对象 team_association_application
 * 
 * @author ruoyi
 * @date 2020-08-23
 */
public class TeamAssociationApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 申请ID */
    private Long id;

    /** 申请人Id(userId) */
    @Excel(name = "申请人Id(userId)")
    private Long applicantId;

    /** 审核人Id(salesManId) */
    @Excel(name = "审核人Id(salesManId)")
    private Long approverId;

    private Long DeptId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setApplicantId(Long applicantId) 
    {
        this.applicantId = applicantId;
    }

    public Long getApplicantId() 
    {
        return applicantId;
    }
    public void setApproverId(Long approverId) 
    {
        this.approverId = approverId;
    }

    public Long getApproverId() 
    {
        return approverId;
    }

    public Long getDeptId() {
        return DeptId;
    }

    public void setDeptId(Long deptId) {
        DeptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("applicantId", getApplicantId())
            .append("approverId", getApproverId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
