package intern_server.shibing.controller;

import intern_server.shibing.data.po.SysDict;
import intern_server.shibing.service.CommonService;
import intern_server.shibing.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/8 21:46
 */
@RestController
public class CommonController {

    @Resource
    RedisUtil redisUtil;

    @Autowired
    private CommonService commonService;

    @RequestMapping("/auth/redis/data/{key}")
    public Object initRedisData(@PathVariable String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        return redisUtil.get(key);
    }

    @GetMapping("/common/getSysDict")
    public List<SysDict> getSysDict() {
        return commonService.getSysDict();
    }

    public Map<String,Object> createSysDict(@RequestBody SysDict sysDict){
        return commonService.createSysDict(sysDict);

    }
}
