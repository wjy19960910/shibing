package intern_server.shibing.data.po;

import intern_server.common.annotaion.Excel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/19 15:24
 */
@Entity
@Table( name ="report_info" )
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @Excel(name = "实现报告标题")
    private String reportName;
    private String reportDate;
    private String reportContent;
    private String internshipId;
    private String reportState;
    private String approval;
    private String studentNumber;
    private String DismissReason;
    private Date manageTime;
    private String manageUser;
    @Transient
    private String teacherNumber;
    @Transient
    private String companyName;
    @Transient
    private String studentName;
    @Transient
    private String stateName;
    @Transient
    private String internshipIdName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(String internshipId) {
        this.internshipId = internshipId;
    }

    public String getReportState() {
        return reportState;
    }

    public void setReportState(String reportState) {
        this.reportState = reportState;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getDismissReason() {
        return DismissReason;
    }

    public void setDismissReason(String dismissReason) {
        DismissReason = dismissReason;
    }

    public Date getManageTime() {
        return manageTime;
    }

    public void setManageTime(Date manageTime) {
        this.manageTime = manageTime;
    }

    public String getManageUser() {
        return manageUser;
    }

    public void setManageUser(String manageUser) {
        this.manageUser = manageUser;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getInternshipIdName() {
        return internshipIdName;
    }

    public void setInternshipIdName(String internshipIdName) {
        this.internshipIdName = internshipIdName;
    }
}
