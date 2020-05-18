package intern_server.shibing.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import intern_server.shibing.dao.InternshipDao;
import intern_server.shibing.data.po.InternshipInfo;
import intern_server.shibing.service.InternshipService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/18 14:19
 */
@Service
public class InternshipServiceImp implements InternshipService {

    @Autowired
    private InternshipDao internshipDao;


    @Override
    public Map<String, Object> addDataInfo(InternshipInfo internshipInfo) throws Exception {
        Map<String,Object> resultMap = new HashMap<>();
        if(internshipInfo !=null){
            internshipInfo.setManageTime(new Date());
            internshipInfo.setState("0000");
            internshipInfo.setId(UUID.randomUUID().toString());
            internshipDao.insert(internshipInfo);
            resultMap.put("code", "200");
            resultMap.put("msg", "添加信息成功！！！");
            resultMap.put("level", "success");
        }else {
            throw new Exception("服务器错误");
        }
        return resultMap;
    }

    @Override
    public PageInfo fetchDataInfo(Integer page, Integer pageSize, String companyName, String postalCode, String studentNumber, String teacherNumber) {
        PageHelper.startPage(page, pageSize);
        List<InternshipInfo> internshipInfoList=internshipDao.selectDataInfo(companyName,postalCode,studentNumber,teacherNumber);
        if(internshipInfoList.size()>0 && internshipInfoList !=null){
            for (InternshipInfo i:internshipInfoList
                 ) {
                if(!StringUtils.isEmpty(i.getState())){
                    if(i.getState().contains("0000")){
                        i.setStateName("待审核");
                    }
                    if(i.getState().contains("1111")){
                        i.setStateName("已通过");
                    }
                    if(i.getState().contains("2222")){
                        i.setStateName("已停用");
                    }
                    if(i.getState().contains("3333")){
                        i.setStateName("已驳回");
                    }
                }
            }
        }
        return new PageInfo<>(internshipInfoList);
    }

    @Override
    public Map<String, Object> getDataInfoById(String id) {
        Map<String,Object> map = new HashMap<>();
        InternshipInfo internshipInfo = internshipDao.selectByPrimaryKey(id);
        map.put("internshipInfo",internshipInfo);
        return map;
    }

    @Override
    public Map<String, Object> updateDataInfo(InternshipInfo internshipInfo) {
        Map<String,Object> resultMap = new HashMap<>();
        internshipDao.updateByPrimaryKey(internshipInfo);
        resultMap.put("code", "200");
        resultMap.put("msg", "修改信息成功");
        resultMap.put("level", "success");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateDataInfoById(String id, String updateType) {
        Map<String,Object> map = new HashMap<>();
        if("PASS".equals(updateType)){
            String state="1111";
            internshipDao.updateDataStateById(id,state);
            map.put("code", "200");
            map.put("msg", "审核成功");
            map.put("level", "success");

        }
        if("STOP".equals(updateType)){
            String state="2222";
            internshipDao.updateDataStateById(id,state);
            map.put("code", "200");
            map.put("msg", "停用成功");
            map.put("level", "success");

        }
        if("BACK".equals(updateType)){
            String state ="3333";
            internshipDao.updateDataStateById(id,state);
            map.put("code", "200");
            map.put("msg", "驳回成功");
            map.put("level", "success");
        }
        return map;
    }

    @Override
    public Map<String, Object> getDataInfoByIdAndNumber( String studentNumber) {
        Map<String,Object> map = new HashMap<>();
        List<InternshipInfo> list=internshipDao.selectDataInfoByIdAndNumber(studentNumber);
        map.put("listInternShip",list);
        return map;
    }
}
