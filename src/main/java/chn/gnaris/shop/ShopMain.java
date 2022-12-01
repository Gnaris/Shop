package chn.gnaris.shop;

import chn.gnaris.shop.commands.CMD_AdminShop;
import chn.gnaris.shop.entity.Admin.AdminShop;
import chn.gnaris.shop.entity.Admin.AdminShopRunnable;
import chn.gnaris.shop.entity.Serializer.AdminShopSerializer;
import chn.gnaris.shop.event.AdminShopEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ShopMain extends JavaPlugin {

    private Map<String, AdminShop> adminShop = new HashMap<>();
    @Override
    public void onEnable() {
        saveDefaultConfig();

        getCommand("adminshop").setExecutor(new CMD_AdminShop(this));

        getServer().getPluginManager().registerEvents(new AdminShopEvent(this), this);

        try {
            adminShop = new AdminShopSerializer(this).createFile().getAdminShopData();
            if(adminShop == null) adminShop = new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(adminShop.size() > 0) adminShop.values().forEach(AdminShop::spawnSeller);
        Bukkit.getScheduler().runTaskTimer(this, new AdminShopRunnable(this), 20L * 10, 20L * 10);
    }

    @Override

    public void onDisable() {
        adminShop.values().forEach(adminShop -> adminShop.getSeller().setHealth(0));
    }

    public Map<String, AdminShop> getAdminShop() {
        return adminShop;
    }
}
