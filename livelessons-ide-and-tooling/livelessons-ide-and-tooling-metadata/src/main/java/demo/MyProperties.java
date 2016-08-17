package demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myapp")
public class MyProperties {

	/**
	 * The remote address.
	 */
	private String address = "localhost";

	/**
	 * The connection port.
	 */
	private int port = 9000;

	/**
	 * The connection type.
	 */
	private ConnectionType type = ConnectionType.NORMAL;

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ConnectionType getType() {
		return this.type;
	}

	public void setType(ConnectionType type) {
		this.type = type;
	}

}
