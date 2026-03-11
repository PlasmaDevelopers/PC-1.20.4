/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class ExplosionDamageCalculator
/*    */ {
/*    */   public Optional<Float> getBlockExplosionResistance(Explosion $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, FluidState $$4) {
/* 13 */     if ($$3.isAir() && $$4.isEmpty()) {
/* 14 */       return Optional.empty();
/*    */     }
/* 16 */     return Optional.of(Float.valueOf(Math.max($$3.getBlock().getExplosionResistance(), $$4.getExplosionResistance())));
/*    */   }
/*    */   
/*    */   public boolean shouldBlockExplode(Explosion $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, float $$4) {
/* 20 */     return true;
/*    */   }
/*    */   
/*    */   public boolean shouldDamageEntity(Explosion $$0, Entity $$1) {
/* 24 */     return true;
/*    */   }
/*    */   
/*    */   public float getEntityDamageAmount(Explosion $$0, Entity $$1) {
/* 28 */     float $$2 = $$0.radius() * 2.0F;
/* 29 */     Vec3 $$3 = $$0.center();
/*    */     
/* 31 */     double $$4 = Math.sqrt($$1.distanceToSqr($$3)) / $$2;
/* 32 */     double $$5 = (1.0D - $$4) * Explosion.getSeenPercent($$3, $$1);
/*    */     
/* 34 */     return (float)(($$5 * $$5 + $$5) / 2.0D * 7.0D * $$2 + 1.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\ExplosionDamageCalculator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */