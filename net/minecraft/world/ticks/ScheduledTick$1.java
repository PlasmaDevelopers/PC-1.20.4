/*    */ package net.minecraft.world.ticks;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.Hash;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements Hash.Strategy<ScheduledTick<?>>
/*    */ {
/*    */   public int hashCode(ScheduledTick<?> $$0) {
/* 43 */     return 31 * $$0.pos().hashCode() + $$0.type().hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable ScheduledTick<?> $$0, @Nullable ScheduledTick<?> $$1) {
/* 48 */     if ($$0 == $$1) {
/* 49 */       return true;
/*    */     }
/* 51 */     if ($$0 == null || $$1 == null) {
/* 52 */       return false;
/*    */     }
/* 54 */     return ($$0.type() == $$1.type() && $$0.pos().equals($$1.pos()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\ScheduledTick$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */