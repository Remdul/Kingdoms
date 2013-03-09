package kingdomsteam.kingdomsmain.Powers;

import kingdomsteam.kingdomsmain.Main;
import kingdomsteam.kingdomsmain.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Powers {	
	
	static String sendersName;
	static String sendersKingdom;
	static Player senderAsPlayer;
	static Location sendersLocation;
	static int sendersX;
	static int sendersY;
	static int sendersZ;
	static int currentKingdomsLevel;
	static int requiredLevel;
	static String sendersWorldAsString;
	static World kingdomsHomeLocationWorld;
	static double kingdomsHomeLocationX;
	static double kingdomsHomeLocationY;
	static double kingdomsHomeLocationZ;
	
	public static boolean setKingdomHome(String[] args, CommandSender s){
		sendersName = s.getName().toLowerCase();
		sendersKingdom = Main.players.getString("Players." + sendersName + ".Current_Kingdom");
		requiredLevel = Main.levelUpsAndPowersConfig.getInt("Level_Unlocks.SetHome.Level_Unlocked");
		currentKingdomsLevel = Main.kingdoms.getInt("Kingdoms." + sendersKingdom + ".Level");
		senderAsPlayer = (Player) s;
		sendersLocation = senderAsPlayer.getLocation();
		sendersX = (int) sendersLocation.getX();
		sendersY = (int) sendersLocation.getY();
		sendersZ = (int) sendersLocation.getZ();
		sendersWorldAsString = sendersLocation.getWorld().getName();
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Home_Location.X_Coordinate", sendersX);
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Home_Location.Y_Coordinate", sendersY);
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Home_Location.Z_Coordinate", sendersZ);
		Main.kingdoms.set("Kingdoms." + sendersKingdom + ".Home_Location.World", sendersWorldAsString);
		Util.sm(s,"You set your kingdoms home to: " + String.valueOf(sendersX) + "x," + String.valueOf(sendersY) + "y," + String.valueOf(sendersZ) + "z, on the world: " + sendersWorldAsString + ".");
		Main.saveKingdomsYaml();
		return true;
	}
	public static boolean updateKingdomLevel(String pName, String gName){
		int lKingdomsCurrentXP = Main.kingdoms.getInt("Kingdoms." + gName + ".Total_XP");
		int lKingdomsCurrentLevel = Main.kingdoms.getInt("Kingdoms." + gName + ".Level");
		int lNextLevelForKingdom = lKingdomsCurrentLevel + 1;
		int lXPRequiredForNextLevel = Main.levelUpsAndPowersConfig.getInt("Levels_XP_Required."+lNextLevelForKingdom);
		if(lKingdomsCurrentLevel >= 25){
			return false;
		}
		if(lKingdomsCurrentXP >= lXPRequiredForNextLevel){
			Util.bc(" LEVEL UP! The kingdom [" + Util.getRealKingdomName(gName.toLowerCase()) + ChatColor.GRAY + "] has reached level " + lNextLevelForKingdom + "!");
			Main.kingdoms.set("Kingdoms." + gName + ".Level", lNextLevelForKingdom);
			Main.saveKingdomsYaml();
		}
		
		return true;
	}
	
	public static boolean homeTeleport(String[] args, CommandSender s){
		sendersName = s.getName().toLowerCase();
		sendersKingdom = Main.players.getString("Players." + sendersName + ".Current_Kingdom");
		requiredLevel = Main.levelUpsAndPowersConfig.getInt("Level_Unlocks.HomeTele.Level_Unlocked");
		currentKingdomsLevel = Main.kingdoms.getInt("Kingdoms." + sendersKingdom + ".Level");
		senderAsPlayer = (Player) s;
		kingdomsHomeLocationX = Main.kingdoms.getDouble("Kingdoms." + sendersKingdom + ".Home_Location.X_Coordinate");
		kingdomsHomeLocationY = Main.kingdoms.getDouble("Kingdoms." + sendersKingdom + ".Home_Location.Y_Coordinate") + 1;
		kingdomsHomeLocationZ = Main.kingdoms.getDouble("Kingdoms." + sendersKingdom + ".Home_Location.Z_Coordinate");
		kingdomsHomeLocationWorld = Bukkit.getWorld(Main.kingdoms.getString("Kingdoms." + sendersKingdom + ".Home_Location.World"));
		Location teleportLocation = senderAsPlayer.getLocation();
		teleportLocation.setX(kingdomsHomeLocationX);
		teleportLocation.setY(kingdomsHomeLocationY);
		teleportLocation.setZ(kingdomsHomeLocationZ);
		teleportLocation.setWorld(kingdomsHomeLocationWorld);
		senderAsPlayer.teleport(teleportLocation);
		Util.sm(s,"You teleported to your kingdoms home.");
		Main.saveKingdomsYaml();
		return true;
	}
	
	public static boolean compassPoint(String[] args, CommandSender s){
		sendersName = s.getName().toLowerCase();
		sendersKingdom = Main.players.getString("Players." + sendersName + ".Current_Kingdom");
		requiredLevel = Main.levelUpsAndPowersConfig.getInt("Level_Unlocks.Compass.Level_Unlocked");
		currentKingdomsLevel = Main.kingdoms.getInt("Kingdoms." + sendersKingdom + ".Level");
		senderAsPlayer = (Player) s;
		kingdomsHomeLocationX = Main.kingdoms.getDouble("Kingdoms." + sendersKingdom + ".Home_Location.X_Coordinate");
		kingdomsHomeLocationY = Main.kingdoms.getDouble("Kingdoms." + sendersKingdom + ".Home_Location.Y_Coordinate") + 1;
		kingdomsHomeLocationZ = Main.kingdoms.getDouble("Kingdoms." + sendersKingdom + ".Home_Location.Z_Coordinate");
		kingdomsHomeLocationWorld = Bukkit.getWorld(Main.kingdoms.getString("Kingdoms." + sendersKingdom + ".Home_Location.World"));
		Location teleportLocation = senderAsPlayer.getLocation();
		teleportLocation.setX(kingdomsHomeLocationX);
		teleportLocation.setY(kingdomsHomeLocationY);
		teleportLocation.setZ(kingdomsHomeLocationZ);
		teleportLocation.setWorld(kingdomsHomeLocationWorld);
		senderAsPlayer.setCompassTarget(teleportLocation);
		Util.sm(s,"Your compass is now pointing to your kingdoms home location.");
		return true;
	}
	
	public static boolean getPowersCommandList(String[] args, CommandSender s){
		Util.srm(s,ChatColor.DARK_GRAY + "___________ ~..,,|| " + ChatColor.DARK_AQUA +  "Kingdoms Powers Help" + ChatColor.DARK_GRAY + " ||,,..~ ___________");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/kingdom power sethome");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/kingdom power hometele");
		Util.srm(s,ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/kingdom power compass");
		Util.srm(s,ChatColor.DARK_GRAY + "________________________________________________");
		Util.srm(s,"");
		return true;
	}
	
	public static boolean applyPermissions(){
		
		//int lKingdomsCurrentLevel = Main.kingdoms.getInt("Kingdoms." + gName + ".Level");
		
		return true;
	}
}
