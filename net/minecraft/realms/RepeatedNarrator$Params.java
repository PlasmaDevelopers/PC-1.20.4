/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import com.google.common.util.concurrent.RateLimiter;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Params
/*    */ {
/*    */   final Component narration;
/*    */   final RateLimiter rateLimiter;
/*    */   
/*    */   Params(Component $$0, RateLimiter $$1) {
/* 35 */     this.narration = $$0;
/* 36 */     this.rateLimiter = $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\realms\RepeatedNarrator$Params.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */