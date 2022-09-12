package com.muthoot.locationservice.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Controller
public class DirectionsController {
	
	private final String REST_KEY = "5cbbe32a47e0e6e6abd994ad81b34573";
	private final String ROUTE_TYPE = "route_adv";
	private final String DRIVING_MODE = "biking";
	
	@GetMapping("/directions")
	public String getDirections(@RequestParam String source,@RequestParam String destination ) {
		String geoPositions = source+";"+destination;
		try {
			UriComponents uriComponents = UriComponentsBuilder
											.newInstance()
											.scheme("https")
											.host("apis.mapmyindia.com")
											.path("/advancedmaps/v1/")
											//.queryParam("REST_KEY", REST_KEY)
											.path(REST_KEY+"/"+ROUTE_TYPE+"/"+DRIVING_MODE+"/"+geoPositions)
											.build();
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			Request request = new Request.Builder()
									.url(uriComponents.toUriString()).method("GET", null)
									//.addHeader("Authorization", "Bearer 3a4746f8-fe31-47fa-87d1-9340e4a0d2df")
									.build();

			Response response = client.newCall(request).execute();
			System.out.println(response.body().string());
			return response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error!! Oops, something happened.";
		
	}

}
