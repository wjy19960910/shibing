package intern_server.shibing.dao;

import intern_server.shibing.data.po.Notice;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @author Administrator
 */
@Repository
public interface NoticeDao extends Mapper<Notice> {

    List<Notice> selectNoticeInfoData(Notice notice);

    List<Notice> selectNoticeInfoDataAll(Notice notice);

    void deleteNoticeInfoByIds(String[] id);
}
