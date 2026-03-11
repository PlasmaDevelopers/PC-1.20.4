/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import net.minecraft.world.inventory.ContainerData;
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
/*     */ class null
/*     */   implements ContainerData
/*     */ {
/*     */   public int get(int $$0) {
/*  83 */     switch ($$0) {
/*     */       case 0:
/*  85 */         return AbstractFurnaceBlockEntity.this.litTime;
/*     */       case 1:
/*  87 */         return AbstractFurnaceBlockEntity.this.litDuration;
/*     */       case 2:
/*  89 */         return AbstractFurnaceBlockEntity.this.cookingProgress;
/*     */       case 3:
/*  91 */         return AbstractFurnaceBlockEntity.this.cookingTotalTime;
/*     */     } 
/*     */ 
/*     */     
/*  95 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int $$0, int $$1) {
/* 100 */     switch ($$0) {
/*     */       case 0:
/* 102 */         AbstractFurnaceBlockEntity.this.litTime = $$1;
/*     */         break;
/*     */       case 1:
/* 105 */         AbstractFurnaceBlockEntity.this.litDuration = $$1;
/*     */         break;
/*     */       case 2:
/* 108 */         AbstractFurnaceBlockEntity.this.cookingProgress = $$1;
/*     */         break;
/*     */       case 3:
/* 111 */         AbstractFurnaceBlockEntity.this.cookingTotalTime = $$1;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 120 */     return 4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\AbstractFurnaceBlockEntity$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */