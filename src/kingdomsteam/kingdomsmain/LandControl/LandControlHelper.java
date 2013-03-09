package kingdomsteam.kingdomsmain.LandControl;


import java.util.List;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.PoliticsAndWar.PoliticsHelper;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LandControlHelper {
	
	public static final int lineWidth = 27;
	public static final int halfLineWidth = lineWidth / 2;
	
    public static boolean isChunkClaimed(Player p){

    	String chunkClaimer = p.getName();
    	String chunkWorld = p.getWorld().getName().toLowerCase();
    	int chunkX = p.getLocation().getChunk().getX();
    	int chunkZ = p.getLocation().getChunk().getZ();
    	String chunkClaimerKingdom = Main.players.getString("Players."+chunkClaimer.toLowerCase()+".Current_Kingdom");
    	
    	if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom") != null){
	    	if ((Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom").matches(chunkClaimerKingdom))){
	    		Util.er(p,"Your kingdom already owns this chunk of land.");
	    		return true;
	    	}
	    	if ((!Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom").matches(chunkClaimerKingdom))){
	    		Util.er(p,"Another kingdom has already claimed this chunk of land.");
	    		return true;
	    	}
	    }
    	return false;
    }

    public static boolean isClaimerAdjacent(Player p){
    	
    	String chunkClaimer = p.getName();
    	String chunkWorld = p.getWorld().getName().toLowerCase();
    	int chunkX = p.getLocation().getChunk().getX();
    	int chunkZ = p.getLocation().getChunk().getZ();
    	
    	
    	String chunkClaimerKingdom = Main.players.getString("Players."+chunkClaimer.toLowerCase()+".Current_Kingdom");
    	   	
    	if (Main.kingdoms.getString("Kingdoms."+chunkClaimerKingdom+".LandControlled.ChunksClaimed") == null){
    		return true;
    	}
    	if (Main.kingdoms.getInt("Kingdoms."+chunkClaimerKingdom+".LandControlled.ChunksClaimed") == 0){
    		return true;
    	}
    	if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom") == null){
    		
	        if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+(chunkX +1)+"_"+chunkZ+".Owning_Kingdom") != null){
	            if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+(chunkX +1)+"_"+chunkZ+".Owning_Kingdom").matches(chunkClaimerKingdom)){
	    	        //Util.er(p,"I'm reading that this plot is +1 X Chunk away from a chunk you own");
	        	    return true;
	            }
	        }
	        if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+(chunkX -1)+"_"+chunkZ+".Owning_Kingdom") != null){
	            if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+(chunkX -1)+"_"+chunkZ+".Owning_Kingdom").matches(chunkClaimerKingdom)){
	            	//Util.er(p,"I'm reading that this plot is -1 X Chunk away from a chunk you own");
	        	    return true;
	            }
	        }
	        if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+(chunkZ+1)+".Owning_Kingdom") != null){
	            if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+(chunkZ+1)+".Owning_Kingdom").matches(chunkClaimerKingdom)){
	            	//Util.er(p,"I'm reading that this plot is +1 Z Chunk away from a chunk you own");
	        	    return true;
	            }
	        }
	        if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+(chunkZ-1)+".Owning_Kingdom") != null){
	            if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+(chunkZ-1)+".Owning_Kingdom").matches(chunkClaimerKingdom)){
	            	//Util.er(p,"I'm reading that this plot is -1 Z Chunk away from a chunk you own");
	        	    return true;
	            }
	        }
		    else{
		    	Util.er(p,"You can only claim chunks that are adjacent to chunks you already own!");
		    }
	    }
    	return false;
    }	

    public static boolean isChunkClaimersKingdom(Player p){
    	
    	String chunkClaimer = p.getName();
    	String chunkWorld = p.getWorld().getName().toLowerCase();
    	int chunkX = p.getLocation().getChunk().getX();
    	int chunkZ = p.getLocation().getChunk().getZ();
    	String chunkClaimerKingdom = Main.players.getString("Players."+chunkClaimer.toLowerCase()+".Current_Kingdom");
    	
    	if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom") != null){
	    	if ((Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom").matches(chunkClaimerKingdom))){
	    		return true;
	    	}
	    	else{
	    		Util.er(p,"Cannot unclaim another Kingdom's land!");
	    		return false;
	    	}
	    }
    	return false;
    }

    public static boolean canPlayerUse(Player p){
    	String chunkClaimer = p.getName();
    	String chunkWorld = p.getWorld().getName().toLowerCase();
    	int chunkX = p.getLocation().getChunk().getX();
    	int chunkZ = p.getLocation().getChunk().getZ();
    	String chunkClaimerKingdom = Main.players.getString("Players."+chunkClaimer.toLowerCase()+".Current_Kingdom");
    	
    	if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom") != null){
	    	if ((Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom").matches(chunkClaimerKingdom))){
	    		return true;
	    	}
	    	else{;
	    		return false;
	    	}
	    }
    	return true;
    }
    
    public static boolean canPlayerOpen(Player p){
    	String chunkClaimer = p.getName();
    	String chunkWorld = p.getWorld().getName().toLowerCase();
    	int chunkX = p.getLocation().getChunk().getX();
    	int chunkZ = p.getLocation().getChunk().getZ();
    	String chunkClaimerKingdom = Main.players.getString("Players."+chunkClaimer.toLowerCase()+".Current_Kingdom");
    	
    	if (Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom") != null){
	    	if ((Main.landcontrol.getString("Chunks."+chunkWorld+"_"+chunkX+"_"+chunkZ+".Owning_Kingdom").matches(chunkClaimerKingdom))){
	    		return true;
	    	}
	    	else{;
	    		return false;
	    	}
	    }
    	return true;
    }

    public static String getPlayersChunk(Player p, String kingdomName){
    	
    	String world = p.getWorld().getName().toLowerCase();
    	int x = p.getLocation().getChunk().getX();
        int z = p.getLocation().getChunk().getZ();
        if (Main.landcontrol.getString("Chunks."+world+"_"+x+"_"+z+".Owning_Kingdom") != null){
        	kingdomName = Main.players.getString("Players."+p.getName().toLowerCase()+".Current_Kingdom");
        	return kingdomName;
        }
		return null;
    }
    
    public static void getMap(Player p){
    	
		int c = (p.getLocation().getChunk().getX() -7);
		int r = (p.getLocation().getChunk().getZ() -3);
		String w = (p.getWorld().getName().toLowerCase());
		String pName = p.getName().toLowerCase();
		String mapInfoPrefix = new String("");
		int ROWS = 7;
		int COLS = 15;		
		String kingdomName = Main.players.getString("Players."+pName+".Current_Kingdom");	
		Util.srm(p, "        "+ChatColor.GRAY+"____ ~..,,|| "+ChatColor.DARK_AQUA+"Map of Nearby Territory"+ChatColor.GRAY+" ||,,..~ ____");
		for (int i = 0; i < ROWS; i++) {
			StringBuilder sb = new StringBuilder();
			String sbAsString;
			for (int j = 0; j < COLS; j++) {
				
				if (Main.landcontrol.getString("Chunks."+w+"_"+c+"_"+r+".Owning_Kingdom") != null){
					String chunksKingdom = Main.landcontrol.getString("Chunks."+w+"_"+c+"_"+r+".Owning_Kingdom");
					List<String> alliances;
					alliances = Main.kingdoms.getStringList("Kingdoms."+chunksKingdom+".Current_Allies");
					if(kingdomName.matches(chunksKingdom)){
						if ((r ==(p.getLocation().getChunk().getZ())) && c ==(p.getLocation().getChunk().getX())){
							sb.append(ChatColor.DARK_GREEN+"p");
						}
						else{
							sb.append(ChatColor.DARK_GREEN+"+");
						}
					}
					if(!kingdomName.matches(chunksKingdom)){
						if(alliances.contains(kingdomName)){
							if ((r ==(p.getLocation().getChunk().getZ())) && c ==(p.getLocation().getChunk().getX())){
								sb.append(ChatColor.BLUE+"p");
							}
							else{
								sb.append(ChatColor.BLUE+"+");
							}
						}
						else if(PoliticsHelper.areKingdomsAtWar(kingdomName, chunksKingdom)==true){
							if ((r ==(p.getLocation().getChunk().getZ())) && c ==(p.getLocation().getChunk().getX())){
								sb.append(ChatColor.RED+"p");
							}
							else{
								sb.append(ChatColor.RED+"+");
							}
						}
						else{
							if ((r ==(p.getLocation().getChunk().getZ())) && c ==(p.getLocation().getChunk().getX())){
								sb.append(ChatColor.WHITE+"p");
							}
							else{
								sb.append(ChatColor.WHITE+"+");
							}
						}
					}
				}
				if (Main.landcontrol.getString("Chunks."+w+"_"+c+"_"+r+".Owning_Kingdom") == null){
					if ((r ==(p.getLocation().getChunk().getZ())) && c ==(p.getLocation().getChunk().getX())){
						sb.append(ChatColor.DARK_GRAY+"p");
					}
					else{
						sb.append(ChatColor.DARK_GRAY+"-");
					}
				}
				c += 1;
			}
			//sb.append("\n");
			sbAsString = sb.toString();
			mapInfoPrefix = mapInfoPrefix(i);
			Util.sprm(p.getName()," "+sbAsString+" "+mapInfoPrefix);
			c = (p.getLocation().getChunk().getX() -7);
			r += 1;
		}
    }
    
public static String mapInfoPrefix(int i){
    	
    	if (i == 0){
    		return ""+ChatColor.DARK_AQUA+"      "+ChatColor.GRAY+"["+ChatColor.DARK_AQUA+"Map Legend"+ChatColor.GRAY+"]     ";
    	}
    	if (i == 1){
    		return " "+ChatColor.DARK_GREEN+"+"+" = Your Kingdom";
    	}
		if (i == 2) {
			return " "+ChatColor.WHITE+"+"+" = Neutral Kingdom               "+ChatColor.DARK_AQUA+"N";
		}
		if (i == 3) {                                                                                 
			return " "+ChatColor.BLUE+"+"+" = Allied Kingdom                "+ChatColor.DARK_AQUA+"W+E";
		}
		if (i == 4) {
			return " "+ChatColor.RED+ "+"+" = Enemy Kingdom                 "+ChatColor.DARK_AQUA+"S";
		}
		if (i == 5) {
			return " "+ChatColor.DARK_GRAY+ "-"+" = Open Territory            ";
		} 
		if (i == 6) {
			return " "+ChatColor.GRAY+"p"+" = Player Location";
		}
		if (i == 7) {
			return "                            ";
		}
		return "                            ";
    }
}
