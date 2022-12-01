package chn.gnaris.shop.event;

import Entity.Economy;
import Economy.EconomyAPI;
import chn.gnaris.shop.ShopMain;
import chn.gnaris.shop.entity.Admin.AdminShop;
import chn.gnaris.shop.entity.ShopItem;
import chn.gnaris.shop.entity.Admin.GUI;
import chn.gnaris.shop.event.Controller.AdminShopEventController;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminShopEvent implements Listener {

    private ShopMain plugin;
    private EconomyAPI economyAPI = (EconomyAPI) Bukkit.getServer().getPluginManager().getPlugin("Economy");

    public AdminShopEvent(ShopMain plugin) {
        this.plugin = plugin;
    }

    private boolean isAdminShop(Entity entity)
    {
        if(!(entity instanceof Villager)) return false;
        if(entity.getCustomName() == null) return false;
        return plugin.getAdminShop().values().stream().anyMatch(shop -> shop.getSeller().getCustomName().equalsIgnoreCase(entity.getCustomName()));
    }
    private AdminShop getAdminShop(Entity entity)
    {
        return plugin.getAdminShop().values().stream().filter(shop -> shop.getSeller().getCustomName().equalsIgnoreCase(entity.getCustomName())).findFirst().orElse(null);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e)
    {
        if(!isAdminShop(e.getEntity())) return;
        AdminShop adminShop = getAdminShop(e.getEntity());
        if(e.getDamager() instanceof Player) ((Player) e.getDamager()).openInventory(new GUI(adminShop).getInventory());
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e)
    {
        if(!isAdminShop(e.getRightClicked())) return;
        AdminShop adminShop = getAdminShop(e.getRightClicked());
        e.getPlayer().openInventory(new GUI(adminShop).getInventory());
        e.setCancelled(true);
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e)
    {
        if(!isAdminShop(e.getTarget())) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        if(!plugin.getAdminShop().values().stream().anyMatch(shop -> shop.getSellerName().equalsIgnoreCase(e.getView().getTitle()))) return;
        e.setCancelled(true);
        if(e.getCurrentItem() == null) return;

        AdminShop adminShop = plugin.getAdminShop().values().stream()
                .filter(shop -> shop.getSellerName().equalsIgnoreCase(e.getView().getTitle()))
                .findFirst()
                .orElse(null);

        String itemClickedName = e.getCurrentItem().getItemMeta().getDisplayName();
        int itemClickedSlot = e.getSlot();
        ShopItem shopItem = adminShop.getItems().stream()
                .filter(it -> it.getItem().equals(e.getCurrentItem()))
                .findFirst()
                .orElse(null);
        if(shopItem == null) return;
        Economy playerEconomy = economyAPI.getEconomies().get(e.getWhoClicked().getUniqueId());

        AdminShopEventController adminShopEventController = new AdminShopEventController((Player) e.getWhoClicked(), shopItem, playerEconomy);
        if(e.getClick().equals(ClickType.RIGHT))
        {
            if(!adminShopEventController.canBuy(1)) return;
            if(shopItem.isInfinite()) shopItem.setQuantity(shopItem.getQuantity() - 1);
            playerEconomy.remove(shopItem.getPurchasePrice());
            e.getWhoClicked().getInventory().addItem(new ItemStack(e.getCurrentItem().getType()));
            e.getWhoClicked().sendMessage("§c-" + shopItem.getPurchasePrice() + economyAPI.getCurrency());
            return;
        }
        if(e.getClick().equals(ClickType.SHIFT_RIGHT))
        {
            if(!adminShopEventController.canBuy(64)) return;
            if(shopItem.isInfinite()) shopItem.setQuantity(shopItem.getQuantity() - 64);
            playerEconomy.remove(shopItem.getPurchasePrice() * 64);
            e.getWhoClicked().getInventory().addItem(new ItemStack(e.getCurrentItem().getType(), 64));
            e.getWhoClicked().sendMessage("§c-" + shopItem.getPurchasePrice() * 64 + economyAPI.getCurrency());
            return;
        }
        if(e.getClick().equals(ClickType.LEFT))
        {
            List<ItemStack> itemList = Arrays.asList(e.getWhoClicked().getInventory().getContents()).stream()
                    .filter(ci -> ci != null && ci.getType().equals(e.getCurrentItem().getType()))
                    .collect(Collectors.toList());
            if(!adminShopEventController.canSell(itemList)) return;
            itemList.get(0).setAmount(itemList.get(0).getAmount() - 1);
            playerEconomy.add(shopItem.getSellingPrice());
            e.getWhoClicked().sendMessage("§a+" + shopItem.getSellingPrice() + economyAPI.getCurrency());
            return;
        }

        if(e.getClick().equals(ClickType.SHIFT_LEFT))
        {
            List<ItemStack> itemList = Arrays.asList(e.getWhoClicked().getInventory().getContents()).stream()
                    .filter(ci -> ci != null && ci.getType().equals(e.getCurrentItem().getType()) && ci.getAmount() == 64)
                    .collect(Collectors.toList());
            if(!adminShopEventController.canSell(itemList)) return;
            playerEconomy.add(shopItem.getSellingPrice() * 64);
            itemList.get(0).setAmount(0);
            e.getWhoClicked().sendMessage("§a+" + shopItem.getSellingPrice() * 64 + economyAPI.getCurrency());
        }
    }
}
