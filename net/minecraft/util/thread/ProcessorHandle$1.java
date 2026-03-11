/*    */ package net.minecraft.util.thread;
/*    */ 
/*    */ import java.util.function.Consumer;
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
/*    */   implements ProcessorHandle<Msg>
/*    */ {
/*    */   public String name() {
/* 39 */     return name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tell(Msg $$0) {
/* 44 */     tell.accept($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\thread\ProcessorHandle$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */