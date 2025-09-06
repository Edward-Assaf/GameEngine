package media;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import main.Main;

/**
 * The {@code MediaResource} class represents
 * an individual media resource (e.g. image or GIF), 
 * and the necessary functionalities to handle it.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class MediaResource {
	/**
	 * The name of the resource file.
	 */
	private String resourceName;
	
	/**
	 * The input stream that will read the resource file (internal mode).
	 */
	private InputStream resourceInputStream;
	
	/**
	 * The file object storing the resource file (external mode)
	 */
	private File resourceFile;
	
	/**
	 * The absolute path that the resource <b>should</b> be stored in, in /bin/res/.
	 */
	private String resourceAbsolutePath;
	
	/**
	 * The type of the resource file (e.g. jpg, png... etc).
	 */
	private String resourceType;
	
	/**
	 * This function returns the absolute path the media resource file
	 * <b>should</b> be stored in.
	 * 
	 * <p>The {@code getResourceAbsolutePath} function can be used to obtain the
	 * path of the resource file after adding a tag (see {@link #addTag(String)}).
	 * This is because {@link #resourceAbsolutePath} stores the path this resource
	 * <b>should</b> be stored in, not the actual path.</p>
	 * 
	 * @return A {@code String}, the absolute path the resource <b>should</b> be stored in.
	 */
	public String getResourceAbsolutePath() {
		return resourceAbsolutePath;
	}
	
	/**
	 * Getter for {@link #resourceInputStream}.
	 * 
	 * @return  The input stream that stores the resource file (internal mode).
	 */
	public InputStream getInputStream() {
		return resourceInputStream;
	}
	
	/**
	 * Getter for {@link #resourceFile}.
	 * 
	 * @return  The file object that stores the resource file (external mode).
	 */
	public File getResourceFile() {
		return resourceFile;
	}
	
	/**
	 * Getter for {@link #resourceType}; the type of the resource
	 * file (e.g. jpg, png... etc).
	 * 
	 * @return  A string, the resource's type.
	 */
	public String getResourceType() {
		return resourceType;
	}
	
	/**
	 * Getter for {@link #resourceName}. The resource name is
	 * modified when {@link #addTag(String)} is called.
	 * 
	 * @return  The name of the resource file.
	 */
	public String getResourceName() {
		return resourceName;
	}
	
	/**
	 * This constructor retrieves a specific media resource and
	 * builds a manageable {@code MediaResource} instance.
	 * 
	 * @param resourceName  the name of the resource file in /src/res/, without a path.
	 */
	public MediaResource(String resourceName) {
		this.resourceName = resourceName;
		if (Main.ECLIPSE_RUN_MODE) {
			internalClassPathMode(resourceName);
		}
		else {
			externalExecutableMode(resourceName);
		}
	}
	
	/**
	 * This function adds a specific tag name to the resource file's name
	 * to make it unique. This is useful when modifying media files such that
	 * modified versions are stored as different copies with a unique name.
	 * 
	 * <p>The {@link #getResourceAbsolutePath()} function can be used to obtain the
	 * path of the resource file after adding a tag. This is because
	 * {@link #resourceAbsolutePath} stores the path this resource <b>should</b>
	 * be stored in, not the actual path.</p>
	 * 
	 * @param tag  The tag name to be added to the resource file's name.
	 */
	public void addTag(String tag) {
		String modifiedName = resourceAbsolutePath.substring(0, resourceAbsolutePath.indexOf(".")) + tag;
		resourceName = resourceName.substring(0, resourceName.indexOf(".")) + tag + "." + resourceType;
		resourceAbsolutePath = modifiedName + "." + resourceType;
	}
	
	/**
	 * This function is used when in internal mode (looking for resources
	 * happens in the bin directory) to find and load a specific resource
	 * file.
	 * 
	 * @param resourceName  The name of the resource file to load.
	 */
	private void internalClassPathMode(String resourceName) {
		// Getting the resource file.
		resourceInputStream = getClass().getResourceAsStream("/resources/" + resourceName);
		
		// Checking if the resource file exists.
		if (resourceInputStream == null) {
			System.out.println("Resource " + resourceName + " failed to load");
			return;
		}
		
		// Storing the resource's metadata (type, bin path).
		resourceType = resourceName.substring(resourceName.indexOf(".") + 1);
		resourceAbsolutePath = System.getProperty("user.dir") + "/bin/resources/" + resourceName;
	}
	
	/**
	 * This function is used when in external mode (looking for resources
	 * happens in an external directory) to find and load a specific resource
	 * file.
	 * 
	 * @param resourceName  The name of the resource file to load.
	 */
	private void externalExecutableMode(String resourceName) {
		try {
			// Getting the resource file.
			File jarDir = new File(".").getCanonicalFile();
			File resourceDir = new File(jarDir, "resources");
			resourceFile = new File(resourceDir, resourceName);
			
			// Checking if the resource file exists.
			if (!resourceFile.exists()) {
				System.out.println("Resource " + resourceName + " failed to load");
				return;
			}
			
			// Storing the resource's metadata (type, external path).
			resourceType = resourceName.substring(resourceName.indexOf(".") + 1);
			resourceAbsolutePath = resourceFile.getAbsolutePath();
		} catch (IOException e) {
			System.out.println("Resource " + resourceName + " failed to load");
		}
	}
	
}
