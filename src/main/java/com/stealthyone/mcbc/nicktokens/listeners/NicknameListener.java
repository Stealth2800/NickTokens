package com.stealthyone.mcbc.nicktokens.listeners;

import com.stealthyone.mcbc.nicktokens.NickTokens;
import com.stealthyone.mcbc.nicktokens.messages.Messages;
import com.stealthyone.mcbc.nicktokens.permissions.PermissionNode;
import net.ess3.api.events.NickChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class NicknameListener implements Listener {

    private NickTokens plugin;

    public NicknameListener(NickTokens plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onNickChange(NickChangeEvent e) {
        Player p = e.getController().getBase();
        if (PermissionNode.ADMIN.isAllowed(p)) return;

        UUID uuid = p.getUniqueId();

        int tokens = plugin.getTokenManager().getTokens(uuid);
        if (tokens <= 0) {
            e.setCancelled(true);
            Messages.CANNOT_CHANGE_NICK.sendTo(p);
        } else {
            plugin.getTokenManager().removeTokens(uuid, 1);
            Messages.PLAYER_TOKEN_USED.sendTo(p);
        }
    }

}