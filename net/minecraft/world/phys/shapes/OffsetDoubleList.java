/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*    */ 
/*    */ public class OffsetDoubleList extends AbstractDoubleList {
/*    */   private final DoubleList delegate;
/*    */   private final double offset;
/*    */   
/*    */   public OffsetDoubleList(DoubleList $$0, double $$1) {
/* 11 */     this.delegate = $$0;
/* 12 */     this.offset = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDouble(int $$0) {
/* 17 */     return this.delegate.getDouble($$0) + this.offset;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 22 */     return this.delegate.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\OffsetDoubleList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */