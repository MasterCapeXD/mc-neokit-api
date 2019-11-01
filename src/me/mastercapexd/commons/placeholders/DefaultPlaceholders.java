package me.mastercapexd.commons.placeholders;

import org.bukkit.World;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Player;

public class DefaultPlaceholders {

	public static final Placeholder<AnimalTamer> ENTITY_NAME_PLACEHOLDER = Placeholder.create("{entity_name}", entity -> entity.getName());
	
	public static final Placeholder<World> WORLD_NAME_PLACEHOLDER = Placeholder.create("{world}", world -> world.getName());
	
	public static final Placeholder<Player> PLAYER_LEVEL_PLACEHOLDER = Placeholder.create("{level}", player -> (player.getLevel() + ""));
	
	public static final Placeholder<Number> COUNT_PLACEHOLDER = Placeholder.create("{count}", Object::toString);
	
	private DefaultPlaceholders() {
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}