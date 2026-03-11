package net.minecraft.commands.functions;

import com.mojang.brigadier.CommandDispatcher;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import net.minecraft.commands.FunctionInstantiationException;
import net.minecraft.commands.execution.UnboundEntryAction;
import net.minecraft.resources.ResourceLocation;

interface Entry<T> {
  IntList parameters();
  
  UnboundEntryAction<T> instantiate(List<String> paramList, CommandDispatcher<T> paramCommandDispatcher, T paramT, ResourceLocation paramResourceLocation) throws FunctionInstantiationException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\functions\MacroFunction$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */