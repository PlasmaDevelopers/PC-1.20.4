/*    */ package net.minecraft.world.entity.ai.memory;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
/*    */ import net.minecraft.world.entity.ai.behavior.EntityTracker;
/*    */ import net.minecraft.world.entity.ai.behavior.PositionTracker;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class WalkTarget
/*    */ {
/*    */   private final PositionTracker target;
/*    */   
/*    */   public WalkTarget(BlockPos $$0, float $$1, int $$2) {
/* 16 */     this((PositionTracker)new BlockPosTracker($$0), $$1, $$2);
/*    */   }
/*    */   private final float speedModifier; private final int closeEnoughDist;
/*    */   public WalkTarget(Vec3 $$0, float $$1, int $$2) {
/* 20 */     this((PositionTracker)new BlockPosTracker(BlockPos.containing((Position)$$0)), $$1, $$2);
/*    */   }
/*    */   
/*    */   public WalkTarget(Entity $$0, float $$1, int $$2) {
/* 24 */     this((PositionTracker)new EntityTracker($$0, false), $$1, $$2);
/*    */   }
/*    */   
/*    */   public WalkTarget(PositionTracker $$0, float $$1, int $$2) {
/* 28 */     this.target = $$0;
/* 29 */     this.speedModifier = $$1;
/* 30 */     this.closeEnoughDist = $$2;
/*    */   }
/*    */   
/*    */   public PositionTracker getTarget() {
/* 34 */     return this.target;
/*    */   }
/*    */   
/*    */   public float getSpeedModifier() {
/* 38 */     return this.speedModifier;
/*    */   }
/*    */   
/*    */   public int getCloseEnoughDist() {
/* 42 */     return this.closeEnoughDist;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\memory\WalkTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */