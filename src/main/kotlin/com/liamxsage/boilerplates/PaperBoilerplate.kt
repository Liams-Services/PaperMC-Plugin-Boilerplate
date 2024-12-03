package com.liamxsage.boilerplates

import com.liamxsage.boilerplates.managers.RegisterManager
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
            RegisterManager.registerListeners(this)
        }
        logger.info("Plugin enabled in $time ms")
        logger.info("PaperBoilerplate is now tweaking your vanilla behavior!")
    }
}