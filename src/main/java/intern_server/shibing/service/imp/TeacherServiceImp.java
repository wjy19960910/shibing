package intern_server.shibing.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import intern_server.shibing.dao.TeacherDao;
import intern_server.shibing.data.po.Teacher;
import intern_server.shibing.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherServiceImp implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public PageInfo fetchTeacherInfoData(Integer page, Integer pageSize, String fuzzy) {
        PageHelper.startPage(page,pageSize);
        Teacher teacher = new Teacher();
        if(!StringUtils.isEmpty(fuzzy)){
            teacher.setFuzzy(fuzzy);
        }
        List<Teacher> teacherList = teacherDao.selectTeacherInfoData(teacher);
        return new PageInfo<>(teacherList);
    }

    @Override
    public Map<String, Object> insertTeacherInfo(Teacher teacher) {
        Map<String,Object> map = new HashMap<>();
        teacher.setId(UUID.randomUUID().toString());
        teacher.setState("0000");
        teacher.setManageTime(new Date());
        teacherDao.insert(teacher);
        map.put("code", "200");
        map.put("msg", "新增教师成功！");
        map.put("level", "success");
        return map;
    }

    @Override
    public Map<String, Object> removeTeacherInfo(String[] ids) {
        Map<String,Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("msg", "删除教师信息成功！");
        map.put("level", "success");
        return null;
    }

    @Override
    public Map<String, Object> getTeacherInfoById(String id) {
        Map<String,Object> map = new HashMap<>();
        Teacher teacher = teacherDao.selectTeacherInfoById(id);
        map.put("teacherInfo",teacher);
        return map;
    }

    @Override
    public Map<String, Object> editTeacherInfo(Teacher teacher) {
        Map<String,Object> map = new HashMap<>();
        teacher.setManageTime(new Date());
        teacherDao.updateByPrimaryKey(teacher);
        map.put("code", "200");
        map.put("msg", "修改公告成功！");
        map.put("level", "success");
        return map;
    }
}
