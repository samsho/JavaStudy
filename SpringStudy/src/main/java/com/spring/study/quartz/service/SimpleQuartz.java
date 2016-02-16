package com.spring.study.quartz.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * ClassName: BackTablesDelQZ
 * Description: 删除回收站表定时器
 * Date: 2016/2/14 8:40
 *
 * @author sm12652
 * @version V1.0
 */
@Component
public class SimpleQuartz{

    private static final Logger logger = LogManager.getLogger(SimpleQuartz.class);

    public void run() {
        logger.info("............");
    }

    public static void main(String[] args) {
        SimpleQuartz simpleQuartz = new SimpleQuartz();
        simpleQuartz.run();
    }
}
