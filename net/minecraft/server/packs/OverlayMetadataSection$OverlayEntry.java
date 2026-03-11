/*    */ package net.minecraft.server.packs;
/*    */ 
/*    */ public final class OverlayEntry extends Record {
/*    */   private final InclusiveRange<Integer> format;
/*    */   private final String overlay;
/*    */   static final Codec<OverlayEntry> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry;
/*    */   }
/*    */   
/* 16 */   public OverlayEntry(InclusiveRange<Integer> $$0, String $$1) { this.format = $$0; this.overlay = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry;
/* 16 */     //   0	8	1	$$0	Ljava/lang/Object; } public InclusiveRange<Integer> format() { return this.format; } public String overlay() { return this.overlay; } static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)InclusiveRange.codec((Codec)Codec.INT).fieldOf("formats").forGetter(OverlayEntry::format), (App)ExtraCodecs.validate((Codec)Codec.STRING, OverlayMetadataSection::validateOverlayDir).fieldOf("directory").forGetter(OverlayEntry::overlay)).apply((Applicative)$$0, OverlayEntry::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isApplicable(int $$0) {
/* 23 */     return this.format.isValueInRange(Integer.valueOf($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\OverlayMetadataSection$OverlayEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */