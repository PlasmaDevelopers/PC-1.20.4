/*    */ package net.minecraft.world.entity.projectile;
/*    */ 
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.ExperienceOrb;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.item.alchemy.PotionUtils;
/*    */ import net.minecraft.world.item.alchemy.Potions;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ 
/*    */ public class ThrownExperienceBottle
/*    */   extends ThrowableItemProjectile {
/*    */   public ThrownExperienceBottle(EntityType<? extends ThrownExperienceBottle> $$0, Level $$1) {
/* 17 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public ThrownExperienceBottle(Level $$0, LivingEntity $$1) {
/* 21 */     super(EntityType.EXPERIENCE_BOTTLE, $$1, $$0);
/*    */   }
/*    */   
/*    */   public ThrownExperienceBottle(Level $$0, double $$1, double $$2, double $$3) {
/* 25 */     super(EntityType.EXPERIENCE_BOTTLE, $$1, $$2, $$3, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getDefaultItem() {
/* 30 */     return Items.EXPERIENCE_BOTTLE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getGravity() {
/* 35 */     return 0.07F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHit(HitResult $$0) {
/* 40 */     super.onHit($$0);
/*    */     
/* 42 */     if (level() instanceof ServerLevel) {
/* 43 */       level().levelEvent(2002, blockPosition(), PotionUtils.getColor(Potions.WATER));
/*    */       
/* 45 */       int $$1 = 3 + (level()).random.nextInt(5) + (level()).random.nextInt(5);
/* 46 */       ExperienceOrb.award((ServerLevel)level(), position(), $$1);
/*    */       
/* 48 */       discard();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\ThrownExperienceBottle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */