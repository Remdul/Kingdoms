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

public class MemberManagement {
	
	static List<String> onlineListInStringFormat;
	static Player[] onlineListInPlayerFormat;
	static Date now;
	
	public static boolean promote(String[] args, CommandSender s){
		String sendersPlayerName = s.getName().toLowerCase();
		String sendersKingdom = Main.players.getString("Players." + sendersPlayerName + ".Current_Kingdom");
		String promoteesName = args[1].toLowerCase();
		onlineListInStringFormat = new ArrayList<String>();
		onlineListInPlayerFormat = Bukkit.getOnlinePlayers();
		int promoteesCurrentRank = Main.players.getInt("Players." + promoteesName + ".Current_Rank");
		for(Player p : onlineListInPlayerFormat){
			String name = p.getName().toLowerCase();
			onlineListInStringFormat.add(name);
		}
		String promoteesOldRank = Main.kingdoms.getString("Kingdoms." + sendersKingdom + ".Ranks." + promoteesCurrentRank);
		promoteesCurrentRank = promoteesCurrentRank + 1;
		String updatedPromoteesRank = Main.kingdoms.getString("Kingdoms." + sendersKingdom + ".Ranks." + promoteesCurrentRank);
		Main.players.set("Players." + promoteesName + ".Current_Rank", promoteesCurrentRank);
		for(Player p : onlineListInPlayerFormat){
			if(p.getName().toLowerCase().matches(promoteesName)){
				p.sendMessage(ChatColor.DARK_GRAY + "["+ChatColor.DARK_AQUA+"Kingdoms"+ChatColor.DARK_GRAY+"]" + ChatColor.GRAY + " You were "+ ChatColor.GREEN + "promoted" + ChatColor.GRAY + " to " + updatedPromoteesRank + " by " + s.getName() + ".");
			}
		}
		Util.sm(s,"You " + ChatColor.GREEN + "promoted " + ChatColor.GRAY + promoteesName + " to " + updatedPromoteesRank + " (from " + promoteesOldRank + ").");
		Main.savePlayersYaml();
		return true;
	}
	
	public static boolean demote(String[] args, CommandSender s){
		String sendersPlayerName = s.getName().toLowerCase();
		String sendersKingdom = Main.players.getString("Players." + sendersPlayerName + ".Current_Kingdom");
		String demoteesName = args[1].toLowerCase();
		onlineListInStringFormat = new ArrayList<String>();
		onlineListInPlayerFormat = Bukkit.getOnlinePlayers();
		int demoteesCurrentRank = Main.players.getInt("Players." + demoteesName + ".Current_Rank");
		String demoteesOldRank = Main.kingdoms.getString("Kingdoms." + sendersKingdom + ".Ranks." + demoteesCurrentRank);
		demoteesCurrentRank = demoteesCurrentRank - 1;
		String updatedDemoteesRank = Main.kingdoms.getString("Kingdoms." + sendersKingdom + ".Ranks." + demoteesCurrentRank);
		Main.players.set("Players." + demoteesName + ".Current_Rank", demoteesCurrentRank);
		for(Player p : onlineListInPlayerFormat){
			if(p.getName().toLowerCase().matches(demoteesName)){
				p.sendMessage(ChatColor.DARK_GRAY + "["+ChatColor.DARK_AQUA+"Kingdoms"+ChatColor.DARK_GRAY+"]" + ChatColor.GRAY + " You were " + ChatColor.RED + "demoted " + ChatColor.GRAY + "to " + updatedDemoteesRank + " by " + s.getName() + ".");
			}
		}
		Util.sm(s,"You " + ChatColor.RED + "demoted " + ChatColor.GRAY + demoteesName + " to " + updatedDemoteesRank + " (from " + demoteesOldRank + ").");
		Main.savePlayersYaml();
		return true;
	}

	public static boolean setNewLeader(String[] args, CommandSender s){
		String targetPlayersName = args[1].toLowerCase();
		String targetPlayersKingdom = Main.players.getString("Players." + targetPlayersName + ".Current_Kingdom");
		String sendersPlayerName = s.getName().toLowerCase();
		String sendersKingdom = Main.players.getString("Players." + sendersPlayerName + ".Current_Kingdom");
		int defaultKingdomRank = Main.kingdoms.getInt("Kingdoms." + sendersKingdom + ".New_Member_Starting_Rank");
		Main.players.set("Players." + sendersPlayerName + ".Kingdom_Leader", false);
		Main.players.set("Players." + targetPlayersName + ".Kingdom_Leader", true);
		Main.players.set("Players." + sendersPlayerName + ".Current_Rank", defaultKingdomRank);
		Main.players.set("Players." + targetPlayersName + ".Current_Rank", 10);
		Util.sm(s,"You declared the user " + targetPlayersName + " as the new kingdom leader of " + sendersKingdom + ".");
		Util.gbc(targetPlayersKingdom, sendersPlayerName+" has declared "+targetPlayersName+" the new kingdom leader.");
		Main.savePlayersYaml();
		return true;
	}
}
