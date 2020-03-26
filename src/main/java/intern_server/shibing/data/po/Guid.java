package intern_server.shibing.data.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/24 15:40
 */
@Entity
@Table( name ="guid_info" )
public class Guid implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String gTitle;
    private String gContent;
    private String gDate;
    private String studentId;
    private String remark;
    private String manageUser;
    private Date manageTime;
    private String state;
    private String teacherNumber;

    @Transient
    private String gTeacherName;

    @Transient
    private String gStudentName;

    @Transient
    private List<String> studentIdList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getgTitle() {
        return gTitle;
    }

    public void setgTitle(String gTitle) {
        this.gTitle = gTitle;
    }

    public String getgContent() {
        return gContent;
    }

    public void setgContent(String gContent) {
        this.gContent = gContent;
    }

    public String getgDate() {
        return gDate;
    }

    public void setgDate(String gDate) {
        this.gDate = gDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getManageUser() {
        return manageUser;
    }

    public void setManageUser(String manageUser) {
        this.manageUser = manageUser;
    }

    public Date getManageTime() {
        return manageTime;
    }

    public void setManageTime(Date manageTime) {
        this.manageTime = manageTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getgTeacherName() {
        return gTeacherName;
    }

    public void setgTeacherName(String gTeacherName) {
        this.gTeacherName = gTeacherName;
    }

    public String getgStudentName() {
        return gStudentName;
    }

    public void setgStudentName(String gStudentName) {
        this.gStudentName = gStudentName;
    }

    public List<String> getStudentIdList() {
        return studentIdList;
    }

    public void setStudentIdList(List<String> studentIdList) {
        this.studentIdList = studentIdList;
    }
}
