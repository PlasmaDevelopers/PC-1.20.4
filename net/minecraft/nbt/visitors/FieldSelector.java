/*    */ package net.minecraft.nbt.visitors;
/*    */ public final class FieldSelector extends Record {
/*    */   private final List<String> path;
/*    */   private final TagType<?> type;
/*    */   private final String name;
/*    */   
/*  7 */   public FieldSelector(List<String> $$0, TagType<?> $$1, String $$2) { this.path = $$0; this.type = $$1; this.name = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/nbt/visitors/FieldSelector;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/nbt/visitors/FieldSelector; } public List<String> path() { return this.path; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/nbt/visitors/FieldSelector;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/nbt/visitors/FieldSelector; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/nbt/visitors/FieldSelector;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/nbt/visitors/FieldSelector;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public TagType<?> type() { return this.type; } public String name() { return this.name; }
/*    */    public FieldSelector(TagType<?> $$0, String $$1) {
/*  9 */     this(List.of(), $$0, $$1);
/*    */   }
/*    */   
/*    */   public FieldSelector(String $$0, TagType<?> $$1, String $$2) {
/* 13 */     this(List.of($$0), $$1, $$2);
/*    */   }
/*    */   
/*    */   public FieldSelector(String $$0, String $$1, TagType<?> $$2, String $$3) {
/* 17 */     this(List.of($$0, $$1), $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\visitors\FieldSelector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */