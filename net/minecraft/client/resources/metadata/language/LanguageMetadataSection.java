/*    */ package net.minecraft.client.resources.metadata.language;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.resources.language.LanguageInfo;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionType;
/*    */ 
/*    */ public final class LanguageMetadataSection extends Record {
/*    */   private final Map<String, LanguageInfo> languages;
/*    */   
/* 11 */   public LanguageMetadataSection(Map<String, LanguageInfo> $$0) { this.languages = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/metadata/language/LanguageMetadataSection;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/client/resources/metadata/language/LanguageMetadataSection; } public Map<String, LanguageInfo> languages() { return this.languages; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/metadata/language/LanguageMetadataSection;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/resources/metadata/language/LanguageMetadataSection; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/metadata/language/LanguageMetadataSection;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/resources/metadata/language/LanguageMetadataSection;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final Codec<String> LANGUAGE_CODE_CODEC = ExtraCodecs.sizeLimitedString(1, 16);
/*    */   
/* 14 */   public static final Codec<LanguageMetadataSection> CODEC = Codec.unboundedMap(LANGUAGE_CODE_CODEC, LanguageInfo.CODEC)
/* 15 */     .xmap(LanguageMetadataSection::new, LanguageMetadataSection::languages);
/*    */   
/* 17 */   public static final MetadataSectionType<LanguageMetadataSection> TYPE = MetadataSectionType.fromCodec("language", CODEC);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\language\LanguageMetadataSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */