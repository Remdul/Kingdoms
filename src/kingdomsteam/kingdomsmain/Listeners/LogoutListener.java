package kingdomsteam.kingdomsmain.Listeners;

import kingdomsteam.kingdomsmain.Main;
//import latros.z.kingdoms.Functions.Util.Util;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LogoutListener implements Listener{
	
	@EventHandler
	public boolean onPlayerQuitEvent(PlayerQuitEvent e){
		String playerName = e.getPlayer().getName().toLowerCase();
		Long loginTimeMillis = Main.players.getLong("Players." + playerName + ".ContribData_IGNOREME");
		Long logoutTimeMillis = System.currentTimeMillis();
		long totalMinutesLoggedIn = (((logoutTimeMillis - loginTimeMillis) / 1000) / 60);
		String currentKingdom = Main.players.getString("Players." + playerName + ".Current_Kingdom");
		
		int currentContributions = Main.players.getInt("Players." + playerName + ".Kingdom_Contributions");
		int updatedContributions = (int) (currentContributions + totalMinutesLoggedIn);
		
		int currentKingdomTotalXP = Main.kingdoms.getInt("Kingdoms." + currentKingdom + ".Total_XP");
		int updCurrentKingdomTotalXP = (int) (currentKingdomTotalXP + totalMinutesLoggedIn);
		
		if(totalMinutesLoggedIn >= 1 && (!currentKingdom.matches("None") || currentKingdom == null)){
			Main.players.set("Players." + playerName + ".Kingdom_Contributions", updatedContributions);
			Main.kingdoms.set("Kingdoms." + currentKingdom + ".Total_XP", updCurrentKingdomTotalXP);
			Main.saveKingdomsYaml();
			Main.savePlayersYaml();
			return true;
		}else if(totalMinutesLoggedIn < 1){
			return false;
		}
		Main.saveKingdomsYaml();
		Main.savePlayersYaml();
		return true;
	}
}
