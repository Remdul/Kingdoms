package kingdomsteam.kingdomsmain.PoliticsAndWar;


import java.util.ArrayList;
import java.util.List;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Economy.EconomyHelper;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Politics {
	
	public static boolean offerAlliance(CommandSender s, String sendersKingdom, String targetKingdom){
		List<String> targetsCurrentAllianceRequestsReceived = new ArrayList<String>();
		List<String> targetsCurrentAllies = new ArrayList<String>();
		
		if(PoliticsHelper.areKingdomsAtWar(sendersKingdom, targetKingdom)){
			Util.er(s, "Your kingdoms are at war, you cannot attempt to form an alliance!");
			return false;
		}
		
		targetsCurrentAllianceRequestsReceived = Main.kingdoms.getStringList("Kingdoms." + targetKingdom + ".Alliance_Requests_Received");
		targetsCurrentAllies = Main.kingdoms.getStringList("Kingdoms." + targetKingdom + ".Current_Allies");
		if(targetsCurrentAllies.contains(sendersKingdom)){
			Util.er(s, "You dudes are already allied, dummy.");
			return false;
		}
		if(targetsCurrentAllianceRequestsReceived.contains(sendersKingdom)){
			Util.er(s, "You've already sent an alliance request to that kingdom! Talk to their kingdom leader.");
			return false;
		}
		targetsCurrentAllianceRequestsReceived.add(sendersKingdom);
		Main.kingdoms.set("Kingdoms." + targetKingdom + ".Alliance_Requests_Received", targetsCurrentAllianceRequestsReceived);
		Util.gbc(targetKingdom, "Your kingdom has received an alliance request from the kingdom [" + Util.getRealKingdomName(sendersKingdom.toLowerCase()) + ChatColor.GRAY + "]. To accept the alliance, your kingdom leader must type \"" + ChatColor.DARK_AQUA + "/kingdom acceptalliance " + Util.getRealKingdomName(sendersKingdom.toLowerCase()) + ChatColor.GRAY + "\".");
		Util.gbc(sendersKingdom, "Your kingdom has sent an alliance request to the kingdom [" + Util.getRealKingdomName(targetKingdom) + ChatColor.GRAY + "]. If they accept the request, an alliance announcement will be made.");
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean acceptAlliance(CommandSender s, String sendersKingdom, String targetKingdom){
		List<String> sendersKingdomsAllianceRequestsReceived = new ArrayList<String>();
		List<String> targetKingdomsAllianceRequestsReceived = new ArrayList<String>();
		List<String> sendersKingdomsCurrentAllies = new ArrayList<String>();
		List<String> targetKingdomsCurrentAllies = new ArrayList<String>();
		
		sendersKingdomsAllianceRequestsReceived = Main.kingdoms.getStringList("Kingdoms." + sendersKingdom + ".Alliance_Requests_Received");
		targetKingdomsAllianceRequestsReceived = Main.kingdoms.getStringList("Kingdoms." + targetKingdom + ".Alliance_Requests_Received");
		sendersKingdomsCurrentAllies = Main.kingdoms.getStringList("Kingdoms." + sendersKingdom + ".Current_Allies");
		targetKingdomsCurrentAllies = Main.kingdoms.getStringList("Kingdoms." + sendersKingdom + ".Current_Allies");
		
		if(PoliticsHelper.areKingdomsAtWar(sendersKingdom, targetKingdom)){
			Util.er(s, "Your kingdoms are at war, you cannot form an alliance!");
			return false;
		}
		if(sendersKingdomsCurrentAllies.contains(targetKingdom)){
			Util.er(s, "You dudes are already allied, dummy.");
			return false;
		}
		if(!sendersKingdomsAllianceRequestsReceived.contains(targetKingdom)){
			Util.er(s, "That kingdom hasn't requested that you be allied.");
			return false;
		}
		
		sendersKingdomsAllianceRequestsReceived.remove(targetKingdom);
		targetKingdomsAllianceRequestsReceived.remove(sendersKingdom);
		sendersKingdomsCurrentAllies.add(targetKingdom);
		targetKingdomsCurrentAllies.add(sendersKingdom);
		
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Alliance_Requests_Received", sendersKingdomsAllianceRequestsReceived);
		Main.kingdoms.set("Kingdoms." + targetKingdom + ".Alliance_Requests_Received", targetKingdomsAllianceRequestsReceived);
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Current_Allies", sendersKingdomsCurrentAllies);
		Main.kingdoms.set("Kingdoms." + targetKingdom + ".Current_Allies", targetKingdomsCurrentAllies);
		Util.bc(" [" + Util.getRealKingdomName(sendersKingdom.toLowerCase()) + ChatColor.GRAY + "] and [" + Util.getRealKingdomName(targetKingdom) + ChatColor.GRAY + "] are now " + ChatColor.DARK_GREEN + ChatColor.ITALIC + ChatColor.BOLD + "ALLIES" + ChatColor.RESET + ChatColor.GRAY + ".");
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean cancelAlliance(CommandSender s, String sendersKingdom, String targetKingdomToDeally){
		List<String> sendersKingdomsCurrentAllies = new ArrayList<String>();
		List<String> targetKingdomsCurrentAllies = new ArrayList<String>();
		
		sendersKingdomsCurrentAllies = Main.kingdoms.getStringList("Kingdoms." + sendersKingdom + ".Current_Allies");
		targetKingdomsCurrentAllies = Main.kingdoms.getStringList("Kingdoms." + targetKingdomToDeally + ".Current_Allies");
		
		if(sendersKingdomsCurrentAllies.contains(targetKingdomToDeally.toLowerCase()) && targetKingdomsCurrentAllies.contains(sendersKingdom.toLowerCase())){
			Util.bc(ChatColor.GRAY + " [" + Util.getRealKingdomName(sendersKingdom.toLowerCase()) + ChatColor.GRAY + "] and [" + Util.getRealKingdomName(targetKingdomToDeally.toLowerCase()) + ChatColor.GRAY + "] are now " + ChatColor.WHITE + ChatColor.ITALIC + ChatColor.BOLD + "NEUTRAL" + ChatColor.RESET + ChatColor.GRAY + ".");
			sendersKingdomsCurrentAllies.remove(targetKingdomToDeally);
			targetKingdomsCurrentAllies.remove(sendersKingdom);
			Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Current_Allies", sendersKingdomsCurrentAllies);
			Main.kingdoms.set("Kingdoms." + targetKingdomToDeally + ".Current_Allies", targetKingdomsCurrentAllies);
			Main.saveKingdomsYaml();
			return true;
		}
		Util.er(s, "You aren't allied with that kingdom, cannot de-ally.");
		return false;
	}
	
	public static boolean declareWar(CommandSender s, String sendersKingdom, String targetKingdom){
		Player p = (Player) s;
    	String Fail = "";
    	double warCost = Main.economyConfig.getInt("Economy.Declare_War_Cost");
    	if(Main.economyConfig.getString("Economy.Currency_Enabled").matches("true")){
    		String currency = Main.economy.currencyNameSingular();
    		Fail = "You do not have enough, you require: "+ChatColor.DARK_AQUA+warCost+" "+currency;
    	}
    	else {
    		Fail = "You do not have enough, you require: "+ChatColor.DARK_AQUA+warCost;
    	}
    	if (EconomyHelper.completeTransaction(p, warCost, Fail)==false){
    		Util.er(s, "You can't afford to declare war on that Kingdom. War cost: " + String.valueOf(warCost));
    		return false;
    	}
		String sendersKingdomsCurrentEnemy = Main.kingdoms.getString("Kingdoms." + sendersKingdom + ".Current_War_Enemy");
		String targetKingdomsCurrentEnemy = Main.kingdoms.getString("Kingdoms." + targetKingdom + ".Current_War_Enemy");
		List<String> sendersKingdomsCurrentAllies = new ArrayList<String>();
		List<String> targetKingdomsCurrentAllies = new ArrayList<String>();
		sendersKingdomsCurrentAllies = Main.kingdoms.getStringList("Kingdoms." + sendersKingdom + ".Current_Allies");
		targetKingdomsCurrentAllies = Main.kingdoms.getStringList("Kingdoms." + targetKingdom + ".Current_Allies");
		if((sendersKingdomsCurrentAllies != null && targetKingdomsCurrentAllies != null) && (sendersKingdomsCurrentAllies.contains(targetKingdom) || targetKingdomsCurrentAllies.contains(sendersKingdom))){
			Util.er(s, "Your kingdom is currently allied with [" + Util.getRealKingdomName(targetKingdom.toLowerCase()) + ChatColor.RED + "], you can't declare war on them until you aren't allies.");
			return false;
		}
		if((sendersKingdomsCurrentEnemy != null && targetKingdomsCurrentEnemy != null) && (sendersKingdomsCurrentEnemy.matches(targetKingdom) && targetKingdomsCurrentEnemy.matches(sendersKingdom))){
			Util.er(s, "Your kingdom is already at war with [" + Util.getRealKingdomName(targetKingdom.toLowerCase()) + ChatColor.RED + "].");
			return false;
		}
		Util.DEBUG("Checking kingdom war targets...");
		Util.DEBUG("Target kingdoms current war enemy: " + targetKingdomsCurrentEnemy);
		Util.DEBUG("Senders kingdoms current war enemy: " + sendersKingdomsCurrentEnemy);
		Util.DEBUG("If both of those ^ are \"None\", I SHOULD let the war happen. Otherwise, no war!");
		if(!(sendersKingdomsCurrentEnemy.equalsIgnoreCase("none")) || !(targetKingdomsCurrentEnemy.equalsIgnoreCase("none"))){
			Util.er(s, "That kingdom is already at war with another kingdom. Wait until their current war has ended.");
			return false;
		}
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Current_War_Enemy", targetKingdom);
		Main.kingdoms.set("Kingdoms." + targetKingdom + ".Current_War_Enemy", sendersKingdom);
		Util.rbc(ChatColor.DARK_GRAY + " --------------------- !!!! WAR !!!! --------------------- ");
		Util.rbc(ChatColor.GRAY + "                                The kingdoms:\n                      [" + Util.getRealKingdomName(sendersKingdom.toLowerCase()) + ChatColor.GRAY + "] and [" + Util.getRealKingdomName(targetKingdom.toLowerCase()) + ChatColor.GRAY + "]\n                            Are now " + ChatColor.DARK_RED + ChatColor.ITALIC + ChatColor.BOLD + "AT WAR!");
		Util.rbc(ChatColor.DARK_GRAY + " -------------------------------------------------- ");
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean offerTruce(CommandSender s, String sendersKingdom, String targetKingdom){
		
		if(PoliticsHelper.areKingdomsAtWar(sendersKingdom, targetKingdom) == false){
			Util.er(s, "Your kingdom isn't at war with that kingdom, so you cannot offer them a truce!");
			return false;
		}
		String targetsCurrentTruceRequest = Main.kingdoms.getString("Kingdoms." + targetKingdom + ".Truce_Request");
		if(targetsCurrentTruceRequest != null && targetsCurrentTruceRequest.matches(sendersKingdom)){
			Util.er(s, "You've already sent a truce request to that kingdom! Talk to their kingdom leader.");
			return false;
		}
		Main.kingdoms.set("Kingdoms." + targetKingdom + ".Truce_Request", sendersKingdom);
		Util.gbc(targetKingdom, "Your kingdom has received a truce request from the kingdom [" + Util.getRealKingdomName(sendersKingdom.toLowerCase()) + ChatColor.GRAY + "]. To accept the truce, your kingdom leader must type \"" + ChatColor.DARK_AQUA + "/kingdom accepttruce " + Util.getRealKingdomName(sendersKingdom.toLowerCase()) + ChatColor.GRAY + "\".");
		Util.gbc(sendersKingdom, "Your kingdom has sent a truce request to the kingdom [" + Util.getRealKingdomName(targetKingdom.toLowerCase()) + ChatColor.GRAY + "]. If they accept the request, an announcement of the war ending will be made.");
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean acceptTruce(CommandSender s, String sendersKingdom, String targetKingdom){
		if(PoliticsHelper.areKingdomsAtWar(sendersKingdom, targetKingdom) == false){
			Util.er(s, "Your kingdom isn't at war with that kingdom.");
			return false;
		}
		String sendersCurrentTruceRequest = Main.kingdoms.getString("Kingdoms." + sendersKingdom + ".Truce_Request");
		if(!sendersCurrentTruceRequest.matches(targetKingdom)){
			Util.er(s, "That kingdom hasn't offered you a truce.");
			return false;
		}
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Truce_Request", "None");
		Main.kingdoms.set("Kingdoms." + targetKingdom + ".Truce_Request", "None");
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Current_War_Enemy", "None");
		Main.kingdoms.set("Kingdoms." + targetKingdom + ".Current_War_Enemy", "None");
		Util.bc(" A truce has been established between [" + Util.getRealKingdomName(sendersKingdom.toLowerCase()) + ChatColor.GRAY + "] and [" + Util.getRealKingdomName(targetKingdom.toLowerCase()) + ChatColor.GRAY + "]. the war is now over!");
		Util.gbc(sendersKingdom, "Your kingdoms war with [" + Util.getRealKingdomName(targetKingdom.toLowerCase()) + ChatColor.GRAY + "] has come to an end.");
		Util.gbc(targetKingdom, "Your kingdoms war with [" + Util.getRealKingdomName(sendersKingdom.toLowerCase()) + ChatColor.GRAY + "] has come to an end.");
		Main.saveKingdomsYaml();
		return true;
	}
}
