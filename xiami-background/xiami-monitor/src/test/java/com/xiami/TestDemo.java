package com.xiami;

import com.xiami.job.MyJob;
import org.junit.Test;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­22 8:32
 */
public class TestDemo {

    @Test
    public void toClass(){
        String classDemo="com.xiami.job.MyJob1";

        try {
            Class clazz = Class.forName(classDemo);
            System.out.println("111111111");
            //MyJob myJob = (MyJob) clazz.newInstance();
            //myJob.testHi();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }
}
