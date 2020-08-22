package com.ruoyi.businessteam.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 业务人员信息对象 dt_salesman
 * 
 * @author ruoyi
 * @date 2020-08-22
 */
public class DtSalesman extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 业务人员ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 佣金 */
    @Excel(name = "佣金")
    private BigDecimal commission;

    /** 接单上限 */
    @Excel(name = "接单上限")
    private Long receivingLimit;

    /** 关联状态（0已关联 1未关联） */
    @Excel(name = "关联状态", readConverterExp = "0=已关联,1=未关联")
    private String associationStatus;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setCommission(BigDecimal commission) 
    {
        this.commission = commission;
    }

    public BigDecimal getCommission() 
    {
        return commission;
    }
    public void setReceivingLimit(Long receivingLimit) 
    {
        this.receivingLimit = receivingLimit;
    }

    public Long getReceivingLimit() 
    {
        return receivingLimit;
    }
    public void setAssociationStatus(String associationStatus) 
    {
        this.associationStatus = associationStatus;
    }

    public String getAssociationStatus() 
    {
        return associationStatus;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("commission", getCommission())
            .append("receivingLimit", getReceivingLimit())
            .append("associationStatus", getAssociationStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
