package intern_server.shibing.dao;


import intern_server.shibing.data.po.SysDict;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @author Administrator
 */
@Repository
public interface SysDictDao extends Mapper<SysDict> {


    List<SysDict> listSysDictInfo();
}
