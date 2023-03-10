package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Clazz;
import com.atguigu.myzhxy.service.ClazzService;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

//    http://localhost:9001/sms/clazzController/getClazzsByOpr/1/3

    @ApiOperation("分页带条件查询班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzByOpr(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的查询条件") Clazz clazz
    ) {
        Page<Clazz> page = new Page<>(pageNo, pageSize);

        IPage<Clazz> iPage = clazzService.getClazzsByOpr(page, clazz);

        return Result.ok(iPage);
    }

    //    http://localhost:9001/sms/clazzController/saveOrUpdateClazz
    @ApiOperation("增加或者修改班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("JSON的Clzz对象") @RequestBody Clazz clazz) {
        //接收参数
        //调用服务层方法完成增减或者修改
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    //http://localhost:9001/sms/clazzController/deleteClazz
    @ApiOperation("删除Clazz信息")
    @DeleteMapping("deleteClazz")
    public Result deleteClazz(@ApiParam("要删除的所有的clazz的id的JSON集合") @RequestBody List<Integer> ids) {
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    //GET/sms/clazzController/getClazzs

    @ApiOperation("查询所有班级信息")
    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> clazzes = clazzService.getClazzs();
        return Result.ok(clazzes);
    }
}