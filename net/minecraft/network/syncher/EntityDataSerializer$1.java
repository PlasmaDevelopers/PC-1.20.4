/*    */ package net.minecraft.network.syncher;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
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
/*    */   implements EntityDataSerializer.ForValueType<T>
/*    */ {
/*    */   public void write(FriendlyByteBuf $$0, T $$1) {
/* 30 */     writer.accept($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public T read(FriendlyByteBuf $$0) {
/* 35 */     return (T)reader.apply($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\syncher\EntityDataSerializer$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */