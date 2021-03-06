package com.dachen.medicine.volley.custom;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.dachen.medicine.common.utils.LogUtils;
import com.dachen.medicine.entity.Result;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 通过字符串参数集，请求json参数，并序列号为JsonModel对象
 * 
 * @author dty
 * 
 * @param <T>
 */
public class StringJsonArrayRequest<T> extends Request<String> {

	public static interface Listener<T> {
		void onResponse(ArrayList<T> result);
		void onFailure(Exception e, String errorMsg,int s);
	}

	private Listener<T> mListener;
	private Class<T> mClazz;
	private Map<String, String> mParams;
	private Gson gson = new Gson();
	private boolean mGzipEnable = false;

	/**
	 * 
	 * 请求方式post
	 * 
	 * @param url
	 *            url地址
	 * @param listener
	 */
	public StringJsonArrayRequest(String url, ErrorListener errorListener,
			Listener<T> listener, Class<T> clazz, Map<String, String> params) {
		this(Method.POST, url, errorListener, listener, clazz, params);
	}

	/**
	 * 
	 * @param method
	 *            请求方式，post或者get
	 * @param url
	 *            url地址
	 * @param listener
	 */
	public StringJsonArrayRequest(int method, String url,
			ErrorListener errorListener, Listener<T> listener, Class<T> clazz,
			Map<String, String> params) {
		super(method, url, errorListener);
		mListener = listener;
		mClazz = clazz;
		mParams = params;
		if (method == Method.GET) {
			spliceGetUrl();
		}
	}

	public void setGzipEnable(boolean eanble) {
		mGzipEnable = eanble;
	}

	/* Post 参数设置 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		if (getMethod() != Method.POST && getMethod() != Method.PUT) {
			return null;
		}

		return mParams;
	}
	@Override
	public void deliverError(VolleyError error) {
		System.err.println("ERROR"+error.getMessage());
		// TODO Auto-generated method stub 
		if (null!=error&&error.getMessage().contains("ConnectException")) {
			mListener.onFailure(error, "", 3); 
		}else {
			mListener.onFailure(error, "", 2); 
		}  
		super.deliverError(error);
	}
	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {
		System.err.println("volleyError"+volleyError.getMessage()); 
		// TODO Auto-generated method stub 
		return super.parseNetworkError(volleyError);
	}
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		if (mGzipEnable) {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Charset", "UTF-8");
			headers.put("Content-Type", "application/x-javascript");
			headers.put("Accept-Encoding", "gzip,deflate");
			return headers;
		} else {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Charset", "UTF-8");
		//	headers.put("content-length", "100000000");
			headers.put("Content-Type", "application/x-www-form-urlencoded");
			return headers;
		}
	}

	/* Get 参数拼接 */
	private void spliceGetUrl() {
		if (mParams != null && mParams.size() > 0) {
			String url = getUrl();
			if (TextUtils.isEmpty(url)) {
				return;
			}
			if (url != null && !url.contains("?")) {
				url += "?";
			}
			String param = "";
			for (String key : mParams.keySet()) {
				param += (key + "=" + mParams.get(key) + "&");
			}
			param = param.substring(0, param.length() - 1);// 去掉最后一个&
			setUrl(url + param);
		}
	}

	@Override
	protected void deliverResponse(String arg0) { 
		if (mListener == null) {
			return;
		} 
		LogUtils.burtLog(LogUtils.W,"arg0=="+arg0);
		if (TextUtils.isEmpty(arg0)) {
			deliverError(new VolleyError(new NetworkError()));
			return;
		}
		if (arg0.toString().contains("#type")&&arg0.toString().contains("#message")
				&&arg0.toString().contains("hippo")) {
			if (arg0.contains("没有登录或登录已超时")){
				mListener.onFailure(new VolleyError(new NetworkError()), "", Result.CODE_TOKEN_ERROR);
				return;
			}
			mListener.onFailure(new VolleyError(new NetworkError()), "", 4);
			return;
		}
		ArrayList<T> list = new ArrayList<T>();
		try {
		//	System.err.println("arg0==" + arg0);

			/*list = gson.fromJson(arg0, new TypeToken<List<T>>() {
			}.getType()); */

			JsonArray array = new JsonParser().parse(arg0).getAsJsonArray();
			 for(final JsonElement elem : array){
			 list.add(new Gson().fromJson(elem, mClazz));
			 }

		} catch (Exception e) { 
			mListener.onFailure(new VolleyError(new NetworkError()), "", 4); 
			e.printStackTrace();
			return;
		}
		mListener.onResponse(list);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		String parsed;
		try {
			if (mGzipEnable) {
				parsed = getRealString(response.data);
			} else {
				parsed = new String(response.data,
						HttpHeaderParser.parseCharset(response.headers));
			}
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return Response.success(parsed,
				HttpHeaderParser.parseCacheHeaders(response));
	}

	private int getShort(byte[] data) {
		return (int) ((data[0] << 8) | data[1] & 0xFF);
	}

	/**
	 * GZip解压缩
	 */
	private String getRealString(byte[] data) {
		byte[] h = new byte[2];
		h[0] = (data)[0];
		h[1] = (data)[1];
		int head = getShort(h);
		boolean t = head == 0x1f8b;
		InputStream in;
		StringBuilder sb = new StringBuilder();
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			if (t) {
				in = new GZIPInputStream(bis);
			} else {
				in = bis;
			}
			BufferedReader r = new BufferedReader(new InputStreamReader(in),
					1000);
			for (String line = r.readLine(); line != null; line = r.readLine()) {
				sb.append(line);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
