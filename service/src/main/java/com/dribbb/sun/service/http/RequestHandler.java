package com.dribbb.sun.service.http;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * DataService异步调用方式的回传代理]
 */
public interface RequestHandler<T extends Request, R extends Response> {

	/**
	 * Request请求开始执行
	 * 注意不包括队列的排队时间，即Request请求真正被处理的时刻发出
	 * 
	 * @param req
	 *            被执行的Request请求
	 */
	void onRequestStart(T req);

	/**
	 * Request请求的执行进度<br>
	 * 是否支持进度回传由DataService的实现决定<br>
	 * count和total的具体含义由DataService的实现决定
	 * 
	 * @param req
	 *            被执行的Request请求
	 * @param count
	 * @param total
	 */
	void onRequestProgress(T req, int count, int total);

	/**
	 * 请求成功并返回<br>
	 * response.error = null<br>
	 * 
	 * @param req
	 *            被执行的Request请求
	 * @param resp
	 * 
	 */
	void onRequestFinish(T req, R resp) throws IOException;

	/**
	 * 请求成功并返回<br>
	 * response.error为异常原因<br>
	 * 
	 * @param req
	 *            被执行的Request请求
	 * @param resp
	 * 
	 */
	void onRequestFailed(T req, R resp);

}
