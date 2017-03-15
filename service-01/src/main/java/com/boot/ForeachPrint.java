package com.boot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by abee on 17-3-14.
 */
@Component
public class ForeachPrint implements CommandLineRunner {

    @Value("${from}")
    private String from;


    @Override
    public synchronized void run(String... strings) throws Exception {


        for (int i = 0;0<1;i++){
            System.err.println("service-01   我是  :" + from);


            Thread.sleep(1000);

        }

    }


}
