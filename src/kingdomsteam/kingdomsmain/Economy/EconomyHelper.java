package kingdomsteam.kingdomsmain.Economy;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class EconomyHelper implements Listener{

	public static void playerTransaction(Player p, Double currency){
		String playerName=p.getName();
		Main.economy.withdrawPlayer(playerName,currency);
		return;
	}
	public static boolean canPlayerAfford(Player p, Double amount){
		String playerName=p.getName();
		if(Main.economy.getBalance(playerName) >= amount){
			return true;
		}
		return false;
	}
	public static boolean isVaultEnabled(){
		if(Main.economy.isEnabled() == true){
			return true;
		}
		return false;
	}
	
	public static boolean completeTransaction(Player p, Double amount, String Fail){
		if (Main.economyConfig.getString("Economy.Currency_Enabled").matches("false")){
			return true;
		}
		else if (!EconomyHelper.canPlayerAfford(p, amount)){
	    	return false;
	    }
		else{
	    EconomyHelper.playerTransaction(p, amount);
	    return true;
		}
	}
	
	public static void getPriceList(CommandSender s){
		Util.srm(s,ChatColor.DARK_GRAY + "_________ ~..,,|| " + ChatColor.DARK_AQUA +  "Current Prices " + ChatColor.DARK_GRAY + " ||,,..~ _________");
		Util.srm(s, ChatColor.GRAY + " - Cost to claim new Kingdom land   : "+ChatColor.DARK_AQUA+Main.economyConfig.getString("Economy.Land_Claim_Cost"));
		Util.srm(s, ChatColor.GRAY + " - Cost to create a new Kingdom    : "+ChatColor.DARK_AQUA+Main.economyConfig.getString("Economy.Kingdom_Charter_Cost"));
		Util.srm(s, ChatColor.GRAY + " - Cost to declare a new war    : "+ChatColor.DARK_AQUA+Main.economyConfig.getString("Economy.Declare_War_Cost"));
	}
}