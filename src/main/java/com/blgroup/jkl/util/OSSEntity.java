//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.blgroup.jkl.util;

import org.springframework.stereotype.Component;
/**
 * oss.endpoint=http://oss-cn-hangzhou.aliyuncs.com
	oss.accessKeyId=bozCBSsHQyldC6Bq
	oss.accessKeySecret=UeYpgfVwHns3hmvxKBNmh06qN17jH4
	oss.bucketName=sit-b2b
	oss.roleArn=acs:ram::1655782734425455:role/riso-oss

 * @author XJM29
 *
 */

@Component
public class OSSEntity {
    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private String accessKeyId ="bozCBSsHQyldC6Bq";
    private String accessKeySecret ="UeYpgfVwHns3hmvxKBNmh06qN17jH4";
    private String bucketName ="sit-b2b";
    private String roleArn ="acs:ram::1655782734425455:role/riso-oss";

    public OSSEntity() {
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return this.accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String roleArn) {
        this.roleArn = roleArn;
    }
}
