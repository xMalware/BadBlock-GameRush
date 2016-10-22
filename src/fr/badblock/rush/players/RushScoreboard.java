package fr.badblock.rush.players;

import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.BadblockPlayer.BadblockMode;
import fr.badblock.gameapi.players.BadblockTeam;
import fr.badblock.gameapi.players.scoreboard.BadblockScoreboardGenerator;
import fr.badblock.gameapi.players.scoreboard.CustomObjective;
import fr.badblock.rush.entities.RushTeamData;

public class RushScoreboard extends BadblockScoreboardGenerator {
	public static final String WINS 	  = "wins",
							   KILLS 	  = "kills",
							   DEATHS 	  = "deaths",
							   LOOSES 	  = "looses",
							   BROKENBEDS = "brokenbeds";
	
	private CustomObjective objective;
	private BadblockPlayer  player;

	public RushScoreboard(BadblockPlayer player){
		this.objective = GameAPI.getAPI().buildCustomObjective("rush");
		this.player    = player;

		objective.showObjective(player);
		objective.setDisplayName("&b&o" + GameAPI.getGameName());
		objective.setGenerator(this);

		objective.generate();
	}

	@Override
	public void generate(){
		objective.changeLine(15, "&8&m----------------------");

		int i = 14;

		for(BadblockTeam team : GameAPI.getAPI().getTeams()){
			RushTeamData data = team.teamData(RushTeamData.class);

			if(!data.hasBed())
				objective.changeLine(i, team.getChatName().getAsLine(player) + " > &c✘");
			else objective.changeLine(i, team.getChatName().getAsLine(player) + " > &a✔");
			i--;
		}

		if(player.getBadblockMode() != BadblockMode.SPECTATOR){
			objective.changeLine(i,  ""); i--;

			objective.changeLine(i,  i18n("rush.scoreboard.wins", stat(WINS))); i--;
			objective.changeLine(i,  i18n("rush.scoreboard.kills", stat(KILLS))); i--;
			objective.changeLine(i,  i18n("rush.scoreboard.deaths", stat(DEATHS))); i--;
			objective.changeLine(i,  i18n("rush.scoreboard.brokenbeds", stat(BROKENBEDS))); i--;
		}

		for(int a=3;a<=i;a++)
			objective.removeLine(a);

		objective.changeLine(2,  "&8&m----------------------");
	}
	
	private int stat(String name){
		return (int) player.getPlayerData().getStatistics("rush", name);
	}

	private String i18n(String key, Object... args){
		return GameAPI.i18n().get(player.getPlayerData().getLocale(), key, args)[0];
	}
}