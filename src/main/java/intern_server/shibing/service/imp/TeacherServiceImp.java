package intern_server.shibing.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.dao.TeacherDao;
import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.po.Teacher;
import intern_server.shibing.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class TeacherServiceImp implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private AuthUserDao authUserDao;

    @Override
    public PageInfo fetchTeacherInfoData(Integer page, Integer pageSize, String fuzzy) {
        PageHelper.startPage(page,pageSize);
        Teacher teacher = new Teacher();
        if(!StringUtils.isEmpty(fuzzy)){
            teacher.setFuzzy(fuzzy);
        }
        List<Teacher> teacherList = teacherDao.selectTeacherInfoData(teacher);
        if(teacherList.size()>0){
            for (Teacher teacher1:teacherList
                 ) {
                if(teacher1.getState().contains("0000")){
                    teacher1.setStateName("未注册");
                }
                if(teacher1.getState().contains("1111")){
                    teacher1.setStateName("已注册");
                }
            }
        }
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
        List<String> teacherName = new ArrayList<>();
        if(ids.length>0){
            for(int i=0;i<ids.length;i++){
               Teacher teacher=teacherDao.selectTeacherInfoById(ids[i]);
                if(!StringUtils.isEmpty(teacher.getState()) && teacher.getState().contains("1111")){
                    teacherName.add(teacher.getTeacherName());
                }
            }
            if(teacherName.size()>0 && teacherName!=null){
                map.put("code", "9999");
                map.put("msg", String.join(",",teacherName)+"用户已被使用，不可删除");
                map.put("level", "warning");
                return map;
            }else {
                teacherDao.deleteTeacherInfoByIds(ids);
                map.put("code", "200");
                map.put("msg", "删除成功");
                map.put("level", "success");
            }
        }

        return map;
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
