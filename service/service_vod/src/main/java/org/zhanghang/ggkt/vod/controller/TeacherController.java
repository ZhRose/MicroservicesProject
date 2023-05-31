package org.zhanghang.ggkt.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhanghang.ggkt.model.vod.Teacher;
import com.zhanghang.ggkt.vo.vod.TeacherQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.zhanghang.Result.Result;
import org.zhanghang.exception.DiyException;
import org.zhanghang.ggkt.vod.service.TeacherService;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zhanghang
 * @since 2023-05-30
 */
@RestController
@RequestMapping("/admin/vod/teacher")
@ApiModel("讲师接口")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    //查询所有讲师  http://localhost:8301/admin/vod/teacher/findAll
//    @ApiOperation("查询所有讲师")
//    @GetMapping("/findAll")
//    public List<Teacher> findAll() {
//        List<Teacher> teachers = teacherService.list();
//        return teachers;
//    }
    @ApiOperation("查询所有讲师")
    @GetMapping("/findAll")
    public Result findAll() {
        try {
            int a = 10/0;
        }catch(Exception e) {
            throw new DiyException(20001,"出现自定义异常");//手动抛出自定义异常，便于测试
        }
        List<Teacher> teachers = teacherService.list();
        return Result.ok(teachers).message("查询成功");
    }

    //逻辑删除讲师      通过swagger2进行测试
    @DeleteMapping("remove/{id}")
    @ApiOperation("逻辑删除讲师")
    public Result removeById(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    //条件查询分页
    @ApiOperation("条件查询分页")
    @PostMapping("findQueryPage/{current}/{limit}")
    public Result findPage(@ApiParam(name = "current", value = "当前页码", required = true)
                           @PathVariable long current,
                           @ApiParam(name = "limit", value = "每页记录数", required = true)
                           @PathVariable long limit,
                           @ApiParam(name = "teacherVo", value = "查询对象", required = false)
                           @RequestBody(required = false) TeacherQueryVo teacherQueryVo
    ) {
        //创建page对象，传递当前页和记录数
        Page<Teacher> page = new Page<>(current, limit);
        if(teacherQueryVo==null){ //判断是否为空的情况,空的情况下全部查询出来
            Page<Teacher> page1 = teacherService.page(page, null);
            return Result.ok(page1);
        }else {
            //获取条件值
            String name = teacherQueryVo.getName();//讲师名称
            Integer level = teacherQueryVo.getLevel();//讲师级别
            String joinDataBegin = teacherQueryVo.getJoinDateBegin();//开始时间
            String joinDataEnd = teacherQueryVo.getJoinDateEnd();//结束时间
            //封装条件
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(joinDataBegin)) {
                wrapper.ge("join_date", joinDataBegin);//将表中的数据与开始时间和结束时间作比较
            }
            if (!StringUtils.isEmpty(joinDataEnd)) {
                wrapper.le("join_data", joinDataEnd);
            }
            Page<Teacher> page1 = teacherService.page(page, wrapper);

            return Result.ok(page1);
        }
    }
    @ApiOperation("添加讲师")
    @PostMapping("/saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher){
        boolean isSuccess=teacherService.save(teacher);
        if(isSuccess){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }

    @ApiOperation("根据id查询")
    @GetMapping("/getTeacher/{id}")
    public Result getTeacher(@PathVariable Long id){
        Teacher teacher =teacherService.getById(id);
        return Result.ok(teacher);
    }

    @ApiOperation("修改讲师")
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher){
        boolean isSuccess = teacherService.updateById(teacher);
        if(isSuccess){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }

    @ApiOperation("批量删除讲师")
    @DeleteMapping("/deleteTeachers")
    public Result deleteTeacher(@RequestBody List<Long> idlist){
        boolean isSuccess = teacherService.removeByIds(idlist);
        if(isSuccess){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }

}