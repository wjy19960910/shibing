package intern_server.common.utils;


import com.auth0.jwt.JWT;
import intern_server.shibing.constant.Constants;

import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.po.UserSessionVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.spi.FileSystemProvider;
import java.util.List;
import java.util.Optional;

/**
 * @Author:byteblogs
 * @Date:2018/09/27 12:52
 */
@Component
public class SessionUtil {

    @Autowired
    private AuthUserDao authUserDao;
    private static AuthUserDao authUserDaos;

    @PostConstruct
    public void init(){
        authUserDaos=authUserDao;
    }

    /**
     * 获取用户Session信息
     * @return
     */
    public static UserSessionVO getUserSessionInfo() {

        // 获取请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取请求头Token值
        String token = Optional.ofNullable(request.getHeader(Constants.AUTHENTICATION)).orElse(null);

        if (StringUtils.isBlank(token)) {
            return null;
        }
        String socialId=null;
        String avatar = null;
        socialId= JWT.decode(token).getAudience().get(0);
        avatar=JWT.decode(token).getAudience().get(1);
        System.out.println(avatar);
        UserSessionVO userSessionVO = new UserSessionVO();
        userSessionVO.setSocialId(socialId);
        userSessionVO.setAvatar(avatar);
        return userSessionVO;
    }

}
