/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgeableMobGroupData
/*     */   implements SpawnGroupData
/*     */ {
/*     */   private int groupSize;
/*     */   private final boolean shouldSpawnBaby;
/*     */   private final float babySpawnChance;
/*     */   
/*     */   private AgeableMobGroupData(boolean $$0, float $$1) {
/* 178 */     this.shouldSpawnBaby = $$0;
/* 179 */     this.babySpawnChance = $$1;
/*     */   }
/*     */   
/*     */   public AgeableMobGroupData(boolean $$0) {
/* 183 */     this($$0, 0.05F);
/*     */   }
/*     */   
/*     */   public AgeableMobGroupData(float $$0) {
/* 187 */     this(true, $$0);
/*     */   }
/*     */   
/*     */   public int getGroupSize() {
/* 191 */     return this.groupSize;
/*     */   }
/*     */   
/*     */   public void increaseGroupSizeByOne() {
/* 195 */     this.groupSize++;
/*     */   }
/*     */   
/*     */   public boolean isShouldSpawnBaby() {
/* 199 */     return this.shouldSpawnBaby;
/*     */   }
/*     */   
/*     */   public float getBabySpawnChance() {
/* 203 */     return this.babySpawnChance;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\AgeableMob$AgeableMobGroupData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */