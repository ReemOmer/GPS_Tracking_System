package com.sust.gpstracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class HttpRmi {

	private HttpPost request = null;
	private List<NameValuePair> nameValuePairs = null;
	private int connectionTimeoutMillis = 30*1000;
	private int socketTimeoutMillis = 0;
	private HttpClient httpClient;
	
	public HttpRmi(String url){
		request = new HttpPost(url);
		nameValuePairs = new ArrayList<NameValuePair>(1);
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeoutMillis);
		HttpConnectionParams.setSoTimeout(httpParams, socketTimeoutMillis);		
		HttpProtocolParams.setContentCharset(httpParams, "utf-8");

		httpClient = new DefaultHttpClient(httpParams);
	}
	
	public HttpRmi add(String key, String value){
		nameValuePairs.add(new BasicNameValuePair(key, value));
		return this;
	}
	
	public String execute() throws IOException{
		String response = "";
		try{ 
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8" ) );
			
			response = httpClient.execute(request, new BasicResponseHandler());
		}catch(Exception e){
			e.printStackTrace();
			throw new IOException("no connection");
		}
		
		return response;
	}
	
}
