/*    */ package net.minecraft.world.entity.animal;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class Salmon extends AbstractSchoolingFish {
/*    */   public Salmon(EntityType<? extends Salmon> $$0, Level $$1) {
/* 13 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxSchoolSize() {
/* 20 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getBucketItemStack() {
/* 25 */     return new ItemStack((ItemLike)Items.SALMON_BUCKET);
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 30 */     return SoundEvents.SALMON_AMBIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 35 */     return SoundEvents.SALMON_DEATH;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 40 */     return SoundEvents.SALMON_HURT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getFlopSound() {
/* 45 */     return SoundEvents.SALMON_FLOP;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Salmon.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */