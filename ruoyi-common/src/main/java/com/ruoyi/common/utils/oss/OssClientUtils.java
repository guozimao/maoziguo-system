package com.ruoyi.common.utils.oss;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OssClientUtils {

    private static String OSSAccessKeyId;

    private static String enpoint;

    public static String getOSSAccessKeyId() {
        return OSSAccessKeyId;
    }

    @Value("${oss.accessKeyId}")
    public void setOSSAccessKeyId(String OSSAccessKeyId) {
        OssClientUtils.OSSAccessKeyId = OSSAccessKeyId;
    }

    @Value("${oss.enpoint}")
    public void setEnpoint(String enpoint) {
        OssClientUtils.enpoint = enpoint;
    }

    /** oss参数格式(path,Expires,Signature) */
    public static String getPictureUrlByOssParam(String ossParam){
        String[] param = StringUtils.split(ossParam,",");
        if(param.length != 3){
            throw new BusinessException("OssParam格式不正确");
        }
        return enpoint + param[0] + "?Expires=" + param[1] + "&OSSAccessKeyId=" + OSSAccessKeyId + "&Signature=" + param[2];
    }
}
