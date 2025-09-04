package org.eggstudios.togglePVP

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.eggstudios.togglePVP.commands.PVPCommand

class TogglePVP : JavaPlugin() {

    override fun onEnable() {
        registerCommand()
        createTeams()

        logger.info("TogglePVP has been enabled.")
    }

    fun registerCommand() {
        getCommand("pvp")?.setExecutor(PVPCommand())
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
}
