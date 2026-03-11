/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ 
/*    */ public final class StructuresDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final ResourceKey<Level> dimension;
/*    */   private final BoundingBox mainBB;
/*    */   private final List<PieceInfo> pieces;
/*    */   
/* 12 */   public StructuresDebugPayload(ResourceKey<Level> $$0, BoundingBox $$1, List<PieceInfo> $$2) { this.dimension = $$0; this.mainBB = $$1; this.pieces = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload; } public ResourceKey<Level> dimension() { return this.dimension; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } public BoundingBox mainBB() { return this.mainBB; } public List<PieceInfo> pieces() { return this.pieces; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 17 */   public static final ResourceLocation ID = new ResourceLocation("debug/structures");
/*    */   
/*    */   public StructuresDebugPayload(FriendlyByteBuf $$0) {
/* 20 */     this($$0
/* 21 */         .readResourceKey(Registries.DIMENSION), 
/* 22 */         readBoundingBox($$0), $$0
/* 23 */         .readList(PieceInfo::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 29 */     $$0.writeResourceKey(this.dimension);
/* 30 */     writeBoundingBox($$0, this.mainBB);
/* 31 */     $$0.writeCollection(this.pieces, ($$1, $$2) -> $$2.write($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 36 */     return ID;
/*    */   }
/*    */   
/*    */   static BoundingBox readBoundingBox(FriendlyByteBuf $$0) {
/* 40 */     return new BoundingBox($$0.readInt(), $$0.readInt(), $$0.readInt(), $$0.readInt(), $$0.readInt(), $$0.readInt());
/*    */   }
/*    */   
/*    */   static void writeBoundingBox(FriendlyByteBuf $$0, BoundingBox $$1) {
/* 44 */     $$0.writeInt($$1.minX());
/* 45 */     $$0.writeInt($$1.minY());
/* 46 */     $$0.writeInt($$1.minZ());
/* 47 */     $$0.writeInt($$1.maxX());
/* 48 */     $$0.writeInt($$1.maxY());
/* 49 */     $$0.writeInt($$1.maxZ());
/*    */   }
/*    */   public static final class PieceInfo extends Record { private final BoundingBox boundingBox; private final boolean isStart;
/* 52 */     public PieceInfo(BoundingBox $$0, boolean $$1) { this.boundingBox = $$0; this.isStart = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #52	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #52	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #52	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo;
/* 52 */       //   0	8	1	$$0	Ljava/lang/Object; } public BoundingBox boundingBox() { return this.boundingBox; } public boolean isStart() { return this.isStart; }
/*    */      public PieceInfo(FriendlyByteBuf $$0) {
/* 54 */       this(
/* 55 */           StructuresDebugPayload.readBoundingBox($$0), $$0
/* 56 */           .readBoolean());
/*    */     }
/*    */ 
/*    */     
/*    */     public void write(FriendlyByteBuf $$0) {
/* 61 */       StructuresDebugPayload.writeBoundingBox($$0, this.boundingBox);
/* 62 */       $$0.writeBoolean(this.isStart);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\StructuresDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */