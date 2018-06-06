package com.manager.pojo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ProduceTest {

    @Test
    public void newProduce(){
        Produce produce = new Produce();
        System.out.println(produce.toString());
    }
}
