package it.madlabs.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class SimplePreFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(SimplePreFilter.class);

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SEND_RESPONSE_FILTER_ORDER;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		for (String key : ctx.keySet()) {
			log.info(String.format("Context value for %s:=[%s]", key, ctx.get(key)));
		}
		return null;
	}
}
