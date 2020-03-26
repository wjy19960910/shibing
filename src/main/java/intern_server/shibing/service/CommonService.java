package intern_server.shibing.service;

import intern_server.shibing.data.po.Student;
import intern_server.shibing.data.po.SysDict;
import intern_server.shibing.data.vo.DealVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/17 20:54
 */
public interface CommonService {
    List<SysDict> getSysDict();

    Map<String, Object> createSysDict(SysDict sysDict);

    List<DealVo> fetchDealInfo();

    List<Student> fetchStudentByTeacherNumber() throws Exception;
}
