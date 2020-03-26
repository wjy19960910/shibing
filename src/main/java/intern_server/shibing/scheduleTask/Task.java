package intern_server.shibing.scheduleTask;

import intern_server.common.utils.DateUtils;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.dao.ReportDao;
import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.po.Report;
import intern_server.shibing.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import tk.mybatis.mapper.entity.Example;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/21 16:40
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class Task {
    @Autowired
    private AuthUserDao authUserDao;
    @Autowired
    private ReportDao reportDao;

    //3.添加定时任务
   // @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        List<AuthUser> authUserList = authUserDao.selectAll();
        List<AuthUser> studentlist = new ArrayList<>();
        List<AuthUser> teacherlist = new ArrayList<>();
        if(authUserList.size()>0 && authUserList!=null){
            for ( AuthUser a:authUserList
                 ) {
                if(!StringUtils.isEmpty(a.getStudentNumber())){
                    studentlist.add(a);
                }
                if(!StringUtils.isEmpty(a.getTeacherNumber())){
                    teacherlist.add(a);
                }
            }
        }
        if(studentlist !=null && studentlist.size()>0){
            for (AuthUser a: studentlist
                 ) {

                Report report = new Report();
                report.setId(UUID.randomUUID().toString());
                report.setReportDate(DateUtils.getDate());
                report.setStudentNumber(a.getStudentNumber());
                report.setReportState("8888");
                Example example = new Example(Report.class);
                example.createCriteria().andEqualTo("reportDate",report.getReportDate()).andEqualTo("studentNumber",a.getStudentNumber());
                List<Report> list = reportDao.selectByExample(example);
                if(list.size() == 0 ){
                    reportDao.insert(report);
                    a.setYesOrNo("YES");
                    authUserDao.updateByPrimaryKey(a);
                }else {
                    for (Report s:list
                         ) {
                        System.err.println("当天已经填写日志的学生:" +s.getStudentNumber()+"日期为"+s.getReportState());
                    }

                }
            }
        }
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
