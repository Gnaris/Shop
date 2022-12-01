package chn.gnaris.shop.event.Controller;

import Economy.EconomyAPI;
import Entity.Economy;
import chn.gnaris.shop.entity.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AdminShopEventController {

    private Player player;
    private ShopItem shopItem;
    private Economy playerEconomy;

    private EconomyAPI economyAPI = (EconomyAPI) Bukkit.getServer().getPluginManager().getPlugin("Economy");

    public AdminShopEventController(Player player, ShopItem shopItem, Economy playerEconomy) {
        this.player = player;
        this.shopItem = shopItem;
        this.playerEconomy = playerEconomy;
    }

    public boolean canBuy(int itemAmount)
    {
        if(!shopItem.canBuy())
        {
            player.sendMessage("§cCe shop ne vend pas cette item");
            return false;
        }
        if((playerEconomy.getMoney() - (shopItem.getPurchasePrice() * itemAmount)) < 0)
        {
            player.sendMessage("§cVous n'avez pas assez d'argent. Il vous manque " + ((shopItem.getPurchasePrice() * itemAmount) - playerEconomy.getMoney()) + economyAPI.getCurrency());
            return false;
        }
        if(!shopItem.isInfinite())
        {
            if(shopItem.getQuantity() - itemAmount < 0)
            {
                player.sendMessage("§cEn rupture de stock !");
                return false;
            }
        }
        return true;
    }

    public boolean canSell(List<ItemStack> itemList)
    {
        if(!shopItem.canSell())
        {
            player.sendMessage("§cCe shop ne rachète pas cette item");
            return false;
        }
        if(itemList.size() == 0)
        {
            player.sendMessage("§cVous en avez pas assez");
            return false;
        }
        return true;
    }
}
