/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.RandomizableContainer;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class MonsterRoomFeature
/*     */   extends Feature<NoneFeatureConfiguration> {
/*  26 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  27 */   private static final EntityType<?>[] MOBS = new EntityType[] { EntityType.SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.SPIDER };
/*  28 */   private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();
/*     */   
/*     */   public MonsterRoomFeature(Codec<NoneFeatureConfiguration> $$0) {
/*  31 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/*  36 */     Predicate<BlockState> $$1 = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);
/*  37 */     BlockPos $$2 = $$0.origin();
/*  38 */     RandomSource $$3 = $$0.random();
/*  39 */     WorldGenLevel $$4 = $$0.level();
/*  40 */     int $$5 = 3;
/*  41 */     int $$6 = $$3.nextInt(2) + 2;
/*  42 */     int $$7 = -$$6 - 1;
/*  43 */     int $$8 = $$6 + 1;
/*     */     
/*  45 */     int $$9 = -1;
/*  46 */     int $$10 = 4;
/*     */     
/*  48 */     int $$11 = $$3.nextInt(2) + 2;
/*  49 */     int $$12 = -$$11 - 1;
/*  50 */     int $$13 = $$11 + 1;
/*     */     
/*  52 */     int $$14 = 0;
/*  53 */     for (int $$15 = $$7; $$15 <= $$8; $$15++) {
/*  54 */       for (int $$16 = -1; $$16 <= 4; $$16++) {
/*  55 */         for (int $$17 = $$12; $$17 <= $$13; $$17++) {
/*  56 */           BlockPos $$18 = $$2.offset($$15, $$16, $$17);
/*  57 */           boolean $$19 = $$4.getBlockState($$18).isSolid();
/*     */           
/*  59 */           if ($$16 == -1 && !$$19) {
/*  60 */             return false;
/*     */           }
/*  62 */           if ($$16 == 4 && !$$19) {
/*  63 */             return false;
/*     */           }
/*     */           
/*  66 */           if (($$15 == $$7 || $$15 == $$8 || $$17 == $$12 || $$17 == $$13) && 
/*  67 */             $$16 == 0 && $$4.isEmptyBlock($$18) && $$4.isEmptyBlock($$18.above())) {
/*  68 */             $$14++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  75 */     if ($$14 < 1 || $$14 > 5) {
/*  76 */       return false;
/*     */     }
/*     */     
/*  79 */     for (int $$20 = $$7; $$20 <= $$8; $$20++) {
/*  80 */       for (int $$21 = 3; $$21 >= -1; $$21--) {
/*  81 */         for (int $$22 = $$12; $$22 <= $$13; $$22++) {
/*  82 */           BlockPos $$23 = $$2.offset($$20, $$21, $$22);
/*     */           
/*  84 */           BlockState $$24 = $$4.getBlockState($$23);
/*  85 */           if ($$20 == $$7 || $$21 == -1 || $$22 == $$12 || $$20 == $$8 || $$21 == 4 || $$22 == $$13) {
/*  86 */             if ($$23.getY() >= $$4.getMinBuildHeight() && !$$4.getBlockState($$23.below()).isSolid()) {
/*  87 */               $$4.setBlock($$23, AIR, 2);
/*  88 */             } else if ($$24.isSolid() && 
/*  89 */               !$$24.is(Blocks.CHEST)) {
/*  90 */               if ($$21 == -1 && $$3.nextInt(4) != 0) {
/*  91 */                 safeSetBlock($$4, $$23, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), $$1);
/*     */               } else {
/*  93 */                 safeSetBlock($$4, $$23, Blocks.COBBLESTONE.defaultBlockState(), $$1);
/*     */               }
/*     */             
/*     */             }
/*     */           
/*  98 */           } else if (!$$24.is(Blocks.CHEST) && !$$24.is(Blocks.SPAWNER)) {
/*  99 */             safeSetBlock($$4, $$23, AIR, $$1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 106 */     for (int $$25 = 0; $$25 < 2; $$25++) {
/* 107 */       for (int $$26 = 0; $$26 < 3; $$26++) {
/* 108 */         int $$27 = $$2.getX() + $$3.nextInt($$6 * 2 + 1) - $$6;
/* 109 */         int $$28 = $$2.getY();
/* 110 */         int $$29 = $$2.getZ() + $$3.nextInt($$11 * 2 + 1) - $$11;
/* 111 */         BlockPos $$30 = new BlockPos($$27, $$28, $$29);
/*     */         
/* 113 */         if ($$4.isEmptyBlock($$30)) {
/*     */ 
/*     */ 
/*     */           
/* 117 */           int $$31 = 0;
/* 118 */           for (Direction $$32 : Direction.Plane.HORIZONTAL) {
/* 119 */             if ($$4.getBlockState($$30.relative($$32)).isSolid()) {
/* 120 */               $$31++;
/*     */             }
/*     */           } 
/*     */           
/* 124 */           if ($$31 == 1) {
/*     */ 
/*     */ 
/*     */             
/* 128 */             safeSetBlock($$4, $$30, StructurePiece.reorient((BlockGetter)$$4, $$30, Blocks.CHEST.defaultBlockState()), $$1);
/* 129 */             RandomizableContainer.setBlockEntityLootTable((BlockGetter)$$4, $$3, $$30, BuiltInLootTables.SIMPLE_DUNGEON);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 135 */     safeSetBlock($$4, $$2, Blocks.SPAWNER.defaultBlockState(), $$1);
/* 136 */     BlockEntity $$33 = $$4.getBlockEntity($$2);
/*     */     
/* 138 */     if ($$33 instanceof SpawnerBlockEntity) { SpawnerBlockEntity $$34 = (SpawnerBlockEntity)$$33;
/* 139 */       $$34.setEntityId(randomEntityId($$3), $$3); }
/*     */     else
/* 141 */     { LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", new Object[] { Integer.valueOf($$2.getX()), Integer.valueOf($$2.getY()), Integer.valueOf($$2.getZ()) }); }
/*     */ 
/*     */     
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   private EntityType<?> randomEntityId(RandomSource $$0) {
/* 148 */     return (EntityType)Util.getRandom((Object[])MOBS, $$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\MonsterRoomFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */