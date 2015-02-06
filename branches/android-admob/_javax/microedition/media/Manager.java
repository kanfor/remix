package javax.microedition.media;

import java.io.IOException;

public class Manager {
	public static final String TONE_DEVICE_LOCATOR = "device://tone";

	private static final String supportedContentTypes[] = { "audio/x-wav",
			"audio/basic", "audio/mpeg", "audio/midi", "audio/x-tone-seq",
			"audio/amr" };

	private static final String supportedProtocols[] = { "http://", "rtsp://" };

	public static String[] getSupportedContentTypes(String protocol) {
		return supportedContentTypes;
	}

	public static String[] getSupportedProtocols(String content_type) {
		return supportedProtocols;
	}

	public static Player createPlayer(java.io.InputStream ins, String type) throws IOException, MediaException {
		return new SoundPlayer(ins, type);
	}

	public static Player createPlayer(String locator) throws IOException, MediaException {
		Player player = null;
		if (TONE_DEVICE_LOCATOR.equals(locator))
			player = new TonePlayer();

		return player;
	}

	public static void playTone(int note, int duration, int volume) throws MediaException {
		// do nothing
	}

}
