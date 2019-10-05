package com.jwxt.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

/**网关限流过滤器，通过令牌桶算法实现  最先执行*/
@Component
public class LimiterFilter extends ZuulFilter {


    //volatile: 保持内存可见性   作用: 防止指令重排
    //内存可见性:指当某个线程正在使用对象状态而另一个线程在同时修改该状态，
    //需要确保当一个线程修改了对象状态后，其他线程能够立即看到发生的状态变化。

    //指令重排序是JVM为了优化指令，提高程序运行效率，在不影响单线程程序执行结果的前提下，
    //尽可能地提高并行度。编译器、处理器也遵循这样一个目标。注意是单线程
    private static volatile RateLimiter rateLimiter = RateLimiter.create(1);//当我们有一系列的任务需要执行，并且我们期望是每秒执行100个任务,速率是100个令牌/秒

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
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        if(!rateLimiter.tryAcquire()){
            RequestContext context = RequestContext.getCurrentContext();
            context.setSendZuulResponse(false);
            Result result = new Result(ResultCode.SERVICE_IS_RATE_LIMIT);
            context.setResponseBody(JSON.toJSONString(result));
            context.getResponse().setContentType("application/json;charset=utf-8");
            context.setResponseStatusCode(429); //429这个状态码在http协议中是指请求超过速率限制。
        }
        return null;
    }
}
