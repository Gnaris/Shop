package chn.gnaris.shop.entity;

import chn.gnaris.shop.ShopMain;
import org.bukkit.entity.Player;

public abstract class Controller {

    protected ShopMain plugin;

    protected Player player;

    public Controller(ShopMain plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }
}
