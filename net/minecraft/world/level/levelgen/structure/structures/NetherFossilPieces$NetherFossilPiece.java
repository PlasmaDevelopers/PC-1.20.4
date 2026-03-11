/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ import net.minecraft.world.level.StructureManager;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Mirror;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
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
/*    */ public class NetherFossilPiece
/*    */   extends TemplateStructurePiece
/*    */ {
/*    */   public NetherFossilPiece(StructureTemplateManager $$0, ResourceLocation $$1, BlockPos $$2, Rotation $$3) {
/* 49 */     super(StructurePieceType.NETHER_FOSSIL, 0, $$0, $$1, $$1.toString(), makeSettings($$3), $$2);
/*    */   }
/*    */   
/*    */   public NetherFossilPiece(StructureTemplateManager $$0, CompoundTag $$1) {
/* 53 */     super(StructurePieceType.NETHER_FOSSIL, $$1, $$0, $$1 -> makeSettings(Rotation.valueOf($$0.getString("Rot"))));
/*    */   }
/*    */   
/*    */   private static StructurePlaceSettings makeSettings(Rotation $$0) {
/* 57 */     return (new StructurePlaceSettings()).setRotation($$0).setMirror(Mirror.NONE).addProcessor((StructureProcessor)BlockIgnoreProcessor.STRUCTURE_AND_AIR);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 62 */     super.addAdditionalSaveData($$0, $$1);
/* 63 */     $$1.putString("Rot", this.placeSettings.getRotation().name());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void handleDataMarker(String $$0, BlockPos $$1, ServerLevelAccessor $$2, RandomSource $$3, BoundingBox $$4) {}
/*    */ 
/*    */   
/*    */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 72 */     $$4.encapsulate(this.template.getBoundingBox(this.placeSettings, this.templatePosition));
/* 73 */     super.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\NetherFossilPieces$NetherFossilPiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */