package intern_server.shibing.service;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Teacher;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/4 15:19
 */
public interface AdminService {

    PageInfo fetchUserDataInfo(Integer page, Integer pageSize, String fuzzy);
}
