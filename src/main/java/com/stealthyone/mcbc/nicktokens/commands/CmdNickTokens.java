package com.stealthyone.mcbc.nicktokens.commands;

import com.stealthyone.mcb.stbukkitlib.utils.QuickMap;
import com.stealthyone.mcbc.nicktokens.NickTokens;
import com.stealthyone.mcbc.nicktokens.messages.Messages;
import com.stealthyone.mcbc.nicktokens.permissions.PermissionNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdNickTokens implements CommandExecutor {

    private NickTokens plugin;

    public CmdNickTokens(NickTokens plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "add":
                    cmdAdd(sender, command, label, args);
                    return true;

                case "check":
                    cmdCheck(sender, command, label, args);
                    return true;

                case "help":
                    Messages.HELP_FULL.sendTo(sender, new QuickMap<>("{LABEL}", label).build());
                    return true;

                case "reload":
                    cmdReload(sender, command, label, args);
                    return true;

                case "remove":
                    cmdRemove(sender, command, label, args);
                    return true;

                case "reset":
                    cmdReset(sender, command, label, args);
                    return true;

                case "save":
                    cmdSave(sender, command, label, args);
                    return true;

                default:
                    Messages.UNKNOWN_COMMAND.sendTo(sender);
                    break;
            }
        }
        Messages.HELP.sendTo(sender, new QuickMap<>("{LABEL}", label).build());
        return true;
    }

    /*
     * Add tokens to a player's balance.
     */
    private void cmdAdd(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionNode.TOKENS_ADD.isAllowedAlert(sender)) return;

        if (args.length < 2) {
            Messages.TOKENS_ADD.sendTo(sender, new QuickMap<>("{LABEL}", label).build());
            return;
        }

        String targetName;
        int tokens;

        try {
            tokens = Integer.parseInt(args[2]);
            targetName = args[1];
        } catch (NumberFormatException ex) {
            Messages.TOKENS_MUST_BE_NUMBER.sendTo(sender);
            return;
        } catch (IndexOutOfBoundsException ex) {
            try {
                tokens = Integer.parseInt(args[1]);
                if (!(sender instanceof Player)) {
                    Messages.TOKENS_ADD.sendTo(sender, new QuickMap<>("{LABEL}", label).build());
                    return;
                } else {
                    targetName = sender.getName();
                }
            } catch (NumberFormatException ex2) {
                Messages.TOKENS_ADD.sendTo(sender, new QuickMap<>("{LABEL}", label).build());
                return;
            }
        }

        if (tokens <= 0) {
            Messages.TOKENS_INVALID_NUMBER.sendTo(sender);
            return;
        }

        UUID uuid = plugin.getUuidTracker().getUuid(targetName);
        if (uuid == null) {
            Messages.PLAYER_NOT_FOUND.sendTo(sender, new QuickMap<>("{PLAYER}", targetName).build());
            return;
        }

        plugin.getTokenManager().addTokens(uuid, tokens);
        Messages.PLAYER_BALANCE_ADDED.sendTo(sender, new QuickMap<>("{PLAYER}", plugin.getUuidTracker().getName(uuid)).put("{TOKENS}", Integer.toString(tokens)).build());
    }

    /*
     * Check a player's balance.
     */
    private void cmdCheck(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionNode.TOKENS_CHECK.isAllowedAlert(sender)) return;

        String targetName;
        try {
            targetName = args[1];
        } catch (IndexOutOfBoundsException ex) {
            if (!(sender instanceof Player)) {
                Messages.TOKENS_CHECK.sendTo(sender, new QuickMap<>("{LABEL}", label).build());
                return;
            } else {
                targetName = sender.getName();
            }
        }

        if (!targetName.equalsIgnoreCase(sender.getName()) && !PermissionNode.TOKENS_CHECK_OTHER.isAllowed(sender)) {
            Messages.CANNOT_USE_ON_OTHER.sendTo(sender);
            return;
        }

        UUID uuid = plugin.getUuidTracker().getUuid(targetName);
        if (uuid == null) {
            Messages.PLAYER_NOT_FOUND.sendTo(sender, new QuickMap<>("{PLAYER}", targetName).build());
            return;
        }

        int tokens = plugin.getTokenManager().getTokens(uuid);
        Messages.PLAYER_BALANCE.sendTo(sender, new QuickMap<>("{PLAYER}", plugin.getUuidTracker().getName(uuid)).put("{TOKENS}", Integer.toString(tokens)).build());
    }

    /*
     * Reload plugin configuration.
     */
    private void cmdReload(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionNode.RELOAD.isAllowedAlert(sender)) return;

        plugin.reloadConfig();
        plugin.getMessageManager().reloadMessages();
        plugin.getTokenManager().reloadTokens();
        Messages.PLUGIN_RELOADED.sendTo(sender);
    }

    /*
     * Remove tokens from a player's balance.
     */
    private void cmdRemove(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionNode.TOKENS_REMOVE.isAllowedAlert(sender)) return;

        if (args.length < 2) {
            Messages.TOKENS_REMOVE.sendTo(sender, new QuickMap<>("{LABEL}", label).build());
            return;
        }

        String targetName;
        int tokens;

        try {
            tokens = Integer.parseInt(args[2]);
            targetName = args[1];
        } catch (NumberFormatException ex) {
            Messages.TOKENS_MUST_BE_NUMBER.sendTo(sender);
            return;
        } catch (IndexOutOfBoundsException ex) {
            try {
                tokens = Integer.parseInt(args[1]);
                if (!(sender instanceof Player)) {
                    Messages.TOKENS_REMOVE.sendTo(sender, new QuickMap<>("{LABEL}", label).build());
                    return;
                } else {
                    targetName = sender.getName();
                }
            } catch (NumberFormatException ex2) {
                Messages.TOKENS_REMOVE.sendTo(sender, new QuickMap<>("{LABEL}", label).build());
                return;
            }
        }

        if (tokens <= 0) {
            Messages.TOKENS_INVALID_NUMBER.sendTo(sender);
            return;
        }

        UUID uuid = plugin.getUuidTracker().getUuid(targetName);
        if (uuid == null) {
            Messages.PLAYER_NOT_FOUND.sendTo(sender, new QuickMap<>("{PLAYER}", targetName).build());
            return;
        }

        if (plugin.getTokenManager().removeTokens(uuid, tokens)) {
            Messages.PLAYER_BALANCE_REMOVED.sendTo(sender, new QuickMap<>("{PLAYER}", plugin.getUuidTracker().getName(uuid)).put("{TOKENS}", Integer.toString(tokens)).build());
        } else {
            Messages.PLAYER_BALANCE_TOO_LOW.sendTo(sender, new QuickMap<>("{PLAYER}", plugin.getUuidTracker().getName(uuid)).put("{TOKENS}", Integer.toString(tokens)).build());
        }
    }

    /*
     * Reset a player's balance.
     */
    private void cmdReset(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionNode.TOKENS_RESET.isAllowedAlert(sender)) return;

        String targetName;
        try {
            targetName = args[1];
        } catch (IndexOutOfBoundsException ex) {
            if (!(sender instanceof Player)) {
                Messages.TOKENS_RESET.sendTo(sender, new QuickMap<>("{LABEL}", label).build());
                return;
            }
            targetName = sender.getName();
        }

        UUID uuid = plugin.getUuidTracker().getUuid(targetName);
        if (uuid == null) {
            Messages.PLAYER_NOT_FOUND.sendTo(sender, new QuickMap<>("{PLAYER}", targetName).build());
            return;
        }

        plugin.getTokenManager().resetTokens(uuid);
        Messages.PLAYER_BALANCE_RESET.sendTo(sender, new QuickMap<>("{PLAYER}", plugin.getUuidTracker().getName(uuid)).build());
    }

    /*
     * Save plugin data.
     */
    private void cmdSave(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionNode.SAVE.isAllowedAlert(sender)) return;

        plugin.getTokenManager().save();
        plugin.saveConfig();
        Messages.PLUGIN_SAVED.sendTo(sender);
    }

}