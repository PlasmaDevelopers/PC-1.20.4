/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ public abstract class DataSlot {
/*    */   public static DataSlot forContainer(final ContainerData container, final int dataId) {
/*  5 */     return new DataSlot()
/*    */       {
/*    */         public int get() {
/*  8 */           return container.get(dataId);
/*    */         }
/*    */ 
/*    */         
/*    */         public void set(int $$0) {
/* 13 */           container.set(dataId, $$0);
/*    */         }
/*    */       };
/*    */   }
/*    */   private int prevValue;
/*    */   public static DataSlot shared(final int[] storage, final int index) {
/* 19 */     return new DataSlot()
/*    */       {
/*    */         public int get() {
/* 22 */           return storage[index];
/*    */         }
/*    */ 
/*    */         
/*    */         public void set(int $$0) {
/* 27 */           storage[index] = $$0;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public static DataSlot standalone() {
/* 33 */     return new DataSlot()
/*    */       {
/*    */         private int value;
/*    */         
/*    */         public int get() {
/* 38 */           return this.value;
/*    */         }
/*    */ 
/*    */         
/*    */         public void set(int $$0) {
/* 43 */           this.value = $$0;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract int get();
/*    */ 
/*    */   
/*    */   public abstract void set(int paramInt);
/*    */   
/*    */   public boolean checkAndClearUpdateFlag() {
/* 55 */     int $$0 = get();
/* 56 */     boolean $$1 = ($$0 != this.prevValue);
/* 57 */     this.prevValue = $$0;
/* 58 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\DataSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */