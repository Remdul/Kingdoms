package kingdomsteam.kingdomsmain;

import kingdomsteam.kingdomsmain.Admin.Admin;
import kingdomsteam.kingdomsmain.Admin.AdminHelper;
import kingdomsteam.kingdomsmain.Chat.Chat;
import kingdomsteam.kingdomsmain.Economy.EconomyHelper;
import kingdomsteam.kingdomsmain.General.AddKingdom;
import kingdomsteam.kingdomsmain.General.General;
import kingdomsteam.kingdomsmain.General.GeneralHelper;
import kingdomsteam.kingdomsmain.General.KingdomManagement;
import kingdomsteam.kingdomsmain.General.MemberManagement;
import kingdomsteam.kingdomsmain.General.Recruitment;
import kingdomsteam.kingdomsmain.LandControl.LandControl;
import kingdomsteam.kingdomsmain.LandControl.LandControlHelper;
import kingdomsteam.kingdomsmain.PoliticsAndWar.Politics;
import kingdomsteam.kingdomsmain.Powers.Powers;
import kingdomsteam.kingdomsmain.Powers.PowersHelper;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("kingdom") && args.length < 5 && args.length != 0) {

			if (!(s instanceof Player)) {
				Util.er(s, "Only players can type Kingdoms commands. Log into the game and try again.");
				return false;
			}
			
			Player p = (Player) s;
			
			if (AdminHelper.isBannedFromKingdoms(p) == true) {
				Util.er(s, "You are currently banned from interacting with the Kingdoms system. Talk to your server admin if you believe this is in error.");
				return false;
			}
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			// [][][][][][][][][][][][][][][][][][][][][][][][]GENERAL POLITICS COMMANDS [][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			
			// [][][][][][][][][][][][][][][][][][][][][][][][POLITICS OFFERALLIANCE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("offeralliance")) {
				if (!s.hasPermission("kingdoms.politics.offeralliance") && !s.hasPermission("kingdoms.politics.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isFeatureEnabledPolitics(s) == false){
					Util.er(s, "Kingdom politics are currently disabled on this server.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				String sendersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
				if(sendersKingdom.toLowerCase().matches(args[1].toLowerCase()) == true){
					Util.er(s, "You can't do political interactions with your own kingdom.");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(p.getName().toLowerCase()) == false){
					Util.er(s, "You need to be the kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1].toLowerCase()) == false){
					Util.er(s, "No kingdom by that name exists.");
					return false;
				}
				Politics.offerAlliance(s, sendersKingdom, args[1].toLowerCase());
				return true;
			}
			// [][][][][][][][][][][][][][][][][][][][][][][][POLITICS ACCEPTALLIANCE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("acceptalliance")) {
				if (!s.hasPermission("kingdoms.politics.offeralliance") && !s.hasPermission("kingdoms.politics.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isFeatureEnabledPolitics(s) == false){
					Util.er(s, "Kingdom politics are currently disabled on this server.");
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				String sendersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
				if(sendersKingdom.toLowerCase().matches(args[1].toLowerCase()) == true){
					Util.er(s, "You can't do political interactions with your own kingdom.");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(p.getName().toLowerCase()) == false){
					Util.er(s, "You need to be the kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1].toLowerCase()) == false){
					Util.er(s, "No kingdom by that name exists.");
					return false;
				}
				Politics.acceptAlliance(s, sendersKingdom, args[1].toLowerCase());
				return true;
			}			
			// [][][][][][][][][][][][][][][][][][][][][][][][POLITICS OFFERTRUCE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("offertruce")) {
				String sendersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
				if (!s.hasPermission("kingdoms.politics.offeralliance") && !s.hasPermission("kingdoms.politics.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isFeatureEnabledWar(s) == false){
					Util.er(s, "Kingdom wars are currently disabled on this server.");
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(sendersKingdom.toLowerCase().matches(args[1].toLowerCase()) == true){
					Util.er(s, "You can't do political interactions with your own kingdom.");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(p.getName().toLowerCase()) == false){
					Util.er(s, "You need to be the kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1].toLowerCase()) == false){
					Util.er(s, "No kingdom by that name exists.");
					return false;
				}
				Politics.offerTruce(s, sendersKingdom.toLowerCase(), args[1].toLowerCase());
				return true;
			}
			// [][][][][][][][][][][][][][][][][][][][][][][][POLITICS ACCEPTTRUCE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("accepttruce")) {
				if (!s.hasPermission("kingdoms.politics.offeralliance") && !s.hasPermission("kingdoms.politics.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isFeatureEnabledWar(s) == false){
					Util.er(s, "Kingdom wars are currently disabled on this server.");
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				String sendersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
				if(sendersKingdom.toLowerCase().matches(args[1].toLowerCase()) == true){
					Util.er(s, "You can't do political interactions with your own kingdom.");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(p.getName().toLowerCase()) == false){
					Util.er(s, "You need to be the kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1].toLowerCase()) == false){
					Util.er(s, "No kingdom by that name exists.");
					return false;
				}
				Politics.acceptTruce(s, sendersKingdom, args[1].toLowerCase());
				return true;
			}
			// [][][][][][][][][][][][][][][][][][][][][][][][POLITICS CANCELALLIANCE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("cancelalliance")) {
				if (!s.hasPermission("kingdoms.politics.offeralliance") && !s.hasPermission("kingdoms.politics.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isFeatureEnabledPolitics(s) == false){
					Util.er(s, "Kingdom politics are currently disabled on this server.");
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				String sendersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
				if(sendersKingdom.toLowerCase().matches(args[1].toLowerCase()) == true){
					Util.er(s, "You can't do political interactions with your own kingdom.");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(p.getName().toLowerCase()) == false){
					Util.er(s, "You need to be the kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1].toLowerCase()) == false){
					Util.er(s, "No kingdom by that name exists.");
					return false;
				}
				Politics.cancelAlliance(s, sendersKingdom, args[1].toLowerCase());
				return true;
			}
			// [][][][][][][][][][][][][][][][][][][][][][][][POLITICS DECLAREWAR COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("declarewar")) {
				if (!s.hasPermission("kingdoms.politics.offeralliance") && !s.hasPermission("kingdoms.politics.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isFeatureEnabledWar(s) == false){
					Util.er(s, "Kingdom wars are currently disabled on this server.");
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				String sendersKingdom = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Kingdom");
				if(sendersKingdom.toLowerCase().matches(args[1].toLowerCase()) == true){
					Util.er(s, "You can't do political interactions with your own kingdom.");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(p.getName().toLowerCase()) == false){
					Util.er(s, "You need to be the kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1].toLowerCase()) == false){
					Util.er(s, "No kingdom by that name exists.");
					return false;
				}
				Politics.declareWar(s, sendersKingdom, args[1].toLowerCase());
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			// [][][][][][][][][][][][][][][][][][][][][][][][]GENERAL KINGDOM-INTERNAL COMMANDS [][][][][][][][][][][][][][][][][][][][][][][][][][]
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]

			// [][][][][][][][][][][][][][][][][][][][][][][][SHOW MAP COMMANDS][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("map")) {
				if (!s.hasPermission("kingdoms.general.map") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isFeatureEnabledLandClaiming(s) == false){
					Util.er(s, "Land Claiming currently disabled on this server, and therefore so is the territory map.");
					return false;
				}
				LandControlHelper.getMap(p);
				
				return true;
			}
			
			// [][][][][][][][][][][][][][][][][][][][][][][][PRICES COMMANDS][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("prices")) {
				if (!s.hasPermission("kingdoms.general.pricelist") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isFeatureEnabledEconomy(s) == false){
					Util.er(s, "Economy integration is currently disabled on this server, cannot display prices.");
					return false;
				}
				EconomyHelper.getPriceList(s);
				return true;
			}
			
			// [][][][][][][][][][][][][][][][][][][][][][][][SET MOTD COMMANDS][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("setmotd")) {
				if (!s.hasPermission("kingdoms.general.motd") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.isSenderKingdomOfficer(p.getName().toLowerCase()) == false && GeneralHelper.isSenderKingdomLeader(p.getName().toLowerCase()) == false){
					Util.er(s, "You need to be the kingdom leader, or officer to use that command.");
					return false;
				}
				GeneralHelper.setKingdomMotd(args, p);
				return true;
			}
			
			
			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM CREATE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("create")) {
				if (!s.hasPermission("kingdoms.general.create") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == true){
					Util.er(s, "You need to be kingdomless to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isDesiredKingdomNameOnTheBannedKingdomNamesList(s, args) == true){
					Util.er(s, "Your chosen kingdom name is a banned kingdom name on this server. Try a different name.");
					return false;
				}
				if(GeneralHelper.isOnlyLettersAndNumbers(args[1].toLowerCase()) == false){
					Util.er(s, "Your chosen kingdom name cannot contain anything other than numbers or letters.");
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1].toLowerCase()) == true){
					Util.er(s, "A kingdom by that name already exists.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				if ((AddKingdom.create(args, s)) == false){
					double claimCost =Main.economyConfig.getDouble("Economy.Kingdom_Charter_Cost");
					Util.er(s, "You cannot afford to create a kingdom. You need: "+ChatColor.DARK_AQUA+claimCost);
					return false;
				}
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM DISBAND COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("disband") || args[0].toLowerCase().matches("disb")) {
				if (!s.hasPermission("kingdoms.general.disband") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualOne(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false){
					Util.er(s, "You need to be the kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.doesKingdomExist(Main.players.getString("Players."+s.getName().toLowerCase()+".Current_Kingdom")) == false){
					Util.er(s, "No kingdom exists to be disbanded.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				KingdomManagement.disband(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM INVITE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("invite") || args[0].toLowerCase().matches("inv")) {
				if (!s.hasPermission("kingdoms.general.invite") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isInKingdom(args[1].toLowerCase())){
					Util.er(s, "That player is already in a kingdom.");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false && GeneralHelper.isOfficer(s) == false){
					Util.er(s, "You need to be the kingdom leader or officer to use that command.");
					return false;
				}
				if(GeneralHelper.isTargetOnline(args[1].toLowerCase()) == false){
					Util.er(s, "That player isn't online.");
					return false;
				}
				if(GeneralHelper.doesTargetHaveMatchingInvite(s, args[1].toLowerCase()) == true){
					Util.er(s, "That player is already invited to join your kingdom. Don't spam them!");
					return false;
				}
				if(GeneralHelper.isSendersKingdomAlreadyFull(s) == true){
					Util.er(s, "Your Kingdom's Roster is full, you can't invite any more players to your kingdom until you downsize!");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				Recruitment.sendInvite(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM JOIN COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("join")) {
				if (!s.hasPermission("kingdoms.general.join") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == true){
					Util.er(s, "You are already in a kingdom.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1].toLowerCase()) == false){
					Util.er(s, "No kingdom by that name exists.");
					return false;
				}
				if(GeneralHelper.doesSendersInviteMatchTargetKingdom(s, args[1].toLowerCase()) == false){
					Util.er(s, "You aren't currently invited to join that kingdom.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				Recruitment.joinKingdom(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM LEAVE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("leave") || args[0].toLowerCase().matches("quit")) {
				if (!s.hasPermission("kingdoms.general.leave") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualOne(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == true){
					Util.er(s, "You are the kingdom leader, you can't leave the kingdom. Disband it or declare a new leader first.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				
				General.leaveKingdom(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM PROMOTE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("promote") || args[0].toLowerCase().matches("prom")) {
				if (!s.hasPermission("kingdoms.general.promote") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false && GeneralHelper.isSenderKingdomOfficer(s.getName().toLowerCase()) == false){
					Util.er(s, "You need to be a kingdom leader or officer to use that command.");
					return false;
				}
				if(GeneralHelper.isTargetOnline(args[1].toLowerCase()) == false){
					Util.er(s, "Target player is not online.");
					return false;
				}
				if(GeneralHelper.isTargetSameAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "You can't promote yourself! Choose someone else.");
					return false;
				}
				if(GeneralHelper.isTargetInSameKingdomAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == false){
					Util.er(s, "You can't promote somebody who isn't in the same kingdom as you.");
					return false;
				}
				if(GeneralHelper.isTargetSameRankAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "You can't promote somebody who is at the same rank as you within the kingdom.");
					return false;
				}
				if(GeneralHelper.isTargetAlreadyRankOfficer(args[1].toLowerCase())){
					Util.er(s, "Target player is already Officer and therefore can't be promoted higher unless you declare them the new kingdom leader.");
					return false;
				}
				if(GeneralHelper.isTargetRank8AndSenderRank9(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "That player is 1 rank below you, you can't promote someone to your rank within a kingdom.");
					return false;
				}
				if(GeneralHelper.isTargetHigherRankThanSender(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "That player is higher rank than you, you can't promote them.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				MemberManagement.promote(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM DEMOTE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("demote") || args[0].toLowerCase().matches("dem")) {
				if (!s.hasPermission("kingdoms.general.demote") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false && GeneralHelper.isSenderKingdomOfficer(s.getName().toLowerCase()) == false){
					Util.er(s, "You need to be a kingdom leader or officer to use that command.");
					return false;
				}
				if(GeneralHelper.isTargetOnline(args[1].toLowerCase()) == false){
					Util.er(s, "Target player is not online.");
					return false;
				}
				if(GeneralHelper.isTargetSameAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "You can't demote yourself! Choose someone else.");
					return false;
				}
				if(GeneralHelper.isTargetInSameKingdomAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == false){
					Util.er(s, "You can't demote somebody who isn't in the same kingdom as you.");
					return false;
				}
				if(GeneralHelper.isTargetAlreadyLowestRank(args[1].toLowerCase()) == true){
					Util.er(s, "Target player is already at the lowest rank within the kingdom and therefore cannot be demoted any lower.");
					return false;
				}
				if(GeneralHelper.isTargetSameRankAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "Target player is the same rank as you and therefore cannot be demoted by you.");
					return false;
				}
				if(GeneralHelper.isTargetHigherRankThanSender(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "That player is higher rank than you, you can't demote them.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				MemberManagement.demote(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM KICK COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("kick")) {
				if (!s.hasPermission("kingdoms.general.kick") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false && GeneralHelper.isSenderKingdomOfficer(s.getName().toLowerCase()) == false){
					Util.er(s, "You need to be a kingdom leader or officer to use that command.");
					return false;
				}
				if(GeneralHelper.doesTargetPlayerExist(args[1]) == false){
					Util.er(s, "No player by that name exists.");
					return false;
				}
				if(GeneralHelper.isTargetInSameKingdomAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == false){
					Util.er(s, "You can't kick somebody who isn't in the same kingdom as you.");
					return false;
				}
				if(GeneralHelper.isTargetSameAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "You can't kick yourself!");
					return false;
				}
				if(GeneralHelper.isTargetSameRankAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "Target player is the same rank as you and therefore cannot be kicked by you.");
					return false;
				}
				if(GeneralHelper.isTargetHigherRankThanSender(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "That player is higher rank than you, you can't kick them.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				KingdomManagement.kick(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM SETNEWLEADER COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("snl") || args[0].toLowerCase().matches("setnewleader")) {
				if (!s.hasPermission("kingdoms.general.setnewleader") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false){
					Util.er(s, "You need to be a kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.isTargetSameAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == true){
					Util.er(s, "You can't mark yourself as the kingdom leader, you are already the kingdom leader!");
					return false;
				}
				if(GeneralHelper.isTargetInSameKingdomAsSender(s.getName().toLowerCase(), args[1].toLowerCase()) == false){
					Util.er(s, "You can't mark somebody who isn't in the same kingdom as you as the leader of your kingdom.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				MemberManagement.setNewLeader(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM SETTYPE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("settype")) {
				if (!s.hasPermission("kingdoms.general.settype") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false){
					Util.er(s, "You need to be a kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.isString3to12(args[1].toLowerCase()) == false){
					Util.er(s, "Your chosen kingdom type must be between 3 and 12 characters.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				KingdomManagement.setKingdomType(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM SETMAXMEMBERS COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("setmaxmembers")) {
				if (!s.hasPermission("kingdoms.general.setmaxmembers") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false){
					Util.er(s, "You need to be a kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.isOnlyNumbers(args[1]) == false){
					Util.er(s, "The max members you are attempting to set can only contain #'s.");
					return false;
				}
				if(GeneralHelper.startsWithZero(args[1]) == true){
					Util.er(s, "The max members you are attempting cannot start with '0'.");
					return false;
				}
				if(GeneralHelper.isWithinMaxMembersLimits(args[1]) == false){
					Util.er(s, "The max members you are attempting to must be between 3 and 250.");
					return false;
				}
				
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				KingdomManagement.setMaxMembers(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM RENAMERANK COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("renamerank")) {
				if (!s.hasPermission("kingdoms.general.renamerank") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualThree(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false){
					Util.er(s, "You need to be a kingdom leader to use that command.");
					return false;
				}
				if(GeneralHelper.isOnlyLettersAndNumbers(args[1].toLowerCase()) == false){
					Util.er(s, "The new kingdom rank name can only contain letters & numbers.");
					return false;
				}
				if(GeneralHelper.startsWithZero(args[1]) == true){
					Util.er(s, "The target rank you're trying to change cannot start with '0'.");
					return false;
				}
				
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				KingdomManagement.setRankName(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM SETLOWESTRANK COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("setlowestrank") || args[0].toLowerCase().matches("setdefaultrank")) {
				if (!s.hasPermission("kingdoms.general.setlowestrank") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.doArgsEqualTwo(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.isOnlyNumbers(args[1]) == false){
					Util.er(s, "The rank you're trying to mark as the lowest rank must only contain numbers.");
					return false;
				}
				if(GeneralHelper.startsWithZero(args[1]) == true){
					Util.er(s, "The target rank you're trying to change cannot start with '0'.");
					return false;
				}
				if(GeneralHelper.isLowestRankPermitted(args[1]) == false){
					Util.er(s, "The desired new lowest rank must be between 1 and 8.");
					return false;
				}
				
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				KingdomManagement.setLowestRank(args, s);
				return true;
			}
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			// [][][][][][][][][][][][][][][][][][][][][][][][]GENERAL COMMANDS[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM INFO COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if ((args[0].toLowerCase().matches("info") || args[0].toLowerCase().matches("inf")) && args.length == 2) {
				if (!s.hasPermission("kingdoms.general.kingdominfo") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1].toLowerCase()) == false){
					Util.er(s, "No kingdom by that name exists.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getKingdomInfo(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM OWN INFO COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if ((args[0].toLowerCase().matches("info") || args[0].toLowerCase().matches("inf")) && args.length == 1) {
				if (!s.hasPermission("kingdoms.general.kingdominfo") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to view your own kingdoms info page. To see a different kingdom, try \"" + ChatColor.BLUE + "/kingdom info <kingdom name>" + ChatColor.RED + "\".");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getOwnKingdomInfo(s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM DISMISSINVITE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("dismissinvite") || args[0].toLowerCase().matches("dismissinvites")) {
				if (!s.hasPermission("kingdoms.general.dismissinvite") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doArgsEqualOne(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == true){
					Util.er(s, "You're already in a kingdom, you can't interact with kingdom invitation commands.");
					return false;
				}
				if(GeneralHelper.hasKingdomInvite(s) == false){
					Util.er(s, "You don't currently have any invites.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				Recruitment.dismissInvite(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM CHECMKINVITE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("checkinvites") || args[0].toLowerCase().matches("checkinvite") || args[0].toLowerCase().matches("checkinv")) {
				if (!s.hasPermission("kingdoms.general.checkinvite") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doArgsEqualOne(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == true){
					Util.er(s, "You're already in a kingdom, you can't interact with kingdom invitation commands.");
					return false;
				}
				if(GeneralHelper.hasKingdomInvite(s) == false){
					Util.er(s, "You don't currently have any invites.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				Recruitment.getKingdomInvites(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM KINGDOMLIST COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("kingdomlist") || args[0].toLowerCase().matches("list")) {
				if (!s.hasPermission("kingdoms.general.kingdomlist") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doArgsEqualOne(args) == false){
					Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getKingdomList(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM VIEW ROSTER COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if ((args[0].toLowerCase().matches("viewroster") || args[0].toLowerCase().matches("memberlist") || args[0].toLowerCase().matches("members") || args[0].toLowerCase().matches("roster")) && args.length == 2) {
				if (!s.hasPermission("kingdoms.general.viewroster") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1]) == false){
					Util.er(s, "No kingdom by that name exists.");
					return false;
				}
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getKingdomRoster(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM VIEW OWN ROSTER COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if ((args[0].toLowerCase().matches("viewroster") || args[0].toLowerCase().matches("memberlist") || args[0].toLowerCase().matches("members") || args[0].toLowerCase().matches("roster")) && args.length == 1) {
				if (!s.hasPermission("kingdoms.general.viewroster") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to view your own kingdoms member list. To see a different kingdom, try \"" + ChatColor.BLUE + "/kingdom viewroster <kingdom name>" + ChatColor.RED + "\".");
					return false;
				}
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getOwnKingdomRoster(s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM RANK LIST COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if ((args[0].toLowerCase().matches("ranklist") || args[0].toLowerCase().matches("ranks") || args[0].toLowerCase().matches("viewranks")) && args.length == 2){
				if (!s.hasPermission("kingdoms.general.ranklist") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doesKingdomExist(args[1]) == false){
					Util.er(s, "No kingdom by that name exists.");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getKingdomRanks(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM OWN RANK LIST COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if ((args[0].toLowerCase().matches("ranklist") || args[0].toLowerCase().matches("ranks") || args[0].toLowerCase().matches("viewranks")) && args.length == 1) {
				if (!s.hasPermission("kingdoms.general.ranklist") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to view your own kingdoms rank list. To see a different kingdom, try \"" + ChatColor.BLUE + "/kingdom viewranks <kingdom name>" + ChatColor.RED + "\".");
					return false;
				}

				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getOwnKingdomRanks(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM PLAYERINFO COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if ((args[0].toLowerCase().matches("playerinfo") || args[0].toLowerCase().matches("pinfo")) && args.length == 2) {
				if (!s.hasPermission("kingdoms.general.playerinfo") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.doesTargetPlayerExist(args[1].toLowerCase()) == false){
					Util.er(s, "No player by that name exists.");
					return false;
				}
				
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getPlayerInfo(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM OWN PLAYERINFO COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if ((args[0].toLowerCase().matches("playerinfo") || args[0].toLowerCase().matches("pinfo")) && args.length == 1) {
				if (!s.hasPermission("kingdoms.general.playerinfo") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getOwnPlayerInfo(s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM HELP COMMAND PAGE 1][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if ((args[0].toLowerCase().matches("help") || args[0].toLowerCase().matches("commands")) && args.length == 1) {
				if (!s.hasPermission("kingdoms.general.help") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getGeneralCommandListPage1(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM HELP COMMAND PAGE 1][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (((args[0].toLowerCase().matches("help") || args[0].toLowerCase().matches("commands")) && args.length == 2) && args[1].matches("1")) {
				if (!s.hasPermission("kingdoms.general.help") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getGeneralCommandListPage1(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM HELP COMMAND PAGE 2][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (((args[0].toLowerCase().matches("help") || args[0].toLowerCase().matches("commands")) && args.length == 2) && args[1].matches("2")) {
				if (!s.hasPermission("kingdoms.general.help") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getGeneralCommandListPage2(args, s);
				return true;
			}

			// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM HELP COMMAND PAGE 3][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (((args[0].toLowerCase().matches("help") || args[0].toLowerCase().matches("commands")) && args.length == 2) && args[1].matches("3")) {
				if (!s.hasPermission("kingdoms.general.help") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				General.getGeneralCommandListPage3(args, s);
				return true;
			}

			
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			// [][][][][][][][][][][][][][][][][][][][][][][][]CHAT COMMANDS[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			
			// [][][][][][][][][][][][][][][][][][][][][][][][CHAT FOCUS COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if (args[0].toLowerCase().matches("focus")) {
				if (!s.hasPermission("kingdoms.general.chat") && !s.hasPermission("kingdoms.general.*")) {
					Util.noPermsError(s);
					return false;
				}
				if(GeneralHelper.isInKingdom(s) == false){
					Util.er(s, "You need to be in a kingdom to use that command.");
					return false;
				}
				if(GeneralHelper.isFeatureEnabledChat(s) == false){
					Util.er(s, "Kingdom chat is currently disabled on this server.");
					return false;
				}
				//TODO: CHECK FOR: is chat even enabled on this server
				
				// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
				Chat.chatFocusToggle(s, args);
				return true;
			}
			
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			// [][][][][][][][][][][][][][][][][][][][][][][][]ADMIN COMMANDS[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			
			if ((args[0].toLowerCase().matches("admin") || args[0].toLowerCase().matches("adm") || args[0].toLowerCase().matches("a")) && args.length > 1) {

				// [][][][][][][][][][][][][][][][][][][][][][][][ADMIN SETLEVEL COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
				if (args[1].toLowerCase().matches("setlevel")) {
					if (!s.hasPermission("kingdoms.admin.setlevel") && !s.hasPermission("kingdoms.admin.*")) {
						Util.noPermsError(s);
						return false;
					}
					if(GeneralHelper.doArgsEqualFour(args) == false){
						Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
						return false;
					}
					if(GeneralHelper.doesKingdomExist(args[2]) == false){
						Util.er(s, "No kingdom by that name exists.");
						return false;
					}
					if(GeneralHelper.isOnlyNumbers(args[3]) == false){
						Util.er(s, "The desired kingdom level can only contain numbers.");
						return false;
					}
					if(GeneralHelper.startsWithZero(args[3]) == true){
						Util.er(s, "The desired kingdom level cannot start with '0'.");
						return false;
					}
					if(GeneralHelper.isWithinLevelLimits(args[3]) == false){
						Util.er(s, "You cannot set a kingdoms level above 25 or below 1.");
						return false;
					}
					
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					Admin.manuallySetKingdomLevel(args, s);
					return true;
				}

				if (args[1].toLowerCase().matches("disband")) {
					if (!s.hasPermission("kingdoms.admin.disband") && !s.hasPermission("kingdoms.admin.*")) {
						Util.noPermsError(s);
						return false;
					}
					if(GeneralHelper.doesKingdomExist(args[2]) == false){
						Util.er(s, "No kingdom by that name exists.");
						return false;
					}
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					AdminHelper.adminDisband(s, args);
					return true;
				}
				
				if (args[1].toLowerCase().matches("removeplayer")) {
					if (!s.hasPermission("kingdoms.admin.kick") && !s.hasPermission("kingdoms.admin.*")) {
						Util.noPermsError(s);
						return false;
					}
					if (GeneralHelper.isTargetKingdomLeader(args[2])){
						Util.er(s, "That player is a Kingdom Leader, you should disband the kingdom instead or set a new leader first.");
						return false;
					}
					if (GeneralHelper.isTargetInAKingdom(args[2]) == false){
						Util.er(s, "The player targeted is not in a Kingdom currently");
						return false;
					}
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					AdminHelper.adminKick(args, s);
					return true;
				}
				
				// [][][][][][][][][][][][][][][][][][][][][][][][ADMIN LOADCONFIGS COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
				if (args[1].toLowerCase().matches("loadconfigs") ||args[1].toLowerCase().matches("loadconfigchanges") || args[1].toLowerCase().matches("reloadconfig") || args[1].toLowerCase().matches("reloadconfigs") || args[1].toLowerCase().matches("reload")) {
					if (!s.hasPermission("kingdoms.admin.reloadconfig") && !s.hasPermission("kingdoms.admin.*")) {
						Util.noPermsError(s);
						return false;
					}
					if(GeneralHelper.doArgsEqualTwo(args) == false){
						Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
						return false;
					}
					
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					Util.sam(s, "You loaded any changes that were made to the configuration YML files since the last restart.");
					Main.reloadYamls();
					return true;
				}

				// [][][][][][][][][][][][][][][][][][][][][][][][ADMIN CHANGETAG COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
				if (args[1].toLowerCase().matches("changetag")) {
					if (!s.hasPermission("kingdoms.admin.changetag") && !s.hasPermission("kingdoms.admin.*")) {
						Util.noPermsError(s);
						return false;
					}
					if(GeneralHelper.doArgsEqualFour(args) == false){
						Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
						return false;
					}
					if(GeneralHelper.doesKingdomExist(args[2]) == false){
						Util.er(s, "No kingdom by that name exists.");
						return false;
					}
					if(AdminHelper.isNewTagWithin3xTheOldLength(args[3], args[2]) == false){
						Util.er(s, "The inputted prefix is too long. It can be no longer than 3x the length of the normal kingdom name.");
						return false;
					}
					
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					Admin.changeKingdomTag(args, s);
					return true;
				}

				// [][][][][][][][][][][][][][][][][][][][][][][][ADMIN BANPLAYER COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
				if (args[1].toLowerCase().matches("banplayer") || args[1].toLowerCase().matches("ban")) {
					if (!s.hasPermission("kingdoms.admin.banplayer") && !s.hasPermission("kingdoms.admin.*")) {
						Util.noPermsError(s);
						return false;
					}
					if(GeneralHelper.doArgsEqualThree(args) == false){
						Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
						return false;
					}
					if(GeneralHelper.doesTargetPlayerExist(args[2]) == false){
						Util.er(s, "No player by that name exists.");
						return false;
					}
					if(GeneralHelper.isInKingdom(args[2]) == true){
						Util.er(s, "That player is already in a kingdom. Remove them from their kingdom first.");
						return false;
					}
					if(AdminHelper.isAlreadyBannedFromKingdoms(args[2]) == true){
						Util.er(s, "That player is already in banned from using the kingdom system.");
						return false;
					}
					
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					Admin.banPlayer(args, s);
					return true;
				}

				// [][][][][][][][][][][][][][][][][][][][][][][][ADMIN UNBANPLAYER COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
				if (args[1].toLowerCase().matches("unbanplayer") || args[1].toLowerCase().matches("unban")) {
					if (!s.hasPermission("kingdoms.admin.unbanplayer") && !s.hasPermission("kingdoms.admin.*")) {
						Util.noPermsError(s);
						return false;
					}
					if(GeneralHelper.doArgsEqualThree(args) == false){
						Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
						return false;
					}
					if(GeneralHelper.doesTargetPlayerExist(args[2]) == false){
						Util.er(s, "No player by that name exists.");
						return false;
					}
					if(AdminHelper.isAlreadyBannedFromKingdoms(args[2]) == false){
						Util.er(s, "That player isn't banned from using the kingdom system.");
						return false;
					}
					
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					Admin.unbanPlayer(args, s);
					return true;
				}

				// [][][][][][][][][][][][][][][][][][][][][][][][ADMIN HELP COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
				if ((args[1].toLowerCase().matches("help") || args[1].toLowerCase().matches("commands"))) {
					if (!s.hasPermission("kingdoms.admin.help") && !s.hasPermission("kingdoms.admin.*")) {
						Util.noPermsError(s);
						return false;
					}
					
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					Admin.getAdminCommandList(args, s);
					return true;
				}
				Util.er(s, "That is not a recognized kingdom admin command. To see a list of proper commands, type \"" + ChatColor.BLUE + "/kingdom admin help" + ChatColor.RED + "\".");
				return true;
			}
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			// [][][][][][][][][][][][][][][][][][][][][][][][]POWERS COMMANDS[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][
			// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
			if ((args[0].toLowerCase().matches("powers") || args[0].matches("power") || args[0].matches("pow")) && args.length > 1) {

				// [][][][][][][][][][][][][][][][][][][][][][][][POWERS HOMETELE COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
				if (args[1].toLowerCase().matches("hometele") || args[1].toLowerCase().matches("hometeleport") || args[1].toLowerCase().matches("recall")) {
					if (!s.hasPermission("kingdoms.powers.hometeleport") && !s.hasPermission("kingdoms.powers.*")) {
						Util.noPermsError(s);
						return false;
					}
					if(GeneralHelper.doArgsEqualTwo(args) == false){
						Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
						return false;
					}
					if(GeneralHelper.isFeatureEnabledLevelUpsAndPowers(s) == false){
						Util.er(s, "Kingdom level ups and powers are currently disabled on this server.");
						return false;
					}
					if(GeneralHelper.isInKingdom(s) == false){
						Util.er(s, "You need to be in a kingdom to use that command.");
						return false;
					}
					if(PowersHelper.isHomeTeleFeatureEnabled() == false){
						Util.er(s, "The kingdom-home-teleport feature is not enabled on your server.");
						return false;
					}
					if(PowersHelper.isSendersKingdomHighEnoughLevelForHomeTele(s) == false){
						Util.er(s, "Your kingdom isn't high enough level to use this kingdom power.");
						return false;
					}
					if(PowersHelper.sendersKingdomHasHomeLocationSet(s) == false){
						Util.er(s, "Your kingdom doesn't have a home location set.");
						return false;
					}
					if(PowersHelper.isTargetWorldNull(s.getName().toLowerCase()) == true){
						Util.er(s, "The world we're trying to send you to is null, so we didn't teleport you there just to be safe.");
						return false;
					}
					
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					Powers.homeTeleport(args, s);
					return true;
				}

				// [][][][][][][][][][][][][][][][][][][][][][][][POWERS SETHOME COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
				if (args[1].toLowerCase().matches("sethome")) {
					if (!s.hasPermission("kingdoms.powers.sethome") && !s.hasPermission("kingdoms.powers.*")) {
						Util.noPermsError(s);
						return false;
					}
					if(GeneralHelper.doArgsEqualTwo(args) == false){
						Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
						return false;
					}
					if(GeneralHelper.isFeatureEnabledLevelUpsAndPowers(s) == false){
						Util.er(s, "Kingdom level ups and powers are currently disabled on this server.");
						return false;
					}
					if(GeneralHelper.isInKingdom(s) == false){
						Util.er(s, "You need to be in a kingdom to use that command.");
						return false;
					}
					if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false){
						Util.er(s, "You need to be the kingdom leader to use that command.");
						return false;
					}
					if(PowersHelper.isSetHomeFeatureEnabled() == false){
						Util.er(s, "The kingdom-set-home feature is not enabled on your server.");
						return false;
					}
					if(PowersHelper.isSendersKingdomHighEnoughLevelForSetHome(s) == false){
						Util.er(s, "Your kingdom isn't high enough level to use this kingdom power.");
						return false;
					}
					
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					Powers.setKingdomHome(args, s);
					return true;
				}

				// [][][][][][][][][][][][][][][][][][][][][][][][POWERS COMPASS COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
				if (args[1].toLowerCase().matches("compass")) {
					if (!s.hasPermission("kingdoms.powers.compass") && !s.hasPermission("kingdoms.powers.*")) {
						Util.noPermsError(s);
						return false;
					}
					if(GeneralHelper.doArgsEqualTwo(args) == false){
						Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
						return false;
					}
					if(GeneralHelper.isFeatureEnabledLevelUpsAndPowers(s) == false){
						Util.er(s, "Kingdom level ups and powers are currently disabled on this server.");
						return false;
					}
					if(GeneralHelper.isInKingdom(s) == false){
						Util.er(s, "You need to be in a kingdom to use that command.");
						return false;
					}
					if(PowersHelper.isCompassFeatureEnabled() == false){
						Util.er(s, "The kingdom-compass feature is not enabled on your server.");
						return false;
					}
					if(PowersHelper.isSendersKingdomHighEnoughLevelForCompass(s) == false){
						Util.er(s, "Your kingdom isn't high enough level to use this kingdom power.");
						return false;
					}
					if(PowersHelper.sendersKingdomHasHomeLocationSet(s) == false){
						Util.er(s, "Your kingdom doesn't have a home location set.");
						return false;
					}
					if(PowersHelper.isOnSameWorldAsHomeLoc(s) == false){
						Util.er(s, "You aren't on the same world as your kingdoms home, so your compass can't point to it.");
						return false;
					}
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					Powers.compassPoint(args, s);
					return true;
				}

				// [][][][][][][][][][][][][][][][][][][][][][][][POWERS HELP COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
				if (args[1].toLowerCase().matches("help") || args[1].toLowerCase().matches("commands")) {
					if (!s.hasPermission("kingdoms.powers.help") && !s.hasPermission("kingdoms.powers.*")) {
						Util.noPermsError(s);
						return false;
					}
					if(GeneralHelper.isFeatureEnabledLevelUpsAndPowers(s) == false){
						Util.er(s, "Kingdom level ups and powers are currently disabled on this server.");
						return false;
					}
					
					// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
					Powers.getPowersCommandList(args, s);
					return true;
				}
				Util.er(s, "That is not a recognized kingdom power command. To see a list of proper commands, type \"" + ChatColor.BLUE + "/kingdom powers help" + ChatColor.RED + "\".");
				return true;
			}
		}
		// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		// [][][][][][][][][][][][][][][][][][][][][][][][]SHORT FORM COMMANDS[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][
		// [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		
		// [][][][][][][][][][][][][][][][][][][][][][][][SHORTFORM CLAIM COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		if (cmd.getName().equalsIgnoreCase("kclaim")) {

			Player p = (Player) s;
			LandControl.setChunkTotal(p);
			
			if (!(s instanceof Player)) {
				Util.er(s, "Only players can type Kingdoms commands. Log into the game and try again.");
				return false;
			}

			if (AdminHelper.isBannedFromKingdoms(p) == true) {
				Util.er(s, "You are currently banned from interacting with the Kingdoms system. Talk to your server admin if you believe this is in error.");
				return false;
			}
			
			if (!s.hasPermission("kingdoms.protection.claim") && s.hasPermission("kingdoms.protection.*")) {
				Util.er(s, "You don't have permission to use this command.");
				return true;
			}
			if(GeneralHelper.isFeatureEnabledLandClaiming(s) == false){
				Util.er(s, "Kingdom land claiming is currently disabled on this server.");
				return false;
			}
			if (GeneralHelper.isInKingdom(p) == false){
				Util.er(s, "You are are not in a Kingdom.");
				return false;
			}
			if (GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false && GeneralHelper.isSenderKingdomOfficer(s.getName().toLowerCase())){
			 	Util.er(s, "You need to be a kingdom leader or officer to use that command.");
			}
			if (LandControlHelper.isChunkClaimed(p) == true){
				return false;
			}
			if (LandControlHelper.isClaimerAdjacent(p) == false){
				return false;
			}
			// CHECK FOR: can the player afford it
			// --- COMMAND PASSED ALL CHECKS ---
			LandControl.claim(p, args);
			return true;
		}
		
		// [][][][][][][][][][][][][][][][][][][][][][][][SHORTFORM UNCLAIM COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		if (cmd.getName().equalsIgnoreCase("kunclaim")) {

			Player p = (Player) s;
			LandControl.setChunkTotal(p);
			
			if (!(s instanceof Player)) {
				Util.er(s, "Only players can type Kingdoms commands. Log into the game and try again.");
				return false;
			}

			if (AdminHelper.isBannedFromKingdoms(p) == true) {
				Util.er(s, "You are currently banned from interacting with the Kingdoms system. Talk to your server admin if you believe this is in error.");
				return false;
			}
			if(GeneralHelper.isFeatureEnabledLandClaiming(s) == false){
				Util.er(s, "Kingdom land claiming is currently disabled on this server.");
				return false;
			}
			
			if (!s.hasPermission("kingdoms.protection.unclaim") && s.hasPermission("kingdoms.protection.*")) {
				Util.er(s, "You don't have permission to use this command.");
				return true;
			}
			if (GeneralHelper.isInKingdom(p) == false){
				Util.er(s, "You are are not in a Kingdom.");
				return false;
			}
			if (GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false && GeneralHelper.isSenderKingdomOfficer(s.getName().toLowerCase())){
			 	Util.er(s, "You need to be a kingdom leader or officer to use that command.");
			}
			if (LandControlHelper.isChunkClaimersKingdom(p) == false){
				return false;
			}

			// --- COMMAND PASSED ALL CHECKS ---
			LandControl.unclaim(p, args);
			return true;
		}

		// [][][][][][][][][][][][][][][][][][][][][][][][SHORTFORM POWERS HOME COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		if (cmd.getName().equalsIgnoreCase("khome")) {

			Player p = (Player) s;
			
			if (!(s instanceof Player)) {
				Util.er(s, "Only players can type Kingdoms commands. Log into the game and try again.");
				return false;
			}

			if (AdminHelper.isBannedFromKingdoms(p) == true) {
				Util.er(s, "You are currently banned from interacting with the Kingdoms system. Talk to your server admin if you believe this is in error.");
				return false;
			}
			if (!s.hasPermission("kingdoms.powers.hometeleport") && !s.hasPermission("kingdoms.powers.*")) {
				Util.noPermsError(s);
				return false;
			}
			if(GeneralHelper.isFeatureEnabledLevelUpsAndPowers(s) == false){
				Util.er(s, "Kingdom level ups and powers are currently disabled on this server.");
				return false;
			}
			if(GeneralHelper.isInKingdom(s) == false){
				Util.er(s, "You need to be in a kingdom to use that command.");
				return false;
			}
			if(PowersHelper.isHomeTeleFeatureEnabled() == false){
				Util.er(s, "The kingdom-home-teleport feature is not enabled on your server.");
				return false;
			}
			if(PowersHelper.isSendersKingdomHighEnoughLevelForHomeTele(s) == false){
				Util.er(s, "Your kingdom isn't high enough level to use this kingdom power.");
				return false;
			}
			if(PowersHelper.sendersKingdomHasHomeLocationSet(s) == false){
				Util.er(s, "Your kingdom doesn't have a home location set.");
				return false;
			}
			if(PowersHelper.isTargetWorldNull(s.getName().toLowerCase()) == true){
				Util.er(s, "The world we're trying to send you to is null, so we didn't teleport you there just to be safe.");
				return false;
			}
			
			// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
			Powers.homeTeleport(args, s);
			return true;
		}

		// [][][][][][][][][][][][][][][][][][][][][][][][SHORTFORM POWERS SETHOME COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		if (cmd.getName().equalsIgnoreCase("ksethome")) {
			Player p = (Player) s;
			if (!(s instanceof Player)) {
				Util.er(s, "Only players can type Kingdoms commands. Log into the game and try again.");
				return false;
			}
			if (AdminHelper.isBannedFromKingdoms(p) == true) {
				Util.er(s, "You are currently banned from interacting with the Kingdoms system. Talk to your server admin if you believe this is in error.");
				return false;
			}
			if (!s.hasPermission("kingdoms.powers.sethome") && !s.hasPermission("kingdoms.powers.*")) {
				Util.noPermsError(s);
				return false;
			}
			if(GeneralHelper.isFeatureEnabledLevelUpsAndPowers(s) == false){
				Util.er(s, "Kingdom level ups and powers are currently disabled on this server.");
				return false;
			}
			if(GeneralHelper.isInKingdom(s) == false){
				Util.er(s, "You need to be in a kingdom to use that command.");
				return false;
			}
			if(GeneralHelper.isSenderKingdomLeader(s.getName().toLowerCase()) == false){
				Util.er(s, "You need to be the kingdom leader to use that command.");
				return false;
			}
			if(PowersHelper.isSetHomeFeatureEnabled() == false){
				Util.er(s, "The kingdom-set-home feature is not enabled on your server.");
				return false;
			}
			if(PowersHelper.isSendersKingdomHighEnoughLevelForSetHome(s) == false){
				Util.er(s, "Your kingdom isn't high enough level to use this kingdom power.");
				return false;
			}
			
			// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
			Powers.setKingdomHome(args, s);
			return true;
		}

		// [][][][][][][][][][][][][][][][][][][][][][][][SHORTFORM POWERS COMPASS COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		if (cmd.getName().equalsIgnoreCase("kcompass")) {
			Player p = (Player) s;
			if (!(s instanceof Player)) {
				Util.er(s, "Only players can type Kingdoms commands. Log into the game and try again.");
				return false;
			}
			if (AdminHelper.isBannedFromKingdoms(p) == true) {
				Util.er(s, "You are currently banned from interacting with the Kingdoms system. Talk to your server admin if you believe this is in error.");
				return false;
			}
			if (!s.hasPermission("kingdoms.powers.compass") && !s.hasPermission("kingdoms.powers.*")) {
				Util.noPermsError(s);
				return false;
			}
			if(GeneralHelper.isFeatureEnabledLevelUpsAndPowers(s) == false){
				Util.er(s, "Kingdom level ups and powers are currently disabled on this server.");
				return false;
			}
			if(GeneralHelper.isInKingdom(s) == false){
				Util.er(s, "You need to be in a kingdom to use that command.");
				return false;
			}
			if(PowersHelper.isCompassFeatureEnabled() == false){
				Util.er(s, "The kingdom-compass feature is not enabled on your server.");
				return false;
			}
			if(PowersHelper.isSendersKingdomHighEnoughLevelForCompass(s) == false){
				Util.er(s, "Your kingdom isn't high enough level to use this kingdom power.");
				return false;
			}
			if(PowersHelper.sendersKingdomHasHomeLocationSet(s) == false){
				Util.er(s, "Your kingdom doesn't have a home location set.");
				return false;
			}
			if(PowersHelper.isOnSameWorldAsHomeLoc(s) == false){
				Util.er(s, "You aren't on the same world as your kingdoms home, so your compass can't point to it.");
				return false;
			}
			// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
			Powers.compassPoint(args, s);
			return true;
		}

		// [][][][][][][][][][][][][][][][][][][][][][][][SHORTFORM KINGDOM HELP COMMAND PAGE 1][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		if ((cmd.getName().equalsIgnoreCase("kingdom") || cmd.getName().equalsIgnoreCase("k")) && (args.length == 0 || (args.length == 1 && args[0].matches("1")))) {
			if (!s.hasPermission("kingdoms.general.help") && !s.hasPermission("kingdoms.general.*")) {
				Util.noPermsError(s);
				return false;
			}
			
			// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
			General.getGeneralCommandListPage1(args, s);
			return true;
		}

		// [][][][][][][][][][][][][][][][][][][][][][][][SHORTFORM KINGDOM HELP COMMAND PAGE 1][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		if ((cmd.getName().equalsIgnoreCase("kingdom") || cmd.getName().equalsIgnoreCase("k")) && (args.length == 0 || (args.length == 1 && args[0].matches("1")))) {
			if (!s.hasPermission("kingdoms.general.help") && !s.hasPermission("kingdoms.general.*")) {
				Util.noPermsError(s);
				return false;
			}
			
			// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
			General.getGeneralCommandListPage1(args, s);
			return true;
		}

		// [][][][][][][][][][][][][][][][][][][][][][][][SHORTFORM KINGDOM HELP COMMAND PAGE 2][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		if (((cmd.getName().equalsIgnoreCase("kingdom") || cmd.getName().equalsIgnoreCase("k")) && args.length == 1) && args[0].matches("2")) {
			if (!s.hasPermission("kingdoms.general.help") && !s.hasPermission("kingdoms.general.*")) {
				Util.noPermsError(s);
				return false;
			}
			
			// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
			General.getGeneralCommandListPage2(args, s);
			return true;
		}

		// [][][][][][][][][][][][][][][][][][][][][][][][SHORTFORM KINGDOM HELP COMMAND PAGE 2][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		if (((cmd.getName().equalsIgnoreCase("kingdom") || cmd.getName().equalsIgnoreCase("k")) && args.length == 1) && args[0].matches("3")) {
			if (!s.hasPermission("kingdoms.general.help") && !s.hasPermission("kingdoms.general.*")) {
				Util.noPermsError(s);
				return false;
			}
			
			// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
			General.getGeneralCommandListPage3(args, s);
			return true;
		}

		// [][][][][][][][][][][][][][][][][][][][][][][][KINGDOM VERSION COMMAND][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
		if (cmd.getName().equalsIgnoreCase("kversion")) {
			if (!s.hasPermission("kingdoms.general.version") && !s.hasPermission("kingdoms.general.*")) {
				Util.noPermsError(s);
				return false;
			}
			
			// --- COMMAND PASSED ALL CHECKS --- 19/02/2013
			Util.sm(s, "Your plugin version: " + Main.version);
			return true;
		}
		
		Util.er(s, "Incorrectly formatted kingdom command. For help, type \"" + ChatColor.BLUE + "/kingdom help" + ChatColor.RED + "\".");
		return true;
	}
}