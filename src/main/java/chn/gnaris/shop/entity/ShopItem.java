package chn.gnaris.shop.entity;

import org.bukkit.inventory.ItemStack;

public class ShopItem {

    private int slot;
    private ItemStack item;
    private long quantity;
    private long purchasePrice;
    private long sellingPrice;
    private boolean canBuy;
    private boolean canSell;
    private boolean isInfinite;

    public ShopItem(int slot, ItemStack item, long quantity, long purchasePrice, long sellingPrice, boolean canBuy, boolean canSell, boolean isInfinite) {
        this.slot = slot;
        this.item = item;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.canBuy = canBuy;
        this.canSell = canSell;
        this.isInfinite = isInfinite;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItem() {
        return item;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getPurchasePrice() {
        return purchasePrice;
    }

    public long getSellingPrice() {
        return sellingPrice;
    }

    public boolean canBuy() {
        return canBuy;
    }

    public boolean canSell() {
        return canSell;
    }

    public boolean isInfinite() {
        return isInfinite;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
