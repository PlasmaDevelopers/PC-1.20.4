/*     */ package net.minecraft.world.item;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.BaseCoralWallFanBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.BonemealableBlock;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ public class BoneMealItem extends Item {
/*     */   public static final int GRASS_SPREAD_WIDTH = 3;
/*     */   
/*     */   public BoneMealItem(Item.Properties $$0) {
/*  34 */     super($$0);
/*     */   }
/*     */   public static final int GRASS_SPREAD_HEIGHT = 1; public static final int GRASS_COUNT_MULTIPLIER = 3;
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  39 */     Level $$1 = $$0.getLevel();
/*  40 */     BlockPos $$2 = $$0.getClickedPos();
/*  41 */     BlockPos $$3 = $$2.relative($$0.getClickedFace());
/*     */ 
/*     */     
/*  44 */     if (growCrop($$0.getItemInHand(), $$1, $$2)) {
/*  45 */       if (!$$1.isClientSide) {
/*  46 */         $$0.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
/*  47 */         $$1.levelEvent(1505, $$2, 0);
/*     */       } 
/*  49 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */ 
/*     */     
/*  53 */     BlockState $$4 = $$1.getBlockState($$2);
/*  54 */     boolean $$5 = $$4.isFaceSturdy((BlockGetter)$$1, $$2, $$0.getClickedFace());
/*  55 */     if ($$5 && 
/*  56 */       growWaterPlant($$0.getItemInHand(), $$1, $$3, $$0.getClickedFace())) {
/*  57 */       if (!$$1.isClientSide) {
/*  58 */         $$0.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
/*  59 */         $$1.levelEvent(1505, $$3, 0);
/*     */       } 
/*  61 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */ 
/*     */     
/*  65 */     return InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   public static boolean growCrop(ItemStack $$0, Level $$1, BlockPos $$2) {
/*  69 */     BlockState $$3 = $$1.getBlockState($$2);
/*     */     
/*  71 */     Block block = $$3.getBlock(); if (block instanceof BonemealableBlock) { BonemealableBlock $$4 = (BonemealableBlock)block;
/*     */       
/*  73 */       if ($$4.isValidBonemealTarget((LevelReader)$$1, $$2, $$3)) {
/*  74 */         if ($$1 instanceof ServerLevel) {
/*  75 */           if ($$4.isBonemealSuccess($$1, $$1.random, $$2, $$3)) {
/*  76 */             $$4.performBonemeal((ServerLevel)$$1, $$1.random, $$2, $$3);
/*     */           }
/*  78 */           $$0.shrink(1);
/*     */         } 
/*  80 */         return true;
/*     */       }  }
/*     */     
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean growWaterPlant(ItemStack $$0, Level $$1, BlockPos $$2, @Nullable Direction $$3) {
/*  87 */     if (!$$1.getBlockState($$2).is(Blocks.WATER) || $$1.getFluidState($$2).getAmount() != 8) {
/*  88 */       return false;
/*     */     }
/*     */     
/*  91 */     if (!($$1 instanceof ServerLevel)) {
/*  92 */       return true;
/*     */     }
/*     */     
/*  95 */     RandomSource $$4 = $$1.getRandom();
/*     */     
/*     */     int $$5;
/*  98 */     label48: for ($$5 = 0; $$5 < 128; $$5++) {
/*  99 */       BlockPos $$6 = $$2;
/* 100 */       BlockState $$7 = Blocks.SEAGRASS.defaultBlockState();
/*     */       
/* 102 */       for (int $$8 = 0; $$8 < $$5 / 16; $$8++) {
/* 103 */         $$6 = $$6.offset($$4.nextInt(3) - 1, ($$4.nextInt(3) - 1) * $$4.nextInt(3) / 2, $$4.nextInt(3) - 1);
/*     */         
/* 105 */         if ($$1.getBlockState($$6).isCollisionShapeFullBlock((BlockGetter)$$1, $$6)) {
/*     */           continue label48;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 111 */       Holder<Biome> $$9 = $$1.getBiome($$6);
/* 112 */       if ($$9.is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
/* 113 */         if ($$5 == 0 && $$3 != null && $$3.getAxis().isHorizontal()) {
/*     */           
/* 115 */           $$7 = BuiltInRegistries.BLOCK.getTag(BlockTags.WALL_CORALS).flatMap($$1 -> $$1.getRandomElement($$0.random)).map($$0 -> ((Block)$$0.value()).defaultBlockState()).orElse($$7);
/* 116 */           if ($$7.hasProperty((Property)BaseCoralWallFanBlock.FACING)) {
/* 117 */             $$7 = (BlockState)$$7.setValue((Property)BaseCoralWallFanBlock.FACING, (Comparable)$$3);
/*     */           }
/* 119 */         } else if ($$4.nextInt(4) == 0) {
/* 120 */           $$7 = BuiltInRegistries.BLOCK.getTag(BlockTags.UNDERWATER_BONEMEALS).flatMap($$1 -> $$1.getRandomElement($$0.random)).map($$0 -> ((Block)$$0.value()).defaultBlockState()).orElse($$7);
/*     */         } 
/*     */       }
/*     */       
/* 124 */       if ($$7.is(BlockTags.WALL_CORALS, $$0 -> $$0.hasProperty((Property)BaseCoralWallFanBlock.FACING))) {
/* 125 */         int $$10 = 0;
/* 126 */         while (!$$7.canSurvive((LevelReader)$$1, $$6) && $$10 < 4) {
/* 127 */           $$7 = (BlockState)$$7.setValue((Property)BaseCoralWallFanBlock.FACING, (Comparable)Direction.Plane.HORIZONTAL.getRandomDirection($$4));
/* 128 */           $$10++;
/*     */         } 
/*     */       } 
/*     */       
/* 132 */       if ($$7.canSurvive((LevelReader)$$1, $$6)) {
/*     */ 
/*     */ 
/*     */         
/* 136 */         BlockState $$11 = $$1.getBlockState($$6);
/* 137 */         if ($$11.is(Blocks.WATER) && $$1.getFluidState($$6).getAmount() == 8) {
/* 138 */           $$1.setBlock($$6, $$7, 3);
/*     */         
/*     */         }
/* 141 */         else if ($$11.is(Blocks.SEAGRASS) && $$4.nextInt(10) == 0) {
/* 142 */           ((BonemealableBlock)Blocks.SEAGRASS).performBonemeal((ServerLevel)$$1, $$4, $$6, $$11);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     $$0.shrink(1);
/* 148 */     return true;
/*     */   }
/*     */   public static void addGrowthParticles(LevelAccessor $$0, BlockPos $$1, int $$2) {
/*     */     double $$7;
/* 152 */     if ($$2 == 0) {
/* 153 */       $$2 = 15;
/*     */     }
/*     */     
/* 156 */     BlockState $$3 = $$0.getBlockState($$1);
/* 157 */     if ($$3.isAir()) {
/*     */       return;
/*     */     }
/*     */     
/* 161 */     double $$4 = 0.5D;
/*     */     
/* 163 */     if ($$3.is(Blocks.WATER)) {
/* 164 */       $$2 *= 3;
/* 165 */       double $$5 = 1.0D;
/* 166 */       $$4 = 3.0D;
/* 167 */     } else if ($$3.isSolidRender((BlockGetter)$$0, $$1)) {
/* 168 */       $$1 = $$1.above();
/* 169 */       $$2 *= 3;
/* 170 */       $$4 = 3.0D;
/* 171 */       double $$6 = 1.0D;
/*     */     } else {
/* 173 */       $$7 = $$3.getShape((BlockGetter)$$0, $$1).max(Direction.Axis.Y);
/*     */     } 
/*     */     
/* 176 */     $$0.addParticle((ParticleOptions)ParticleTypes.HAPPY_VILLAGER, $$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
/*     */     
/* 178 */     RandomSource $$8 = $$0.getRandom();
/* 179 */     for (int $$9 = 0; $$9 < $$2; $$9++) {
/* 180 */       double $$10 = $$8.nextGaussian() * 0.02D;
/* 181 */       double $$11 = $$8.nextGaussian() * 0.02D;
/* 182 */       double $$12 = $$8.nextGaussian() * 0.02D;
/*     */       
/* 184 */       double $$13 = 0.5D - $$4;
/* 185 */       double $$14 = $$1.getX() + $$13 + $$8.nextDouble() * $$4 * 2.0D;
/* 186 */       double $$15 = $$1.getY() + $$8.nextDouble() * $$7;
/* 187 */       double $$16 = $$1.getZ() + $$13 + $$8.nextDouble() * $$4 * 2.0D;
/*     */       
/* 189 */       if (!$$0.getBlockState(BlockPos.containing($$14, $$15, $$16).below()).isAir())
/* 190 */         $$0.addParticle((ParticleOptions)ParticleTypes.HAPPY_VILLAGER, $$14, $$15, $$16, $$10, $$11, $$12); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BoneMealItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */