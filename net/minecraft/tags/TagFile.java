/*   */ package net.minecraft.tags;
/*   */ 
/*   */ public final class TagFile extends Record {
/*   */   private final List<TagEntry> entries;
/*   */   private final boolean replace;
/*   */   public static final Codec<TagFile> CODEC;
/*   */   
/* 8 */   public TagFile(List<TagEntry> $$0, boolean $$1) { this.entries = $$0; this.replace = $$1; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lnet/minecraft/tags/TagFile;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #8	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 8 */     //   0	7	0	this	Lnet/minecraft/tags/TagFile; } public List<TagEntry> entries() { return this.entries; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/tags/TagFile;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #8	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/tags/TagFile; } public final boolean equals(Object $$0) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lnet/minecraft/tags/TagFile;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #8	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lnet/minecraft/tags/TagFile;
/* 8 */     //   0	8	1	$$0	Ljava/lang/Object; } public boolean replace() { return this.replace; } static {
/* 9 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)TagEntry.CODEC.listOf().fieldOf("values").forGetter(TagFile::entries), (App)Codec.BOOL.optionalFieldOf("replace", Boolean.valueOf(false)).forGetter(TagFile::replace)).apply((Applicative)$$0, TagFile::new));
/*   */   }
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\TagFile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */