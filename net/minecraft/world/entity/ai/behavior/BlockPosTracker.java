/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class BlockPosTracker implements PositionTracker {
/*    */   private final BlockPos blockPos;
/*    */   
/*    */   public BlockPosTracker(BlockPos $$0) {
/* 12 */     this.blockPos = $$0.immutable();
/* 13 */     this.centerPosition = Vec3.atCenterOf((Vec3i)$$0);
/*    */   }
/*    */   private final Vec3 centerPosition;
/*    */   public BlockPosTracker(Vec3 $$0) {
/* 17 */     this.blockPos = BlockPos.containing((Position)$$0);
/* 18 */     this.centerPosition = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3 currentPosition() {
/* 23 */     return this.centerPosition;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos currentBlockPosition() {
/* 28 */     return this.blockPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isVisibleBy(LivingEntity $$0) {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     return "BlockPosTracker{blockPos=" + this.blockPos + ", centerPosition=" + this.centerPosition + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\BlockPosTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */