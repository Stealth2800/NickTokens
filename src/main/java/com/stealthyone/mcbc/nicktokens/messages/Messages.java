package com.stealthyone.mcbc.nicktokens.messages;

import com.stealthyone.mcb.stbukkitlib.messages.MessagePath;
import com.stealthyone.mcbc.nicktokens.NickTokens;
import org.bukkit.command.CommandSender;

import java.util.Map;

public enum Messages implements MessagePath{

    CANNOT_CHANGE_NICK(Messages.CATEGORY_ERRORS),
    CANNOT_USE_ON_OTHER(Messages.CATEGORY_ERRORS),
    NO_PERMISSION(Messages.CATEGORY_ERRORS),
    PLAYER_NOT_FOUND(Messages.CATEGORY_ERRORS),
    PLAYER_BALANCE_TOO_LOW(Messages.CATEGORY_ERRORS),
    TOKENS_INVALID_NUMBER(Messages.CATEGORY_ERRORS),
    TOKENS_MUST_BE_NUMBER(Messages.CATEGORY_ERRORS),
    UNKNOWN_COMMAND(Messages.CATEGORY_ERRORS),

    PLAYER_BALANCE(Messages.CATEGORY_NOTICES),
    PLAYER_BALANCE_ADDED(Messages.CATEGORY_NOTICES),
    PLAYER_BALANCE_REMOVED(Messages.CATEGORY_NOTICES),
    PLAYER_BALANCE_RESET(Messages.CATEGORY_NOTICES),
    PLUGIN_RELOADED(Messages.CATEGORY_NOTICES),
    PLUGIN_SAVED(Messages.CATEGORY_NOTICES),
    PLAYER_TOKEN_USED(Messages.CATEGORY_NOTICES),

    HELP(Messages.CATEGORY_USAGES),
    HELP_FULL(Messages.CATEGORY_USAGES),
    TOKENS_ADD(Messages.CATEGORY_USAGES),
    TOKENS_CHECK(Messages.CATEGORY_USAGES),
    TOKENS_REMOVE(Messages.CATEGORY_USAGES),
    TOKENS_RESET(Messages.CATEGORY_USAGES);

    private static final String CATEGORY_ERRORS = "errors";
    private static final String CATEGORY_NOTICES = "notices";
    private static final String CATEGORY_USAGES = "usages";

    private String path;

    private Messages(String category) {
        path = category + "." + toString().toLowerCase();
    }

    @Override
    public String getPath() {
        return path;
    }

    public void sendTo(CommandSender sender) {
        NickTokens.getInstance().getMessageManager().getMessage(this).sendTo(sender);
    }

    public void sendTo(CommandSender sender, Map<String, String> replacements) {
        NickTokens.getInstance().getMessageManager().getMessage(this).sendTo(sender, replacements);
    }

    public String[] getFormattedMessages() {
        return NickTokens.getInstance().getMessageManager().getMessage(this).getFormattedMessages();
    }

    public String[] getFormattedMessages(Map<String, String> replacements) {
        return NickTokens.getInstance().getMessageManager().getMessage(this).getFormattedMessages(replacements);
    }

}