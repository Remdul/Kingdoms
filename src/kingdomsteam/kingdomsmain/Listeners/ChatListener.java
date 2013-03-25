package kingdomsteam.kingdomsmain.Listeners;


import java.util.List;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.General.GeneralHelper;
import kingdomsteam.kingdomsmain.Util.Util;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener{
	static Player[] ListOfPlayers;
	static List<Player> ListOfPlayersInTheSameKingdom;
	public static String chatters[];
	
	@EventHandler(priority=EventPriority.HIGH)
	public boolean onPlayerChat(AsyncPlayerChatEvent e) {
		CommandSender s = (CommandSender) e.getPlayer();
		Player player = e.getPlayer();
		String talker = e.getPlayer().getName();
		String playersName = e.getPlayer().getName().toLowerCase();
		
		if (GeneralHelper.motdChatMessage.containsKey(player) && GeneralHelper.motdChatMessage.get(player) == true){
			String talkersKingdom = Main.players.getString("Players." + playersName + ".Current_Kingdom");
			String motd = e.getMessage();
			Main.kingdoms.set("Kingdoms."+talkersKingdom+".Message_of_the_Day", motd);
			GeneralHelper.motdChatMessage.put(player, false);
			e.setCancelled(true);
			Util.sm(player, "MOTD has been set!");
			Main.saveKingdomsYaml();
			//DAFUQ?
		}
		
		if(Main.players.getBoolean("Players." + playersName + ".Kingdom_Chat_Focus") == true){
			if(!Main.players.getString("Players." + playersName + ".Current_Kingdom").matches("None")){
				String talkersKingdom = Main.players.getString("Players." + playersName + ".Current_Kingdom");
				ListOfPlayers = Bukkit.getOnlinePlayers();
				int playersCurrentRankNumber = Main.players.getInt("Players." + playersName + ".Current_Rank");
				String playersCurrentRankString = Main.kingdoms.getString("Kingdoms." + talkersKingdom + ".Ranks." + playersCurrentRankNumber);
				for(Player p : ListOfPlayers){
					String name = p.getName().toLowerCase();
					if(Main.players.getString("Players." + name + ".Current_Kingdom").matches(talkersKingdom) || p.hasPermission("kingdoms.admin.chatspy") || p.hasPermission("kingdoms.admin.*")){
						p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + Util.getRealKingdomName(talkersKingdom) + " Kingdom" + ChatColor.DARK_GRAY + "] " + "[" + ChatColor.BLUE + playersCurrentRankString + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + talker + ": " + e.getMessage());
						e.setCancelled(true);
					}
				}
				return true;
			}
		}
		
		if(GeneralHelper.isInKingdom(s)){
			if(Main.chatConfig.getBoolean("Chat.Display_Kingdom_Tags_In_Chat") == true){
				String playersCurrentKingdom = Main.players.getString("Players." + playersName + ".Current_Kingdom");
				String playersKingdomPrefix = Main.kingdoms.getString("Kingdoms." + playersCurrentKingdom + ".Chat_Prefix");
				String newFormatKingdom = Main.chatConfig.getString("Chat.Chat_Prefix");
				String newString = newFormatKingdom.replace("X", playersKingdomPrefix);
				String oldFormat = e.getFormat();
				String newFormat = newString + oldFormat;
				newFormat = newFormat.replace('&', '§');
				if(Main.kingdoms.getInt("Kingdoms." + playersCurrentKingdom + ".Roster_Size") >= Main.mainConfig.getInt("General.Kingdom_Charter_Size")){
					e.setFormat(newFormat);
				}
				return true;
			}
			return false;
		}
		
		if(!GeneralHelper.isInKingdom(s)){
			if(Main.chatConfig.getBoolean("Chat.Display_Kingdom_Tags_In_Chat") == true){
				if(Main.chatConfig.getBoolean("Chat.Display_Kingdomless_Peoples_Prefix") == true){
					String newFormatKingdom = Main.chatConfig.getString("Chat.Chat_Prefix");
					String kindomLessPrefix = Main.chatConfig.getString("Chat.Kingdomless_Person_Prefix");
					String newString = newFormatKingdom.replace("X", kindomLessPrefix);
					String oldFormat = e.getFormat();
					String newFormat = newString + oldFormat;
					newFormat = newFormat.replace('&', '§');
					e.setFormat(newFormat);
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
}