package kingdomsteam.kingdomsmain.General;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GeneralHelper {
	
	public static Map<Player, Boolean> motdChatMessage = new HashMap<Player, Boolean>();

	public static boolean isInKingdom(CommandSender s) {
		String kingdomName = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		if (kingdomName == null || kingdomName.matches("None") || kingdomName.matches("N/A")) {
			return false;
		}
		return true;
	}
	public static boolean doesTargetHaveMatchingInvite(CommandSender s, String target) {
		String targetsName = target.toLowerCase();
		String sendersName = s.getName().toLowerCase();
		String targetsCurrentInvite = Main.players.getString("Players."+targetsName+".Current_Invitation");
		String sendersKingdom = Main.players.getString("Players."+sendersName+".Current_Kingdom");
		if(!targetsCurrentInvite.matches(sendersKingdom)){
			return false;
		}
		return true;
	}
	public static boolean isSendersKingdomAlreadyFull(CommandSender s) {
		String sendersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		int sendersKingdomsMaxMembers = Main.kingdoms.getInt("Kingdoms." + sendersKingdom + ".Max_Members");
		int sendersKingdomsRosterSize = Main.kingdoms.getInt("Kingdoms." + sendersKingdom + ".Roster_Size");
		if(sendersKingdomsRosterSize >= sendersKingdomsMaxMembers){
			return true;
		}
		return false;
	}
	public static boolean isKingdomNoLongerCharter(CommandSender s){
		String sendersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
		if(Main.kingdoms.getInt("Kingdoms." + sendersKingdom + ".Roster_Size") >= Main.mainConfig.getInt("General.Kingdom_Charter_Size")){
			return true;
		}
		return false;
	}
	public static boolean doesSendersInviteMatchTargetKingdom(CommandSender s, String targetKingdom) {
		String sendersName = s.getName().toLowerCase();
		String sendersCurrentInvite = Main.players.getString("Players."+sendersName+".Current_Invitation");
		if(!sendersCurrentInvite.matches(targetKingdom.toLowerCase())){
			return false;
		}
		return true;
	}
	public static boolean isInKingdom(String s) {
		String kingdomName = Main.players.getString("Players." + s.toLowerCase() + ".Current_Kingdom");
		if (kingdomName == null || kingdomName.matches("None") || kingdomName.matches("N/A")) {
			return false;
		}
		return true;
	}
	public static boolean isInKingdom(Player p) {
		String kingdomName = Main.players.getString("Players." + p.getName().toLowerCase() + ".Current_Kingdom");
		if (kingdomName == null || kingdomName.matches("None") || kingdomName.matches("N/A")) {
			return false;
		}
		return true;
	}
	public static boolean doArgsEqualTwo(String[] args){
		if(args.length != 2){
			return false;
		}
		return true;
	}
	public static boolean isString3to12(String args) {
		if(args.length() > 12 || args.length() < 3){
			return false;
		}
		return true;
	}
	public static boolean isOnlyNumbers(String args) {
		if(!args.matches("^[0-9]+$")){
			return false;
		}
		return true;
	}
	public static boolean doesKingdomExist(String kingdom){
		String tarKingdomType = Main.kingdoms.getString("Kingdoms." + kingdom.toLowerCase() + ".Type");
		if(tarKingdomType == null){
			return false;
		}
		return true;
		
	}
	public static boolean doArgsEqualOne(String[] args) {
		if(args.length != 1){
			return false;
		}
		return true;
	}
	public static boolean isSenderKingdomLeader(String player) {
		boolean isKingdomLeader = Main.players.getBoolean("Players." + player.toLowerCase() + ".Kingdom_Leader");
		if (isKingdomLeader == false) {
			return false;
		}
		return true;
	}
	public static boolean isOfficer(CommandSender s) {
		String playerName = s.getName().toLowerCase();
		int getCurrentRankNumber = Main.players.getInt("Players." + playerName + ".Current_Rank");
		if (getCurrentRankNumber != 9) {
			return false;
		}
		return true;
	}
	public static boolean isSenderKingdomOfficer(String sendersName) {
		int getCurrentRankNumber = Main.players.getInt("Players." + sendersName + ".Current_Rank");
		if (getCurrentRankNumber != 9) {
			return false;
		}
		return true;
	}
	public static boolean hasKingdomInvite(CommandSender s) {
		String playerName = s.getName().toLowerCase();
		String hasActiveInvite = Main.players.getString("Players." + playerName + ".Current_Invitation");
		if (hasActiveInvite.matches("N/A")) {
			return false;
		}
		return true;
	}
	public static boolean isTargetOnline(String targetsName) {
		Player[] playerList = Bukkit.getOnlinePlayers();
		String name;
		for(Player p : playerList){
			name = p.getName().toLowerCase();
			if(targetsName.toLowerCase().matches(name)){
				return true;
			}
		}
		return false;
	}
	public static boolean isTargetInSameKingdomAsSender(String sendersName, String targetsName) {
		String sendersKingdom = Main.players.getString("Players."+sendersName+".Current_Kingdom");
		String targetsKingdom = Main.players.getString("Players."+targetsName+".Current_Kingdom");
		if(!sendersKingdom.matches(targetsKingdom)){
			return false;
		}
		return true;
	}
	public static boolean isTargetSameRankAsSender(String sendersName, String targetsName) {
		int sendersRank = Main.players.getInt("Players."+sendersName+".Current_Rank");
		int targetsRank = Main.players.getInt("Players."+targetsName+".Current_Rank");
		if(sendersRank == targetsRank){
			return true;
		}
		return false;
	}
	public static boolean isTargetAlreadyRankOfficer(String targetsName) {
		int targetsRank = Main.players.getInt("Players."+targetsName+".Current_Rank");
		if(targetsRank == 9){
			return true;
		}
		return false;
	}
	public static boolean isTargetRank8AndSenderRank9(String sendersName, String targetsName) {
		int sendersRank = Main.players.getInt("Players."+sendersName+".Current_Rank");
		int targetsRank = Main.players.getInt("Players."+targetsName+".Current_Rank");
		if(sendersRank == 9 && targetsRank == 8){
			return true;
		}
		return false;
	}
	public static boolean isTargetHigherRankThanSender(String sendersName, String targetsName) {
		int sendersRank = Main.players.getInt("Players."+sendersName+".Current_Rank");
		int targetsRank = Main.players.getInt("Players."+targetsName+".Current_Rank");
		if(sendersRank < targetsRank){
			return true;
		}
		return false;
	}
	public static boolean isTargetAlreadyLowestRank(String targetsName) {
		int targetsRank = Main.players.getInt("Players."+targetsName+".Current_Rank");
		if(targetsRank == 1){
			return true;
		}
		return false;
	}
	public static boolean startsWithZero(String maxMembers) {
		if(maxMembers.startsWith("0")){
			return true;
		}
		return false;
	}
	public static boolean isWithinMaxMembersLimits(String maxMembers) {
		int maxMembersInt = Integer.parseInt(maxMembers);
		if(maxMembersInt < 3 || maxMembersInt > 250){
			return false;
		}
		return true;
	}
	public static boolean isOnlyLettersAndNumbers(String input) {
		if(!input.matches("^[0-9a-zA-Z]+$")){
			return false;
		}
		return true;
	}
	public static boolean isLowestRankPermitted(String input) {
		int lowestRank = Integer.parseInt(input);
		if(lowestRank < 1 || lowestRank > 8){
			return false;
		}
		return true;
	}
	public static boolean isTargetKingdomFullyCreated(String targetKingdom) {
		int targetKingdomsRosterSize = Main.kingdoms.getInt("Kingdoms."+targetKingdom+".Roster_Size");
		int minMembersForKingdomCreation = Main.mainConfig.getInt("General.Kingdom_Charter_Size");
		if(targetKingdomsRosterSize < minMembersForKingdomCreation){
			return false;
		}
		return true;
	}
	public static boolean doArgsEqualFour(String[] args) {
		if(args.length != 4){
			return false;
		}
		return true;
	}
	public static boolean isWithinLevelLimits(String level) {
		int levelInt = Integer.parseInt(level);
		if(levelInt > 25 || levelInt < 1){
			return false;
		}
		return true;
	}
	public static boolean doesTargetKingdomExist(String kingdom) {
		if(Main.kingdoms.getString("Kingdoms."+kingdom.toLowerCase()+".Chat_Prefix") == null){
			return false;
		}
		return true;
	}
	public static boolean doesTargetPlayerExist(String player) {
		if(Main.players.getString("Players."+player.toLowerCase()+".Current_Kingdom") == null){
			return false;
		}
		return true;
	}
	public static boolean isTargetKingdomLeader(String player) {
		if(Main.players.getBoolean("Players."+player.toLowerCase()+".Kingdom_Leader") == false){
			return false;
		}
		return true;
	}
	public static boolean doesTargetKingdomMatchTargetPlayersKingdom(String[] args) {
		String targetKingdom = args[2].toLowerCase();
		String targetPlayersActualKingdom = Main.players.getString("Kingdoms."+args[3].toLowerCase()+".Current_Kingdom");
		if(targetKingdom.matches(targetPlayersActualKingdom)){
			return true;
		}
		return false;
	}
	public static boolean doArgsEqualThree(String[] args) {
		if(args.length != 3){
			return false;
		}
		return true;
	}
	public static boolean isTargetSameAsSender(String sender, String target) {
		if(sender.matches(target)){
			return true;
		}
		return false;
	}
	public static boolean isKingdomWithinRosterLimits(CommandSender s) {
		Player p = (Player) s;
		String playersKingdom = Main.players.getString("Players."+p.getName().toLowerCase()+".Current_Kingdom");
		int listedMaxMembers =Main.kingdoms.getInt("Kingdoms."+playersKingdom+"Max_Members");
		int listedRosterSize =Main.kingdoms.getInt("Kingdoms."+playersKingdom+"Roster_Size");
		if(listedRosterSize > listedMaxMembers){
			return false;
		}
		return true;
	}
	public static boolean isTargetKingdomSameAsSendersKingdom(String sendersKingdom, String targetKingdom){
		if(targetKingdom.equalsIgnoreCase(sendersKingdom)){
			return true;
		}
		return false;
	}
	public static boolean isTargetInAKingdom(String targetPlayer){
		 String targetPlayersKingdom = Main.players.getString("Players."+targetPlayer.toLowerCase()+".Current_Kingdom");
		 if (!targetPlayersKingdom.matches("None")){
			 return true;
		 }
		return false;
	}
	public static boolean isDesiredKingdomNameOnTheBannedKingdomNamesList(CommandSender s, String[] args){
		List<String> bannedNames = Main.mainConfig.getStringList("Banned_Kingdom_Names");
		for(String str : bannedNames){
			if(str.equalsIgnoreCase(args[1])){
				return true;
			}
		}
		return false;
	}
	public static String getKingdomMotd(Player p, String kingdomName){
		
	    String motd;
	    if (Main.kingdoms.getString("Kingdoms."+kingdomName+".Message_of_the_Day") == null){
	    	motd = "No MOTD set! type: /gu setmotd <message> to set one!";
	    	return motd;
	    }
	    motd = Main.kingdoms.getString("Kingdoms."+kingdomName+".Message_of_the_Day");
		return motd;
	}
	public static boolean setKingdomMotd(String[] args, Player p){
		
		motdChatMessage.put(p, true);
		Util.sm(p, "You may now enter what you would like your Kingdom's MOTD to be:");
		return true;
	}
	public static boolean isFeatureEnabledPolitics(CommandSender s){
		if(Main.mainConfig.getBoolean("Features.Politics_Enabled") == true){
			return true;
		}
		return false;
	}
	public static boolean isFeatureEnabledWar(CommandSender s){
		if(Main.mainConfig.getBoolean("Features.War_Enabled") == true){
			return true;
		}
		return false;
	}
	public static boolean isFeatureEnabledChat(CommandSender s){
		if(Main.mainConfig.getBoolean("Features.Chat_Enabled") == true){
			return true;
		}
		return false;
	}
	public static boolean isFeatureEnabledLevelUpsAndPowers(CommandSender s){
		if(Main.mainConfig.getBoolean("Features.Level_Ups_And_Powers_Enabled") == true){
			return true;
		}
		return false;
	}
	public static boolean isFeatureEnabledEconomy(CommandSender s){
		if(Main.mainConfig.getBoolean("Features.Economy_Integration_Enabled") == true){
			return true;
		}
		return false;
	}
	public static boolean isFeatureEnabledLandClaiming(CommandSender s){
		if(Main.mainConfig.getBoolean("Features.Land_Claiming_Enabled") == true){
			return true;
		}
		return false;
	}
}
