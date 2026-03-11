package net.minecraft.commands.arguments;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.scores.ScoreAccess;

@FunctionalInterface
public interface Operation {
  void apply(ScoreAccess paramScoreAccess1, ScoreAccess paramScoreAccess2) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\OperationArgument$Operation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */