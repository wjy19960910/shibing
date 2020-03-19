package intern_server.shibing.service;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Company;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/4 15:19
 */
public interface CompanyService {
    PageInfo listCompany(Integer page, Integer pageSize, String companyName, String companyNumber, String companyPhone);

    Map<String, Object> addCompany(Company company);

    Map<String, Object> updateCompany(Company company);

    Map<String, Object> removeCompany(String[] ids);

    Map<String, Object> getCompany(String id);
}
