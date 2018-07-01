package com.example.demo.controler;


import com.example.demo.dao.WebAccountMapper;
import com.example.demo.dao.WebUserMapper;
import com.example.demo.model.WebAccount;
import com.example.demo.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AccountControler {
    @Autowired
    private WebAccountMapper mapper;

    @Autowired
    private WebUserMapper umapper;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login_submit", method = RequestMethod.POST)
    public ModelAndView login(String account, String password, HttpServletRequest request) {
        HttpSession session=request.getSession();
        session.setAttribute("account",account);
        WebAccount result=mapper.selectByPrimaryKey(account);
        ModelAndView m=new ModelAndView();
        String msg=null;
        if(result!=null&&result.getPassword().equals(password)){
            switch (result.getType()){
                case 0:
                    msg=account+" 尚未通过管理员审核！";
                    m.setViewName("login");
                    break;
                case 1://cashier
                    m.addObject("name",umapper.selectByPrimaryKey(account).getName());
                    msg=account+" 登录成功！";
                    m.setViewName("cashier");
                    break;
                case 2:
                    msg=account+" 以经理身份登录！";
                    m.setViewName("manager");
                    break;
                case 3:
                    msg=account+" 以管理员身份登录！";
                    m.setViewName("administrator");
                    break;
            }
        }
        else{
            m.setViewName("login");
            msg=account+" 登录失败！";
        }
        m.addObject("msg",msg);
        return m;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(){
        return "register";
    }


    @RequestMapping(value = "/register_submit",method = RequestMethod.POST)
    public ModelAndView register(String account,String password,String question,String answer){
        ModelAndView m=new ModelAndView();
        String msg=null;
        int rows=0;
        try {
            rows=mapper.insert(new WebAccount(account,password,question,answer,1));
        } catch (Exception e){
            System.out.println("插入失败！");
        }
        if(rows!=0){
            m.setViewName("login");
            msg=account+" 注册成功！";
            System.out.println("插入成功！");
        }
        else{
            m.setViewName("register");
            msg=account+" 注册失败！";
        }
        m.addObject("msg",msg);
        return m;
    }

    @RequestMapping(value = "/changePassword",method = RequestMethod.GET)
    public String changePassword(){
        return "changePassword";
    }

    @RequestMapping(value = "/change_submit",method = RequestMethod.POST)
    public ModelAndView changePassword(String account,String oldPassword,
                                       String newPassword,String newPasswordAgain){
        ModelAndView m=new ModelAndView();
        WebAccount result=mapper.selectByPrimaryKey(account);
        String msg=null;
        int rows=0;
        if(result!=null
                &&result.getPassword().equals(oldPassword)
                &&newPassword.equals(newPasswordAgain)){
            rows=mapper.updateByPrimaryKey(new WebAccount(account,newPassword,
                    result.getQuestion(),result.getAnswer(),result.getType()));
        }
        if(rows!=0){
            m.setViewName("login");
            msg=account+" 修改密码成功！";
        }
        else{
            m.setViewName("changePassword");
            msg=account+" 修改密码失败！";
        }
        m.addObject("msg",msg);
        return m;
    }

    @RequestMapping(value = "/forgetPassword",method = RequestMethod.GET)
    public String forgetPassword(){
        return "forgetPassword";
    }

    @RequestMapping(value = "/forget_submit",method = RequestMethod.POST)
    public ModelAndView forgetPassword(String account,String answer,String newPassword){
        ModelAndView m=new ModelAndView();
        WebAccount result=mapper.selectByPrimaryKey(account);
        String msg=null;
        int rows=0;
        if(result!=null
                &&result.getAnswer().equals(answer)){
            rows=mapper.updateByPrimaryKey(new WebAccount(account,newPassword,
                    result.getQuestion(),result.getAnswer(),result.getType()));
            m.setViewName("login");
            msg=account+" 找回密码成功！";
        }
        else {
            m.setViewName("forgetPassword");
            msg=account+" 找回密码失败！";
        }
        m.addObject("msg",msg);
        return m;
    }

    @RequestMapping(value = "/show_question",method = RequestMethod.GET)
    @ResponseBody
    public String showQuestion(String account){
        WebAccount result=mapper.selectByPrimaryKey(account);
        if(result!=null){
            return result.getQuestion();
        }
        else{
            return "显示问题出错！";
        }
    }

    @RequestMapping(value = "/edit_info",method = RequestMethod.GET)
    public ModelAndView editInfo(HttpServletRequest request){
        ModelAndView m=new ModelAndView("editInfo");
        String account=(String)request.getSession().getAttribute("account");
        WebUser result=umapper.selectByPrimaryKey(account);
        m.addObject("userInfo",result);
        return m;
    }

    @RequestMapping(value = "/go_back",method = RequestMethod.GET)
    public ModelAndView goBack(HttpServletRequest request){
        ModelAndView m=new ModelAndView();
        String account=(String)request.getSession().getAttribute("account");
        WebUser result=umapper.selectByPrimaryKey(account);
        switch (mapper.selectTypeByAccount(account)){
            case 1:
                m.addObject("name",result.getName());
                m.setViewName("cashier");
                break;
            case 2:
                m.setViewName("manager");
                break;
            case 3:
                m.setViewName("administrator");
                break;
                default:
                    m.setViewName("login");
        }
        return m;
    }
}
