package me.mastercapexd.commons;

import javax.annotation.Nonnull;

public final class Cylinder {

	private final Circle circle;
	private final double yMin, yMax;
	
	public Cylinder(Tile center, double radius, double yMin, double yMax) {
		this(new Circle(center, radius), yMin, yMax);
	}
	
	public Cylinder(Circle circle, double yMin, double yMax) {
		this.circle = circle;
		this.yMin = Math.min(yMin, yMax);
		this.yMax = Math.max(yMin, yMax);
	}
	
	@Nonnull
	public String getWorld() {
		return circle.getWorld();
	}
	
	@Nonnull
	public Tile getCenter() {
		return circle.getCenter();
	}
	
	public double getRadius() {
		return circle.getRadius();
	}
	
	public boolean contains(Tile tile) {
		return circle.contains(tile);
	}
	
	public boolean contains(Point point) {
		return circle.contains(point.toTile())
				&& point.getBlockY() >= this.getMinimumY()
				&& point.getBlockY() <= this.getMaximumY();
	}
	
	public boolean intersects(Circle other) {
		return circle.intersects(other);
	}
	
	public boolean intersects(Cylinder other) {
		return circle.intersects(other.circle) &&
				((this.getMinimumY() >= other.getMinimumY() && this.getMinimumY() <= other.getMaximumY()) || 
				(this.getMaximumY() <= other.getMaximumY() && this.getMaximumY() >= other.getMinimumY()));
	}
	
	public boolean intersects(Cuboid cuboid) {
		return cuboid.intersects(this);
	}
	
	public double getMinimumY() {
		return yMin;
	}
	
	public double getMaximumY() {
		return yMax;
	}
	
	public Circle toCircle() {
		return new Circle(circle.getCenter(), circle.getRadius());
	}
	
	@Nonnull
	@Override
	public String toString() {
		return "Cylinder: {circle: " + circle.toString() + "} {minimum Y: " + yMin + ", maximum Y: " + yMax + "}";
	}
}