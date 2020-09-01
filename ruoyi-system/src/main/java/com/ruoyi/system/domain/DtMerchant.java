package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商家信息对象 dt_merchant
 * 
 * @author zimao
 * @date 2020-09-01
 */
public class DtMerchant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商家ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 已关联店铺名集合 */
    @Excel(name = "已关联店铺名集合")
    private String associatedShopName;

    /** 佣金 */
    @Excel(name = "佣金")
    private BigDecimal commission;

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
    public void setAssociatedShopName(String associatedShopName) 
    {
        this.associatedShopName = associatedShopName;
    }

    public String getAssociatedShopName() 
    {
        return associatedShopName;
    }
    public void setCommission(BigDecimal commission) 
    {
        this.commission = commission;
    }

    public BigDecimal getCommission() 
    {
        return commission;
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
            .append("associatedShopName", getAssociatedShopName())
            .append("commission", getCommission())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
