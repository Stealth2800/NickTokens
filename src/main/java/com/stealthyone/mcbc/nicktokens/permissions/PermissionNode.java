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
package com.stealthyone.mcbc.nicktokens.permissions;

import com.stealthyone.mcbc.nicktokens.messages.Messages;
import org.bukkit.command.CommandSender;

public enum PermissionNode {

    ADMIN,

    TOKENS_ADD,
    TOKENS_CHECK,
    TOKENS_CHECK_OTHER,
    TOKENS_REMOVE,
    TOKENS_RESET,

    RELOAD,
    SAVE;

    private String permission;

    private PermissionNode() {
        permission = "nicktokens." + toString().toLowerCase().replace("_", ".");
    }

    public boolean isAllowed(CommandSender sender) {
        return sender.hasPermission(permission);
    }

    public boolean isAllowedAlert(CommandSender sender) {
        boolean result = isAllowed(sender);
        if (!result) {
            Messages.NO_PERMISSION.sendTo(sender);
        }
        return result;
    }

}