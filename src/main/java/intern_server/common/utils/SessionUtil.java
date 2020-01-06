package intern_server.common.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import intern_server.common.config.BeanTool;
import intern_server.shibing.constant.Constants;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.po.UserSessionVO;
import intern_server.shibing.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author:byteblogs
 * @Date:2018/09/27 12:52
 */
public class SessionUtil {

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

        // 获取 token 中的 user id
        AuthUser authUser = null;
        try {
            authUser = JsonUtil.parseObject(JWT.decode(token).getAudience().get(0), AuthUser.class);
        } catch (JWTDecodeException j) {
            //TODO
        }

        AuthUserDao userDao = BeanTool.getBean(AuthUserDao.class);

        AuthUser user = userDao.selectByPrimaryKey(authUser.getId());
        if (user == null) {
            //TODO
        }

        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            //TODO
        }



        UserSessionVO userSessionVO = new UserSessionVO();
        userSessionVO.setName(user.getName()).setSocialId(user.getSocialId()).setRoleId(user.getRoleId()).setId(user.getId());
        return userSessionVO;
    }

}
