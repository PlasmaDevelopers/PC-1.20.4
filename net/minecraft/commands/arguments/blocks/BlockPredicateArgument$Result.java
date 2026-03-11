package net.minecraft.commands.arguments.blocks;

import java.util.function.Predicate;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

public interface Result extends Predicate<BlockInWorld> {
  boolean requiresNbt();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\blocks\BlockPredicateArgument$Result.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */