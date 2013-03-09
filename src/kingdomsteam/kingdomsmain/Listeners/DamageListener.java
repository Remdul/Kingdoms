package kingdomsteam.kingdomsmain.Listeners;

import kingdomsteam.kingdomsmain.PoliticsAndWar.PoliticsHelper;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener{
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public boolean onPlayerDamageByPlayerEvent(EntityDamageByEntityEvent e){
		Player damagee;
		Player damager;
		if(e.isCancelled()){
			if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)){
				return false;
			}
			damager = (Player) e.getDamager();
			damagee = (Player) e.getEntity();
			if(PoliticsHelper.areKingdomsAtWar(damager, damagee) == true){
				e.setCancelled(false);
				return true;
			}
			return false;
		}
		return false;
	}
}
