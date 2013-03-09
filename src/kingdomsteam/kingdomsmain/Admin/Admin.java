package kingdomsteam.kingdomsmain.Admin;


import java.util.Date;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Util.Util;



import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Admin {
	
	static Date now;
	static String nowString;
	
	public static boolean banPlayer(String[] args, CommandSender s) {
		String targetPlayersName = args[2].toLowerCase();
		Main.players.set("Players." + targetPlayersName + ".Banned", true);
		Util.sam(s,"You banned " + targetPlayersName + " from using Kingdoms functionality.");
		Main.savePlayersYaml();
		return true;
	}		
	
	public static boolean unbanPlayer(String[] args, CommandSender s) {
		String targetPlayersName = args[2].toLowerCase();
		Main.players.set("Players." + targetPlayersName + ".Banned", false);
		Util.sam(s,"You unbanned " + targetPlayersName + " from using Kingdoms functionality.");
		Main.savePlayersYaml();
		return true;
	}
	
	public static boolean manuallyRemoveMember(String[] args, CommandSender s){
		String targetPlayersName = args[3].toLowerCase();
		String targetPlayersKingdom = args[2].toLowerCase();
		String targetPlayersActualKingdom = Main.players.getString("Players." + targetPlayersName + ".Current_Kingdom");
		int currRosterSize = Main.kingdoms.getInt("Kingdoms." + targetPlayersKingdom + ".Roster_Size");
		int updRosterSize = currRosterSize - 1;
		Main.players.set("Players." + targetPlayersName + ".Kingdom_Leader", false);
		Main.players.set("Players." + targetPlayersName + ".Current_Kingdom", "None");
		Main.players.set("Players." + targetPlayersName + ".Current_Rank", 0);
		Main.players.set("Players." + targetPlayersName + ".Kingdom_Contributions", 0);
		Main.players.set("Players." + targetPlayersName + ".Member_Since", "N/A");
		Main.players.set("Players." + targetPlayersName + ".Current_Invitation", "N/A");
		Main.kingdoms.set("Kingdoms." + targetPlayersKingdom + ".Roster_Size", updRosterSize);
		Util.sam(s,"You removed the user " + targetPlayersName + " from the Kingdom " + targetPlayersActualKingdom + ".");
		Main.savePlayersYaml();
		Main.saveKingdomsYaml();
		return true;
	}
	public static boolean manuallySetNewLeader(String[] args, CommandSender s){
		//TODO
		return true;
	}
	public static boolean manuallySetNewKingdomHome(String[] args, CommandSender s){
		//TODO
		return true;
	}
	public static boolean manuallyAddMember(String[] args, CommandSender s) {
		String targetPlayersName = args[3].toLowerCase();
		String targetPlayersKingdom = args[2].toLowerCase();
		int currRosterSize = Main.kingdoms.getInt("Kingdoms." + targetPlayersKingdom + ".Roster_Size");
		int updRosterSize = currRosterSize + 1;
		int targetPlayersKingdomNewMemberStartingRank = Main.kingdoms.getInt("Kingdoms." + targetPlayersKingdom + ".New_Member_Starting_Rank");
		now = new Date();
		nowString = now.toString();
		Main.players.set("Players." + targetPlayersName + ".Kingdom_Leader", false);
		Main.players.set("Players." + targetPlayersName + ".Current_Kingdom", targetPlayersKingdom);
		Main.players.set("Players." + targetPlayersName + ".Current_Rank", targetPlayersKingdomNewMemberStartingRank);
		Main.players.set("Players." + targetPlayersName + ".Kingdom_Contributions", 0);
		Main.players.set("Players." + targetPlayersName + ".Member_Since", nowString);
		Main.players.set("Players." + targetPlayersName + ".Current_Invitation", "N/A");
		Main.kingdoms.set("Kingdoms." + targetPlayersKingdom + ".Roster_Size", updRosterSize);
		Util.sam(s,"You added the user " + targetPlayersName + " to the Kingdom " + targetPlayersKingdom + ".");
		Main.savePlayersYaml();
		Main.saveKingdomsYaml();
		return true;
	}

	public static boolean manuallySetKingdomLevel(String[] args, CommandSender s) {
		String targetKingdom = args[2].toLowerCase();
		int targetKingdomsNewLevel = Integer.parseInt(args[3]);
		Main.kingdoms.set("Kingdoms." + targetKingdom + ".Level", targetKingdomsNewLevel);
		Util.sam(s,"You set the level of \"" + targetKingdom + "\" to " + targetKingdomsNewLevel + ".");
		Main.saveKingdomsYaml();
		return true;
	}

	public static boolean changeKingdomTag(String[] args, CommandSender s) {
		String inputtedPrefix = args[3];
		String newKingdomFormat = inputtedPrefix.replace('&', '§');
		Main.kingdoms.set("Kingdoms." + args[2].toLowerCase() + ".Chat_Prefix", newKingdomFormat);
		Util.sam(s,"Kingdom prefix changed to " + newKingdomFormat + "§f.");
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean getAdminCommandList(String[] args, CommandSender s){
		s.sendMessage(ChatColor.DARK_GRAY + "___________ ~..,,|| " + ChatColor.DARK_AQUA + "Kingdoms Admin Help" + ChatColor.DARK_GRAY + " ||,,..~ ___________");
		s.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom admin setlevel <Kingdom name> <# (1-25)>");
		s.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom admin removemember <Kingdom name> <player name>");
		s.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom admin addmember <Kingdom name> <player name>");
		s.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom admin banplayer <player name>");
		s.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom admin hometele <Kingdom name>");
		s.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/Kingdom admin sethome <Kingdomname>");
		Util.srm(s,ChatColor.GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}
}
