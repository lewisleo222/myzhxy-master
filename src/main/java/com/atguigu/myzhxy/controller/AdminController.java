package com.atguigu.myzhxy.controller;


import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.pojo.Clazz;
import com.atguigu.myzhxy.service.AdminService;
import com.atguigu.myzhxy.util.MD5;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService ;

//    http://localhost:9001/sms/adminController/getAllAdmin/1/3
    @ApiOperation("分页待条件查询管路员")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("管理员名字") String adminName
    ){
        Page<Admin> pageParam=new Page<>(pageNo,pageSize);

        IPage<Admin> iPage=adminService.getAdminsByOpr(pageParam,adminName);

        return Result.ok(iPage);
    }

    //    http://localhost:9001/sms/adminController/saveOrUpdateAdmin
    @ApiOperation("增加或者修改班级信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@ApiParam("JSON的Admin对象") @RequestBody Admin admin) {
        Integer id = admin.getId();
        if (id == null || 0 == id) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    //http://localhost:9001/sms/clazzController/deleteAdmin
    @ApiOperation("删除Admin信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteClazz(@ApiParam("要删除的所有的admin的id的JSON集合") @RequestBody List<Integer> ids) {
        adminService.removeByIds(ids);
        return Result.ok();
    }
}
