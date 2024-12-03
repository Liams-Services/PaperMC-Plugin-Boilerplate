package com.liamxsage.boilerplates.managers

import com.google.common.reflect.ClassPath
import com.liamxsage.boilerplates.PACKAGE_NAME
import com.liamxsage.boilerplates.extensions.getLogger
import com.liamxsage.boilerplates.interfaces.BrigCommand
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

object RegisterManager {

    private val logger = getLogger()

    private fun <T : Any, E: Any> E.loadClassesInPackage(packageName: String, clazzType: KClass<T>): List<KClass<out T>> {
        try {
            val classLoader = this.javaClass.classLoader
            val allClasses = ClassPath.from(classLoader).allClasses
            val classes = mutableListOf<KClass<out T>>()
            for (classInfo in allClasses) {
                if (!classInfo.name.startsWith(PACKAGE_NAME)) continue
                if (classInfo.packageName.startsWith(packageName) && !classInfo.name.contains('$')) {
                    try {
                        val loadedClass = classInfo.load().kotlin
                        if (clazzType.isInstance(loadedClass.javaObjectType.getDeclaredConstructor().newInstance())) {
                            classes.add(loadedClass as KClass<out T>)
                        }
                    } catch (_: Exception) {
                        // Ignore, as this is not a class we need to load
                    }
                }
            }
            return classes
        } catch (exception: Exception) {
            logger.error("Failed to load classes", exception)
            return emptyList()
        }
    }


    fun registerCommands(commands: Commands) {
        val commandClasses = loadClassesInPackage(PACKAGE_NAME, BrigCommand::class)

        commandClasses.forEach {
            val command = it.primaryConstructor?.call() as BrigCommand

            commands.register(
                command.register(),
                command.description,
                command.aliases
            )

            logger.info("Command ${it.simpleName} registered")
        }

        logger.info("Registered ${commandClasses.size} minecraft commands")
    }

    fun registerListeners(plugin: Plugin) {
        val listenerClasses = loadClassesInPackage(PACKAGE_NAME, Listener::class)

        var amountListeners = 0
        listenerClasses.forEach {
            try {
                val listener = it.primaryConstructor?.call() as Listener
                Bukkit.getPluginManager().registerEvents(listener, plugin)
                amountListeners++
            } catch (e: Exception) {
                logger.error("Failed to register listener: ${it.simpleName}", e)
            }
        }
        if (amountListeners == 0) return
        plugin.logger.info("Registered $amountListeners listeners")
    }
}