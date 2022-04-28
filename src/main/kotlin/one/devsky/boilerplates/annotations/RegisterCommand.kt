package one.devsky.boilerplates.annotations

import org.bukkit.permissions.PermissionDefault

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RegisterCommand(
    val name: String,
    val description: String = "",

    /**
     * Todo: Setup a default permission for your commands
     */
    val permission: String = "",
    val permissionDefault: PermissionDefault = PermissionDefault.TRUE,
    val aliases: Array<out String> = []
)
