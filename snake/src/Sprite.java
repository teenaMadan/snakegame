import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Sprite {
	public int x,y;
	private Image image;
	private int frameWidth,frameHeight;
	private int currentFrame;
	
	Sprite() {
		x = 0;
		y = 0;
		currentFrame = 0;
	}
	
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
	public void setCurrentFrame(int Frame){
		currentFrame = Frame;
	}
	public void draw(Graphics g){		
		g.setClip(x,y,frameWidth,frameHeight);
		g.drawImage(image,x-currentFrame*frameWidth,y,Graphics.TOP|Graphics.LEFT);
	}
	public int getFrameWidth()	{
		return frameWidth;
	}
	public int getFrameHeight() {
		return frameHeight;
	}
}
