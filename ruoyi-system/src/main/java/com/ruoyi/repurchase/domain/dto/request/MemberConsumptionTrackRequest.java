package com.ruoyi.repurchase.domain.dto.request;

public class MemberConsumptionTrackRequest {
    private String[] scopeDate;

    private String storeName;

    private String memberName;

    public String[] getScopeDate() {
        if (scopeDate == null){
            scopeDate = new String[] {"",""};
        }
        return scopeDate;
    }

    public void setScopeDate(String[] scopeDate) {
        this.scopeDate = scopeDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
