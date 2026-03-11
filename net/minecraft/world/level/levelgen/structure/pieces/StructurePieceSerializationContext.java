/*    */ package net.minecraft.world.level.levelgen.structure.pieces;
/*    */ 
/*    */ 
/*    */ public final class StructurePieceSerializationContext extends Record {
/*    */   private final ResourceManager resourceManager;
/*    */   private final RegistryAccess registryAccess;
/*    */   private final StructureTemplateManager structureTemplateManager;
/*    */   
/*  9 */   public StructurePieceSerializationContext(ResourceManager $$0, RegistryAccess $$1, StructureTemplateManager $$2) { this.resourceManager = $$0; this.registryAccess = $$1; this.structureTemplateManager = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePieceSerializationContext;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePieceSerializationContext; } public ResourceManager resourceManager() { return this.resourceManager; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePieceSerializationContext;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePieceSerializationContext; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePieceSerializationContext;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePieceSerializationContext;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public RegistryAccess registryAccess() { return this.registryAccess; } public StructureTemplateManager structureTemplateManager() { return this.structureTemplateManager; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static StructurePieceSerializationContext fromLevel(ServerLevel $$0) {
/* 15 */     MinecraftServer $$1 = $$0.getServer();
/* 16 */     return new StructurePieceSerializationContext($$1
/* 17 */         .getResourceManager(), (RegistryAccess)$$1
/* 18 */         .registryAccess(), $$1
/* 19 */         .getStructureManager());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pieces\StructurePieceSerializationContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */