/*    */ package net.minecraft.client;
/*    */ 
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.util.ByIdMap;
/*    */ import net.minecraft.util.OptionEnum;
/*    */ 
/*    */ public enum GraphicsStatus
/*    */   implements OptionEnum {
/*  9 */   FAST(0, "options.graphics.fast"),
/* 10 */   FANCY(1, "options.graphics.fancy"),
/* 11 */   FABULOUS(2, "options.graphics.fabulous"); private static final IntFunction<GraphicsStatus> BY_ID; private final int id; private final String key;
/*    */   
/*    */   static {
/* 14 */     BY_ID = ByIdMap.continuous(GraphicsStatus::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.WRAP);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   GraphicsStatus(int $$0, String $$1) {
/* 20 */     this.id = $$0;
/* 21 */     this.key = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 26 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 31 */     return this.key;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     switch (this) { default: throw new IncompatibleClassChangeError();case FAST: case FANCY: case FABULOUS: break; }  return 
/*    */ 
/*    */       
/* 39 */       "fabulous";
/*    */   }
/*    */ 
/*    */   
/*    */   public static GraphicsStatus byId(int $$0) {
/* 44 */     return BY_ID.apply($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\GraphicsStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */