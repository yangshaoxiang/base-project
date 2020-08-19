package com.ysx.common.frameworkext.requestbodyparam;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description:  过滤请求，将向下传递的 request 对象做包装,使 request 的流可以重复获取
 * @author ysx
 */
@Slf4j
public class RepeatInputStreamFilter implements Filter {


	@Override
	public void init(FilterConfig filterConfig) {
		log.info("------------ RepeatInputStreamFilter init ------------");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		String contentType = req.getContentType();
		// 只对 POST 和 PUT 请求 且 传参为 json 形式的做包装
		String method = httpServletRequest.getMethod();
		boolean methodMatch = "POST".equals(method)||"PUT".equals(method);
		boolean contentMatch = contentType!=null&&contentType.toLowerCase().contains("json");
		if(methodMatch&&contentMatch){
			req = new RepeatInputStreamHttpServletRequestWrapper(httpServletRequest);
		}
		chain.doFilter(req,res);
	}

	@Override
	public void destroy() {}

}
