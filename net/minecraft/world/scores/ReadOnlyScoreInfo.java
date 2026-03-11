/*    */ package net.minecraft.world.scores;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.network.chat.numbers.NumberFormat;
/*    */ 
/*    */ public interface ReadOnlyScoreInfo
/*    */ {
/*    */   int value();
/*    */   
/*    */   boolean isLocked();
/*    */   
/*    */   @Nullable
/*    */   NumberFormat numberFormat();
/*    */   
/*    */   default MutableComponent formatValue(NumberFormat $$0) {
/* 18 */     return ((NumberFormat)Objects.<NumberFormat>requireNonNullElse(numberFormat(), $$0)).format(value());
/*    */   }
/*    */   
/*    */   static MutableComponent safeFormatValue(@Nullable ReadOnlyScoreInfo $$0, NumberFormat $$1) {
/* 22 */     return ($$0 != null) ? $$0.formatValue($$1) : $$1.format(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\ReadOnlyScoreInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */