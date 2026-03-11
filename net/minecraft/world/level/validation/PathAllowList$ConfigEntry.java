/*    */ package net.minecraft.world.level.validation;
/*    */ 
/*    */ import java.nio.file.FileSystem;
/*    */ import java.nio.file.PathMatcher;
/*    */ import java.util.Optional;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ConfigEntry
/*    */   extends Record
/*    */ {
/*    */   private final PathAllowList.EntryType type;
/*    */   private final String pattern;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public ConfigEntry(PathAllowList.EntryType $$0, String $$1) {
/* 28 */     this.type = $$0; this.pattern = $$1; } public PathAllowList.EntryType type() { return this.type; } public String pattern() { return this.pattern; }
/*    */    public PathMatcher compile(FileSystem $$0) {
/* 30 */     return type().compile($$0, this.pattern);
/*    */   }
/*    */   
/*    */   static Optional<ConfigEntry> parse(String $$0) {
/* 34 */     if ($$0.isBlank() || $$0.startsWith("#")) {
/* 35 */       return Optional.empty();
/*    */     }
/* 37 */     if (!$$0.startsWith("[")) {
/* 38 */       return Optional.of(new ConfigEntry(PathAllowList.EntryType.PREFIX, $$0));
/*    */     }
/*    */     
/* 41 */     int $$1 = $$0.indexOf(']', 1);
/* 42 */     if ($$1 == -1) {
/* 43 */       throw new IllegalArgumentException("Unterminated type in line '" + $$0 + "'");
/*    */     }
/*    */     
/* 46 */     String $$2 = $$0.substring(1, $$1);
/* 47 */     String $$3 = $$0.substring($$1 + 1);
/* 48 */     switch ($$2) { case "glob": case "regex":
/*    */       
/*    */       case "prefix":
/* 51 */        }  throw new IllegalArgumentException("Unsupported definition type in line '" + $$0 + "'");
/*    */   }
/*    */ 
/*    */   
/*    */   static ConfigEntry glob(String $$0) {
/* 56 */     return new ConfigEntry(PathAllowList.EntryType.FILESYSTEM, "glob:" + $$0);
/*    */   }
/*    */   
/*    */   static ConfigEntry regex(String $$0) {
/* 60 */     return new ConfigEntry(PathAllowList.EntryType.FILESYSTEM, "regex:" + $$0);
/*    */   }
/*    */   
/*    */   static ConfigEntry prefix(String $$0) {
/* 64 */     return new ConfigEntry(PathAllowList.EntryType.PREFIX, $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\validation\PathAllowList$ConfigEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */