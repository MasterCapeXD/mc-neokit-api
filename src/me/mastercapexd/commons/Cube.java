package me.mastercapexd.commons;

import javax.annotation.Nonnull;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Cube {

	private final Cuboid cuboid;
	private final Point center;
	private final double length;
	
	public Cube(Point center, double length) {
		this.cuboid = new Cuboid(
				new Point(center.getWorld(), center.getX() - length, center.getY() - length, center.getZ() - length),
				new Point(center.getWorld(), center.getX() + length, center.getY() + length, center.getZ() + length));
		this.center = center;
		this.length = length;
	}
	
	@Nonnull
	public Point getCenter() {
		return center;
	}
	
	public double getLength() {
		return length;
	}
	
	@Nonnull
	public String getWorld() {
		return cuboid.getWorld();
	}
	
	@Nonnull
	public Point getMinimumPoint() {
		return cuboid.getMinimumPoint();
	}
	
	public Point getMaxXMinYMinZ() {
		return cuboid.getMaxXMinYMinZ();
	}
	
	public Point getMaxXMinYMaxZ() {
		return cuboid.getMaxXMinYMinZ();
	}
	
	public Point getMinXMinYMaxZ() {
		return cuboid.getMinXMinYMaxZ();
	}
	
	public Point getMinXMaxYMaxZ() {
		return cuboid.getMinXMaxYMaxZ();
	}
	
	public Point getMinXMaxYMinZ() {
		return cuboid.getMinXMaxYMinZ();
	}
	
	public Point getMaxXMaxYMinZ() {
		return cuboid.getMaxXMaxYMinZ();
	}
	
	@Nonnull
	public Point getMaximumPoint() {
		return cuboid.getMaximumPoint();
	}
	
	public Point[] getVertices() {
		return cuboid.getVertices();
	}
	
	public boolean contains(Tile tile) {
		return cuboid.contains(tile);
	}
	
	public boolean contains(Point point) {
		return cuboid.contains(point);
	}
	
	public boolean intersects(Plot other) {
		return cuboid.intersects(other);
	}
	
	public boolean intersects(Cube other) {
		return cuboid.intersects(other.toCuboid());
	}
	
	public boolean intersects(Cuboid other) {
		return cuboid.intersects(other);
	}
	
	public boolean intersects(Circle other) {
		return cuboid.intersects(other);
	}
	
	public boolean intersects(Cylinder other) {
		return cuboid.intersects(other);
	}
	
	public long getVolume() {
		return cuboid.getVolume();
	}
	
	public long getWidth() {
		return cuboid.getWidth();
	}
	
	public long getHeight() {
		return cuboid.getHeight();
	}
	
	public long getDepth() {
		return cuboid.getDepth();
	}
	
	@Nonnull
	public Plot toPlot() {
		return cuboid.toPlot();
	}
	
	@Nonnull
	public Cuboid toCuboid() {
		return cuboid;
	}
	
	@Nonnull
	@Override
	public String toString() {
		return "Cube: {center: " + center.toString() + "} {length: " + length + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || !(obj instanceof Cube))
			return false;
		
		Cube cube = (Cube) obj;
		return this.center.equals(cube.center) && this.length == cube.length;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(center).append(length).append(cuboid).toHashCode();
	}
}