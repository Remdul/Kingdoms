package kingdomsteam.kingdomsmain.LandControl;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Economy.EconomyHelper;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LandControl {
	
	public static boolean claim(Player p, String[] args){
		String chunkWorld = p.getWorld().getName().toLowerCase();
    	int chunkX = p.getLocation().getChunk().getX();
    	int chunkZ = p.getLocation().getChunk().getZ();
    	CommandSender s = (CommandSender) p;
    	String chunkClaimer = p.getName();
    	String Fail = "";
    	double claimCost = Main.economyConfig.getInt("Economy.Land_Claim_Cost");
    	if(Main.economyConfig.getString("Economy.Currency_Enabled").matches("true")){
    		String currency = Main.economy.currencyNameSingular();
    		Fail = "You do not have enough, you require: "+ChatColor.DARK_AQUA+claimCost+" "+currency;
    	}
    	else {
    		Fail = "You do not have enough, you require: "+ChatColor.DARK_AQUA+claimCost;
    	}
    	String chunkClaimerKingdom = Main.players.getString("Players."+chunkClaimer.toLowerCase()+".Current_Kingdom");
    	if (EconomyHelper.completeTransaction(p, claimCost, Fail)==false){
    		Util.er(s, "You can't afford to claim land. Claim cost: " + String.valueOf(claimCost));
    		return false;
    	}
		Main.landcontrol.set("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".X_Location", chunkX);
	    Main.landcontrol.set("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Z_Location", chunkZ);
	    Main.landcontrol.set("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".World", chunkWorld);
	    Main.landcontrol.set("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom", chunkClaimerKingdom);
	    Main.landcontrol.set("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Plot_Type", "public");
	    LandControlHelper.getMap(p);
	    Util.sm(p,"You claimed this land for the kingdom ["+ Util.getRealKingdomName(chunkClaimerKingdom) + ChatColor.GRAY + "]!");
	    Main.saveLandControlYaml();
		return true;
	}

    public static boolean unclaim(Player p, String[] args){
    	String chunkWorld = p.getWorld().getName().toLowerCase();
    	int chunkX = p.getLocation().getChunk().getX();
    	int chunkZ = p.getLocation().getChunk().getZ();
	    Main.landcontrol.set("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ, null);
	    setChunkTotal(p);
	    Main.saveLandControlYaml();
	    Util.sm(p,"You have unclaimed this land!");
    	return true;
    }

    public static boolean setChunkTotal(Player p){
    	int chunkTotal = 0;
    	String sendersPlayerName = p.getName().toLowerCase();
		String sendersCurrentKingdom = Main.players.getString("Players." + sendersPlayerName + ".Current_Kingdom");
		
		if (Main.landcontrol.getConfigurationSection("Chunks") != null){
			for (String chunkRepresentation : Main.landcontrol.getConfigurationSection("Chunks").getKeys(false)){
				String kingdomNameRepresentation = Main.landcontrol.getString("Chunks."+chunkRepresentation+".Owning_Kingdom");
				if(kingdomNameRepresentation.matches(sendersCurrentKingdom)){
					chunkTotal += 1;
				}
			}
		}
		Main.kingdoms.set("Kingdoms."+sendersCurrentKingdom+".LandControlled.ChunksClaimed", chunkTotal);
		Main.saveKingdomsYaml();
    	return true;
    }
}