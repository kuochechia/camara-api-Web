package com.taiwanmobile.pt.Web.service;

import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Service("AuthService")
public class AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Value("${auth.client.id}")
	private String clientId;
	@Value("${auth.redirect.uri}")
	private String redirectUri;
	@Value("${auth.response.type}")
	private String responseType;
	@Value("${apigw.auth.request.url}")
	private String authRequestURL;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public String getAuthorizeUrl(String scope, String phoneNumber) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>(); 
    	params.add("client_id", clientId);
    	params.add("redirect_uri", redirectUri);
    	params.add("response_type", responseType);
    	params.add("scope", scope);
    	params.add("phone_number", phoneNumber);
    	
    	URI uri = UriComponentsBuilder.fromHttpUrl(authRequestURL)
    			.queryParams(params)
                .build()
                .toUri();
    	
    	ResponseEntity<String> authRequestResp = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
    	logger.info("authRequestResp: "+ authRequestResp);
    	String authResp = authRequestResp.getBody();
        Map<String, String> authMap = new Gson().fromJson(authResp, new TypeToken<Map<String, String>>(){}.getType());
        
        return authMap.get("authorizeUrl");
	}
	
	
	public Map<String, String> getAccessToken(String authorizeUrl) {
		ResponseEntity<String> authorizeResp = restTemplate.exchange(authorizeUrl, HttpMethod.GET, null, String.class);
    	logger.info("authorizeResp: " + authorizeResp);
    	String tokenInfo = authorizeResp.getBody();
    	
    	return new Gson().fromJson(tokenInfo, new TypeToken<Map<String, String>>(){}.getType());
	}
	
}
