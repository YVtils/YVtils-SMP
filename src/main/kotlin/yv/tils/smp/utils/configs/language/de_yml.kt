package yv.tils.smp.utils.configs.language

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class de_yml {
    private var file = File(YVtils.instance.dataFolder.path + "/language", "de.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://docs.yvtils.net/smp/language/de.yml")
        ymlFile.addDefault(
            "Language",
            "DE"
        )
        ymlFile.addDefault(
            "#1",
            "Bitte benutze immer alle bereits angegebenen Variablen, da ansonsten das Sprachen-System nicht korrekt funktioniert! - Variablen sind daran zu erkennen, dass sie mit kleiner/größer als Zeichen umhüllt sind (z.B. <prefix>)."
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
            "PLUGIN_UPDATE_AVAILABLE_PATCH",
            "<prefix> <yellow>Es ist ein Patch verfügbar! <newline><yellow>Verwendete Version: <gray><oldVersion><newline><yellow>Neueste Version: <gray><newVersion><newline><yellow>Download: <gray><link>"
        )
        ymlFile.addDefault(
            "PLUGIN_UPDATE_AVAILABLE_MINOR",
            "<prefix> <#FF8349>Es ist ein kleines Feature Update verfügbar! <newline><yellow>Verwendete Version: <gray><oldVersion><newline><yellow>Neueste Version: <gray><newVersion><newline><yellow>Download: <gray><link>"
        )
        ymlFile.addDefault(
            "PLUGIN_UPDATE_AVAILABLE_MAJOR",
            "<prefix> <red>Es ist ein Major Update verfügbar! Es wird empfohlen, das Update so bald wie möglich zu installieren! <newline><yellow>Verwendete Version: <gray><oldVersion><newline><yellow>Neueste Version: <gray><newVersion><newline><yellow>Download: <gray><link>"
        )

        ymlFile.addDefault(
            "PLAYER_PLUGIN_UPDATE_AVAILABLE_PATCH",
            "<prefix> <yellow>Ein Patch ist verfügbar! <newline><yellow>Verwendete Version: <gray><oldVersion><newline><yellow>Neueste Version: <gray><newVersion><newline><yellow>Download: <gray><link>"
        )
        ymlFile.addDefault(
            "PLAYER_PLUGIN_UPDATE_AVAILABLE_MINOR",
            "<prefix> <#FF8349>Ein kleines Feature Update ist verfügbar! <newline><yellow>Verwendete Version: <gray><oldVersion><newline><yellow>Neueste Version: <gray><newVersion><newline><yellow>Download: <gray><link>"
        )
        ymlFile.addDefault(
            "PLAYER_PLUGIN_UPDATE_AVAILABLE_MAJOR",
            "<prefix> <red>Ein Major Update ist verfügbar! Es wird empfohlen, das Update so bald wie möglich zu installieren! <newline><yellow>Verwendete Version: <gray><oldVersion><newline><yellow>Neueste Version: <gray><newVersion><newline><yellow>Download: <gray><link>"
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

        ymlFile.addDefault(
            "SPAWN_ELYTRA_BOOST",
            "<white>Benutze <yellow><key><white> um dich zu boosten"
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
            "<green>[<aqua>Notiz<green>]<white> <message>"
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
            "Dieser Spieler ist nicht gebannt!"
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
            "Discord User '<discordUser>' hat seinen gewhitelisteten Account von '<oldName>' zu '<newName>' geändert"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_ADD",
            "Discord User '<discordUser>' hat seinen Minecraft Account '<name>' zur Whitelist hinzugefügt"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_WRONG",
            "Discord User '<discordUser>' hat versucht den Minecraft Account '<name>' zur Whitelist hinzuzufügen, aber es ist fehlgeschlagen! -> Account existiert nicht"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_REGISTERED_NAME_SERVERERROR_CHECK_INPUT",
            "Discord User '<discordUser>' hat versucht den Minecraft Account '<name>' zur Whitelist hinzuzufügen, aber es ist fehlgeschlagen! -> Überprüfungsserver sind nicht erreichbar"
        )

        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_CHANGE",
            "Du hast erfolgreich deinen gewhitelisteten Account geändert!"
        )

        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_CHANGE",
            "<oldName> -> <newName>"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_NAME_ADD",
            "Du hast deinen Minecraft Account erfolgreich zur Whitelist hinzugefügt!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_NAME_ADD",
            "Account Name: <accountName>"
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
            "Account Name: <accountName> • Du hast unerlaubte Zeichen verwendet! Erlaubt sind: a-z; A-Z; 0-9; _"
        )

        ymlFile.addDefault(
            "EMBED_BUILDER_TITLE_ACCOUNT_ALREADY_WHITELISTED",
            "Account ist bereits auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_BUILDER_DESCRIPTION_ACCOUNT_ALREADY_WHITELISTED",
            "Account Name: <accountName> • Dieser Account ist bereits auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_TITLE",
            "Whitelist Check"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_WHITELISTED_DESC",
            "Account Name: <mcName> • Dieser Account ist als '<dcName>' auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_CHECK_NOT_WHITELISTED_DESC",
            "Account Name: <mcName> • Dieser Account ist nicht auf der Whitelist!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_ADD_TITLE",
            "Du hast erfolgreich den Minecraft Account gewhitelistet!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_ADD_DESC",
            "Minecraft Account: <mcName> • Discord Account: <dcName>"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REPLACE_TITLE",
            "Du hast erfolgreich den gewhitelisteten Account von <dcName> geändert!"
        )

        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REPLACE_DESC",
            "<oldName> -> <newName>"
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
            "Du hast erfolgreich den Account von <mcName> (<dcName>) von der whitelist entfernt!"
        )
        ymlFile.addDefault(
            "EMBED_CMD_WHITELIST_REMOVED_DESC",
            "Möchtest du noch einen Account entfernen? <newline> ￬ Wähle ihn unten aus ￬"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_ADD",
            "Discord User '<discordUser>' hat den Minecraft Account '<mcName>' (verbunden mit '<dcName>') zur whitelist hinzugefügt"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_CHANGE",
            "Discord User '<discordUser>' hat den gewhitelisteten Account von '<dcName>' von '<oldName>' zu '<newName>' geändert"
        )
        ymlFile.addDefault(
            "MODULE_DISCORD_CMD_REGISTERED_REMOVE",
            "Discord User '<discordUser>' hat den Minecraft Account '<mcName>' (verbunden mit '<dcName>') von der whitelist entfernt"
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
            "MODULE_STATUS_CLEAR_CONSOLE",
            "<gray>Du musst einen Spieler angeben, um den Status zu löschen!"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_PLAYER_HAS_NO_STATUS",
            "<gray>Du hast keinen Status!"
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
            "<gray>Neuer Status: <white><status>"
        )
        ymlFile.addDefault(
            "MODULE_STATUS_SELECTED_STATUS_JOIN_ANNOUNCEMENT",
            "<gold>Willkommen zurück!<newline><gray>Aktueller Status: <white><status>"
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

        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_MUTE",
            "<prefix> <dark_gray><player> <gray>wurde von <dark_gray><moderator> <gray>gemuted! Grund: <dark_gray><reason>"
        )

        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_TEMPMUTE",
            "<prefix> <dark_gray><player> <gray>wurde von <dark_gray><moderator> <gray>temporär gemuted! Grund: <dark_gray><reason><gray>, Dauer: <dark_gray><duration>"
        )

        ymlFile.addDefault(
            "MOD_ANNOUNCEMENT_UNMUTE",
            "<prefix> <dark_gray><player> <gray>wurde von <dark_gray><moderator> <gray>unmuted!"
        )

        ymlFile.addDefault(
            "PLAYER_ALREADY_MUTED",
            "Dieser Spieler ist bereits gemuted!"
        )

        ymlFile.addDefault(
            "MOD_PLAYER_NOT_MUTED",
            "<prefix> <dark_gray><player> <gray>ist nicht gemuted!"
        )

        ymlFile.addDefault(
            "PLAYER_YOU_ARE_MUTED",
            "<prefix> <gray>Du bist gemuted! Grund: <dark_gray><reason><gray>, Dauer: <dark_gray><duration>"
        )

        ymlFile.addDefault(
            "PLAYER_TRIED_TO_WRITE_WHILE_MUTED",
            "<prefix> <gray><player> hat versucht zu schreiben, während er gemuted war. Nachricht: <message>; Grund: <reason>; Dauer: <duration>"
        )

        ymlFile.addDefault(
            "PLAYER_GOT_MUTED",
            "<prefix> <gray>Du wurdest gemuted! Grund: <dark_gray><reason>"
        )

        ymlFile.addDefault(
            "PLAYER_GOT_TEMPMUTED",
            "<prefix> <gray>Du wurdest temporär gemuted! Grund: <dark_gray><reason><gray>, Dauer: <dark_gray><duration>"
        )

        ymlFile.addDefault(
            "PLAYER_GOT_UNMUTED",
            "<prefix> <gray>Du wurdest entmuted!"
        )

        ymlFile.addDefault(
            "MODULE_STATUS_BLACKLISTED_STATUS",
            "<gray>Dieser Status ist auf der <red>Blacklist<gray>!"
        )

        ymlFile.addDefault(
            "VANISH_TOGGLE_OTHER",
            "<prefix> <gray>Vanish von <yellow><player> <gray>wurde aktualiesiert!"
        )

        ymlFile.addDefault(
            "MODULE_VANISH_VANISH_LORE",
            "<white>Verwende Farbstoff, um das Verschwinden umzuschalten."
        )

        ymlFile.addDefault(
            "MODULE_VANISH_LAYER_LORE",
            "<white>Klicke auf die Karte, um zwischen Ebenen zu wechseln. <newline><white>Klicke auf den Farbstoff, um die Verwendung von Ebenen umzuschalten. <newline><newline><gray>Ebene 1 < Ebene 2 < Ebene 3 | Höhere Ebenen können niedrigere sehen, aber nicht umgekehrt."
        )

        ymlFile.addDefault(
            "MODULE_VANISH_ITEM_PICKUP_LORE",
            "<white>Verwende Farbstoff, um das Aufnehmen von Gegenständen umzuschalten. <newline><white>Wenn aktiviert, kannst du Gegenstände aufnehmen."
        )

        ymlFile.addDefault(
            "MODULE_VANISH_INV_INTERACTION_LORE",
            "<white>Verwende Farbstoff, um die lautlose Inventar interaktion umzuschalten. <newline><white>Wenn aktiviert, werden Kisten für andere Spieler nicht als geöffnet sichtbar sein."
        )

        ymlFile.addDefault(
            "MODULE_VANISH_MOB_TARGET_LORE",
            "<white>Verwende Farbstoff, um das Anvisieren von Kreaturen umzuschalten. <newline><white>Wenn aktiviert, werden Kreaturen dich nicht anvisieren."
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_ADD_BLOCK",
            "<white>Der Block <yellow><block> <white>wurde zur MultiMine Liste hinzugefügt!"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_REMOVE_BLOCK",
            "<white>Der Block <yellow><block> <white>wurde von der MultiMine Liste entfernt!"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_ADD_MULTIPLE",
            "<white>Folgende Blöcke wurden zur MultiMine Liste hinzugefügt: <newline><yellow><blocks>"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_REMOVE_MULTIPLE",
            "<white>Folgende Blöcke wurden von der MultiMine Liste entfernt: <newline><yellow><blocks>"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_NO_BLOCK",
            "<white>Es wurde kein Block angegeben!"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_NO_BLOCK_IN_HAND",
            "<white>Es wurde kein Block in der Hand gefunden!"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_NO_CONTAINER_IN_HAND",
            "<white>Es wurde kein Container in der Hand gefunden!"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_MULTIPLE_CONSOLE",
            "<white>Es können nicht mehrere Blöcke über die Konsole hinzugefügt werden!"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_BLOCK_ALREADY_IN_LIST",
            "<white>Der Block <yellow><block> <white>ist bereits in der Liste!"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_BLOCK_NOT_IN_LIST",
            "<white>Der Block <yellow><block> <white>ist nicht in der Liste!"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_TOGGLE_ACTIVATE",
            "<white>MultiMine wurde <yellow>aktiviert<white>!"
        )

        ymlFile.addDefault(
            "MODULE_MULTIMINE_TOGGLE_DEACTIVATE",
            "<white>MultiMine wurde <yellow>deaktiviert<white>!"
        )

        ymlFile.addDefault(
            "MODULE_WAYPOINT_ALREADY_EXISTS",
            "<white>Der Wegpunkt <yellow><waypoint> <white>existiert bereits!"
        )

        ymlFile.addDefault(
            "MODULE_WAYPOINT_NOT_FOUND",
            "<white>Der Wegpunkt <yellow><waypoint> <white>existiert nicht!"
        )

        ymlFile.addDefault(
            "MODULE_WAYPOINT_CREATED",
            "<white>Der Wegpunkt <yellow><waypoint> <white>wurde erstellt!"
        )

        ymlFile.addDefault(
            "MODULE_WAYPOINT_DELETED",
            "<white>Der Wegpunkt <yellow><waypoint> <white>wurde gelöscht!"
        )

        ymlFile.addDefault(
            "MODULE_WAYPOINT_DELETE_NOT_ALLOWED",
            "<white>Du bist nicht berechtigt, den Wegpunkt <yellow><waypoint> <white>zu löschen!"
        )

        ymlFile.addDefault(
            "MODULE_WAYPOINT_NAVIGATE_STARTED",
            "<white>Die Navigation zum Wegpunkt <yellow><waypoint> <white>wurde gestartet!"
        )

        ymlFile.addDefault(
            "MODULE_WAYPOINT_NAVIGATE_STOPPED",
            "<white>Die Navigation zum Wegpunkt <yellow><waypoint> <white>wurde gestoppt!"
        )

        ymlFile.addDefault(
            "MODULE_WAYPOINT_NAVIGATE_FINISHED",
            "<white>Die Navigation zum Wegpunkt <yellow><waypoint> <white>wurde beendet!"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_PLAYER_HEAD_DISPLAY_NAME",
            "<gold><player>s Kopf"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_LOAD_PLAYER_HEAD_FAILED",
            "<red>Beim laden des Spieler Kopfes ist ein Fehler aufgetreten!"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_INFO_POINT",
            "<yellow> Fusion Items bearbeiten: " +
                    "<newline><gray>    - Links Klick auf ein Item im unteren Inventar, um das Item zum Input hinzuzufügen " +
                    "<newline><gray>    - Rechts Klick auf ein Item im unteren Inventar, um das Item zum Output hinzuzufügen " +
                    "<newline><gray>    - Links Klick auf ein Item im oberen Inventar, um das Item zu bearbeiten " +
                    "<newline><gray>    - Rechts Klick auf ein Item im oberen Inventar, um das Item zu entfernen "
        )

        ymlFile.addDefault(
            "MODULE_FUSION_CHANGE_THUMBNAIL",
            "<green>Thumbnail ändern"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_EDIT_NAME",
            "<gold>Fusion Namen bearbeiten " +
                    "<newline><gray>Aktueller Name: <white><name>" +
                    "<newline><red>'c' zum abbrechen"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_EDIT_DESCRIPTION",
            "<gold>Fusion Beschreibung bearbeiten " +
                    "<newline><gray>Aktuelle Beschreibung: <white><description>" +
                    "<newline><red>'c' zum abbrechen" +
                    "<newline><gray><click:open_url:https://docs.yvtils.net/colorcode/>Color Code Guide</click>"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_UPDATED_NAME",
            "<green>Der Name wurde zu <white><name> <green>geändert!"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_UPDATED_DESCRIPTION",
            "<green>Die Beschreibung wurde zu <white><description> <green>geändert!"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_FILTER",
            "<yellow>Filter"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_FILTER_LORE",
            "<gray>Klicken um den Filter zu ändern"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_EDIT_TAG",
            "<gold>Fusion Tag bearbeiten " +
                    "<newline><gray>Aktueller Tag: <white><tag>" +
                    "<newline><red>'c' zum Abbrechen"
        )

        ymlFile.addDefault(
            "MODULE_FUSION_CREATE_TAG",
            "<gold>Neuen Fusion Tag erstellen " +
                    "<newline><red>'c' zum Abbrechen"
        )

        ymlFile.addDefault(
            "GUI_LEFT_CLICK_EDIT",
            "<green>Links Klick zum bearbeiten"
        )

        ymlFile.addDefault(
            "GUI_RIGHT_CLICK_REMOVE",
            "<red>Rechts Klick zum entfernen"
        )

        ymlFile.addDefault(
            "GUI_PAGE_BACK",
            "<red>Zurück"
        )

        ymlFile.addDefault(
            "PROCESS_CANCELLED",
            "<red>Abgebrochen!"
        )

        ymlFile.addDefault(
            "INPUT_TOO_LONG",
            "<red>Der eingegebene Text ist zu lang!"
        )

        ymlFile.addDefault(
            "ENABLED",
            "AKTIVIERT"
        )

        ymlFile.addDefault(
            "DISABLED",
            "DEAKTIVIERT"
        )

        ymlFile.addDefault(
            "NONE",
            "Keiner"
        )

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}