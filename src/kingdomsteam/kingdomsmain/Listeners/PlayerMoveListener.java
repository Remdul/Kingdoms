package kingdomsteam.kingdomsmain.Listeners;


import java.util.HashMap;
import java.util.Map;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.LandControl.LandControlHelper;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener{

	static Map<Player, Boolean> isPlayerInKingdomChunk = new HashMap<Player, Boolean>();
	static Map<Player, String> isPlayerInSameKingdomChunk = new HashMap<Player, String>();

	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerMove (PlayerMoveEvent e){
		
		Player p = e.getPlayer();
		String w = p.getLocation().getWorld().getName().toLowerCase();
		int c = p.getLocation().getChunk().getX();
		int r = p.getLocation().getChunk().getZ();
		String ownedKingdomName = Main.landcontrol.getString("Chunks."+w+"_"+c+"_"+r+".Owning_Kingdom");
		String playersKingdom = Main.players.getString("Players." + p.getName().toLowerCase() + ".Current_Kingdom");
		
		if (LandControlHelper.getPlayersChunk(p, ownedKingdomName)!=null) {
			if (isPlayerInKingdomChunk.containsKey(p)) {
				if (isPlayerInKingdomChunk.get(p) == false) {
					isPlayerInKingdomChunk.put(p, true);
					ownedKingdomName = Main.landcontrol.getString("Chunks."+w+"_"+c+"_"+r+".Owning_Kingdom");
					isPlayerInSameKingdomChunk.put(p, ownedKingdomName);
					if(playersKingdom.matches(ownedKingdomName)){
						Util.sm(p,"You have entered your Kingdom. You feel safe.");
					}
					if(!playersKingdom.matches(ownedKingdomName)){
						Util.srm(p,ChatColor.DARK_GRAY + "         ------------! Warning !------------");
						Util.srm(p,ChatColor.RED + "        You have entered ["+Util.getRealKingdomName(ownedKingdomName)+ChatColor.RED+"] territory.\n" + ChatColor.BOLD + " They have been alerted to your presence." + ChatColor.RESET + ChatColor.RED + "\n                              Beware.");
						Util.gbc(ownedKingdomName, ChatColor.RED + p.getName() + " has entered our guilds territory! Possible raid incoming, " + ChatColor.BOLD + "defend the Kingdom!");
					}
				}
				if (isPlayerInSameKingdomChunk.containsKey(p)){
					if (!isPlayerInSameKingdomChunk.get(p).matches(ownedKingdomName)){
						ownedKingdomName = Main.landcontrol.getString("Chunks."+w+"_"+c+"_"+r+".Owning_Kingdom");
						isPlayerInSameKingdomChunk.put(p, ownedKingdomName);
						if(playersKingdom.matches(ownedKingdomName)){
							Util.sm(p,"You have entered your Kingdom. You feel safe.");
						}
						if(!playersKingdom.matches(ownedKingdomName)){
							Util.srm(p,ChatColor.DARK_GRAY + "         ------------! Warning !------------");
							Util.srm(p,ChatColor.RED + "        You have entered ["+Util.getRealKingdomName(ownedKingdomName)+ChatColor.RED+"] territory.\n" + ChatColor.BOLD + " They have been alerted to your presence." + ChatColor.RESET + ChatColor.RED + "\n                              Beware.");
							Util.gbc(ownedKingdomName, ChatColor.RED + p.getName() + " has entered our guilds territory! Possible raid incoming, " + ChatColor.BOLD + "defend the Kingdom!");
						}
					}
				}
				
			}
			if (!isPlayerInKingdomChunk.containsKey(p)){
				isPlayerInKingdomChunk.put(p, true);
			}
		}
		
		if (LandControlHelper.getPlayersChunk(p, ownedKingdomName)==null) {
			if (isPlayerInKingdomChunk.containsKey(p)) {
				if (isPlayerInKingdomChunk.get(p) == true) {
					isPlayerInKingdomChunk.put(p, false);
					Util.sm(p, "You are now in the wilderness.");
				}
			}
			if (!isPlayerInKingdomChunk.containsKey(p)){
				isPlayerInKingdomChunk.put(p, false);
			}
		}
		
	}
}
