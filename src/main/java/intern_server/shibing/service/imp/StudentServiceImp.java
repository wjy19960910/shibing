package intern_server.shibing.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import intern_server.shibing.dao.StudentDao;
import intern_server.shibing.data.po.Student;
import intern_server.shibing.enums.RoleEnum;
import intern_server.shibing.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImp implements StudentService {

    @Autowired
    private StudentDao studentDao;


    /**
     * 分页查询全部学生信息
     * @param page
     * @param pageSize
     * @param fuzzy
     * @return
     */
    @Override
    public PageInfo selectStudentInfoData(Integer page, Integer pageSize, String fuzzy,String roleName,String teacherNumber) {
        PageHelper.startPage(page,pageSize);

            Student student=new Student();
            if(!StringUtils.isEmpty(fuzzy)){
                student.setFuzzy(fuzzy);
            }
            if(!StringUtils.isEmpty(teacherNumber)){
                student.setTeacherNumber(teacherNumber);
            }
            List<Student> studentList=studentDao.selectStudentInfoData(student);
            return new PageInfo<>(studentList);

    }

    @Override
    public Map<String, Object> addStudentInfo(Student student) {
         Map<String,Object> resultMap = new HashMap<>();
        //管理员
        if(student!=null){
            if(student.getRoleName().equals(RoleEnum.ADMIN.getRoleName())){
                studentDao.insert(student);
                resultMap.put("code", "200");
                resultMap.put("msg", "添加用户成功");
                resultMap.put("level", "success");
            }
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> updateStudentInfo(Student student) {
        Map<String,Object> resultMap = new HashMap<>();
        if(student!=null){
            //管理员
            if(student.getRoleName().equals(RoleEnum.ADMIN.getRoleName())){
                studentDao.updateByPrimaryKey(student);
                resultMap.put("code", "200");
                resultMap.put("msg", "修改用户成功");
                resultMap.put("level", "success");

            }else if(student.getRoleName().equals(RoleEnum.TEACHER.getRoleName())){
                studentDao.updateByPrimaryKey(student);
                resultMap.put("code", "200");
                resultMap.put("msg", "修改用户成功");
                resultMap.put("level", "success");
            }else{
                resultMap.put("code", "9999");
                resultMap.put("msg", "修改用户失败");
                resultMap.put("level", "error");
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteStudentInfo(String ids) {
        return null;
    }
}
