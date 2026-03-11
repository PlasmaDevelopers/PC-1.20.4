/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.syncher.EntityDataAccessor;
/*    */ import net.minecraft.network.syncher.SynchedEntityData;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBasedSteering
/*    */ {
/*    */   private static final int MIN_BOOST_TIME = 140;
/*    */   private static final int MAX_BOOST_TIME = 700;
/*    */   private final SynchedEntityData entityData;
/*    */   private final EntityDataAccessor<Integer> boostTimeAccessor;
/*    */   private final EntityDataAccessor<Boolean> hasSaddleAccessor;
/*    */   private boolean boosting;
/*    */   private int boostTime;
/*    */   
/*    */   public ItemBasedSteering(SynchedEntityData $$0, EntityDataAccessor<Integer> $$1, EntityDataAccessor<Boolean> $$2) {
/* 22 */     this.entityData = $$0;
/* 23 */     this.boostTimeAccessor = $$1;
/* 24 */     this.hasSaddleAccessor = $$2;
/*    */   }
/*    */   
/*    */   public void onSynced() {
/* 28 */     this.boosting = true;
/* 29 */     this.boostTime = 0;
/*    */   }
/*    */   
/*    */   public boolean boost(RandomSource $$0) {
/* 33 */     if (this.boosting) {
/* 34 */       return false;
/*    */     }
/* 36 */     this.boosting = true;
/* 37 */     this.boostTime = 0;
/* 38 */     this.entityData.set(this.boostTimeAccessor, Integer.valueOf($$0.nextInt(841) + 140));
/* 39 */     return true;
/*    */   }
/*    */   
/*    */   public void tickBoost() {
/* 43 */     if (this.boosting && this.boostTime++ > boostTimeTotal()) {
/* 44 */       this.boosting = false;
/*    */     }
/*    */   }
/*    */   
/*    */   public float boostFactor() {
/* 49 */     if (this.boosting) {
/* 50 */       return 1.0F + 1.15F * Mth.sin(this.boostTime / boostTimeTotal() * 3.1415927F);
/*    */     }
/* 52 */     return 1.0F;
/*    */   }
/*    */   
/*    */   private int boostTimeTotal() {
/* 56 */     return ((Integer)this.entityData.get(this.boostTimeAccessor)).intValue();
/*    */   }
/*    */   
/*    */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 60 */     $$0.putBoolean("Saddle", hasSaddle());
/*    */   }
/*    */   
/*    */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 64 */     setSaddle($$0.getBoolean("Saddle"));
/*    */   }
/*    */   
/*    */   public void setSaddle(boolean $$0) {
/* 68 */     this.entityData.set(this.hasSaddleAccessor, Boolean.valueOf($$0));
/*    */   }
/*    */   
/*    */   public boolean hasSaddle() {
/* 72 */     return ((Boolean)this.entityData.get(this.hasSaddleAccessor)).booleanValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ItemBasedSteering.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */