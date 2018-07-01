package com.example.demo;

import java.util.Random;

public class MyTest {
    Random r;
    public MyTest(){
        r=new Random();
    }
    public String buildPhoneNumber(){
        String ans="13";
        for(int i=0;i<9;i++){
            ans+=String.valueOf(r.nextInt(10));
        }
        return ans;
    }

    public String buildName(){
        String ans="";
        int lens=3+r.nextInt(10);
        for(int i=0;i<lens;++i){
            ans+=(char)('a'+r.nextInt(26));
        }
        return ans;
    }
    public int buildAge(){
        return 20+r.nextInt(30);
    }
    public int buildSex(){
        return r.nextInt(2);
    }
    public static void main(String args[]){

    }
}
