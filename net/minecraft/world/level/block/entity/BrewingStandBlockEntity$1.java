/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.world.inventory.ContainerData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ContainerData
/*    */ {
/*    */   public int get(int $$0) {
/* 60 */     switch ($$0) {
/*    */       case 0:
/* 62 */         return BrewingStandBlockEntity.this.brewTime;
/*    */       case 1:
/* 64 */         return BrewingStandBlockEntity.this.fuel;
/*    */     } 
/* 66 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(int $$0, int $$1) {
/* 71 */     switch ($$0) {
/*    */       case 0:
/* 73 */         BrewingStandBlockEntity.this.brewTime = $$1;
/*    */         break;
/*    */       case 1:
/* 76 */         BrewingStandBlockEntity.this.fuel = $$1;
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 83 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BrewingStandBlockEntity$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */