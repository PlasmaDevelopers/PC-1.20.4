/*   */ package com.mojang.realmsclient.util;public final class WorldGenerationInfo extends Record { private final String seed; private final LevelType levelType;
/*   */   private final boolean generateStructures;
/*   */   private final Set<String> experiments;
/*   */   
/* 5 */   public WorldGenerationInfo(String $$0, LevelType $$1, boolean $$2, Set<String> $$3) { this.seed = $$0; this.levelType = $$1; this.generateStructures = $$2; this.experiments = $$3; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/util/WorldGenerationInfo;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 5 */     //   0	7	0	this	Lcom/mojang/realmsclient/util/WorldGenerationInfo; } public String seed() { return this.seed; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/util/WorldGenerationInfo;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lcom/mojang/realmsclient/util/WorldGenerationInfo; } public final boolean equals(Object $$0) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/util/WorldGenerationInfo;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lcom/mojang/realmsclient/util/WorldGenerationInfo;
/* 5 */     //   0	8	1	$$0	Ljava/lang/Object; } public LevelType levelType() { return this.levelType; } public boolean generateStructures() { return this.generateStructures; } public Set<String> experiments() { return this.experiments; }
/*   */    }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\WorldGenerationInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */