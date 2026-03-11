/*     */ package net.minecraft.world.level.levelgen.blockpredicates;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.List;
/*     */ import java.util.function.BiPredicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ 
/*     */ public interface BlockPredicate
/*     */   extends BiPredicate<WorldGenLevel, BlockPos> {
/*  21 */   public static final Codec<BlockPredicate> CODEC = BuiltInRegistries.BLOCK_PREDICATE_TYPE.byNameCodec().dispatch(BlockPredicate::type, BlockPredicateType::codec);
/*     */ 
/*     */ 
/*     */   
/*  25 */   public static final BlockPredicate ONLY_IN_AIR_PREDICATE = matchesBlocks(new Block[] { Blocks.AIR });
/*  26 */   public static final BlockPredicate ONLY_IN_AIR_OR_WATER_PREDICATE = matchesBlocks(new Block[] { Blocks.AIR, Blocks.WATER });
/*     */   
/*     */   BlockPredicateType<?> type();
/*     */   
/*     */   static BlockPredicate allOf(List<BlockPredicate> $$0) {
/*  31 */     return new AllOfPredicate($$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate allOf(BlockPredicate... $$0) {
/*  35 */     return allOf(List.of($$0));
/*     */   }
/*     */   
/*     */   static BlockPredicate allOf(BlockPredicate $$0, BlockPredicate $$1) {
/*  39 */     return allOf(List.of($$0, $$1));
/*     */   }
/*     */   
/*     */   static BlockPredicate anyOf(List<BlockPredicate> $$0) {
/*  43 */     return new AnyOfPredicate($$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate anyOf(BlockPredicate... $$0) {
/*  47 */     return anyOf(List.of($$0));
/*     */   }
/*     */   
/*     */   static BlockPredicate anyOf(BlockPredicate $$0, BlockPredicate $$1) {
/*  51 */     return anyOf(List.of($$0, $$1));
/*     */   }
/*     */   
/*     */   static BlockPredicate matchesBlocks(Vec3i $$0, List<Block> $$1) {
/*  55 */     return new MatchingBlocksPredicate($$0, (HolderSet<Block>)HolderSet.direct(Block::builtInRegistryHolder, $$1));
/*     */   }
/*     */   
/*     */   static BlockPredicate matchesBlocks(List<Block> $$0) {
/*  59 */     return matchesBlocks(Vec3i.ZERO, $$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate matchesBlocks(Vec3i $$0, Block... $$1) {
/*  63 */     return matchesBlocks($$0, List.of($$1));
/*     */   }
/*     */   
/*     */   static BlockPredicate matchesBlocks(Block... $$0) {
/*  67 */     return matchesBlocks(Vec3i.ZERO, $$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate matchesTag(Vec3i $$0, TagKey<Block> $$1) {
/*  71 */     return new MatchingBlockTagPredicate($$0, $$1);
/*     */   }
/*     */   
/*     */   static BlockPredicate matchesTag(TagKey<Block> $$0) {
/*  75 */     return matchesTag(Vec3i.ZERO, $$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate matchesFluids(Vec3i $$0, List<Fluid> $$1) {
/*  79 */     return new MatchingFluidsPredicate($$0, (HolderSet<Fluid>)HolderSet.direct(Fluid::builtInRegistryHolder, $$1));
/*     */   }
/*     */   
/*     */   static BlockPredicate matchesFluids(Vec3i $$0, Fluid... $$1) {
/*  83 */     return matchesFluids($$0, List.of($$1));
/*     */   }
/*     */   
/*     */   static BlockPredicate matchesFluids(Fluid... $$0) {
/*  87 */     return matchesFluids(Vec3i.ZERO, $$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate not(BlockPredicate $$0) {
/*  91 */     return new NotPredicate($$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate replaceable(Vec3i $$0) {
/*  95 */     return new ReplaceablePredicate($$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate replaceable() {
/*  99 */     return replaceable(Vec3i.ZERO);
/*     */   }
/*     */   
/*     */   static BlockPredicate wouldSurvive(BlockState $$0, Vec3i $$1) {
/* 103 */     return new WouldSurvivePredicate($$1, $$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate hasSturdyFace(Vec3i $$0, Direction $$1) {
/* 107 */     return new HasSturdyFacePredicate($$0, $$1);
/*     */   }
/*     */   
/*     */   static BlockPredicate hasSturdyFace(Direction $$0) {
/* 111 */     return hasSturdyFace(Vec3i.ZERO, $$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate solid(Vec3i $$0) {
/* 115 */     return new SolidPredicate($$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate solid() {
/* 119 */     return solid(Vec3i.ZERO);
/*     */   }
/*     */   
/*     */   static BlockPredicate noFluid() {
/* 123 */     return noFluid(Vec3i.ZERO);
/*     */   }
/*     */   
/*     */   static BlockPredicate noFluid(Vec3i $$0) {
/* 127 */     return matchesFluids($$0, new Fluid[] { Fluids.EMPTY });
/*     */   }
/*     */   
/*     */   static BlockPredicate insideWorld(Vec3i $$0) {
/* 131 */     return new InsideWorldBoundsPredicate($$0);
/*     */   }
/*     */   
/*     */   static BlockPredicate alwaysTrue() {
/* 135 */     return TrueBlockPredicate.INSTANCE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\BlockPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */