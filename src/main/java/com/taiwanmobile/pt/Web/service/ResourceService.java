package com.taiwanmobile.pt.Web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.taiwanmobile.pt.Web.vo.Device;
import com.taiwanmobile.pt.Web.vo.Ipv4Address;
import com.taiwanmobile.pt.Web.vo.PhoneNumberRequest;
import com.taiwanmobile.pt.Web.vo.RoamingRequest;

@Service("ResourceService")
public class ResourceService {
	
	@Value("${apigw.number.verification.verify.url}")
	private String numberVerificationVerifyURL;
	@Value("${apigw.sim.swap.retrieve.date.url}")
	private String simSwapRetrieveDateURL;
	@Value("${apigw.roaming.status.roaming.url}")
	private String roamingStatusRoamingURL;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public String verify(String phoneNumber, Map<String, String> tokenMap) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(tokenMap.get("access_token"));
    	
    	PhoneNumberRequest pnRequest = new PhoneNumberRequest();
    	pnRequest.setPhoneNumber(phoneNumber);
    	
    	HttpEntity<PhoneNumberRequest> requestEntity = new HttpEntity<PhoneNumberRequest>(pnRequest, headers);
    	ResponseEntity<String> nvResp = restTemplate.exchange(numberVerificationVerifyURL, HttpMethod.POST, requestEntity, String.class);
    	
    	return nvResp.getBody();
	}
	
	
	public String retrieveDate(String phoneNumber, Map<String, String> tokenMap) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(tokenMap.get("access_token"));
    	
    	PhoneNumberRequest pnRequest = new PhoneNumberRequest();
    	pnRequest.setPhoneNumber(phoneNumber);
    	
    	HttpEntity<PhoneNumberRequest> requestEntity = new HttpEntity<PhoneNumberRequest>(pnRequest, headers);
    	ResponseEntity<String> ssResp = restTemplate.exchange(simSwapRetrieveDateURL, HttpMethod.POST, requestEntity, String.class);
    	
    	return ssResp.getBody();
	}
	
	
	public String roaming(String phoneNumber, Map<String, String> tokenMap) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(tokenMap.get("access_token"));
    	
    	Device device = new Device();
    	device.setPhoneNumber(phoneNumber);
    	Ipv4Address ip4 = new Ipv4Address();
    	device.setIpv4Address(ip4);
    	RoamingRequest rr = new RoamingRequest();
    	rr.setDevice(device);
    	
    	HttpEntity<RoamingRequest> requestEntity = new HttpEntity<RoamingRequest>(rr, headers);
    	ResponseEntity<String> rsResp = restTemplate.exchange(roamingStatusRoamingURL, HttpMethod.POST, requestEntity, String.class);
    	
    	return rsResp.getBody();
	}
	
}
