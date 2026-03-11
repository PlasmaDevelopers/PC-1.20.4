/*    */ package net.minecraft.world.entity.ai.village.poi;
/*    */ 
/*    */ 
/*    */ public final class PoiType extends Record {
/*    */   private final Set<BlockState> matchingStates;
/*    */   private final int maxTickets;
/*    */   private final int validRange;
/*    */   
/*  9 */   public Set<BlockState> matchingStates() { return this.matchingStates; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/ai/village/poi/PoiType;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/ai/village/poi/PoiType; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/ai/village/poi/PoiType;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/ai/village/poi/PoiType; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/ai/village/poi/PoiType;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/entity/ai/village/poi/PoiType;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public int maxTickets() { return this.maxTickets; } public int validRange() { return this.validRange; }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final Predicate<Holder<PoiType>> NONE = $$0 -> false;
/*    */   
/*    */   public PoiType(Set<BlockState> $$0, int $$1, int $$2)
/*    */   {
/* 17 */     $$0 = Set.copyOf($$0);
/*    */     this.matchingStates = $$0;
/*    */     this.maxTickets = $$1;
/*    */     this.validRange = $$2; } public boolean is(BlockState $$0) {
/* 21 */     return this.matchingStates.contains($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\village\poi\PoiType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */