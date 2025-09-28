package org.eggstudios.togglePVP

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileWriter
import org.bukkit.plugin.java.JavaPlugin
import org.eggstudios.togglePVP.commands.PVPCommand
import java.io.FileReader
import java.util.UUID

class TogglePVP : JavaPlugin() {
    lateinit var pvpCommand: PVPCommand

    override fun onEnable() {
        pvpCommand = PVPCommand()
        registerCommand()
        createTeams()
        loadCooldowns()

        logger.info("TogglePVP has been enabled.")
    }

    override fun onDisable() {
        saveCooldowns()
        logger.info("TogglePVP has been disabled.")
    }

    fun registerCommand() {
        getCommand("pvp")?.setExecutor(pvpCommand)
        logger.info("Commands have been registered successfully.")
    }

    fun createTeams() {
        val scoreboard = Bukkit.getScoreboardManager().mainScoreboard

        if (scoreboard.getTeam("PVP") == null) {
            val pvpTeam = scoreboard.registerNewTeam("PVP")
            pvpTeam.prefix(Component.text("PVP ", NamedTextColor.RED, TextDecoration.BOLD))
        }

        if (scoreboard.getTeam("PVPLO") == null) {
            val pvpLoTeam = scoreboard.registerNewTeam("PVPLO")
            pvpLoTeam.prefix(Component.text("PVPLO", NamedTextColor.DARK_RED, TextDecoration.BOLD))
        }
    }

    private fun saveCooldowns() {
        val yaml = Yaml()
        // Convert UUID keys to String for YAML serialization
        val cooldownToSave = pvpCommand.cooldown.mapKeys { it.key.toString() }
        val file = File(dataFolder, "cooldown.yaml")
        // Ensure parent directories exist
        file.parentFile?.mkdirs()
        FileWriter(file).use { writer ->
            yaml.dump(cooldownToSave, writer)
        }
        logger.info("Cooldowns have been saved.")
    }

    private fun loadCooldowns() {
        val file = File(dataFolder, "cooldown.yaml")
        if (!file.exists()) return
        val yaml = Yaml()
        FileReader(file).use { reader ->
            val loaded = yaml.load<Map<String, Any>>(reader)
            if (loaded != null) {
                pvpCommand.cooldown.clear()
                for ((key, value) in loaded) {
                    try {
                        val uuid = UUID.fromString(key)
                        val time = (value as? Number)?.toLong() ?: value.toString().toLongOrNull()
                        if (time != null) {
                            pvpCommand.cooldown[uuid] = time
                        }
                    } catch (_: Exception) {}
                }
                logger.info("Cooldowns have been loaded.")
            }
        }
    }
}
