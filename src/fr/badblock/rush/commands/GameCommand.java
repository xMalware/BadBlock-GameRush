package fr.badblock.rush.commands;

import org.bukkit.command.CommandSender;

import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.BadblockPlayer.GamePermission;
import fr.badblock.gameapi.players.BadblockTeam;
import fr.badblock.gameapi.utils.i18n.TranslatableString;
import fr.badblock.rush.PluginRush;
import fr.badblock.rush.runnables.StartRunnable;

public class GameCommand extends AbstractCommand {
	public GameCommand() {
		super("game", new TranslatableString("commands.grush.usage"), GamePermission.BMODERATOR);
		allowConsole(false);
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		if(args.length == 0) {
			return false;
		}
		
		BadblockPlayer player = (BadblockPlayer) sender;
		PluginRush plug = PluginRush.getInstance();

		switch(args[0].toLowerCase()){
			case "start":
				String msg = "commands.grush.start";
				
				if(!StartRunnable.started()){
					StartRunnable.startGame(true);
				} else msg += "-fail";
				
				player.sendTranslatedMessage(msg);
			break;
			case "stop":
				msg = "commands.grush.stop";
				
				if(StartRunnable.started()){
					StartRunnable.stopGame();
				} else msg += "-fail";
				
				player.sendTranslatedMessage(msg);
			break;
			case "playersperteam":
				if(args.length != 2)
					return false;
				
				int perTeam = 4;
				
				try {
					perTeam = Integer.parseInt(args[1]);
				} catch(Exception e){
					return false;
				}
				
				for(BadblockTeam team : GameAPI.getAPI().getTeams())
					team.setMaxPlayers(perTeam);
				
				plug.getConfiguration().maxPlayersInTeam = perTeam;
				plug.setMaxPlayers(GameAPI.getAPI().getTeams().size() * perTeam);
				
				player.sendTranslatedMessage("commands.grush.modifycount");
			break;
			default: return false;
		}
		
		return true;
	}
}