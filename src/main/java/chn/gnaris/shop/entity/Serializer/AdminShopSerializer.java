package chn.gnaris.shop.entity.Serializer;

import chn.gnaris.shop.entity.Admin.AdminShop;
import chn.gnaris.shop.ShopMain;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.*;
import java.util.Map;

public class AdminShopSerializer {

    private File file;

    public AdminShopSerializer(ShopMain plugin)
    {
        this.file = new File(plugin.getDataFolder() + "/AdminShop.json");
    }

    public AdminShopSerializer createFile() throws IOException {
        if(!file.exists())
        {
            file.createNewFile();
        }
        return this;
    }

    public Map<String, AdminShop> getAdminShopData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder data = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) data.append(line);
        Gson gson = new Gson();
        return gson.fromJson(data.toString(), new TypeToken<Map<String, AdminShop>>(){}.getType());
    }
}
