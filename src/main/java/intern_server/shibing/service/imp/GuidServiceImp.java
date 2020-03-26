package intern_server.shibing.service.imp;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import intern_server.common.utils.SessionUtil;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.dao.GuidDao;
import intern_server.shibing.dao.StudentDao;
import intern_server.shibing.dao.TeacherDao;
import intern_server.shibing.data.po.*;
import intern_server.shibing.service.GuidService;

import intern_server.shibing.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.xml.crypto.Data;
import java.util.*;


@Service
public class GuidServiceImp implements GuidService {
    @Autowired
    private AuthUserDao authUserDao;
    @Autowired
    private GuidDao guidDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;

    @Override
    public Map<String, Object> createData(Guid guid) {
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        String socialId = userSessionInfo.getSocialId();
        Map<String,Object> resultMap = new HashMap<>();
        if(guid!=null && !StringUtils.isEmpty(guid.getgDate())){
            Example example = new Example(Guid.class);
            example.createCriteria().andEqualTo("gDate",guid.getgDate()).andEqualTo(guid.getTeacherNumber());
            List<Guid> list=guidDao.selectByExample(example);
            if(list.size() > 0 && list != null){
                if(!org.apache.commons.lang3.StringUtils.isEmpty(list.get(0).getgDate()) && list.get(0).getgContent()!= null){
                    resultMap.put("code", "3000");
                    resultMap.put("msg", "该日期的指导记录已经存在，请重新选择日期");
                    resultMap.put("level", "warning");
                    return resultMap;
                }
                if(list.get(0).getState().contains("8888")){
                    guid.setId(list.get(0).getId());
                    guid.setState("0000");
                    guid.setManageTime(new Date());
                    guidDao.updateByPrimaryKey(guid);
                    if(!StringUtils.isEmpty(socialId)){
                        updateUserInfo(socialId);
                    }
                    resultMap.put("code", "200");
                    resultMap.put("msg", "填写指导记录成功！！！!");
                    resultMap.put("level", "success");
                    return resultMap;

                }
            }
        }

        if(!StringUtils.isEmpty(socialId)){
            AuthUser authUser = authUserDao.getUserInfo(socialId);
            if(!StringUtils.isEmpty(authUser.getTeacherNumber())){
               guid.setTeacherNumber(authUser.getTeacherNumber());
                guid.setManageUser(authUser.getTeacherNumber());
            }
        }
        //0000 人工添加，8888 系统生成
        guid.setState("0000");
        guid.setManageTime(new Date());
        guid.setId(UUID.randomUUID().toString());
        guidDao.insert(guid);
        if(!StringUtils.isEmpty(socialId)){
            updateUserInfo(socialId);
        }
        resultMap.put("code", "200");
        resultMap.put("msg", "填写指导记录成功！！！");
        resultMap.put("level", "success");
        return resultMap;
    }

    @Override
    public PageInfo fetchDataInfo(Integer page, Integer pageSize, String gTitle, String gDate) {
        PageHelper.startPage(page, pageSize);
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        String socialId = userSessionInfo.getSocialId();
        String teacherNumber="";
        if(!StringUtils.isEmpty(socialId)){
            AuthUser authUser = authUserDao.getUserInfo(socialId);
            if(!StringUtils.isEmpty(authUser.getTeacherNumber())){
                teacherNumber=authUser.getTeacherNumber();
            }
        }
        List<Guid> list = guidDao.selectDataInfo(teacherNumber,gTitle,gDate);
        if(!CollectionUtils.isEmpty(list)){
            for (Guid g:list
                 ) {
                if(!StringUtils.isEmpty(g.getStudentId()) && g.getStudentId().contains(",")){
                    List<String> idList = new ArrayList<>();
                    List<String> studentName = new ArrayList<>();
                    String[] arry =g.getStudentId().split(",");
                    if(arry.length>0){
                        for (int i=0;i<arry.length;i++){
                            idList.add(arry[i]);
                            Student student=studentDao.selectStudentInfoById(arry[i]);
                            if(student !=null && !StringUtils.isEmpty(student.getStudentName())){
                                studentName.add(student.getStudentName());
                            }
                        }
                    }
                    g.setStudentIdList(idList);
                    g.setgStudentName(String.join(",",studentName));
                }else if(!StringUtils.isEmpty(g.getStudentId())){
                    List<String> idList = new ArrayList<>();
                    idList.add(g.getStudentId());
                    g.setStudentIdList(idList);
                    Student student=studentDao.selectStudentInfoById(g.getStudentId());
                    if(student !=null && !StringUtils.isEmpty(student.getStudentName())){
                        g.setgStudentName(student.getStudentName());
                    }
                }

                if(!StringUtils.isEmpty(g.getTeacherNumber())){
                      Teacher teacher=teacherDao.selectTeacherInfoByNumber(g.getTeacherNumber());
                      if(!StringUtils.isEmpty(teacher.getTeacherName())){
                          g.setgTeacherName(teacher.getTeacherName());
                      }
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public Map<String, Object> getDataInfoById(String id) {
        Map<String,Object> map = new HashMap<>();
        Guid guid = guidDao.selectDataInfoById(id);
        if(guid !=null){
            if(!StringUtils.isEmpty(guid.getStudentId())){
                List<String> list = Arrays.asList(guid.getStudentId().split(","));
                guid.setStudentIdList(list);
            }
        }
        map.put("guidInfo",guid);
        return map;
    }

    @Override
    public Map<String, Object> updateDataInfo(Guid guid) {
        Map<String,Object> resultMap = new HashMap<>();
        if(guid!=null){
            guid.setManageTime(new Date());
            guidDao.updateByPrimaryKey(guid);
            resultMap.put("code", "200");
            resultMap.put("msg", "修改实习报告成功");
            resultMap.put("level", "success");
        }else {
            System.err.println("服务器错误");
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> removeDataInfo(String[] ids) {
        Map<String,Object> map = new HashMap<>();
        guidDao.deleteDataInfoByIds(ids);
        map.put("code", "200");
        map.put("msg", "删除成功");
        map.put("level", "success");
        return map;
    }


    /**
     * 通用处理逻辑
     * @param socialId
     */
    public void updateUserInfo(String socialId){
        List<String> list=new ArrayList<>();
        if(!org.apache.commons.lang3.StringUtils.isEmpty(socialId)){
            AuthUser authUser=authUserDao.getUserInfo(socialId);
            if(authUser !=null && !org.apache.commons.lang3.StringUtils.isEmpty(authUser.getTeacherNumber())){
                Example example = new Example(Guid.class);
                example.createCriteria().andEqualTo("teacherNumber",authUser.getTeacherNumber());
                List<Guid> guidList = guidDao.selectByExample(example);
                if(guidList.size()>0 && guidList!=null){
                    for (Guid r:guidList
                    ) {
                        if(r.getgContent() == null && org.apache.commons.lang3.StringUtils.isEmpty(r.getgContent())){
                            list.add("yes");
                        }
                    }
                }

                if(list.size()>0 && list!=null){
                    authUser.setYesOrNo("YES");
                    authUserDao.updateAuthUserById(authUser);
                }else {
                    authUser.setYesOrNo("NO");
                    authUserDao.updateAuthUserById(authUser);
                }
            }else {
                System.err.println("权限错误");
            }
        }
    }
}
