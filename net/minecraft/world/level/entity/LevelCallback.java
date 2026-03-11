package net.minecraft.world.level.entity;

public interface LevelCallback<T> {
  void onCreated(T paramT);
  
  void onDestroyed(T paramT);
  
  void onTickingStart(T paramT);
  
  void onTickingEnd(T paramT);
  
  void onTrackingStart(T paramT);
  
  void onTrackingEnd(T paramT);
  
  void onSectionChange(T paramT);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\LevelCallback.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */