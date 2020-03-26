package intern_server.shibing.service;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.InternshipInfo;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/18 14:20
 */
public interface InternshipService {
    Map<String, Object> addDataInfo(InternshipInfo internshipInfo) throws Exception;

    PageInfo fetchDataInfo(Integer page, Integer pageSize, String companyName, String postalCode, String studentNumber, String teacherNumber);

    Map<String, Object> getDataInfoById(String id);

    Map<String, Object> updateDataInfo(InternshipInfo internshipInfo);

    Map<String, Object> updateDataInfoById(String id, String updateType);

    Map<String, Object> getDataInfoByIdAndNumber( String studentNumber);
}
