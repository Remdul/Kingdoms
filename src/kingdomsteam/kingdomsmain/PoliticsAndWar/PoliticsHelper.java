package kingdomsteam.kingdomsmain.PoliticsAndWar;
import java.util.List;

import kingdomsteam.kingdomsmain.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PoliticsHelper {
	
	public static boolean areKingdomsAtWar(Player damager, Player damagee){
		String damagersKingdom = Main.players.getString("Players." + damager.getName().toLowerCase() + ".Current_Kingdom");
		String damageesKingdom = Main.players.getString("Players." + damagee.getName().toLowerCase() + ".Current_Kingdom");
		String damagersCurrentWarEnemy = Main.kingdoms.getString("Kingdoms." + damagersKingdom + ".Current_War_Enemy");
		String damageesCurrentWarEnemy = Main.kingdoms.getString("Kingdoms." + damageesKingdom + ".Current_War_Enemy");
		if(damagersCurrentWarEnemy == null || damageesCurrentWarEnemy == null){
			return false;
		}
		if(damageesCurrentWarEnemy.matches(damagersKingdom) && damagersCurrentWarEnemy.matches(damageesKingdom)){
			return true;
		}
		return false;
	}

	public static boolean areKingdomsAtWar(String sendersKingdom, String targetKingdom) {
		String sendersKingdomsCurrentWarEnemy = Main.kingdoms.getString("Kingdoms." + sendersKingdom + ".Current_War_Enemy");
		String targetKingdomsCurrentWarEnemy = Main.kingdoms.getString("Kingdoms." + targetKingdom + ".Current_War_Enemy");
		if(sendersKingdomsCurrentWarEnemy == null || targetKingdomsCurrentWarEnemy == null){
			return false;
		}
		if(sendersKingdomsCurrentWarEnemy.matches(targetKingdom) && targetKingdomsCurrentWarEnemy.matches(sendersKingdom)){
			return true;
		}
		return false;
	}
	
	public static boolean isKingdomInARaidableState(String targetKingdom){
		targetKingdom = targetKingdom.toLowerCase();
		//Long now = System.currentTimeMillis();
		Player[] onlineList = Bukkit.getOnlinePlayers();
		List<String> memberList = Main.kingdoms.getStringList("Kingdoms." + targetKingdom.toLowerCase() + ".Members");
		int numMembersOnline = 0;
		for(Player onlinePlayer : onlineList){
			String onlinePlayersName = onlinePlayer.getName();
			for(String member : memberList){
				if(onlinePlayersName.matches(member)){
					numMembersOnline++;
				}
			}
		}
		if(numMembersOnline >= Main.politicsAndWarConfig.getInt("War.Minimum_Members_Online_To_Be_Raidable")){
			//Util.DEBUG("The kingdom " + targetKingdom + " has at least " + String.valueOf(Main.politicsAndWarConfig.getInt("War.Minimum_Members_Online_To_Be_Raidable")) + " members online so they are raidable.");
			return true;
		}
/*		if(Main.lastKingdomInteractions.get(targetKingdom) >= now){
			//Util.DEBUG("The kingdom " + targetKingdom + " had 3 members online in the last " + String.valueOf(Main.politicsAndWarConfig.getInt("War.Raidability_Grace_Period_(Minutes)")) + " minutes so they are raidable.");
			return true;
		}*/
		return false;
	}
}
