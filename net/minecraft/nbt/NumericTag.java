/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NumericTag
/*    */   implements Tag
/*    */ {
/*    */   public abstract long getAsLong();
/*    */   
/*    */   public abstract int getAsInt();
/*    */   
/*    */   public abstract short getAsShort();
/*    */   
/*    */   public abstract byte getAsByte();
/*    */   
/*    */   public abstract double getAsDouble();
/*    */   
/*    */   public abstract float getAsFloat();
/*    */   
/*    */   public abstract Number getAsNumber();
/*    */   
/*    */   public String toString() {
/* 23 */     return getAsString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\NumericTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */