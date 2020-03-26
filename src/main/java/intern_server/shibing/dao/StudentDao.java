package intern_server.shibing.dao;

import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.po.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StudentDao extends Mapper<Student> {
    void updateStudentState(@Param("state") String state, @Param("studentNumber") String studentNumber);

    List<Student> selectStudentInfoData(Student student);

    Student selectStudentInfoById(@Param("studentNumber") String studentNumber);

    void deleteStudentInfoByIds(String[] ids);

    Student selectStudentInfoByKey(String id);

    List<Student> selectStudentInfoAll(@Param("studentName") String studentName, @Param("studentClass") String studentClass, @Param("studentNumber") String studentNumber);

    void updateStudentStateById(@Param("id") String id, @Param("state") String state,@Param("teacherNumber") String teacherNumber);

    List<Student> selectStudentInfoByTeacherNumber(@Param("teacherNumber") String teacherNumber);
}
