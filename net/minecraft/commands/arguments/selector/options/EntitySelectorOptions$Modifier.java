package net.minecraft.commands.arguments.selector.options;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;

public interface Modifier {
  void handle(EntitySelectorParser paramEntitySelectorParser) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\selector\options\EntitySelectorOptions$Modifier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */