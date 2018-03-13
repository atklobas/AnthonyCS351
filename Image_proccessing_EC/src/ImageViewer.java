

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
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;


public class ImageViewer {
	public static final float TO_DEGREES=1f/360;
	public static final float TO_INT=1f/100;
	float hue=0,sat=0,bri=0;
	private JFrame frame;
	private JLabel label;
	private BufferedImage screen;
	private BufferedImage raw;

	
	public ImageViewer(){
		SwingUtilities.invokeLater(()->{
			
		
			//create a Frame/window
			frame=new JFrame("image viewer");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			try {
				raw=ImageIO.read(new File("wheel.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//Keep the modified image seperate for original 
			screen=new BufferedImage(raw.getWidth(),raw.getHeight(),BufferedImage.TYPE_INT_ARGB);
			screen.getGraphics().drawImage(raw, 0, 0, null);
			
			//create component to display image;
			ImageIcon icon=new ImageIcon();
			icon.setImage(screen);
			label=new JLabel(icon);
			
			//create panel for controls
			JPanel controls=new JPanel();
			GridLayout contLayout=new GridLayout(2,3);
			controls.setLayout(contLayout);
			
			JSlider hue= new JSlider(JSlider.HORIZONTAL,-180,180,0);
			hue.addChangeListener((ChangeEvent e)->{changeHue(TO_DEGREES*((JSlider)e.getSource()).getValue());});
			
			
			JSlider sat= new JSlider(JSlider.HORIZONTAL,-100,100,0);
			sat.addChangeListener((ChangeEvent e)->{changeSat(TO_INT*((JSlider)e.getSource()).getValue());});
			
			JSlider bri= new JSlider(JSlider.HORIZONTAL,-100,100,0);
			bri.addChangeListener((ChangeEvent e)->{changeBri(TO_INT*((JSlider)e.getSource()).getValue());});
			
			//add sliders that control hue,saturation, and brightness
			controls.add(hue);
			controls.add(sat);
			controls.add(bri);
			controls.add(new JLabel("Hue"));
			controls.add(new JLabel("Saturation"));
			controls.add(new JLabel("Brightness"));
			
			
			
			BorderLayout layout =new BorderLayout();
			frame.setLayout(layout);
			frame.add(label, BorderLayout.CENTER);
			frame.add(controls,BorderLayout.SOUTH);
			
			frame.add(label);
			frame.pack();
			frame.setVisible(true);
		});
	}
	
	
	public void update(int ymin, int ymax) {
		
	}
	
	
	
	public void update() {
		long time=System.currentTimeMillis();
		int height=raw.getHeight();
		int width=raw.getWidth();
		float[] hsb=new float[3];
		for (int y = 0; y < height; y++) {	
			for (int x = 0; x < width; x++) {
				int rgb = raw.getRGB(x, y);
				
				Color.RGBtoHSB((rgb>>16)&0xFF, (rgb>>8)&0xFF, rgb&0xFF, hsb);
				//add hue offset, if its outside of [0-1] add/subtact (it loops) 
				hsb[0]+=hue;
				if(hue>1)hue-=1;
				else if (hue<0)hue+=1;
				
				//if saturation or brightness is outside of range, put it at the extreme
				hsb[1]+=sat;
				hsb[2]+=bri;
				for(int i=1;i<3;i++) {
					if(hsb[i]<0)hsb[i]=0;
					else if(hsb[i]>1)hsb[i]=1;
				}

				screen.setRGB(x, y, (rgb|0xFFFFFF)&Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
			}
		}
		System.out.println(System.currentTimeMillis()-time);
		
	}
	public void changeHue(float hue) {
		this.hue=hue;
		update();
		frame.repaint();
	}
	public void changeSat(float sat) {
		this.sat=sat;
		update();
		frame.repaint();
	}
	public void changeBri(float bri) {
		this.bri=bri;
		update();
		frame.repaint();
	}

	public void refresh(){
	
		frame.repaint();

	}
	
	
	public Raster getImageRaster(){
		return raw.getRaster();
	}
	

	public static void main(String[] args) throws IOException{
		new ImageViewer();
	}

}
