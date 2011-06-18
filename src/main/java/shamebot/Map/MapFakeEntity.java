package shamebot.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;

import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;

public class MapFakeEntity extends Entity {

	public MapFakeEntity(Location loc) {
		super(((CraftWorld)loc.getWorld()).getHandle());
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
	}

	@Override
	protected void a(NBTTagCompound arg0) {
	}

	@Override
	protected void b() {
	}

	@Override
	protected void b(NBTTagCompound arg0) {
	}

}
