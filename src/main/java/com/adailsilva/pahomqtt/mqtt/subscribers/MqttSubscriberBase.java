package com.adailsilva.pahomqtt.mqtt.subscribers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adailsilva.pahomqtt.mqtt.publishers.MqttPublisherBase;

/* MQTT Subscriber Configuration Base Interface */

public interface MqttSubscriberBase {

//	public static final Logger logger = LoggerFactory.getLogger(MqttSubscriberBase.class);
	public static final Logger logger = LoggerFactory.getLogger(MqttPublisherBase.class);

	/* Subscribe message */
	public void subscribeMessage(String topic);

	/* Disconnect MQTT Client */
	public void disconnect();

}
