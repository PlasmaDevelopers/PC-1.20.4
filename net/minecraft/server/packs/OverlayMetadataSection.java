/*    */ package net.minecraft.server.packs;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.InclusiveRange;
/*    */ 
/*    */ public final class OverlayMetadataSection extends Record {
/*    */   private final List<OverlayEntry> overlays;
/*    */   
/* 13 */   public OverlayMetadataSection(List<OverlayEntry> $$0) { this.overlays = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/OverlayMetadataSection;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 13 */     //   0	7	0	this	Lnet/minecraft/server/packs/OverlayMetadataSection; } public List<OverlayEntry> overlays() { return this.overlays; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/OverlayMetadataSection;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/packs/OverlayMetadataSection; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/OverlayMetadataSection;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/packs/OverlayMetadataSection;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } private static final Pattern DIR_VALIDATOR = Pattern.compile("[-_a-zA-Z0-9.]+"); private static final Codec<OverlayMetadataSection> CODEC;
/*    */   public static final class OverlayEntry extends Record { private final InclusiveRange<Integer> format; private final String overlay; static final Codec<OverlayEntry> CODEC;
/* 16 */     public OverlayEntry(InclusiveRange<Integer> $$0, String $$1) { this.format = $$0; this.overlay = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #16	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #16	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #16	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/server/packs/OverlayMetadataSection$OverlayEntry;
/* 16 */       //   0	8	1	$$0	Ljava/lang/Object; } public InclusiveRange<Integer> format() { return this.format; } public String overlay() { return this.overlay; } static {
/* 17 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)InclusiveRange.codec((Codec)Codec.INT).fieldOf("formats").forGetter(OverlayEntry::format), (App)ExtraCodecs.validate((Codec)Codec.STRING, OverlayMetadataSection::validateOverlayDir).fieldOf("directory").forGetter(OverlayEntry::overlay)).apply((Applicative)$$0, OverlayEntry::new));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean isApplicable(int $$0) {
/* 23 */       return this.format.isValueInRange(Integer.valueOf($$0));
/*    */     } }
/*    */ 
/*    */   
/*    */   private static DataResult<String> validateOverlayDir(String $$0) {
/* 28 */     if (!DIR_VALIDATOR.matcher($$0).matches()) {
/* 29 */       return DataResult.error(() -> $$0 + " is not accepted directory name");
/*    */     }
/* 31 */     return DataResult.success($$0);
/*    */   }
/*    */   static {
/* 34 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)OverlayEntry.CODEC.listOf().fieldOf("entries").forGetter(OverlayMetadataSection::overlays)).apply((Applicative)$$0, OverlayMetadataSection::new));
/*    */   }
/*    */ 
/*    */   
/* 38 */   public static final MetadataSectionType<OverlayMetadataSection> TYPE = MetadataSectionType.fromCodec("overlays", CODEC);
/*    */   
/*    */   public List<String> overlaysForVersion(int $$0) {
/* 41 */     return this.overlays.stream().filter($$1 -> $$1.isApplicable($$0)).map(OverlayEntry::overlay).toList();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\OverlayMetadataSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */