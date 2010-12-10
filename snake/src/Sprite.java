/*
 * Developed by: Bruno Firmino da Silva
 * Creation date: 05/12/2010
 * Class description: this class creates defines the properties and methods of the game Sprites.
 */

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Sprite {
	public int x,y;
	private Image image;
	private int frameWidth,frameHeight;
	private int currentFrame;
	
	// Class constructor.
	Sprite() {
		x = 0;
		y = 0;
		currentFrame = 0;
	}
	
	// Method to load the image file from disc.
	public void loadFromFile(String File,int numberOfFrames) {
		try	{
			image = Image.createImage(File);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro ao carregar:" + File);
		}
		frameWidth = image.getWidth() / numberOfFrames;
		frameHeight = image.getHeight();
	}
	
	// Set the image frame.
	public void setCurrentFrame(int Frame){
		currentFrame = Frame;
	}
	
	// Draw the Sprite in the stage.
	public void draw(Graphics g){		
		g.setClip(x,y,frameWidth,frameHeight);
		g.drawImage(image,x-currentFrame*frameWidth,y,Graphics.TOP|Graphics.LEFT);
	}
	
	// Returns the Sprite width.
	public int getFrameWidth()	{
		return frameWidth;
	}
	
	// Returns the Sprite height.
	public int getFrameHeight() {
		return frameHeight;
	}
	
}
