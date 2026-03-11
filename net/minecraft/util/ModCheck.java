/*    */ package net.minecraft.util;
/*    */ 
/*    */ public final class ModCheck extends Record {
/*    */   private final Confidence confidence;
/*    */   private final String description;
/*    */   
/*  7 */   public ModCheck(Confidence $$0, String $$1) { this.confidence = $$0; this.description = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/ModCheck;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/util/ModCheck; } public Confidence confidence() { return this.confidence; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/ModCheck;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/ModCheck; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/ModCheck;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/ModCheck;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public String description() { return this.description; }
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Confidence
/*    */   {
/* 13 */     PROBABLY_NOT("Probably not.", false),
/* 14 */     VERY_LIKELY("Very likely;", true),
/* 15 */     DEFINITELY("Definitely;", true);
/*    */     
/*    */     final String description;
/*    */     
/*    */     final boolean shouldReportAsModified;
/*    */     
/*    */     Confidence(String $$0, boolean $$1) {
/* 22 */       this.description = $$0;
/* 23 */       this.shouldReportAsModified = $$1;
/*    */     }
/*    */   }
/*    */   
/*    */   public static ModCheck identify(String $$0, Supplier<String> $$1, String $$2, Class<?> $$3) {
/* 28 */     String $$4 = $$1.get();
/* 29 */     if (!$$0.equals($$4)) {
/* 30 */       return new ModCheck(Confidence.DEFINITELY, $$2 + " brand changed to '" + $$2 + "'");
/*    */     }
/* 32 */     if ($$3.getSigners() == null) {
/* 33 */       return new ModCheck(Confidence.VERY_LIKELY, $$2 + " jar signature invalidated");
/*    */     }
/* 35 */     return new ModCheck(Confidence.PROBABLY_NOT, $$2 + " jar signature and brand is untouched");
/*    */   }
/*    */   
/*    */   public boolean shouldReportAsModified() {
/* 39 */     return this.confidence.shouldReportAsModified;
/*    */   }
/*    */   
/*    */   public ModCheck merge(ModCheck $$0) {
/* 43 */     return new ModCheck((Confidence)ObjectUtils.max((Comparable[])new Confidence[] { this.confidence, $$0.confidence }, ), this.description + "; " + this.description);
/*    */   }
/*    */   
/*    */   public String fullDescription() {
/* 47 */     return this.confidence.description + " " + this.confidence.description;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ModCheck.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */