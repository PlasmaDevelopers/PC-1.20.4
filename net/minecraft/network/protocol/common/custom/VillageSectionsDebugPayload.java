/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.core.SectionPos;
/*    */ 
/*    */ public final class VillageSectionsDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final Set<SectionPos> villageChunks;
/*    */   private final Set<SectionPos> notVillageChunks;
/*    */   
/* 10 */   public VillageSectionsDebugPayload(Set<SectionPos> $$0, Set<SectionPos> $$1) { this.villageChunks = $$0; this.notVillageChunks = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/VillageSectionsDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 10 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/VillageSectionsDebugPayload; } public Set<SectionPos> villageChunks() { return this.villageChunks; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/VillageSectionsDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/VillageSectionsDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/VillageSectionsDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/VillageSectionsDebugPayload;
/* 10 */     //   0	8	1	$$0	Ljava/lang/Object; } public Set<SectionPos> notVillageChunks() { return this.notVillageChunks; }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public static final ResourceLocation ID = new ResourceLocation("debug/village_sections");
/*    */   
/*    */   public VillageSectionsDebugPayload(FriendlyByteBuf $$0) {
/* 17 */     this((Set<SectionPos>)$$0
/* 18 */         .readCollection(java.util.HashSet::new, FriendlyByteBuf::readSectionPos), (Set<SectionPos>)$$0
/* 19 */         .readCollection(java.util.HashSet::new, FriendlyByteBuf::readSectionPos));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     $$0.writeCollection(this.villageChunks, FriendlyByteBuf::writeSectionPos);
/* 26 */     $$0.writeCollection(this.notVillageChunks, FriendlyByteBuf::writeSectionPos);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 31 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\VillageSectionsDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */