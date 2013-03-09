package kingdomsteam.kingdomsmain.General;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Util.Util;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Recruitment {

	static List<String> onlineListInStringFormat;
	static List<String> kingdomMembers;
	static Player[] onlineListInPlayerFormat;
	static Date now;
	
	public static boolean sendInvite(String[] args, CommandSender s){
		String inviteeName = args[1].toLowerCase();
		String invitersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		onlineListInStringFormat = new ArrayList<String>();
		onlineListInPlayerFormat = Bukkit.getOnlinePlayers();
		for(Player p : onlineListInPlayerFormat){
			String name = p.getName().toLowerCase();
			onlineListInStringFormat.add(name);
		}
		if(onlineListInStringFormat.contains(inviteeName)){
			Util.sm(s,"You invited the player " + Util.getRealPlayerName(inviteeName) + " to join your kingdom.");
			Main.players.set("Players." + inviteeName + ".Current_Invitation", invitersKingdom);
			for(Player p : onlineListInPlayerFormat){
				String name = p.getName().toLowerCase();
				if(name.matches(inviteeName)){
					p.sendMessage(ChatColor.DARK_GRAY + "["+ChatColor.DARK_AQUA+"Kingdoms"+ChatColor.DARK_GRAY+"]" + ChatColor.GRAY + "You were invited to join the kingdom " + Util.getRealKingdomName(invitersKingdom) + ChatColor.GRAY + ".");
					p.sendMessage(ChatColor.DARK_GRAY + "["+ChatColor.DARK_AQUA+"Kingdoms"+ChatColor.DARK_GRAY+"]" + ChatColor.GRAY + "Type \"" + ChatColor.DARK_AQUA + "/kingdom join " + Util.getRealKingdomName(invitersKingdom) + ChatColor.GRAY + "\" to join.");
				}
			}
			Main.savePlayersYaml();
			return true;
		}
		Util.er(s,"No player of that name was found online currently. You can only invite a player who is online.");
		return true;
	}

	public static boolean joinKingdom(String[] args, CommandSender s){

		String sendersPlayerName = s.getName().toLowerCase();
		String sendersPlayerTag = s.getName();
		String kingdomInvitedToInputted = args[1].toLowerCase();

		now = new Date();
		String date = now.toString();
		int currRosterSize = Main.kingdoms.getInt("Kingdoms." + kingdomInvitedToInputted + ".Roster_Size");
		int updRosterSize = currRosterSize + 1;
		int sendersTargetKingdomNewMemberStartingRank = Main.kingdoms.getInt("Kingdoms." + kingdomInvitedToInputted + ".New_Member_Starting_Rank");
		
		if(Main.kingdoms.contains("Kingdoms."+kingdomInvitedToInputted+".Members")){
			kingdomMembers = Main.kingdoms.getStringList("Kingdoms."+kingdomInvitedToInputted+".Members");
		}
		if(kingdomMembers.contains(sendersPlayerName.toLowerCase())){
			return false;
		}
		kingdomMembers.add(sendersPlayerTag);
		Main.players.set("Players." + sendersPlayerName + ".Current_Kingdom", kingdomInvitedToInputted);
		Main.players.set("Players." + sendersPlayerName + ".Member_Since", date);
		Main.players.set("Players." + sendersPlayerName + ".Kingdom_Leader", false);
		Main.players.set("Players." + sendersPlayerName + ".Current_Rank", sendersTargetKingdomNewMemberStartingRank);
		Main.players.set("Players." + sendersPlayerName + ".Current_Invitation", "N/A");
		Main.kingdoms.set("Kingdoms." + kingdomInvitedToInputted + ".Roster_Size", updRosterSize);
		Main.kingdoms.set("Kingdoms."+ kingdomInvitedToInputted +".Members", kingdomMembers);
		Main.savePlayersYaml();
		Main.saveKingdomsYaml();
		if(updRosterSize == Main.mainConfig.getInt("General.Kingdom_Charter_Size")){
			Util.bc(" The kingdom [" + Util.getRealKingdomName(kingdomInvitedToInputted) + ChatColor.GRAY + "] has been created because it reached the minimum # of members to be an official kingdom.");
		}
		Util.sm(s,"You joined the kingdom " + ChatColor.DARK_GRAY + Util.getRealKingdomName(kingdomInvitedToInputted) + ChatColor.GRAY + "!");
		Util.gbc(kingdomInvitedToInputted.toLowerCase(), Util.getRealPlayerName(sendersPlayerName)+" has joined the kingdom.");
		return true;
	}
	
	public static boolean getKingdomInvites(String[] args, CommandSender s){
		String sendersPlayerName = s.getName().toLowerCase();
		String sendersCurrentKingdomInvitation = Main.players.getString("Players." + sendersPlayerName + ".Current_Invitation");
		Util.sm(s,"You are currently invited to the kingdom: " + Util.getRealKingdomName(sendersCurrentKingdomInvitation) + ".");
		return true;
	}
	
	public static boolean dismissInvite(String[] args, CommandSender s){
		String sendersPlayerName = s.getName().toLowerCase();
		String sendersCurrentKingdomInvitation = Main.players.getString("Players." + sendersPlayerName + ".Current_Invitation");
		Main.players.set("Players." + sendersPlayerName + ".Current_Invitation", "N/A");
		Util.sm(s,"You cancelled your pending kingdom invite with the kingdom " + Util.getRealKingdomName(sendersCurrentKingdomInvitation) + ".");
		Main.savePlayersYaml();
		return true;
	}
}
