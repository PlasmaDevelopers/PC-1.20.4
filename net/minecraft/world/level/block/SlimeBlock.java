/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class SlimeBlock extends HalfTransparentBlock {
/* 13 */   public static final MapCodec<SlimeBlock> CODEC = simpleCodec(SlimeBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<SlimeBlock> codec() {
/* 17 */     return CODEC;
/*    */   }
/*    */   
/*    */   public SlimeBlock(BlockBehaviour.Properties $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, float $$4) {
/* 26 */     if ($$3.isSuppressingBounce()) {
/* 27 */       super.fallOn($$0, $$1, $$2, $$3, $$4);
/*    */     } else {
/*    */       
/* 30 */       $$3.causeFallDamage($$4, 0.0F, $$0.damageSources().fall());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateEntityAfterFallOn(BlockGetter $$0, Entity $$1) {
/* 36 */     if ($$1.isSuppressingBounce()) {
/* 37 */       super.updateEntityAfterFallOn($$0, $$1);
/*    */     } else {
/* 39 */       bounceUp($$1);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void bounceUp(Entity $$0) {
/* 44 */     Vec3 $$1 = $$0.getDeltaMovement();
/* 45 */     if ($$1.y < 0.0D) {
/*    */       
/* 47 */       double $$2 = ($$0 instanceof net.minecraft.world.entity.LivingEntity) ? 1.0D : 0.8D;
/* 48 */       $$0.setDeltaMovement($$1.x, -$$1.y * $$2, $$1.z);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void stepOn(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3) {
/* 58 */     double $$4 = Math.abs(($$3.getDeltaMovement()).y);
/* 59 */     if ($$4 < 0.1D && !$$3.isSteppingCarefully()) {
/* 60 */       double $$5 = 0.4D + $$4 * 0.2D;
/* 61 */       $$3.setDeltaMovement($$3.getDeltaMovement().multiply($$5, 1.0D, $$5));
/*    */     } 
/* 63 */     super.stepOn($$0, $$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SlimeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */