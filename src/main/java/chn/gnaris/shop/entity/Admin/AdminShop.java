package chn.gnaris.shop.entity.Admin;

import chn.gnaris.shop.entity.ShopItem;
import chn.gnaris.shop.entity.Serializer.ShopLocation;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AdminShop {
    private transient Villager seller;
    private String sellerName;
    private String profession;
    private ShopLocation position;

    private List<ShopItem> items;

    public AdminShop(String sellerName, String profession, ShopLocation position, List<ShopItem> items) {
        this.sellerName = sellerName;
        this.profession = profession;
        this.position = position;
        this.items = items;
    }

    public void spawnSeller()
    {
        seller = (Villager) position.getLocation().getWorld().spawnEntity(position.getLocation(), EntityType.VILLAGER);
        seller.setCustomName(sellerName);
        seller.setVillagerExperience(1);
        seller.setCollidable(false);
        seller.setSilent(true);
        try {
            seller.setProfession(Villager.Profession.valueOf(profession));
        }catch (IllegalArgumentException e)
        {
            seller.setProfession(Villager.Profession.NONE);
        }
        seller.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 100, false, false, false));
    }

    public Location getPosition() {
        return position.getLocation();
    }

    public String getSellerName() {
        return sellerName;
    }
    public List<ShopItem> getItems() {
        return items;
    }

    public Villager getSeller() {
        return seller;
    }
}