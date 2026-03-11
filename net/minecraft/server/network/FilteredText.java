/*    */ package net.minecraft.server.network;
/*    */ 
/*    */ 
/*    */ public final class FilteredText extends Record {
/*    */   private final String raw;
/*    */   private final FilterMask mask;
/*    */   
/*  8 */   public FilteredText(String $$0, FilterMask $$1) { this.raw = $$0; this.mask = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/network/FilteredText;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/server/network/FilteredText; } public String raw() { return this.raw; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/network/FilteredText;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/network/FilteredText; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/network/FilteredText;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/network/FilteredText;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public FilterMask mask() { return this.mask; }
/*  9 */    public static final FilteredText EMPTY = passThrough("");
/*    */   
/*    */   public static FilteredText passThrough(String $$0) {
/* 12 */     return new FilteredText($$0, FilterMask.PASS_THROUGH);
/*    */   }
/*    */   
/*    */   public static FilteredText fullyFiltered(String $$0) {
/* 16 */     return new FilteredText($$0, FilterMask.FULLY_FILTERED);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String filtered() {
/* 21 */     return this.mask.apply(this.raw);
/*    */   }
/*    */   
/*    */   public String filteredOrEmpty() {
/* 25 */     return Objects.<String>requireNonNullElse(filtered(), "");
/*    */   }
/*    */   
/*    */   public boolean isFiltered() {
/* 29 */     return !this.mask.isEmpty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\FilteredText.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */