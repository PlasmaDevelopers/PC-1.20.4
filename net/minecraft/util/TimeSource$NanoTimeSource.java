/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.function.LongSupplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface NanoTimeSource
/*    */   extends TimeSource, LongSupplier
/*    */ {
/*    */   default long get(TimeUnit $$0) {
/* 13 */     return $$0.convert(getAsLong(), TimeUnit.NANOSECONDS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\TimeSource$NanoTimeSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */