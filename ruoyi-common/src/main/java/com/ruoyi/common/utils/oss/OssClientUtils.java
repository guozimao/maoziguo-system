package com.ruoyi.common.utils.oss;

import com.aliyun.oss.OSSClient;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;

@Component
public class OssClientUtils {

    /** 阿里云API的密钥Access Key ID */
    private static String OSSAccessKeyId;
    /** 阿里云API的内或外网域名 */
    private static String enpoint;
    /** 阿里云API的密钥Access Key Secret */
    private static String accessKeySecret;
    /** 阿里云API的bucket名称 */
    private static String bucketName;
    /** 阿里云API的文件夹名称 */
    private static String folder;

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

    @Value("${oss.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        OssClientUtils.accessKeySecret = accessKeySecret;
    }

    @Value("${oss.bucketName}")
    public void setBucketName(String bucketName) {
        OssClientUtils.bucketName = bucketName;
    }

    @Value("${oss.folder}")
    public void setFolder(String folder) {
        OssClientUtils.folder = folder;
    }

    /** oss参数格式(path,Expires,Signature) */
    public static String getPictureUrlByOssParam(String ossParam){
        if(StringUtils.isEmpty(ossParam)){
            return null;
        }
        String[] param = StringUtils.split(ossParam,",");
        if(param.length != 3){
            throw new BusinessException("OssParam格式不正确");
        }
        return "http://" + bucketName + "." + enpoint + "/" + param[0] + "?Expires=" + param[1] + "&OSSAccessKeyId=" + OSSAccessKeyId + "&Signature=" + param[2];
    }

    public static URL picOSS(byte buf[]) {
        String fileName = UUID.randomUUID().toString()+ ".png";
        String endpoint = OssClientUtils.enpoint;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
        // https://ram.console.aliyun.com 创建
        String accessKeyId = OssClientUtils.OSSAccessKeyId;
        String accessKeySecret = OssClientUtils.accessKeySecret;
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传
        ossClient.putObject(OssClientUtils.bucketName, folder + fileName, new ByteArrayInputStream(buf));
        // 关闭client
        ossClient.shutdown();
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        //int random = (int)(Math.random() * (OSSClientUtil.folders.size() - 1));
        URL url = ossClient.generatePresignedUrl(OssClientUtils.bucketName, folder + fileName, expiration);
        return url;
    }

    public static String URL2OssParam(URL url){
        String path = url.getPath();
        String queryParam = url.getQuery();
        String expires = org.apache.commons.lang3.StringUtils.substringBetween(queryParam + "&","Expires=","&");
        String signature = org.apache.commons.lang3.StringUtils.substringBetween(queryParam + "&","Signature=","&");
        String ossPictureParam = org.apache.commons.lang3.StringUtils.joinWith(",",path,expires,signature);
        return ossPictureParam;
    }
}
