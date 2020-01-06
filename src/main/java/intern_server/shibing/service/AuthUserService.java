package intern_server.shibing.service;

import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.vo.AuthUserVO;

import java.util.Map;

public interface AuthUserService {
    AuthUser userLogin(AuthUser authUser);
    Map<String, Object> registerUser(AuthUser authUser);
    Map<String,String> modifyPsd(String socialId,String oldPassword,String newPassword);
    Map<String,Object> getUserInfo(AuthUserVO authUser);
}
