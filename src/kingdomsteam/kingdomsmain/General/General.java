package kingdomsteam.kingdomsmain.General;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class General {

	static List<String> kingdomMembers;
	static Set<String> kingdomList;
	static Set<String> memberList;
	static Set<String> listOfPlayers;
	static Set<String> rankList;
	static List<String> playerNames = new ArrayList<String>();
	
	public static boolean getPlayerInfo(String[] args, CommandSender s){
		String sendersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		String targetsCurrentKingdom = Main.players.getString("Players." + args[1].toLowerCase() + ".Current_Kingdom");
		String targetsCurrentKingdomProper = Main.kingdoms.getString("Kingdoms." + targetsCurrentKingdom.toLowerCase() + ".Chat_Prefix");
		int targetPlayersContributions = Main.players.getInt("Players." + args[1].toLowerCase() + ".Kingdom_Contributions");
		int targetsRankInt = Main.players.getInt("Players." + args[1].toLowerCase() + ".Current_Rank");
		String targetsRank = Main.kingdoms.getString("Kingdoms." + targetsCurrentKingdom.toLowerCase() + ".Ranks." + targetsRankInt);
		String targetsMemberSince = Main.players.getString("Players." + args[1].toLowerCase() + ".Member_Since");
		String targetKingdomsWarEnemy = Main.kingdoms.getString("Kingdoms." + targetsCurrentKingdom + ".Current_War_Enemy");
		List<String> targetKingdomsAllies = Main.kingdoms.getStringList("Kingdoms." + targetsCurrentKingdom + ".Current_Allies");
		Player[] playerList = Bukkit.getOnlinePlayers();
		boolean targetIsOnline = false;
		if(GeneralHelper.isTargetOnline(args[1].toLowerCase())){
			targetIsOnline = true;
			for(Player p : playerList){
				playerNames.add(p.getName().toLowerCase());
			}
			String p = Bukkit.getPlayer(args[1].toLowerCase()).getName();
			Util.srm(s,ChatColor.DARK_GRAY + "_________ ~..,,|| " + ChatColor.DARK_AQUA +  "Player Info for: " + p + ChatColor.DARK_GRAY + " ||,,..~ _________");
		}
		if(targetIsOnline == false){
			Util.srm(s,ChatColor.DARK_GRAY + "_________ ~..,,|| " + ChatColor.DARK_AQUA +  "Player Info for: " + args[1] + ChatColor.DARK_GRAY + " ||,,..~ _________");
		}
		if(targetsCurrentKingdomProper == null){
			Util.srm(s,ChatColor.DARK_GRAY + "Current Kingdom: " + ChatColor.GRAY + "None");
		}else{
			Util.srm(s,ChatColor.DARK_GRAY + "Current Kingdom: " + ChatColor.GRAY + Util.getRealKingdomName(targetsCurrentKingdom));
		}
		Util.srm(s,ChatColor.DARK_GRAY + "Kingdom Contributions: " + ChatColor.GRAY + targetPlayersContributions);
		if(targetsRank == null){
			Util.srm(s,ChatColor.DARK_GRAY + "Current Rank: " + ChatColor.GRAY + "None");
		}else{
			Util.srm(s,ChatColor.DARK_GRAY + "Current Rank: " + ChatColor.GRAY + targetsRank);
		}
		if(GeneralHelper.isInKingdom(s) == true){
			if (!targetsCurrentKingdom.matches("None")){
			    if(targetKingdomsWarEnemy.matches(sendersKingdom)){
				    Util.srm(s,ChatColor.DARK_GRAY + "Your standing with this player: " + ChatColor.DARK_RED + ChatColor.ITALIC + ChatColor.BOLD + "AT WAR");
			    }else if(targetKingdomsAllies.contains(sendersKingdom)){
				    Util.srm(s,ChatColor.DARK_GRAY + "Your standing with this player: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + ChatColor.BOLD + "ALLIES");
			    }else{
				    Util.srm(s,ChatColor.DARK_GRAY + "Your standing with this player: " + ChatColor.WHITE + ChatColor.ITALIC + ChatColor.BOLD + "NEUTRAL");
			    }
			}
		}
		Util.srm(s,ChatColor.DARK_GRAY + "Member Since: " + ChatColor.GRAY + targetsMemberSince);
		Util.srm(s,ChatColor.DARK_GRAY + "______________________________________________");
		Util.srm(s,"");
		return true;
	}
	
	public static boolean leaveKingdom(String[] args, CommandSender s){
		String sendersPlayerName = s.getName().toLowerCase();
		String sendersProperCasedName = s.getName();
		String sendersCurrKingdom = Main.players.getString("Players." + sendersPlayerName + ".Current_Kingdom");
		List<String> kingdomMembers = Main.kingdoms.getStringList("Kingdoms."+sendersCurrKingdom+".Members");
		int currRosterSize = Main.kingdoms.getInt("Kingdoms." + sendersCurrKingdom + ".Roster_Size");
		int updRosterSize = currRosterSize - 1;
		Util.gbc(sendersCurrKingdom, Util.getRealPlayerName(sendersPlayerName)+" has left the Kingdom.");
		Util.sm(s,"You left the Kingdom " + Util.getRealKingdomName(sendersCurrKingdom) + ".");
		kingdomMembers.remove(sendersProperCasedName);
		Main.kingdoms.set("Kingdoms."+sendersCurrKingdom+".Members", kingdomMembers);
		Main.players.set("Players." + sendersPlayerName + ".Kingdom_Leader", false);
		Main.players.set("Players." + sendersPlayerName + ".Current_Kingdom", "None");
		Main.players.set("Players." + sendersPlayerName + ".Current_Rank", 0);
		Main.players.set("Players." + sendersPlayerName + ".Kingdom_Contributions", 0);
		Main.players.set("Players." + sendersPlayerName + ".Member_Since", "N/A");
		Main.players.set("Players." + sendersPlayerName + ".Current_Invitation", "N/A");
		Main.kingdoms.set("Kingdoms." + sendersCurrKingdom + ".Roster_Size", updRosterSize);
		Main.savePlayersYaml();
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean getOwnPlayerInfo(CommandSender s){
		String targetsCurrentKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		String targetsCurrentKingdomProper = Main.kingdoms.getString("Kingdoms." + targetsCurrentKingdom.toLowerCase() + ".Chat_Prefix");
		int targetPlayersContributions = Main.players.getInt("Players." + s.getName().toLowerCase() + ".Kingdom_Contributions");
		int targetsRankInt = Main.players.getInt("Players." + s.getName().toLowerCase() + ".Current_Rank");
		String targetsRank = Main.kingdoms.getString("Kingdoms." + targetsCurrentKingdom.toLowerCase() + ".Ranks." + targetsRankInt);
		String targetsMemberSince = Main.players.getString("Players." + s.getName().toLowerCase() + ".Member_Since");
		Util.srm(s,ChatColor.DARK_GRAY + "_________ ~..,,|| " + ChatColor.DARK_AQUA +  "Player Info for: " + s.getName() + ChatColor.DARK_GRAY + " ||,,..~ _________");
		if(targetsCurrentKingdomProper == null){
			Util.srm(s,ChatColor.DARK_GRAY + "Current Kingdom: " + ChatColor.GRAY + "None");
		}else{
			Util.srm(s,ChatColor.DARK_GRAY + "Current Kingdom: " + ChatColor.GRAY + targetsCurrentKingdomProper);
		}
		Util.srm(s,ChatColor.DARK_GRAY + "Kingdom Contributions: " + ChatColor.GRAY + targetPlayersContributions);
		if(targetsRank == null){
			Util.srm(s,ChatColor.DARK_GRAY + "Current Rank: " + ChatColor.GRAY + "None");
		}else{
			Util.srm(s,ChatColor.DARK_GRAY + "Current Rank: " + ChatColor.GRAY + targetsRank);
		}
		Util.srm(s,ChatColor.DARK_GRAY + "Member Since: " + ChatColor.GRAY + targetsMemberSince);
		Util.srm(s,ChatColor.DARK_GRAY + "______________________________________________");
		Util.srm(s,"");
		return true;
	}

	public static boolean getKingdomInfo(String[] args, CommandSender s){
		String inputtedKingdom = args[1].toLowerCase();
		String sendersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		String level = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Level");
		String type = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Type");
		String creationDate = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Creation_Date");
		String memberCap = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Max_Members");
		String rosterSize = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Roster_Size");
		int currentXP = Main.kingdoms.getInt("Kingdoms." + inputtedKingdom + ".Total_XP");
		String targetKingdomsWarEnemy = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Current_War_Enemy");
		List<String> targetKingdomsAllies = Main.kingdoms.getStringList("Kingdoms." + inputtedKingdom + ".Current_Allies");
		Util.srm(s,ChatColor.DARK_GRAY + "__________ ~..,,|| " + ChatColor.DARK_AQUA +  "Kingdom Info for: " + Util.getRealKingdomName(inputtedKingdom) + ChatColor.DARK_GRAY + " ||,,..~ __________");
		Util.srm(s,ChatColor.DARK_GRAY + "Name: " + ChatColor.GRAY + Util.getRealKingdomName(inputtedKingdom));
		Util.srm(s,ChatColor.DARK_GRAY + "Level: " + ChatColor.GRAY + level);
		Util.srm(s,ChatColor.DARK_GRAY + "Total XP: " + ChatColor.GRAY + currentXP);
		Util.srm(s,ChatColor.DARK_GRAY + "Type: " + ChatColor.GRAY + type);
		Util.srm(s,ChatColor.DARK_GRAY + "Member Cap: " + ChatColor.GRAY + memberCap);
		Util.srm(s,ChatColor.DARK_GRAY + "Roster Size: " + ChatColor.GRAY + rosterSize);
		if(GeneralHelper.isInKingdom(s) == true){
			if(targetKingdomsWarEnemy.matches(sendersKingdom)){
				Util.srm(s,ChatColor.DARK_GRAY + "Your Kingdoms standing with this Kingdom: " + ChatColor.DARK_RED + ChatColor.ITALIC + ChatColor.BOLD + "AT WAR");
			}else if(targetKingdomsAllies.contains(sendersKingdom)){
				Util.srm(s,ChatColor.DARK_GRAY + "Your Kingdoms standing with this Kingdom: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + ChatColor.BOLD + "ALLIES");
			}else{
				Util.srm(s,ChatColor.DARK_GRAY + "Your Kingdoms standing with this Kingdom: " + ChatColor.WHITE + ChatColor.ITALIC + ChatColor.BOLD + "NEUTRAL");
			}
		}
		Util.srm(s,ChatColor.DARK_GRAY + "Creation Date: " + ChatColor.GRAY + creationDate);
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}

	public static boolean getOwnKingdomInfo(CommandSender s){
		String inputtedKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		String level = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Level");
		String type = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Type");
		String creationDate = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Creation_Date");
		String memberCap = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Max_Members");
		String rosterSize = Main.kingdoms.getString("Kingdoms." + inputtedKingdom + ".Roster_Size");
		int currentXP = Main.kingdoms.getInt("Kingdoms." + inputtedKingdom + ".Total_XP");
		Util.srm(s,ChatColor.DARK_GRAY + "__________ ~..,,|| " + ChatColor.DARK_AQUA +  "Kingdom Info for: " + Util.getRealKingdomName(inputtedKingdom) + ChatColor.DARK_GRAY + " ||,,..~ __________");
		Util.srm(s,ChatColor.DARK_GRAY + "Name: " + ChatColor.GRAY + Util.getRealKingdomName(inputtedKingdom));
		Util.srm(s,ChatColor.DARK_GRAY + "Kingdom Level: " + ChatColor.GRAY + level);
		Util.srm(s,ChatColor.DARK_GRAY + "Total XP: " + ChatColor.GRAY + currentXP);
		Util.srm(s,ChatColor.DARK_GRAY + "Type: " + ChatColor.GRAY + type);
		Util.srm(s,ChatColor.DARK_GRAY + "Creation Date: " + ChatColor.GRAY + creationDate);
		Util.srm(s,ChatColor.DARK_GRAY + "Member Cap: " + ChatColor.GRAY + memberCap);
		Util.srm(s,ChatColor.DARK_GRAY + "Roster Size: " + ChatColor.GRAY + rosterSize);
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}
	
	public static boolean getKingdomList(String[] args, CommandSender s){
		StringBuilder sb = new StringBuilder();
		kingdomList = Main.kingdoms.getConfigurationSection("Kingdoms.").getKeys(false);
		if(kingdomList.isEmpty()){
			Util.er(s, "No Kingdoms currently exist on this server, unable to display list.");
			return false;
		}
		Util.srm(s,ChatColor.DARK_GRAY + "______________ ~..,,|| " + ChatColor.DARK_AQUA +  "List of Kingdoms" + ChatColor.DARK_GRAY + " ||,,..~ ______________");
		for(String kingdomNameRepresentation : kingdomList){
			sb.append(Util.getRealKingdomName(kingdomNameRepresentation) + ChatColor.GRAY + "[" + String.valueOf(Main.kingdoms.getInt("Kingdoms." + kingdomNameRepresentation + ".Roster_Size")) + "]" + ChatColor.GRAY + ", ");
		}
		String sbAsString = sb.toString();
		sbAsString.trim();
		sbAsString = sbAsString.substring(0, sbAsString.length() -2);
		Util.srm(s,ChatColor.GRAY + sbAsString);
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		Util.srm(s, ChatColor.GRAY + "For information on a Kingdom, type \"" + ChatColor.GRAY + "/Kingdom info <Kingdom name>" + ChatColor.GRAY + "\".");
		return true;
	}
	
	public static boolean getKingdomRoster(String[] args, CommandSender s){
		StringBuilder sb = new StringBuilder();
		String inputtedKingdom = args[1].toLowerCase();
		List<String> kingdomRoster = Main.kingdoms.getStringList("Kingdoms."+inputtedKingdom+".Members");
		listOfPlayers = Main.players.getConfigurationSection("Players.").getKeys(false);
		for(String playerNameRepresentation : listOfPlayers){
			String currentKingdomLowercase = Main.players.getString("Players." + playerNameRepresentation + ".Current_Kingdom");
			if(currentKingdomLowercase.matches(args[1])){
				sb.append(playerNameRepresentation + ", ");
			}
		}
		Util.srm(s,ChatColor.DARK_GRAY + "_____ ~..,,|| " + ChatColor.DARK_AQUA +  "List of members in " + Util.getRealKingdomName(inputtedKingdom) + ChatColor.DARK_GRAY + " ||,,..~ _____");
		Util.srm(s, ChatColor.GRAY+kingdomRoster.toString());
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}
	
	public static boolean getKingdomRanks(String[] args, CommandSender s){
		
		String inputtedKingdom = args[1].toLowerCase();
		StringBuilder sb = new StringBuilder();
		rankList = Main.kingdoms.getConfigurationSection("Kingdoms."+inputtedKingdom.toLowerCase()+".Ranks").getKeys(false);
		Util.srm(s,ChatColor.DARK_GRAY + "__________ ~..,,|| " + ChatColor.DARK_AQUA +  "List of ranks in " + Util.getRealKingdomName(inputtedKingdom) + ChatColor.DARK_GRAY + " ||,,..~ __________");
		for(String rankNumberRepresentation : rankList){
			sb.append(Main.kingdoms.getString("Kingdoms."+inputtedKingdom+".Ranks."+rankNumberRepresentation) + ", ");
		}
		String sbAsString = sb.toString();
		sbAsString.trim();
		sbAsString = sbAsString.substring(0, sbAsString.length() -2);
		Util.srm(s,ChatColor.GRAY + sbAsString);
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}
	
	public static boolean getOwnKingdomRanks(String[] args, CommandSender s){
		String currentKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		String currentKingdomTag = Main.kingdoms.getString("Kingdoms." + currentKingdom.toLowerCase() + ".Chat_Prefix");
		StringBuilder sb = new StringBuilder();
		rankList = Main.kingdoms.getConfigurationSection("Kingdoms."+currentKingdom+".Ranks").getKeys(false);
		Util.srm(s,ChatColor.DARK_GRAY + "_______ ~..,,|| " + ChatColor.DARK_AQUA +  "List of ranks in " + currentKingdomTag + ChatColor.DARK_GRAY + " ||,,..~ _______");
		for(String rankNumberRepresentation : rankList){
			sb.append(Main.kingdoms.getString("Kingdoms."+currentKingdom+".Ranks."+rankNumberRepresentation) + ", ");
		}
		String sbAsString = sb.toString();
		sbAsString.trim();
		sbAsString = sbAsString.substring(0, sbAsString.length() -2);
		Util.srm(s,ChatColor.GRAY + sbAsString);
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}

	public static boolean getGeneralCommandListPage1(String[] args, CommandSender s){
		Util.srm(s,ChatColor.DARK_GRAY + "___ ~..,,|| " + ChatColor.DARK_AQUA +  "Kingdoms General Help" + ChatColor.DARK_GRAY + " ["+ChatColor.DARK_AQUA+"PAGE 1 OF 3"+ChatColor.DARK_GRAY+"]" + ChatColor.DARK_GRAY + " ||,,..~ ___");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom create <Kingdom name>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom disband");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom invite <player name>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom dismissinvite");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom cancelinvite <player name>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom checkinvite");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom join <Kingdom name>");
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}

	public static boolean getGeneralCommandListPage2(String[] args, CommandSender s){
		Util.srm(s,ChatColor.DARK_GRAY + "___ ~..,,|| " + ChatColor.DARK_AQUA +  "Kingdoms General Help" + ChatColor.DARK_GRAY + " ["+ChatColor.DARK_AQUA+"PAGE 2 OF 3"+ChatColor.DARK_GRAY+"]" + ChatColor.DARK_GRAY + " ||,,..~ ___");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom leave");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom promote <player name>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom demote <player name>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom kick <player name>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom setnewleader <player name>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom info || /Kingdom info <Kingdom name>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom list");
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}

	public static boolean getGeneralCommandListPage3(String[] args, CommandSender s){
		Util.srm(s,ChatColor.DARK_GRAY + "___ ~..,,|| " + ChatColor.DARK_AQUA +  "Kingdoms General Help" + ChatColor.DARK_GRAY + " ["+ChatColor.DARK_AQUA+"PAGE 3 OF 3"+ChatColor.DARK_GRAY+"]" + ChatColor.DARK_GRAY + " ||,,..~ ___");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom viewroster <Kingdom name>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom ranklist <Kingdom name> || /Kingdom ranklist");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom playerinfo <player name>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom settype <type>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom setmaxmembers <#>");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom renamerank <rank #> <new name>");
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}

	public static boolean getOwnKingdomRoster(CommandSender s) {
		StringBuilder sb = new StringBuilder();
		String inputtedKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		List<String> kingdomRoster = Main.kingdoms.getStringList("Kingdoms."+inputtedKingdom+".Members");
		String sendersCurrentKingdom = Main.players.getString("Players."+s.getName().toLowerCase()+".Current_Kingdom");
		String sendersCurrentKingdomPrefix = Main.kingdoms.getString("Kingdoms."+sendersCurrentKingdom.toLowerCase()+".Chat_Prefix");
		listOfPlayers = Main.players.getConfigurationSection("Players.").getKeys(false);
		for(String playerNameRepresentation : listOfPlayers){
			String currentKingdom = Main.players.getString("Players." + playerNameRepresentation + ".Current_Kingdom");
			if(currentKingdom.matches(sendersCurrentKingdom)){
				sb.append(playerNameRepresentation + ", ");
			}
		}
		Util.srm(s,ChatColor.DARK_GRAY + "_______ ~..,,|| " + ChatColor.DARK_AQUA +  "List of members in " + sendersCurrentKingdomPrefix + ChatColor.DARK_GRAY + " ||,,..~ _______");
		Util.srm(s, ChatColor.GRAY+kingdomRoster.toString());
		//sbAsString = sb.toString();
		//sbAsString.trim();
		//sbAsString = sbAsString.substring(0, sbAsString.length() -2);
		//Util.srm(s,ChatColor.GRAY + sbAsString);
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}
}
