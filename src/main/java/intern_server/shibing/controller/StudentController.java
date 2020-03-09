package intern_server.shibing.controller;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Student;
import intern_server.shibing.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/4 15:07
 * 管理学生信息
 */
@RestController
@RequestMapping("/auth/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    /**
     * 查询全部学生信息
     * @param page
     * @param pageSize
     * @param fuzzy
     * @return
     */
    @GetMapping("/fetchStudentInfoData")
    public PageInfo fetchStudentInfoData(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) String fuzzy,
     @RequestParam(required = false) String roleName,@RequestParam(required = false) String teacherNumber
    ){
         return studentService.selectStudentInfoData(page,pageSize,fuzzy,roleName,teacherNumber);
    }

    /**
     * 创建学生信息
     * @param student
     * @return
     */

    @PostMapping("/addStudentInfo")
    public Map<String,Object> addStudentInfo(@RequestBody Student student){
        return studentService.addStudentInfo(student);
    }

    @PutMapping("/updateStudentInfo")
    public Map<String,Object> updateStudentInfo(@RequestBody Student student){
        return studentService.updateStudentInfo(student);
    }

    @DeleteMapping("/deleteStudentInfo/{ids}")
    public Map<String,Object> deleteStudentInfo(@PathVariable String ids){
        return studentService.deleteStudentInfo(ids);
    }
}
