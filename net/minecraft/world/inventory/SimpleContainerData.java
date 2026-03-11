/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ public class SimpleContainerData implements ContainerData {
/*    */   private final int[] ints;
/*    */   
/*    */   public SimpleContainerData(int $$0) {
/*  7 */     this.ints = new int[$$0];
/*    */   }
/*    */ 
/*    */   
/*    */   public int get(int $$0) {
/* 12 */     return this.ints[$$0];
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(int $$0, int $$1) {
/* 17 */     this.ints[$$0] = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 22 */     return this.ints.length;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\SimpleContainerData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */