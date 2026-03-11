/*    */ package net.minecraft.server;
/*    */ 
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.world.level.WorldDataConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DataLoadContext
/*    */   extends Record
/*    */ {
/*    */   private final ResourceManager resources;
/*    */   private final WorldDataConfiguration dataConfiguration;
/*    */   private final RegistryAccess.Frozen datapackWorldgen;
/*    */   private final RegistryAccess.Frozen datapackDimensions;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/WorldLoader$DataLoadContext;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #75	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/WorldLoader$DataLoadContext;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/WorldLoader$DataLoadContext;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #75	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/WorldLoader$DataLoadContext;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/WorldLoader$DataLoadContext;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #75	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/WorldLoader$DataLoadContext;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public DataLoadContext(ResourceManager $$0, WorldDataConfiguration $$1, RegistryAccess.Frozen $$2, RegistryAccess.Frozen $$3) {
/* 75 */     this.resources = $$0; this.dataConfiguration = $$1; this.datapackWorldgen = $$2; this.datapackDimensions = $$3; } public ResourceManager resources() { return this.resources; } public WorldDataConfiguration dataConfiguration() { return this.dataConfiguration; } public RegistryAccess.Frozen datapackWorldgen() { return this.datapackWorldgen; } public RegistryAccess.Frozen datapackDimensions() { return this.datapackDimensions; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\WorldLoader$DataLoadContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */