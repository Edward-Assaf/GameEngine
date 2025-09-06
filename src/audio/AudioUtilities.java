package audio;

import java.util.Map;

/**
 * The {@code AudioUtilities} class offers helper definitions,
 * used to manage audio resources.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class AudioUtilities {
	/**
	 * A map of all audio resources available. Each {@link audio.AudioResource}
	 * object is given a {@code String} name.
	 */
	public static final Map<String, AudioResource> AUDIO_RESOURCES = Map.of(
			"backgroundMusic", new AudioResource("backgroundMusic.wav"),
			"clickSound", new AudioResource("UIsound1.wav"),
			"cancelSound", new AudioResource("UIsound4.wav"),
			"escapeSound", new AudioResource("UIsound5.wav"),
			"missPunch", new AudioResource("airPunch.wav"),
			"hitPunch", new AudioResource("actualPunch.wav"),
			"gameMusic", new AudioResource("gameMusic.wav")
	);
	
	/**
	 * An array of the string names for all audio resources that
	 * are categorized as sound effects.
	 */
	public static final String[] SFX_AUDIO_NAMES = new String[] {
		"clickSound", "cancelSound", "escapeSound", "hitPunch", "missPunch"
	};
	
	/**
	 * An array of the string names for all audio resources that
	 * are categorized as music.
	 */
	public static final String[] MUSIC_AUDIO_NAMES = new String[] {"backgroundMusic", "gameMusic"};
	
	/**
	 * The default volume for audio resources. All audio resources
	 * are initialized with this value.
	 */
	public static final double DEFAULT_AUDIO_VOLUME = 0.6;
	
	/**
	 * This function takes a ratio (from 0.0 to 1.0) and
	 * sets the volume of all audio resources according
	 * to it. For example, 0.45 ratio means 45% volume
	 * will be set for all audio resources.
	 * 
	 * <p>Maximum and minimum volume values are retrieved
	 * from each audio resource's volume controller. This
	 * means that, while the ratio will be equal among all
	 * audio resources, the actual decibel value for the
	 * volume of each audio resource <b>may</b> vary.</p>
	 * 
	 * @param ratio  The ratio of volume desired.
	 */
	public static void setAllAudioResourcesVolume(double ratio) {
		for (String key : AUDIO_RESOURCES.keySet()) {
			AUDIO_RESOURCES.get(key).setVolume(ratio);
		}
	}
	
	/**
	 * This function takes a ratio (from 0.0 to 1.0) and
	 * sets the volume of all SFX-categorized audio resources
	 * according to it. For example, 0.45 ratio means 45% volume
	 * will be set for all SFX-categorized audio resources. To
	 * categorize an audio resource as SFX, its {@code String}
	 * name should be added to {@link #SFX_AUDIO_NAMES}.
	 * 
	 * <p>Maximum and minimum volume values are retrieved
	 * from each audio resource's volume controller. This
	 * means that, while the ratio will be equal among all
	 * audio resources, the actual decibel value for the
	 * volume of each audio resource <b>may</b> vary.</p>
	 * 
	 * @param ratio  The ratio of volume desired.
	 */
	public static void setAudioResourcesVolume_SFX(double ratio) {
		for (String key : SFX_AUDIO_NAMES) {
			AUDIO_RESOURCES.get(key).setVolume(ratio);
		}
	}
	
	/**
	 * This function takes a ratio (from 0.0 to 1.0) and
	 * sets the volume of all music-categorized audio resources
	 * according to it. For example, 0.45 ratio means 45% volume
	 * will be set for all music-categorized audio resources. To
	 * categorize an audio resource as music, its {@code String}
	 * name should be added to {@link #MUSIC_AUDIO_NAMES}.
	 * 
	 * <p>Maximum and minimum volume values are retrieved
	 * from each audio resource's volume controller. This
	 * means that, while the ratio will be equal among all
	 * audio resources, the actual decibel value for the
	 * volume of each audio resource <b>may</b> vary.</p>
	 * 
	 * @param ratio  The ratio of volume desired.
	 */
	public static void setAudioResourcesVolume_Music(double ratio) {
		for (String key : MUSIC_AUDIO_NAMES) {
			AUDIO_RESOURCES.get(key).setVolume(ratio);
		}
	}
}
