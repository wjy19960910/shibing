package intern_server.shibing.data.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/18 14:09
 */
@Entity
@Table( name ="internship_info" )
public class InternshipInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String postalCode;
    private String internshipStartTime;
    private String internshipWage;
    private String bankName;
    private String bankAccount;
    private String remark;
    private String state;
    private String studentNumber;
    private Date manageTime;
    private String internshipEndTime;
    private String companyName;
    private String internshipAddress;

    @Transient
    private String studentName;
    @Transient
    private String studentClass;
    @Transient
    private String studentEmail;
    @Transient
    private String studentPhone;
    @Transient
    private String stateName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getInternshipStartTime() {
        return internshipStartTime;
    }

    public void setInternshipStartTime(String internshipStartTime) {
        this.internshipStartTime = internshipStartTime;
    }

    public String getInternshipWage() {
        return internshipWage;
    }

    public void setInternshipWage(String internshipWage) {
        this.internshipWage = internshipWage;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Date getManageTime() {
        return manageTime;
    }

    public void setManageTime(Date manageTime) {
        this.manageTime = manageTime;
    }

    public String getInternshipEndTime() {
        return internshipEndTime;
    }

    public void setInternshipEndTime(String internshipEndTime) {
        this.internshipEndTime = internshipEndTime;
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

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getInternshipAddress() {
        return internshipAddress;
    }

    public void setInternshipAddress(String internshipAddress) {
        this.internshipAddress = internshipAddress;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
