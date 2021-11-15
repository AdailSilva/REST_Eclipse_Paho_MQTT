package com.adailsilva.pahomqtt.mqtt.publishers;

/* MQTT Publisher Configuration Base Interface */

public interface MqttPublisherBase {

//	public static final Logger logger = LoggerFactory.getLogger(MqttSubscriberBase.class);
//	public static final Logger logger = LoggerFactory.getLogger(MqttPublisherBase.class);

	/* Publish message */
	public void publishMessage(String topic, String message);

	/* Disconnect MQTT Client */
	public void disconnect();

}
