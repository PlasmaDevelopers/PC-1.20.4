package net.minecraft.server.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.numbers.NumberFormat;

@FunctionalInterface
public interface NumberFormatCommandExecutor {
  int run(CommandContext<CommandSourceStack> paramCommandContext, @Nullable NumberFormat paramNumberFormat) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ScoreboardCommand$NumberFormatCommandExecutor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */