package net.minecraft.commands.functions;

import java.util.List;
import net.minecraft.commands.execution.UnboundEntryAction;
import net.minecraft.resources.ResourceLocation;

public interface InstantiatedFunction<T> {
  ResourceLocation id();
  
  List<UnboundEntryAction<T>> entries();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\functions\InstantiatedFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */