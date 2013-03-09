package kingdomsteam.kingdomsmain.Chat;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chat {
	static Player[] ListOfPlayers;
	
	public static boolean chatFocusToggle(CommandSender s, String[] args){
		String name = s.getName().toLowerCase();
		boolean focusStatus = Main.players.getBoolean("Players." + name + ".Kingdom_Chat_Focus");
		if(focusStatus == true){
			Main.players.set("Players." + name + ".Kingdom_Chat_Focus", false);
			Util.sm(s,"You are no longer focused on Kingdom chat.\nTo talk in Kingdom chat, type \"" + ChatColor.DARK_AQUA + "/gu focus" + ChatColor.GRAY + "\" again.");
		}
		if(focusStatus == false){
			
			Main.players.set("Players." + name + ".Kingdom_Chat_Focus", true);
			Util.sm(s,"You set your focus to Kingdom chat.\nTo talk in normal chat, type \"" + ChatColor.DARK_AQUA + "/gu focus" + ChatColor.GRAY + "\" again.");
		}
		Main.savePlayersYaml();
		return true;
	}

	public static boolean sendKingdomMessage(CommandSender s, String[] args) {
		String playersName = s.getName().toLowerCase();
		if(!Main.players.getString("Players." + playersName + ".Current_Kingdom").matches("None")){
			String talkersKingdom = Main.players.getString("Players." + playersName + ".Current_Kingdom");
			ListOfPlayers = Bukkit.getOnlinePlayers();
			String playersKingdomPrefix = Main.kingdoms.getString("Kingdoms." + talkersKingdom + ".Chat_Prefix");
			int playersCurrentRankNumber = Main.players.getInt("Players." + playersName + ".Current_Rank");
			String playersCurrentRankString = Main.kingdoms.getString("Kingdoms." + talkersKingdom + ".Ranks." + playersCurrentRankNumber);
			
			StringBuilder sb = new StringBuilder();
			
			for(String str : args){
				sb.append(str + " ");
			}
			String sbAsString = sb.toString();
			sbAsString.trim();
			for(Player p : ListOfPlayers){
				String name = p.getName().toLowerCase();
				if(Main.players.getString("Players." + name + ".Current_Kingdom").matches(talkersKingdom)){
					p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + playersKingdomPrefix + ChatColor.DARK_GRAY + "] " + "[" + ChatColor.BLUE + playersCurrentRankString + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + s.getName() + ": " + sbAsString);
					s.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + playersKingdomPrefix + ChatColor.DARK_GRAY + "] " + "[" + ChatColor.BLUE + playersCurrentRankString + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + s.getName() + ": " + sbAsString);
					Bukkit.getLogger().info(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + playersKingdomPrefix + ChatColor.DARK_GRAY + "] " + "[" + ChatColor.BLUE + playersCurrentRankString + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + s.getName() + ": " + sbAsString);
					return true;
				}
			}
			return true;
		}
		return true;
	}
}
