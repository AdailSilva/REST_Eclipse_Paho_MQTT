package com.adailsilva.pahomqtt.resources;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adailsilva.pahomqtt.RestEclipsePahoMqttApplication;

@RestController
public class RestartController {
	
	@PostMapping(value = "/adailsilva/mqtt/restart")
	public void restartRestEclipsePahoMqttApplication() {
		RestEclipsePahoMqttApplication.restart();
	}

}
