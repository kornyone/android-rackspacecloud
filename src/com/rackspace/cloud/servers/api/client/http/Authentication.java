/**
 * 
 */
package com.rackspace.cloud.servers.api.client.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.rackspace.cloud.servers.api.client.Account;

/**
 * @author Mike Mayo mike@overhrd.com
 *
 */
public class Authentication {

	public static boolean authenticate() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet get = new HttpGet("https://auth.api.rackspacecloud.com/v1.0");
		
		get.addHeader("X-Auth-User", Account.getUsername());
		get.addHeader("X-Auth-Key", Account.getApiKey());
		
		try {			
			HttpResponse resp = httpclient.execute(get);
		    System.out.println(resp.getStatusLine().toString()); 			
		    
		    if (resp.getStatusLine().getStatusCode() == 204) {
		    	Account.setAuthToken(resp.getFirstHeader("X-Auth-Token").getValue());
		    	Account.setServerUrl(resp.getFirstHeader("X-Server-Management-Url").getValue());
		    	Account.setStorageUrl(resp.getFirstHeader("X-Storage-Url").getValue());
		    	Account.setCdnManagementUrl(resp.getFirstHeader("X-Cdn-Management-Url").getValue());
		    	return true;
		    } else {
		    	return false;
		    }
		} catch (ClientProtocolException cpe) {
			// TODO Auto-generated catch block
			cpe.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}