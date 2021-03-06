package com.dachen.medicine.net;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * 浣滆� yuankangle 鏃堕棿 2015/7/14
 */
public class GsonRequest<T> extends Request<T> {

	/**
	 * Gson parser
	 */
	private final Gson mGson;

	/**
	 * Class type for the response
	 */
	private final Class<T> mClass;

	Map<String, String> params;
	/**
	 * Callback for response delivery
	 */
	private final Response.Listener<T> mListener;

	/**
	 * @param method
	 *            Request type.. Method.GET etc
	 * @param url
	 *            path for the requests
	 * @param objectClass
	 *            expected class type for the response. Used by gson for
	 *            serialization.
	 * @param listener
	 *            handler for the response
	 * @param errorListener
	 *            handler for errors
	 */
	public GsonRequest(int method, Map<String, String> params, String url,
			Class<T> objectClass, Response.Listener<T> listener,
			Response.ErrorListener errorListener) {

		super(method, url, errorListener);
		this.mClass = objectClass;
		this.mListener = listener;
		mGson = new Gson();
		this.params = params;
	}

	/* Post 参数设置 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		if (getMethod() != Method.POST && getMethod() != Method.PUT) {
			return null;
		}

		return params;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			System.err.println("jsonafdadfafasf---=" + json);
			return Response.success(mGson.fromJson(json, mClass),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);

	}

}