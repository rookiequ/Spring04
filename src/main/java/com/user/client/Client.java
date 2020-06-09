package com.user.client;

import com.user.proxy.IProducer;
import com.user.proxy.Producer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zzq
 */
public class Client {



    /**
     *  动态代理：AOP的实现原理
     *
     *  作用：不修改源码的基础上对方法进行增强
     *  分类：
     *      基于接口的动态代理
     *      基于子类的动态代理
     *  如何创建代理对象
     *      使用Proxy类种的newProxyInstance方法
     *  创建动态代理对象的要求：
     *      被代理类至少实现一个接口
     *  newProxyInstance怎么用
     *      三个参数：
     *          ClassLoader:类加载器
     *              加载代理对象的字节码，和被代理对象使用相同的类加载器
     *          Class[]:字节码数组
     *              用于让代理对象和被代理对象拥有相同的方法
     *          InvocationHandler:
     *              用于提供增强的代码，这是一个接口，我们要实现它
     *
     * @param args
     */
    public static void main(String[] args) {
        //代理商
        final Producer producer = new Producer();
        IProducer producerProxy = (IProducer) Proxy.newProxyInstance(
                producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 作用: 执行被代理对象的任何接口方法都会经过该方法
                     * 参数：
                     * @param proxy 代理对象的引用
                     * @param method 当前执行的方法
                     * @param args 当前执行方法的参数
                     * @return 和被代理对象有相同的返回值
                     * @throws Throwable
                     */
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //下面是增强的代码
                        //1.首先定义返回值，代理后由代理对象提供
                        Object returnValue = null;
                        //2.判断当前方法是不是销售
                        if ("saleProduct".equals(method.getName())) {
                            //3.获取方法的执行参数
                            Float money = (Float) args[0];
                            //4.执行方法，第一个参数为当前方法的对象，第二个参数为方法所需的参数
                            returnValue = method.invoke(producer, money * 0.8f);
                        };
                        return returnValue;
                    }

                });
        //用户付了10000块
        producerProxy.saleProduct(10000.f);
    }
}
