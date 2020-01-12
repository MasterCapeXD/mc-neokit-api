package me.mastercapexd.commons;

import javax.annotation.Nonnull;

import org.apache.commons.lang.builder.HashCodeBuilder;

public final class Direction {

	public static final Direction DOWN = new Direction(90, 0);
	public static final Direction UP = new Direction(-90, 0);
	
	public static final Direction NORTH = new Direction(0, -179.9f);
	public static final Direction EAST = new Direction(0, -90);
	public static final Direction SOUTH = new Direction(0, 0);
	public static final Direction WEST = new Direction(0, 90);
	
	public static final UnaryDirection getCompassDirection(Direction direction) {
		if (isNorth(direction))
			return UnaryDirection.NORTH;
		else if (isWest(direction))
			return UnaryDirection.WEST;
		else if (isSouth(direction))
			return UnaryDirection.SOUTH;
		else if (isEast(direction))
			return UnaryDirection.EAST;
		
		return null;
	}
	
	public static boolean isTop(Direction direction) {
		return direction.getPitch() <= -45.0;
	}
	
	public static boolean isMedium(Direction direction) {
		return !isTop(direction) && !isBottom(direction);
	}
	
	public static boolean isBottom(Direction direction) {
		return direction.getPitch() >= 45.0;
	}
	
	public static boolean isNorth(Direction direction) {
		float yaw = Math.abs((direction.getYaw() % 360));
		return (yaw >= 135.1 && yaw <= 225.0);
	}
	
	public static boolean isEast(Direction direction) {
		float yaw = Math.abs((direction.getYaw() % 360));
		return direction.getYaw() >= 0 ? ((yaw >= 225.1 && yaw <= 315.0)) : ((yaw >= 45.1 && yaw <= 135.0));
	}
	
	public static boolean isSouth(Direction direction) {
		float yaw = Math.abs((direction.getYaw() % 360));
		return yaw >= 315.1 || yaw <= 45.0;
	}
	
	public static boolean isWest(Direction direction) {
		float yaw = Math.abs((direction.getYaw() % 360));
		return direction.getYaw() >= 0 ? ((yaw >= 45.1 && yaw <= 135.0)) : ((yaw >= 225.1 && yaw <= 315.0));
	}
	
	private final float pitch, yaw;
	
	public Direction(float pitch, float yaw) {
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	@Nonnull
	public Direction setPitch(float pitch) {
		return new Direction(pitch, yaw);
	}
	
	@Nonnull
	public Direction setYaw(float yaw) {
		return new Direction(pitch, yaw);
	}
	
	@Nonnull
	@Override
	public String toString() {
		return pitch + ";" + yaw;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || !(obj instanceof Direction))
			return false;
		
		Direction direction = (Direction) obj;
		return this.pitch == direction.pitch && this.yaw == direction.yaw;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(pitch).append(yaw).toHashCode();
	}
}