package intern_server.shibing.dao;

import intern_server.shibing.data.po.Company;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CompanyDao extends Mapper<Company> {
    /**
     * 修改状态
     * @param state
     * @param companyNumber
     */
    void updateCompanyState(@Param("state") String state,@Param("companyNumber") String companyNumber);

    List<Company> selectCompanyInfo(@Param("companyName") String companyName, @Param("companyNumber") String companyNumber, @Param("companyPhone") String companyPhone);

    Company selectCompanyInfoById(@Param("id") String id);

    void deleteCompanyInfoByIds(String[] ids);
}
