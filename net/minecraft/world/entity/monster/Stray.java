/*    */ package net.minecraft.world.entity.monster;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.MobSpawnType;
/*    */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*    */ import net.minecraft.world.entity.projectile.Arrow;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ 
/*    */ public class Stray
/*    */   extends AbstractSkeleton {
/*    */   public Stray(EntityType<? extends Stray> $$0, Level $$1) {
/* 22 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public static boolean checkStraySpawnRules(EntityType<Stray> $$0, ServerLevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 26 */     BlockPos $$5 = $$3;
/*    */     while (true) {
/* 28 */       $$5 = $$5.above();
/* 29 */       if (!$$1.getBlockState($$5).is(Blocks.POWDER_SNOW))
/* 30 */         return (checkMonsterSpawnRules((EntityType)$$0, $$1, $$2, $$3, $$4) && (
/* 31 */           MobSpawnType.isSpawner($$2) || $$1.canSeeSky($$5.below()))); 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 36 */     return SoundEvents.STRAY_AMBIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 41 */     return SoundEvents.STRAY_HURT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 46 */     return SoundEvents.STRAY_DEATH;
/*    */   }
/*    */ 
/*    */   
/*    */   SoundEvent getStepSound() {
/* 51 */     return SoundEvents.STRAY_STEP;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractArrow getArrow(ItemStack $$0, float $$1) {
/* 56 */     AbstractArrow $$2 = super.getArrow($$0, $$1);
/* 57 */     if ($$2 instanceof Arrow) {
/* 58 */       ((Arrow)$$2).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600));
/*    */     }
/* 60 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Stray.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */