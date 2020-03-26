package intern_server.shibing.service.imp;

import intern_server.common.utils.SessionUtil;
import intern_server.shibing.dao.AuthUserDao;
import intern_server.shibing.dao.ReportDao;
import intern_server.shibing.dao.StudentDao;
import intern_server.shibing.dao.SysDictDao;
import intern_server.shibing.data.po.*;
import intern_server.shibing.data.vo.DealVo;
import intern_server.shibing.service.CommonService;
import intern_server.shibing.utils.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/17 20:54
 */
@Service
public class CommonServiceImp implements CommonService {
    @Autowired
    private SysDictDao sysDictDao;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private AuthUserDao authUserDao;
    @Autowired
    private StudentDao studentDao;

    @Override
    public List<SysDict> getSysDict() {
        return sysDictDao.listSysDictInfo();
    }

    @Override
    public Map<String, Object> createSysDict(SysDict sysDict) {
        Map<String,String> resultMap = new HashMap<>();
        Example example = new Example(SysDict.class);
        example.createCriteria().andEqualTo("state","0000")
                .andEqualTo("level",sysDict.getLevel());

        return null;
    }

    @Override
    public List<DealVo> fetchDealInfo() {
        UserSessionVO userSessionVO= SessionUtil.getUserSessionInfo();
        String socialId=userSessionVO.getSocialId();
        List<DealVo> dealVos=new ArrayList<>();
        if(!StringUtils.isEmpty(socialId)){
            AuthUser authUser = authUserDao.getUserInfo(socialId);
            if(authUser.getStudentNumber()!=null && !StringUtils.isEmpty(authUser.getStudentNumber())){
                Example example=new Example(Report.class);
                example.createCriteria().andEqualTo("studentNumber",authUser.getStudentNumber()).andEqualTo("reportState","8888");
                List<Report> reportList =reportDao.selectByExample(example);
                if(reportList.size()>0 && reportList!=null){
                    for (Report r:reportList
                         ) {
                        DealVo dealVo=new DealVo();
                        dealVo.setDealDate(r.getReportDate());
                        dealVo.setDealName("实习报告");
                        dealVo.setReportName(authUser.getName());
                        dealVo.setDealRemark("请填写对应日期实习日报");
                        dealVos.add(dealVo);
                    }
                }
            }
        }

        return dealVos;
    }


    @Override
    public List<Student> fetchStudentByTeacherNumber() throws Exception{
        UserSessionVO userSessionVO= SessionUtil.getUserSessionInfo();
        String socialId=userSessionVO.getSocialId();
        if(!StringUtils.isEmpty(socialId)){
            AuthUser authUser = authUserDao.getUserInfo(socialId);
            if(authUser != null && !StringUtils.isEmpty(authUser.getTeacherNumber())){
                List<Student> studentList=studentDao.selectStudentInfoByTeacherNumber(authUser.getTeacherNumber());
                return studentList;
            }
        }else {
            throw new Exception("token 异常，请重新登陆");
        }
        return new ArrayList<>();
    }
}
