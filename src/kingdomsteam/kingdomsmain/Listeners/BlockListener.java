package kingdomsteam.kingdomsmain.Listeners;
import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener{
	
	@EventHandler
	public boolean onBlockBreak(BlockBreakEvent event){
		Player p = event.getPlayer();
		int playerX = p.getLocation().getChunk().getX();
		int playerZ = p.getLocation().getChunk().getZ();
		String playerWorld = p.getWorld().getName().toLowerCase();
		String chunkBuilder = Main.players.getString("Players."+p.getName().toLowerCase()+".Current_Kingdom");
		if (Main.landcontrol.getString("Chunks."+playerWorld+"_"+playerX+"_"+playerZ+".Owning_Kingdom") == null){
			return true;
		}
		if (!Main.landcontrol.getString("Chunks."+playerWorld+"_"+playerX+"_"+playerZ+".Owning_Kingdom").matches(chunkBuilder)){
			String owningKingdom = Main.landcontrol.getString("Chunks."+playerWorld+"_"+playerX+"_"+playerZ+".Owning_Kingdom");
			if (owningKingdom != null){
				Util.er(p,"This land is claimed by [" + Util.getRealKingdomName(owningKingdom.toLowerCase()) + ChatColor.RED + "], you are not allowed to destroy here.");
				event.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
	@EventHandler
	public boolean onBlockPlace(BlockPlaceEvent event){
		Player p = event.getPlayer();
		int playerX = p.getLocation().getChunk().getX();
		int playerZ = p.getLocation().getChunk().getZ();
		String playerWorld = p.getWorld().getName().toLowerCase();
		String chunkBuilder = Main.players.getString("Players."+p.getName().toLowerCase()+".Current_Kingdom");
		if (Main.landcontrol.getString("Chunks."+playerWorld+"_"+playerX+"_"+playerZ+".Owning_Kingdom") == null){
			return true;
		}
		if (!Main.landcontrol.getString("Chunks."+playerWorld+"_"+playerX+"_"+playerZ+".Owning_Kingdom").matches(chunkBuilder)){
			String owningKingdom = Main.landcontrol.getString("Chunks."+playerWorld+"_"+playerX+"_"+playerZ+".Owning_Kingdom");
			if (Main.landcontrol.getString("Chunks."+playerWorld+"_"+playerX+"_"+playerZ+".Owning_Kingdom") != null){
				Util.er(p,"This land is claimed by [" + Util.getRealKingdomName(owningKingdom.toLowerCase()) + ChatColor.RED + "], you are not allowed to build here.");
				event.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}