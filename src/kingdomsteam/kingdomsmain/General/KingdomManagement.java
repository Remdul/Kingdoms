package kingdomsteam.kingdomsmain.General;

import java.util.ArrayList;
import java.util.List;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Util.Util;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class KingdomManagement {
	
	static List<String> kingdomMembers = new ArrayList<String>();
	
	public static boolean setKingdomType(String[] args, CommandSender s){
		String sendersCurrKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		Main.kingdoms.set("Kingdoms."+sendersCurrKingdom+".Type", args[1]);
		Util.sm(s,"You set your kingdoms type to \"" + args[1] + "\".");
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean disband(String[] args, CommandSender s){
		String sendersPlayerName = s.getName().toLowerCase();
		String sendersCurrentKingdom = Main.players.getString("Players." + sendersPlayerName + ".Current_Kingdom");
		
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
		Util.sm(s,"You disbanded your kingdom called " + Util.getRealKingdomName(sendersCurrentKingdom) + ChatColor.GRAY + "!");
		Util.bc(" The kingdom [" + Util.getRealKingdomName(sendersCurrentKingdom) + ChatColor.GRAY + "] has disbanded.");
		Main.kingdoms.set("Kingdoms." + sendersCurrentKingdom, null);
		Main.saveKingdomsYaml();
		Main.savePlayersYaml();
		Main.saveLandControlYaml();
		return true;
	}
	public static boolean kick(String[] args, CommandSender s){
		String targetPlayersName = args[1].toLowerCase();
		String sendersPlayerName = s.getName().toLowerCase();
		String sendersKingdom = Main.players.getString("Players." + sendersPlayerName + ".Current_Kingdom");
		int currRosterSize = Main.kingdoms.getInt("Kingdoms." + sendersKingdom + ".Roster_Size");
		int updRosterSize = currRosterSize - 1;
		kingdomMembers = Main.kingdoms.getStringList("Kingdoms."+sendersKingdom+".Members");
		int size = kingdomMembers.size();
		String sendersRealKingdomName = Util.getRealKingdomName(sendersKingdom);
		String targetsRealPlayerName = Util.getRealPlayerName(targetPlayersName);
		String sendersRealPlayerName = Util.getRealPlayerName(sendersPlayerName);
		Util.sm(s,"You kicked the user " + targetsRealPlayerName + " from " + sendersRealKingdomName + ".");
		Util.gbc(sendersKingdom, Util.getRealPlayerName(sendersPlayerName)+" has kicked "+targetsRealPlayerName+" from the kingdom.");
		CommandSender dude = (CommandSender) Bukkit.getPlayer(targetPlayersName);
        Util.sm(dude, sendersRealPlayerName + " has kicked you from the kingdom " + Util.getRealKingdomName(sendersKingdom) + ChatColor.GRAY + ".");
		for (int i=0; i<size;i++){
			String kingdomMember;
			kingdomMember = kingdomMembers.get(i);
			if (kingdomMember.toLowerCase().matches(targetPlayersName)){
				kingdomMembers.remove(kingdomMember);
				System.out.println(kingdomMember);
			}
		}
		Main.kingdoms.set("Kingdoms."+sendersKingdom+".Members", kingdomMembers);
		Main.players.set("Players." + targetPlayersName + ".Kingdom_Leader", false);
		Main.players.set("Players." + targetPlayersName + ".Current_Kingdom", "None");
		Main.players.set("Players." + targetPlayersName + ".Current_Rank", 0);
		Main.players.set("Players." + targetPlayersName + ".Kingdom_Contributions", 0);
		Main.players.set("Players." + targetPlayersName + ".Member_Since", "N/A");
		Main.players.set("Players." + targetPlayersName + ".Current_Invitation", "N/A");
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Roster_Size", updRosterSize);
		Main.savePlayersYaml();
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean setMaxMembers(String[] args, CommandSender s){
		String sendersCurrKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		int newMaxMembers = Integer.parseInt(args[1].toLowerCase());
		Main.kingdoms.set("Kingdoms."+sendersCurrKingdom+".Max_Members", newMaxMembers);
		Util.sm(s,"You set your kingdoms maxmembers to \"" + args[1] + "\".");
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean setRankName(String[] args, CommandSender s){
		String newRankName = args[2];
		if(newRankName.length() > Main.mainConfig.getInt("General.Maximum_Characters_For_Rank_Names") || newRankName.length() < Main.mainConfig.getInt("General.Minimum_Characters_For_Rank_Names")){
			Util.er(s, "That rank name doesn't fall between " + String.valueOf(Main.mainConfig.getInt("General.Minimum_Characters_For_Rank_Names")) + " and " + String.valueOf(Main.mainConfig.getInt("General.Maximum_Characters_For_Rank_Names")) + " characters, unable to rename rank.");
			return false;
		}
		String inputtedRankNumber = args[1];
		String sendersPlayerName = s.getName().toLowerCase();
		String sendersCurrKingdom = Main.players.getString("Players." + sendersPlayerName + ".Current_Kingdom");
		String oldRankName = Main.kingdoms.getString("Kingdoms." + sendersCurrKingdom + ".Ranks." + inputtedRankNumber);
		Main.kingdoms.set("Kingdoms." + sendersCurrKingdom + ".Ranks." + inputtedRankNumber, newRankName);
		Util.sm(s,"You renamed the rank \"" + oldRankName + "\" to \"" + newRankName + "\".");
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean setLowestRank(String[] args, CommandSender s){
		String sendersCurrKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		Main.kingdoms.set("Kingdoms."+sendersCurrKingdom+".New_Member_Starting_Rank", Integer.parseInt(args[1]));
		Util.sm(s,"You set your kingdoms starting rank to \"" + args[1] + "\".");
		Main.saveKingdomsYaml();
		return true;
	}
}
