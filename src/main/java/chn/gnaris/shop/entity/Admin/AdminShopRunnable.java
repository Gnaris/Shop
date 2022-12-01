package chn.gnaris.shop.entity.Admin;

import chn.gnaris.shop.ShopMain;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AdminShopRunnable implements Runnable {

    private ShopMain plugin;

    public AdminShopRunnable(ShopMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if(plugin.getAdminShop().size() > 0)
        {
            for(AdminShop adminShop : plugin.getAdminShop().values())
            {
                if(adminShop.getSeller().getLocation().getX() != adminShop.getPosition().getX() ||
                        adminShop.getSeller().getLocation().getY() != adminShop.getPosition().getY() ||
                        adminShop.getSeller().getLocation().getZ() != adminShop.getPosition().getZ()
                )
                {
                    adminShop.getSeller().teleport(adminShop.getPosition());
                }
                if(adminShop.getSeller().isDead())
                {
                    adminShop.spawnSeller();
                }
                if(!adminShop.getSeller().hasPotionEffect(PotionEffectType.SLOW))
                {
                    adminShop.getSeller().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 10, false, false, false));
                }
            }
        }
    }
}
