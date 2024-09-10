package com.taiwanmobile.pt.Web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.taiwanmobile.pt.Web.service.AuthService;
import com.taiwanmobile.pt.Web.service.ResourceService;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class WebController {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private ResourceService resourceService;
	
	
    @GetMapping("/number_verification")
    public String nvIndex(Model model) {
        return "number_verification_index";
    }
    
    
    @PostMapping("/verify")
    public String verify(HttpServletResponse response, @RequestParam("phoneNumber") String phoneNumber, Model model) {
    	String authorizeUrl = authService.getAuthorizeUrl("number-verification-verify-read", phoneNumber);
    	Map<String, String> tokenMap = authService.getAccessToken(authorizeUrl);
    	String nvRespInfo = resourceService.verify(phoneNumber, tokenMap);
    	
    	model.addAttribute("response", nvRespInfo);
    	
		return "number_verification_result";
    }
    
    
    @GetMapping("/sim_swap")
    public String ssIndex(Model model) {
        return "sim_swap_index";
    }
    
    
    @PostMapping("/retrieve-date")
    public String retrieveDate(HttpServletResponse response, @RequestParam("phoneNumber") String phoneNumber, Model model) {
    	String authorizeUrl = authService.getAuthorizeUrl("retrieve-sim-swap-date", phoneNumber);
    	Map<String, String> tokenMap = authService.getAccessToken(authorizeUrl);
    	String ssRespInfo = resourceService.retrieveDate(phoneNumber, tokenMap);
    	
    	model.addAttribute("response", ssRespInfo);
    	
		return "sim_swap_result";
    }
    
    
    @GetMapping("/roaming_status")
    public String rsIndex(Model model) {
        return "roaming_status_index";
    }
    
    
    @PostMapping("/roaming")
    public String roaming(HttpServletResponse response, @RequestParam("phoneNumber") String phoneNumber, Model model) {
    	String authorizeUrl = authService.getAuthorizeUrl("device-status:roaming:read", phoneNumber);
    	Map<String, String> tokenMap = authService.getAccessToken(authorizeUrl);
    	String rsRespInfo = resourceService.roaming(phoneNumber, tokenMap);
    	
    	model.addAttribute("response", rsRespInfo);
    	
		return "roaming_status_result";
    }
    
}