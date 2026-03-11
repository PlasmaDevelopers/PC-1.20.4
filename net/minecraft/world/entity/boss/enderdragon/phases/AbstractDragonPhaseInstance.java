/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public abstract class AbstractDragonPhaseInstance
/*    */   implements DragonPhaseInstance {
/*    */   protected final EnderDragon dragon;
/*    */   
/*    */   public AbstractDragonPhaseInstance(EnderDragon $$0) {
/* 16 */     this.dragon = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSitting() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void doClientTick() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void doServerTick() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCrystalDestroyed(EndCrystal $$0, BlockPos $$1, DamageSource $$2, @Nullable Player $$3) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void begin() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void end() {}
/*    */ 
/*    */   
/*    */   public float getFlySpeed() {
/* 46 */     return 0.6F;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3 getFlyTargetLocation() {
/* 52 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public float onHurt(DamageSource $$0, float $$1) {
/* 57 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getTurnSpeed() {
/* 62 */     float $$0 = (float)this.dragon.getDeltaMovement().horizontalDistance() + 1.0F;
/* 63 */     float $$1 = Math.min($$0, 40.0F);
/*    */     
/* 65 */     return 0.7F / $$1 / $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\AbstractDragonPhaseInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */