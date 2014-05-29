package com.stealthyone.mcbc.nicktokens.backend;

import com.stealthyone.mcb.stbukkitlib.storage.YamlFileManager;
import com.stealthyone.mcbc.nicktokens.NickTokens;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class TokenManager {

    private NickTokens plugin;

    private YamlFileManager tokenFile;
    private Map<UUID, Integer> loadedTokens = new HashMap<>();

    public TokenManager(NickTokens plugin) {
        this.plugin = plugin;
    }

    public void save() {
        FileConfiguration config = tokenFile.getConfig();
        for (Entry<UUID, Integer> entry : loadedTokens.entrySet()) {
            config.set(entry.getKey().toString(), entry.getValue());
        }
        tokenFile.saveFile();
    }

    public void load() {
        tokenFile = new YamlFileManager(plugin.getDataFolder() + File.separator + "data" + File.separator + "tokens.yml");
        reloadTokens();
    }

    public int reloadTokens() {
        loadedTokens.clear();
        FileConfiguration config = tokenFile.getConfig();
        for (String uuidRaw : config.getKeys(false)) {
            UUID uuid;
            try {
                uuid = UUID.fromString(uuidRaw);
            } catch (Exception ex) {
                plugin.getLogger().severe("An error occurred while trying to load tokens for UUID: '" + uuidRaw + "' in data/tokens.yml");
                continue;
            }

            loadedTokens.put(uuid, config.getInt(uuidRaw));
        }
        return loadedTokens.size();
    }

    public int getTokens(UUID uuid) {
        return !loadedTokens.containsKey(uuid) ? 0 : loadedTokens.get(uuid);
    }

    public void addTokens(UUID uuid, int amt) {
        loadedTokens.put(uuid, getTokens(uuid) + amt);
    }

    public boolean removeTokens(UUID uuid, int amt) {
        if (amt > getTokens(uuid)) {
            return false;
        }
        loadedTokens.put(uuid, getTokens(uuid) - amt);
        return true;
    }

    public void resetTokens(UUID uuid) {
        loadedTokens.put(uuid, 0);
    }

}