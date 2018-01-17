//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.blgroup.jkl.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

@Component
@Scope("singleton")
public class OSSUtils {
    private static final Logger logger = LoggerFactory.getLogger(OSSUtils.class);
    
    @Autowired
    private OSSEntity ossEntity;

    public OSSUtils() {
    }

    

    public OSSClient open() {
        return this.ossClient();
    }

    @Bean
    public OSSClient ossClient() {
        ClientConfiguration conf = new ClientConfiguration();
        conf.setSupportCname(true);
        return new OSSClient(this.ossEntity.getEndpoint(), this.ossEntity.getAccessKeyId(), this.ossEntity.getAccessKeySecret(), conf);
    }

    public List<Bucket> listBuckets() {
        OSSClient ossClient = this.open();
        return ossClient.listBuckets();
    }

    public ObjectListing ObjectListing(String keyPrifex) {
        OSSClient ossClient = this.open();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(this.ossEntity.getBucketName());
        listObjectsRequest.setPrefix(keyPrifex);
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        return listing;
    }

    public List<OSSObjectSummary> listObjects(String keyPrifex) {
        ObjectListing listing = this.ObjectListing(keyPrifex);
        List sums = listing.getObjectSummaries();
        return sums;
    }

    public List<String> listObjectPrefixes(String keyPrifex) {
        OSSClient ossClient = this.open();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(this.ossEntity.getBucketName());
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setPrefix(keyPrifex);
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        List compres = listing.getCommonPrefixes();
        return compres;
    }

    public boolean ObjectExist(String fileName) {
        OSSClient ossClient = this.open();
        boolean found = ossClient.doesObjectExist(this.ossEntity.getBucketName(), fileName);
        return found;
    }

    public void deleteObject(String fileName) {
        OSSClient ossClient = this.open();
        ossClient.deleteObject(this.ossEntity.getBucketName(), fileName);
    }

    public List<String> deleteObjects(List<String> fileNames) {
        OSSClient ossClient = this.open();
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects((new DeleteObjectsRequest(this.ossEntity.getBucketName())).withKeys(fileNames));
        List deletedObjects = deleteObjectsResult.getDeletedObjects();
        return deletedObjects;
    }

    public PutObjectResult upload(String fileName, String content) {
        OSSClient ossClient = this.open();
        return ossClient.putObject(this.ossEntity.getBucketName(), fileName, new ByteArrayInputStream(content.getBytes()));
    }

    public PutObjectResult upload(String fileName, InputStream inputStream) {
        OSSClient ossClient = this.open();
        return ossClient.putObject(this.ossEntity.getBucketName(), fileName, inputStream);
    }

    public PutObjectResult upload(String fileName, File file) {
        OSSClient ossClient = this.open();
        return ossClient.putObject(this.ossEntity.getBucketName(), fileName, file);
    }

    public void getObject(String objectKey) {
        OSSClient ossClient = this.open();

        try {
            try {
                OSSObject e = ossClient.getObject(this.ossEntity.getBucketName(), objectKey);
                ObjectMetadata meta = e.getObjectMetadata();
                System.out.println("ContentLength=" + meta.getContentLength());
                InputStream objectContent = e.getObjectContent();
                System.out.println("objectContent=" + objectContent.read());
                ObjectMetadata objectMetadata = ossClient.getObjectMetadata(this.ossEntity.getBucketName(), objectKey);
                System.out.println("ContentType=" + objectMetadata.getContentType());
            } catch (IOException var10) {
                logger.error("OSSUtils.getObject 错误：", var10);
                var10.printStackTrace();
            }

        } finally {
            ;
        }
    }

    public void getObjectAdvanced(String objectKey, String localUrl) {
        OSSClient ossClient = this.open();

        try {
            try {
                GetObjectRequest e = new GetObjectRequest(this.ossEntity.getBucketName(), objectKey);
                String FileName = objectKey;
                if(objectKey.lastIndexOf("/") >= 0) {
                    FileName = objectKey.substring(objectKey.lastIndexOf("/"), objectKey.length());
                }

                File file = new File(localUrl + FileName);
                ossClient.getObject(e, file);
            } catch (Exception var10) {
                logger.error("OSSUtils.getObjectAdvanced 错误：", var10);
            }

        } finally {
            ;
        }
    }

    public PutObjectResult mkdir(String dir) {
        OSSClient ossClient = this.open();
        return ossClient.putObject(this.ossEntity.getBucketName(), dir, new ByteArrayInputStream(new byte[0]));
    }

    public URL generatePresignedUrl(String key) {
        OSSClient ossClient = this.open();
        Date expiration = new Date((new Date()).getTime() + 3600000L);
        URL url = ossClient.generatePresignedUrl(this.ossEntity.getBucketName(), key, expiration, HttpMethod.PUT);
        return url;
    }

    public URL generatePutPresignedUrl(String key) {
        OSSClient ossClient = this.open();
        new Date((new Date()).getTime() + 3600000L);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(this.ossEntity.getBucketName(), key);
        generatePresignedUrlRequest.setMethod(HttpMethod.PUT);
        generatePresignedUrlRequest.addUserMetadata("author", "riso");
        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        return url;
    }

    public void main(String[] args) throws FileNotFoundException {
        System.out.println("-->列举buckets列表:");
        List list = this.listBuckets();
        Iterator pors = list.iterator();

        while(pors.hasNext()) {
            Bucket inputStream = (Bucket)pors.next();
            System.out.println(inputStream.getName());
        }

        System.out.println("-->upload File(上传-流式上传(上传字符串)):");
        PutObjectResult pors1 = this.upload("sit/item/s.txt", "上传字符串");
        System.out.println("-->upload File(传-上传文件流):");
        FileInputStream inputStream1 = new FileInputStream("D:\\i.txt");
        this.upload("sit/item/i.txt", (InputStream)inputStream1);
        System.out.println("-->upload File(上传-上传本地文件):");
        PutObjectResult por1 = this.upload("sit/item/a1.txt", new File("D:\\test.txt"));
        this.upload("sit/item/a2.txt", new File("D:\\test.txt"));
        this.upload("sit/item/a3.txt", new File("D:\\test.txt"));
        this.upload("sit/item/a4.txt", new File("D:\\test.txt"));
        this.upload("a.txt", new File("D:\\test.txt"));
        System.out.println(por1.getETag() + "|" + por1.getCallbackResponseBody());
        List sums = this.listObjects("sit/item/");
        System.out.println("-->sums(递归列举 文件):");
        Iterator comms = sums.iterator();

        while(comms.hasNext()) {
            OSSObjectSummary existFileName = (OSSObjectSummary)comms.next();
            System.out.println("\t" + existFileName.getKey());
        }

        System.out.println("-->comms(单层列举文件夹):");
        List comms1 = this.listObjectPrefixes("sit/item/");
        Iterator existFileName1 = comms1.iterator();

        String delFileName;
        while(existFileName1.hasNext()) {
            delFileName = (String)existFileName1.next();
            System.out.println("\t" + delFileName);
        }

        String existFileName2 = "sit/item/a4.txt";
        System.out.println("-->ObjectExist:" + existFileName2);
        System.out.println(this.ObjectExist(existFileName2));
        delFileName = "sit/item/a.txt";
        System.out.println("-->Delete:" + delFileName);
        this.deleteObject(delFileName);
        System.out.println("-->Delete objects:");
        ArrayList fileNames = new ArrayList();
        fileNames.add("sit/item/a1.txt");
        fileNames.add("sit/item/a2.txt");
        fileNames.add("sit/item/a3.txt");
        List delList = this.deleteObjects(fileNames);
        Iterator getFileName = delList.iterator();

        while(getFileName.hasNext()) {
            String str = (String)getFileName.next();
            System.out.println(str);
        }

        System.out.println("-->get object:");
        this.getObject("sit/item/a4.txt");
        System.out.println("-->get object local:");
        String getFileName1 = "sit/item/s.txt";
        if(this.ObjectExist(getFileName1)) {
            this.getObjectAdvanced("D:/oss/", getFileName1);
        }

    }
}
