package intern_server.shibing.service.imp;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import intern_server.common.utils.SessionUtil;
import intern_server.shibing.constant.Constants;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.data.po.AuthUser;
import intern_server.shibing.data.po.UserSessionVO;
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
import java.util.HashMap;
import java.util.Map;

import static intern_server.common.utils.SessionUtil.getUserSessionInfo;

@Service
public class AuthUserServiceImp implements AuthUserService {
    @Autowired
    private AuthUserDao authUserDao;


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
              }else {
                  AuthUser authUser1=new AuthUser();
                  authUser1.setSocialId(authUser.getSocialId());
                  authUser1.setName(authUser1.getName());
                  authUser1.setRoleId(RoleEnum.USER.getRoleId());
                  String password=authUser.getPassword();
                  authUser1.setPassword(SecureUtil.des(Constants.KEY).encryptHex(password));
                  authUser1.setCreateTime(LocalDateTime.now());

                  authUserDao.insert(authUser1);
              }
          }
          return result;
    }

    @Override
    public Map<String, String> modifyPsd(String socialId, String oldPassword, String newPassword) {
        return null;
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
        result.put("userInfos",authUser);
        return result;
    }

    @Override
    public Map<String, Object> saveImage(MultipartFile file, HttpServletRequest request) throws IOException {
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
        Map<String,Object> result = new HashMap<>();
        result.put("code", Constants.SUCCESS_CODE);
        result.put("msg", "头像上传成功");
        result.put("level", "success");

        return null;
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
