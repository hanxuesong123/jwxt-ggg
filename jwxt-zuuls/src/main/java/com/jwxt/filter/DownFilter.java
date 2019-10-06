package com.jwxt.filter;

import com.alibaba.fastjson.JSON;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.JSONUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
/**降级*/
@Component
public class DownFilter extends ZuulFilter {

    @Value("${down.service}")
    private String BASIC_CONF;

    public DownFilter(){
        super();
    }


    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE; //route
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {

        Object success = RequestContext.getCurrentContext().get("isSuccess");

        return false;
        //return success == null ? true : Boolean.parseBoolean(success.toString());
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext context = RequestContext.getCurrentContext();

        Object serviceId = context.get("serviceId");

        if(serviceId != null && BASIC_CONF != null){
            List<String> serviceIds = Arrays.asList(BASIC_CONF.split(","));
            if(serviceIds.contains(serviceId.toString())){
                context.setSendZuulResponse(false);
                context.set("isSuccess",false);
                Result result = new Result(ResultCode.SERVICE_IS_DOWN);
                context.setResponseBody(JSON.toJSONString(result));
                context.getResponse().setContentType("application/json;charset=utf-8");
                return null;
            }
        }

        return null;
    }
}
