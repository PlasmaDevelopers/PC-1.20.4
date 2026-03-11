package net.minecraft.server.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

@FunctionalInterface
interface CommandFunction<T, R> {
  R apply(T paramT) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\CloneCommands$CommandFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */