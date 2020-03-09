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
}
