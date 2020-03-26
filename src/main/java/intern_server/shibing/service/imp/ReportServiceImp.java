package intern_server.shibing.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import intern_server.common.utils.SessionUtil;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.dao.InternshipDao;
import intern_server.shibing.dao.ReportDao;
import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.po.InternshipInfo;
import intern_server.shibing.data.po.Report;
import intern_server.shibing.data.po.UserSessionVO;
import intern_server.shibing.service.ReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/19 15:41
 */
@Service
public class ReportServiceImp implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private InternshipDao internshipDao;
    @Autowired
    private AuthUserDao authUserDao;

    @Override
    public Map<String, Object> createReport(Report report) {
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        String socialId = userSessionInfo.getSocialId();
        Map<String,Object> resultMap = new HashMap<>();
        if(report !=null && !StringUtils.isEmpty(report.getReportDate())){
            Example example = new Example(Report.class);
            example.createCriteria().andEqualTo("reportDate",report.getReportDate()).andEqualTo(report.getStudentNumber());

            List<Report> list = reportDao.selectByExample(example);
            if(list.size()>0 && list !=null ){
                if(!StringUtils.isEmpty(list.get(0).getReportContent()) && list.get(0).getReportContent() != null){
                    resultMap.put("code", "3000");
                    resultMap.put("msg", "该日期的报告已经存在，请重新选择日期");
                    resultMap.put("level", "warning");
                    return resultMap;
                }
                if(list.get(0).getReportState().contains("8888")){
                    report.setId(list.get(0).getId());
                    report.setManageTime(new Date());
                    //0000 待审核，1111已审批，2222，已驳回，9999，已删除
                    report.setReportState("0000");
                    reportDao.updateByPrimaryKey(report);
                    if(!StringUtils.isEmpty(socialId)){
                        updateAuthUser(socialId);
                    }
                    resultMap.put("code", "200");
                    resultMap.put("msg", "填写实习报告成功！！！!");
                    resultMap.put("level", "success");
                    return resultMap;
                }

            }
            report.setId(UUID.randomUUID().toString());
            report.setManageTime(new Date());
            //0000 待审核，1111已审批，2222，已驳回，9999，已删除
            report.setReportState("0000");
            if(!StringUtils.isEmpty(report.getTeacherNumber())){
                report.setApproval(report.getTeacherNumber());
                report.setManageUser(report.getTeacherNumber());
            }
            if(!StringUtils.isEmpty(report.getStudentNumber())){
                report.setManageUser(report.getStudentNumber());
            }
            reportDao.insert(report);
            if(!StringUtils.isEmpty(socialId)){
                updateAuthUser(socialId);
            }
            resultMap.put("code", "200");
            resultMap.put("msg", "填写实习报告成功！！！");
            resultMap.put("level", "success");
        }

        return resultMap;
    }

    @Override
    public PageInfo fetchReportInfo(Integer page, Integer pageSize, String studentNumber, String teacherNumber, String reportName, String reportDate) {
        PageHelper.startPage(page, pageSize);
        String sort=null;
        if(StringUtils.isEmpty(teacherNumber)){
            sort="desc";
        }else {
            sort="asc";
        }
        List<Report> reportList = reportDao.selectReportInfo(studentNumber,teacherNumber,reportName,reportDate,sort);
        if(reportList.size()>0 && reportList != null){
            for (Report r:reportList
                 ) {
                if(!StringUtils.isEmpty(r.getReportState())){
                    String name="";
                    switch (r.getReportState()){
                        case "0000":
                            name="待审核";
                            break;
                        case "1111":
                            name="已审批";
                            break;
                        case "2222":
                            name="已驳回";
                            break;
                        default:
                            name="已删除";
                    }
                    r.setStateName(name);
                }
            }
        }
        return new PageInfo<>(reportList);
    }

    @Override
    public Map<String, Object> getReportInfoById(String id) {
        Map<String,Object> map = new HashMap<>();
        Report report = reportDao.selectReportById(id);
        if(report!=null && !StringUtils.isEmpty(report.getInternshipId())){
            Example example = new Example(InternshipInfo.class);
            example.createCriteria().andEqualTo("id",report.getInternshipId());
            List<InternshipInfo> list= internshipDao.selectByExample(example);
            if(list.size()>0 && list!=null){
                report.setInternshipIdName(list.get(0).getCompanyName());
            }

        }
        map.put("reportInfo",report);
        return map;
    }

    @Override
    public Map<String, Object> removeReportInfo(String[] ids) {
        Map<String,Object> map = new HashMap<>();
        reportDao.deleteReportInfoByIds(ids);
        map.put("code", "200");
        map.put("msg", "删除成功");
        map.put("level", "success");
        return map;
    }

    @Override
    public Map<String, Object> updateReportInfo(Report report) {
        Map<String,Object> resultMap = new HashMap<>();
        if(report !=null){
            report.setManageTime(new Date());
            report.setReportState("0000");
        }
        reportDao.updateByPrimaryKey(report);
        resultMap.put("code", "200");
        resultMap.put("msg", "修改实习报告成功");
        resultMap.put("level", "success");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateInfoById(String id, String changeType,String dismissReason) {
        Map<String,Object> resultMap = new HashMap<>();
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        String socialId = userSessionInfo.getSocialId();


        if(!StringUtils.isEmpty(id)){
            Example example = new Example(Report.class);
            example.createCriteria().andEqualTo("id",id);
            List<Report> list=reportDao.selectByExample(example);
            if(list.size()>0 && list!=null){
                Report report = list.get(0);
                if("PASS".equals(changeType)){
                    report.setReportState("1111");
                }else if("STOP".equals(changeType)){
                    report.setReportState("2222");
                    report.setDismissReason(dismissReason);
                }
                if(!StringUtils.isEmpty(socialId)){
                    AuthUser authUser=authUserDao.getUserInfo(socialId);
                    if(authUser!=null && !StringUtils.isEmpty(authUser.getTeacherNumber())){
                        report.setApproval(authUser.getTeacherNumber());
                    }
                }
                reportDao.updateByPrimaryKey(report);
                resultMap.put("code", "200");
                resultMap.put("msg", "操作成功");
                resultMap.put("level", "success");
            }
        }
        return resultMap;
    }

    /**
     * 处理中心处理逻辑
     * @param socialId
     */
    public void updateAuthUser(String socialId){
        List<String> list=new ArrayList<>();
        if(!StringUtils.isEmpty(socialId)){
            AuthUser authUser=authUserDao.getUserInfo(socialId);
            if(authUser !=null && !StringUtils.isEmpty(authUser.getStudentNumber())){
                Example example = new Example(Report.class);
                example.createCriteria().andEqualTo("studentNumber",authUser.getStudentNumber());
                List<Report> reportList = reportDao.selectByExample(example);
                if(reportList.size()>0 && reportList!=null){
                    for (Report r:reportList
                         ) {
                        if(r.getReportContent() == null && StringUtils.isEmpty(r.getReportContent())){
                            list.add("yes");
                        }
                    }
                }

                if(list.size()>0 && list!=null){
                    authUser.setYesOrNo("YES");
                    authUserDao.updateAuthUserById(authUser);
                }else {
                    authUser.setYesOrNo("NO");
                    authUserDao.updateAuthUserById(authUser);
                }
            }
        }

    }
}
