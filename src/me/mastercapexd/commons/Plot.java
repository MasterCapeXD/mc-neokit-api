package me.mastercapexd.commons;

import javax.annotation.Nonnull;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.common.base.Preconditions;

public class Plot {

	private final Tile min, max, minXMaxZ, maxXMinZ;
	
	public Plot(@Nonnull String world, int xMin, int zMin, int xMax, int zMax) {
		this(new Tile(world, xMin, zMin), new Tile(world, xMax, zMax));
	}
	
	public Plot(@Nonnull Tile min, @Nonnull Tile max) {
		Preconditions.checkArgument(min.getWorld().equals(max.getWorld()), "Different worlds of points!");
		
		String world = min.getWorld();
		
		int
		xMin = Math.min(min.getBlockX(), max.getBlockX()),
		zMin = Math.min(min.getBlockZ(), max.getBlockZ()),
		xMax = Math.max(min.getBlockX(), max.getBlockX()),
		zMax = Math.max(min.getBlockZ(), max.getBlockZ());
		
		this.min = new Tile(world, xMin, zMin);
		this.max = new Tile(world, xMax, zMax);
		this.minXMaxZ = new Tile(world, xMin, zMax);
		this.maxXMinZ = new Tile(world, xMax, zMin);
	}
	
	@Nonnull
	public String getWorld() {
		return min.getWorld();
	}
	
	@Nonnull
	public Tile getMinimum() {
		return min;
	}
	
	public Tile getMinXMaxZ() {
		return minXMaxZ;
	}
	
	public Tile getMaxXMinZ() {
		return maxXMinZ;
	}
	
	@Nonnull
	public Tile getMaximum() {
		return max;
	}
	
	public Tile[] getVertices() {
		return new Tile[] {getMinimum(), getMinXMaxZ(), getMaxXMinZ(), getMaximum()};
	}
	
	public boolean contains(Tile tile) {
		if (!getWorld().equals(tile.getWorld()))
			return false;
		
		return
				min.getBlockX() <= tile.getBlockX() && min.getBlockZ() <= tile.getBlockZ() &&
				max.getBlockX() >= tile.getBlockX() && max.getBlockZ() >= tile.getBlockZ();
	}
	
	public boolean intersects(Plot other) {
		if (!getWorld().equals(other.getWorld()))
			return false;
		
		return other.min.getBlockX() <= max.getBlockX()
				&& other.max.getBlockX() >= min.getBlockX()
				&& other.min.getBlockZ() <= max.getBlockZ()
				&& other.max.getBlockZ() >= min.getBlockZ();
	}
	
	public boolean intersects(Circle other) {
		if (!getWorld().equals(other.getWorld()))
			return false;
		
		if (contains(other.getCenter()))
			return true;
		
		boolean intersects = false;
		for (Tile tile : getVertices()) {
			int distance = (int) Math.sqrt((tile.getBlockX() - other.getCenter().getBlockX()) * (tile.getBlockX() - other.getCenter().getBlockX()) + (tile.getBlockZ() - other.getCenter().getBlockZ()) * (tile.getBlockZ() - other.getCenter().getBlockZ()));
			if (distance <= other.getRadius()) {
				intersects = true;
				break;
			}
		}
		return intersects;
	}
	
	public long getSquare() {
		return getWidth() * getDepth();
	}
	
	public long getWidth() {
		return max.getBlockX() - min.getBlockX() + 1;
	}
	
	public long getDepth() {
		return max.getBlockZ() - min.getBlockZ() + 1;
	}
	
	@Nonnull
	@Override
	public String toString() {
		return "Plot: {min: " + min.toString() + "} {max: " + max.toString() + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || !(obj instanceof Plot))
			return false;
		
		Plot plot = (Plot) obj;
		return this.min.equals(plot.min) && this.max.equals(plot.max);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(min).append(max).toHashCode();
	}
}