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
/*    */ class null
/*    */   implements Hash.Strategy<SavedTick<?>>
/*    */ {
/*    */   public int hashCode(SavedTick<?> $$0) {
/* 30 */     return 31 * $$0.pos().hashCode() + $$0.type().hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable SavedTick<?> $$0, @Nullable SavedTick<?> $$1) {
/* 35 */     if ($$0 == $$1) {
/* 36 */       return true;
/*    */     }
/* 38 */     if ($$0 == null || $$1 == null) {
/* 39 */       return false;
/*    */     }
/* 41 */     return ($$0.type() == $$1.type() && $$0.pos().equals($$1.pos()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\SavedTick$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */