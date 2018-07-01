package com.example.demo.controler;

import com.example.demo.dao.WebOrderMapper;
import com.example.demo.dao.WebProductsMapper;
import com.example.demo.dao.WebUserMapper;
import com.example.demo.model.WebOrder;
import com.example.demo.model.WebProducts;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CashierControler {

    @Autowired
    private WebProductsMapper pmp;

    @Autowired
    private WebOrderMapper omp;

    @Autowired
    private WebUserMapper ump;

    @RequestMapping(value = "/collect_money",method = RequestMethod.GET)
    public ModelAndView collectMoney(){
        return new ModelAndView("collectMoney");
    }


    public boolean modify(String account,String productId,int number){
        try {
            WebProducts result=pmp.selectByPrimaryKey(productId);
            if(number>result.getNumber()){
                return false;
            }
            if(pmp.updateByPrimaryKey(new WebProducts(productId,result.getName(),//减少剩余商品数量
                    result.getPrice(),result.getNumber()-number))>0&&
                    omp.insertOrder(new WebOrder(null,productId,account,number,null))>0){
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private ObjectMapper m=new ObjectMapper();

    @RequestMapping(value = "/buy",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Boolean> buy(HttpServletRequest request){
        HashMap<String,Boolean> ans=null;
        try{
            String account=(String)request.getSession().getAttribute("account");
            if(account==null){
                return null;
            }
            JavaType jt=getCollectionType(ArrayList.class,WebProducts.class);
            ArrayList<WebProducts> result=(ArrayList<WebProducts>)
                    m.readValue((String)request.getParameter("order"),jt);
            ans=new HashMap<>();
            for(WebProducts i:result){
                boolean a=modify(account,i.getId(),i.getNumber());
                ans.put(i.getId(),a);
                System.out.println(a?"success":"fail")  ;
            }
            return ans;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ans;
    }
    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return m.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    @RequestMapping(value = "/check_my_order")
    public String checkMyOrder(){
        return "myOrder";
    }

    private Map<String,Object> getOrder(WebOrder order){
        Map<String,Object> m=new HashMap<>();
        WebProducts p=pmp.selectByPrimaryKey(order.getProductId());
        m.put("cashierName",ump.selectByPrimaryKey(order.getCashierAccount()).getName());
        m.put("orderTime",order.getOrderTime().getTime());
        m.put("productName",p.getName());
        m.put("number",order.getNumber());
        m.put("unitPrice",p.getPrice());
        m.put("cashierId",order.getCashierAccount());
        m.put("productId",order.getProductId());
        return m;
    }

    @RequestMapping(value = "/get_my_order")
    @ResponseBody
    public List<Map<String,Object>> getMyOrder(HttpServletRequest request){
        List<Map<String,Object>> l=new ArrayList<>();
        List<WebOrder> result=omp.selectAll();
        String account=(String)request.getSession().getAttribute("account");
        for(WebOrder i:result){
            if(account.equals(i.getCashierAccount())){//只返回该收银员的订单信息
                l.add(getOrder(i));
            }
        }
        return l;
    }

    @RequestMapping(value = "/check_my_performance")
    public String checkMyPerformance(){
        return "myPerformance";
    }


    public List<Map<String,Object>> getOnesPerformance(String account){
        List<WebProducts> products=pmp.selectAll();
        List<WebOrder> orders=omp.selectAll();
        List<Map<String,Object>> result=new ArrayList<>();
        for(WebProducts i:products){
            Map<String,Object> m=new HashMap<>();
            int number=0;
            m.put("name",i.getName());
            m.put("price",i.getPrice());
            for(WebOrder j:orders){
                if(account.equals(j.getCashierAccount())&&
                        i.getId().equals(j.getProductId())){
                    number+=j.getNumber();
                }
            }
            m.put("number",number);
            System.out.println(m);
            result.add(m);
        }
        return result;
    }

    @RequestMapping(value = "/get_my_performance")
    @ResponseBody
    public List<Map<String,Object>> getMyPerformance(HttpServletRequest request){
        String account=(String)request.getSession().getAttribute("account");
        return getOnesPerformance(account);
    }
}
