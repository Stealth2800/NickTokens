/*
 * NickTokens - Tokens for nickname changes using Essentials
 * Copyright (C) 2014 Stealth2800 <stealth2800@stealthyone.com>
 * Website: <http://stealthyone.com/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.stealthyone.mcbc.nicktokens;

import com.stealthyone.mcb.stbukkitlib.messages.MessageManager;
import com.stealthyone.mcb.stbukkitlib.players.PlayerUUIDTracker;
import com.stealthyone.mcb.stbukkitlib.updates.UpdateChecker;
import com.stealthyone.mcbc.nicktokens.backend.TokenManager;
import com.stealthyone.mcbc.nicktokens.commands.CmdNickTokens;
import com.stealthyone.mcbc.nicktokens.listeners.NicknameListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.File;
import java.io.IOException;

public class NickTokens extends JavaPlugin {

    private static NickTokens instance;

    private MessageManager messageManager = new MessageManager(this);
    private PlayerUUIDTracker uuidTracker;
    private TokenManager tokenManager = new TokenManager(this);
    private UpdateChecker updateChecker;

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

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            //Failed
        }

        updateChecker = new UpdateChecker(this, 81382);
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

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

}