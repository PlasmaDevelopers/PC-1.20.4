/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ import java.util.List;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.ConstantInt;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.monster.Drowned;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
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
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.CappedProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ 
/*     */ public class OceanRuinPieces {
/*  55 */   static final StructureProcessor WARM_SUSPICIOUS_BLOCK_PROCESSOR = archyRuleProcessor(Blocks.SAND, Blocks.SUSPICIOUS_SAND, BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY);
/*  56 */   static final StructureProcessor COLD_SUSPICIOUS_BLOCK_PROCESSOR = archyRuleProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY);
/*     */   
/*  58 */   private static final ResourceLocation[] WARM_RUINS = new ResourceLocation[] { new ResourceLocation("underwater_ruin/warm_1"), new ResourceLocation("underwater_ruin/warm_2"), new ResourceLocation("underwater_ruin/warm_3"), new ResourceLocation("underwater_ruin/warm_4"), new ResourceLocation("underwater_ruin/warm_5"), new ResourceLocation("underwater_ruin/warm_6"), new ResourceLocation("underwater_ruin/warm_7"), new ResourceLocation("underwater_ruin/warm_8") };
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
/*  69 */   private static final ResourceLocation[] RUINS_BRICK = new ResourceLocation[] { new ResourceLocation("underwater_ruin/brick_1"), new ResourceLocation("underwater_ruin/brick_2"), new ResourceLocation("underwater_ruin/brick_3"), new ResourceLocation("underwater_ruin/brick_4"), new ResourceLocation("underwater_ruin/brick_5"), new ResourceLocation("underwater_ruin/brick_6"), new ResourceLocation("underwater_ruin/brick_7"), new ResourceLocation("underwater_ruin/brick_8") };
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
/*  80 */   private static final ResourceLocation[] RUINS_CRACKED = new ResourceLocation[] { new ResourceLocation("underwater_ruin/cracked_1"), new ResourceLocation("underwater_ruin/cracked_2"), new ResourceLocation("underwater_ruin/cracked_3"), new ResourceLocation("underwater_ruin/cracked_4"), new ResourceLocation("underwater_ruin/cracked_5"), new ResourceLocation("underwater_ruin/cracked_6"), new ResourceLocation("underwater_ruin/cracked_7"), new ResourceLocation("underwater_ruin/cracked_8") };
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
/*  91 */   private static final ResourceLocation[] RUINS_MOSSY = new ResourceLocation[] { new ResourceLocation("underwater_ruin/mossy_1"), new ResourceLocation("underwater_ruin/mossy_2"), new ResourceLocation("underwater_ruin/mossy_3"), new ResourceLocation("underwater_ruin/mossy_4"), new ResourceLocation("underwater_ruin/mossy_5"), new ResourceLocation("underwater_ruin/mossy_6"), new ResourceLocation("underwater_ruin/mossy_7"), new ResourceLocation("underwater_ruin/mossy_8") };
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
/* 102 */   private static final ResourceLocation[] BIG_RUINS_BRICK = new ResourceLocation[] { new ResourceLocation("underwater_ruin/big_brick_1"), new ResourceLocation("underwater_ruin/big_brick_2"), new ResourceLocation("underwater_ruin/big_brick_3"), new ResourceLocation("underwater_ruin/big_brick_8") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static final ResourceLocation[] BIG_RUINS_MOSSY = new ResourceLocation[] { new ResourceLocation("underwater_ruin/big_mossy_1"), new ResourceLocation("underwater_ruin/big_mossy_2"), new ResourceLocation("underwater_ruin/big_mossy_3"), new ResourceLocation("underwater_ruin/big_mossy_8") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   private static final ResourceLocation[] BIG_RUINS_CRACKED = new ResourceLocation[] { new ResourceLocation("underwater_ruin/big_cracked_1"), new ResourceLocation("underwater_ruin/big_cracked_2"), new ResourceLocation("underwater_ruin/big_cracked_3"), new ResourceLocation("underwater_ruin/big_cracked_8") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   private static final ResourceLocation[] BIG_WARM_RUINS = new ResourceLocation[] { new ResourceLocation("underwater_ruin/big_warm_4"), new ResourceLocation("underwater_ruin/big_warm_5"), new ResourceLocation("underwater_ruin/big_warm_6"), new ResourceLocation("underwater_ruin/big_warm_7") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static StructureProcessor archyRuleProcessor(Block $$0, Block $$1, ResourceLocation $$2) {
/* 131 */     return (StructureProcessor)new CappedProcessor((StructureProcessor)new RuleProcessor(
/*     */           
/* 133 */           List.of(new ProcessorRule((RuleTest)new BlockMatchTest($$0), (RuleTest)AlwaysTrueTest.INSTANCE, (PosRuleTest)PosAlwaysTrueTest.INSTANCE, $$1
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 138 */               .defaultBlockState(), (RuleBlockEntityModifier)new AppendLoot($$2)))), 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 143 */         (IntProvider)ConstantInt.of(5));
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation getSmallWarmRuin(RandomSource $$0) {
/* 148 */     return (ResourceLocation)Util.getRandom((Object[])WARM_RUINS, $$0);
/*     */   }
/*     */   
/*     */   private static ResourceLocation getBigWarmRuin(RandomSource $$0) {
/* 152 */     return (ResourceLocation)Util.getRandom((Object[])BIG_WARM_RUINS, $$0);
/*     */   }
/*     */   
/*     */   public static void addPieces(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2, StructurePieceAccessor $$3, RandomSource $$4, OceanRuinStructure $$5) {
/* 156 */     boolean $$6 = ($$4.nextFloat() <= $$5.largeProbability);
/* 157 */     float $$7 = $$6 ? 0.9F : 0.8F;
/*     */     
/* 159 */     addPiece($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     
/* 161 */     if ($$6 && $$4.nextFloat() <= $$5.clusterProbability) {
/* 162 */       addClusterRuins($$0, $$4, $$2, $$1, $$5, $$3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addClusterRuins(StructureTemplateManager $$0, RandomSource $$1, Rotation $$2, BlockPos $$3, OceanRuinStructure $$4, StructurePieceAccessor $$5) {
/* 168 */     BlockPos $$6 = new BlockPos($$3.getX(), 90, $$3.getZ());
/* 169 */     BlockPos $$7 = StructureTemplate.transform(new BlockPos(15, 0, 15), Mirror.NONE, $$2, BlockPos.ZERO).offset((Vec3i)$$6);
/* 170 */     BoundingBox $$8 = BoundingBox.fromCorners((Vec3i)$$6, (Vec3i)$$7);
/* 171 */     BlockPos $$9 = new BlockPos(Math.min($$6.getX(), $$7.getX()), $$6.getY(), Math.min($$6.getZ(), $$7.getZ()));
/* 172 */     List<BlockPos> $$10 = allPositions($$1, $$9);
/* 173 */     int $$11 = Mth.nextInt($$1, 4, 8);
/*     */     
/* 175 */     for (int $$12 = 0; $$12 < $$11; $$12++) {
/* 176 */       if (!$$10.isEmpty()) {
/* 177 */         int $$13 = $$1.nextInt($$10.size());
/* 178 */         BlockPos $$14 = $$10.remove($$13);
/* 179 */         Rotation $$15 = Rotation.getRandom($$1);
/* 180 */         BlockPos $$16 = StructureTemplate.transform(new BlockPos(5, 0, 6), Mirror.NONE, $$15, BlockPos.ZERO).offset((Vec3i)$$14);
/* 181 */         BoundingBox $$17 = BoundingBox.fromCorners((Vec3i)$$14, (Vec3i)$$16);
/* 182 */         if (!$$17.intersects($$8))
/*     */         {
/*     */ 
/*     */           
/* 186 */           addPiece($$0, $$14, $$15, $$5, $$1, $$4, false, 0.8F); } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<BlockPos> allPositions(RandomSource $$0, BlockPos $$1) {
/* 192 */     List<BlockPos> $$2 = Lists.newArrayList();
/* 193 */     $$2.add($$1.offset(-16 + Mth.nextInt($$0, 1, 8), 0, 16 + Mth.nextInt($$0, 1, 7)));
/* 194 */     $$2.add($$1.offset(-16 + Mth.nextInt($$0, 1, 8), 0, Mth.nextInt($$0, 1, 7)));
/* 195 */     $$2.add($$1.offset(-16 + Mth.nextInt($$0, 1, 8), 0, -16 + Mth.nextInt($$0, 4, 8)));
/* 196 */     $$2.add($$1.offset(Mth.nextInt($$0, 1, 7), 0, 16 + Mth.nextInt($$0, 1, 7)));
/* 197 */     $$2.add($$1.offset(Mth.nextInt($$0, 1, 7), 0, -16 + Mth.nextInt($$0, 4, 6)));
/* 198 */     $$2.add($$1.offset(16 + Mth.nextInt($$0, 1, 7), 0, 16 + Mth.nextInt($$0, 3, 8)));
/* 199 */     $$2.add($$1.offset(16 + Mth.nextInt($$0, 1, 7), 0, Mth.nextInt($$0, 1, 7)));
/* 200 */     $$2.add($$1.offset(16 + Mth.nextInt($$0, 1, 7), 0, -16 + Mth.nextInt($$0, 4, 8)));
/*     */     
/* 202 */     return $$2;
/*     */   }
/*     */   private static void addPiece(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2, StructurePieceAccessor $$3, RandomSource $$4, OceanRuinStructure $$5, boolean $$6, float $$7) {
/*     */     ResourceLocation $$8;
/* 206 */     switch ($$5.biomeTemp) {
/*     */       
/*     */       default:
/* 209 */         $$8 = $$6 ? getBigWarmRuin($$4) : getSmallWarmRuin($$4);
/* 210 */         $$3.addPiece((StructurePiece)new OceanRuinPiece($$0, $$8, $$1, $$2, $$7, $$5.biomeTemp, $$6)); return;
/*     */       case COLD:
/*     */         break;
/* 213 */     }  ResourceLocation[] $$9 = $$6 ? BIG_RUINS_BRICK : RUINS_BRICK;
/* 214 */     ResourceLocation[] $$10 = $$6 ? BIG_RUINS_CRACKED : RUINS_CRACKED;
/* 215 */     ResourceLocation[] $$11 = $$6 ? BIG_RUINS_MOSSY : RUINS_MOSSY;
/*     */     
/* 217 */     int $$12 = $$4.nextInt($$9.length);
/* 218 */     $$3.addPiece((StructurePiece)new OceanRuinPiece($$0, $$9[$$12], $$1, $$2, $$7, $$5.biomeTemp, $$6));
/* 219 */     $$3.addPiece((StructurePiece)new OceanRuinPiece($$0, $$10[$$12], $$1, $$2, 0.7F, $$5.biomeTemp, $$6));
/* 220 */     $$3.addPiece((StructurePiece)new OceanRuinPiece($$0, $$11[$$12], $$1, $$2, 0.5F, $$5.biomeTemp, $$6));
/*     */   }
/*     */   
/*     */   public static class OceanRuinPiece
/*     */     extends TemplateStructurePiece
/*     */   {
/*     */     private final OceanRuinStructure.Type biomeType;
/*     */     private final float integrity;
/*     */     private final boolean isLarge;
/*     */     
/*     */     public OceanRuinPiece(StructureTemplateManager $$0, ResourceLocation $$1, BlockPos $$2, Rotation $$3, float $$4, OceanRuinStructure.Type $$5, boolean $$6) {
/* 231 */       super(StructurePieceType.OCEAN_RUIN, 0, $$0, $$1, $$1.toString(), makeSettings($$3, $$4, $$5), $$2);
/*     */       
/* 233 */       this.integrity = $$4;
/* 234 */       this.biomeType = $$5;
/* 235 */       this.isLarge = $$6;
/*     */     }
/*     */     
/*     */     private OceanRuinPiece(StructureTemplateManager $$0, CompoundTag $$1, Rotation $$2, float $$3, OceanRuinStructure.Type $$4, boolean $$5) {
/* 239 */       super(StructurePieceType.OCEAN_RUIN, $$1, $$0, $$3 -> makeSettings($$0, $$1, $$2));
/*     */       
/* 241 */       this.integrity = $$3;
/* 242 */       this.biomeType = $$4;
/* 243 */       this.isLarge = $$5;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static StructurePlaceSettings makeSettings(Rotation $$0, float $$1, OceanRuinStructure.Type $$2) {
/* 249 */       StructureProcessor $$3 = ($$2 == OceanRuinStructure.Type.COLD) ? OceanRuinPieces.COLD_SUSPICIOUS_BLOCK_PROCESSOR : OceanRuinPieces.WARM_SUSPICIOUS_BLOCK_PROCESSOR;
/*     */       
/* 251 */       return (new StructurePlaceSettings())
/* 252 */         .setRotation($$0)
/* 253 */         .setMirror(Mirror.NONE)
/* 254 */         .addProcessor((StructureProcessor)new BlockRotProcessor($$1))
/* 255 */         .addProcessor((StructureProcessor)BlockIgnoreProcessor.STRUCTURE_AND_AIR)
/* 256 */         .addProcessor($$3);
/*     */     }
/*     */     
/*     */     public static OceanRuinPiece create(StructureTemplateManager $$0, CompoundTag $$1) {
/* 260 */       Rotation $$2 = Rotation.valueOf($$1.getString("Rot"));
/* 261 */       float $$3 = $$1.getFloat("Integrity");
/* 262 */       OceanRuinStructure.Type $$4 = OceanRuinStructure.Type.valueOf($$1.getString("BiomeType"));
/* 263 */       boolean $$5 = $$1.getBoolean("IsLarge");
/* 264 */       return new OceanRuinPiece($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 269 */       super.addAdditionalSaveData($$0, $$1);
/* 270 */       $$1.putString("Rot", this.placeSettings.getRotation().name());
/* 271 */       $$1.putFloat("Integrity", this.integrity);
/* 272 */       $$1.putString("BiomeType", this.biomeType.toString());
/* 273 */       $$1.putBoolean("IsLarge", this.isLarge);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void handleDataMarker(String $$0, BlockPos $$1, ServerLevelAccessor $$2, RandomSource $$3, BoundingBox $$4) {
/* 278 */       if ("chest".equals($$0)) {
/* 279 */         $$2.setBlock($$1, (BlockState)Blocks.CHEST.defaultBlockState().setValue((Property)ChestBlock.WATERLOGGED, Boolean.valueOf($$2.getFluidState($$1).is(FluidTags.WATER))), 2);
/*     */         
/* 281 */         BlockEntity $$5 = $$2.getBlockEntity($$1);
/* 282 */         if ($$5 instanceof ChestBlockEntity) {
/* 283 */           ((ChestBlockEntity)$$5).setLootTable(this.isLarge ? BuiltInLootTables.UNDERWATER_RUIN_BIG : BuiltInLootTables.UNDERWATER_RUIN_SMALL, $$3.nextLong());
/*     */         }
/*     */       }
/* 286 */       else if ("drowned".equals($$0)) {
/* 287 */         Drowned $$6 = (Drowned)EntityType.DROWNED.create((Level)$$2.getLevel());
/* 288 */         if ($$6 != null) {
/* 289 */           $$6.setPersistenceRequired();
/* 290 */           $$6.moveTo($$1, 0.0F, 0.0F);
/* 291 */           $$6.finalizeSpawn($$2, $$2.getCurrentDifficultyAt($$1), MobSpawnType.STRUCTURE, null, null);
/* 292 */           $$2.addFreshEntityWithPassengers((Entity)$$6);
/* 293 */           if ($$1.getY() > $$2.getSeaLevel()) {
/* 294 */             $$2.setBlock($$1, Blocks.AIR.defaultBlockState(), 2);
/*     */           } else {
/* 296 */             $$2.setBlock($$1, Blocks.WATER.defaultBlockState(), 2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 304 */       int $$7 = $$0.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, this.templatePosition.getX(), this.templatePosition.getZ());
/* 305 */       this.templatePosition = new BlockPos(this.templatePosition.getX(), $$7, this.templatePosition.getZ());
/* 306 */       BlockPos $$8 = StructureTemplate.transform(new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1), Mirror.NONE, this.placeSettings.getRotation(), BlockPos.ZERO).offset((Vec3i)this.templatePosition);
/* 307 */       this.templatePosition = new BlockPos(this.templatePosition.getX(), getHeight(this.templatePosition, (BlockGetter)$$0, $$8), this.templatePosition.getZ());
/*     */       
/* 309 */       super.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */     }
/*     */     
/*     */     private int getHeight(BlockPos $$0, BlockGetter $$1, BlockPos $$2) {
/* 313 */       int $$3 = $$0.getY();
/* 314 */       int $$4 = 512;
/* 315 */       int $$5 = $$3 - 1;
/* 316 */       int $$6 = 0;
/* 317 */       for (BlockPos $$7 : BlockPos.betweenClosed($$0, $$2)) {
/* 318 */         int $$8 = $$7.getX();
/* 319 */         int $$9 = $$7.getZ();
/* 320 */         int $$10 = $$0.getY() - 1;
/* 321 */         BlockPos.MutableBlockPos $$11 = new BlockPos.MutableBlockPos($$8, $$10, $$9);
/* 322 */         BlockState $$12 = $$1.getBlockState((BlockPos)$$11);
/* 323 */         FluidState $$13 = $$1.getFluidState((BlockPos)$$11);
/* 324 */         while (($$12.isAir() || $$13.is(FluidTags.WATER) || $$12.is(BlockTags.ICE)) && $$10 > $$1.getMinBuildHeight() + 1) {
/* 325 */           $$10--;
/* 326 */           $$11.set($$8, $$10, $$9);
/* 327 */           $$12 = $$1.getBlockState((BlockPos)$$11);
/* 328 */           $$13 = $$1.getFluidState((BlockPos)$$11);
/*     */         } 
/*     */         
/* 331 */         $$4 = Math.min($$4, $$10);
/* 332 */         if ($$10 < $$5 - 2) {
/* 333 */           $$6++;
/*     */         }
/*     */       } 
/*     */       
/* 337 */       int $$14 = Math.abs($$0.getX() - $$2.getX());
/* 338 */       if ($$5 - $$4 > 2 && $$6 > $$14 - 2) {
/* 339 */         $$3 = $$4 + 1;
/*     */       }
/*     */       
/* 342 */       return $$3;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\OceanRuinPieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */