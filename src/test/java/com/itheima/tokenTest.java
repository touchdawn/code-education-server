package com.itheima;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

public class tokenTest {

    private long time = 1000*60*60*24;
    private String signature = "admin";

    @Test
    public void jwt(){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("username","tom")
                .claim("role","admin")
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() +time))
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        System.out.println(jwtToken);
    }


    @Test
    public void parse(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2NDczMjU1MzIsImp0aSI6IjFkMmE2N2Q3LTM2NDktNDgwMi1hNWI5LTAwMTA2OTIxODkwZSJ9.yIqeC-XkHf4ZtiQM3yqxXXAfPj8QMahMVGz9SzegVcg";
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        System.out.println(claims.get("username"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getExpiration());
    }

    @Test
    public void qiniu(){
        String accessKey = "lBWb8CP90dSUR-ljomttFgKaJZYntGMCEKk8Oqt2";
        String secretKey = "Kbkff9JOeQr5ZtnGelmGiv2ARLjlkjA5_2_F34A8";
        String bucket = "test-20220313";
        String key = "22334";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
    }
    @Test
    public void aaa(){
        String accessKey = "SELECT  t.ID                                                                                                                      as \"id\",\n" +
                "                                   t.SUB_PROJECT_ID                                                                               as \"subProjectId\",\n" +
                "                                   t.PARENT_ID                                                                                    as \"parentId\",\n" +
                "                                   t.TASK_TITLE                                                                                   as \"taskTitle\",\n" +
                "                                   t.TASK_TITLE                                                                                   as \"label\",\n" +
                "                                   PRODUCT_LINE_GROUP.PRODUCT_LINE_CODE                                                           as \"productLineCode\",\n" +
                "                                   PRODUCT_LINE_GROUP.GROUP_NAME                                                                  as \"groupName\",\n" +
                "                                   PM_PROJECT_TASK.TASK_TITLE                                                                     as \"strategyTitle\",\n" +
                "                                   t.TASK_DETAIL                                                                                  as \"taskDetail\",\n" +
                "                                   t.STATUS                                                                                       as \"status\",\n" +
                "                                   t.PRIORITY                                                                                     as \"priority\",\n" +
                "                                   t.RISK_LEVEL                                                                                   as \"riskLevel\",\n" +
                "                                   t.RISK_REASON                                                                                  as \"riskReason\",\n" +
                "                                   t.CREATE_EMPLOYEE                                                                              as \"createEmployeeId\",\n" +
                "                                   t.ORDER_NUM                                                                                    as \"orderNum\",\n" +
                "                                   t.HAS_DEFECT                                                                                   as \"hasDefect\",\n" +
                "                                   t.CAN_SELECT                                                                                   as \"canSelect\",\n" +
                "                                   t.EMPLOYEE_AUTH                                                                                as \"employeeAuth\",\n" +
                "                                   t.DEPT_PRJ_CODE                                                                                as \"deptPrjCode\",\n" +
                "                                   TO_CHAR(t.START_TIME, 'YYYY-MM-DD')                                                            as \"startTime\",\n" +
                "                                   TO_CHAR(t.END_TIME, 'YYYY-MM-DD')                                                              as \"endTime\",\n" +
                "                                   TO_CHAR(t.REAL_START_TIME, 'YYYY-MM-DD')                                                       as \"realStartTime\",\n" +
                "                                   TO_CHAR(t.REAL_END_TIME, 'YYYY-MM-DD')                                                         as \"realEndTime\",\n" +
                "                                   (select v1.EMPLOYEE_NAME from CA_EMPLOYEE v1 where v1.ID = t.CREATE_EMPLOYEE)                  as \"createEmployeeName\",\n" +
                "                                   (select count(1) from PM_SUB_PROJECT_TASK v2 where v2.PARENT_ID = t.ID)                        as \"childrenNum\",\n" +
                "                                   (select count(1) from PM_SUB_PROJECT_TASK_LINK v3 where v3.TASK_ID = t.ID)                     as \"employeeNum\",\n" +
                "                                   (select to_char(wm_concat(v4.EMPLOYEE_ID)) from PM_SUB_PROJECT_TASK_LINK v4 where v4.TASK_ID = t.ID) as \"employeeIds\",\n" +
                "                                   (select to_char(wm_concat(v6.EMPLOYEE_NAME)) from PM_SUB_PROJECT_TASK_LINK v5\n" +
                "                                   join CA_EMPLOYEE v6 on v5.EMPLOYEE_ID = v6.ID where v5.TASK_ID = t.ID)                         as \"employeeList\",\n" +
                "                                   (select to_char(wm_concat(v8.TYPE_NAME)) from PM_SUB_PROJECT_TASK_TYPE_LINK v7\n" +
                "                                   join PM_SUB_PROJECT_TASK_TYPE v8 on v7.TYPE_ID = v8.ID where v7.TASK_ID = t.ID)                as \"taskType\",\n" +
                "                                   t.TASK_LOAD_TYPE                                                                               as \"taskLoadType\",\n" +
                "                                   t.NESMA_TASK_LOAD                                                                              as \"totalLoad\",\n" +
                "                                   (select sum(WORK_LOAD) from TASK_WORKITEM_LINK link where link.TASK_ID = t.ID GROUP BY link.TASK_ID)   as \"taskLoad\"\n" +
                "                FROM PM_SUB_PROJECT_TASK t\n" +
                "                    left join PM_PROJECT_TASK on PM_PROJECT_TASK.ID = t.PROJECT_TASK_ID\n" +
                "                    left join PRODUCT_LINE_GROUP on PRODUCT_LINE_GROUP.ID = PM_PROJECT_TASK.PRODUCT_LINE_GROUP\n" +
                "                where t.SUB_PROJECT_ID = ?1 and DEFECT_FLAG = 0 \n"+
                "                order by t.ORDER_NUM ";
        System.out.println(accessKey);
    }


}
