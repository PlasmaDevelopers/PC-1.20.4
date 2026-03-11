package net.minecraft.advancements;

public interface Listener {
  void onAddAdvancementRoot(AdvancementNode paramAdvancementNode);
  
  void onRemoveAdvancementRoot(AdvancementNode paramAdvancementNode);
  
  void onAddAdvancementTask(AdvancementNode paramAdvancementNode);
  
  void onRemoveAdvancementTask(AdvancementNode paramAdvancementNode);
  
  void onAdvancementsCleared();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\AdvancementTree$Listener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */