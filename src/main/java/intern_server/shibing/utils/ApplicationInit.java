package intern_server.shibing.utils;

import intern_server.shibing.constant.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ApplicationInit implements ApplicationRunner {

    @Resource
    RedisUtil redisUtil;

    @Value("${noticeType.show}")
    private Boolean noticeType;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisUtil.set("0001", "通知");
        redisUtil.set("0002", "公告");
        redisUtil.set(Constants.NOTICE_TYPE, noticeType);
    }
}
