/*     */ package net.minecraft.gametest.framework;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.arguments.blocks.BlockInput;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.CommandBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.StructureMode;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StructureUtils
/*     */ {
/*  36 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   public static final String DEFAULT_TEST_STRUCTURES_DIR = "gameteststructures";
/*  38 */   public static String testStructuresDir = "gameteststructures";
/*     */   
/*     */   public static Rotation getRotationForRotationSteps(int $$0) {
/*  41 */     switch ($$0) {
/*     */       case 0:
/*  43 */         return Rotation.NONE;
/*     */       case 1:
/*  45 */         return Rotation.CLOCKWISE_90;
/*     */       case 2:
/*  47 */         return Rotation.CLOCKWISE_180;
/*     */       case 3:
/*  49 */         return Rotation.COUNTERCLOCKWISE_90;
/*     */     } 
/*  51 */     throw new IllegalArgumentException("rotationSteps must be a value from 0-3. Got value " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getRotationStepsForRotation(Rotation $$0) {
/*  56 */     switch ($$0) {
/*     */       case NONE:
/*  58 */         return 0;
/*     */       case CLOCKWISE_90:
/*  60 */         return 1;
/*     */       case CLOCKWISE_180:
/*  62 */         return 2;
/*     */       case COUNTERCLOCKWISE_90:
/*  64 */         return 3;
/*     */     } 
/*  66 */     throw new IllegalArgumentException("Unknown rotation value, don't know how many steps it represents: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AABB getStructureBounds(StructureBlockEntity $$0) {
/*  71 */     return AABB.of(getStructureBoundingBox($$0));
/*     */   }
/*     */   
/*     */   public static BoundingBox getStructureBoundingBox(StructureBlockEntity $$0) {
/*  75 */     BlockPos $$1 = getStructureOrigin($$0);
/*  76 */     BlockPos $$2 = getTransformedFarCorner($$1, $$0.getStructureSize(), $$0.getRotation());
/*     */     
/*  78 */     return BoundingBox.fromCorners((Vec3i)$$1, (Vec3i)$$2);
/*     */   }
/*     */   
/*     */   public static BlockPos getStructureOrigin(StructureBlockEntity $$0) {
/*  82 */     return $$0.getBlockPos().offset((Vec3i)$$0.getStructurePos());
/*     */   }
/*     */   
/*     */   public static void addCommandBlockAndButtonToStartTest(BlockPos $$0, BlockPos $$1, Rotation $$2, ServerLevel $$3) {
/*  86 */     BlockPos $$4 = StructureTemplate.transform($$0.offset((Vec3i)$$1), Mirror.NONE, $$2, $$0);
/*  87 */     $$3.setBlockAndUpdate($$4, Blocks.COMMAND_BLOCK.defaultBlockState());
/*  88 */     CommandBlockEntity $$5 = (CommandBlockEntity)$$3.getBlockEntity($$4);
/*  89 */     $$5.getCommandBlock().setCommand("test runthis");
/*     */     
/*  91 */     BlockPos $$6 = StructureTemplate.transform($$4.offset(0, 0, -1), Mirror.NONE, $$2, $$4);
/*     */     
/*  93 */     $$3.setBlockAndUpdate($$6, Blocks.STONE_BUTTON.defaultBlockState().rotate($$2));
/*     */   }
/*     */   
/*     */   public static void createNewEmptyStructureBlock(String $$0, BlockPos $$1, Vec3i $$2, Rotation $$3, ServerLevel $$4) {
/*  97 */     BoundingBox $$5 = getStructureBoundingBox($$1.above(), $$2, $$3);
/*  98 */     clearSpaceForStructure($$5, $$4);
/*     */     
/* 100 */     $$4.setBlockAndUpdate($$1, Blocks.STRUCTURE_BLOCK.defaultBlockState());
/*     */     
/* 102 */     StructureBlockEntity $$6 = (StructureBlockEntity)$$4.getBlockEntity($$1);
/* 103 */     $$6.setIgnoreEntities(false);
/* 104 */     $$6.setStructureName(new ResourceLocation($$0));
/* 105 */     $$6.setStructureSize($$2);
/* 106 */     $$6.setMode(StructureMode.SAVE);
/* 107 */     $$6.setShowBoundingBox(true);
/*     */   }
/*     */   public static StructureBlockEntity prepareTestStructure(GameTestInfo $$0, BlockPos $$1, Rotation $$2, ServerLevel $$3) {
/*     */     BlockPos $$9;
/* 111 */     Vec3i $$4 = ((StructureTemplate)$$3.getStructureManager().get(new ResourceLocation($$0.getStructureName())).orElseThrow(() -> new IllegalStateException("Missing test structure: " + $$0.getStructureName()))).getSize();
/* 112 */     BoundingBox $$5 = getStructureBoundingBox($$1, $$4, $$2);
/*     */ 
/*     */     
/* 115 */     if ($$2 == Rotation.NONE) {
/* 116 */       BlockPos $$6 = $$1;
/* 117 */     } else if ($$2 == Rotation.CLOCKWISE_90) {
/* 118 */       BlockPos $$7 = $$1.offset($$4.getZ() - 1, 0, 0);
/* 119 */     } else if ($$2 == Rotation.CLOCKWISE_180) {
/* 120 */       BlockPos $$8 = $$1.offset($$4.getX() - 1, 0, $$4.getZ() - 1);
/* 121 */     } else if ($$2 == Rotation.COUNTERCLOCKWISE_90) {
/* 122 */       $$9 = $$1.offset(0, 0, $$4.getX() - 1);
/*     */     } else {
/* 124 */       throw new IllegalArgumentException("Invalid rotation: " + $$2);
/*     */     } 
/*     */     
/* 127 */     forceLoadChunks($$5, $$3);
/* 128 */     clearSpaceForStructure($$5, $$3);
/* 129 */     return createStructureBlock($$0, $$9.below(), $$2, $$3);
/*     */   }
/*     */   
/*     */   private static void forceLoadChunks(BoundingBox $$0, ServerLevel $$1) {
/* 133 */     $$0.intersectingChunks().forEach($$1 -> $$0.setChunkForced($$1.x, $$1.z, true));
/*     */   }
/*     */   
/*     */   public static void clearSpaceForStructure(BoundingBox $$0, ServerLevel $$1) {
/* 137 */     int $$2 = $$0.minY() - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     BoundingBox $$3 = new BoundingBox($$0.minX() - 2, $$0.minY() - 3, $$0.minZ() - 3, $$0.maxX() + 3, $$0.maxY() + 20, $$0.maxZ() + 3);
/*     */ 
/*     */     
/* 148 */     BlockPos.betweenClosedStream($$3).forEach($$2 -> clearBlock($$0, $$2, $$1));
/* 149 */     $$1.getBlockTicks().clearArea($$3);
/* 150 */     $$1.clearBlockEvents($$3);
/* 151 */     AABB $$4 = new AABB($$3.minX(), $$3.minY(), $$3.minZ(), $$3.maxX(), $$3.maxY(), $$3.maxZ());
/* 152 */     List<Entity> $$5 = $$1.getEntitiesOfClass(Entity.class, $$4, $$0 -> !($$0 instanceof net.minecraft.world.entity.player.Player));
/* 153 */     $$5.forEach(Entity::discard);
/*     */   }
/*     */   
/*     */   public static BlockPos getTransformedFarCorner(BlockPos $$0, Vec3i $$1, Rotation $$2) {
/* 157 */     BlockPos $$3 = $$0.offset($$1).offset(-1, -1, -1);
/* 158 */     return StructureTemplate.transform($$3, Mirror.NONE, $$2, $$0);
/*     */   }
/*     */   
/*     */   public static BoundingBox getStructureBoundingBox(BlockPos $$0, Vec3i $$1, Rotation $$2) {
/* 162 */     BlockPos $$3 = getTransformedFarCorner($$0, $$1, $$2);
/* 163 */     BoundingBox $$4 = BoundingBox.fromCorners((Vec3i)$$0, (Vec3i)$$3);
/*     */     
/* 165 */     int $$5 = Math.min($$4.minX(), $$4.maxX());
/* 166 */     int $$6 = Math.min($$4.minZ(), $$4.maxZ());
/*     */ 
/*     */     
/* 169 */     return $$4.move($$0.getX() - $$5, 0, $$0.getZ() - $$6);
/*     */   }
/*     */   
/*     */   public static Optional<BlockPos> findStructureBlockContainingPos(BlockPos $$0, int $$1, ServerLevel $$2) {
/* 173 */     return findStructureBlocks($$0, $$1, $$2).stream()
/* 174 */       .filter($$2 -> doesStructureContain($$2, $$0, $$1))
/* 175 */       .findFirst();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BlockPos findNearestStructureBlock(BlockPos $$0, int $$1, ServerLevel $$2) {
/* 180 */     Comparator<BlockPos> $$3 = Comparator.comparingInt($$1 -> $$1.distManhattan((Vec3i)$$0));
/*     */     
/* 182 */     Collection<BlockPos> $$4 = findStructureBlocks($$0, $$1, $$2);
/* 183 */     Optional<BlockPos> $$5 = $$4.stream().min($$3);
/* 184 */     return $$5.orElse(null);
/*     */   }
/*     */   
/*     */   public static Collection<BlockPos> findStructureBlocks(BlockPos $$0, int $$1, ServerLevel $$2) {
/* 188 */     Collection<BlockPos> $$3 = Lists.newArrayList();
/*     */     
/* 190 */     BoundingBox $$4 = (new BoundingBox($$0)).inflatedBy($$1);
/* 191 */     BlockPos.betweenClosedStream($$4).forEach($$2 -> {
/*     */           if ($$0.getBlockState($$2).is(Blocks.STRUCTURE_BLOCK)) {
/*     */             $$1.add($$2.immutable());
/*     */           }
/*     */         });
/* 196 */     return $$3;
/*     */   }
/*     */   
/*     */   private static StructureBlockEntity createStructureBlock(GameTestInfo $$0, BlockPos $$1, Rotation $$2, ServerLevel $$3) {
/* 200 */     $$3.setBlockAndUpdate($$1, Blocks.STRUCTURE_BLOCK.defaultBlockState());
/*     */     
/* 202 */     StructureBlockEntity $$4 = (StructureBlockEntity)$$3.getBlockEntity($$1);
/* 203 */     $$4.setMode(StructureMode.LOAD);
/* 204 */     $$4.setRotation($$2);
/* 205 */     $$4.setIgnoreEntities(false);
/* 206 */     $$4.setStructureName(new ResourceLocation($$0.getStructureName()));
/* 207 */     $$4.setMetaData($$0.getTestName());
/*     */     
/* 209 */     if (!$$4.loadStructureInfo($$3)) {
/* 210 */       throw new RuntimeException("Failed to load structure info for test: " + $$0.getTestName() + ". Structure name: " + $$0.getStructureName());
/*     */     }
/* 212 */     return $$4;
/*     */   }
/*     */   
/*     */   private static void clearBlock(int $$0, BlockPos $$1, ServerLevel $$2) {
/*     */     BlockState $$4;
/* 217 */     if ($$1.getY() < $$0) {
/* 218 */       BlockState $$3 = Blocks.STONE.defaultBlockState();
/*     */     } else {
/* 220 */       $$4 = Blocks.AIR.defaultBlockState();
/*     */     } 
/* 222 */     BlockInput $$5 = new BlockInput($$4, Collections.emptySet(), null);
/* 223 */     $$5.place($$2, $$1, 2);
/* 224 */     $$2.blockUpdated($$1, $$4.getBlock());
/*     */   }
/*     */   
/*     */   private static boolean doesStructureContain(BlockPos $$0, BlockPos $$1, ServerLevel $$2) {
/* 228 */     StructureBlockEntity $$3 = (StructureBlockEntity)$$2.getBlockEntity($$0);
/* 229 */     return getStructureBoundingBox($$3).isInside((Vec3i)$$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\StructureUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */