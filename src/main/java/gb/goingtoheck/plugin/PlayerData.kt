package gb.goingtoheck.plugin

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*
import kotlin.collections.HashMap

data class PlayerData(val id: UUID, val name: String) {
    var boolData: Boolean = false;
    companion object: Listener {
        private val data = HashMap<UUID, PlayerData>()

        operator fun get(id: UUID) = data[id]
        operator fun get(p: Player) = data[p.uniqueId]!!

        @EventHandler
        public fun onJoin(e: PlayerJoinEvent) {
            PlayerData(e.player.uniqueId, e.player.name)
        }

        @EventHandler
        public fun onQuit(e: PlayerQuitEvent) {
            data.remove(e.player.uniqueId)
        }
    }

    init {
        data[id] = this
    }
}
