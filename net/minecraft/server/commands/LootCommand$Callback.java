package net.minecraft.server.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
interface Callback {
  void accept(List<ItemStack> paramList) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\LootCommand$Callback.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */