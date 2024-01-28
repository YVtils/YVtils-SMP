package yv.tils.smp.utils.configs.language

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class de_yml {
    private var file = File(YVtils.instance.dataFolder.path + "/language", "de.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvnetwork.de/yvtils-smp/docs")
        ymlFile.addDefault(
            "Language",
            "DE"
        )
        ymlFile.addDefault(
            "#1",
            "Bitte benutze immer alle bereits angegebenen Variablen, da ansonsten das Sprachen-System nicht korrekt funktioniert! - Variablen sind daran zu erkennen, dass sie mit Prozent Zeichen umhüllt sind (z.B. <prefix>)."
        )
        ymlFile.addDefault(
            "START_MESSAGE",
            "<prefix> <green>Plugin startet!"
        )
        ymlFile.addDefault(
            "START_COMPLETED_MESSAGE",
            "<prefix> <dark_green>YVtils-SMP Start ist abgeschlossen!"
        )
        ymlFile.addDefault(
            "STOP_MESSAGE",
            "<prefix> <red>Plugin wird gestoppt!"
        )
        ymlFile.addDefault(
            "STOP_COMPLETED_MESSAGE",
            "<prefix> <dark_red>YVtils-SMP wurde gestoppt!"
        )

        ymlFile.addDefault(
            "PLUGIN_UP_TO_DATE",
            "<prefix> <white>Das Plugin hat keine Updates zur Verfügung!"
        )
        ymlFile.addDefault(
            "PLUGIN_UPDATE_AVAILABLE",
            "<prefix> <yellow>Die Plugin Version <newversion> ist nun verfügbar. Der Server nutzt noch <oldversion>! Lade die neue Version über <link> herunter."
        )

        ymlFile.addDefault(
            "PLAYER_PLUGIN_UPDATE_AVAILABLE",
            "<prefix> <white>Update verfügbar:<newline><yellow>Verwendete Version: <gray><oldversion><newline><yellow>Neueste Version: <gray><newversion><newline><yellow>Download: <gray><link>"
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
            "<prefix> <dark_red>Unbekannter Spieler"
        )
        ymlFile.addDefault(
            "PLAYER_ALREADY_BANNED",
            "Dieser Spieler ist bereits gebannt!"
        )
        ymlFile.addDefault(
            "MISSING_PERMISSION",
            "<red>Fehlende Berechtigung:"
        )

        ymlFile.addDefault("SPAWN_ELYTRA_BOOST",
            "<white>Um dich zu boosten drücke"
        )

        ymlFile.addDefault(
            "SMP_START_MESSAGE",
            "<light_purple>Viel Spaß bei <projectname>!"
        )
        ymlFile.addDefault(
            "SMP_ALREADY_STARTED",
            "<light_purple>Dieses SMP wurde bereits gestartet!"
        )

        ymlFile.addDefault(
            "FLY_COMMAND_ENABLE",
            "<gray>Du kannst nun fliegen!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_DISABLE",
            "<gray>Du kannst nun nicht mehr fliegen!"
        )
        ymlFile.addDefault(
            "FLY_COMMAND_ENABLE_OTHER",
            "<gray><player> kann nun fliegen!"
        )


        ymlFile.addDefault(
            "FLY_COMMAND_DISABLE_OTHER",
            "<gray><player> kann nun nicht mehr fliegen!"
        )

        ymlFile.addDefault(
            "HEAL_PLAYER_HEALED",
            "<gray>Du wurdest <dark_gray><bold>geheilt!"
        )
        ymlFile.addDefault(
            "HEAL_OTHER_PLAYER_HEALED",
            "<gray><player> wurde <dark_gray><bold>geheilt!"
        )

        ymlFile.addDefault(
            "GODMODE_COMMAND_ENABLE",
            "<gray>God Mode ist nun <dark_gray>aktiviert<gray>!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_DISABLE",
            "<gray>God Mode ist nun <dark_gray>deaktiviert<gray>!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_ENABLE_OTHER",
            "<gray><player> ist nun im God Mode!"
        )
        ymlFile.addDefault(
            "GODMODE_COMMAND_DISABLE_OTHER",
            "<gray><player> ist nun nicht mehr im God Mode!"
        )

        ymlFile.addDefault(
            "GAMEMODE_SWITCH",
            "<gray>Dein Spielmodus wurde zu <green><gamemode> <gray>geändert!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_ALREADY_IN_THIS_GAMEMODE",
            "<red>Du bist bereits in diesem Spielmodus!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_OTHER",
            "<gray>Der Spielmodus von <player> wurde zu <green><gamemode> <gray>geändert!"
        )
        ymlFile.addDefault(
            "GAMEMODE_SWITCH_ALREADY_IN_THIS_GAMEMODE_OTHER",
            "<red><player> ist bereits in diesem Spielmodus!"
        )

        ymlFile.addDefault(
            "MSG_NOTE",
            "<yellow>[<red>Notiz<yellow>]<white> <message>"
        )
        ymlFile.addDefault(
            "MSG_PLAYER_WENT_OFFLINE",
            "<prefix> <dark_red>Der Spieler mit dem du zuletzt geschrieben hast ist offline gegangen!"
        )
        ymlFile.addDefault(
            "MSG_HAVENT_MESSAGED_A_PLAYER",
            "<prefix> <dark_red>Du hast noch niemandem geschrieben!"
        )

        ymlFile.addDefault(
            "MOD_NO_REASON",
            "Kein Grund war angegeben!"
        )
        ymlFile.addDefault(
            "MOD_PLAYER_NOT_BANNED",
            "<prefix> <dark_gray><player> <gray>ist nicht gebannt!"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_KICK",
            "<prefix> <dark_gray><player> <gray>wurde von <dark_gray><moderator> <gray>gekickt! Grund: <dark_gray><reason>"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_BAN",
            "<prefix> <dark_gray><player> <gray>wurde von <dark_gray><moderator> <gray>gebannt! Grund: <dark_gray><reason>"
        )

        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_TEMPBAN",
            "<prefix> <dark_gray><player> <gray>wurde von <dark_gray><moderator><gray> getempbannt! Grund: <dark_gray><reason><gray>, Dauer: <dark_gray><duration>"
        )
        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_UNBAN",
            "<prefix> <dark_gray><player> <gray>wurde von <dark_gray><moderator> <gray>entbannt!"
        )

        ymlFile.addDefault(
            "MAINTENANCE_PLAYER_NOT_ALLOWED_TO_JOIN_KICK_MESSAGE",
            "<prefix> Du bist nicht erlaubt, den Server während Wartungsarbeiten zu betreten!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_COMMAND_DEACTIVATE",
            "<prefix> <gray>Der Maintenance Modus ist nun <green>deaktiviert<gray>!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_COMMAND_ACTIVATE",
            "<prefix> <gray>Der Maintenance Modus ist nun <green>aktiviert<gray>!"
        )
        ymlFile.addDefault(
            "MAINTENANCE_ALREADY_STATE",
            "<prefix> <gray>Der Maintenance Modus ist bereits in diesem Status!"
        )

        ymlFile.addDefault(
            "MAINTENANCE_ILLEGAL_CONFIG_VALUE",
            "<prefix> Dieser Config Wert ist nicht zulässig!"
        )

        ymlFile.addDefault(
            "COMMAND_USAGE",
            "<gray>Benutze:"
        )
        ymlFile.addDefault(
            "COMMAND_REPLACE_COPY_COMMAND_TO_CLIPBOARD",
            "<gray>Klicke um es zu kopieren!"
        )
        ymlFile.addDefault(
            "COMMAND_REPLACE_NEW_COMMAND_INFO",
            "<gray>Bitte benutze: <command>"
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
            "Discord User '<discorduser>' hat seinen gewhitelisteten Account von '<oldname>' zu '<newname>' geändert"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_ADD",
            "Discord User '<discorduser>' hat seinen Minecraft Account '<name>' zur Whitelist hinzugefügt"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_WRONG",
            "Discord User '<discorduser>' hat versucht den Minecraft Account '<name>' zur Whitelist hinzuzufügen, aber es ist fehlgeschlagen! -> Account existiert nicht"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_SERVERERROR_CHECK_INPUT",
            "Discord User '<discorduser>' hat versucht den Minecraft Account '<name>' zur Whitelist hinzuzufügen, aber es ist fehlgeschlagen! -> Überprüfungsserver sind nicht erreichbar"
        )

        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_CHANGE",
            "Du hast erfolgreich deinen gewhitelisteten Account geändert!"
        )

        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_CHANGE",
            "<oldname> -> <newname>"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_ADD",
            "Du hast deinen Minecraft Account erfolgreich zur Whitelist hinzugefügt!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_ADD",
            "Account Name: <accountname>"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_NOTEXISTING",
            "Dieser Minecraft Account existiert nicht!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_NOTEXISTING",
            "Account Name: <accountname> • Überprüfe den Namen und versuche es erneut! Wenn du denkst, dass es ein Fehler ist, kontaktiere den Developer oder die Server Administration, damit sie es dem Developer mitteilen können!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_SERVER_ERROR",
            "Überprüfungsserver sind nicht erreichbar!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_SERVER_ERROR",
            "Account Name: <accountname> • Versuche es in ein paar Minuten/Stunden erneut! Wenn dieser Fehler bestehen bleibt, kontaktiere den Developer!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_UNALLOWED_CHARACTERS",
            "Dieser Minecraft Account existiert nicht!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_UNALLOWED_CHARACTERS",
            "Account Name: <accountname> • Du hast unerlaubte Zeichen verwendet! Erlaubt sind: a-z; A-Z; 0-9; _"
        )

        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_ACCOUNT_ALREADY_WHITELISTED",
            "Account ist bereits auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_ACCOUNT_ALREADY_WHITELISTED",
            "Account Name: <accountname> • Dieser Account ist bereits auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_TITLE",
            "Whitelist Check"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_WHITELISTED_DESC",
            "Account Name: <mcname> • Dieser Account ist als '<dcname>' auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_NOT_WHITELISTED_DESC",
            "Account Name: <mcname> • Dieser Account ist nicht auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_ADD_TITLE",
            "Du hast erfolgreich den Minecraft Account gewhitelistet!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_ADD_DESC",
            "Minecraft Account: <mcname> • Discord Account: <dcName>"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REPLACE_TITLE",
            "Du hast erfolgreich den gewhitelisteten Account von <dcname> geändert!"
        )

        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REPLACE_DESC",
            "<oldname> -> <newname>"
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
            "Du hast erfolgreich den Account von <mcname> (<dcname>) von der whitelist entfernt!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVED_DESC",
            "Möchtest du noch einen Account entfernen? <newline> ￬ Wähle ihn unten aus ￬"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_ADD",
            "Discord User '<discorduser>' hat den Minecraft Account '<mcname>' (verbunden mit '<dcname>') zur whitelist hinzugefügt"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_CHANGE",
            "Discord User '<discorduser>' hat den gewhitelisteten Account von '<dcname>' von '<oldname>' zu '<newname>' geändert"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_REMOVE",
            "Discord User '<discorduser>' hat den Minecraft Account '<mcname>' (verbunden mit '<dcname>') von der whitelist entfernt"
        )

        ymlFile.addDefault(
            "EMBED_CMD_ROLE_ADD_ERROR_TITLE",
            "Es ist ein Fehler beim hinzufügen aufgetreten!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_ROLE_ADD_ERROR_DESC",
            "Die Rolle, welche dem User gegeben werden sollte (<role>), konnte vom Bot wegen falscher Hierarchie der Rollen nicht hinzugefügt werden! <newline>Bitte setze die Rolle vom Bot über die Rolle welche vergeben werden soll."
        )

        ymlFile.addDefault(
            "MODULE_STATUS_OTHER_PLAYER_HAS_NO_STATUS",
            "Dieser Spieler hat keinen Status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_OTHER_UNALLOWED",
            "<red>Du kannst nicht den Status von anderen Spieler löschen!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_OTHER_CLEARED",
            "<gray>Du hast erfolgreich den Status von <yellow><player> <gray>gelöscht!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CLEAR_CLEARED",
            "<gray>Du hast erfolgreich deinen Status <gray>gelöscht!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_PLAYER_HAS_NO_STATUS",
            "<gray>Du hast keinen Status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_CUSTOM_STATUS_TOO_LONG",
            "<red>Dieser custom Status ist zu lang!"
        )

        ymlFile.addDefault(
            "MODULE_STATUS_NOT_ALLOWED_TO_SET_CUSTOM_STATUS",
            "<gray>Du bist nicht berechtigt dir einen eigenen Status zu setzen!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_NO_DEFAULT_STATUS",
            "<gray>Das ist kein Default Status!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_SET",
            "<gray>Du hast dir den Status <status> <gray>gesetzt!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_SELECTED_STATUS_JOIN_ANNOUNCEMENT",
            "<gray>Der Status '<status><gray>' ist gesetzt!"
        )

        ymlFile.addDefault(
            "MODULE_CCR_ACCEPT_RECIPE",
            "<green>Crafting Rezept akzeptieren"
        )

        ymlFile.addDefault(
            "MODULE_INVSEE_INVENTORY",
            "<yellow>Inventar von <player>"
        )
        ymlFile.addDefault(
            "MODULE_INVSEE_ENDERCHEST",
            "<dark_purple>Enderchest von <player>"
        )

        ymlFile.addDefault(
            "PLAYER_ARGUMENT_MISSING",
            "<red>Um diesen Command hier auszuführen, musst du einen Spieler angeben!"
        )

        ymlFile.addDefault(
            "GLOBALMUTE_ENABLE_ANNOUNCEMENT",
            "<prefix> <gray>Der Globalmute wurde <green>aktiviert<gray>!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_DISABLE_ANNOUNCEMENT",
            "<prefix> <gray>Der Globalmute wurde <green>deaktiviert<gray>!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ENABLE_FEEDBACK",
            "<prefix> <gray>Du hast den Chat <green>deaktiviert<gray>!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_DISABLE_FEEDBACK",
            "<prefix> <gray>Du hast den Chat <green>aktiviert<gray>!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_TRY_TO_WRITE",
            "<prefix> <gray>Der Globalmute ist aktiviert!"
        )
        ymlFile.addDefault(
            "GLOBALMUTE_ALREADY_STATE",
            "<prefix> <gray>Der Globalmute ist bereits in diesem Status!"
        )
        ymlFile.addDefault(
            "SPEED_CHANGE_SELF",
            "<gray>Deine Geschwindigkeit wurde zu <green><speed><gray> geändert!"
        )
        ymlFile.addDefault(
            "SPEED_CHANGE_OTHER",
            "<gray>Die Geschwindigkeit von <yellow><player> <gray>wurde zu <green><speed><gray> geändert!"
        )
        ymlFile.addDefault(
            "SPEED_RESET_SELF",
            "<gray>Deine Geschwindigkeit wurde zurückgesetzt!"
        )
        ymlFile.addDefault(
            "SPEED_RESET_OTHER",
            "<gray>Die Geschwindigkeit von <yellow><player> <gray>wurde zurückgesetzt!"
        )
        ymlFile.addDefault(
            "ADVANCEMENT_ANNOUNCEMENT",
            "<white><player> hat den Fortschritt <advancement> <white>erzielt"
        )
        ymlFile.addDefault(
            "VANISH_DEACTIVATE",
            "<prefix> <gray>Vanish ist nun deaktiviert!"
        )
        ymlFile.addDefault(
            "VANISH_ACTIVATE",
            "<prefix> <gray>Vanish ist nun aktiviert!"
        )
        ymlFile.addDefault(
            "VANISH_REFRESH",
            "<prefix> <gray>Deine Vanish Einstellungen wurde aktualisiert!"
        )
        ymlFile.addDefault(
            "VANISH_REFRESH_OTHER",
            "<prefix> <gray>Die Vanish Einstellungen von <yellow><player> <gray>wurden aktualisiert!"
        )

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}