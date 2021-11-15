package com.adailsilva.pahomqtt.mqtt.subscribers;

import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adailsilva.pahomqtt.RestEclipsePahoMqttApplication;
import com.adailsilva.pahomqtt.configurations.MqttConfig;

/* MQTT Subscriber Class */

@Component
public class MqttSubscriber extends MqttConfig implements MqttCallback, MqttSubscriberBase {

	private String brokerUrl = null;

	final private String colon = ":";
	final private String clientId = "javaSubscriberClient";

	private MqttClient mqttClient = null;
	private MqttConnectOptions mqttConnectOptions = null;
	private MemoryPersistence memoryPersistence = null;

	private static final Logger logger = LoggerFactory.getLogger(MqttSubscriber.class);

	/* Private default constructor */
	private MqttSubscriber() {
		this.config();
	}

	@Override
	protected void config() {

		this.brokerUrl = this.TCP + this.broker + colon + this.port;
		this.memoryPersistence = new MemoryPersistence();
		this.mqttConnectOptions = new MqttConnectOptions();

		try {
			this.mqttClient = new MqttClient(brokerUrl, clientId, memoryPersistence);
			this.mqttConnectOptions.setCleanSession(true);
			this.mqttConnectOptions.setUserName(this.userName);
			this.mqttConnectOptions.setPassword(this.password.toCharArray());
			this.mqttClient.connect(this.mqttConnectOptions);
			this.mqttClient.setCallback(this);
		} catch (MqttException me) {
			logger.error("Error in the default configuration builder settings", me);
			me.printStackTrace();
			System.out.println(">>> Exception <<<");
			System.out.println("Reason: " + me.getReasonCode());
			System.out.println("Message: " + me.getMessage());
			System.out.println("Location: " + me.getLocalizedMessage());
			System.out.println("Cause: " + me.getCause());
			System.out.println("Exception: " + me);
			// Restart the entire application
			// API reset implementation in case of failure to connect with MQTT broker
			RestEclipsePahoMqttApplication.restart();
		}
	}

	@Override
	protected void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {

		String protocol = this.TCP;
		if (true == ssl) {
			protocol = this.SSL;
		}

		this.brokerUrl = protocol + this.broker + colon + this.port;
		this.memoryPersistence = new MemoryPersistence();
		this.mqttConnectOptions = new MqttConnectOptions();

		try {
			this.mqttClient = new MqttClient(brokerUrl, clientId, memoryPersistence);
			this.mqttConnectOptions.setCleanSession(true);
			if (true == withUserNamePass) {
				if (userName != null) {
					this.mqttConnectOptions.setUserName(this.userName);
				}
				if (password != null) {
					this.mqttConnectOptions.setPassword(this.password.toCharArray());
				}
			}
			this.mqttClient.connect(this.mqttConnectOptions);
			this.mqttClient.setCallback(this);
		} catch (MqttException me) {
			logger.error("Error in custom configuration builder settings", me);
			me.printStackTrace();
			System.out.println(">>> Exception <<<");
			System.out.println("Reason: " + me.getReasonCode());
			System.out.println("Message: " + me.getMessage());
			System.out.println("Location: " + me.getLocalizedMessage());
			System.out.println("Cause: " + me.getCause());
			System.out.println("Exception: " + me);
			// Restart the entire application
			// API reset implementation in case of failure to connect with MQTT broker
			RestEclipsePahoMqttApplication.restart();
		}
	}

	@Override
	public void subscribeMessage(String topic) {
		try {
			this.mqttClient.subscribe(topic, this.qos);
		} catch (MqttException me) {
			logger.error("Error in subscribeMessage", me);
			me.printStackTrace();
			System.out.println(">>> Exception <<<");
			System.out.println("Reason: " + me.getReasonCode());
			System.out.println("Message: " + me.getMessage());
			System.out.println("Location: " + me.getLocalizedMessage());
			System.out.println("Cause: " + me.getCause());
			System.out.println("Exception: " + me);
			// Restart the entire application
			// API reset implementation in case of failure to connect with MQTT broker
			RestEclipsePahoMqttApplication.restart();
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		logger.info("Connection Lost");

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		/*
		 * Called when a message arrives from the server that matches any subscription
		 * made by the client.
		 */
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println();
		System.out.println("***********************************************************************");
		System.out.println("Message Arrived at Time: " + time + "  \nTopic: " + topic + "  \nMessage: \n"
				+ new String(message.getPayload()));
		System.out.println("***********************************************************************");
		System.out.println();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

		/* Leave it blank for subscriber */

	}

	public void disconnect() {
		try {
			this.mqttClient.disconnect();
		} catch (MqttException me) {
			logger.error("Error disconnecting", me);
			me.printStackTrace();
			System.out.println(">>> Exception <<<");
			System.out.println("Reason: " + me.getReasonCode());
			System.out.println("Message: " + me.getMessage());
			System.out.println("Location: " + me.getLocalizedMessage());
			System.out.println("Cause: " + me.getCause());
			System.out.println("Exception: " + me);
		}
	}

}
