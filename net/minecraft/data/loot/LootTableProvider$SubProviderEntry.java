/*    */ package net.minecraft.data.loot;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
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
/*    */ public final class SubProviderEntry
/*    */   extends Record
/*    */ {
/*    */   private final Supplier<LootTableSubProvider> provider;
/*    */   final LootContextParamSet paramSet;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public SubProviderEntry(Supplier<LootTableSubProvider> $$0, LootContextParamSet $$1) {
/* 36 */     this.provider = $$0; this.paramSet = $$1; } public Supplier<LootTableSubProvider> provider() { return this.provider; } public LootContextParamSet paramSet() { return this.paramSet; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\LootTableProvider$SubProviderEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */