package com.davidxl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.davidxl.common.CheckAuthority;
import com.davidxl.common.type.NormalResultType;
import com.davidxl.dubbo.service.UserService;
import com.davidxl.model.User;
import com.davidxl.util.VerifyCodeUtil;
import com.davidxl.web.CommonResult;
import com.davidxl.web.NormalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Api(value="用户模块")
//@RequestMapping(value = {"/user"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@Slf4j
public class UserController {


    @Reference(version = "1.1.0")
    private UserService userService;


    @ApiOperation(value="验证图形验证码", notes="验证图形验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "verifyCode", value = "verifyCode", defaultValue = "", dataType = "string")
    })
    @RequestMapping(value="/checkVerifyCode", method= RequestMethod.GET)
    public CommonResult checkVerifyCode(HttpServletRequest httpServletRequest, String verifyCode ){
        CommonResult r = new CommonResult();

//        String captchaId = VerifyCodeUtil.checkVerifyCode(httpServletRequest,verifyCode);
//
//        log.debug("Session  vrifyCode "+captchaId+" form vrifyCode "+verifyCode);

        if (!VerifyCodeUtil.checkVerifyCode(httpServletRequest,verifyCode)) {
            r.setCode(-1);
            r.setErrMsg("错误的验证码");

        } else {
            r.setCode(0);
            r.setErrMsg("登录成功");

        }

        return   r ;

    }

    @ApiOperation(value="新增用户", notes="新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "令牌", defaultValue = "39MEHmCu4BmumCRSAriORS4lUXcQYSU9cM4DRUJCLL6YWXAvS4igTbSWEQbpvC8U_NlBH8NnwWzfdNtwDDwdzXXQE7wbnt7it31ThNqHnJo", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "user",
                              value = "{ \"name\": \"xlr\", \"age\": 3, \"amount\": 10.11, \"sex\": \"male=男;female=女;unknown=未知\"  }", required = true, dataType = "string")
    })
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public CommonResult insertUser(@RequestBody User user,@RequestAttribute Integer employeeID){
        CommonResult r = new CommonResult();

        System.out.printf("" + employeeID);
//        if (1==1)
//            throw new RuntimeException("测试发生异常了");

//        if (user.getAge()==3)
//            throw  new NormalException(-1,"三岁无法注册!");

        Assert.state(user.getAge()!=3,"三岁无法注册!");


        if (userService.insertSelective(user) !=1)
            throw  new NormalException(NormalResultType.err_database_insert);

//        User user = new User();
//        user.setName(name);
//        user.setAge(age);
//       user.setSex(SexType.unknown);
        return   r ;
        //return "插入结果:" + userService.insertSelective(user) + " userID =  " + user.getId();
        //return peoplePerties.getName()+"====="+peoplePerties.getAge();
//        return "index";
    }

    @ApiOperation(value="根据主键查询用户", notes="根据主键查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "用户id", defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "id1", value = "用户id1", defaultValue = "1", dataType = "int")
    })
    @RequestMapping(value="/select", method= RequestMethod.GET)
    @CheckAuthority(funcs = {"sys.test.pass","fjadkfaldaf.dld"})
    public CommonResult selectByPrimaryKey(Integer id   ){

        CommonResult r = new CommonResult();
        User user  = userService.selectByPrimaryKey(id);
        r.setData(user);
        return   r  ;
    }


    // 创建线程安全的Map
    static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<Integer, User>());

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public CommonResult getUserById (@PathVariable(value = "id") Integer id){

        CommonResult r = new CommonResult();
        User user  = userService.selectByPrimaryKey(id);
        r.setData(user);
        return   r  ;

    }

    /**
     * 查询用户列表
     * @return
     */
    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserList (){
        JsonResult r = new JsonResult();
        try {
            List<User> userList = new ArrayList<User>(users.values());
            r.setResult(userList);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add (@RequestBody User user){
        JsonResult r = new JsonResult();
        try {
            users.put(user.getId(), user);
            r.setResult(user.getId());
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");

            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @ApiOperation(value="删除用户", notes="根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete (@PathVariable(value = "id") Integer id){
        JsonResult r = new JsonResult();
        try {
            users.remove(id);
            r.setResult(id);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");

            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 根据id修改用户信息
     * @param user
     * @return
     */
    @ApiOperation(value="更新信息", notes="根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update (@PathVariable("id") Integer id, @RequestBody User user){
        JsonResult r = new JsonResult();
        try {
            User u = users.get(id);
            u.setName(user.getName());
            u.setAge(user.getAge());
            users.put(id, u);
            r.setResult(u);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");

            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    @ApiIgnore//使用该注解忽略这个API
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String  jsonTest() {
        return " hi you!";
    }

}



//
//@RestController
//@ResponseBody
//@Api(value = "User相关接口")
//@RequestMapping(value = {"/user"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
///**
// * Created by xianglei on 2018/4/17.
// */
//public class UserController {
//
//
//    @Autowired
//    private UserService userService;
//
////    @ApiOperation(value="新增用户", notes="新增用户")
////    @ApiImplicitParams({
////                      @ApiImplicitParam(paramType = "query", name = "user",
////                              value = "{ \"name\": \"xlr\", \"age\": 3, \"amount\": 10.11, \"sex\": \"m=男;f=女;u=未知\"  }", required = true, dataType = "string")
////    })
////    @RequestMapping(value="/insert", method=RequestMethod.POST)
////    public String UserSeriviceDubbo(@RequestBody User user){
//////        User user = new User();
//////        user.setName(name);
//////        user.setAge(age);
//////        user.setSex(SexType.unknown);
////
////        return "插入结果:" + userService.insertSelective(user) + " userID =  " + user.getId();
////        //return peoplePerties.getName()+"====="+peoplePerties.getAge();
//////        return "index";
////    }
//
////    @ApiOperation(value="根据主键查询用户", notes="根据主键查询用户")
////    @ApiImplicitParams({
////            @ApiImplicitParam(paramType = "query", name = "id", value = "用户id", defaultValue = "1", dataType = "int"),
////            @ApiImplicitParam(paramType = "query", name = "id1", value = "用户id1", defaultValue = "1", dataType = "int")
////    })
////    @RequestMapping(value="/select", method=RequestMethod.GET)
////    public String selectByPrimaryKey( Integer id   ){
////        User user  = userService.selectByPrimaryKey(id);
////
////        return   JSONObject.toJSONString(user)  ;
////    }
//
//}
