package yv.tils.smp.utils.configs.language

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class en_yml {
    private var file = File(YVtils.instance.dataFolder.path + "/language", "en.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    //TODO: Reformat Placeholders PREFIX -> %prefix%

    fun strings() {
        ymlFile.addDefault(
            "Language",
            "EN"
        )
        ymlFile.addDefault(
            "#1",
            "Please always use given variables! You can recognize them by looking for full capslock words"
        )
        ymlFile.addDefault(
            "START_MESSAGE",
            "PREFIX §aPlugin is starting!"
        )
        ymlFile.addDefault(
            "START_COMPLETED_MESSAGE",
            "PREFIX §2Plugin has started successfully!"
        )
        ymlFile.addDefault(
            "STOP_MESSAGE",
            "PREFIX §cPlugin is getting stopped!"
        )
        ymlFile.addDefault(
            "STOP_COMPLETED_MESSAGE",
            "PREFIX §4Plugin got stopped!"
        )
        ymlFile.addDefault(
            "PLUGIN_UP_TO_DATE",
            "PREFIX §fThe Plugin has no Updates available!"
        )
        ymlFile.addDefault(
            "PLUGIN_UPDATE_AVAILABLE",
            "PREFIX §eThe Plugin Version NEWVERSION is now available. The Server is using OLDVERSION. Download the newest version here LINK"
        )

        ymlFile.addDefault(
            "PLAYER_PLUGIN_UPDATE_AVAILABLE",
            "PREFIX §fUpdate available:\\n§eRunning Version: §7OLDVERSION\\n§eNewest Version: §7NEWVERSION\\n§eDownload: §7LINK"
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
            "PREFIX §4Unkown Player"
        )
        ymlFile.addDefault(
            "PLAYER_ALREADY_BANNED",
            "This player is already banned!"
        )
        ymlFile.addDefault(
            "MISSING_PERMISSION",
            "§cMissing Permission:"
        )
        ymlFile.addDefault(
            "SPAWN_ELYTRA_BOOST",
            "§fTo boost yourself press"
        )
        ymlFile.addDefault(
            "SMP_START_MESSAGE",
            "§dHave fun at project name!"
        )
        ymlFile.addDefault(
            "SMP_ALREADY_STARTED",
            "§dThis SMP has already started!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_ENABLE",
            "§7You can fly now!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_DISABLE",
            "§7You can't fly anymore!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_ENABLE_OTHER",
            "§7PLAYER can now fly!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_DISABLE_OTHER",
            "§7PLAYER can't fly anymore!"
        )
        ymlFile.addDefault(
            "HEAL_PLAYER_HEALED",
            "§7You got §8§lhealed!"
        )
        ymlFile.addDefault(
            "HEAL_OTHER_PLAYER_HEALED",
            "§7PLAYER got §8§lhealed!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_ENABLE",
            "§7God Mode is now §8activated§7!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_DISABLE",
            "§7God Mode is now §8deactivated§7!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_ENABLE_OTHER",
            "§7PLAYER is now in the God Mode!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_DISABLE_OTHER",
            "§7PLAYER is not in the God Mode anymore!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_SURVIVAL",
            "§7Your game mode got changed to §aSurvival§7!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_CREATIVE",
            "§7Your game mode got changed to §aCreative§7!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_ADVENTURE",
            "§7Your gamemode got changed to §aAdventure§7!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_SPECTATOR",
            "§7Your game mode got changed to §aSpectator§7!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_SURVIVAL_OTHER",
            "§7The game mode of PLAYER got changed to §aSurvival§7!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_CREATIVE_OTHER",
            "§7The game mode of PLAYER got changed to §aCreative§7!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_ADVENTURE_OTHER",
            "§7The game mode of PLAYER got changed to §aAdventure§7!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_SPECTATOR_OTHER",
            "§7The game mode of PLAYER got changed to §aSpectator§7!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_ALREADY_IN_THIS_GAMEMODE",
            "§cYou are already in this Gamemode!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_ALREADY_IN_THIS_GAMEMODE_OTHER",
            "§cPLAYER is already in this game mode!"
        )
        ymlFile.addDefault(
            "MSG_NOTE",
            "§e[§cNote§e]§f MESSAGE"
        )
        ymlFile.addDefault(
            "MSG_PLAYER_WENT_OFFLINE",
            "PREFIX §4The player with which you have written, has gone offline!"
        )
        ymlFile.addDefault(
            "MSG_HAVENT_MESSAGED_A_PLAYER",
            "PREFIX §4You haven't written to anybody yet!"
        )
        ymlFile.addDefault(
            "MOD_NO_REASON",
            "No Reason was given!"
        )
        ymlFile.addDefault(
            "MOD_PLAYER_NOT_BANNED",
            "PREFIX §8PLAYER §7 is not banned!"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_KICK",
            "'PREFIX §8PLAYER §7got kicked from §8MODERATOR§7! Reason"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_BAN",
            "'PREFIX §8PLAYER §7got banned from §8MODERATOR§7! Reason"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_TEMPBAN",
            "'PREFIX §8PLAYER §7got banned temporary from §8MODERATOR§7! Reason"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_UNBAN",
            "PREFIX §8PLAYER §7got unbanned from §8MODERATOR§7!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_PLAYER_NOT_ALLOWED_TO_JOIN_KICK_MESSAGE",
            "You are not allowed to join the server while maintenance works!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_COMMAND_DEACTIVATE",
            "§7The maintenance mode is now §adeactivated§7!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_COMMAND_ACTIVATE",
            "§7The maintenance mode is now §aactivated§7!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_ALREADY_DEACTIVATED",
            "§7The maintenance mode is already §adeactivated§7!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_ALREADY_ACTIVATED",
            "§7The maintenance mode is already §aactivated§7!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_ILLEGAL_CONFIG_VALUE",
            "This config value is not valid!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_STATUS_ENABLED",
            "§7The maintenance mode is §aactivated§7!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_STATUS_DISABLED",
            "§7The maintenance mode is §adactivated§7!"
        )
        ymlFile.addDefault(
            "COMMAND_USAGE",
            "§7Use:"
        )
        ymlFile.addDefault(
            "COMMAND_REPLACE_COPY_COMMAND_TO_CLIPBOARD",
            "§7Click to copy!"
        )
        ymlFile.addDefault(
            "COMMAND_REPLACE_NEW_COMMAND_INFO",
            "§7Please use: COMMAND"
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
            "Discord user 'DISCORDUSER' has changed his whitelisted account from 'OLDNAME' to 'NEWNAME'"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_ADD",
            "Discord user 'DISCORDUSER' has added his Minecraft account 'NAME' to the whitelist"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_WRONG",
            "Discord user 'DISCORDUSER' has tried to whitelist the Minecraft account 'NAME', but it failed! -> Account does not exist"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_SERVERERROR_CHECK_INPUT",
            "Discord user 'DISCORDUSER' has tried to whitelist the Minecraft account 'NAME', but it failed! -> Authentication server is not reachable"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_CHANGE",
            "You successfully changed your Minecraft account!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_CHANGE",
            "OLDNAME -> NEWNAME"
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
            "You successfully changed the whitelisted account of DCNAME!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REPLACE_DESC",
            "OLDNAME -> NEWNAME"
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
            "You successfully removed the Minecraft account MCNAME (DCNAME) from the whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVED_DESC",
            "Do you want to remove a second account? \\n ￬ Choose it down here ￬"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_ADD",
            "Discord user 'DISCORDUSER' has whitelisted the Minecraft account 'MCNAME' (linked with 'DCNAME')"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_CHANGE",
            "Discord user 'DISCORDUSER' has changed the linked account from 'DCNAME' from 'OLDNAME' to 'NEWNAME'"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_REMOVE",
            "Discord user 'DISCORDUSER' has removed the Minecraft account 'MCNAME' (linked with 'DCNAME') from the whitelist"
        )
        ymlFile.addDefault(
            "EMBED_CMD_ROLE_ADD_ERROR_TITLE",
            "There occurred an error while adding the role!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_ROLE_ADD_ERROR_DESC",
            "The role that should be given (ROLE), could not be given to the user because of the wrong hierarchy! Please set the bot role over the role which should be given to the user."
        )
        ymlFile.addDefault(
            "MODULE_STATUS_OTHER_PLAYER_HAS_NO_STATUS",
            "This player has no status set!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_OTHER_UNALLOWED",
            "§cYou can't clear the status of other players!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_OTHER_CLEARED",
            "§7You successfully cleared the status of §ePLAYER §7!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_CLEARED",
            "§7You successfully cleared the status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_PLAYER_HAS_NO_STATUS",
            "§7You have no status set!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CUSTOM_STATUS_TOO_LONG",
            "§cThis custom status is too long!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_NOT_ALLOWED_TO_SET_CUSTOM_STATUS",
            "§7You are missing permissions to set a custom status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_NO_DEFAULT_STATUS",
            "§7This is no default status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_SET",
            "§7You successfully set the status STATUS§7!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_SELECTED_STATUS_JOIN_ANNOUNCEMENT",
            "§7The status 'STATUS§7' is set!"
        )
        ymlFile.addDefault(
            "MODULE_CCR_ACCEPT_RECIPE",
            "§aAccept crafting recipe"
        )
        ymlFile.addDefault(
            "MODULE_INVSEE_INVENTORY",
            "§eInventory of PLAYER"
        )
        ymlFile.addDefault(
            "MODULE_INVSEE_ENDERCHEST",
            "§5Enderchest of PLAYER"
        )
        ymlFile.addDefault(
            "PLAYER_ARGUMENT_MISSING",
            "§cTo use this command here, a player is required!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ENABLE_ANNOUNCEMENT",
            "PREFIX §7The global mute got §aactivated§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_DISABLE_ANNOUNCEMENT",
            "PREFIX §7The global mute got §adeactivated§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ENABLE_FEEDBACK",
            "PREFIX §7You §adeactivated §7the chat!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_DISABLE_FEEDBACK",
            "PREFIX §7You §aactivated §7the chat!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_TRY_TO_WRITE",
            "PREFIX §7The global mute is activated!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_STATUS_ENABLED",
            "PREFIX §7The global mute is §aactivated§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_STATUS_DISABLED",
            "PREFIX §7The global mute is §adisabled§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ALREADY_ENABLED",
            "PREFIX §7The global mute is already §aactivated§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ALREADY_DISABLED",
            "PREFIX §7The global mute is already §adisabled§7!"
        )
        ymlFile.addDefault(
            "SPEED_CHANGE_SELF",
            "§7Your speed got changed to §aSPEED§7!"
        )
        ymlFile.addDefault(
            "SPEED_CHANGE_OTHER",
            "§7The speed of §ePLAYER §7got changed to §aSPEED§7!"
        )
        ymlFile.addDefault(
            "SPEED_RESET_SELF",
            "§7Your speed got reset!"
        )
        ymlFile.addDefault(
            "SPEED_RESET_OTHER",
            "§7The speed of §ePLAYER §7got reset!"
        )
        ymlFile.addDefault(
            "ADVANCEMENT_ANNOUNCEMENT",
            "§fPLAYER has made the advancement ADVANCEMENT"
        )
        ymlFile.addDefault(
            "VANISH_ACTIVATE",
            "PREFIX §7Vanish is now activated!"
        )
        ymlFile.addDefault(
            "VANISH_DEACTIVATE",
            "PREFIX §7Vanish is now deactivated!"
        )
        ymlFile.addDefault(
            "VANISH_REFRESH",
            "PREFIX §7Your vanish settings got refreshed!"
        )
        ymlFile.addDefault(
            "VANISH_REFRESH_OTHER",
            "PREFIX §7The vanish settings of §ePLAYER §7got refreshed!"
        )


        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}