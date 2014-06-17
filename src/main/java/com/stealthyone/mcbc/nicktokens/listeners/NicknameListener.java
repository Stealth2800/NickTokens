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