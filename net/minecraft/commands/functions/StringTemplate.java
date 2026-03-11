/*    */ package net.minecraft.commands.functions;
/*    */ 
/*    */ public final class StringTemplate extends Record {
/*    */   private final List<String> segments;
/*    */   private final List<String> variables;
/*    */   
/*  7 */   public StringTemplate(List<String> $$0, List<String> $$1) { this.segments = $$0; this.variables = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/commands/functions/StringTemplate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/commands/functions/StringTemplate; } public List<String> segments() { return this.segments; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/functions/StringTemplate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/commands/functions/StringTemplate; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/commands/functions/StringTemplate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/commands/functions/StringTemplate;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public List<String> variables() { return this.variables; }
/*    */   
/*    */   public static StringTemplate fromString(String $$0, int $$1) {
/* 10 */     ImmutableList.Builder<String> $$2 = ImmutableList.builder();
/* 11 */     ImmutableList.Builder<String> $$3 = ImmutableList.builder();
/*    */     
/* 13 */     int $$4 = $$0.length();
/* 14 */     int $$5 = 0;
/* 15 */     int $$6 = $$0.indexOf('$');
/* 16 */     while ($$6 != -1) {
/* 17 */       if ($$6 == $$4 - 1 || $$0.charAt($$6 + 1) != '(') {
/* 18 */         $$6 = $$0.indexOf('$', $$6 + 1);
/*    */         
/*    */         continue;
/*    */       } 
/* 22 */       $$2.add($$0.substring($$5, $$6));
/* 23 */       int $$7 = $$0.indexOf(')', $$6 + 1);
/* 24 */       if ($$7 == -1) {
/* 25 */         throw new IllegalArgumentException("Unterminated macro variable in macro '" + $$0 + "' on line " + $$1);
/*    */       }
/* 27 */       String $$8 = $$0.substring($$6 + 2, $$7);
/* 28 */       if (!isValidVariableName($$8)) {
/* 29 */         throw new IllegalArgumentException("Invalid macro variable name '" + $$8 + "' on line " + $$1);
/*    */       }
/* 31 */       $$3.add($$8);
/* 32 */       $$5 = $$7 + 1;
/* 33 */       $$6 = $$0.indexOf('$', $$5);
/*    */     } 
/* 35 */     if ($$5 == 0) {
/* 36 */       throw new IllegalArgumentException("Macro without variables on line " + $$1);
/*    */     }
/* 38 */     if ($$5 != $$4) {
/* 39 */       $$2.add($$0.substring($$5));
/*    */     }
/* 41 */     return new StringTemplate((List<String>)$$2.build(), (List<String>)$$3.build());
/*    */   }
/*    */   
/*    */   private static boolean isValidVariableName(String $$0) {
/* 45 */     for (int $$1 = 0; $$1 < $$0.length(); $$1++) {
/* 46 */       char $$2 = $$0.charAt($$1);
/* 47 */       if (!Character.isLetterOrDigit($$2) && $$2 != '_') {
/* 48 */         return false;
/*    */       }
/*    */     } 
/* 51 */     return true;
/*    */   }
/*    */   
/*    */   public String substitute(List<String> $$0) {
/* 55 */     StringBuilder $$1 = new StringBuilder();
/* 56 */     for (int $$2 = 0; $$2 < this.variables.size(); $$2++) {
/* 57 */       $$1.append(this.segments.get($$2)).append($$0.get($$2));
/*    */     }
/*    */     
/* 60 */     if (this.segments.size() > this.variables.size()) {
/* 61 */       $$1.append(this.segments.get(this.segments.size() - 1));
/*    */     }
/* 63 */     return $$1.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\functions\StringTemplate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */