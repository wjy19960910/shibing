package intern_server.shibing.service.imp;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.service.AdminService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    private AuthUserDao authUserDao;


    @Override
    public PageInfo fetchUserDataInfo(Integer page, Integer pageSize, String fuzzy) {
        PageHelper.startPage(page,pageSize);
        AuthUser authUser = new AuthUser();
        if(!StringUtils.isEmpty(fuzzy)){
            authUser.setFuzzy(fuzzy);
        }
        List<AuthUser> authUserList = authUserDao.selectUserInfoData(authUser);
        return new PageInfo<>(authUserList);
    }
}
