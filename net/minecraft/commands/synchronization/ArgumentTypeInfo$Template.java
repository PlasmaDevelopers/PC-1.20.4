package net.minecraft.commands.synchronization;

import net.minecraft.commands.CommandBuildContext;

public interface Template<A extends com.mojang.brigadier.arguments.ArgumentType<?>> {
  A instantiate(CommandBuildContext paramCommandBuildContext);
  
  ArgumentTypeInfo<A, ?> type();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\ArgumentTypeInfo$Template.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */