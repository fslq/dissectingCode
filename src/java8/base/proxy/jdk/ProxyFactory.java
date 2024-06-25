package java8.base.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * 获取代理对象的工厂
 */
public class ProxyFactory {
    /**
     * 获取实例
     * @param proxyObject 被代理对象
     * @return
     */
    public static Object getProxyInstance(Object proxyObject){
        return Proxy.newProxyInstance(proxyObject.getClass().getClassLoader(),proxyObject.getClass().getInterfaces(), new InvocationHandlerTest(proxyObject));
    }
}
