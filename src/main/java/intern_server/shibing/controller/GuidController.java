package intern_server.shibing.controller;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Guid;
import intern_server.shibing.data.po.Report;
import intern_server.shibing.service.GuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/24 15:51
 */
@RestController
@RequestMapping("/guid")
public class GuidController {
    @Autowired
    private GuidService guidService;

    @PostMapping("/createData")
    public Map<String,Object> createData(@RequestBody Guid guid){
        return guidService.createData(guid);
    }

    @GetMapping("/fetchDataInfo")
    public PageInfo fetchDataInfo(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                   @RequestParam(required = false) String gTitle, @RequestParam(required = false) String gDate){

        return guidService.fetchDataInfo(page,pageSize,gTitle,gDate);
    }

    @GetMapping("/getDataInfoById/{id}")
    public Map<String,Object> getDataInfoById(@PathVariable String id){

        return guidService.getDataInfoById(id);
    }

    @PutMapping("/updateDataInfo")
    public Map<String,Object> updateDataInfo(@RequestBody Guid guid){

        return guidService.updateDataInfo(guid);
    }

    @DeleteMapping("/removeDataInfo/{ids}")
    public Map<String,Object> removeDataInfo(@PathVariable String[] ids){
        return guidService.removeDataInfo(ids);
    }
}
