package chn.gnaris.shop.commands.Controller;

import chn.gnaris.shop.ShopMain;
import chn.gnaris.shop.entity.Controller;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.Arrays;

public class AdminShopController extends Controller {

    public AdminShopController(ShopMain plugin, Player player) {
        super(plugin, player);
    }

    public boolean canCreateAdminShop(String villagerName, String profession)
    {
        if(plugin.getAdminShop().containsKey(villagerName))
        {
            player.sendMessage("§cLe nom de ce shop est déjà utilisé");
            return false;
        }
        try{
            Villager.Profession.valueOf(profession);
        }catch (IllegalArgumentException e)
        {
            StringBuilder professions = new StringBuilder();
            Arrays.stream(Villager.Profession.values()).forEach(p -> professions.append(p.name()).append(" "));
            player.sendMessage("§cProfession non reconnu, voici la liste : " + professions);
            return false;
        }
        return true;
    }

    public boolean canAddItem(String slot, String quantity, String purchasePrice, String sellingPrice, String canBuy, String canSell, String isInfinite)
    {
        try{
            Integer.parseInt(slot);
            Integer.parseInt(quantity);
            Integer.parseInt(purchasePrice);
            Integer.parseInt(sellingPrice);
        }catch (NumberFormatException e)
        {
            player.sendMessage("§c" + e.getMessage().substring(19, 21) + " n'est pas un nombre valide ");
            return false;
        }
        if(!canBuy.equalsIgnoreCase("true") && !canBuy.equalsIgnoreCase("false"))
        {
            player.sendMessage("§c" + canBuy + " n'est pas une valeur valide (true ou false)");
            return false;
        }
        if(!canSell.equalsIgnoreCase("true") && !canSell.equalsIgnoreCase("false"))
        {
            player.sendMessage("§c" + canSell + " n'est pas une valeur valide (true ou false)");
            return false;
        }if(!isInfinite.equalsIgnoreCase("true") && !isInfinite.equalsIgnoreCase("false"))
        {
            player.sendMessage("§c" + isInfinite + " n'est pas une valeur valide (true ou false)");
            return false;
        }
        return true;
    }
}
