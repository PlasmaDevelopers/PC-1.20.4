/*    */ package net.minecraft.world.scores;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.numbers.NumberFormat;
/*    */ 
/*    */ public interface ScoreAccess
/*    */ {
/*    */   int get();
/*    */   
/*    */   void set(int paramInt);
/*    */   
/*    */   default int add(int $$0) {
/* 14 */     int $$1 = get() + $$0;
/* 15 */     set($$1);
/* 16 */     return $$1;
/*    */   }
/*    */   
/*    */   default int increment() {
/* 20 */     return add(1);
/*    */   }
/*    */   
/*    */   default void reset() {
/* 24 */     set(0);
/*    */   }
/*    */   
/*    */   boolean locked();
/*    */   
/*    */   void unlock();
/*    */   
/*    */   void lock();
/*    */   
/*    */   @Nullable
/*    */   Component display();
/*    */   
/*    */   void display(@Nullable Component paramComponent);
/*    */   
/*    */   void numberFormatOverride(@Nullable NumberFormat paramNumberFormat);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\ScoreAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */