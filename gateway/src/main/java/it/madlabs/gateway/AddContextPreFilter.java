package it.madlabs.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddContextPreFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(AddContextPreFilter.class);

	@Autowired
	private DiscoveryClient discoveryClient;

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SEND_FORWARD_FILTER_ORDER;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	private Map<String,String> rewriteRules = new HashMap<String, String>()
	{
		{
			put("anag", "/anag");
		}
	};

	@Override
	public Object run() {
		log.debug("Rewriting urls for legagy gateway");

		RequestContext ctx = RequestContext.getCurrentContext();
		String contextURI = (String) ctx.get("requestURI");
		String proxy = (String) ctx.get("proxy");

		List<ServiceInstance> res = this.discoveryClient.getInstances(proxy);
		String contextPath = res.get(0).getMetadata().get("contextPath");
		if (contextPath != null) {
			ctx.put("requestURI", contextPath + contextURI);
			log.debug("Rewriting url for " + proxy);
		}
		HttpServletRequest request = ctx.getRequest();
		log.info(String.format("Modified: %s request to %s", request.getMethod(), request.getRequestURL().toString()));

		return null;
	}

//	@Override
//	public Object run() {
//		RequestContext ctx = RequestContext.getCurrentContext();
//		HttpServletRequest request = ctx.getRequest();
//
//		log.info(String.format("Original: %s request to %s", request.getMethod(), request.getRequestURL().toString()));
//
//		try {
//			String url = UriComponentsBuilder.fromHttpUrl(extractCompleteContextPath(request)).path("anag").build().toUriString();
//			URL routeHost = new URL(url);
//			log.info(String.format("url: %s", url));
//			log.info(String.format("routeHost: %s", routeHost));
//			ctx.setRouteHost(routeHost);
//
//
//			//ctx.set("requestURI", url);
//
//			request = ctx.getRequest();
//			log.info(String.format("Modified: %s request to %s", request.getMethod(), request.getRequestURL().toString()));
//
//		} catch(MalformedURLException mue) {
//			log.error("Cannot forward to outage period endpoint");
//		}
//		return null;
//	}

	public static String extractCompleteContextPath(HttpServletRequest req) {

		String scheme = req.getScheme();             // http
		String serverName = req.getServerName();     // hostname.com
		int serverPort = req.getServerPort();        // 80
		String contextPath = req.getContextPath();   // /mywebapp

//		String servletPath = req.getServletPath();   // /servlet/MyServlet
//		String pathInfo = req.getPathInfo();         // /a/b;c=123
//		String queryString = req.getQueryString();          // d=789

		// Reconstruct original requesting URL
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if (serverPort != 80 && serverPort != 443) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath);
//		url.append(servletPath);
//
//		if (pathInfo != null) {
//			url.append(pathInfo);
//		}
//		if (queryString != null) {
//			url.append("?").append(queryString);
//		}
		return url.toString();
	}
}
