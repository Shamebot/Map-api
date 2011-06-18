package shamebot.Map;

import java.awt.Color;

public class MapColor {

	protected Hue color;
	protected Brightness brightness;
	
	public MapColor(Hue color, Brightness brightness)
	{
		if(color == null)
		{
			this.color = Hue.Transparent;
		}
		else
		{
			this.color = color;
		}
		
		if(brightness == null)
		{
			this.brightness = Brightness.High;
		}
		else
		{
			this.brightness = brightness;
		}
	}
	
	public MapColor(byte minecraftColor)
	{
		byte colorFromMinecraftColor = (byte)(minecraftColor >> 2);
		byte brightnessFromMinecraftColor = (byte)(minecraftColor & 3);
		
		if(0 > colorFromMinecraftColor || colorFromMinecraftColor > 13)
		{
			throw new IllegalArgumentException("There is no such color: " + colorFromMinecraftColor);
		}
		if(0 > brightnessFromMinecraftColor || brightnessFromMinecraftColor > 2)
		{
			throw new IllegalArgumentException("There is no such brightness: " + brightnessFromMinecraftColor);
		}
		
		this.color = Hue.values()[colorFromMinecraftColor];
		this.brightness = Brightness.values()[brightnessFromMinecraftColor];
	}
	
	public MapColor(Color color)
	{
		this(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	public MapColor(int r, int g, int b)
	{
		int minDistanceSquared = -1;
		MapColor closestMapColor = null;
		for(Hue aColor : Hue.values())
		{
			for(Brightness brightness : Brightness.values())
			{
				MapColor mapColor = new MapColor(aColor, brightness);
				int distanceSquared = mapColor.getDistanceSquared(r, g, b);
				if(distanceSquared < minDistanceSquared || minDistanceSquared == -1)
				{
					minDistanceSquared = distanceSquared;
					closestMapColor = mapColor;
				}
			}
		}
		this.color = closestMapColor.getHue();
		this.brightness = closestMapColor.getBrightness();
	}
	
	public byte getMinecraftColor()
	{
		return (byte) (color.getColor() << 2 | (color != Hue.Transparent?brightness.getBrightness():0));
	}

	public short getR()
	{
		return (short) Math.round(
									color == Hue.Transparent ? 
										color.getR() * 235 / 255F:
										color.getR() * brightness.getBrightnessFactor()
									
								);
	}
	
	public short getG()
	{
		return (short) Math.round(
				color == Hue.Transparent ? 
					color.getG() * 235 / 255F:
					color.getG() * brightness.getBrightnessFactor()
				
			);
	}
	
	public short getB()
	{
		return (short) Math.round(
				color == Hue.Transparent ? 
					color.getB() * 235 / 255F:
					color.getB() * brightness.getBrightnessFactor()
				
			);
	}
	
	public short[] getRGB()
	{
		short[] rgb = new short[3];
		rgb[0]=getR();
		rgb[0]=getG();
		rgb[0]=getB();
		return rgb;
	}

	public Color getColor()
	{
		return new Color(getR(), getG(), getB());
	}
	
	public Hue getHue()
	{
		return color;
	}
	
	public Brightness getBrightness()
	{
		return brightness;
	}
	
	public int getDistanceSquared(int r, int g, int b)
	{
		return (r-getR())*(r-getR())+(g-getG())*(g-getG())+(b-getB())*(b-getB());
	}
	
	public int getDistanceSquared(Color color)
	{
		return getDistanceSquared(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	public enum Hue
	{
		Transparent	(0xd6, 0xbe, 0x96),
		LightGreen	(0x7f, 0xb2, 0x38),
		LightBrown	(0xf7, 0xe9, 0xa3),
		Grey		(0xa7, 0xa7, 0xa7),
		Red			(0xff, 0x00, 0x00),
		LightBlue	(0xa0, 0xa0, 0xff),
		Gray2		(0xa7, 0xa7, 0xa7),
		Green		(0x00, 0x7c, 0x00),
		White		(0xff, 0xff, 0xff),
		Grey3		(0xa4, 0xa8, 0xb8),
		Brown		(0xb7, 0x6a, 0x2f),
		DarkGrey	(0x70, 0x70, 0x70),
		Blue		(0x40, 0x40, 0xff),
		DarkBrown	(0x68, 0x53, 0x32);
		
		byte r,g,b;
		Hue(int r, int g, int b)
		{
			this.r = (byte) r;
			this.g = (byte) g;
			this.b = (byte) b;
		}
		
		public int getColor()
		{
			return ordinal();
		}
		
		public short getR()
		{
			return (short)(r & 0xFF);
		}
		
		public short getG()
		{
			return (short)(g & 0xFF);
		}
		
		public short getB()
		{
			return (short)(b & 0xFF);
		}
		
		public short[] getRGB()
		{
			short[] rgb = new short[3];
			rgb[0]=getR();
			rgb[0]=getG();
			rgb[0]=getB();
			return rgb;
		}
	}

	public enum Brightness
	{
		Low(180),
		Middle(220),
		High(255);
		
		byte brightness;
		Brightness(int brightness)
		{

			System.out.println("br: "+brightness);
			this.brightness = (byte)brightness;
			System.out.println("brthis: "+this.brightness);
		}
		
		public byte getBrightness()
		{
			return (byte)ordinal();
		}
		
		public float getBrightnessFactor()
		{
			return (brightness & 0xFF) / 255F;
		}
	}
}
