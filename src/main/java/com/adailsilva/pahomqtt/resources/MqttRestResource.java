package com.adailsilva.pahomqtt.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adailsilva.pahomqtt.mqtt.publishers.MqttPublisherBase;

/* Demo Controller to Test Rest API */

@RestController
public class MqttRestResource {

	@Autowired
	MqttPublisherBase publisher;

	@RequestMapping(value = "/adailsilva/mqtt/send", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String index(@RequestBody String data) {
		publisher.publishMessage("/adailsilva/notifications", data);
		return "Message sent to Broker";
	}

}
