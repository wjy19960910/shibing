package intern_server.shibing.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import intern_server.shibing.constant.Constants;
import intern_server.shibing.data.po.AuthUser;

import java.util.Date;

public class JwtUtil {

    public static String getToken(AuthUser user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getSocialId(),user.getAvatar()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

}
