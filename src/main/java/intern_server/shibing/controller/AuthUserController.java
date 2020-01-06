package intern_server.shibing.controller;

import intern_server.common.annotaion.LoginRequired;
import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.vo.AuthUserVO;
import intern_server.shibing.enums.RoleEnum;
import intern_server.shibing.service.AuthUserService;
import intern_server.shibing.utils.JwtUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;

    /**
     * 登录
     * @param authUser
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Map<String,Object> userLogin(@RequestBody AuthUser authUser, HttpSession session){
        Map<String,Object> result = new HashMap<>();
        if(authUser!=null) {
            AuthUser authUser1 = authUserService.userLogin(authUser);
            if (authUser1 != null) {
                result.put("code", 200);
                result.put("msg", "登录成功");
                result.put("token", JwtUtil.getToken(authUser1));
                result.put("level", "success");
            }
        }
        return result;
    }

    /**
     * 注册
     * @param authUser
     * @return
     */
    @PostMapping("/register")
    public Map<String,Object> registerUser(@RequestBody AuthUser authUser){
        return authUserService.registerUser(authUser);
    }



    /**
     * 修改用户信息
     * @return
     */
    public Map<String,String> modifyUserInfo(){
        return null;
    }


    @GetMapping("/getUserInfo/{token}")
    public Map<String,Object> getUserInfo( @PathVariable String token) {
        System.out.println(token);
        return null;
    }

}
