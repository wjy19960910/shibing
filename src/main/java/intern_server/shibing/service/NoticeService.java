package intern_server.shibing.service;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Notice;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/6 16:56
 */
public interface NoticeService {
    PageInfo selectNoticeInfoData(Integer page, Integer pageSize, String fuzzy, String roleName, String teacherNumber, String companyNumber);

    PageInfo selectNoticeInfoDataAll(Integer page, Integer pageSize, String fuzzy);

    Map<String, Object> insertNoticeInfo(Notice notice);

    Map<String, Object> editNoticeInfo(Notice notice);

    Map<String, Object> removeNoticeInfo(String[] ids);

    Map<String, Object> getNoticeInfoById(String id);
}
