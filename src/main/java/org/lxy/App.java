package org.lxy;

import org.check.Train;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Hello world!
 */
@RestController
@EnableAutoConfiguration
public class App {
    public int times = 1;
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/index")
    String home() {
        return "Hello World";
    }

    @RequestMapping("/gaussian")
    String gaussian(@RequestParam("size")int size,@RequestParam("slide")int slide,
                    @RequestParam("time_start")int timeStart,@RequestParam("time_size")int timeSize) {
        this.times++;
        System.out.println("time:"+times+"\tsize:"+size+"slide:"+slide);
        Train train = new Train();
        return train.trainAuthorized(size,slide,timeStart,timeSize);
    }
}
