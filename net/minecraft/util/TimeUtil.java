/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import net.minecraft.util.valueproviders.UniformInt;
/*    */ 
/*    */ 
/*    */ public class TimeUtil
/*    */ {
/*  9 */   public static final long NANOSECONDS_PER_SECOND = TimeUnit.SECONDS.toNanos(1L);
/* 10 */   public static final long NANOSECONDS_PER_MILLISECOND = TimeUnit.MILLISECONDS.toNanos(1L);
/* 11 */   public static final long MILLISECONDS_PER_SECOND = TimeUnit.SECONDS.toMillis(1L);
/* 12 */   public static final long SECONDS_PER_HOUR = TimeUnit.HOURS.toSeconds(1L);
/*    */   
/*    */   public static UniformInt rangeOfSeconds(int $$0, int $$1) {
/* 15 */     return UniformInt.of($$0 * 20, $$1 * 20);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\TimeUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */