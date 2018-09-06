package org.yqj.boot.demo.resolve;

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Type;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/9/6
 * Email: qijunyao@xiaohongshu.com
 */
public class DogHourse implements House<Dog>{
    @Override
    public void liveInHouse(Dog a) {

    }

    public static void main(String[] args) {

        springSupport();

        localReal();
    }

    private static void localReal(){
        // 获取继承接口的范型
        Class<DogHourse> subClass = DogHourse.class;
        Class<House> interfaceClass = House.class;

        Type[] types = subClass.getGenericInterfaces();
        Type dogHourseType = types[0];

        // todo 实现获取接口的 范型参数类型
    }

    private static void springSupport(){
        /**
         * spring 支持对于 继承的 接口， 是 范型的类型， 实例化 proxy, 获取对应的 对象的范型类型。
         */
        System.out.println(GenericTypeResolver.resolveTypeArgument(DogHourse.class, House.class));
    }
}
