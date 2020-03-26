package intern_server.shibing.service;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Report;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/19 15:39
 */
public interface ReportService {
    Map<String, Object> createReport(Report report);

    PageInfo fetchReportInfo(Integer page, Integer pageSize, String studentNumber, String teacherNumber, String reportName, String reportDate);

    Map<String, Object> getReportInfoById(String id);

    Map<String, Object> removeReportInfo(String[] ids);

    Map<String, Object> updateReportInfo(Report report);

    Map<String, Object> updateInfoById(String id, String changeType,String dismissReason);
}
