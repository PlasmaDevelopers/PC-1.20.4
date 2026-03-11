/*   */ package net.minecraft.world.level.entity;
/*   */ 
/*   */ import net.minecraft.world.entity.Entity;
/*   */ 
/*   */ public interface EntityInLevelCallback {
/* 6 */   public static final EntityInLevelCallback NULL = new EntityInLevelCallback() {
/*   */       public void onMove() {}
/*   */       
/*   */       public void onRemove(Entity.RemovalReason $$0) {}
/*   */     };
/*   */   
/*   */   void onMove();
/*   */   
/*   */   void onRemove(Entity.RemovalReason paramRemovalReason);
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\EntityInLevelCallback.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */