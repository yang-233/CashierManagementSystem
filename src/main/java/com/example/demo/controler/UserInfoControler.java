package com.example.demo.controler;

import com.example.demo.dao.WebUserMapper;
import com.example.demo.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserInfoControler {

    @Autowired
    private WebUserMapper umapper;
    @RequestMapping(value = "/modify_info",method = RequestMethod.POST)
    @ResponseBody
    public boolean modifyInfo(HttpServletRequest request,String name, String telephone, int age, int sex){
        String account=(String)request.getSession().getAttribute("account");
        if(umapper.updateByPrimaryKey(new WebUser(account,name,telephone,age,sex))>0){
            return true;
        }
        return false;
    }
}
