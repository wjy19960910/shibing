package intern_server.shibing.controller;

import intern_server.shibing.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/8 21:46
 */
@RestController
public class CommonController {

    @Resource
    RedisUtil redisUtil;

    @RequestMapping("/auth/redis/data/{key}")
    public Object initRedisData(@PathVariable String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        return redisUtil.get(key);
    }
}
