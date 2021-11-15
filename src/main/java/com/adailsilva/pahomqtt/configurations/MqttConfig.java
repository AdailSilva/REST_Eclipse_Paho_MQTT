package com.adailsilva.pahomqtt.configurations;

public abstract class MqttConfig {

	protected final String broker = "localhost";
	protected final int qos = 2;
	protected Boolean hasSSL = false; /* By default SSL is disabled */
	protected Integer port = 1883; /* Default port */
	protected final String userName = "adailsilva";
	protected final String password = "@Hacker101";
	protected final String TCP = "tcp://";
	protected final String SSL = "ssl://";

	/* Default Configuration */
	protected abstract void config();

	/* Custom Configuration */
	protected abstract void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass);

}
