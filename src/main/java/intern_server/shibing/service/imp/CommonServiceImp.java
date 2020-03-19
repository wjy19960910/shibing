package intern_server.shibing.service.imp;

import intern_server.shibing.dao.SysDictDao;
import intern_server.shibing.data.po.SysDict;
import intern_server.shibing.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/17 20:54
 */
@Service
public class CommonServiceImp implements CommonService {
    @Autowired
    private SysDictDao sysDictDao;

    @Override
    public List<SysDict> getSysDict() {
        return sysDictDao.listSysDictInfo();
    }

    @Override
    public Map<String, Object> createSysDict(SysDict sysDict) {
        Map<String,String> resultMap = new HashMap<>();
        Example example = new Example(SysDict.class);
        example.createCriteria().andEqualTo("state","0000")
                .andEqualTo("level",sysDict.getLevel());

        return null;
    }
}
