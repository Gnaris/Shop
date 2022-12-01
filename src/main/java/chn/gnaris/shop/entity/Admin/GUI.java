package chn.gnaris.shop.entity.Admin;

import Economy.EconomyAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUI {

    private Inventory inventory;
    private EconomyAPI economyAPI = (EconomyAPI) Bukkit.getServer().getPluginManager().getPlugin("Economy");

    public GUI(AdminShop adminShop) {
        this.inventory = Bukkit.createInventory(null, 54, adminShop.getSellerName());
        adminShop.getItems().forEach(item -> {
            ItemStack itemStack = item.getItem();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setLore(Arrays.asList(
                    "",
                    "§aStock : " + (item.isInfinite() ? "Infini" : item.getQuantity()),
                    item.canBuy() ? "§aPrix d'achat : " + item.getPurchasePrice() + economyAPI.getCurrency() + "/u (" + (item.getPurchasePrice() * 64) + economyAPI.getCurrency() + "/stack)" : "",
                    item.canSell() ? "§cPrix de vente : " + item.getSellingPrice() + economyAPI.getCurrency() + "/u (" + (item.getSellingPrice() * 64) + economyAPI.getCurrency() + "/stack)" : "",
                    "",
                    "§aClique droit pour acheter",
                    "§cClique gauche pour vendre",
                    "§6Maintenez SHIFT pour le faire par stack"
                    ));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(item.getSlot(), itemStack);
        });
    }

    public Inventory getInventory() {
        return inventory;
    }
}
