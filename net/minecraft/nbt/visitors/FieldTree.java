/*    */ package net.minecraft.nbt.visitors;
/*    */ 
/*    */ public final class FieldTree extends Record {
/*    */   private final int depth;
/*    */   private final Map<String, TagType<?>> selectedFields;
/*    */   private final Map<String, FieldTree> fieldsToRecurse;
/*    */   
/*  8 */   public FieldTree(int $$0, Map<String, TagType<?>> $$1, Map<String, FieldTree> $$2) { this.depth = $$0; this.selectedFields = $$1; this.fieldsToRecurse = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/nbt/visitors/FieldTree;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/nbt/visitors/FieldTree; } public int depth() { return this.depth; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/nbt/visitors/FieldTree;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/nbt/visitors/FieldTree; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/nbt/visitors/FieldTree;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/nbt/visitors/FieldTree;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public Map<String, TagType<?>> selectedFields() { return this.selectedFields; } public Map<String, FieldTree> fieldsToRecurse() { return this.fieldsToRecurse; }
/*    */    private FieldTree(int $$0) {
/* 10 */     this($$0, new HashMap<>(), new HashMap<>());
/*    */   }
/*    */   
/*    */   public static FieldTree createRoot() {
/* 14 */     return new FieldTree(1);
/*    */   }
/*    */   
/*    */   public void addEntry(FieldSelector $$0) {
/* 18 */     if (this.depth <= $$0.path().size()) {
/* 19 */       ((FieldTree)this.fieldsToRecurse.computeIfAbsent($$0.path().get(this.depth - 1), $$0 -> new FieldTree(this.depth + 1))).addEntry($$0);
/*    */     } else {
/* 21 */       this.selectedFields.put($$0.name(), $$0.type());
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isSelected(TagType<?> $$0, String $$1) {
/* 26 */     return $$0.equals(selectedFields().get($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\visitors\FieldTree.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */