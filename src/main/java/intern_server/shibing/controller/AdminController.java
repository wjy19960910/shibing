package intern_server.shibing.controller;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/14 15:59
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/fetchUserDataInfo")
    public PageInfo fetchUserDataInfo(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) String fuzzy){
        return adminService.fetchUserDataInfo(page,pageSize,fuzzy);
    }

}
