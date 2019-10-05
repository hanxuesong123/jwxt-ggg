package com.jwxt.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

/**限流过滤器  最先执行*/
@Component
public class LimiterFilter extends ZuulFilter {


    //volatile: 保持内存可见性   作用: 防止指令重排
    //内存可见性:指当某个线程正在使用对象状态而另一个线程在同时修改该状态，
    //需要确保当一个线程修改了对象状态后，其他线程能够立即看到发生的状态变化。

    //指令重排序是JVM为了优化指令，提高程序运行效率，在不影响单线程程序执行结果的前提下，
    //尽可能地提高并行度。编译器、处理器也遵循这样一个目标。注意是单线程
    private static volatile RateLimiter rateLimiter = RateLimiter.create(50);//当我们有一系列的任务需要执行，并且我们期望是每秒执行50个任务,速率是50个令牌/秒


    public LimiterFilter(){
        super();
    }


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        rateLimiter.acquire(3);
        return null;
    }
}
