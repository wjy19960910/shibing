package intern_server.shibing.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import intern_server.shibing.dao.CompanyDao;
import intern_server.shibing.data.po.Company;
import intern_server.shibing.data.po.Student;
import intern_server.shibing.service.CompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class CompanyServiceImp implements CompanyService {

    @Autowired
    private CompanyDao companyDao;


    @Override
    public PageInfo listCompany(Integer page, Integer pageSize, String companyName, String companyNumber, String companyPhone) {
        PageHelper.startPage(page, pageSize);
        List<Company> companyList = companyDao.selectCompanyInfo(companyName,companyNumber,companyPhone);
        return new PageInfo<>(companyList);
    }

    @Override
    public Map<String, Object> addCompany(Company company) {
        Map<String,Object> resultMap = new HashMap<>();
        if(company!=null && !StringUtils.isEmpty(company.getCompanyNumber())){
            Example example=new Example(Company.class);
            example.createCriteria().andEqualTo("companyNumber",company.getCompanyNumber());
            List<Company> companyList = companyDao.selectByExample(example);
            if(companyList.size()>0 && company !=null){
                resultMap.put("code", "3000");
                resultMap.put("msg", "该企业信用号已存在，请核对！！！");
                resultMap.put("level", "warning");
                return resultMap;
            }
        }
        company.setState("0000");
        company.setId(UUID.randomUUID().toString());
        company.setManageTime(new Date());
        companyDao.insert(company);
        resultMap.put("code", "200");
        resultMap.put("msg", "添加企业信息成功！！！");
        resultMap.put("level", "success");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateCompany(Company company) {
        Map<String,Object> resultMap = new HashMap<>();
        companyDao.updateByPrimaryKey(company);
        resultMap.put("code", "200");
        resultMap.put("msg", "修改信息成功");
        resultMap.put("level", "success");
        return resultMap;
    }

    @Override
    public Map<String, Object> removeCompany(String[] ids) {
        Map<String,Object> map = new HashMap<>();
        List<String> deleteName = new ArrayList<>();
        if(ids.length>0){
            for (int i=0;i<ids.length;i++){
                Company company = companyDao.selectCompanyInfoById(ids[i]);
                if(company.getCompanyNumber().contains("1111")){
                    deleteName.add(company.getCompanyName());
                }
            }
            if(deleteName.size()>0 && deleteName!=null){
                map.put("code", "9999");
                map.put("msg", String.join(",",deleteName)+"用户已被使用，不可删除");
                map.put("level", "warning");
                return map;
            }else {
                companyDao.deleteCompanyInfoByIds(ids);
                map.put("code", "200");
                map.put("msg", "删除成功");
                map.put("level", "success");
            }

        }

        return map;
    }

    @Override
    public Map<String, Object> getCompany(String id) {
        Map<String,Object> map = new HashMap<>();
        Company company = companyDao.selectCompanyInfoById(id);
        map.put("companyInfo",company);
        return map;
    }
}
