package java8.base.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/**
 * 代理方法：增强
 */
public class InvocationHandlerTest implements InvocationHandler{
    //代理对象
    private Object proxyObject;
    public InvocationHandlerTest(Object proxyObject){
        this.proxyObject=proxyObject;
    }
    /**
     * 代码增强部分
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        System.out.println("被代理方法："+method.getName());
        System.out.println("代理前");
        Object invoke = method.invoke(proxyObject, args);
        System.out.println("代理后");
        return invoke;
    }
}
