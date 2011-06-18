package shamebot.Map;

import java.awt.Color;

public class MapPixel extends MapColor {

	private byte x,y;
	public MapPixel(Hue color, Brightness brightness, byte x, byte y) {
		super(color, brightness);
		this.x = x;
		this.y = y;
	}
	
	public MapPixel(byte minecraftColor, byte x, byte y) {
		super(minecraftColor);
		this.x = x;
		this.y = y;
	}
	
	public MapPixel(MapColor mapColor, byte x, byte y) {
		super(mapColor.getHue(), mapColor.getBrightness());
		this.x = x;
		this.y = y;
	}
	
	public MapPixel(int r, int g, int b, byte x, byte y)
	{
		super(null, null);
		int minDistanceSquared = -1;
		MapColor closestMapColor = null;
		for(Hue aColor : Hue.values())
		{
			for(Brightness brightness : Brightness.values())
			{
				MapPixel mapPixel = new MapPixel(aColor, brightness, x, y);
				int distanceSquared = mapPixel.getDistanceSquared(r, g, b);
				if(distanceSquared < minDistanceSquared || minDistanceSquared == -1)
				{
					minDistanceSquared = distanceSquared;
					closestMapColor = mapPixel;
				}
			}
		}
		this.color = closestMapColor.getHue();
		this.brightness = closestMapColor.getBrightness();
	}
	
	public MapPixel(Color color, byte x, byte y)
	{
		this(color.getRed(), color.getGreen(), color.getBlue(), x, y);
	}
	
	public short getX()
	{
		return (short)(x & 0xFF);
	}
	
	public short getY()
	{
		return (short)(y & 0xFF);
	}
	
	@Override
	public short getR()
	{
		return (short) Math.round(
				color == Hue.Transparent ? 
					color.getR() * (239 - (x+y & 1) * 8) / 255F:
					super.getR()
				
			);
	}

	@Override
	public short getG()
	{
		return (short) Math.round(
				color == Hue.Transparent ? 
					color.getG() * (239 - (x+y & 1) * 8) / 255F:
					super.getG()
				
			);
	}
	
	@Override
	public short getB()
	{
		return (short) Math.round(
				color == Hue.Transparent ? 
					color.getB() * (239 - (x+y & 1) * 8) / 255F:
					super.getB()
				
			);
	}


}
