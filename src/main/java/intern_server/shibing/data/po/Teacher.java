package intern_server.shibing.data.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wangjingyuan
 * @Date: 2020/2/25 16:24
 */
@Entity
@Table( name ="teacher_info" )
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String teacherNumber;
    private String teacherName;
    private String state;
    private String teacherPhone;
    private String teacherPosition;
    private String teacherDepartments;
    private String manageUser;
    private Date manageTime;

    @Transient
    private String fuzzy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getTeacherPosition() {
        return teacherPosition;
    }

    public void setTeacherPosition(String teacherPosition) {
        this.teacherPosition = teacherPosition;
    }

    public String getTeacherDepartments() {
        return teacherDepartments;
    }

    public void setTeacherDepartments(String teacherDepartments) {
        this.teacherDepartments = teacherDepartments;
    }

    public String getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(String fuzzy) {
        this.fuzzy = fuzzy;
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
}
