/*   */ package net.minecraft.world.level.storage.loot;
/*   */ public final class LootDataId<T> extends Record { private final LootDataType<T> type;
/*   */   private final ResourceLocation location;
/*   */   
/* 5 */   public LootDataId(LootDataType<T> $$0, ResourceLocation $$1) { this.type = $$0; this.location = $$1; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/LootDataId;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/LootDataId;
/*   */     // Local variable type table:
/*   */     //   start	length	slot	name	signature
/* 5 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/LootDataId<TT;>; } public LootDataType<T> type() { return this.type; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/LootDataId;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/LootDataId;
/*   */     // Local variable type table:
/*   */     //   start	length	slot	name	signature
/*   */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/LootDataId<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/LootDataId;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/LootDataId;
/*   */     //   0	8	1	$$0	Ljava/lang/Object;
/*   */     // Local variable type table:
/*   */     //   start	length	slot	name	signature
/* 5 */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/LootDataId<TT;>; } public ResourceLocation location() { return this.location; }
/*   */    }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootDataId.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */