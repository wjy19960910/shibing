package intern_server.shibing.service;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Student;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/4 15:17
 */
public interface StudentService {
    /**
     * 查询全部学生信息
     * @param page
     * @param pageSize
     * @param fuzzy
     * @return
     */
    PageInfo selectStudentInfoData(Integer page, Integer pageSize, String fuzzy,String roleName,String teacherNumber);

    Map<String, Object> addStudentInfo(Student student);

    Map<String, Object> updateStudentInfo(Student student);

    Map<String, Object> deleteStudentInfo(String ids);
}
