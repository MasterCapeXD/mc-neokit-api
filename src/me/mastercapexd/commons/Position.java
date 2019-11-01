package me.mastercapexd.commons;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Location;

public final class Position {

	private final Point point;
	private final Direction direction;
	
	public Position(@Nonnull Location location) {
		this(location.getWorld().getName(),
				location.getX(), location.getY(), location.getZ(),
				location.getPitch(), location.getYaw());
	}
	
	public Position(@Nonnull String world, double x, double y, double z, float pitch, float yaw) {
		this(new Point(world, x, y, z), new Direction(pitch, yaw));
	}
	
	public Position(@Nonnull Point point, Direction direction) {
		this.point = point;
		this.direction = direction;
	}
	
	@Nonnull
	public String getWorld() {
		return point.getWorld();
	}
	
	@Nonnull
	public Position switchWorld(String world) {
		return new Position(world, point.getX(), point.getY(), point.getZ(), direction.getPitch(), direction.getYaw());
	}
	
	public double getX() {
		return point.getX();
	}
	
	public double getY() {
		return point.getY();
	}
	
	public double getZ() {
		return point.getZ();
	}
	
	public int getBlockX() {
		return point.getBlockX();
	}
	
	public int getBlockY() {
		return point.getBlockY();
	}
	
	public int getBlockZ() {
		return point.getBlockZ();
	}
	
	@Nonnull
	public Position add(@Nonnull UnaryDirection direction, double add) {
		return new Position(point.add(direction, add), this.direction);
	}
	
	public Position add(double x, double y, double z) {
		return new Position(point.add(x, y, z), direction);
	}
	
	@Nonnull
	public Position setX(double x) {
		return new Position(point.getWorld(), x, point.getY(), point.getZ(), direction.getPitch(), direction.getYaw());
	}
	
	@Nonnull
	public Position setY(double y) {
		return new Position(point.getWorld(), point.getX(), y, point.getZ(), direction.getPitch(), direction.getYaw());
	}
	
	@Nonnull
	public Position setZ(double z) {
		return new Position(point.getWorld(), point.getX(), point.getY(), z, direction.getPitch(), direction.getYaw());
	}
	
	public float getPitch() {
		return direction.getPitch();
	}
	
	public float getYaw() {
		return direction.getYaw();
	}
	
	@Nonnull
	public Position setPitch(float pitch) {
		return new Position(point.getWorld(), point.getX(), point.getY(), point.getZ(), pitch, direction.getYaw());
	}
	
	@Nonnull
	public Position setYaw(float yaw) {
		return new Position(point.getWorld(), point.getX(), point.getY(), point.getZ(), direction.getPitch(), yaw);
	}
	
	@Nonnull
	public Point toPoint() {
		return point;
	}
	
	@Nonnull
	public Direction toDirection() {
		return direction;
	}
	
	@Nonnull
	public Location toLocation() {
		Location location = point.toLocation();
		location.setPitch(direction.getPitch());
		location.setYaw(direction.getYaw());
		return location;
	}
	
	@Nonnull
	@Override
	public String toString() {
		return point.toString() + ":" + direction.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || (obj instanceof Position))
			return false;
		
		Position position = (Position) obj;
		return this.point.equals(position.point) && this.direction.equals(position.direction);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(point).append(direction).build();
	}
}