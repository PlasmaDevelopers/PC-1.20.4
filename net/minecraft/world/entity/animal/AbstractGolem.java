/*    */ package net.minecraft.world.entity.animal;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public abstract class AbstractGolem
/*    */   extends PathfinderMob {
/*    */   protected AbstractGolem(EntityType<? extends AbstractGolem> $$0, Level $$1) {
/* 13 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected SoundEvent getAmbientSound() {
/* 19 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected SoundEvent getDeathSound() {
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmbientSoundInterval() {
/* 36 */     return 120;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean removeWhenFarAway(double $$0) {
/* 41 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\AbstractGolem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */