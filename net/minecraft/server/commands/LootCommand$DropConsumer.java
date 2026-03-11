package net.minecraft.server.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
interface DropConsumer {
  int accept(CommandContext<CommandSourceStack> paramCommandContext, List<ItemStack> paramList, LootCommand.Callback paramCallback) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\LootCommand$DropConsumer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */