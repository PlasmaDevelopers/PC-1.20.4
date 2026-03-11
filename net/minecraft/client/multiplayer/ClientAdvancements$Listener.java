package net.minecraft.client.multiplayer;

import javax.annotation.Nullable;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementTree;

public interface Listener extends AdvancementTree.Listener {
  void onUpdateAdvancementProgress(AdvancementNode paramAdvancementNode, AdvancementProgress paramAdvancementProgress);
  
  void onSelectedTabChanged(@Nullable AdvancementHolder paramAdvancementHolder);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientAdvancements$Listener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */