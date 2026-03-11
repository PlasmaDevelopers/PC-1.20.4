/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public interface ServerLevelAccessor
/*    */   extends LevelAccessor {
/*    */   ServerLevel getLevel();
/*    */   
/*    */   default void addFreshEntityWithPassengers(Entity $$0) {
/* 11 */     $$0.getSelfAndPassengers().forEach(this::addFreshEntity);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\ServerLevelAccessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */