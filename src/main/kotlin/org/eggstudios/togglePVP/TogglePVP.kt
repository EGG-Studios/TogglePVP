package org.eggstudios.togglePVP

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
            // Make the name of the team red
        }

        if (scoreboard.getTeam("PVPLO") == null) {
            val pvpLoTeam = scoreboard.registerNewTeam("PVPLO")
            // Make the name of the team dark red
        }
    }
}
