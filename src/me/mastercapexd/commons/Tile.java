package me.mastercapexd.commons;

import javax.annotation.Nonnull;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;

public final class Tile {

	public static Tile fromString(@Nonnull String string) {
		String[] data = string.split(":");
		String[] coords = data[1].split(";");
		return new Tile(
				data[0],
				Double.parseDouble(coords[0]),
				Double.parseDouble(coords[1]));
	}
	
	public static String toString(@Nonnull Tile position) {
		return position.toString();
	}
	
	private final String world;
	private final double x, z;
	
	public Tile(@Nonnull Location location) {
		this(location.getWorld().getName(), location.getX(), location.getZ());
	}
	
	public Tile(@Nonnull String world, double x, double z) {
		this.world = world;
		this.x = x;
		this.z = z;
	}
	
	@Nonnull
	public String getWorld() {
		return world;
	}
	
	public double getX() {
		return x;
	}
	
	public double getZ() {
		return z;
	}
	
	public int getBlockX() {
		return (int) Math.floor(x);
	}
	
	public int getBlockZ() {
		return (int) Math.floor(z);
	}
	
	public Tile add(@Nonnull UnaryDirection direction, double add) {
		double x = 0, z = 0;
		
		switch (direction) {
		case NORTH:
			z-=add;
			break;
		case WEST:
			x-=add;
			break;
		case SOUTH:
			z+=add;
			break;
		case EAST:
			x+=add;
			break;
		default:
			break;
		}
		
		return add(x, z);
	}
	
	public Tile add(double x, double z) {
		return new Tile(world, this.x + x, this.z + z);
	}
	
	@Nonnull
	@Override
	public String toString() {
		return world + ":" + x + ";" + z;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || !(obj instanceof Tile))
			return false;
		
		Tile point = (Tile) obj;
		return this.world.equals(point.world) && this.x == point.x && this.z == point.z;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(world).append(x).append(z).toHashCode();
	}
}