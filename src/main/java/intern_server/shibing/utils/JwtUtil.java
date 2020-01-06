package intern_server.shibing.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import intern_server.shibing.constant.Constants;
import intern_server.shibing.data.po.AuthUser;

import java.util.Date;

public class JwtUtil {

    public static String getToken(AuthUser authUserVO) {
        String sign = authUserVO.getPassword();
        return JWT.create().withExpiresAt(new Date(System.currentTimeMillis()+ Constants.EXPIRE_TIME))
                .sign(Algorithm.HMAC256(sign));
    }

}
