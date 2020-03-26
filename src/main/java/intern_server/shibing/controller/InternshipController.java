package intern_server.shibing.controller;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.InternshipInfo;
import intern_server.shibing.data.po.Student;
import intern_server.shibing.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * 实习信息管理
 * @Date: 2020/3/18 14:18
 */
@RestController
@RequestMapping("/internShip")
public class InternshipController {
    @Autowired
    private InternshipService internshipService;

    @GetMapping("/fetchDataInfo")
    public PageInfo fetchDataInfo(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                        @RequestParam(required = false) String companyName, @RequestParam(required = false) String postalCode,
                                        @RequestParam(required = false) String studentNumber,@RequestParam(required = false) String teacherNumber){
        return internshipService.fetchDataInfo(page,pageSize,companyName,postalCode,studentNumber,teacherNumber);

    }


    @PostMapping("/addDataInfo")
    public Map<String,Object> addDataInfo(@RequestBody InternshipInfo internshipInfo) throws Exception {
        return internshipService.addDataInfo(internshipInfo);
    }

    @GetMapping("/getDataInfoById/{id}")
    public Map<String,Object> getDataInfoById(@PathVariable String id){
        return internshipService.getDataInfoById(id);
    }

    @PutMapping("/updateDataInfo")
    public Map<String,Object> updateDataInfo(@RequestBody InternshipInfo internshipInfo){
        return internshipService.updateDataInfo(internshipInfo);
    }

    @GetMapping("/updateDataInfoById/{id}/{updateType}")
    public Map<String,Object> updateDataInfoById(@PathVariable String id,@PathVariable String updateType){
        return internshipService.updateDataInfoById(id,updateType);
    }

    @GetMapping("/getDataInfoByIdAndNumber/{studentNumber}")
    public Map<String,Object> getDataInfoByIdAndNumber(@PathVariable String studentNumber){
        return internshipService.getDataInfoByIdAndNumber(studentNumber);
    }

}
