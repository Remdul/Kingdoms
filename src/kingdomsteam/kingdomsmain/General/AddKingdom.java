package kingdomsteam.kingdomsmain.General;


import java.util.Date;
import java.util.List;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Var;
import kingdomsteam.kingdomsmain.Economy.EconomyHelper;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddKingdom {
	static List<String> kingdomMembers;
	static Date now;
	
	public static boolean create(String[] args, CommandSender s){
		
		//Variable initialization if the Kingdom creation passed all of the necessary checks
		String playerName = s.getName().toLowerCase();
		String playerTag = s.getName();
		now = new Date();
		Player p = (Player) s;
		String dateCreated = now.toString();
		String kingdomNameInternal = args[1].toLowerCase();
		String kingdomNameDisplayed = args[1];
		if(kingdomNameInternal.length() > Main.mainConfig.getInt("General.Maximum_Characters_For_Kingdom_Names") || kingdomNameInternal.length() < Main.mainConfig.getInt("General.Minimum_Characters_For_Kingdom_Names")){
			Util.er(s, "That Kingdom name doesn't fall between " + String.valueOf(Main.mainConfig.getInt("General.Minimum_Characters_For_Rank_Names")) + " and " + String.valueOf(Main.mainConfig.getInt("General.Maximum_Characters_For_Rank_Names")) + " characters, unable to create Kingdom.");
			return false;
		}
		kingdomMembers = Main.kingdoms.getStringList("Kingdoms."+ kingdomNameInternal +".Members");
		kingdomMembers.add(playerTag);
		String Fail = "";
		double charterCost = Main.economyConfig.getDouble("Economy.Kingdom_Charter_Cost");
		if (Main.economyConfig.getString("Economy.Currency_Enabled").matches("true")){
			String currency = Main.economy.currencyNameSingular();
			Fail = "You do not have enough, you require: "+ChatColor.DARK_AQUA+charterCost+" "+currency;
		}
		else{
			Fail = "You do not have enough, you require: "+ChatColor.DARK_AQUA+charterCost;
		}
    	if (EconomyHelper.completeTransaction(p, charterCost, Fail)==false){
    		return false;
    	}
		
		//Kingdom creation (assuming it got through all the above checks)!
		String defaultKingdomChatColor = Main.chatConfig.getString("Chat.Default_Kingdom_Tag_Color");
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Level", Var.KingdomDefaults_Level); //level
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Chat_Prefix", defaultKingdomChatColor + kingdomNameDisplayed); //chat prefix
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Type", Var.KingdomDefaults_Type); //type
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Creation_Date", dateCreated); //creation date
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Max_Members", Var.KingdomDefaults_MaxMembers); //max members
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Roster_Size", 1); //number of members
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Total_XP", 0);
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".New_Member_Starting_Rank", Var.KingdomDefaults_DefaultRank); //new member starting rank
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Current_War_Enemy", "None"); 
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Truce_Request", "None");
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Home_Location.X_Coordinate", Var.KingdomDefaults_HomeX);
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Home_Location.Y_Coordinate", Var.KingdomDefaults_HomeY);
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Home_Location.Z_Coordinate", Var.KingdomDefaults_HomeZ);
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Home_Location.World", Var.KingdomDefaults_HomeWorld);
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Ranks.10", Var.KingdomDefaults_Rank10); // default rank 10
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Ranks.9", Var.KingdomDefaults_Rank9); // default rank 9 
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Ranks.8", Var.KingdomDefaults_Rank8); // default rank 8
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Ranks.7", Var.KingdomDefaults_Rank7); // default rank 7
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Ranks.6", Var.KingdomDefaults_Rank6); // default rank 6
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Ranks.5", Var.KingdomDefaults_Rank5); // default rank 5
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Ranks.4", Var.KingdomDefaults_Rank4); // default rank 4
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Ranks.3", Var.KingdomDefaults_Rank3); // default rank 3
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Ranks.2", Var.KingdomDefaults_Rank2); // default rank 2
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Ranks.1", Var.KingdomDefaults_Rank1); // default rank 1
		Main.kingdoms.set("Kingdoms." + kingdomNameInternal + ".Members", kingdomMembers);
		Main.players.set("Players." + playerName + ".Kingdom_Leader", true);
		Main.players.set("Players." + playerName + ".Current_Kingdom", kingdomNameInternal);
		Main.players.set("Players." + playerName + ".Current_Rank", 10);
		Main.players.set("Players." + playerName + ".Kingdom_Contributions", 0);
		Main.players.set("Players." + playerName + ".Member_Since", dateCreated);
		Main.players.set("Players." + playerName + ".Current_Invitation", "N/A");
		if(Main.mainConfig.getInt("General.Kingdom_Charter_Size") > 1){
			Util.sm(s,"You created a Kingdom called " + Util.getRealKingdomName(kingdomNameInternal) + ChatColor.GRAY + "! Your Kingdom tag will NOT display in chat until you reach the minimum # of members to be an official Kingdom (" + Main.mainConfig.getInt("General.Kingdom_Charter_Size") + " members).");
		}
		if(Main.mainConfig.getInt("General.Kingdom_Charter_Size") <= 1){
			Util.bc(" The Kingdom [" + ChatColor.GREEN + Util.getRealKingdomName(kingdomNameInternal) + ChatColor.GRAY + "] has been created because it reached the minimum # of members to be an official Kingdom. Congratulations!");
		}
		Main.savePlayersYaml();
		Main.saveKingdomsYaml();
		return true;
	}
}
