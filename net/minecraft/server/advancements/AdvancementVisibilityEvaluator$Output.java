package net.minecraft.server.advancements;

import net.minecraft.advancements.AdvancementNode;

@FunctionalInterface
public interface Output {
  void accept(AdvancementNode paramAdvancementNode, boolean paramBoolean);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\advancements\AdvancementVisibilityEvaluator$Output.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */