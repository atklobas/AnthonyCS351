

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

public class ImageViewer {
	public static final float TO_DEGREES=1f/360;
	private JFrame frame;
	private JLabel label;
	private BufferedImage screen;
	private BufferedImage raw;
	public ImageViewer() throws IOException{
		//create a Frame/window
		frame=new JFrame("image viewer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		raw=ImageIO.read(new File("flower.png"));
		
		//Keep the modified image seperate for original 
		screen=new BufferedImage(raw.getWidth(),raw.getHeight(),BufferedImage.TYPE_INT_RGB);
		screen.getGraphics().drawImage(raw, 0, 0, null);
		
		//create component to display image;
		ImageIcon icon=new ImageIcon();
		icon.setImage(screen);
		label=new JLabel(icon);
		
		JPanel controls=new JPanel();
		GridLayout contLayout=new GridLayout(2,3);
		controls.setLayout(contLayout);
		JSlider hue= new JSlider(JSlider.HORIZONTAL,0,360,0);
		hue.addChangeListener((ChangeEvent e)->{changeHue(TO_DEGREES*((JSlider)e.getSource()).getValue());});
		
		
		JSlider sat= new JSlider(JSlider.HORIZONTAL,0,360,0);
		
		
		JSlider bri= new JSlider(JSlider.HORIZONTAL,0,360,0);
		
		controls.add(hue);
		controls.add(sat);
		controls.add(bri);
		controls.add(new JLabel("Hue"));
		controls.add(new JLabel("Sat"));
		controls.add(new JLabel("Bri"));
		
		
		
		BorderLayout layout =new BorderLayout();
		frame.setLayout(layout);
		frame.add(label, BorderLayout.CENTER);
		frame.add(controls,BorderLayout.SOUTH);
		
		frame.add(label);
		frame.pack();
		frame.setVisible(true);
	}
	
	public int rotateHue(int rgb, float angle ) {
		int b=rgb&0xFF;
		int g=(rgb>>8)&0xFF;
		int r=(rgb>>16)&0xFF;
		float[] hsb=Color.RGBtoHSB(r, g, b, null);
		hsb[0]+=angle;
		return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
	}
	public void changeHue(float offset) {
		
		long time=System.currentTimeMillis();
		int width=raw.getWidth();
		int height=raw.getHeight();
		
		for (int y = 0; y < height; y++) {	
			for (int x = 0; x < width; x++) {
				int rgb = rotateHue(raw.getRGB(x, y), offset);
				screen.setRGB(x, y, rgb);
			}
		}
		System.out.println(System.currentTimeMillis()-time);
		frame.repaint();
///		System.out.println("test");
		
	}

	public void refresh(){
	
		frame.repaint();

	}
	
	
	public Raster getImageRaster(){
		return raw.getRaster();
	}
	

	public static void main(String[] args) throws IOException{
		ImageViewer view =new ImageViewer();
		//Graph g=new rasterGraph(view.getImageRaster());
		//graphs.GraphAlgorithms.addObserver(()->view.refresh());
		//graphs.GraphAlgorithms.depthFirstSearch(g);
		view.frame.repaint();
	}

}
