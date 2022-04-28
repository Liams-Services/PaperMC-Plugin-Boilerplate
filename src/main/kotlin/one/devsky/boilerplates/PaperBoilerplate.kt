package one.devsky.boilerplates

import one.devsky.boilerplates.managers.RegisterManager.registerAll
import org.bukkit.plugin.java.JavaPlugin
import kotlin.system.measureTimeMillis

class PaperBoilerplate : JavaPlugin() {

    companion object {
        lateinit var instance: PaperBoilerplate
            private set
    }

    init {
        instance = this
    }

    override fun onEnable() {
        // Plugin startup logic
        val time = measureTimeMillis {
            registerAll()
        }
        println("Plugin enabled in $time ms")
        println("PaperBoilerplate is now tweaking your vanilla behavior!")
    }
}