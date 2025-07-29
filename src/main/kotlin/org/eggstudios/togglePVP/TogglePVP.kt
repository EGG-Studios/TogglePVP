package org.eggstudios.togglePVP

import org.bukkit.plugin.java.JavaPlugin
import org.eggstudios.togglePVP.commands.PVPCommand

class TogglePVP : JavaPlugin() {

    override fun onEnable() {
        registerCommand()

        logger.info("TogglePVP has been enabled.")
    }

    fun registerCommand() {
        getCommand("pvp")?.setExecutor(PVPCommand())
        logger.info("Commands have been registered successfully.")
    }
}
