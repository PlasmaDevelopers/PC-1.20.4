/*    */ package net.minecraft.world.entity.projectile;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class SpectralArrow extends AbstractArrow {
/* 15 */   private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack((ItemLike)Items.SPECTRAL_ARROW);
/* 16 */   private int duration = 200;
/*    */   
/*    */   public SpectralArrow(EntityType<? extends SpectralArrow> $$0, Level $$1) {
/* 19 */     super((EntityType)$$0, $$1, DEFAULT_ARROW_STACK);
/*    */   }
/*    */   
/*    */   public SpectralArrow(Level $$0, LivingEntity $$1, ItemStack $$2) {
/* 23 */     super(EntityType.SPECTRAL_ARROW, $$1, $$0, $$2);
/*    */   }
/*    */   
/*    */   public SpectralArrow(Level $$0, double $$1, double $$2, double $$3, ItemStack $$4) {
/* 27 */     super(EntityType.SPECTRAL_ARROW, $$1, $$2, $$3, $$0, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 32 */     super.tick();
/*    */     
/* 34 */     if ((level()).isClientSide && !this.inGround) {
/* 35 */       level().addParticle((ParticleOptions)ParticleTypes.INSTANT_EFFECT, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doPostHurtEffects(LivingEntity $$0) {
/* 41 */     super.doPostHurtEffects($$0);
/*    */     
/* 43 */     MobEffectInstance $$1 = new MobEffectInstance(MobEffects.GLOWING, this.duration, 0);
/* 44 */     $$0.addEffect($$1, getEffectSource());
/*    */   }
/*    */ 
/*    */   
/*    */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 49 */     super.readAdditionalSaveData($$0);
/* 50 */     if ($$0.contains("Duration")) {
/* 51 */       this.duration = $$0.getInt("Duration");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 57 */     super.addAdditionalSaveData($$0);
/* 58 */     $$0.putInt("Duration", this.duration);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\SpectralArrow.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */