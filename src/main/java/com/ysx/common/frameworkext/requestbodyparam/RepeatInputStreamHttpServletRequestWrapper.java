package com.ysx.common.frameworkext.requestbodyparam;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

/**
 * @Description: 解决 request 不能获取两次得问题，重写 getInputStream
 * @author ysx
 */
public class RepeatInputStreamHttpServletRequestWrapper extends HttpServletRequestWrapper implements Serializable{

	private static final long serialVersionUID = 7201425890527231947L;
	private byte[] requestBody = null;

	RepeatInputStreamHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		// 缓存请求body
		try {
			requestBody = StreamUtils.copyToByteArray(request.getInputStream());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重写 getInputStream()
	 */
	@Override
	public ServletInputStream getInputStream() {
		if (requestBody == null) {
			requestBody = new byte[0];
		}
		final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
		return new ServletInputStream() {
			@Override
			public int read(){
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}
			@Override
			public boolean isReady() {
				return false;
			}
			@Override
			public void setReadListener(ReadListener listener) {
			}
		};
	}

	/**
	 * 重写 getReader()
	 */
	@Override
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

}