package intern_server.shibing.controller;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.dao.CompanyDao;
import intern_server.shibing.data.po.Company;
import intern_server.shibing.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/4 15:15
 * 管理公司信息
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/listCompany")
    public PageInfo listCompany(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                        @RequestParam(required = false) String companyName, @RequestParam(required = false) String companyNumber,
                                        @RequestParam(required = false) String companyPhone){
        return companyService.listCompany(page,pageSize,companyName,companyNumber,companyPhone);

    }

    @PostMapping("/addCompany")
    public Map<String,Object> addCompany(@RequestBody Company company){
        return companyService.addCompany(company);
    }

    @PutMapping("/updateCompany")
    public Map<String,Object> updateCompany(@RequestBody Company company){
        return companyService.updateCompany(company);
    }

    @DeleteMapping("/removeCompany/{ids}")
    public Map<String,Object> removeCompany(@PathVariable String[] ids){
        return companyService.removeCompany(ids);
    }

    @GetMapping("/getCompany/{id}")
    public Map<String,Object> getCompany(@PathVariable String id){
        return companyService.getCompany(id);
    }
}
