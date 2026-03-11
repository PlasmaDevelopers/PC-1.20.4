package net.minecraft.server.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

@FunctionalInterface
interface TailProvider {
  ArgumentBuilder<CommandSourceStack, ?> construct(ArgumentBuilder<CommandSourceStack, ?> paramArgumentBuilder, LootCommand.DropConsumer paramDropConsumer);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\LootCommand$TailProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */