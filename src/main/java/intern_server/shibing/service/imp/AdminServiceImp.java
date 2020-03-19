package intern_server.shibing.service.imp;


import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import intern_server.shibing.constant.Constants;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.dao.CompanyDao;
import intern_server.shibing.dao.StudentDao;
import intern_server.shibing.dao.TeacherDao;
import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.po.Teacher;
import intern_server.shibing.service.AdminService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    private AuthUserDao authUserDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private CompanyDao companyDao;


    @Override
    public PageInfo fetchUserDataInfo(Integer page, Integer pageSize, String fuzzy) {
        PageHelper.startPage(page,pageSize);
        AuthUser authUser = new AuthUser();
        if(!StringUtils.isEmpty(fuzzy)){
            authUser.setFuzzy(fuzzy);
        }
        List<AuthUser> authUserList = authUserDao.selectUserInfoData(authUser);
        if(authUserList.size()>0 && authUserList !=null){
            for (AuthUser a:authUserList
                 ) {
                if(!StringUtils.isEmpty(a.getPassword())){
                    a.setPasswordChange(SecureUtil.des(Constants.KEY).decryptStr(a.getPassword()));
                }
                if(a.getRoleId()!=null){
                    if(a.getRoleId() ==1){
                        a.setRoleIdName("学生用户");
                    }
                    if(a.getRoleId() ==2){
                        a.setRoleIdName("管理员");
                    }
                    if(a.getRoleId() ==3){
                        a.setRoleIdName("老师用户");
                    }
                    if(a.getRoleId() ==4){
                        a.setRoleIdName("企业用户");
                    }

                }
            }
        }
        return new PageInfo<>(authUserList);
    }

    @Override
    public Map<String, Object> getUserInfo(String id) {
        Map<String,Object> map = new HashMap<>();
        AuthUser authUser=authUserDao.getUserInfo(id);
        map.put("userInfos",authUser);
        return map;
    }

    @Override
    public Map<String, Object> removeUserInfo(String id) {
        Map<String,Object> resultMap = new HashMap<>();
        if(!StringUtils.isEmpty(id)){
            AuthUser authUser=authUserDao.getUserInfo(id);
            if(authUser != null){
                if(!StringUtils.isEmpty(authUser.getStudentNumber())){
                    studentDao.updateStudentState("0000",authUser.getStudentNumber());
                }
                if(!StringUtils.isEmpty(authUser.getTeacherNumber())){
                    teacherDao.updateTeacherState("0000",authUser.getTeacherNumber());
                }
                if(StringUtils.isEmpty(authUser.getCompanyNumber())){
                    companyDao.updateCompanyState("0000",authUser.getCompanyNumber());
                }
                authUserDao.deleteByPrimaryKey(authUser.getId());
                resultMap.put("code", "200");
                resultMap.put("msg", "删除用户成功");
                resultMap.put("level", "success");
            }


        }
        return resultMap;
    }
}
