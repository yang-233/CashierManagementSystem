package com.example.demo.controler;

import com.example.demo.dao.WebAccountMapper;
import com.example.demo.dao.WebUserMapper;
import com.example.demo.model.WebAccount;
import com.example.demo.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdministratorControler {

    @Autowired
    private WebAccountMapper mapper;

    @Autowired
    private WebUserMapper umapper;

    @RequestMapping(value = "/administrator",method = RequestMethod.GET)
    public String administrator(){
        return "administrator";
    }

    @RequestMapping(value = "/show_user_no_certify",method = RequestMethod.GET)
    @ResponseBody
    public List<WebAccount> showUser(HttpServletRequest request){
        HttpSession session=request.getSession();
        //System.out.println(session.getAttribute("account"));
        return mapper.selectByType(0);
    }

    @RequestMapping(value = "/certify",method = RequestMethod.GET)
    @ResponseBody
    public String certify(String account){
        try {
            WebAccount result=mapper.selectByPrimaryKey(account);
            if(result!=null&&
                    result.getType()==0&&
                    mapper.updateByPrimaryKey(new WebAccount(account,result.getPassword(),result.getQuestion(),
                            result.getAnswer(),1))>0&&
                    umapper.insert(new WebUser(account))>0
                    ){
                return "1";
            }
        }
        catch (Exception e){
        }
        return "0";
    }

    public ArrayList<Map<String,String>> getResult(){
        try {
            ArrayList<Map<String,String>> result=new ArrayList<>();
            List<WebUser> l=umapper.selectAll();
            for(WebUser i:l){
                Map<String,String> m=new HashMap<String, String>();
                m.put("account",i.getAccount());
                m.put("telephone",i.getTelephone());
                m.put("name",i.getName());
                m.put("age",String.valueOf(i.getAge()));
                m.put("sex",i.getSex()==0?"女":"男");
                String type=null;
                switch (mapper.selectTypeByAccount(i.getAccount())){
                    case 0:
                        type="未审核";
                        break;
                    case 1:
                        type="收银员";
                        break;
                    case 2:
                        type="经理";
                        break;
                    case 3:
                        type="管理员";
                        break;
                    default:
                        type="傻逼";
                        break;
                }
                m.put("type",type);
                result.add(m);
            }
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/get_users",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,String>> getUsers(){
        return getResult();
    }

    @RequestMapping(value = "/delete_user",method = RequestMethod.GET)
    @ResponseBody
    public String deleteUser(String account){
        try {
            if(mapper.deleteByPrimaryKey(account)>0&&
                    umapper.deleteByPrimaryKey(account)>0){
                return "1";
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "0";
    }
}
