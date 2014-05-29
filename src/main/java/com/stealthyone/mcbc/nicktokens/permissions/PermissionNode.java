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