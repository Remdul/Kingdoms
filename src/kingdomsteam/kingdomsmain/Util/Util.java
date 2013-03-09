package kingdomsteam.kingdomsmain.Util;


import java.util.List;

import kingdomsteam.kingdomsmain.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {
	public static void DEBUG(String s){
		Bukkit.broadcastMessage(ChatColor.GRAY + "[KINGDOMS DEBUG] " + ChatColor.DARK_GRAY + s);
	}
	public static void sam(CommandSender s, String m) {
		s.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "Kingdoms Admin" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + m);
	}
	public static void srm(CommandSender s, String m){
		s.sendMessage(" " + m);
	}
	public static void bc(String s){
		Bukkit.broadcastMessage(ChatColor.DARK_GRAY+"["+ChatColor.DARK_AQUA+"Kingdoms"+ChatColor.DARK_GRAY+"]"+ChatColor.GRAY+ s);
	}
	public static void rbc(String s){
		Bukkit.broadcastMessage(s);
	}
	public static void sm(CommandSender s, String msg){
		s.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.DARK_AQUA+"Kingdoms"+ChatColor.DARK_GRAY+"]"+ChatColor.GRAY+ " " + msg);
	}
	public static void pm(String player, String msg){
		Player p = Bukkit.getPlayer(player);
		p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.DARK_AQUA+"Kingdoms"+ChatColor.DARK_GRAY+"]"+ChatColor.GRAY+ " " + msg);
	}
	public static void er(CommandSender s, String msg){
		s.sendMessage(ChatColor.DARK_RED + "[Kingdoms Error]" + ChatColor.RED + " " + msg);
	}
	public static void noPermsError(CommandSender s){
		s.sendMessage(ChatColor.DARK_RED + "[Kingdoms Error]" + ChatColor.RED + " You don't have permission to use this kingdoms command.");
	}
	public static void gbc(String targetKingdom, String msg){
		Player[] ListOfPlayers = Bukkit.getOnlinePlayers();
		for(Player p : ListOfPlayers){
			String name = p.getName().toLowerCase();
			if(Main.players.getString("Players." + name + ".Current_Kingdom").matches(targetKingdom) || p.hasPermission("kingdoms.admin.chatspy") || p.hasPermission("kingdoms.admin.*")){
				p.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + Util.getRealKingdomName(targetKingdom) + ChatColor.GRAY + "] " + msg);
			}
		}
	}
	public static void sprm(String player, String sb) {
		Player p = Bukkit.getPlayer(player);
		p.sendRawMessage(sb);
	}
	public static String getRealPlayerName(String lowerCasePlayerName){
		String realPlayerName = Main.players.getString("Players." + lowerCasePlayerName + ".Actual_Name");
		return realPlayerName;
	}
	public static String getRealKingdomName(String lowerCaseKingdomName){
		String realKingdomName = Main.kingdoms.getString("Kingdoms." + lowerCaseKingdomName + ".Chat_Prefix");
		realKingdomName = realKingdomName.replace('&', '§');
		return realKingdomName;
	}
	public static void generateMainConfig(){
		//Kingdom Generic Configuration
		List<String> bannedKingdomNames = Main.mainConfig.getStringList("Banned_Kingdom_Names");
		Main.mainConfig.options().header("-- MAIN CONFIGURATION EXPLANATION --\nKingdom_Charter_Size --> Minimum # of people in a kingdom before it becomes a \"real\" kingdom.\nMinimum\\Maximum_Characters_For_Kingdom\\Rank_Names --> Settings for limiting the size of peoples kingdom/rank names.\nFEATURES --> Global settings for enabling // disabling entire sections of the plugin. CURRENTLY NOT WORKING, will work by Thursday FEB 28 guaranteed.\nBanned_Kingdom_Names --> A list of names that aren't allowed for kingdom names.\nKingdom_Creation_Defaults --> All of the default settings for when a kingdom is first created.");
		Main.mainConfig.set("General.Kingdom_Charter_Size", 1);
		Main.mainConfig.set("General.Minimum_Characters_For_Kingdom_Names", 3);
		Main.mainConfig.set("General.Minimum_Characters_For_Rank_Names", 3);
		Main.mainConfig.set("General.Maximum_Characters_For_Kingdom_Names", 12);
		Main.mainConfig.set("General.Maximum_Characters_For_Rank_Names", 12);
		Main.mainConfig.set("General.Debug_Mode_(DANGEROUS)_Enabled", false);
		
		
		Main.mainConfig.set("Features.Politics_Enabled", true);
		Main.mainConfig.set("Features.War_Enabled", true);
		Main.mainConfig.set("Features.Land_Claiming_Enabled", true);
		Main.mainConfig.set("Features.Chat_Enabled", true);
		Main.mainConfig.set("Features.Level_Ups_And_Powers_Enabled", true);
		Main.mainConfig.set("Features.Economy_Integration_Enabled", true);
		
		bannedKingdomNames.clear();
		bannedKingdomNames.add("None");
		bannedKingdomNames.add("null");
		bannedKingdomNames.add("kingdomless");
		bannedKingdomNames.add("admin");
		bannedKingdomNames.add("staff");
		Main.mainConfig.set("Banned_Kingdom_Names", bannedKingdomNames);
		
		Main.mainConfig.set("Kingdom_Creation_Defaults.Charter_Size", 1);
		Main.mainConfig.set("Kingdom_Creation_Defaults.Level", 1);
		Main.mainConfig.set("Kingdom_Creation_Defaults.Type", "Undefined");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Max_Members", 25);
		Main.mainConfig.set("Kingdom_Creation_Defaults.New_Member_Starting_Rank", 1);
		Main.mainConfig.set("Kingdom_Creation_Defaults.Message_of_the_Day","No MOTD set! Your kingdom leader must ype: /gu setmotd to set one!");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Home_Location.X_Coordinate", 0);
		Main.mainConfig.set("Kingdom_Creation_Defaults.Home_Location.Y_Coordinate", 0);
		Main.mainConfig.set("Kingdom_Creation_Defaults.Home_Location.Z_Coordinate", 0);
		Main.mainConfig.set("Kingdom_Creation_Defaults.Home_Location.World", "World");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Ranks.1", "Cupcake");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Ranks.2", "Recruit");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Ranks.3", "Recruit+");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Ranks.4", "Member");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Ranks.5", "Member+");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Ranks.6", "Member++");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Ranks.7", "Veteran");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Ranks.8", "Veteran+");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Ranks.9", "Kingdom Officer");
		Main.mainConfig.set("Kingdom_Creation_Defaults.Ranks.10", "Kingdom Leader");
		
		
		Main.saveMainConfigYaml();
	}
	
    public static void generateEconomyConfig(){
    	double claimCost = 150.0;
    	double kingdomCreateCost = 500.0;
    	double warCost = 2500.0;
    	Main.economyConfig.options().header("-- ECONOMY CONFIGURATION EXPLANATION --\nCurrency_Enabled --> Whether or not the plugin uses economy. If this is set to false, all kingdom commands are FREE and do not cost money.\nLand_Claim_Cost --> The cost to claim land.\nKingdom_Charter_Cost --> The cost to create a kingdom.\nDeclare_War_Cost --> The cost to declare a war.");
    	Main.economyConfig.set("Economy.Currency_Enabled", "true");
    	Main.economyConfig.set("Economy.Land_Claim_Cost", claimCost);
    	Main.economyConfig.set("Economy.Kingdom_Charter_Cost", kingdomCreateCost);
    	Main.economyConfig.set("Economy.Declare_War_Cost", warCost);
		Main.saveEconomyConfigYaml();
    }
	
    public static void generateLevelUpsAndPowersConfig(){
    	Main.levelUpsAndPowersConfig.options().header("-- LEVEL UPS & POWERS CONFIGURATION EXPLANATION --\n-- CURRENTLY UNDER CONSTRUCTION, WILL ADD AN EXPLANATION FOR EACH SETTING IN A FUTURE UPDATE --\n(Honestly most of these are self-explanatory though.)");
		Main.levelUpsAndPowersConfig.set("Level_Unlocks.HomeTele.Enabled", true);
		Main.levelUpsAndPowersConfig.set("Level_Unlocks.HomeTele.Cooldown_(Seconds)", 0.5);
		Main.levelUpsAndPowersConfig.set("Level_Unlocks.HomeTele.Level_Unlocked", 2);
		Main.levelUpsAndPowersConfig.set("Level_Unlocks.SetHome.Enabled", true);
		Main.levelUpsAndPowersConfig.set("Level_Unlocks.SetHome.Cooldown_(Minutes)", 0.5);
		Main.levelUpsAndPowersConfig.set("Level_Unlocks.SetHome.Level_Unlocked", 2);
		Main.levelUpsAndPowersConfig.set("Level_Unlocks.Compass.Enabled", true);
		Main.levelUpsAndPowersConfig.set("Level_Unlocks.Compass.Cooldown_(Seconds)", 0.5);
		Main.levelUpsAndPowersConfig.set("Level_Unlocks.Compass.Level_Unlocked", 2);
		Main.levelUpsAndPowersConfig.set("Levels_XP_Required.2", 15);
		Main.levelUpsAndPowersConfig.set("Levels_XP_Required.3", 30);
		int xprequired = 60;
		for(int i = 4; i < 26; ++i){
			xprequired = (int) (xprequired * 1.25);
			String iAsString = String.valueOf(i); 
			Main.levelUpsAndPowersConfig.set("Levels_XP_Required." + iAsString, xprequired);
		}
		Main.levelUpsAndPowersConfig.set("Permission_Node_Unlocks.Enabled", "true");
		Main.levelUpsAndPowersConfig.set("Permission_Node_Unlocks.Level_1_Nodes", "this.is.a.node");
		Main.levelUpsAndPowersConfig.set("Permission_Node_Unlocks.Level_2_Nodes", "this.is.a.node.also");
		Main.levelUpsAndPowersConfig.set("Permission_Node_Unlocks.Level_3_Nodes", "this.is.a.node.as.well");
		Main.saveLevelUpsAndPowersConfigYaml();
    }
	
    public static void generateChatConfig(){
    	Main.chatConfig.options().header("-- CHAT CONFIGURATION EXPLANATION --\nDisplay_Kingdom_Tags_In_Chat --> whether or not kingdom tags show up in chat.\nChat_Prefix --> the format of how the kingdom tags appear in chat. \"X\" IS THE KINGDOM NAME, do not remove the X.\nKingdom_Private_Channels_Enabled --> CURRENTLY NOT WORKING.");
    	Main.chatConfig.set("Chat.Display_Kingdom_Tags_In_Chat", true);
		Main.chatConfig.set("Chat.Chat_Prefix", "&f[&7X&f] ");
		Main.chatConfig.set("Chat.Kingdom_Private_Channels_Enabled", true);
		Main.chatConfig.set("Chat.Default_Kingdom_Tag_Color", "&a");
		Main.saveChatConfigYaml();
    }

    public static void generatePoliticsAndWarConfig(){
    	Main.politicsAndWarConfig.options().header("-- POLITICS AND WAR CONFIGURATION EXPLANATION --\nWar_Duration_(Hours) --> CURRENTLY NOT WORKING.\nBypass_PvP_Safe_Zones_If_At_War --> true/false - say whether or not kingdoms who are at war can damage each other *EVEN IN PVP SAFE ZONES* if they are at war.");
    	Main.politicsAndWarConfig.set("War.War_Duration_(Hours)", 48);
    	Main.politicsAndWarConfig.set("War.Bypass_PvP_Safe_Zones_If_At_War", true);
    	Main.savePoliticsAndWarConfigYaml();
    }
	
    public static void generateLandControlConfig(){
    	Main.landControlConfig.options().header("-- LAND CONTROL CONFIGURATION EXPLANATION --\nProtect_Chests --> CURRENTLY NOT WORKING.\nProtect_Devices --> CURRENTLY NOT WORKING.\nRequire_Adjacency_For_Land_Claiming --> CURRENTLY NOT WORKING.\nMinimum_Distance_Between_Kingdoms_(Chunks) --> CURRENTLY NOT WORKING.\nMinimum_Distance_From_Spawn_(Chunks) --> CURRENTLY NOT WORKING.");
    	Main.landControlConfig.set("Land_Control.Protect_Chests", true);
    	Main.landControlConfig.set("Land_Control.Protect_Devices", true);
    	Main.landControlConfig.set("Land_Control.Require_Adjacency_For_Land_Claiming", true);
    	Main.landControlConfig.set("Land_Control.Minimum_Distance_Between_Kingdoms_(Chunks)", 10);
    	Main.landControlConfig.set("Land_Control.Minimum_Distance_From_Spawn_(Chunks)", 20);
    	Main.saveLandControlConfigYaml();
    }
}


