package com.stealthyone.mcbc.nicktokens;

import com.stealthyone.mcb.stbukkitlib.messages.MessageManager;
import com.stealthyone.mcb.stbukkitlib.players.PlayerUUIDTracker;
import com.stealthyone.mcbc.nicktokens.backend.TokenManager;
import com.stealthyone.mcbc.nicktokens.commands.CmdNickTokens;
import com.stealthyone.mcbc.nicktokens.listeners.NicknameListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class NickTokens extends JavaPlugin {

    private static NickTokens instance;

    private MessageManager messageManager = new MessageManager(this);
    private PlayerUUIDTracker uuidTracker;
    private TokenManager tokenManager = new TokenManager(this);

    private CmdNickTokens cmdNickTokens = new CmdNickTokens(this);
    private NicknameListener nicknameListener = new NicknameListener(this);

    @Override
    public void onLoad() {
        NickTokens.instance = this;
        getDataFolder().mkdir();
        new File(getDataFolder() + File.separator + "data").mkdir();
    }

    @Override
    public void onEnable() {
        messageManager.reloadMessages();
        uuidTracker = new PlayerUUIDTracker(this);
        uuidTracker.load();
        tokenManager.load();

        Bukkit.getPluginManager().registerEvents(nicknameListener, this);

        getCommand("nicktokens").setExecutor(cmdNickTokens);

        getLogger().info(String.format("%s v%s by Stealth2800 ENABLED.", getName(), getDescription().getVersion()));
    }


    @Override
    public void onDisable() {
        uuidTracker.save();
        tokenManager.save();

        getLogger().info(String.format("%s v%s by Stealth2800 DISABLED.", getName(), getDescription().getVersion()));
        NickTokens.instance = null;
    }

    public static NickTokens getInstance() {
        return NickTokens.instance;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public PlayerUUIDTracker getUuidTracker() {
        return uuidTracker;
    }

    public TokenManager getTokenManager() {
        return tokenManager;
    }

}