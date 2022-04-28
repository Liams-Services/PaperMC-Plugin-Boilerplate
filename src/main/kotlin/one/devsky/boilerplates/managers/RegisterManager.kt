package one.devsky.boilerplates.managers

import one.devsky.boilerplates.PaperBoilerplate
import one.devsky.boilerplates.annotations.RegisterCommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.event.Listener
import org.bukkit.permissions.Permission
import org.bukkit.plugin.Plugin
import org.reflections8.Reflections
import kotlin.system.measureTimeMillis

object RegisterManager {

    /**
     * Register all commands and listeners from the plugin
     * Todo: Change the reflections path to match your package structure
     */
    fun registerAll() {
        val reflections = Reflections("one.devsky.boilerplates")

        val timeListeners = measureTimeMillis {
            for (clazz in reflections.getSubTypesOf(Listener::class.java)) {
                try {
                    val constructor = clazz.getDeclaredConstructor()

                    constructor.isAccessible = true

                    val event = constructor.newInstance() as Listener

                    Bukkit.getPluginManager().registerEvents(event, PaperBoilerplate.instance)
                    Bukkit.getConsoleSender()
                        .sendMessage("Listener ${event.javaClass.simpleName} registered")
                } catch (exception: InstantiationError) {
                    exception.printStackTrace()
                } catch (exception: IllegalAccessException) {
                    exception.printStackTrace()
                }
            }
        }
        println("Registered listeners in $timeListeners ms")

        val timeCommands = measureTimeMillis {
            for (clazz in reflections.getTypesAnnotatedWith(RegisterCommand::class.java)) {
                try {
                    val annotation: RegisterCommand = clazz.getAnnotation(RegisterCommand::class.java)

                    val pluginClass: Class<PluginCommand> = PluginCommand::class.java
                    val constructor = pluginClass.getDeclaredConstructor(String::class.java, Plugin::class.java)

                    constructor.isAccessible = true

                    val command: PluginCommand = constructor.newInstance(annotation.name, PaperBoilerplate.instance)

                    command.aliases = annotation.aliases.toList()
                    command.description = annotation.description
                    command.permission = Permission(annotation.permission, annotation.permissionDefault).name
                    command.setExecutor(clazz.getDeclaredConstructor().newInstance() as CommandExecutor)

                    Bukkit.getCommandMap().register(PaperBoilerplate.instance.name.lowercase(), command)
                    Bukkit.getConsoleSender().sendMessage("Command ${command.name} registered")
                } catch (exception: InstantiationError) {
                    exception.printStackTrace()
                } catch (exception: IllegalAccessException) {
                    exception.printStackTrace()
                }
            }
        }
        println("Registered commands in $timeCommands ms")
    }
}