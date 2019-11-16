package me.mastercapexd.commons;

import javax.annotation.Nonnull;

public class Circle {

	private final Tile center;
	private final double radius;
	
	public Circle(String world, double x, double z, double radius) {
		this(new Tile(world, x, z), radius);
	}
	
	public Circle(Tile center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	@Nonnull
	public String getWorld() {
		return center.getWorld();
	}
	
	@Nonnull
	public Tile getCenter() {
		return center;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public boolean contains(Tile tile) {
		return Math.pow((tile.getBlockX() - center.getBlockX()), 2) +
				Math.pow((tile.getBlockZ() - center.getBlockZ()), 2) <= Math.pow(radius, 2);
	}
	
	public boolean intersects(Circle other) {
		if (!getWorld().equals(other.getWorld()))
			return false;
		
		if (contains(other.getCenter()) || other.contains(this.getCenter()))
			return true;
		
		double length = Math.abs(Math.sqrt(
				Math.pow(other.getCenter().getBlockX() - this.getCenter().getBlockX(), 2)
				+ Math.pow(other.getCenter().getBlockZ() - this.getCenter().getBlockZ(), 2)));
		
		return length - other.getRadius() - this.getRadius() > 0;
	}
	
	@Nonnull
	@Override
	public String toString() {
		return "Circle: {center: " + center.toString() + "} {radius: " + radius + "}";
	}
}