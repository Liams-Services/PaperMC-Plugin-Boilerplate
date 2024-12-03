package com.liamxsage.boilerplates.commands

import com.liamxsage.boilerplates.PREFIX
import com.liamxsage.boilerplates.interfaces.BrigCommand
import com.mojang.brigadier.Command
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.fruxz.stacked.text
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands

class TemplateCommand : BrigCommand {

    override fun register(): LiteralCommandNode<CommandSourceStack> {
        return Commands.literal("template")
            .executes { ctx ->
                ctx.source.sender.sendMessage(text("$PREFIX Hello World!"))
                Command.SINGLE_SUCCESS
            }
            .build()
    }

}