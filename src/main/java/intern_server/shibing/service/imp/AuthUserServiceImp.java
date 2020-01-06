package intern_server.shibing.service.imp;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import intern_server.shibing.constant.Constants;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.vo.AuthUserVO;
import intern_server.shibing.enums.RoleEnum;
import intern_server.shibing.service.AuthUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthUserServiceImp implements AuthUserService {
    @Autowired
    private AuthUserDao authUserDao;


    /**
     * 用户登录
     * @param authUser
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AuthUser userLogin(AuthUser authUser) {


        return authUserDao.getUserLogin(authUser.getSocialId(), SecureUtil.des(Constants.KEY).encryptHex(authUser.getPassword()));
    }


    /**
     * 用户注册
     * @param authUser
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> registerUser(AuthUser authUser) {
          Map<String,Object> result = new HashMap<>();
          if(authUser!=null && !StringUtils.isEmpty(authUser.getSocialId())){
              int count=authUserDao.selectCount(authUser);
              if(count>0){
                  result.put("code", Constants.ALREADY_EXISTS);
                  result.put("msg", "账号已存在，请重新输入");
                  result.put("level", "error");
              }else {
                  AuthUser authUser1=new AuthUser();
                  authUser1.setSocialId(authUser.getSocialId());
                  authUser1.setName(authUser1.getName());
                  authUser1.setRoleId(RoleEnum.USER.getRoleId());
                  String password=authUser.getPassword();
                  authUser1.setPassword(SecureUtil.des(Constants.KEY).encryptHex(password));
                  authUser1.setCreateTime(LocalDateTime.now());
                  authUserDao.insert(authUser1);
              }
          }
          return result;
    }

    @Override
    public Map<String, String> modifyPsd(String socialId, String oldPassword, String newPassword) {
        return null;
    }

    /**
     * 获取用户信息
     * @param authUser
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(AuthUserVO authUser) {
        return null;
    }
}
