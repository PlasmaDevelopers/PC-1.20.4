/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class EntityTracker
/*    */   implements PositionTracker {
/*    */   private final Entity entity;
/*    */   private final boolean trackEyeHeight;
/*    */   
/*    */   public EntityTracker(Entity $$0, boolean $$1) {
/* 17 */     this.entity = $$0;
/* 18 */     this.trackEyeHeight = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3 currentPosition() {
/* 23 */     return this.trackEyeHeight ? this.entity.position().add(0.0D, this.entity.getEyeHeight(), 0.0D) : this.entity.position();
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos currentBlockPosition() {
/* 28 */     return this.entity.blockPosition();
/*    */   }
/*    */   
/*    */   public boolean isVisibleBy(LivingEntity $$0) {
/*    */     LivingEntity $$1;
/* 33 */     Entity entity = this.entity; if (entity instanceof LivingEntity) { $$1 = (LivingEntity)entity; }
/* 34 */     else { return true; }
/*    */ 
/*    */     
/* 37 */     if (!$$1.isAlive()) {
/* 38 */       return false;
/*    */     }
/*    */     
/* 41 */     Optional<NearestVisibleLivingEntities> $$3 = $$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
/* 42 */     return ($$3.isPresent() && ((NearestVisibleLivingEntities)$$3.get()).contains($$1));
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 46 */     return this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     return "EntityTracker for " + this.entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\EntityTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */