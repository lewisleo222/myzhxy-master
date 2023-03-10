package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Student;
import com.atguigu.myzhxy.service.StudentService;
import com.atguigu.myzhxy.util.MD5;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.CtBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = "学生管理器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation("分页带条件查询")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentBuOpr(
            @ApiParam("页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询的条件") Student student
    ) {
        Page<Student> page = new Page<>(pageNo, pageSize);
        IPage<Student> iPage = studentService.getClazzsByOpr(page, student);
        return Result.ok(iPage);
    }
//    http://localhost:9001/sms/studentController/addOrUpdateStudent
    @ApiOperation("增加或者修改学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@ApiParam("JSON的Student对象") @RequestBody Student student)
    {
        //接收参数
        //调用服务层方法完成增减或者修改
        Integer id=student.getId();
        if (null == id || 0 == id) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

//    http://localhost:9001/sms/studentController/delStudentById
    @ApiOperation("删除学生信息")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@ApiParam("要删除的所有的student的id的JSON集合")
                                 @RequestBody List<Integer> ids)
    {
        studentService.removeByIds(ids);
        return Result.ok();
    }
}
