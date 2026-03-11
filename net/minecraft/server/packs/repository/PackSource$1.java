/*    */ package net.minecraft.server.packs.repository;
/*    */ 
/*    */ import java.util.function.UnaryOperator;
/*    */ import net.minecraft.network.chat.Component;
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
/*    */   implements PackSource
/*    */ {
/*    */   public Component decorate(Component $$0) {
/* 28 */     return decorator.apply($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldAddAutomatically() {
/* 33 */     return addAutomatically;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\PackSource$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */