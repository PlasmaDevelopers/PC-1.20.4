/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.StructureManager;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Mirror;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*    */ import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*    */ 
/*    */ public class NetherFossilPieces {
/* 25 */   private static final ResourceLocation[] FOSSILS = new ResourceLocation[] { new ResourceLocation("nether_fossils/fossil_1"), new ResourceLocation("nether_fossils/fossil_2"), new ResourceLocation("nether_fossils/fossil_3"), new ResourceLocation("nether_fossils/fossil_4"), new ResourceLocation("nether_fossils/fossil_5"), new ResourceLocation("nether_fossils/fossil_6"), new ResourceLocation("nether_fossils/fossil_7"), new ResourceLocation("nether_fossils/fossil_8"), new ResourceLocation("nether_fossils/fossil_9"), new ResourceLocation("nether_fossils/fossil_10"), new ResourceLocation("nether_fossils/fossil_11"), new ResourceLocation("nether_fossils/fossil_12"), new ResourceLocation("nether_fossils/fossil_13"), new ResourceLocation("nether_fossils/fossil_14") };
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
/*    */   public static void addPieces(StructureTemplateManager $$0, StructurePieceAccessor $$1, RandomSource $$2, BlockPos $$3) {
/* 43 */     Rotation $$4 = Rotation.getRandom($$2);
/* 44 */     $$1.addPiece((StructurePiece)new NetherFossilPiece($$0, (ResourceLocation)Util.getRandom((Object[])FOSSILS, $$2), $$3, $$4));
/*    */   }
/*    */   
/*    */   public static class NetherFossilPiece extends TemplateStructurePiece {
/*    */     public NetherFossilPiece(StructureTemplateManager $$0, ResourceLocation $$1, BlockPos $$2, Rotation $$3) {
/* 49 */       super(StructurePieceType.NETHER_FOSSIL, 0, $$0, $$1, $$1.toString(), makeSettings($$3), $$2);
/*    */     }
/*    */     
/*    */     public NetherFossilPiece(StructureTemplateManager $$0, CompoundTag $$1) {
/* 53 */       super(StructurePieceType.NETHER_FOSSIL, $$1, $$0, $$1 -> makeSettings(Rotation.valueOf($$0.getString("Rot"))));
/*    */     }
/*    */     
/*    */     private static StructurePlaceSettings makeSettings(Rotation $$0) {
/* 57 */       return (new StructurePlaceSettings()).setRotation($$0).setMirror(Mirror.NONE).addProcessor((StructureProcessor)BlockIgnoreProcessor.STRUCTURE_AND_AIR);
/*    */     }
/*    */ 
/*    */     
/*    */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 62 */       super.addAdditionalSaveData($$0, $$1);
/* 63 */       $$1.putString("Rot", this.placeSettings.getRotation().name());
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     protected void handleDataMarker(String $$0, BlockPos $$1, ServerLevelAccessor $$2, RandomSource $$3, BoundingBox $$4) {}
/*    */ 
/*    */     
/*    */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 72 */       $$4.encapsulate(this.template.getBoundingBox(this.placeSettings, this.templatePosition));
/* 73 */       super.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\NetherFossilPieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */