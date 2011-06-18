package shamebot.Map;

import net.minecraft.server.Item;
import net.minecraft.server.Packet131;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import shamebot.Map.MapColor.Hue;

public class MapColumn {

	byte[] data;
	byte x,y;
	boolean unsent = true;
	
	public MapColumn(byte x, byte y, short height)
	{
		data = new byte[height];
		this.x = x;
		this.y = y;
	}
	
	public MapColumn(byte[] data, byte x, byte y)
	{
		this.data = data;
		this.x = x;
		this.y = y;
	}
	
	public MapPixel getPixel(short y)
	{
		return new MapPixel(data[(short)(y & 0xFF)], x, (byte) y);
	}
	
	public short getHeight()
	{
		return (short)data.length;
	}
	
	public void setPixel(MapPixel pixel)
	{
		setPixel(pixel, pixel.getY());
	}
	
	public void setPixel(MapColor color, short y)
	{
		if((data[y] & 0x3F) != color.getMinecraftColor() || unsent)
		{
			data[y] = (byte)(color.getMinecraftColor() | 0x40);
		}
	}
	
	public void send(Player player, short mapItemDamage)
	{
		unsent = false;
		short start = 0, end = 0;
		for(short i=0; i<data.length+1; i++)
		{
			if(i != data.length && (data[i] & 0x40) == 0x40)
			{
				end = i;
				if(i == data.length)
				{
					send(player, start, end, mapItemDamage);
				}
			}
			else
			{
				if(end > start)
				{
					for(i++;i<data.length && (data[i] & 0x40) != 0x40;i++);
					if(--i - end >= 3 || i == data.length)
					{
						send(player, start, end, mapItemDamage);
						start = i;
					}
					end = i;
				}
				else
				{
					start = (short) (i+1);
					end = i;
				}
			}
		}
		//-----
		/*short start = 0, end = 0;
		for(short i=0; i<data.length+1; i++)
		{
			if(i != data.length && (data[i] & 0x40) == 0x40)
			{
				end = i;
			}
			else
			{
				if(end > start)
				{
					for(i++;i<data.length && (data[i] & 0x40) != 0x40;i++);
					if(i - 1 - end >= 3)
					{
						System.out.println(start+","+end);
						send(player, start, end, mapItemDamage);
						start = i;
					}
					end = i;
				}
				else
				{
					start = end = i;
				}
			}
		}*/
	}
	
	static int count = 0;
	private void send(Player player, short start, short end, short mapItemDamage)
	{
		count++;
		byte[] dataPart = new byte[end - start + 4];
		dataPart[0] = 0;
		dataPart[1] = x;
		dataPart[2] = (byte)(y + start);
		for(short i = 0; i < end - start; i++)
		{
			data[i+start] = (byte)(data[i+start] & 0x3F);
			dataPart[i+3] = (byte)/*((count%14)*4+2);//*/data[i+start];
		}
		((CraftPlayer)player).getHandle().netServerHandler.sendPacket(new Packet131((short)Item.MAP.id,mapItemDamage,dataPart));
	}
}
