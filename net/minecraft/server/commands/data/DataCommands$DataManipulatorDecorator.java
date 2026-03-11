package net.minecraft.server.commands.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

@FunctionalInterface
interface DataManipulatorDecorator {
  ArgumentBuilder<CommandSourceStack, ?> create(DataCommands.DataManipulator paramDataManipulator);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\data\DataCommands$DataManipulatorDecorator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */