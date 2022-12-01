package chn.gnaris.shop.entity.Serializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ShopLocation {
    private String world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public ShopLocation(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public ShopLocation(Location location)
    {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    public Location getLocation()
    {
        return new Location(Bukkit.getWorld(world), x, y ,z, yaw, pitch);
    }
}
