/*    */ package net.minecraft.world.level.levelgen.structure.pieces;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*    */ 
/*    */ public final class PiecesContainer extends Record {
/*    */   private final List<StructurePiece> pieces;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/pieces/PiecesContainer;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/PiecesContainer;
/*    */   }
/*    */   
/* 20 */   public List<StructurePiece> pieces() { return this.pieces; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/pieces/PiecesContainer;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/PiecesContainer; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/pieces/PiecesContainer;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/PiecesContainer;
/* 21 */     //   0	8	1	$$0	Ljava/lang/Object; } private static final Logger LOGGER = LogUtils.getLogger();
/*    */ 
/*    */   
/* 24 */   private static final ResourceLocation JIGSAW_RENAME = new ResourceLocation("jigsaw");
/* 25 */   private static final Map<ResourceLocation, ResourceLocation> RENAMES = (Map<ResourceLocation, ResourceLocation>)ImmutableMap.builder()
/* 26 */     .put(new ResourceLocation("nvi"), JIGSAW_RENAME)
/* 27 */     .put(new ResourceLocation("pcp"), JIGSAW_RENAME)
/* 28 */     .put(new ResourceLocation("bastionremnant"), JIGSAW_RENAME)
/* 29 */     .put(new ResourceLocation("runtime"), JIGSAW_RENAME)
/* 30 */     .build();
/*    */   
/*    */   public PiecesContainer(List<StructurePiece> $$0) {
/* 33 */     this.pieces = List.copyOf($$0);
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 37 */     return this.pieces.isEmpty();
/*    */   }
/*    */   
/*    */   public boolean isInsidePiece(BlockPos $$0) {
/* 41 */     for (StructurePiece $$1 : this.pieces) {
/* 42 */       if ($$1.getBoundingBox().isInside((Vec3i)$$0)) {
/* 43 */         return true;
/*    */       }
/*    */     } 
/* 46 */     return false;
/*    */   }
/*    */   
/*    */   public Tag save(StructurePieceSerializationContext $$0) {
/* 50 */     ListTag $$1 = new ListTag();
/* 51 */     for (StructurePiece $$2 : this.pieces) {
/* 52 */       $$1.add($$2.createTag($$0));
/*    */     }
/* 54 */     return (Tag)$$1;
/*    */   }
/*    */   
/*    */   public static PiecesContainer load(ListTag $$0, StructurePieceSerializationContext $$1) {
/* 58 */     List<StructurePiece> $$2 = Lists.newArrayList();
/* 59 */     for (int $$3 = 0; $$3 < $$0.size(); $$3++) {
/* 60 */       CompoundTag $$4 = $$0.getCompound($$3);
/* 61 */       String $$5 = $$4.getString("id").toLowerCase(Locale.ROOT);
/* 62 */       ResourceLocation $$6 = new ResourceLocation($$5);
/* 63 */       ResourceLocation $$7 = RENAMES.getOrDefault($$6, $$6);
/*    */       
/* 65 */       StructurePieceType $$8 = (StructurePieceType)BuiltInRegistries.STRUCTURE_PIECE.get($$7);
/*    */       
/* 67 */       if ($$8 == null) {
/* 68 */         LOGGER.error("Unknown structure piece id: {}", $$7);
/*    */       } else {
/*    */ 
/*    */         
/*    */         try {
/* 73 */           StructurePiece $$9 = $$8.load($$1, $$4);
/* 74 */           $$2.add($$9);
/* 75 */         } catch (Exception $$10) {
/* 76 */           LOGGER.error("Exception loading structure piece with id {}", $$7, $$10);
/*    */         } 
/*    */       } 
/* 79 */     }  return new PiecesContainer($$2);
/*    */   }
/*    */   
/*    */   public BoundingBox calculateBoundingBox() {
/* 83 */     return StructurePiece.createBoundingBox(this.pieces.stream());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pieces\PiecesContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */