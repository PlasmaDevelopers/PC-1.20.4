/*    */ package net.minecraft.world.entity.monster;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityDimensions;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.MobSpawnType;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public class Husk
/*    */   extends Zombie
/*    */ {
/*    */   public Husk(EntityType<? extends Husk> $$0, Level $$1) {
/* 24 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public static boolean checkHuskSpawnRules(EntityType<Husk> $$0, ServerLevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 28 */     return (checkMonsterSpawnRules((EntityType)$$0, $$1, $$2, $$3, $$4) && (
/* 29 */       MobSpawnType.isSpawner($$2) || $$1.canSeeSky($$3)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isSunSensitive() {
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 39 */     return SoundEvents.HUSK_AMBIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 44 */     return SoundEvents.HUSK_HURT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 49 */     return SoundEvents.HUSK_DEATH;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getStepSound() {
/* 54 */     return SoundEvents.HUSK_STEP;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doHurtTarget(Entity $$0) {
/* 59 */     boolean $$1 = super.doHurtTarget($$0);
/* 60 */     if ($$1 && getMainHandItem().isEmpty() && $$0 instanceof LivingEntity) {
/* 61 */       float $$2 = level().getCurrentDifficultyAt(blockPosition()).getEffectiveDifficulty();
/* 62 */       ((LivingEntity)$$0).addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int)$$2), (Entity)this);
/*    */     } 
/*    */     
/* 65 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean convertsInWater() {
/* 70 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doUnderWaterConversion() {
/* 75 */     convertToZombieType(EntityType.ZOMBIE);
/* 76 */     if (!isSilent()) {
/* 77 */       level().levelEvent(null, 1041, blockPosition(), 0);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getSkull() {
/* 83 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 88 */     return new Vector3f(0.0F, $$1.height + 0.125F * $$2, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Husk.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */