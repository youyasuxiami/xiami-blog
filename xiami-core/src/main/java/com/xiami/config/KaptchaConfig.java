package com.xiami.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {


    @Bean(name = "kaptcha")
    public DefaultKaptcha kaptcha() {

        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();

        properties.setProperty("kaptcha.border", "no");// 图片边框
        properties.setProperty("kaptcha.textproducer.char.length", "4");//验证码个数
        properties.setProperty("kaptcha.textproducer.font.color", "64,158,255");//字体颜色
        //properties.setProperty("kaptcha.image.width", "166");// 图片宽
        //properties.setProperty("kaptcha.image.height", "47"); // 图片高
        properties.setProperty("kaptcha.noise.color", "red"); //验证码噪点颜色,默认为Color.BLACK

        // 边框颜色
        //properties.setProperty("kaptcha.border.color", "105,179,90");

        //properties.setProperty("kaptcha.background.impl",DefaultBackground );
        //properties.setProperty("kaptcha.background.clear.from","black" );
        //properties.setProperty("kaptcha.background.clear.to", "yellow");
        // 字体大小
        //properties.setProperty("kaptcha.textproducer.font.size", "30");
        // session key
        //properties.setProperty("kaptcha.session.key", "code");
        // 验证码长度
        //properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
        //properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");

        //可以设置很多属性,具体看com.google.code.kaptcha.Constants
//      kaptcha.border  是否有边框  默认为true  我们可以自己设置yes，no
//      kaptcha.border.color   边框颜色   默认为Color.BLACK
//      kaptcha.border.thickness  边框粗细度  默认为1
//      kaptcha.producer.impl   验证码生成器  默认为DefaultKaptcha
//      kaptcha.textproducer.impl   验证码文本生成器  默认为DefaultTextCreator
//      kaptcha.textproducer.char.string   验证码文本字符内容范围  默认为abcde2345678gfynmnpwx
//      kaptcha.textproducer.char.length   验证码文本字符长度  默认为5
//      kaptcha.textproducer.font.names    验证码文本字体样式  默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
//      kaptcha.textproducer.font.size   验证码文本字符大小  默认为40
//      kaptcha.textproducer.font.color  验证码文本字符颜色  默认为Color.BLACK
//      kaptcha.textproducer.char.space  验证码文本字符间距  默认为2
//      kaptcha.noise.impl    验证码噪点生成对象  默认为DefaultNoise
//      kaptcha.noise.color   验证码噪点颜色   默认为Color.BLACK
//      kaptcha.obscurificator.impl   验证码样式引擎  默认为WaterRipple
//      kaptcha.word.impl   验证码文本字符渲染   默认为DefaultWordRenderer
//      kaptcha.background.impl   验证码背景生成器   默认为DefaultBackground
//      kaptcha.background.clear.from   验证码背景颜色渐进   默认为Color.LIGHT_GRAY
//      kaptcha.background.clear.to   验证码背景颜色渐进   默认为Color.WHITE

        Config config = new Config(properties);
        kaptcha.setConfig(config);

        return kaptcha;

    }
}


