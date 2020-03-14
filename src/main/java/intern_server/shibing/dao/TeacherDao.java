package intern_server.shibing.dao;

import intern_server.shibing.data.po.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface TeacherDao extends Mapper<Teacher> {
    void updateTeacherState(@Param("state") String state,@Param("teacherNumber") String teacherNumber);

    List<Teacher> selectTeacherInfoData(Teacher teacher);

    Teacher selectTeacherInfoById(String id);
}
