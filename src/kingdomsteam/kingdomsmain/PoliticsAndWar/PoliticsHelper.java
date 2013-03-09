package kingdomsteam.kingdomsmain.PoliticsAndWar;
import kingdomsteam.kingdomsmain.Main;

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
}
