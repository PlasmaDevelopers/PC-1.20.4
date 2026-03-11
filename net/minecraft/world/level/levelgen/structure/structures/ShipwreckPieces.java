/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ import java.util.Map;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.RandomizableContainer;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Rotation;
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
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ 
/*     */ public class ShipwreckPieces {
/*  31 */   static final BlockPos PIVOT = new BlockPos(4, 0, 15);
/*     */   
/*  33 */   private static final ResourceLocation[] STRUCTURE_LOCATION_BEACHED = new ResourceLocation[] { new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static final ResourceLocation[] STRUCTURE_LOCATION_OCEAN = new ResourceLocation[] { new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/upsidedown_full"), new ResourceLocation("shipwreck/upsidedown_fronthalf"), new ResourceLocation("shipwreck/upsidedown_backhalf"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/upsidedown_full_degraded"), new ResourceLocation("shipwreck/upsidedown_fronthalf_degraded"), new ResourceLocation("shipwreck/upsidedown_backhalf_degraded"), new ResourceLocation("shipwreck/sideways_full_degraded"), new ResourceLocation("shipwreck/sideways_fronthalf_degraded"), new ResourceLocation("shipwreck/sideways_backhalf_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   static final Map<String, ResourceLocation> MARKERS_TO_LOOT = Map.of("map_chest", BuiltInLootTables.SHIPWRECK_MAP, "treasure_chest", BuiltInLootTables.SHIPWRECK_TREASURE, "supply_chest", BuiltInLootTables.SHIPWRECK_SUPPLY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addPieces(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2, StructurePieceAccessor $$3, RandomSource $$4, boolean $$5) {
/*  77 */     ResourceLocation $$6 = (ResourceLocation)Util.getRandom($$5 ? (Object[])STRUCTURE_LOCATION_BEACHED : (Object[])STRUCTURE_LOCATION_OCEAN, $$4);
/*  78 */     $$3.addPiece((StructurePiece)new ShipwreckPiece($$0, $$6, $$1, $$2, $$5));
/*     */   }
/*     */   
/*     */   public static class ShipwreckPiece extends TemplateStructurePiece {
/*     */     private final boolean isBeached;
/*     */     
/*     */     public ShipwreckPiece(StructureTemplateManager $$0, ResourceLocation $$1, BlockPos $$2, Rotation $$3, boolean $$4) {
/*  85 */       super(StructurePieceType.SHIPWRECK_PIECE, 0, $$0, $$1, $$1.toString(), makeSettings($$3), $$2);
/*     */       
/*  87 */       this.isBeached = $$4;
/*     */     }
/*     */     
/*     */     public ShipwreckPiece(StructureTemplateManager $$0, CompoundTag $$1) {
/*  91 */       super(StructurePieceType.SHIPWRECK_PIECE, $$1, $$0, $$1 -> makeSettings(Rotation.valueOf($$0.getString("Rot"))));
/*     */       
/*  93 */       this.isBeached = $$1.getBoolean("isBeached");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  98 */       super.addAdditionalSaveData($$0, $$1);
/*  99 */       $$1.putBoolean("isBeached", this.isBeached);
/* 100 */       $$1.putString("Rot", this.placeSettings.getRotation().name());
/*     */     }
/*     */     
/*     */     private static StructurePlaceSettings makeSettings(Rotation $$0) {
/* 104 */       return (new StructurePlaceSettings()).setRotation($$0).setMirror(Mirror.NONE).setRotationPivot(ShipwreckPieces.PIVOT).addProcessor((StructureProcessor)BlockIgnoreProcessor.STRUCTURE_AND_AIR);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void handleDataMarker(String $$0, BlockPos $$1, ServerLevelAccessor $$2, RandomSource $$3, BoundingBox $$4) {
/* 109 */       ResourceLocation $$5 = ShipwreckPieces.MARKERS_TO_LOOT.get($$0);
/* 110 */       if ($$5 != null) {
/* 111 */         RandomizableContainer.setBlockEntityLootTable((BlockGetter)$$2, $$3, $$1.below(), $$5);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 117 */       int $$7 = $$0.getMaxBuildHeight();
/* 118 */       int $$8 = 0;
/* 119 */       Vec3i $$9 = this.template.getSize();
/* 120 */       Heightmap.Types $$10 = this.isBeached ? Heightmap.Types.WORLD_SURFACE_WG : Heightmap.Types.OCEAN_FLOOR_WG;
/* 121 */       int $$11 = $$9.getX() * $$9.getZ();
/* 122 */       if ($$11 == 0) {
/* 123 */         $$8 = $$0.getHeight($$10, this.templatePosition.getX(), this.templatePosition.getZ());
/*     */       } else {
/* 125 */         BlockPos $$12 = this.templatePosition.offset($$9.getX() - 1, 0, $$9.getZ() - 1);
/* 126 */         for (BlockPos $$13 : BlockPos.betweenClosed(this.templatePosition, $$12)) {
/* 127 */           int $$14 = $$0.getHeight($$10, $$13.getX(), $$13.getZ());
/* 128 */           $$8 += $$14;
/* 129 */           $$7 = Math.min($$7, $$14);
/*     */         } 
/* 131 */         $$8 /= $$11;
/*     */       } 
/*     */       
/* 134 */       int $$15 = this.isBeached ? ($$7 - $$9.getY() / 2 - $$3.nextInt(3)) : $$8;
/* 135 */       this.templatePosition = new BlockPos(this.templatePosition.getX(), $$15, this.templatePosition.getZ());
/* 136 */       super.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\ShipwreckPieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */