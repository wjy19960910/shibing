package intern_server.shibing.dao;

import intern_server.shibing.data.po.Guid;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @Author: wangjingyuan
 * @Date: 2020/3/18 14:19
 */
@Repository
public interface GuidDao extends Mapper<Guid> {

    List<Guid> selectDataInfo(@Param("teacherNumber") String teacherNumber,@Param("gTitle") String gTitle,@Param("gDate") String gDate);

    Guid selectDataInfoById(String id);

    void deleteDataInfoByIds(String[] ids);
}
