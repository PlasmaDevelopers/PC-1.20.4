/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ public class EntityBasedExplosionDamageCalculator
/*    */   extends ExplosionDamageCalculator {
/*    */   private final Entity source;
/*    */   
/*    */   public EntityBasedExplosionDamageCalculator(Entity $$0) {
/* 14 */     this.source = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Float> getBlockExplosionResistance(Explosion $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, FluidState $$4) {
/* 19 */     return super.getBlockExplosionResistance($$0, $$1, $$2, $$3, $$4).map($$5 -> Float.valueOf(this.source.getBlockExplosionResistance($$0, $$1, $$2, $$3, $$4, $$5.floatValue())));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldBlockExplode(Explosion $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, float $$4) {
/* 24 */     return this.source.shouldBlockExplode($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\EntityBasedExplosionDamageCalculator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */