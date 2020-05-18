package intern_server.shibing.dao;

        import intern_server.shibing.data.po.InternshipInfo;
        import org.apache.ibatis.annotations.Param;
        import org.springframework.stereotype.Repository;
        import tk.mybatis.mapper.common.Mapper;

        import java.util.List;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/18 14:19
 */
@Repository
public interface InternshipDao extends Mapper<InternshipInfo> {
    List<InternshipInfo> selectDataInfo(String companyName, String postalCode, String studentNumber, String teacherNumber);

    void updateDataStateById(@Param("id") String id, @Param("state") String state);

    List<InternshipInfo> selectDataInfoByIdAndNumber(String studentNumber);

    List<InternshipInfo> selectInternTime(String internshipStartTime, String internshipEndTime);
}
