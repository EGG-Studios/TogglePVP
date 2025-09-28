package org.eggstudios.togglePVP.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.HashMap
import java.util.UUID


class PVPCommand : CommandExecutor {
   val cooldown: HashMap<UUID, Long> = HashMap()

    override fun onCommand(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): Boolean {
        if (p0 is Player) {
            if (!p3.isEmpty()) {
                if (p3[0] == "pvp") {
                    if (!checkCooldown(p0.player)) {
                        val team = Bukkit.getScoreboardManager().mainScoreboard.getTeam("PVP")
                        team?.addEntry(p0.name)

                        cooldown[p0.uniqueId] = System.currentTimeMillis()
                        p0.sendMessage("You have been added to the PVP team.")
                    }
                    return true
                } else if (p3[0] == "loot") {
                    if (!checkCooldown(p0.player)) {
                        val team = Bukkit.getScoreboardManager().mainScoreboard.getTeam("PVPLO")
                        team?.addEntry(p0.name)

                        cooldown[p0.uniqueId] = System.currentTimeMillis()
                        p0.sendMessage("You have been added to the PVPLO team.")
                    }
                    return true
                } else if (p3[0] == "off") {
                    if (!checkCooldown(p0.player)) {
                        p0.player?.scoreboard?.getTeam("PVP")?.removeEntry(p0.name)
                        p0.player?.scoreboard?.getTeam("PVPLO")?.removeEntry(p0.name)

                        cooldown[p0.uniqueId] = System.currentTimeMillis()
                        p0.sendMessage("You are no longer allowed to pvp")
                    }
                    return true
                } else if (p3[0] == "help") {
                    p0.sendMessage("Create a ticket in the Discord server if you need help.")
                    return true
                }
            }
        }

        return false
    }

    fun checkCooldown(player: Player?): Boolean {
        if (cooldown.contains(player?.uniqueId)) {
            val secondsLeft = (cooldown[player?.uniqueId]!! + 3_600_000 - System.currentTimeMillis()) / 1000 // 24 hour cooldown
            if (secondsLeft > 0) {
                // TODO: If it is more than 60 seconds/60 minutes, show minutes and hours instead of just seconds
                player?.sendMessage("You must wait $secondsLeft seconds before toggling PVP again.")
                return true
            } else {
                cooldown.remove(player?.uniqueId)
            }
        }
        return false
    }
}