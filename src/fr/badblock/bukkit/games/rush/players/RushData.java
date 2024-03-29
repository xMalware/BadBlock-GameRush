package fr.badblock.bukkit.games.rush.players;

import fr.badblock.gameapi.players.data.InGameData;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RushData implements InGameData {
	public int kills 		= 0;
	public int deaths		= 0;
	public int brokedBeds   = 0;
	
	public int getScore(){
		return (kills * 20 + brokedBeds * 100) / (deaths == 0 ? 1 : (/* 10 * */deaths));
	}
}
