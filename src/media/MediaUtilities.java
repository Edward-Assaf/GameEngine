package media;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import main.Main;
import windows.Window;

/**
 * The {@code MediaUtilities} class offers helper definitions,
 * used to manage media resources.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class MediaUtilities {
	/**
	 * This function sets up all necessary media resources for the game.
	 */
	public static void setupMediaResources() {
		resizeMediaResource(new MediaResource("MainMenuBackground.jpg"), 
				Window.windowMinimizedWidth, Window.windowMinimizedHeight, "Minimized");
		resizeMediaResource(new MediaResource("MainMenuBackground.jpg"), 
				Window.deviceScreenWidth, Window.deviceScreenHeight, "Resized");
	}
	
	/**
	 * This function is used to resize media resource files such as
	 * GIFs, JPGs, PNGs... to a new dimension (width*height). A
	 * media resource file must exist in the /src/res/ direcotry for
	 * internal resource files, and exist in an external resources
	 * folder next to the executable for external resource files.
	 * 
	 * <p>When a media resource file is resized, one copy is generated
	 * to be stored in /bin/res/. A tag name that is added to the
	 * resource file's name must be given, so that the file name
	 * becomes unique.</p>
	 * 
	 * <p>To avoid performance reduction, media resource files are resized
	 * exactly once. Any media resource file with an already existing store
	 * path (the path its resized copy will be presumably stored in) will
	 * be ignored and treated as an 'already resized' media resource file.</p>
	 * 
	 * @param resource  The {@link MediaResource} object that stores the resource file's metadata.
	 * @param newWidth  The new width for the resource file to be resized to.
	 * @param newHeight  The new height for the resource file to be resized to.
	 * @param tag  The tag name to add to the resource file's name to make it unique.
	 */
	public static void resizeMediaResource(MediaResource resource, int newWidth, int newHeight, String tag) {
		// Ignoring the resource file if its /bin/res/ path already exists.
		resource.addTag(tag);
		
		// Ignore already resized media resource files.
		if (resourceExists(resource.getResourceName())) {
			return;
		}
		
		// Handling the special case (animated GIFs).
		if (resource.getResourceType().equals("gif")) {
			resizeMediaResource_GIF(resource, newWidth, newHeight);
			return;
		}
		
		// Handling static media files.
		try {
			// Getting the static media file and loading it.
			BufferedImage originalImage;
			if (Main.ECLIPSE_RUN_MODE) {
				originalImage = ImageIO.read(resource.getInputStream());
			}
			else {
				originalImage = ImageIO.read(resource.getResourceFile());
			}
			
			// Drawing the scaled version of the static media file.
			BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
			Graphics2D painter = resizedImage.createGraphics();
			painter.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
			painter.dispose();
			
			// Storing a copy of the new resized static media file, in /bin/res/.
			ImageIO.write(resizedImage, resource.getResourceType(), 
					new File(resource.getResourceAbsolutePath()));
		} catch (Exception e) {
			System.out.println("Resources setup failed");
		}
	}
	
	/**
	 * This is a special case of {@code resizeMediaResource} to
	 * handle the resizing of animated GIFs.
	 * 
	 * @param resource  The {@link MediaResource} object that stores the resource file's metadata.
	 * @param newWidth  The new width for the resource file to be resized to.
	 * @param newHeight  The new height for the resource file to be resized to.
	 */
	private static void resizeMediaResource_GIF(MediaResource resource, int newWidth, int newHeight) {
		// TO-DO
	}
	
	/**
	 * This function copies an existing media resource file and adds a tag
	 * to the name of the generated copy. The copy is stored in the same
	 * directory that the original resource file is in.
	 * 
	 * <p>If the path of the generated copy already exists, the new copy
	 * will replace the previously existing file.</p>
	 * 
	 * @param resource  The media resource file to copy.
	 * @param tag  The tag to add to the name of the generated copy.
	 */
	public static void copyResourceWithTag(MediaResource resource, String tag) {
		String originalPath = resource.getResourceAbsolutePath();
		resource.addTag(tag);
		try {
			Files.copy(
				Paths.get(originalPath),
				Paths.get(resource.getResourceAbsolutePath()), 
				StandardCopyOption.REPLACE_EXISTING
			);
		} catch (Exception e) {
			System.out.println("Copying file " + originalPath + " failed");
		}
	}
	
	/**
	 * This function checks if a resource file already exists. When in
	 * internal mode, the bin directory is checked. And when in external mode,
	 * the external resources directory that must be next to the executable
	 * is checked.
	 * 
	 * @param resourceName  The name of the resource file to check, without path.
	 * 
	 * @return  A boolean, true of the resource exists and false otherwise.
	 */
	public static boolean resourceExists(String resourceName) {
		try {
			MediaResource test = new MediaResource(resourceName);
			if (test.getResourceAbsolutePath() == null) {
				return false;
			}
		} catch(Exception e1) {
			return false;
		}
		return true;
	}
}
