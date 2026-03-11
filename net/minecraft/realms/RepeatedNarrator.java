/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import com.google.common.util.concurrent.RateLimiter;
/*    */ import java.time.Duration;
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ import net.minecraft.client.GameNarrator;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class RepeatedNarrator
/*    */ {
/*    */   private final float permitsPerSecond;
/* 12 */   private final AtomicReference<Params> params = new AtomicReference<>();
/*    */   
/*    */   public RepeatedNarrator(Duration $$0) {
/* 15 */     this.permitsPerSecond = 1000.0F / (float)$$0.toMillis();
/*    */   }
/*    */   
/*    */   public void narrate(GameNarrator $$0, Component $$1) {
/* 19 */     Params $$2 = this.params.updateAndGet($$1 -> 
/* 20 */         ($$1 == null || !$$0.equals($$1.narration)) ? new Params($$0, RateLimiter.create(this.permitsPerSecond)) : $$1);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 25 */     if ($$2.rateLimiter.tryAcquire(1))
/* 26 */       $$0.sayNow($$1); 
/*    */   }
/*    */   
/*    */   private static class Params
/*    */   {
/*    */     final Component narration;
/*    */     final RateLimiter rateLimiter;
/*    */     
/*    */     Params(Component $$0, RateLimiter $$1) {
/* 35 */       this.narration = $$0;
/* 36 */       this.rateLimiter = $$1;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\realms\RepeatedNarrator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */