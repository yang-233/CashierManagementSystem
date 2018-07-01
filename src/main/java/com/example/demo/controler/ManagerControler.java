package com.example.demo.controler;

import com.example.demo.dao.WebAccountMapper;
import com.example.demo.dao.WebOrderMapper;
import com.example.demo.dao.WebProductsMapper;
import com.example.demo.dao.WebUserMapper;
import com.example.demo.model.WebAccount;
import com.example.demo.model.WebOrder;
import com.example.demo.model.WebProducts;
import com.example.demo.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ManagerControler {

    @Autowired
    private WebAccountMapper amp;
    @Autowired
    private WebProductsMapper pmp;

    @Autowired
    private WebOrderMapper omp;

    @Autowired
    private WebUserMapper ump;

    @RequestMapping(value = "/manager",method = RequestMethod.GET)
    public String manager(){
        return "manager";
    }


    @RequestMapping(value = "/inventory",method = RequestMethod.GET)
    public String inventory(){
        return "inventory";
    }
    @RequestMapping(value = "/getProducts",method = RequestMethod.GET)
    @ResponseBody
    public List<WebProducts> getProducts(){
        List<WebProducts> l=pmp.selectAll();
        return l;
    }

    @RequestMapping(value = "/add_products",method = RequestMethod.POST)
    @ResponseBody
    public WebProducts addProducts(String id,int number){
        System.out.println(id+"\n"+number);
        WebProducts result=pmp.selectByPrimaryKey(id);
        if(result!=null){
            result.setNumber(result.getNumber()+number);
            if(pmp.updateByPrimaryKey(result)>0)
                return result;
        }
        return null;
    }

    public String getID(){
        String ans=String.valueOf(pmp.getRows()+1);
        int len=ans.length();
        for(int i=0;i<4-len;++i){
            ans="0"+ans;
        }
        return ans;
    }

    @RequestMapping(value = "/add_new_product",method = RequestMethod.POST)
    @ResponseBody
    public String addNewProduct(String name,double price,int number){
        System.out.println("name:"+name+"\nprice:"+price+"\nnumber:"+number);
        if(pmp.selectByName(name).isEmpty()){
            if(pmp.insert(new WebProducts(getID(),name,price,number))>0)
                return "进购成功！";
            else
                return "进购失败！";
        }
        else{
            return "该物品已存在！";
        }
    }

    @RequestMapping(value = "/check_order",method = RequestMethod.GET)
    public String checkOrder(){
        return "checkOrder";
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


    @RequestMapping(value = "/get_all_order",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getAllOrder(){
        List<Map<String,Object>> l=new ArrayList<>();
        List<WebOrder> result=omp.selectAll();
        for(WebOrder i:result){
            l.add(getOrder(i));
        }
        return l;
    }

    @RequestMapping(value = "/cashier_performance")
    public String cashierPerformance(){
        return "cashierPerformance";
    }

    @RequestMapping(value = "/get_performance")
    @ResponseBody
    public List<Map<String,Object>> getPerformance(){
        System.out.println("getPerformance");
        List<WebAccount> a=amp.selectAllCashier();
        List<WebOrder> o=omp.selectAll();
        List<Map<String,Object>> l=new ArrayList<>();
        for(WebAccount i:a){
            if(i.getType()!=1)
                continue;
            int totalNumber=0;
            int totalMoney=0;
            Map<String,Object> m=new HashMap<>();
            WebUser u=ump.selectByPrimaryKey(i.getAccount());
            if(u==null)
                continue;
            m.put("name",u.getName());
            m.put("id",i.getAccount());
            for(WebOrder j:o){
                if(j.getCashierAccount().equals(i.getAccount())){
                    totalNumber+=j.getNumber();
                    totalMoney+=pmp.selectByPrimaryKey(j.getProductId()).getPrice()*j.getNumber();
                }
            }
            m.put("totalNumber",totalNumber);
            m.put("totalMoney",totalMoney);
            l.add(m);
        }
        return l;
    }

    @RequestMapping(value = "/sales_performance")
    public String salesPerformance(){
        return "salesPerformance";
    }

    @RequestMapping(value = "/get_sales_performance")
    @ResponseBody
    public List<Map<String,Object>> getSalesPerformance(){
        List<WebProducts> products=pmp.selectAll();
        List<WebOrder> orders=omp.selectAll();
        List<Map<String,Object>> result=new ArrayList<>();
        for(WebProducts i:products){
            Map<String,Object> m=new HashMap<>();
            int number=0;
            m.put("name",i.getName());
            m.put("price",i.getPrice());
            for(WebOrder j:orders){
                if(i.getId().equals(j.getProductId())){
                    number+=j.getNumber();
                }
            }
            m.put("number",number);
            System.out.println(m);
            result.add(m);
        }
        return result;
    }

    @RequestMapping(value = "/get_sales_performance_by_month")
    @ResponseBody
    public List<Map<String,Object>> getSalesPerformanceByMonth(int year,int month){
        //System.out.println("year:"+year+"\nmonth:"+month+"\n");
        List<WebProducts> products=pmp.selectAll();
        List<WebOrder> orders=omp.selectAll();
        List<Map<String,Object>> result=new ArrayList<>();
        for(WebProducts i:products){
            Map<String,Object> m=new HashMap<>();
            int number=0;
            m.put("name",i.getName());
            m.put("price",i.getPrice());
            for(WebOrder j:orders){
                if(i.getId().equals(j.getProductId())&&
                        j.getOrderTime().after(new Date(year-1900,month-1,1))&&
                        j.getOrderTime().before(new Date(year-1900+(month==12?1:0),month==12?1:(month),1))
                        ){
                    number+=j.getNumber();
                }
            }
            m.put("number",number);
            //System.out.println(m);
            result.add(m);
        }
        return result;
    }
}
