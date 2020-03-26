package intern_server.shibing.service;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Guid;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/4 15:19
 */
public interface GuidService {


    Map<String, Object> createData(Guid guid);


    PageInfo fetchDataInfo(Integer page, Integer pageSize, String gTitle, String gDate);

    Map<String, Object> getDataInfoById(String id);

    Map<String, Object> updateDataInfo(Guid guid);

    Map<String, Object> removeDataInfo(String[] ids);
}
