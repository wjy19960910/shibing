package intern_server.shibing.interceptor;

import intern_server.common.annotaion.LoginRequired;
import intern_server.common.utils.SessionUtil;
import intern_server.shibing.constant.Constants;
import intern_server.shibing.data.po.UserSessionVO;
import intern_server.shibing.enums.RoleEnum;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader(Constants.AUTHENTICATION);
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有LoginRequired注释，没有有则跳过认证
        if (!method.isAnnotationPresent(LoginRequired.class)) {
            return true;
        }

        LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
        if (loginRequired.required()) {
            // 执行认证
            if (token == null) {
             return false;
            }

            RoleEnum role = loginRequired.role();
            if (role == RoleEnum.USER) {
                return true;
            }

            if (role == RoleEnum.ADMIN) {
                UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
                if (role != RoleEnum.getEnumTypeMap().get(userSessionInfo.getRoleId())) {
                 //TODO
                }
            }

            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
