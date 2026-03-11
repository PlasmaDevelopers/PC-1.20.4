/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*     */ import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ 
/*     */ public class IglooPieces {
/*     */   public static final int GENERATION_HEIGHT = 90;
/*  37 */   static final ResourceLocation STRUCTURE_LOCATION_IGLOO = new ResourceLocation("igloo/top");
/*  38 */   private static final ResourceLocation STRUCTURE_LOCATION_LADDER = new ResourceLocation("igloo/middle");
/*  39 */   private static final ResourceLocation STRUCTURE_LOCATION_LABORATORY = new ResourceLocation("igloo/bottom");
/*     */   
/*  41 */   static final Map<ResourceLocation, BlockPos> PIVOTS = (Map<ResourceLocation, BlockPos>)ImmutableMap.of(STRUCTURE_LOCATION_IGLOO, new BlockPos(3, 5, 5), STRUCTURE_LOCATION_LADDER, new BlockPos(1, 3, 1), STRUCTURE_LOCATION_LABORATORY, new BlockPos(3, 6, 7));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   static final Map<ResourceLocation, BlockPos> OFFSETS = (Map<ResourceLocation, BlockPos>)ImmutableMap.of(STRUCTURE_LOCATION_IGLOO, BlockPos.ZERO, STRUCTURE_LOCATION_LADDER, new BlockPos(2, -3, 4), STRUCTURE_LOCATION_LABORATORY, new BlockPos(0, -3, -2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addPieces(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2, StructurePieceAccessor $$3, RandomSource $$4) {
/*  54 */     if ($$4.nextDouble() < 0.5D) {
/*  55 */       int $$5 = $$4.nextInt(8) + 4;
/*  56 */       $$3.addPiece((StructurePiece)new IglooPiece($$0, STRUCTURE_LOCATION_LABORATORY, $$1, $$2, $$5 * 3));
/*  57 */       for (int $$6 = 0; $$6 < $$5 - 1; $$6++) {
/*  58 */         $$3.addPiece((StructurePiece)new IglooPiece($$0, STRUCTURE_LOCATION_LADDER, $$1, $$2, $$6 * 3));
/*     */       }
/*     */     } 
/*     */     
/*  62 */     $$3.addPiece((StructurePiece)new IglooPiece($$0, STRUCTURE_LOCATION_IGLOO, $$1, $$2, 0));
/*     */   }
/*     */   
/*     */   public static class IglooPiece extends TemplateStructurePiece {
/*     */     public IglooPiece(StructureTemplateManager $$0, ResourceLocation $$1, BlockPos $$2, Rotation $$3, int $$4) {
/*  67 */       super(StructurePieceType.IGLOO, 0, $$0, $$1, $$1.toString(), makeSettings($$3, $$1), makePosition($$1, $$2, $$4));
/*     */     }
/*     */     
/*     */     public IglooPiece(StructureTemplateManager $$0, CompoundTag $$1) {
/*  71 */       super(StructurePieceType.IGLOO, $$1, $$0, $$1 -> makeSettings(Rotation.valueOf($$0.getString("Rot")), $$1));
/*     */     }
/*     */     
/*     */     private static StructurePlaceSettings makeSettings(Rotation $$0, ResourceLocation $$1) {
/*  75 */       return (new StructurePlaceSettings()).setRotation($$0).setMirror(Mirror.NONE).setRotationPivot(IglooPieces.PIVOTS.get($$1)).addProcessor((StructureProcessor)BlockIgnoreProcessor.STRUCTURE_BLOCK);
/*     */     }
/*     */     
/*     */     private static BlockPos makePosition(ResourceLocation $$0, BlockPos $$1, int $$2) {
/*  79 */       return $$1.offset((Vec3i)IglooPieces.OFFSETS.get($$0)).below($$2);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  84 */       super.addAdditionalSaveData($$0, $$1);
/*  85 */       $$1.putString("Rot", this.placeSettings.getRotation().name());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void handleDataMarker(String $$0, BlockPos $$1, ServerLevelAccessor $$2, RandomSource $$3, BoundingBox $$4) {
/*  90 */       if (!"chest".equals($$0)) {
/*     */         return;
/*     */       }
/*     */       
/*  94 */       $$2.setBlock($$1, Blocks.AIR.defaultBlockState(), 3);
/*  95 */       BlockEntity $$5 = $$2.getBlockEntity($$1.below());
/*  96 */       if ($$5 instanceof ChestBlockEntity) {
/*  97 */         ((ChestBlockEntity)$$5).setLootTable(BuiltInLootTables.IGLOO_CHEST, $$3.nextLong());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 103 */       ResourceLocation $$7 = new ResourceLocation(this.templateName);
/*     */       
/* 105 */       StructurePlaceSettings $$8 = makeSettings(this.placeSettings.getRotation(), $$7);
/*     */       
/* 107 */       BlockPos $$9 = IglooPieces.OFFSETS.get($$7);
/* 108 */       BlockPos $$10 = this.templatePosition.offset((Vec3i)StructureTemplate.calculateRelativePosition($$8, new BlockPos(3 - $$9.getX(), 0, -$$9.getZ())));
/* 109 */       int $$11 = $$0.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$10.getX(), $$10.getZ());
/* 110 */       BlockPos $$12 = this.templatePosition;
/* 111 */       this.templatePosition = this.templatePosition.offset(0, $$11 - 90 - 1, 0);
/*     */       
/* 113 */       super.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */       
/* 115 */       if ($$7.equals(IglooPieces.STRUCTURE_LOCATION_IGLOO)) {
/* 116 */         BlockPos $$13 = this.templatePosition.offset((Vec3i)StructureTemplate.calculateRelativePosition($$8, new BlockPos(3, 0, 5)));
/* 117 */         BlockState $$14 = $$0.getBlockState($$13.below());
/* 118 */         if (!$$14.isAir() && !$$14.is(Blocks.LADDER)) {
/* 119 */           $$0.setBlock($$13, Blocks.SNOW_BLOCK.defaultBlockState(), 3);
/*     */         }
/*     */       } 
/*     */       
/* 123 */       this.templatePosition = $$12;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\IglooPieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */