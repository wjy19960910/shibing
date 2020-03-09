package intern_server.shibing.dao;

import intern_server.shibing.data.po.Company;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CompanyDao extends Mapper<Company> {
    /**
     * 修改状态
     * @param state
     * @param teacherNumber
     */
    void updateCompanyState(@Param("state") String state,@Param("teacherNumber") String teacherNumber);

}
