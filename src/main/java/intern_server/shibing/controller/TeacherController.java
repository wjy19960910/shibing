package intern_server.shibing.controller;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Notice;
import intern_server.shibing.data.po.Teacher;
import intern_server.shibing.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/4 15:14
 * 管理老师信息
 */
@RestController
@RequestMapping("/auth/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/fetchTeacherInfoData")
    public PageInfo fetchTeacherInfoData(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) String fuzzy){
        return teacherService.fetchTeacherInfoData(page,pageSize,fuzzy);
    }

    @PostMapping("/insertTeacherInfo")
    public Map<String,Object> insertTeacherInfo(@RequestBody Teacher teacher){
        return teacherService.insertTeacherInfo(teacher);
    }

    @DeleteMapping("/removeTeacherInfo/{ids}")
    public Map<String,Object> removeTeacherInfo(@PathVariable String[] ids){
        return teacherService.removeTeacherInfo(ids);
    }

    @GetMapping("/getTeacherInfoById/{id}")
    public Map<String,Object> getTeacherInfoById(@PathVariable String id){
        return teacherService.getTeacherInfoById(id);
    }

    @PutMapping("/editTeacherInfo")
    public Map<String,Object> editTeacherInfo(@RequestBody Teacher teacher){
        return teacherService.editTeacherInfo(teacher);
    }
}
