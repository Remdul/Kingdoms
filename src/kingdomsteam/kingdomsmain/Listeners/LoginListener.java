package kingdomsteam.kingdomsmain.Listeners;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.General.GeneralHelper;
import kingdomsteam.kingdomsmain.Powers.Powers;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener{
	
	@EventHandler
	public boolean onPlayerLogin(PlayerJoinEvent e){
		
		String playerName = e.getPlayer().getName().toLowerCase();
		String playersKingdom = Main.players.getString("Players." + playerName + ".Current_Kingdom");
		
		if(Main.players.getString("Players." + playerName + ".Current_Kingdom") == null){
			Main.players.set("Players." + playerName + ".Kingdom_Leader", false);
			Main.players.set("Players." + playerName + ".Actual_Name", e.getPlayer().getName());
			Main.players.set("Players." + playerName + ".Current_Kingdom", "None");
			Main.players.set("Players." + playerName + ".Current_Rank", 0);
			Main.players.set("Players." + playerName + ".Kingdom_Contributions", 0);
			Main.players.set("Players." + playerName + ".Member_Since", "N/A");
			Main.players.set("Players." + playerName + ".Current_Invitation", "N/A");
			Main.players.set("Players." + playerName + ".Banned", false);
			Main.players.set("Players." + playerName + ".Kingdom_Chat_Focus", false);
			Main.savePlayersYaml();
		}
		Main.players.set("Players." + playerName + ".ContribData_IGNOREME", System.currentTimeMillis());
		if(GeneralHelper.isFeatureEnabledLevelUpsAndPowers((CommandSender) e.getPlayer()) == true){
			if(playersKingdom != null && !playersKingdom.matches("None")){
				if(Main.kingdoms.getInt("Kingdoms." + playersKingdom + ".Roster_Size") >= Main.mainConfig.getInt("General.Kingdom_Charter_Size")){
					Powers.updateKingdomLevel(playerName, playersKingdom);
				}
			}
		}
		
		if (Main.players.getString("Players." + playerName + ".Current_Kingdom") != null && !Main.players.getString("Players." + playerName + ".Current_Kingdom").matches("None")){
			Player p = e.getPlayer();
			String motd = GeneralHelper.getKingdomMotd(p, playersKingdom);
			Util.srm(e.getPlayer(), ChatColor.DARK_GRAY + "_________ ~..,,|| " + ChatColor.DARK_AQUA +  "Kingdom Message of the Day:"+ChatColor.DARK_GRAY + " ||,,..~ _________");
			Util.srm(e.getPlayer(), "  "+motd);
		}
		
		Main.savePlayersYaml();
		return true;
	}
}
