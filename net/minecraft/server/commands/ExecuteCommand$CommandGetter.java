package net.minecraft.server.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

@FunctionalInterface
public interface CommandGetter<T, R> {
  R get(CommandContext<T> paramCommandContext) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ExecuteCommand$CommandGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */