package kingdomsteam.kingdomsmain.Powers;

import kingdomsteam.kingdomsmain.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class PowersHelper {
	public static boolean isHomeTeleFeatureEnabled() {
		boolean isHomeTeleEnabled = Main.levelUpsAndPowersConfig.getBoolean("Level_Unlocks.HomeTele.Enabled");
		if(isHomeTeleEnabled == true){
			return true;
		}
		return false;
	}
	public static boolean isSendersKingdomHighEnoughLevelForHomeTele(CommandSender s) {
		int homeTeleRequiredLevel = Main.levelUpsAndPowersConfig.getInt("Level_Unlocks.HomeTele.Level_Unlocked");
		int sendersKingdomLevel = Main.kingdoms.getInt("Kingdoms."+(Main.players.getString("Players."+s.getName().toLowerCase()+".Current_Kingdom"))+".Level");
		if(sendersKingdomLevel < homeTeleRequiredLevel){
			return false;
		}
		return true;
	}
	public static boolean sendersKingdomHasHomeLocationSet(CommandSender s) {
		String sendersName = s.getName().toLowerCase();
		String sendersKingdom = Main.players.getString("Players."+sendersName+".Current_Kingdom");
		int sendersKingdomYCoord = Main.kingdoms.getInt("Kingdoms."+sendersKingdom+".Home_Location.X_Coordinate");
		if(sendersKingdomYCoord == 0){
			return false;
		}
		return true;
	}
	public static boolean isTargetWorldNull(String sendersName) {
		String sendersKingdom = Main.players.getString("Players."+sendersName+".Current_Kingdom");
		String worldName = Main.kingdoms.getString("Kingdoms."+sendersKingdom+".Home_Location.World");
		if(Bukkit.getWorld(worldName) == null){
			return true;
		}
		return false;
	}
	public static boolean isSetHomeFeatureEnabled() {
		boolean isSetHomeEnabled = Main.levelUpsAndPowersConfig.getBoolean("Level_Unlocks.SetHome.Enabled");
		if(isSetHomeEnabled == true){
			return true;
		}
		return false;
	}
	public static boolean isSendersKingdomHighEnoughLevelForSetHome(CommandSender s) {
		int setHomeRequiredLevel = Main.levelUpsAndPowersConfig.getInt("Level_Unlocks.SetHome.Level_Unlocked");
		int sendersKingdomLevel = Main.kingdoms.getInt("Kingdoms."+(Main.players.getString("Players."+s.getName().toLowerCase()+".Current_Kingdom"))+".Level");
		if(sendersKingdomLevel < setHomeRequiredLevel){
			return false;
		}
		return true;
	}
	public static boolean isCompassFeatureEnabled() {
		boolean isCompassEnabled = Main.levelUpsAndPowersConfig.getBoolean("Level_Unlocks.Compass.Enabled");
		if(isCompassEnabled == true){
			return true;
		}
		return false;
	}
	public static boolean isSendersKingdomHighEnoughLevelForCompass(CommandSender s) {
		int compassRequiredLevel = Main.levelUpsAndPowersConfig.getInt("Level_Unlocks.Compass.Level_Unlocked");
		int sendersKingdomLevel = Main.kingdoms.getInt("Kingdoms."+(Main.players.getString("Players."+s.getName().toLowerCase()+".Current_Kingdom"))+".Level");
		if(sendersKingdomLevel < compassRequiredLevel){
			return false;
		}
		return true;
	}
	public static boolean isOnSameWorldAsHomeLoc(CommandSender s) {
		Player p = (Player) s;
		String sendersKingdom = Main.players.getString("Players."+s.getName().toLowerCase()+".Current_Kingdom");
		String worldName = Main.kingdoms.getString("Kingdoms."+sendersKingdom+".Home_Location.World");
		String sendersWorld = p.getWorld().getName();
		if(!worldName.matches(sendersWorld)){
			return false;
		}
		return true;
	}

}
