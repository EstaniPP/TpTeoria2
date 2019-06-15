package Others;


import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class SaveHTML {
	
	// where the webpage content will be concatenated
	StringBuilder content = new StringBuilder();
	
	public void addPicture(BufferedImage img) {
		// adds a picture to the webpage
		// convert the bufferedimage into a bytearrau
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", bos );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    byte [] data = bos.toByteArray();
	    // convert the data into a base64 string 
	    String encodedString = Base64.getEncoder().encodeToString(data);
	    
	    String image = "<img src=\"data:image/png;base64, " + encodedString + "\" />";
	    
	    content = content.append(image);
	}
	
	public void addBreak() {
		content = content.append("<br />");
	}
	
	public void addText(String text) {
		content = content.append(text);
	}
	
	public void saveHTML(String path, String name) {
		File html = new File(path + name);
		BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(html));
            writer.write(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
		
		try {
			html.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
