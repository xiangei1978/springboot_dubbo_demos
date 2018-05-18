package com.davidxl.model.sys;


import lombok.Data;

import java.util.Set;


@Data
public class EmployeeTokenCache {
    private String token;
    private Integer employeeID;
    private String employeeName;
    private Set<String> function_Points;
    private String  userAgent;
    private Long   expiryTime, createTime;
    private Set<String> Roles;
    private String login_ip;
}
