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

import java.util.*;

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
            if(!StringUtils.isEmpty(roleName)){
                if(roleName.equals(RoleEnum.ADMIN.getRoleName())){
                    student.setTeacherNumber("");
                }
            }
            List<Student> studentList=studentDao.selectStudentInfoData(student);
            if(studentList!=null && studentList.size()>0){
                for (Student s:studentList
                     ) {
                    if(!StringUtils.isEmpty(s.getState())){
                        if(s.getState().contains("0000")){
                            s.setStateName("未注册");
                        }
                        if(s.getState().contains("1111")){
                            s.setStateName("已注册");
                        }
                        if(s.getState().contains("2222")){
                            s.setStateName("已被管理");
                        }
                    }
                }
            }
            return new PageInfo<>(studentList);

    }

    @Override
    public Map<String, Object> addStudentInfo(Student student) throws Exception {
         Map<String,Object> resultMap = new HashMap<>();
        //管理员
        if(student!=null && !StringUtils.isEmpty(student.getStudentNumber())){
            if(student.getRoleName().equals(RoleEnum.ADMIN.getRoleName())){
                Student student1=studentDao.selectStudentInfoById(student.getStudentNumber());
                if(student1!=null){
                    resultMap.put("code", "3000");
                    resultMap.put("msg", "该学号已存在，请核对！！！");
                    resultMap.put("level", "warning");
                    return resultMap;
                }
                student.setId(UUID.randomUUID().toString());
                student.setManagerTime(new Date());
                //0000 未注册  1111已注册 9999已删除
                student.setState("0000");
                studentDao.insert(student);
                resultMap.put("code", "200");
                resultMap.put("msg", "添加学生信息成功！！！");
                resultMap.put("level", "success");
            }

        }else {
            throw new Exception("学生信息为空");
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> updateStudentInfo(Student student) {
        Map<String,Object> resultMap = new HashMap<>();
        if(student!=null && !StringUtils.isEmpty(student.getRoleName())){
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
        }else {
            resultMap.put("code", "9999");
            resultMap.put("msg", "修改用户失败");
            resultMap.put("level", "error");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteStudentInfo(String ids) {
        return null;
    }

    @Override
    public Map<String, Object> removeStudentInfo(String[] ids) {
        Map<String,Object> map = new HashMap<>();
        List<String> deleteName = new ArrayList<>();
        if(ids.length>0){
            for (int i=0;i<ids.length;i++){
                Student student = studentDao.selectStudentInfoByKey(ids[i]);
                if(!StringUtils.isEmpty(student.getStudentNumber())){
                    if(!StringUtils.isEmpty(student.getState() )&& student.getState().contains("1111")){
                        deleteName.add(student.getStudentNumber());
                    }

                }
            }
            if(deleteName.size()>0 && deleteName!=null){
                map.put("code", "9999");
                map.put("msg", String.join(",",deleteName)+"用户已被使用，不可删除");
                map.put("level", "warning");
                return map;
            }else {
                studentDao.deleteStudentInfoByIds(ids);
                map.put("code", "200");
                map.put("msg", "删除成功");
                map.put("level", "success");
            }

        }

        return map;
    }

    @Override
    public Map<String, Object> getStudentInfoById(String id) {
        Map<String,Object> map = new HashMap<>();
        Student student = studentDao.selectStudentInfoByKey(id);
        map.put("studentInfo",student);
        return map;
    }

    @Override
    public PageInfo selectStudentInfoAll(Integer page, Integer pageSize, String studentName, String studentClass, String studentNumber) {
        PageHelper.startPage(page, pageSize);
        List<Student> studentList=studentDao.selectStudentInfoAll(studentName,studentClass,studentNumber);
        return new PageInfo<>(studentList);
    }

    @Override
    public Map<String, Object> updateStudentState(String id,String teacherNumber) {
        Map<String,Object> map = new HashMap<>();
        String state="2222";
        studentDao.updateStudentStateById(id,state,teacherNumber);
        map.put("code", "200");
        map.put("msg", "添加成功");
        map.put("level", "success");
        return map;
    }

    @Override
    public Map<String, Object> removeStudentState(String id) {
        Map<String,Object> map = new HashMap<>();
        String state="1111";
        String teacherNumber="";
        studentDao.updateStudentStateById(id,state,teacherNumber);
        map.put("code", "200");
        map.put("msg", "移除成功");
        map.put("level", "success");
        return map;
    }

    @Override
    public Map<String, Object> getStudentInfoByNumber(String id) {
        Map<String,Object> map = new HashMap<>();
        Student student = studentDao.selectStudentInfoById(id);
        map.put("studentInfoByNumber",student);
        return map;
    }
}
