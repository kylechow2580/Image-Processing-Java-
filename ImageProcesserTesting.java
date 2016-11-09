//gaussianBlur: radius:4 , standart deviation:1.4^4

import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.*;
import javax.swing.*;
import java.util.Scanner;

public class ImageProcesserTesting
{
	public static String folder = "Processed image/";
	public static BufferedImage imageCopy(BufferedImage origin)
	{
		BufferedImage new_img = new BufferedImage(origin.getWidth(),origin.getHeight(),origin.getType());
		Graphics g = new_img.createGraphics();
		g.drawImage(origin, 0, 0, null);
		g.dispose();
		return new_img;
	}
	public static void writeImage(BufferedImage new_img, String name)
	{
		try 
		{
		    ImageIO.write(new_img,"jpg",new File(folder + name + ".jpg"));	    
		} 
		catch (IOException e) 
		{
		}
	}
	public static void validateRGB(double[] rgb)
	{
		if(rgb[0] > 255)
			rgb[0] = 255;
		if(rgb[1] > 255)
			rgb[1] = 255;
		if(rgb[2] > 255)
			rgb[2] = 255;
		if(rgb[0] < 0)
			rgb[0] = 0;
		if(rgb[1] < 0)
			rgb[1] = 0;
		if(rgb[2] < 0)
			rgb[2] = 0;
	}
	public static double gaussianFormula(int x, int y, double sd)
	{
		double baseEindex = -((x*x + y*y)/(2*sd*sd));
		double constant = 1/(2*Math.PI*sd*sd);
		return constant*Math.pow(Math.E,baseEindex);
	}
	public static double[][] gaussianBlurMatrix(int radius, double weight)
	{
		int diameter = 2*radius;
		double sumOfWeight = 0;
		double[][] blurConstant = new double[diameter+1][diameter+1];	
		for(int x=0-radius;x<=radius;x++)
		{
			for (int y=0-radius;y<=radius;y++) 
			{
				blurConstant[x+radius][y+radius] = gaussianFormula(x,y,weight);
				sumOfWeight += blurConstant[x+radius][y+radius];
			}
		}
		for(int x=0;x<diameter+1;x++)
		{
			for (int y=0;y<diameter+1;y++) 
			{
				blurConstant[x][y] /= sumOfWeight;
			}
		}
		return blurConstant;
	}
	public static BufferedImage greyScale(BufferedImage origin)
	{
		BufferedImage new_img = imageCopy(origin);
		int width = origin.getWidth();
		int height = origin.getHeight();
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				Color origin_rgb = new Color(origin.getRGB(i,j));
				double red = origin_rgb.getRed();
				double green = origin_rgb.getGreen();
				double blue = origin_rgb.getBlue();
				double[] rgb = new double[3];
				rgb[0] = 0.2126*red + 0.7152*green + 0.0722*blue;		
				rgb[1] = 0.2126*red + 0.7152*green + 0.0722*blue;			
				rgb[2] = 0.2126*red + 0.7152*green + 0.0722*blue;
				Color new_rgb = new Color((int)rgb[0],(int)rgb[1],(int)rgb[2]);
				new_img.setRGB(i,j,new_rgb.getRGB());
			}
		}
		writeImage(new_img,"GreyScale");
		System.out.println("Function greyScale done!");
		return new_img;
	}
	public static BufferedImage frame(BufferedImage origin)
	{
		BufferedImage new_img = imageCopy(origin);
		int width = origin.getWidth();
		int height = origin.getHeight();
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				if(j<=height*0.05 || j>=height*0.95 || i<=width*0.05 || i>=width*0.95)
				{
					Color origin_rgb = new Color(origin.getRGB(i,j));
					double red = origin_rgb.getRed();
					double green = origin_rgb.getGreen();
					double blue = origin_rgb.getBlue();
					double[] rgb = new double[3];
					rgb[0] = 0.393*red + 0.769*green + 0.189*blue;		
					rgb[1] = 0.349*red + 0.686*green + 0.168*blue;			
					rgb[2] = 0.272*red + 0.534*green + 0.131*blue;
					validateRGB(rgb);
					Color new_rgb = new Color((int)rgb[0],(int)rgb[1],(int)rgb[2]);
					new_img.setRGB(i,j,new_rgb.getRGB());
				}		
			}
		}
		writeImage(new_img,"SepiaToneFrame");
		System.out.println("Function frame done!");
		return new_img;
	}
	public static BufferedImage sepiaTone(BufferedImage origin)
	{
		BufferedImage new_img = imageCopy(origin);
		int width = origin.getWidth();
		int height = origin.getHeight();
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				Color origin_rgb = new Color(origin.getRGB(i,j));
				double red = origin_rgb.getRed();
				double green = origin_rgb.getGreen();
				double blue = origin_rgb.getBlue();
				double[] rgb = new double[3];
				rgb[0] = 0.393*red + 0.569*green + 0.189*blue;		
				rgb[1] = 0.349*red + 0.486*green + 0.168*blue;			
				rgb[2] = 0.272*red + 0.334*green + 0.131*blue;
				validateRGB(rgb);
				Color new_rgb = new Color((int)rgb[0],(int)rgb[1],(int)rgb[2]);
				new_img.setRGB(i,j,new_rgb.getRGB());
			}
		}
		writeImage(new_img,"SepiaTone");
		System.out.println("Function sepiaTone done!");
		return new_img;
	}
	public static BufferedImage unknown(BufferedImage origin)
	{
		BufferedImage new_img = imageCopy(origin);
		int width = origin.getWidth();
		int height = origin.getHeight();
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				Color origin_rgb = new Color(origin.getRGB(i,j));
				double red = origin_rgb.getRed();
				double green = origin_rgb.getGreen();
				double blue = origin_rgb.getBlue();
				double[] rgb = new double[3];
				rgb[0] = 1.7159*red - 0.56501*green - 0.34473*blue;		
				rgb[1] = -0.96924*red + 2.17597*green + 0.04156*blue;			
				rgb[2]= 0.01344*red - 0.11836*green + 1.7517*blue;

				validateRGB(rgb);
				Color new_rgb = new Color((int)rgb[0],(int)rgb[1],(int)rgb[2]);
				new_img.setRGB(i,j,new_rgb.getRGB());
			}
		}
		writeImage(new_img,"unknownColor");
		System.out.println("Function unknown done!");
		return new_img;
	}
	public static BufferedImage camera(BufferedImage origin)
	{
		BufferedImage new_img = imageCopy(origin);
		int width = origin.getWidth();
		int height = origin.getHeight();
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				Color origin_rgb = new Color(origin.getRGB(i,j));
				double red = origin_rgb.getRed();
				double green = origin_rgb.getGreen();
				double blue = origin_rgb.getBlue();
				double[] rgb = new double[3];
				double base = 126;
				rgb[0] = 255/base*red + green*0+ blue*0;		
				rgb[1] = red*0 + 255/base*green + blue*0;			
				rgb[2] = red*0 + green*0 + 255/base*blue;

				validateRGB(rgb);
				Color new_rgb = new Color((int)rgb[0],(int)rgb[1],(int)rgb[2]);
				new_img.setRGB(i,j,new_rgb.getRGB());
			}
		}
		writeImage(new_img,"Camera");
		System.out.println("Function camera done!");
		return new_img;
	}
	public static BufferedImage sharpen(BufferedImage origin)
	{
		BufferedImage new_img = imageCopy(origin);
		int width = origin.getWidth();
		int height = origin.getHeight();
		double[][] blurConstant = {{0,-4,0},{-4,17,-4},{0,-4,0}};

		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				if(i>0 && i<width-1 && j>0 && j<height-1)
				{
					Color[][] kenerlArray = new Color[3][3];
					double[] rgb = new double[3];
					double[][] calArray = new double[3][3];
					for(int x=0;x<3;x++)
					{
						for(int y=0;y<3;y++)
						{
							int current_x = i+x-1;
							int current_y = j+y-1;
							kenerlArray[x][y] = new Color(origin.getRGB(current_x,current_y));
						}
					}
					for(int x=0;x<3;x++)
					{
						for(int y=0;y<3;y++)
						{
							calArray[x][y] = kenerlArray[x][y].getRed() * blurConstant[x][y];
							rgb[0] += calArray[x][y];
							calArray[x][y] = kenerlArray[x][y].getGreen() * blurConstant[x][y];
							rgb[1] += calArray[x][y];
							calArray[x][y] = kenerlArray[x][y].getBlue() * blurConstant[x][y];
							rgb[2] += calArray[x][y];
						}
					}
					validateRGB(rgb);
					Color new_rgb = new Color((int)rgb[0],(int)rgb[1],(int)rgb[2]);
					new_img.setRGB(i,j,new_rgb.getRGB());
				}
			}
		}
		writeImage(new_img,"Sharpen");
		System.out.println("Function sharpen done!");
		return new_img;
	}
	public static BufferedImage centerColor(BufferedImage origin)
	{
		BufferedImage new_img = imageCopy(origin);
		int width = origin.getWidth();
		int height = origin.getHeight();
		int center_x = width/2;
		int center_y = height/2;
		double longest = Math.sqrt(center_x*center_x + center_y*center_y);
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				double distance = Math.sqrt((i-center_x)*(i-center_x) + (j-center_y)*(j-center_y));
				double ratio = distance/longest;
				Color origin_rgb = new Color(origin.getRGB(i,j));
				double red = origin_rgb.getRed();
				double green = origin_rgb.getGreen();
				double blue = origin_rgb.getBlue();
				double rgb[] = new double[3];	
				rgb[0] = ((0.2126-1)*ratio+1)*red + 0.7152*ratio*green + 0.0722*ratio*blue;		
				rgb[1] = 0.2126*ratio*red + ((0.7152-1)*ratio+1)*green + 0.0722*ratio*blue;			
				rgb[2] = 0.2126*ratio*red + 0.7152*ratio*green + ((0.0722-1)*ratio+1)*blue;
				validateRGB(rgb);
				Color new_rgb = new Color((int)rgb[0],(int)rgb[1],(int)rgb[2]);
				new_img.setRGB(i,j,new_rgb.getRGB());
			}
		}
		writeImage(new_img,"CenterColor");
		System.out.println("Function centerColor done!");
		return new_img;
	}
	public static BufferedImage centerGrey(BufferedImage origin)
	{
		BufferedImage new_img = imageCopy(origin);
		int width = origin.getWidth();
		int height = origin.getHeight();
		int center_x = width/2;
		int center_y = height/2;
		int	longest = (int)Math.sqrt(center_x*center_x + center_y*center_y);
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				double distance = Math.sqrt((i-center_x)*(i-center_x) + (j-center_y)*(j-center_y));
				double ratio = distance/longest;
				Color origin_rgb = new Color(origin.getRGB(i,j));
				double red = origin_rgb.getRed();
				double green = origin_rgb.getGreen();
				double blue = origin_rgb.getBlue();
				double rgb[] = new double[3];	
				rgb[0] = ((1-0.2126)*ratio+0.2126)*red + ((-0.7152)*ratio+0.7152)*green + ((-0.0722)*ratio+0.0722)*blue;		
				rgb[1] = ((-0.2126)*ratio+0.2126)*red + ((1-0.7152)*ratio+0.7152)*green + ((-0.0722)*ratio+0.0722)*blue;			
				rgb[2] = ((-0.2126)*ratio+0.2126)*red + ((-0.7152)*ratio+0.7152)*green + ((1-0.0722)*ratio+0.0722)*blue;
				validateRGB(rgb);
				Color new_rgb = new Color((int)rgb[0],(int)rgb[1],(int)rgb[2]);
				new_img.setRGB(i,j,new_rgb.getRGB());
			}
		}
		writeImage(new_img,"CenterGrey");
		System.out.println("Function centerGrey done!");
		return new_img;
	}
	public static BufferedImage darkGrey(BufferedImage origin)
	{
		BufferedImage new_img = imageCopy(origin);
		int width = origin.getWidth();
		int height = origin.getHeight();
		int longest = (int)Math.sqrt(width*width + height*height);
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				double distance = Math.sqrt(i*i + j*j);
				double ratio = distance/longest;
				Color origin_rgb = new Color(origin.getRGB(i,j));
				double red = origin_rgb.getRed();
				double green = origin_rgb.getGreen();
				double blue = origin_rgb.getBlue();
				double outputRed = 0.2126*ratio*red + 0.7152*ratio*green + 0.0722*ratio*blue;		
				double outputGreen = 0.2126*ratio*red + 0.7152*ratio*green + 0.0722*ratio*blue;			
				double outputBlue = 0.2126*ratio*red + 0.7152*ratio*green + 0.0722*ratio*blue;
				Color new_rgb = new Color((int)outputRed,(int)outputGreen,(int)outputBlue);
				new_img.setRGB(i,j,new_rgb.getRGB());
			}
		}
		writeImage(new_img,"DarkGrey");
		System.out.println("Function darkGrey done!");
		return new_img;
	}
	public static BufferedImage gaussianBlur(BufferedImage origin,int radius,double weight, int clearRadius)
	{
		System.out.println("Function GaussianBlur Starting...");
		BufferedImage new_img = imageCopy(origin);
		int width = origin.getWidth();
		int height = origin.getHeight();
		int center_x = width/2;
		int center_y = height/2;
		int diameter = 2*radius;
		
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				int x_different = i-center_x;
				int y_different = j-center_y;
				double distance = Math.sqrt(x_different*x_different + y_different*y_different);
				
				if(i>=radius && i<width-radius && j>=radius && j<height-radius && distance>=clearRadius)
				{
					
					Color[][] kenerlArray = new Color[diameter+1][diameter+1];
					double ratio = distance/(Math.sqrt(center_x*center_x + center_y*center_y)-clearRadius);
					double[][] blurConstant = gaussianBlurMatrix(radius,weight*(Math.pow(1+ratio,4)));
					double[] rgb = new double[3];
					double[][] calArray = new double[diameter+1][diameter+1];
					for(int x=0;x<diameter+1;x++)
					{
						for(int y=0;y<diameter+1;y++)
						{
							int current_x = i+x-radius;
							int current_y = j+y-radius;
							kenerlArray[x][y] = new Color(origin.getRGB(current_x,current_y));
							calArray[x][y] = kenerlArray[x][y].getRed() * blurConstant[x][y];
							rgb[0] += calArray[x][y];
							calArray[x][y] = kenerlArray[x][y].getGreen() * blurConstant[x][y];
							rgb[1] += calArray[x][y];
							calArray[x][y] = kenerlArray[x][y].getBlue() * blurConstant[x][y];
							rgb[2] += calArray[x][y];
							
						}
					}
					validateRGB(rgb);
					Color new_rgb = new Color((int)rgb[0],(int)rgb[1],(int)rgb[2]);
					new_img.setRGB(i,j,new_rgb.getRGB());
				}
			}
		}
		writeImage(new_img,"GaussianBlur");
		System.out.println("Function GaussianBlur done!");
		return new_img;
	}
    public static void main(String args[]) 
    { 
		
    	BufferedImage img = null;
    	Scanner sc = new Scanner(System.in);
    	System.out.print("Picture: ");
    	String filename = sc.nextLine();
		try 
		{
		    img = ImageIO.read(new File(filename));
		    File dir = new File(folder);
			dir.mkdir();
		} 
		catch (IOException e) 
		{
		}

		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage test_img;
		test_img = greyScale(img);
		test_img = sepiaTone(img);
		test_img = frame(img);
		test_img = unknown(img);
		test_img = camera(img);
		test_img = sharpen(img);
		test_img = centerColor(img);
		test_img = centerGrey(img);
		test_img = darkGrey(img);
		test_img = gaussianBlur(img,5,1.5,300);


	

        JFrame demo= new JFrame("Hello haha abc XD");
        demo.setSize(width+100,height+100);
        JLabel lb2 = new JLabel(new ImageIcon(test_img));
        demo.add(lb2);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
        demo.setVisible(true);
	} 
}