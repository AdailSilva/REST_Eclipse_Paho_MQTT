package com.adailsilva.pahomqtt.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adailsilva.pahomqtt.mqtt.subscribers.MqttSubscriberBase;

/* Listening to MQTT messages (Task) */

@Component
public class MessageListener implements Runnable {

	@Autowired
	MqttSubscriberBase subscriber;

	@Override
	public void run() {
		while (true) {
			subscriber.subscribeMessage("/adailsilva/notifications");
//			subscriber.subscribeMessage("application/2/#");
//			subscriber.subscribeMessage("application/2/device/+/event/up");
		}
	}

}
