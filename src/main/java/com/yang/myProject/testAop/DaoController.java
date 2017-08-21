package com.yang.myProject.testAop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yangjing
 */
@RestController
@RequestMapping("/test/aop")
public class DaoController {
    @Autowired
    private Dao dao;

    @RequestMapping(value = "/addTime",produces = "application/json")
    public String testTime(){
        dao.insert();
        dao.update();
        dao.delete();
        //必须是@RestController才能保证返回字符串Success，如果是@Controller的话会返回Success.jsp
        return "Success";
    }
}
