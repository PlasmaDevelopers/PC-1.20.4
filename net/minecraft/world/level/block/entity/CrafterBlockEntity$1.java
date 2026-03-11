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
/*    */ class null
/*    */   implements ContainerData
/*    */ {
/* 39 */   private final int[] slotStates = new int[9];
/* 40 */   private int triggered = 0;
/*    */ 
/*    */   
/*    */   public int get(int $$0) {
/* 44 */     return ($$0 == 9) ? this.triggered : this.slotStates[$$0];
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(int $$0, int $$1) {
/* 49 */     if ($$0 == 9) {
/* 50 */       this.triggered = $$1;
/*    */     } else {
/* 52 */       this.slotStates[$$0] = $$1;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 58 */     return 10;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\CrafterBlockEntity$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */