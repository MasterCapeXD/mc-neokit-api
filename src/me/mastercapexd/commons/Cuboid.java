package me.mastercapexd.commons;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.common.base.Preconditions;

public class Cuboid {

	private final Point min, max, maxXMinYMinZ, maxXMinYMaxZ, minXMinYMaxZ, minXMaxYMaxZ, minXMaxYMinZ, maxXMaxYMinZ;
	
	public Cuboid(@Nonnull String world, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
		this(new Point(world, xMin, yMin, zMin), new Point(world, xMax, yMax, zMax));
	}
	
	public Cuboid(@Nonnull Point min, @Nonnull Point max) {
		Preconditions.checkArgument(min.getWorld().equals(max.getWorld()), "Different worlds of points!");
		String world = min.getWorld();
		
		int
		xMin = Math.min(min.getBlockX(), max.getBlockX()),
		yMin = Math.min(min.getBlockY(), max.getBlockY()),
		zMin = Math.min(min.getBlockZ(), max.getBlockZ()),
		xMax = Math.max(min.getBlockX(), max.getBlockX()),
		yMax = Math.max(min.getBlockY(), max.getBlockY()),
		zMax = Math.max(min.getBlockZ(), max.getBlockZ());
		
		this.min = new Point(world, xMin, yMin, zMin);
		this.max = new Point(world, xMax, yMax, zMax);
		
		this.maxXMinYMinZ = new Point(world, xMax, yMin, zMin);
		this.maxXMinYMaxZ = new Point(world, xMax, yMin, zMax);
		this.minXMinYMaxZ = new Point(world, xMin, yMin, zMax);
		this.minXMaxYMaxZ = new Point(world, xMin, yMax, zMax);
		this.minXMaxYMinZ = new Point(world, xMin, yMax, zMin);
		this.maxXMaxYMinZ = new Point(world, xMax, yMax, zMin);
	}
	
	@Nonnull
	public String getWorld() {
		return min.getWorld();
	}
	
	@Nonnull
	public Point getMinimumPoint() {
		return min;
	}
	
	public Point getMaxXMinYMinZ() {
		return maxXMinYMinZ;
	}
	
	public Point getMaxXMinYMaxZ() {
		return maxXMinYMaxZ;
	}
	
	public Point getMinXMinYMaxZ() {
		return minXMinYMaxZ;
	}
	
	public Point getMinXMaxYMaxZ() {
		return minXMaxYMaxZ;
	}
	
	public Point getMinXMaxYMinZ() {
		return minXMaxYMinZ;
	}
	
	public Point getMaxXMaxYMinZ() {
		return maxXMaxYMinZ;
	}
	
	@Nonnull
	public Point getMaximumPoint() {
		return max;
	}
	
	public Point[] getVertices() {
		return new Point[] {
				getMinimumPoint(), getMaxXMinYMinZ(),
				getMaxXMinYMaxZ(), getMinXMinYMaxZ(),
				getMinXMaxYMaxZ(), getMinXMaxYMinZ(),
				getMaxXMaxYMinZ(), getMaximumPoint()};
	}
	
	public boolean contains(Tile tile) {
		return toPlot().contains(tile);
	}
	
	public boolean contains(Point point) {
		return contains(point.toTile()) &&
				min.getBlockY() <= point.getBlockY() &&
				max.getBlockY() >= point.getBlockY();
	}
	
	public boolean intersects(Plot other) {
		return toPlot().intersects(other);
	}
	
	public boolean intersects(Cuboid other) {
		return intersects(other.toPlot())
				&& other.min.getBlockY() <= max.getBlockY()
				&& other.max.getBlockY() >= min.getBlockY();
	}
	
	public boolean intersects(Circle other) {
		return toPlot().intersects(other);
	}
	
	public boolean intersects(Cylinder other) {
		return intersects(other.toCircle())
				&& other.getMinimumY() <= max.getBlockY()
				&& other.getMaximumY() >= min.getBlockY();
	}
	
	public long getVolume() {
		return getWidth() * getHeight() * getDepth();
	}
	
	public long getWidth() {
		return max.getBlockX() - min.getBlockX() + 1;
	}
	
	public long getHeight() {
		return max.getBlockY() - min.getBlockY() + 1;
	}
	
	public long getDepth() {
		return max.getBlockZ() - min.getBlockZ() + 1;
	}
	
	public Plot toPlot() {
		return new Plot(getMinimumPoint().toTile(), getMaximumPoint().toTile());
	}
	
	@Nonnull
	@Override
	public String toString() {
		return "Cuboid: {min: " + min.toString() + "} {max: " + max.toString() + "}";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || (obj instanceof Cuboid))
			return false;
		
		Cuboid cuboid = (Cuboid) obj;
		return this.min.equals(cuboid.min) && this.max.equals(cuboid.max);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(min).append(max).build();
	}
}