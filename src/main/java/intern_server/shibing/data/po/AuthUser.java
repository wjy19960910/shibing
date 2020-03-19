package intern_server.shibing.data.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Administrator
 */
@Entity
@Table( name ="auth_user" )
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */

    @Id
    private String id;

    /**
     * 用户名
     */
    private String socialId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 角色主键 1 学生用户 2 admin 3公司用户 4老师用户
     */
    private Long roleId;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 个人介绍
     */

    private String introduction;

    private String authAccount;

    private String avatar;


    private Integer registerType;
    private String studentNumber;
    private String teacherNumber;
    private String companyNumber;

    @Transient
    private String roleName;
    @Transient
    private String fuzzy;
    @Transient
    private String passwordChange;
    @Transient
    private String roleIdName;

    public AuthUser(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    public String getAuthAccount() {
        return authAccount;
    }

    public void setAuthAccount(String authAccount) {
        this.authAccount = authAccount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Integer getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Integer registerType) {
        this.registerType = registerType;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(String fuzzy) {
        this.fuzzy = fuzzy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPasswordChange() {
        return passwordChange;
    }

    public void setPasswordChange(String passwordChange) {
        this.passwordChange = passwordChange;
    }

    public String getRoleIdName() {
        return roleIdName;
    }

    public void setRoleIdName(String roleIdName) {
        this.roleIdName = roleIdName;
    }
}
