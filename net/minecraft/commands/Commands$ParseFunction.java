package net.minecraft.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

@FunctionalInterface
public interface ParseFunction {
  void parse(StringReader paramStringReader) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\Commands$ParseFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */