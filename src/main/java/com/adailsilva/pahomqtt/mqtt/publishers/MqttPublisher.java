package com.adailsilva.pahomqtt.mqtt.publishers;

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

/* MQTT Publisher class */

@Component
public class MqttPublisher extends MqttConfig implements MqttCallback, MqttPublisherBase {

	private String brokerUrl = null;

	final private String colon = ":";
	final private String clientId = "javaPublisherClient";

	private MqttClient mqttClient = null;
	private MqttConnectOptions mqttConnectOptions = null;
	private MemoryPersistence memoryPersistence = null;

	private static final Logger logger = LoggerFactory.getLogger(MqttPublisher.class);

	/* Private default constructor */
	private MqttPublisher() {
		this.config();
	}

	/* Private constructor */
	private MqttPublisher(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
		this.config(broker, port, ssl, withUserNamePass);
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

	/* Factory method to get instance of MqttPublisher */
	public static MqttPublisher getInstance() {
		return new MqttPublisher();
	}

	/* Factory method to get instance of MqttPublisher */
	public static MqttPublisher getInstance(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
		return new MqttPublisher(broker, port, ssl, withUserNamePass);
	}

	@Override
	public void publishMessage(String topic, String message) {

		try {
			MqttMessage mqttMessage = new MqttMessage(message.getBytes());
			mqttMessage.setQos(this.qos);
			this.mqttClient.publish(topic, mqttMessage);
		} catch (MqttException me) {
			logger.error("Error posting message", me);
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
	public void connectionLost(Throwable arg0) {
		logger.info("Connection Lost");

	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {

		/* Leave it blank for Publisher */

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		logger.info("Delivery completed");

	}

	@Override
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
			// Restart the entire application
			// API reset implementation in case of failure to connect with MQTT broker
			RestEclipsePahoMqttApplication.restart();
		}
	}

}
