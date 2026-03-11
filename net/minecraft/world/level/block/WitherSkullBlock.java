/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.boss.wither.WitherBoss;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SkullBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
/*     */ import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
/*     */ 
/*     */ public class WitherSkullBlock extends SkullBlock {
/*  27 */   public static final MapCodec<WitherSkullBlock> CODEC = simpleCodec(WitherSkullBlock::new); @Nullable
/*     */   private static BlockPattern witherPatternFull; @Nullable
/*     */   private static BlockPattern witherPatternBase;
/*     */   public MapCodec<WitherSkullBlock> codec() {
/*  31 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WitherSkullBlock(BlockBehaviour.Properties $$0) {
/*  41 */     super(SkullBlock.Types.WITHER_SKELETON, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {
/*  46 */     super.setPlacedBy($$0, $$1, $$2, $$3, $$4);
/*     */     
/*  48 */     BlockEntity $$5 = $$0.getBlockEntity($$1);
/*  49 */     if ($$5 instanceof SkullBlockEntity) {
/*  50 */       checkSpawn($$0, $$1, (SkullBlockEntity)$$5);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void checkSpawn(Level $$0, BlockPos $$1, SkullBlockEntity $$2) {
/*  55 */     if ($$0.isClientSide) {
/*     */       return;
/*     */     }
/*  58 */     BlockState $$3 = $$2.getBlockState();
/*  59 */     boolean $$4 = ($$3.is(Blocks.WITHER_SKELETON_SKULL) || $$3.is(Blocks.WITHER_SKELETON_WALL_SKULL));
/*  60 */     if (!$$4 || $$1.getY() < $$0.getMinBuildHeight() || $$0.getDifficulty() == Difficulty.PEACEFUL) {
/*     */       return;
/*     */     }
/*     */     
/*  64 */     BlockPattern.BlockPatternMatch $$5 = getOrCreateWitherFull().find((LevelReader)$$0, $$1);
/*  65 */     if ($$5 == null) {
/*     */       return;
/*     */     }
/*     */     
/*  69 */     WitherBoss $$6 = (WitherBoss)EntityType.WITHER.create($$0);
/*  70 */     if ($$6 != null) {
/*  71 */       CarvedPumpkinBlock.clearPatternBlocks($$0, $$5);
/*     */       
/*  73 */       BlockPos $$7 = $$5.getBlock(1, 2, 0).getPos();
/*  74 */       $$6.moveTo($$7.getX() + 0.5D, $$7.getY() + 0.55D, $$7.getZ() + 0.5D, ($$5.getForwards().getAxis() == Direction.Axis.X) ? 0.0F : 90.0F, 0.0F);
/*  75 */       $$6.yBodyRot = ($$5.getForwards().getAxis() == Direction.Axis.X) ? 0.0F : 90.0F;
/*  76 */       $$6.makeInvulnerable();
/*     */       
/*  78 */       for (ServerPlayer $$8 : $$0.getEntitiesOfClass(ServerPlayer.class, $$6.getBoundingBox().inflate(50.0D))) {
/*  79 */         CriteriaTriggers.SUMMONED_ENTITY.trigger($$8, (Entity)$$6);
/*     */       }
/*     */       
/*  82 */       $$0.addFreshEntity((Entity)$$6);
/*     */       
/*  84 */       CarvedPumpkinBlock.updatePatternBlocks($$0, $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean canSpawnMob(Level $$0, BlockPos $$1, ItemStack $$2) {
/*  89 */     if ($$2.is(Items.WITHER_SKELETON_SKULL) && $$1.getY() >= $$0.getMinBuildHeight() + 2 && $$0.getDifficulty() != Difficulty.PEACEFUL && !$$0.isClientSide) {
/*  90 */       return (getOrCreateWitherBase().find((LevelReader)$$0, $$1) != null);
/*     */     }
/*     */     
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   private static BlockPattern getOrCreateWitherFull() {
/*  97 */     if (witherPatternFull == null)
/*     */     {
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
/* 109 */       witherPatternFull = BlockPatternBuilder.start().aisle(new String[] { "^^^", "###", "~#~" }).where('#', $$0 -> $$0.getState().is(BlockTags.WITHER_SUMMON_BASE_BLOCKS)).where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.WITHER_SKELETON_SKULL).or((Predicate)BlockStatePredicate.forBlock(Blocks.WITHER_SKELETON_WALL_SKULL)))).where('~', $$0 -> $$0.getState().isAir()).build();
/*     */     }
/*     */     
/* 112 */     return witherPatternFull;
/*     */   }
/*     */   
/*     */   private static BlockPattern getOrCreateWitherBase() {
/* 116 */     if (witherPatternBase == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 125 */       witherPatternBase = BlockPatternBuilder.start().aisle(new String[] { "   ", "###", "~#~" }).where('#', $$0 -> $$0.getState().is(BlockTags.WITHER_SUMMON_BASE_BLOCKS)).where('~', $$0 -> $$0.getState().isAir()).build();
/*     */     }
/*     */     
/* 128 */     return witherPatternBase;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WitherSkullBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */