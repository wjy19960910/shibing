package intern_server.shibing.service;

import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.vo.AuthUserVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface AuthUserService {
    AuthUser userLogin(AuthUser authUser);
    Map<String, Object> registerUser(AuthUser authUser);
    Map<String,Object> modifyPsd(AuthUser authUser);
    Map<String,Object> getUserInfo(String token);

    Map<String, Object> saveImage(MultipartFile file, HttpServletRequest request) throws IOException;

    Map<String, Object> modifyUserInfo(AuthUser authUser);
}
