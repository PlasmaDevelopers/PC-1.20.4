package net.minecraft.server.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import net.minecraft.server.packs.repository.Pack;

interface Inserter {
  void apply(List<Pack> paramList, Pack paramPack) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DataPackCommand$Inserter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */