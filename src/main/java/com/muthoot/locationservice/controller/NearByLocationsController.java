package com.muthoot.locationservice.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Controller
public class NearByLocationsController {
	private final int RADIUS = 10000;
	private final String LOCATION_NAME = "Muthoot";

	@GetMapping("/locateNearbyBranches")
	public String getNearByLocations(@RequestParam String currentLocation) {
		try {
			UriComponents uriComponents = UriComponentsBuilder
											.newInstance()
											.scheme("https")
											.host("atlas.mapmyindia.com")
											.path("/api/places/nearby/json")
											.queryParam("keywords", LOCATION_NAME)
											.queryParam("refLocation", currentLocation)
											.queryParam("radius", RADIUS)
											//.queryParam("searchBy", "dist")
											.build();
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			Request request = new Request.Builder()
									.url(uriComponents.toUriString()).method("GET", null)
									.addHeader("Authorization", "Bearer 3a4746f8-fe31-47fa-87d1-9340e4a0d2df")
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
	
	@GetMapping("/loadmap")
	public String testHtml(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "map";
	}
}
