package gb.goingtoheck.plugin

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import gb.goingtoheck.plugin.listener.PlayerMilk
import gb.goingtoheck.plugin.command.EchoCmd

class Plugin : JavaPlugin() {
    companion object {
        lateinit var instance: Plugin
        private set
    }

    override fun onEnable() {
        instance = this

        getCommand("echo")?.setExecutor(EchoCmd())

        Bukkit.getPluginManager().registerEvents(PlayerData, this)
        Bukkit.getPluginManager().registerEvents(PlayerMilk, this)

        for (player in Bukkit.getOnlinePlayers()) {
            PlayerData(player.uniqueId, player.name)
        }

        logger.info("Plugin enabled!")
    }

    override fun onDisable() {
        logger.info("Plugin disabled!")
    }
}