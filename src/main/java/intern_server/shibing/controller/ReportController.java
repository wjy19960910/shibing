package intern_server.shibing.controller;

import com.github.pagehelper.PageInfo;
import intern_server.common.utils.AjaxResult;
import intern_server.common.utils.SessionUtil;
import intern_server.shibing.dao.ReportDao;
import intern_server.shibing.data.po.Report;
import intern_server.shibing.data.po.Student;
import intern_server.shibing.data.po.UserSessionVO;
import intern_server.shibing.service.ReportService;
import intern_server.shibing.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/19 15:36
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportDao reportDao;


    @PostMapping("/createReport")
    public Map<String,Object> createReport(@RequestBody Report report){
        return reportService.createReport(report);
    }

    @GetMapping("/fetchReportInfo")
    public PageInfo fetchReportInfo(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                    @RequestParam(required = false) String studentNumber, @RequestParam(required = false) String teacherNumber,
                                    @RequestParam(required = false) String reportName, @RequestParam(required = false) String reportDate){
              return reportService.fetchReportInfo(page,pageSize,studentNumber,teacherNumber,reportName,reportDate);
    }

    @GetMapping("/getReportInfoById/{id}")
    public Map<String,Object> getReportInfoById(@PathVariable String id){
        return reportService.getReportInfoById(id);
    }

    @DeleteMapping("/removeReportInfo/{ids}")
    public Map<String,Object> removeReportInfo(@PathVariable String[] ids){
        return reportService.removeReportInfo(ids);
    }

    @PutMapping("/updateReportInfo")
    public Map<String,Object> updateReportInfo(@RequestBody Report report){

        return reportService.updateReportInfo(report);
    }

    @GetMapping("/updateInfoById/{id}/{changeType}/{dismissReason}")
    public Map<String,Object> updateInfoById(@PathVariable String id,@PathVariable String changeType,@PathVariable String dismissReason){

        return reportService.updateInfoById(id,changeType,dismissReason);
    }

    /**
     * 导出
     */
    @GetMapping ("/exportReport")
    public AjaxResult export(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                             @RequestParam(required = false) String studentNumber, @RequestParam(required = false) String teacherNumber,
                             @RequestParam(required = false) String reportName, @RequestParam(required = false) String reportDate)
    {
        Map<String,Object> stringObjectMap=new HashMap<>();
        List<Report> list = reportDao.selectReportInfo(studentNumber,teacherNumber,reportName,reportDate,"asc");
        ExcelUtil<Report> util = new ExcelUtil<Report>(Report.class);
        AjaxResult ajaxResult=util.exportExcel(list, "cat");
        System.out.println(ajaxResult);
        return ajaxResult;
    }

}
