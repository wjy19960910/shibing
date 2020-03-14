package intern_server.shibing.service;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Teacher;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/4 15:19
 */
public interface TeacherService {
    PageInfo fetchTeacherInfoData(Integer page, Integer pageSize, String fuzzy);

    Map<String, Object> insertTeacherInfo(Teacher teacher);

    Map<String, Object> removeTeacherInfo(String[] ids);

    Map<String, Object> getTeacherInfoById(String id);

    Map<String, Object> editTeacherInfo(Teacher teacher);
}
