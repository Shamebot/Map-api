package shamebot.Map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import shamebot.Map.MapColor.Brightness;
import shamebot.Map.MapColor.Hue;


public class Map extends JavaPlugin {

	public void onDisable() {
		// TODO Auto-generated method stub

	}

	public void onEnable() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender instanceof Player)
		{

			final Player player = (Player)sender;
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
				{
					int count = 0;					MapImage mapImage = new MapImage(0,0,128,128);
					BufferedImage image = new BufferedImage(128, 50, BufferedImage.TYPE_INT_ARGB);
					Graphics2D g = image.createGraphics();
					{g.setBackground(new MapColor(null, null).getColor());}
					char[] chars = "abc d123hid ulo +  _".toCharArray();
					public void run()
					{
						count = (count + 3) % 128;
						mapImage.fill(new MapColor(null, null));
						g.clearRect(0, 0, 128, 50);
						g.drawChars(chars, 0, chars.length, count, 35);
						mapImage.drawImage((short)0, (short)50,image);
						mapImage.send(player, (short)0);
					}
				}, 0, 5);
		}
		return true;
	}
}
