package data;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {
	private static ImageLoader imageLoader;

	private Map<String, BufferedImage> imageMap;
	private BufferedImage nullImage;

	public static ImageLoader getInstance() {
		if (imageLoader == null)
			imageLoader = new ImageLoader();
		return imageLoader;
	}

	private ImageLoader() {
		imageMap = new HashMap<>();
		nullImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		nullImage.getGraphics().setColor(Color.RED);
		nullImage.getGraphics().fillRect(0, 0, 64, 64);
	}

	/**
	 * Get a image under the image directory, specified by IMAGE_PATH game property.
	 * See {@link GameProperty}. The image loaded will be cached, so it is
	 * recommended that the user does not cache additional images. If the image
	 * cannot be loaded, a default image is returned.
	 * 
	 * @param fileName
	 * @return
	 */
	public BufferedImage getImage(String fileName) {
		BufferedImage image = getCachedImage(fileName);
		if (image == null) {
			image = loadImageFromFile(fileName);
		}
		return image;
	}

	private BufferedImage loadImageFromFile(String fileName) {
		try {
			final String PATH = GameProperty.getInstance().get("IMAGE_PATH");
			File imageFile = new File(PATH + fileName);
			BufferedImage image = ImageIO.read(imageFile);
			imageMap.put(fileName, image);
			return image;
		} catch (IOException ex) {
			ex.printStackTrace();
			return nullImage;
		}
	}

	private BufferedImage getCachedImage(String fileName) {
		return imageMap.get(fileName);
	}

}
