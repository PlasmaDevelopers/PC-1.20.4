package net.minecraft.server.commands.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

@FunctionalInterface
interface StringProcessor {
  String process(String paramString) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\data\DataCommands$StringProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */