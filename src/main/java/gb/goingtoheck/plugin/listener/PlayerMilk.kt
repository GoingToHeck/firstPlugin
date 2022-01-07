package gb.goingtoheck.plugin.listener

import gb.goingtoheck.plugin.Plugin
import net.kyori.adventure.key.Key
import org.bukkit.Material
import net.kyori.adventure.sound.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.NamespacedKey
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object PlayerMilk : Listener {
    private val key = NamespacedKey(Plugin.instance, "goingtoheck")

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEntityEvent) {
        if (event.player.inventory.itemInMainHand.type == Material.BUCKET && event.rightClicked is Player) {
            val milkItem = ItemStack(Material.MILK_BUCKET, 1)
            val meta: ItemMeta? = milkItem.itemMeta
            meta?.displayName(((event.rightClicked as Player).displayName().append(Component.text("'s Milk"))).color(
                TextColor.color(81,106,214)))
            meta?.itemFlags?.add(ItemFlag.HIDE_POTION_EFFECTS)
            meta?.persistentDataContainer?.set(key, PersistentDataType.STRING, "milk")
            milkItem.itemMeta = meta
            event.player.inventory.setItemInMainHand(milkItem)
            event.player.playSound(Sound.sound(Key.key("entity.ghast.hurt"), Sound.Source.HOSTILE, 1f, 1.25f))
        }
    }

    @EventHandler
    fun onCustomMilkDrink(event: PlayerItemConsumeEvent) {
        if (event.item.type == Material.MILK_BUCKET && event.item.itemMeta?.persistentDataContainer?.get(key, PersistentDataType.STRING) == "milk") {
            event.player.sendMessage("~~You Feel Funny...~~")
            event.player.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION, 100, 1))
        }
    }
}