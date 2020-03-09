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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.IOException;
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
     * @param
     * @return
     */
    @PostMapping("/login")
    public Map<String,Object> userLogin(@RequestBody AuthUser authUser,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();
        if(authUser!=null) {
            AuthUser authUser1 = authUserService.userLogin(authUser);
            if (authUser1 != null) {
                HttpSession session=request.getSession();
                session.setAttribute("userId",authUser1.getId());
                result.put("code", "200");
                result.put("msg", "登录成功");
                result.put("token", JwtUtil.getToken(authUser1));
                result.put("level", "success");
            }else{
                result.put("code", "9999");
                result.put("msg", "用户名或密码错误！");
                result.put("level", "error");
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
    @PutMapping("/updateMineInfo")
    public Map<String,Object> modifyUserInfo(@RequestBody AuthUser authUser){

        return authUserService.modifyUserInfo(authUser);
    }

    /**
     * 修改用户密码
     * @param authUser
     * @return
     */
    @PutMapping("/modifyPsd")
    public Map<String,Object> modifyPsd(@RequestBody AuthUser authUser){
        return authUserService.modifyPsd(authUser);
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @GetMapping("/getUserInfo/{token}")
    public Map<String,Object> getUserInfo( @PathVariable String token) {

        return authUserService.getUserInfo(token);
    }

    /**
     *上传用户头像
     */
    @PostMapping(value = "/saveImage")
    public Map<String,Object> saveImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String fileName = file.getOriginalFilename();
      System.out.println(fileName);
        System.out.println(file);
        return authUserService.saveImage(file,request);
    }


}
