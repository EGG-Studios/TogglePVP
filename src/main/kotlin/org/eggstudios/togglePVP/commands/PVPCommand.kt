package org.eggstudios.togglePVP.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.HashMap


class PVPCommand : CommandExecutor {
    var cooldown: HashMap<Player?, Long?> = HashMap<Player?, Long?>()

    override fun onCommand(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): Boolean {
        if (p0 is Player) {
            if (!p3.isEmpty()) {
                if (checkCooldown(p0.player)) {
                    if (p3[0] == "pvp") {
                        val team = Bukkit.getScoreboardManager().mainScoreboard.getTeam("PVP")
                        team?.addEntry(p0.name)

                    return true
                    } else if (p3[0] == "loot") {
                        val team = Bukkit.getScoreboardManager().mainScoreboard.getTeam("PVPLO")
                        team?.addEntry(p0.name)
                    }
                }
                if (p3[0] == "off") {
                    p0.player?.scoreboard?.getTeam("PVP")?.removeEntry(p0.name)
                    p0.player?.scoreboard?.getTeam("PVPLO")?.removeEntry(p0.name)
                    cooldown[p0.player] = System.currentTimeMillis()
                }
            }
        }

        return false
    }

    fun checkCooldown(player: Player?): Boolean {
        if (cooldown.contains(player)) {
            val secondsLeft = (cooldown[player]!! + 3_600_000 - System.currentTimeMillis()) / 1000 // 24 hour cooldown
            if (secondsLeft > 0) {
                player?.sendMessage("You must wait $secondsLeft seconds before toggling PVP again.")
                return true
            }
            else {
                cooldown.remove(player)
            }
        }
        return false
    }
}