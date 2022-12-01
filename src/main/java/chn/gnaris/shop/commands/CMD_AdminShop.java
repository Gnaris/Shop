package chn.gnaris.shop.commands;

import chn.gnaris.shop.ShopMain;
import chn.gnaris.shop.commands.Controller.AdminShopController;
import chn.gnaris.shop.entity.Admin.AdminShop;
import chn.gnaris.shop.entity.Serializer.ShopLocation;
import chn.gnaris.shop.entity.ShopItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CMD_AdminShop implements CommandExecutor {

    private ShopMain plugin;

    public CMD_AdminShop(ShopMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player) && args.length > 1) return false;
        Player player = (Player) sender;
        AdminShopController adminShopController = new AdminShopController(plugin, player);
        String sellerName = args[1].replace("&", "§");
        if(args.length == 3)
        {
            String profession = args[2];
            // /adminshop create {seller name} {profession}
            if(args[0].equalsIgnoreCase("create"))
            {
                if(!adminShopController.canCreateAdminShop(sellerName, profession)) return false;
                plugin.getAdminShop().put(sellerName, new AdminShop(sellerName, profession, new ShopLocation(player.getLocation()), new ArrayList<>()));
                plugin.getAdminShop().get(sellerName).spawnSeller();
                player.sendMessage("§aLe shop " + sellerName + "§r§a vient d'être créer");
            }

        }

        if(args.length == 9)
        {
            //adminshop setitem {seller name} {slot} {quantity} {purchasePrice} {sellingPrice} {canBuy} {canSell} {infinite}
            String slot = args[2];
            String quantity = args[3];
            String purchasePrice = args[4];
            String sellingPrice = args[5];
            String canBuy = args[6];
            String canSell = args[7];
            String isInfinite = args[8];
            if(args[0].equalsIgnoreCase("setitem"))
            {
                if(!adminShopController.canAddItem(slot, quantity, purchasePrice, sellingPrice, canBuy, canSell, isInfinite)) return false;
                plugin.getAdminShop().get(sellerName).getItems().add(
                        new ShopItem(Integer.parseInt(slot),
                                player.getInventory().getItemInMainHand(),
                                Integer.parseInt(quantity),
                                Integer.parseInt(purchasePrice),
                                Integer.parseInt(sellingPrice),
                                Boolean.parseBoolean(canBuy),
                                Boolean.parseBoolean(canSell),
                                Boolean.parseBoolean(isInfinite)
                                ));
            }
        }
        return false;
    }
}
