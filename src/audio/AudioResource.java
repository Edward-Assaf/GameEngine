package audio;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import camera.CameraUtilities;

/**
 * The {@code AudioResource} class represents
 * an individual audio resource and the necessary
 * functionality to control its play.
 * 
 * @see {@link javax.sound.sampled.Clip}
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class AudioResource {
	/**
	 * This manages the resource (e.g. play/stop/seek functionalities).
	 * 
	 * @see {@link javax.sound.sampled.Clip}
	 */
	private Clip resourceManager;
	
	/**
	 * The volume controller of this resource.
	 */
	private FloatControl volumeController;
	
	/**
	 * This constructor retrieves a specific audio resource and
	 * builds a playable and manageable {@code AudioResource} instance
	 * using {@link javax.sound.sampled.Clip} functionality.
	 * 
	 * @param resourceName  The name of the resource file in /src/res/, without a path.
	 */
	public AudioResource(String resourceName) {
		try {
			// Getting resource path.
			URL path = getClass().getResource("/resources/" + resourceName);
			
			// Setting up resourceManager to manage this resource.
			AudioInputStream audioStream = null;
			audioStream = AudioSystem.getAudioInputStream(path);
			resourceManager = AudioSystem.getClip();
			resourceManager.open(audioStream);
			
			// Adding an audio volume controller and initializing the volume.
			volumeController = (FloatControl) resourceManager.getControl(FloatControl.Type.MASTER_GAIN);
			setVolume(AudioUtilities.DEFAULT_AUDIO_VOLUME);
		} catch (Exception e) {
			resourceManager = null;
			System.out.println("Resource " + resourceName + " failed to load");
		}
	}
	
	/**
	 * This function plays an audio resource <b>exactly once</b>
	 * from the beginning. If an audio resource was paused
	 * previously at a position other than the beginning,
	 * this function will reset the position to 0 before
	 * playing the audio resource <b>exactly once</b>.
	 * 
	 * <p>If you wish to resume an audio resource after it
	 * has been paused (using {@link #pause()}), you should
	 * use either {@link #resumeOnce()} or
	 * {@link #resumeIndefinitely()}, because this function
	 * cannot resume paused audio resources, it resets them.</p>
	 * 
	 * @see #playIndefinitely()
	 */
	public void playOnce() {
		if (resourceManager != null) {
			if (resourceManager.isRunning()) {
				resourceManager.stop();
			}
			resourceManager.setFramePosition(0);
			resourceManager.start();
		}
	}
	
	/**
	 * This function plays an audio resource <b>indefinitely</b>
	 * from the beginning. If an audio resource was paused
	 * previously at a position other than the beginning,
	 * this function will reset the position to 0 before
	 * playing the audio resource <b>indefinitely</b> until it
	 * is paused or terminated.
	 * 
	 * <p>If you wish to resume an audio resource after it
	 * has been paused (using {@link #pause()}), you should
	 * use either {@link #resumeOnce()} or
	 * {@link #resumeIndefinitely()}, because this function
	 * cannot resume paused audio resources, it resets them.</p>
	 * 
	 * @see #playOnce()
	 * @see #terminate()
	 */
	public void playIndefinitely() {
		if (resourceManager != null) {
			if (resourceManager.isRunning()) {
				resourceManager.stop();
			}
			resourceManager.setFramePosition(0);
			resourceManager.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	/**
	 * This function pauses an audio resource's playback.
	 * The {@link #resourceManager} will retain the
	 * information about the position at which the audio
	 * resource was paused. This way, you can resume the
	 * audio resource using {@link #resumeOnce()} or
	 * {@link #resumeIndefinitely()}.
	 * 
	 * <p><b>Warning:</b> The {@link #resourceManager} may
	 * not retain the data line if the audio resource has
	 * been paused for too long. Thus, there is a risk of
	 * losing the position at which the audio resource was
	 * paused. In that case, calling {@link #resumeOnce()}
	 * or {@link #resumeIndefinitely()} will just play the
	 * audio resource from the beginning.</p>
	 * 
	 * @see #terminate()
	 */
	public void pause() {
		if (resourceManager != null) {
			resourceManager.stop();
		}
	}
	
	/**
	 * This function resumes the audio resource from
	 * where it was paused. It starts from the current
	 * position the audio resource was last paused at
	 * and continues until the end and <b>stops</b>.
	 * 
	 * <p>There is a risk that the resume functionality
	 * fails to execute correctly. See {@link #pause()}'s
	 * "Warning" section. In that case, this function will
	 * execute just like {@link #playOnce()}.</p>
	 * 
	 * @see #resumeIndefinitely()
	 */
	public void resumeOnce() {
		if (resourceManager != null) {
			resourceManager.start();
		}
	}
	
	/**
	 * This function resumes the audio resource from
	 * where it was paused. It starts from the current
	 * position the audio resource was last paused at
	 * and continues until the end and <b>loops</b>.
	 * 
	 * <p>There is a risk that the resume functionality
	 * fails to execute correctly. See {@link #pause()}'s
	 * "Warning" section. In that case, this function will
	 * execute just like {@link #playIndefinitely()}.</p>
	 * 
	 * @see #resumeOnce()
	 */
	public void resumeIndefinitely() {
		if (resourceManager != null) {
			resourceManager.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	/**
	 * This function terminates an audio resource's playback.
	 * The {@link #resourceManager} will not retain the
	 * information about the position at which the audio
	 * resource was paused, it will reset to 0 instead. This
	 * way, calling {@link #resumeOnce()} or {@link #resumeIndefinitely()}
	 * will just play the audio resource from the beginning.
	 * 
	 * @see #pause()
	 */
	public void terminate() {
		if (resourceManager != null) {
			resourceManager.stop();
			resourceManager.setFramePosition(0);
		}
	}
	
	/**
	 * This function takes a ratio (from 0.0 to 1.0) and
	 * sets the volume of this audio resource according
	 * to it. For example, 0.45 ratio means 45% volume
	 * will be set for this audio resource.
	 * 
	 * <p>Maximum and minimum volume values are retrieved
	 * from the audio resource's {@link #volumeController}
	 * attribute.</p>
	 * 
	 * @param ratio  The ratio of volume desired.
	 */
	public void setVolume(double ratio) {
		if (ratio < 0) {
			ratio = 0;
		}
		else if (ratio > 1) {
			ratio = 1;
		}
		if (volumeController != null) {
			double linearMinimum = Math.pow(10, volumeController.getMinimum() / 20);
			double linearMaximum = Math.pow(10, volumeController.getMaximum() / 20);
			double linearValue = CameraUtilities.lerp(linearMinimum, linearMaximum, ratio);
			float actualValue = (float) (20 * Math.log10(linearValue));
			volumeController.setValue(actualValue);
		}
	}
}
