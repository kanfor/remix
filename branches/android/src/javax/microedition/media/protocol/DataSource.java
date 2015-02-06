package javax.microedition.media.protocol;

import javax.microedition.media.Controllable;

public abstract class DataSource extends java.lang.Object implements Controllable {
	private java.lang.String locator;

	public DataSource(java.lang.String locator) {
		// TODO implement DataSource
	}

	public java.lang.String getLocator() {
		return this.locator;
	}

	public abstract java.lang.String getContentType();

	public abstract void connect() throws java.io.IOException;

	public abstract void disconnect();

	public abstract void start() throws java.io.IOException;

	public abstract void stop() throws java.io.IOException;

	public abstract SourceStream[] getStreams();

}