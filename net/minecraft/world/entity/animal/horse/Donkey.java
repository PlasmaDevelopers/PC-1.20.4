/*    */ package net.minecraft.world.entity.animal.horse;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.AgeableMob;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.animal.Animal;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class Donkey
/*    */   extends AbstractChestedHorse {
/*    */   public Donkey(EntityType<? extends Donkey> $$0, Level $$1) {
/* 16 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 21 */     return SoundEvents.DONKEY_AMBIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAngrySound() {
/* 26 */     return SoundEvents.DONKEY_ANGRY;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 31 */     return SoundEvents.DONKEY_DEATH;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected SoundEvent getEatingSound() {
/* 37 */     return SoundEvents.DONKEY_EAT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 42 */     return SoundEvents.DONKEY_HURT;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canMate(Animal $$0) {
/* 47 */     if ($$0 == this) {
/* 48 */       return false;
/*    */     }
/*    */     
/* 51 */     if ($$0 instanceof Donkey || $$0 instanceof Horse) {
/* 52 */       return (canParent() && ((AbstractHorse)$$0).canParent());
/*    */     }
/*    */     
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 61 */     EntityType<? extends AbstractHorse> $$2 = ($$1 instanceof Horse) ? EntityType.MULE : EntityType.DONKEY;
/* 62 */     AbstractHorse $$3 = (AbstractHorse)$$2.create((Level)$$0);
/*    */     
/* 64 */     if ($$3 != null) {
/* 65 */       setOffspringAttributes($$1, $$3);
/*    */     }
/*    */     
/* 68 */     return (AgeableMob)$$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\Donkey.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */