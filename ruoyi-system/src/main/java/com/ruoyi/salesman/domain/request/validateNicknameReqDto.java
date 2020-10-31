package com.ruoyi.salesman.domain.request;


public class validateNicknameReqDto {
    private static final long serialVersionUID = 1L;
    /** id */
    private Long id;

    private String platformNickname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatformNickname() {
        return platformNickname;
    }

    public void setPlatformNickname(String platformNickname) {
        this.platformNickname = platformNickname;
    }
}
