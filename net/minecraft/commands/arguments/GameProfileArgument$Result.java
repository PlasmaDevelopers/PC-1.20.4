package net.minecraft.commands.arguments;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;

@FunctionalInterface
public interface Result {
  Collection<GameProfile> getNames(CommandSourceStack paramCommandSourceStack) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\GameProfileArgument$Result.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */