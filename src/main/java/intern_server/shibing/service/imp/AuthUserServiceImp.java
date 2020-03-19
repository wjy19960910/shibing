package intern_server.shibing.service.imp;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import intern_server.common.utils.SessionUtil;
import intern_server.shibing.constant.Constants;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.dao.CompanyDao;
import intern_server.shibing.dao.StudentDao;
import intern_server.shibing.dao.TeacherDao;
import intern_server.shibing.data.po.*;
import intern_server.shibing.data.vo.AuthUserVO;
import intern_server.shibing.enums.RoleEnum;
import intern_server.shibing.service.AuthUserService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.*;

import static intern_server.common.utils.SessionUtil.getUserSessionInfo;
import static javafx.scene.input.KeyCode.L;

@Service
public class AuthUserServiceImp implements AuthUserService {
    @Autowired
    private AuthUserDao authUserDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CompanyDao companyDao;


    /**
     * 用户登录
     * @param authUser
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AuthUser userLogin(AuthUser authUser) {


        return authUserDao.getUserLogin(authUser.getSocialId(), SecureUtil.des(Constants.KEY).encryptHex(authUser.getPassword()));
    }


    /**
     * 用户注册
     * @param authUser
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> registerUser(AuthUser authUser) {
          Map<String,Object> result = new HashMap<>();
          if(authUser!=null && !StringUtils.isEmpty(authUser.getSocialId())){
              int count=authUserDao.selectCount(authUser);
              if(count>0){
                  result.put("code", Constants.ALREADY_EXISTS);
                  result.put("msg", "账号已存在，请重新输入");
                  result.put("level", "error");
                  return result;
              }else {
                  AuthUser authUser1=new AuthUser();
                  authUser1.setSocialId(authUser.getSocialId());
                  if(authUser.getRegisterType()==3){
                      if(authUser.getStudentNumber()!=null){
                          Example example=new Example(Student.class);
                          example.createCriteria().andEqualTo("studentNumber",authUser.getStudentNumber());
                          List<Student> student=studentDao.selectByExample(example);
                          // 1111 代表该账号已被注册
                          if(student.size()>0){
                              if( !student.get(0).getState().equals("1111")){
                                  authUser1.setStudentNumber(authUser.getStudentNumber());
                                  authUser1.setName(student.get(0).getStudentName());
                                  studentDao.updateStudentState("1111",authUser.getStudentNumber());
                              }else {
                                  result.put("code", "4444");
                                  result.put("msg", "用户已被注册或者用户不存在");
                                  result.put("level", "warning");
                                  return result;
                              }
                          }else {
                              result.put("code", "3333");
                              result.put("msg", "用户不存在");
                              result.put("level", "warning");
                              return result;
                          }

                      }
                      authUser1.setRoleId(RoleEnum.USER.getRoleId());
                  }
                  if(authUser.getRegisterType() == 6){

                      if(authUser.getTeacherNumber()!=null){
                          Example example=new Example(Teacher.class);
                          example.createCriteria().andEqualTo("teacherNumber",authUser.getTeacherNumber());
                          List<Teacher> teachers=teacherDao.selectByExample(example);
                          // 1111 代表该账号已被注册
                          if(teachers.size()>0){
                              if(!teachers.get(0).getState().equals("1111")){
                                  authUser1.setTeacherNumber(authUser.getTeacherNumber());
                                  teacherDao.updateTeacherState("1111",authUser.getTeacherNumber());
                              }else {
                                  result.put("code", "4444");
                                  result.put("msg", "用户已被注册或者用户不存在");
                                  result.put("level", "warning");
                                  return result;
                              }
                          }else {
                              result.put("code", "3333");
                              result.put("msg", "用户不存在");
                              result.put("level", "warning");
                              return result;
                          }

                      }

                      authUser1.setRoleId(RoleEnum.TEACHER.getRoleId());
                  }
                  if(authUser.getRegisterType()==9){

                      if(authUser.getCompanyNumber()!=null){
                          Example example=new Example(Company.class);
                          example.createCriteria().andEqualTo("companyNumber",authUser.getCompanyNumber());
                          List<Company> companyList=companyDao.selectByExample(example);
                          // 1111 代表该账号已被注册
                          if(companyList.size()>0){
                              if(!companyList.get(0).getState().equals("1111")){
                                  authUser1.setTeacherNumber(authUser.getCompanyNumber());
                                  companyDao.updateCompanyState("1111",authUser.getCompanyNumber());
                              }else {
                                  result.put("code", "4444");
                                  result.put("msg", "用户已被注册或者用户不存在");
                                  result.put("level", "warning");
                                  return result;
                              }
                          }else {
                              result.put("code", "3333");
                              result.put("msg", "用户不存在");
                              result.put("level", "warning");
                              return result;
                          }

                      }

                      authUser1.setRoleId(RoleEnum.COMPANY.getRoleId());
                  }

                  String password=authUser.getPassword();
                  authUser1.setPassword(SecureUtil.des(Constants.KEY).encryptHex(password));
                  authUser1.setId(UUID.randomUUID().toString());
                  authUser1.setCreateTime(new Date());

                  authUserDao.insert(authUser1);
                  result.put("code", "200");
                  result.put("msg", "注册成功");
                  result.put("level", "success");
              }
          }
          return result;
    }

    /**
     * 修改用户密码
     * @return
     */
    @Override
    public Map<String, Object> modifyPsd(@RequestBody AuthUser authUser) {
        Map<String,Object> result = new HashMap<>();
        if(authUser !=null){
            String password=authUser.getPassword();
            authUser.setPassword(SecureUtil.des(Constants.KEY).encryptHex(password));
            authUserDao.updateAuthUserById(authUser);
            result.put("code", "200");
            result.put("msg", "修改密码成功，请重新登陆！！");
            result.put("level", "success");
        }
        return result;
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String token) {
        Map<String,Object> result = new HashMap<>();
        UserSessionVO userSessionVO=SessionUtil.getUserSessionInfo();
        AuthUser authUser=authUserDao.getUserInfo(userSessionVO.getSocialId());
        if(authUser.getRoleId().equals(RoleEnum.USER.getRoleId())){
            authUser.setRoleName(RoleEnum.USER.getRoleName());
        }
        if(authUser.getRoleId().equals(RoleEnum.TEACHER.getRoleId())){
            authUser.setRoleName(RoleEnum.TEACHER.getRoleName());
        }
        if(authUser.getRoleId().equals(RoleEnum.ADMIN.getRoleId())){
            authUser.setRoleName(RoleEnum.ADMIN.getRoleName());
        }
        if(authUser.getRoleId().equals(RoleEnum.COMPANY.getRoleId())){
            authUser.setRoleName(RoleEnum.COMPANY.getRoleName());
        }

        result.put("userInfos",authUser);
        return result;
    }

    @Override
    public Map<String, Object> saveImage(MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String,Object> result = new HashMap<>();
        String fileName = file.getOriginalFilename();
        String fileExtName = "";
        if (fileName.indexOf(".") <= 0) {
            fileExtName = "jpg";
        } else {
            fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
        }



        BASE64Encoder base64Encoder =new BASE64Encoder();
        String base64EncoderImg = file.getOriginalFilename()+","+ base64Encoder.encode(file.getBytes());
        String str = base64EncoderImg.substring(base64EncoderImg.indexOf(",")+1);
        String imagePath = "E:/images/"+System.currentTimeMillis()+".jpg";
        GenerateImage(str,imagePath);
        UserSessionVO userSessionVO=SessionUtil.getUserSessionInfo();
        authUserDao.updateAvatar(imagePath,userSessionVO.getSocialId());

        result.put("code", Constants.SUCCESS_CODE);
        result.put("msg", "头像上传成功");
        result.put("level", "success");

        return result;
    }

    /**
     * 修改用户信息
     * @param authUser
     * @return
     */
    @Override
    public Map<String, Object> modifyUserInfo(AuthUser authUser) {
        Map<String,Object> result = new HashMap<>();
        if(authUser!=null){
            if(authUser.getId()!=null){
                authUserDao.updateAuthUserById(authUser);
                result.put("code", "200");
                result.put("msg", "修改用户信息成功");
                result.put("level", "success");
            }

        }else {
            result.put("code", "9999");
            result.put("msg", "修改用户信息失败");
            result.put("level", "error");
        }
        return result;
    }

    public static boolean GenerateImage(String imgStr,String imageName) {
        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null)
        {
            return false;
        }
        try {
            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr.replace("data:image/jpg;base64,", ""));
            imgStr = imgStr.replace("base64,", "");
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = imageName;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }



}
