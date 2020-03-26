package intern_server.shibing.dao;

import intern_server.shibing.data.po.Report;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/19 15:38
 */
@Repository
public interface ReportDao extends Mapper<Report> {
    List<Report> selectReportInfo(@Param("studentNumber") String studentNumber,@Param("teacherNumber") String teacherNumber,@Param("reportName") String reportName, @Param("reportDate") String reportDate,@Param("sort") String sort);

    Report selectReportById(String id);

    void deleteReportInfoByIds(String[] ids);
}
