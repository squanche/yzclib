package com.yangzhichao.yzclib.aop.controller;

import com.yangzhichao.yzclib.aop.annotation.SendCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;

/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2019/12/31
 * @class <code>RedisCacheController</code>
 * @see
 * @since JDK1.8
 */
@Controller
public class RedisCacheController {


    @Value("${sql.aaa}")
    HashSet<String> hashSet;

    @SendCache(key = "dc:homePage")
    @RequestMapping(value = "/redisTest",method = RequestMethod.GET)
    @ResponseBody
    public String cacheTest() {

       if( hashSet.contains(" ")){
           System.out.println("aaaa");
       }

        return "data from database";



    }

}