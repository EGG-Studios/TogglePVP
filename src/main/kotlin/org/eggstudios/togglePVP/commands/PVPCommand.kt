package org.eggstudios.togglePVP.commands

import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PVPCommand : CommandExecutor {
    override fun onCommand(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): Boolean {
        if (p0 is Player) {
            if (!p3.isEmpty()) {
                if (p3[0] == "on") {
                    TODO("Add logic to turn the players name red")
//                    return true (Uncomment this line when logic is added)
                } else if (p3[0] == "off") {
                    TODO("Add logic to turn the players name back to normal")
                }
            }
        }

        return false
    }
}