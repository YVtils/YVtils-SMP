package yv.tils.smp.utils.configs.language

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class en_yml {
    private var file = File(YVtils.instance.dataFolder.path + "/language", "en.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    //TODO: Reformat Placeholders PREFIX -> <prefix>

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvnetwork.de/yvtils-smp/docs")
        ymlFile.addDefault(
            "Language",
            "EN"
        )
        ymlFile.addDefault(
            "#1",
            "Please always use given variables! You can recognize them by looking for with percentages surrounded words"
        )
        ymlFile.addDefault(
            "START_MESSAGE",
            "<prefix> <green>Plugin is starting!"
        )
        ymlFile.addDefault(
            "START_COMPLETED_MESSAGE",
            "<prefix> <dark_green>Plugin has started successfully!"
        )
        ymlFile.addDefault(
            "STOP_MESSAGE",
            "<prefix> <red>Plugin is getting stopped!"
        )
        ymlFile.addDefault(
            "STOP_COMPLETED_MESSAGE",
            "<prefix> <dark_red>Plugin got stopped!"
        )
        ymlFile.addDefault(
            "PLUGIN_UP_TO_DATE",
            "<prefix> <white>The Plugin has no Updates available!"
        )
        ymlFile.addDefault(
            "PLUGIN_UPDATE_AVAILABLE",
            "<prefix> <yellow>The Plugin Version <newversion> is now available. The Server is using <oldversion>. Download the newest version here <link>"
        )

        ymlFile.addDefault(
            "PLAYER_PLUGIN_UPDATE_AVAILABLE",
            "<prefix> <white>Update available:<newline><yellow>Running Version: <gray><oldversion><newline><yellow>Newest Version: <gray><newversion><newline><yellow>Download: <gray><link>"
        )

        ymlFile.addDefault(
            "UNKNOWN_TIME_FORMAT",
            "This time format is not available!"
        )
        ymlFile.addDefault(
            "WHITELIST_EMPTY",
            "The whitelist is empty!"
        )
        ymlFile.addDefault(
            "PLAYER_NOT_ONLINE",
            "This player is not online!"
        )
        ymlFile.addDefault(
            "PLAYER_UNKNOWN",
            "<prefix> <dark_red>Unkown Player"
        )
        ymlFile.addDefault(
            "PLAYER_ALREADY_BANNED",
            "This player is already banned!"
        )
        ymlFile.addDefault(
            "MISSING_PERMISSION",
            "<red>Missing Permission:"
        )
        ymlFile.addDefault(
            "SPAWN_ELYTRA_BOOST",
            "<white>To boost yourself press"
        )
        ymlFile.addDefault(
            "SMP_START_MESSAGE",
            "<light_purple>Have fun at project name!"
        )
        ymlFile.addDefault(
            "SMP_ALREADY_STARTED",
            "<light_purple>This SMP has already started!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_ENABLE",
            "<gray>You can fly now!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_DISABLE",
            "<gray>You can't fly anymore!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_ENABLE_OTHER",
            "<gray><player> can now fly!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_DISABLE_OTHER",
            "<gray><player> can't fly anymore!"
        )
        ymlFile.addDefault(
            "HEAL_PLAYER_HEALED",
            "<gray>You got <dark_gray><bold>healed!"
        )
        ymlFile.addDefault(
            "HEAL_OTHER_PLAYER_HEALED",
            "<gray><player> got <dark_gray><bold>healed!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_ENABLE",
            "<gray>God Mode is now <dark_gray>activated<gray>!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_DISABLE",
            "<gray>God Mode is now <dark_gray>deactivated<gray>!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_ENABLE_OTHER",
            "<gray><player> is now in the God Mode!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_DISABLE_OTHER",
            "<gray><player> is not in the God Mode anymore!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH",
            "<gray>Your game mode got changed to <green><gamemode><gray>!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_OTHER",
            "<gray>The game mode of <player> got changed to <green><gamemode><gray>!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_ALREADY_IN_THIS_GAMEMODE",
            "<red>You are already in this Gamemode!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_ALREADY_IN_THIS_GAMEMODE_OTHER",
            "<red><player> is already in this game mode!"
        )
        ymlFile.addDefault(
            "MSG_NOTE",
            "<yellow>[<red>Note<yellow>]<white> <message>"
        )
        ymlFile.addDefault(
            "MSG_PLAYER_WENT_OFFLINE",
            "<prefix> <dark_red>The player with which you have written, has gone offline!"
        )
        ymlFile.addDefault(
            "MSG_HAVENT_MESSAGED_A_PLAYER",
            "<prefix> <dark_red>You haven't written to anybody yet!"
        )
        ymlFile.addDefault(
            "MOD_NO_REASON",
            "No Reason was given!"
        )
        ymlFile.addDefault(
            "MOD_PLAYER_NOT_BANNED",
            "<prefix> <dark_gray><player><gray> is not banned!"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_KICK",
            "'<prefix> <dark_gray><player> <gray>got kicked from <dark_gray><moderator><gray>! Reason"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_BAN",
            "'<prefix> <dark_gray><player> <gray>got banned from <dark_gray><moderator><gray>! Reason"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_TEMPBAN",
            "'<prefix> <dark_gray><player> <gray>got banned temporary from <dark_gray><moderator><gray>! Reason"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_UNBAN",
            "<prefix> <dark_gray><player> <gray>got unbanned from <dark_gray><moderator><gray>!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_PLAYER_NOT_ALLOWED_TO_JOIN_KICK_MESSAGE",
            "You are not allowed to join the server while maintenance works!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_COMMAND_DEACTIVATE",
            "<gray>The maintenance mode is now <green>deactivated<gray>!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_COMMAND_ACTIVATE",
            "<gray>The maintenance mode is now <green>activated<gray>!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_ALREADY_DEACTIVATED",
            "<gray>The maintenance mode is already <green>deactivated<gray>!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_ALREADY_ACTIVATED",
            "<gray>The maintenance mode is already <green>activated<gray>!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_ILLEGAL_CONFIG_VALUE",
            "This config value is not valid!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_STATUS_ENABLED",
            "<gray>The maintenance mode is <green>activated<gray>!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_STATUS_DISABLED",
            "<gray>The maintenance mode is <green>dactivated<gray>!"
        )
        ymlFile.addDefault(
            "COMMAND_USAGE",
            "<gray>Use:"
        )
        ymlFile.addDefault(
            "COMMAND_REPLACE_COPY_COMMAND_TO_CLIPBOARD",
            "<gray>Click to copy!"
        )
        ymlFile.addDefault(
            "COMMAND_REPLACE_NEW_COMMAND_INFO",
            "<gray>Please use: COMMAND"
        )
        ymlFile.addDefault(
            "TAB_COMPLETER_MOD_COMMAND_DURATION",
            "Duration"
        )
        ymlFile.addDefault(
            "TAB_COMPLETER_MOD_COMMAND_REASON",
            "[Reason]"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_NO_BOT_TOKEN_GIVEN",
            "No bot token was found. Please enter one or disable this module!"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_STARTUP_FAILED",
            "Bot startup has failed! Please check your configurations!"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_STARTUP_FINISHED",
            "Bot startup was successful! The bot should be online now!"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_CHANGE",
            "Discord user '<discorduser>' has changed his whitelisted account from '<oldname>' to '<newname>'"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_ADD",
            "Discord user '<discorduser>' has added his Minecraft account '<name>' to the whitelist"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_WRONG",
            "Discord user '<discorduser>' has tried to whitelist the Minecraft account '<name>', but it failed! -> Account does not exist"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_SERVERERROR_CHECK_INPUT",
            "Discord user '<discorduser>' has tried to whitelist the Minecraft account '<name>', but it failed! -> Authentication server is not reachable"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_CHANGE",
            "You successfully changed your Minecraft account!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_CHANGE",
            "<oldname> -> <newname>"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_ADD",
            "You successfully whitelisted your account!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_ADD",
            "Account Name"
        )

        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_NOTEXISTING",
            "This Minecraft account does not exist!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_NOTEXISTING",
            "Account Name"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_SERVER_ERROR",
            "Authentication servers are not available!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_SERVER_ERROR",
            "Account Name"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_UNALLOWED_CHARACTERS",
            "This Minecraft account does not exist!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_UNALLOWED_CHARACTERS",
            "Account Name"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_ACCOUNT_ALREADY_WHITELISTED",
            "This account is already whitelisted!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_ACCOUNT_ALREADY_WHITELISTED",
            "Account Name"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_TITLE",
            "Whitelist Check"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_WHITELISTED_DESC",
            "Account Name"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_NOT_WHITELISTED_DESC",
            "Account Name"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_ADD_TITLE",
            "You successfully whitelisted this account!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_ADD_DESC",
            "Minecraft Account"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REPLACE_TITLE",
            "You successfully changed the whitelisted account of <dcname>!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REPLACE_DESC",
            "<oldname> -> <newname>"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVE_TITLE",
            "Which account do you want to remove from the whitelist?"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVE_DESC",
            "￬ Choose it down here ￬"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVED_TITLE",
            "You successfully removed the Minecraft account <mcname> (<dcname>) from the whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVED_DESC",
            "Do you want to remove a second account? <newline> ￬ Choose it down here ￬"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_ADD",
            "Discord user '<discorduser>' has whitelisted the Minecraft account '<mcname>' (linked with '<dcname>')"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_CHANGE",
            "Discord user '<discorduser>' has changed the linked account from '<dcname>' from '<oldname>' to '<newname>'"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_REMOVE",
            "Discord user '<discorduser>' has removed the Minecraft account '<mcname>' (linked with '<dcname>') from the whitelist"
        )
        ymlFile.addDefault(
            "EMBED_CMD_ROLE_ADD_ERROR_TITLE",
            "There occurred an error while adding the role!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_ROLE_ADD_ERROR_DESC",
            "The role that should be given (<role>), could not be given to the user because of the wrong hierarchy! Please set the bot role over the role which should be given to the user."
        )
        ymlFile.addDefault(
            "MODULE_STATUS_OTHER_PLAYER_HAS_NO_STATUS",
            "This player has no status set!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_OTHER_UNALLOWED",
            "<red>You can't clear the status of other players!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_OTHER_CLEARED",
            "<gray>You successfully cleared the status of <yellow><player> <gray>!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_CLEARED",
            "<gray>You successfully cleared the status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_PLAYER_HAS_NO_STATUS",
            "<gray>You have no status set!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CUSTOM_STATUS_TOO_LONG",
            "<red>This custom status is too long!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_NOT_ALLOWED_TO_SET_CUSTOM_STATUS",
            "<gray>You are missing permissions to set a custom status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_NO_DEFAULT_STATUS",
            "<gray>This is no default status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_SET",
            "<gray>You successfully set the status <status><gray>!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_SELECTED_STATUS_JOIN_ANNOUNCEMENT",
            "<gray>The status '<status><gray>' is set!"
        )
        ymlFile.addDefault(
            "MODULE_CCR_ACCEPT_RECIPE",
            "<green>Accept crafting recipe"
        )
        ymlFile.addDefault(
            "MODULE_INVSEE_INVENTORY",
            "<yellow>Inventory of <player>"
        )
        ymlFile.addDefault(
            "MODULE_INVSEE_ENDERCHEST",
            "<dark_purple>Enderchest of <player>"
        )
        ymlFile.addDefault(
            "PLAYER_ARGUMENT_MISSING",
            "<red>To use this command here, a player is required!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ENABLE_ANNOUNCEMENT",
            "<prefix> <gray>The global mute got <green>activated<gray>!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_DISABLE_ANNOUNCEMENT",
            "<prefix> <gray>The global mute got <green>deactivated<gray>!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ENABLE_FEEDBACK",
            "<prefix> <gray>You <green>deactivated <gray>the chat!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_DISABLE_FEEDBACK",
            "<prefix> <gray>You <green>activated <gray>the chat!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_TRY_TO_WRITE",
            "<prefix> <gray>The global mute is activated!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_STATUS_ENABLED",
            "<prefix> <gray>The global mute is <green>activated<gray>!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_STATUS_DISABLED",
            "<prefix> <gray>The global mute is <green>disabled<gray>!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ALREADY_ENABLED",
            "<prefix> <gray>The global mute is already <green>activated<gray>!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ALREADY_DISABLED",
            "<prefix> <gray>The global mute is already <green>disabled<gray>!"
        )
        ymlFile.addDefault(
            "SPEED_CHANGE_SELF",
            "<gray>Your speed got changed to <green><speed><gray>!"
        )
        ymlFile.addDefault(
            "SPEED_CHANGE_OTHER",
            "<gray>The speed of <yellow><player> <gray>got changed to <green><speed><gray>!"
        )
        ymlFile.addDefault(
            "SPEED_RESET_SELF",
            "<gray>Your speed got reset!"
        )
        ymlFile.addDefault(
            "SPEED_RESET_OTHER",
            "<gray>The speed of <yellow><player> <gray>got reset!"
        )
        ymlFile.addDefault(
            "ADVANCEMENT_ANNOUNCEMENT",
            "<white><player> has made the advancement <advancement>"
        )
        ymlFile.addDefault(
            "VANISH_ACTIVATE",
            "<prefix> <gray>Vanish is now activated!"
        )
        ymlFile.addDefault(
            "VANISH_DEACTIVATE",
            "<prefix> <gray>Vanish is now deactivated!"
        )
        ymlFile.addDefault(
            "VANISH_REFRESH",
            "<prefix> <gray>Your vanish settings got refreshed!"
        )
        ymlFile.addDefault(
            "VANISH_REFRESH_OTHER",
            "<prefix> <gray>The vanish settings of <yellow><player> <gray>got refreshed!"
        )


        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}