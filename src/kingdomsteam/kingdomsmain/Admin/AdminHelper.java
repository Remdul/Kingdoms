package kingdomsteam.kingdomsmain.Admin;

import java.util.ArrayList;
import java.util.List;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Util.Util;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminHelper {
	public static boolean isAlreadyBannedFromKingdoms(String player) {
		boolean bannedStatus = Main.players.getBoolean("Players."+player.toLowerCase()+".Banned");
		if(bannedStatus == false){
			return false;
		}
		return true;
	}
	public static boolean isBannedFromKingdoms(CommandSender s) {
		String playerName = s.getName().toLowerCase();
		boolean isBannedFromKingdoms = Main.players.getBoolean("Players." + playerName + ".Banned");
		if (isBannedFromKingdoms == false) {
			return false;
		}
		return true;
	}
	public static boolean isNewTagWithin3xTheOldLength(String inputtedPrefix, String kingdomName){
		
		int lengthOfKingdomName = kingdomName.length();
		int lengthOfInputtedPrefix = inputtedPrefix.length();
		
		if(lengthOfInputtedPrefix > (lengthOfKingdomName * 3)){
			return false;
		}
		return true;
	}
	
	public static boolean adminDisband(CommandSender s, String[] args){
		
		String sendersCurrentKingdom = args[2].toLowerCase();
		
		if (Main.landcontrol.getConfigurationSection("Chunks.") != null){
			for (String chunkRepresentation : Main.landcontrol.getConfigurationSection("Chunks.").getKeys(false)){
				String kingdomNameRepresentation = Main.landcontrol.getString("Chunks."+chunkRepresentation+".Owning_Kingdom");
				if ((Main.landcontrol.getString("Chunks."+chunkRepresentation) !=null )){
					if(kingdomNameRepresentation.matches(sendersCurrentKingdom)){
						Main.landcontrol.set("Chunks."+chunkRepresentation, null);
					}
				}
			}
		}
		for(String playerNameRepresentation : Main.players.getConfigurationSection("Players").getKeys(false)){
			playerNameRepresentation = playerNameRepresentation.toLowerCase();
			String kingdomNameRepresentation = Main.players.getString("Players." + playerNameRepresentation + ".Current_Kingdom");
			if(kingdomNameRepresentation.matches(sendersCurrentKingdom)){
				Main.players.set("Players." + playerNameRepresentation + ".Kingdom_Leader", false);
				Main.players.set("Players." + playerNameRepresentation + ".Current_Kingdom", "None");
				Main.players.set("Players." + playerNameRepresentation + ".Current_Rank", 0);
				Main.players.set("Players." + playerNameRepresentation + ".Kingdom_Contributions", 0);
				Main.players.set("Players." + playerNameRepresentation + ".Member_Since", "N/A");
				Main.players.set("Players." + playerNameRepresentation + ".Current_Invitation", "N/A");
			}
		}
		Util.sm(s,"You disbanded the Kingdom called " + Util.getRealKingdomName(sendersCurrentKingdom) + "!");
		Util.bc(" The Kingdom [" + Util.getRealKingdomName(sendersCurrentKingdom) + ChatColor.GRAY + "] has disbanded.");
		Main.kingdoms.set("Kingdoms." + sendersCurrentKingdom, null);
		Main.savePlayersYaml();
		Main.saveLandControlYaml();
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean adminKick(String[] args, CommandSender s){
		List<String> kingdomMembers = new ArrayList<String>();
		String sendersPlayerName;
		int currRosterSize;
		int updRosterSize;
		String targetPlayersName;
		String targetPlayersKingdom;
		String sendersKingdom;
		targetPlayersName = args[1].toLowerCase();
		targetPlayersKingdom = Main.players.getString("Players." + targetPlayersName + ".Current_Kingdom");
		sendersPlayerName = s.getName().toLowerCase();
		sendersKingdom = Main.players.getString("Players." + sendersPlayerName + ".Current_Kingdom");
		currRosterSize = Main.kingdoms.getInt("Kingdoms." + sendersKingdom + ".Roster_Size");
		updRosterSize = currRosterSize - 1;
		kingdomMembers = Main.kingdoms.getStringList("Kingdoms."+targetPlayersKingdom+".Members");
		kingdomMembers.remove(targetPlayersName);
		Main.kingdoms.set("Kingdoms."+sendersKingdom+".Members", kingdomMembers);
		Main.players.set("Players." + targetPlayersName + ".Kingdom_Leader", false);
		Main.players.set("Players." + targetPlayersName + ".Current_Kingdom", "None");
		Main.players.set("Players." + targetPlayersName + ".Current_Rank", 0);
		Main.players.set("Players." + targetPlayersName + ".Kingdom_Contributions", 0);
		Main.players.set("Players." + targetPlayersName + ".Member_Since", "N/A");
		Main.players.set("Players." + targetPlayersName + ".Current_Invitation", "N/A");
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Roster_Size", updRosterSize);
		Util.sm(s,"You kicked the user " + targetPlayersName + " from " + sendersKingdom + ".");
		Util.gbc(sendersKingdom, sendersPlayerName+" has kicked "+targetPlayersName+" from the Kingdom.");
        Util.pm(targetPlayersName, sendersPlayerName+" has kicked you from the Kingdom.");
		Main.savePlayersYaml();
		Main.saveKingdomsYaml();
		return true;
	}
}
