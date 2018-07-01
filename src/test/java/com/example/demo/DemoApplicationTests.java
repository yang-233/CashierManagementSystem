package com.example.demo;

import com.example.demo.dao.WebAccountMapper;
import com.example.demo.dao.WebOrderMapper;
import com.example.demo.dao.WebProductsMapper;
import com.example.demo.dao.WebUserMapper;
import com.example.demo.model.WebAccount;
import com.example.demo.model.WebOrder;
import com.example.demo.model.WebProducts;
import com.example.demo.model.WebUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private WebAccountMapper mapper;

    @Autowired
    private WebUserMapper ump;

    @Autowired
    private WebProductsMapper pmp;

    @Autowired
    private WebOrderMapper omp;
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
    @Test
    public void contextLoads() {

        System.out.println(pmp.getRows());
    }
}
