package yv.tils.smp.utils.configs.language

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class de_yml {
    private var file = File(YVtils.instance.dataFolder.path + "/language", "de.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault(
            "Language",
            "DE"
        )
        ymlFile.addDefault(
            "#1",
            "Bitte benutze immer alle bereits angegebenen Variablen, da ansonsten das Sprachen-System nicht korrekt funktioniert! - Variablen sind daran zu erkennen, dass sie mit Prozent Zeichen umhüllt sind (z.B. %prefix%)."
        )
        ymlFile.addDefault(
            "START_MESSAGE",
            "%prefix% §aPlugin startet!"
        )
        ymlFile.addDefault(
            "START_COMPLETED_MESSAGE",
            "%prefix% §2YVtils-SMP Start ist abgeschlossen!"
        )
        ymlFile.addDefault(
            "STOP_MESSAGE",
            "%prefix% §cPlugin wird gestoppt!"
        )
        ymlFile.addDefault(
            "STOP_COMPLETED_MESSAGE",
            "%prefix% §4YVtils-SMP wurde gestoppt!"
        )

        ymlFile.addDefault(
            "PLUGIN_UP_TO_DATE",
            "%prefix% §fDas Plugin hat keine Updates zur Verfügung!"
        )
        ymlFile.addDefault(
            "PLUGIN_UPDATE_AVAILABLE",
            "%prefix% §eDie Plugin Version %newversion% ist nun verfügbar. Der Server nutzt noch %oldversion%! Lade die neue Version über %link% herunter."
        )

        ymlFile.addDefault(
            "PLAYER_PLUGIN_UPDATE_AVAILABLE",
            "%prefix% §fUpdate verfügbar:\\n§eVerwendete Version: §7%oldversion%\\n§eNeueste Version: §7%newversion%\\n§eDownload: §7%link%"
        )

        ymlFile.addDefault(
            "UNKNOWN_TIME_FORMAT",
            "Dieses Zeit Format steht nicht zur Auswahl!"
        )

        ymlFile.addDefault(
            "WHITELIST_EMPTY",
            "Die Whitelist ist leer!"
        )

        ymlFile.addDefault(
            "PLAYER_NOT_ONLINE",
            "Dieser Spieler ist nicht online!"
        )

        ymlFile.addDefault(
            "PLAYER_UNKNOWN",
            "%prefix% §4Unbekannter Spieler"
        )
        ymlFile.addDefault(
            "PLAYER_ALREADY_BANNED",
            "Dieser Spieler ist bereits gebannt!"
        )
        ymlFile.addDefault(
            "MISSING_PERMISSION",
            "§cFehlende Berechtigung:"
        )

        ymlFile.addDefault("SPAWN_ELYTRA_BOOST",
            "§fUm dich zu boosten drücke"
        )

        ymlFile.addDefault(
            "SMP_START_MESSAGE",
            "§dViel Spaß bei %projectname%!"
        )
        ymlFile.addDefault(
            "SMP_ALREADY_STARTED",
            "§dDieses SMP wurde bereits gestartet!"
        )

        ymlFile.addDefault(
            "FLY_COMMAND_ENABLE",
            "§7Du kannst nun fliegen!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_DISABLE",
            "§7Du kannst nun nicht mehr fliegen!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_ENABLE_OTHER",
            "§7%player% kann nun fliegen!"
        )


        ymlFile.addDefault(
            "FLY_COMMAND_DISABLE_OTHER",
            "§7%player% kann nun nicht mehr fliegen!"
        )

        ymlFile.addDefault(
            "HEAL_PLAYER_HEALED",
            "§7Du wurdest §8§lgeheilt!"
        )
        ymlFile.addDefault(
            "HEAL_OTHER_PLAYER_HEALED",
            "§7%player% wurde §8§lgeheilt!"
        )

        ymlFile.addDefault(
            "GODMODE_COMMAND_ENABLE",
            "§7God Mode ist nun §8aktiviert§7!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_DISABLE",
            "§7God Mode ist nun §8deaktiviert§7!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_ENABLE_OTHER",
            "§7%player% ist nun im God Mode!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_DISABLE_OTHER",
            "§7%player% ist nun nicht mehr im God Mode!"
        )

        ymlFile.addDefault(
            "GAMEMODE_SWITCH",
            "§7Dein Spielmodus wurde zu §a%gamemode% §7geändert!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_ALREADY_IN_THIS_GAMEMODE",
            "§cDu bist bereits in diesem Spielmodus!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_OTHER",
            "§7Der Spielmodus von %player% wurde zu §a%gamemode% §7geändert!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_ALREADY_IN_THIS_GAMEMODE_OTHER",
            "§c%player% ist bereits in diesem Spielmodus!"
        )

        ymlFile.addDefault(
            "MSG_NOTE",
            "§e[§cNotiz§e]§f %message%"
        )
        ymlFile.addDefault(
            "MSG_PLAYER_WENT_OFFLINE",
            "%prefix% §4Der Spieler mit dem du zuletzt geschrieben hast ist offline gegangen!"
        )
        ymlFile.addDefault(
            "MSG_HAVENT_MESSAGED_A_PLAYER",
            "%prefix% §4Du hast noch niemandem geschrieben!"
        )

        ymlFile.addDefault(
            "MOD_NO_REASON",
            "Kein Grund war angegeben!"
        )
        ymlFile.addDefault(
            "MOD_PLAYER_NOT_BANNED",
            "%prefix% §8%player% §7ist nicht gebannt!"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_KICK",
            "%prefix% §8%player% §7wurde von §8%moderator% §7gekickt! Grund: §8%reason%"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_BAN",
            "%prefix% §8%player% §7wurde von §8%moderator% §7gebannt! Grund: §8%reason%"
        )

        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_TEMPBAN",
            "%prefix% §8%player% §7wurde von §8%moderator%§7 getempbannt! Grund: §8%reason%§7, Dauer: §8%duration%"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_UNBAN",
            "%prefix% §8%player% §7wurde von §8%moderator% §7entbannt!"
        )

        ymlFile.addDefault(
            "MAINTENANCE_PLAYER_NOT_ALLOWED_TO_JOIN_KICK_MESSAGE",
            "Du bist nicht erlaubt, den Server während Wartungsarbeiten zu betreten!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_COMMAND_DEACTIVATE",
            "§7Der Maintenance Modus ist nun §adeaktiviert§7!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_COMMAND_ACTIVATE",
            "§7Der Maintenance Modus ist nun §aaktiviert§7!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_ALREADY_DEACTIVATED",
            "§7Der Maintenance Modus ist bereits §adeaktiviert§7!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_ALREADY_ACTIVATED",
            "§7Der Maintenance Modus ist bereits §aaktiviert§7!"
        )

        ymlFile.addDefault(
            "MAINTENANCE_ILLEGAL_CONFIG_VALUE",
            "Dieser Config Wert ist nicht zulässig!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_STATUS_ENABLED",
            "%prefix% §7Der Maintenance Modus ist §aaktiviert§7!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_STATUS_DISABLED",
            "%prefix% §7Der Maintenance Modus ist §adeaktiviert§7!"
        )

        ymlFile.addDefault(
            "COMMAND_USAGE",
            "§7Benutze:"
        )
        ymlFile.addDefault(
            "COMMAND_REPLACE_COPY_COMMAND_TO_CLIPBOARD",
            "§7Klicke um es zu kopieren!"
        )
        ymlFile.addDefault(
            "COMMAND_REPLACE_NEW_COMMAND_INFO",
            "§7Bitte benutze: %command%"
        )

        ymlFile.addDefault(
            "TAB_COMPLETER_MOD_COMMAND_DURATION",
            "Dauer"
        )
        ymlFile.addDefault(
            "TAB_COMPLETER_MOD_COMMAND_REASON",
            "[Grund]"
        )

        ymlFile.addDefault(
            "MODULE_DISCORD_NO_BOT_TOKEN_GIVEN",
            "Es wurde kein Bot Token erkannt! Deaktiviere das Bot Modul oder trage einen Token ein."
        )

        ymlFile.addDefault(
            "MODULE_DISCORD_STARTUP_FAILED",
            "Bot Start ist fehlgeschlagen! Bitte überprüfe deine Konfigurationen"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_STARTUP_FINISHED",
            "Bot Start war erfolgreich! Der Bot sollte nun Online sein!"
        )

        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_CHANGE",
            "Discord User '%discorduser%' hat seinen gewhitelisteten Account von '%oldname%' zu '%newname%' geändert"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_ADD",
            "Discord User '%discorduser%' hat seinen Minecraft Account '%name%' zur Whitelist hinzugefügt"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_WRONG",
            "Discord User '%discorduser%' hat versucht den Minecraft Account '%name%' zur Whitelist hinzuzufügen, aber es ist fehlgeschlagen! -> Account existiert nicht"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_SERVERERROR_CHECK_INPUT",
            "Discord User '%discorduser%' hat versucht den Minecraft Account '%name%' zur Whitelist hinzuzufügen, aber es ist fehlgeschlagen! -> Überprüfungsserver sind nicht erreichbar"
        )

        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_CHANGE",
            "Du hast erfolgreich deinen gewhitelisteten Account geändert!"
        )

        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_CHANGE",
            "%oldname% -> %newname%"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_ADD",
            "Du hast deinen Minecraft Account erfolgreich zur Whitelist hinzugefügt!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_ADD",
            "Account Name: %accountname%"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_NOTEXISTING",
            "Dieser Minecraft Account existiert nicht!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_NOTEXISTING",
            "Account Name: %accountname% • Überprüfe den Namen und versuche es erneut! Wenn du denkst, dass es ein Fehler ist, kontaktiere den Developer oder die Server Administration, damit sie es dem Developer mitteilen können!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_SERVER_ERROR",
            "Überprüfungsserver sind nicht erreichbar!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_SERVER_ERROR",
            "Account Name: %accountname% • Versuche es in ein paar Minuten/Stunden erneut! Wenn dieser Fehler bestehen bleibt, kontaktiere den Developer!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_UNALLOWED_CHARACTERS",
            "Dieser Minecraft Account existiert nicht!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_UNALLOWED_CHARACTERS",
            "Account Name: %accountname% • Du hast unerlaubte Zeichen verwendet! Erlaubt sind: a-z; A-Z; 0-9; _"
        )

        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_ACCOUNT_ALREADY_WHITELISTED",
            "Account ist bereits auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_ACCOUNT_ALREADY_WHITELISTED",
            "Account Name: %accountname% • Dieser Account ist bereits auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_TITLE",
            "Whitelist Check"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_WHITELISTED_DESC",
            "Account Name: %mcname% • Dieser Account ist als '%dcname%' auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_NOT_WHITELISTED_DESC",
            "Account Name: %mcname% • Dieser Account ist nicht auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_ADD_TITLE",
            "Du hast erfolgreich den Minecraft Account gewhitelistet!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_ADD_DESC",
            "Minecraft Account: %mcname% • Discord Account: %dcName%"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REPLACE_TITLE",
            "Du hast erfolgreich den gewhitelisteten Account von %dcname% geändert!"
        )

        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REPLACE_DESC",
            "%oldname% -> %newname%"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVE_TITLE",
            "Welchen Account möchtest du von der whitelist entfernen?"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVE_DESC",
            "￬ Wähle ihn unten aus ￬"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVED_TITLE",
            "Du hast erfolgreich den Account von %mcname% (%dcname%) von der whitelist entfernt!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVED_DESC",
            "Möchtest du noch einen Account entfernen? \\n ￬ Wähle ihn unten aus ￬"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_ADD",
            "Discord User '%discorduser%' hat den Minecraft Account '%mcname%' (verbunden mit '%dcname%') zur whitelist hinzugefügt"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_CHANGE",
            "Discord User '%discorduser%' hat den gewhitelisteten Account von '%dcname%' von '%oldname%' zu '%newname%' geändert"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_REMOVE",
            "Discord User '%discorduser%' hat den Minecraft Account '%mcname%' (verbunden mit '%dcname%') von der whitelist entfernt"
        )

        ymlFile.addDefault(
            "EMBED_CMD_ROLE_ADD_ERROR_TITLE",
            "Es ist ein Fehler beim hinzufügen aufgetreten!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_ROLE_ADD_ERROR_DESC",
            "Die Rolle, welche dem User gegeben werden sollte (%role%), konnte vom Bot wegen falscher Hierarchie der Rollen nicht hinzugefügt werden! \\nBitte setze die Rolle vom Bot über die Rolle welche vergeben werden soll."
        )

        ymlFile.addDefault(
            "MODULE_STATUS_OTHER_PLAYER_HAS_NO_STATUS",
            "Dieser Spieler hat keinen Status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_OTHER_UNALLOWED",
            "§cDu kannst nicht den Status von anderen Spieler löschen!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_OTHER_CLEARED",
            "§7Du hast erfolgreich den Status von §e%player% §7gelöscht!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_CLEARED",
            "§7Du hast erfolgreich deinen Status §7gelöscht!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_PLAYER_HAS_NO_STATUS",
            "§7Du hast keinen Status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CUSTOM_STATUS_TOO_LONG",
            "§cDieser custom Status ist zu lang!"
        )

        ymlFile.addDefault(
            "MODULE_STATUS_NOT_ALLOWED_TO_SET_CUSTOM_STATUS",
            "§7Du bist nicht berechtigt dir einen eigenen Status zu setzen!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_NO_DEFAULT_STATUS",
            "§7Das ist kein Default Status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_SET",
            "§7Du hast dir den Status %status% §7gesetzt!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_SELECTED_STATUS_JOIN_ANNOUNCEMENT",
            "§7Der Status '%status%§7' ist gesetzt!"
        )

        ymlFile.addDefault(
            "MODULE_CCR_ACCEPT_RECIPE",
            "§aCrafting Rezept akzeptieren"
        )

        ymlFile.addDefault(
            "MODULE_INVSEE_INVENTORY",
            "§eInventar von %player%"
        )
        ymlFile.addDefault(
            "MODULE_INVSEE_ENDERCHEST",
            "§5Enderchest von %player%"
        )

        ymlFile.addDefault(
            "PLAYER_ARGUMENT_MISSING",
            "§cUm diesen Command hier auszuführen, musst du einen Spieler angeben!"
        )

        ymlFile.addDefault(
            "GLOBALMUTE_ENABLE_ANNOUNCEMENT",
            "%prefix% §7Der Globalmute wurde §aaktiviert§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_DISABLE_ANNOUNCEMENT",
            "%prefix% §7Der Globalmute wurde §adeaktiviert§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ENABLE_FEEDBACK",
            "%prefix% §7Du hast den Chat §adeaktiviert§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_DISABLE_FEEDBACK",
            "%prefix% §7Du hast den Chat §aaktiviert§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_TRY_TO_WRITE",
            "%prefix% §7Der Globalmute ist aktiviert!"
        )

        ymlFile.addDefault(
            "GLOBALMUTE_STATUS_ENABLED",
            "%prefix% §7Der Globalmute ist §aaktiviert§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_STATUS_DISABLED",
            "%prefix% §7Der Globalmute ist §adeaktiviert§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ALREADY_ENABLED",
            "%prefix% §7Der Globalmute ist bereits §aaktiviert§7!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ALREADY_DISABLED",
            "%prefix% §7Der Globalmute ist bereits §adeaktiviert§7!"
        )

        ymlFile.addDefault(
            "SPEED_CHANGE_SELF",
            "§7Deine Geschwindigkeit wurde zu §a%speed%§7 geändert!"
        )
        ymlFile.addDefault(
            "SPEED_CHANGE_OTHER",
            "§7Die Geschwindigkeit von §e%player% §7wurde zu §a%speed%§7 geändert!"
        )
        ymlFile.addDefault(
            "SPEED_RESET_SELF",
            "§7Deine Geschwindigkeit wurde zurückgesetzt!"
        )
        ymlFile.addDefault(
            "SPEED_RESET_OTHER",
            "§7Die Geschwindigkeit von §e%player% §7wurde zurückgesetzt!"
        )
        ymlFile.addDefault(
            "ADVANCEMENT_ANNOUNCEMENT",
            "§f%player% hat den Fortschritt %advancement% §ferzielt"
        )
        ymlFile.addDefault(
            "VANISH_DEACTIVATE",
            "%prefix% §7Vanish ist nun deaktiviert!"
        )
        ymlFile.addDefault(
            "VANISH_ACTIVATE",
            "%prefix% §7Vanish ist nun aktiviert!"
        )
        ymlFile.addDefault(
            "VANISH_REFRESH",
            "%prefix% §7Deine Vanish Einstellungen wurde aktualisiert!"
        )
        ymlFile.addDefault(
            "VANISH_REFRESH_OTHER",
            "%prefix% §7Die Vanish Einstellungen von §e%player% §7wurden aktualisiert!"
        )

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}