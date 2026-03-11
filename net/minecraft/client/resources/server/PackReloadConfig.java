/*   */ package net.minecraft.client.resources.server;public interface PackReloadConfig { void scheduleReload(Callbacks paramCallbacks);
/*   */   public static interface Callbacks { void onSuccess();
/*   */     void onFailure(boolean param1Boolean);
/*   */     List<PackReloadConfig.IdAndPath> packsToLoad(); }
/*   */   public static final class IdAndPath extends Record { private final UUID id;
/*   */     private final Path path;
/*   */     
/* 8 */     public IdAndPath(UUID $$0, Path $$1) { this.id = $$0; this.path = $$1; } public final String toString() { // Byte code:
/*   */       //   0: aload_0
/*   */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/server/PackReloadConfig$IdAndPath;)Ljava/lang/String;
/*   */       //   6: areturn
/*   */       // Line number table:
/*   */       //   Java source line number -> byte code offset
/*   */       //   #8	-> 0
/*   */       // Local variable table:
/*   */       //   start	length	slot	name	descriptor
/* 8 */       //   0	7	0	this	Lnet/minecraft/client/resources/server/PackReloadConfig$IdAndPath; } public UUID id() { return this.id; } public final int hashCode() { // Byte code:
/*   */       //   0: aload_0
/*   */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/server/PackReloadConfig$IdAndPath;)I
/*   */       //   6: ireturn
/*   */       // Line number table:
/*   */       //   Java source line number -> byte code offset
/*   */       //   #8	-> 0
/*   */       // Local variable table:
/*   */       //   start	length	slot	name	descriptor
/*   */       //   0	7	0	this	Lnet/minecraft/client/resources/server/PackReloadConfig$IdAndPath; } public final boolean equals(Object $$0) { // Byte code:
/*   */       //   0: aload_0
/*   */       //   1: aload_1
/*   */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/server/PackReloadConfig$IdAndPath;Ljava/lang/Object;)Z
/*   */       //   7: ireturn
/*   */       // Line number table:
/*   */       //   Java source line number -> byte code offset
/*   */       //   #8	-> 0
/*   */       // Local variable table:
/*   */       //   start	length	slot	name	descriptor
/*   */       //   0	8	0	this	Lnet/minecraft/client/resources/server/PackReloadConfig$IdAndPath;
/* 8 */       //   0	8	1	$$0	Ljava/lang/Object; } public Path path() { return this.path; }
/*   */      }
/*   */    }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\PackReloadConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */