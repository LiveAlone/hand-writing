package org.yqj.hwboot.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.HwSpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/8/15
 * Email: yaoqijunmail@foxmail.com
 */
@SpringBootApplication
@Controller
@Slf4j
public class BootDemoApplication {
    public static void main(String[] args) {
        log.info("start boot demo application context");
        new HwSpringApplication(BootDemoApplication.class)
                .run(args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String rootPage() throws UnknownHostException{
        return "host is " + InetAddress.getLocalHost().getHostName() + " with ip address " + InetAddress.getLocalHost().getHostAddress();
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String indexPage(){
        return "this is test index paging info test docker jenkins config 2222222222";
    }
}
