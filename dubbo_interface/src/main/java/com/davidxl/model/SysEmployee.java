package com.davidxl.model;

import com.davidxl.common.status.EmployeeStatus;
import com.davidxl.common.status.EmployeeType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysEmployee implements Serializable {
    private Integer employeeId;

    private String loginId;

    private String name;

    private Integer departmentId;

    private Integer position;

    private String phoneNum;

    private String email;

    private String password;

    private String safePassword;

    private String lastIp;

    private Date lastLogin;

    private Date createDate;

    private Integer creator;

    private Date modifiedDate;

    private Integer modifer;

    private EmployeeStatus status;

    private EmployeeType type;

}