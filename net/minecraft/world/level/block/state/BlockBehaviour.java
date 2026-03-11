/*      */ package net.minecraft.world.level.block.state;
/*      */ 
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.mojang.datafixers.kinds.App;
/*      */ import com.mojang.datafixers.kinds.Applicative;
/*      */ import com.mojang.serialization.Codec;
/*      */ import com.mojang.serialization.MapCodec;
/*      */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Optional;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.ToIntFunction;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.HolderSet;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.network.protocol.game.DebugPackets;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.tags.FluidTags;
/*      */ import net.minecraft.tags.TagKey;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.MenuProvider;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.projectile.Projectile;
/*      */ import net.minecraft.world.flag.FeatureElement;
/*      */ import net.minecraft.world.flag.FeatureFlag;
/*      */ import net.minecraft.world.flag.FeatureFlagSet;
/*      */ import net.minecraft.world.flag.FeatureFlags;
/*      */ import net.minecraft.world.item.DyeColor;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.context.BlockPlaceContext;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.EmptyBlockGetter;
/*      */ import net.minecraft.world.level.Explosion;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelAccessor;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.EntityBlock;
/*      */ import net.minecraft.world.level.block.Mirror;
/*      */ import net.minecraft.world.level.block.RenderShape;
/*      */ import net.minecraft.world.level.block.Rotation;
/*      */ import net.minecraft.world.level.block.SoundType;
/*      */ import net.minecraft.world.level.block.SupportType;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*      */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*      */ import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.material.Fluid;
/*      */ import net.minecraft.world.level.material.FluidState;
/*      */ import net.minecraft.world.level.material.Fluids;
/*      */ import net.minecraft.world.level.material.MapColor;
/*      */ import net.minecraft.world.level.material.PushReaction;
/*      */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*      */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*      */ import net.minecraft.world.level.storage.loot.LootParams;
/*      */ import net.minecraft.world.level.storage.loot.LootTable;
/*      */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*      */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.BlockHitResult;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.phys.shapes.CollisionContext;
/*      */ import net.minecraft.world.phys.shapes.Shapes;
/*      */ import net.minecraft.world.phys.shapes.VoxelShape;
/*      */ 
/*      */ public abstract class BlockBehaviour implements FeatureElement {
/*   87 */   protected static final Direction[] UPDATE_SHAPE_ORDER = new Direction[] { Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP };
/*      */   
/*      */   protected final boolean hasCollision;
/*      */   
/*      */   protected final float explosionResistance;
/*      */   protected final boolean isRandomlyTicking;
/*      */   protected final SoundType soundType;
/*      */   protected final float friction;
/*      */   protected final float speedFactor;
/*      */   protected final float jumpFactor;
/*      */   protected final boolean dynamicShape;
/*      */   protected final FeatureFlagSet requiredFeatures;
/*      */   protected final Properties properties;
/*      */   @Nullable
/*      */   protected ResourceLocation drops;
/*      */   
/*      */   public BlockBehaviour(Properties $$0) {
/*  104 */     this.hasCollision = $$0.hasCollision;
/*  105 */     this.drops = $$0.drops;
/*  106 */     this.explosionResistance = $$0.explosionResistance;
/*  107 */     this.isRandomlyTicking = $$0.isRandomlyTicking;
/*  108 */     this.soundType = $$0.soundType;
/*  109 */     this.friction = $$0.friction;
/*  110 */     this.speedFactor = $$0.speedFactor;
/*  111 */     this.jumpFactor = $$0.jumpFactor;
/*  112 */     this.dynamicShape = $$0.dynamicShape;
/*  113 */     this.requiredFeatures = $$0.requiredFeatures;
/*  114 */     this.properties = $$0;
/*      */   }
/*      */   
/*      */   public Properties properties() {
/*  118 */     return this.properties;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <B extends Block> RecordCodecBuilder<B, Properties> propertiesCodec() {
/*  130 */     return Properties.CODEC.fieldOf("properties").forGetter(BlockBehaviour::properties);
/*      */   }
/*      */   
/*      */   public static <B extends Block> MapCodec<B> simpleCodec(Function<Properties, B> $$0) {
/*  134 */     return RecordCodecBuilder.mapCodec($$1 -> $$1.group((App)propertiesCodec()).apply((Applicative)$$1, $$0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void updateIndirectNeighbourShapes(BlockState $$0, LevelAccessor $$1, BlockPos $$2, int $$3, int $$4) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/*  148 */     switch ($$3) {
/*      */       case XYZ:
/*  150 */         return !$$0.isCollisionShapeFullBlock($$1, $$2);
/*      */       case XZ:
/*  152 */         return $$1.getFluidState($$2).is(FluidTags.WATER);
/*      */       case null:
/*  154 */         return !$$0.isCollisionShapeFullBlock($$1, $$2);
/*      */     } 
/*  156 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  168 */     return $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean skipRendering(BlockState $$0, BlockState $$1, Direction $$2) {
/*  174 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*  183 */     DebugPackets.sendNeighborsUpdatePacket($$1, $$2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {}
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  194 */     if ($$0.hasBlockEntity() && !$$0.is($$3.getBlock())) {
/*  195 */       $$1.removeBlockEntity($$2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void onExplosionHit(BlockState $$0, Level $$1, BlockPos $$2, Explosion $$3, BiConsumer<ItemStack, BlockPos> $$4) {
/*  202 */     if ($$0.isAir() || $$3.getBlockInteraction() == Explosion.BlockInteraction.TRIGGER_BLOCK) {
/*      */       return;
/*      */     }
/*      */     
/*  206 */     Block $$5 = $$0.getBlock();
/*  207 */     boolean $$6 = $$3.getIndirectSourceEntity() instanceof Player;
/*      */     
/*  209 */     if ($$5.dropFromExplosion($$3) && $$1 instanceof ServerLevel) { ServerLevel $$7 = (ServerLevel)$$1;
/*  210 */       BlockEntity $$8 = $$0.hasBlockEntity() ? $$1.getBlockEntity($$2) : null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  215 */       LootParams.Builder $$9 = (new LootParams.Builder($$7)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf((Vec3i)$$2)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, $$8).withOptionalParameter(LootContextParams.THIS_ENTITY, $$3.getDirectSourceEntity());
/*      */       
/*  217 */       if ($$3.getBlockInteraction() == Explosion.BlockInteraction.DESTROY_WITH_DECAY) {
/*  218 */         $$9.withParameter(LootContextParams.EXPLOSION_RADIUS, Float.valueOf($$3.radius()));
/*      */       }
/*      */       
/*  221 */       $$0.spawnAfterBreak($$7, $$2, ItemStack.EMPTY, $$6);
/*  222 */       $$0.getDrops($$9).forEach($$2 -> $$0.accept($$2, $$1)); }
/*      */ 
/*      */     
/*  225 */     $$1.setBlock($$2, Blocks.AIR.defaultBlockState(), 3);
/*  226 */     $$5.wasExploded($$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  232 */     return InteractionResult.PASS;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean triggerEvent(BlockState $$0, Level $$1, BlockPos $$2, int $$3, int $$4) {
/*  238 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RenderShape getRenderShape(BlockState $$0) {
/*  246 */     return RenderShape.MODEL;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/*  252 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isSignalSource(BlockState $$0) {
/*  258 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public FluidState getFluidState(BlockState $$0) {
/*  264 */     return Fluids.EMPTY.defaultFluidState();
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/*  270 */     return false;
/*      */   }
/*      */   
/*      */   public float getMaxHorizontalOffset() {
/*  274 */     return 0.25F;
/*      */   }
/*      */   
/*      */   public float getMaxVerticalOffset() {
/*  278 */     return 0.2F;
/*      */   }
/*      */ 
/*      */   
/*      */   public FeatureFlagSet requiredFeatures() {
/*  283 */     return this.requiredFeatures;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  291 */     return $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/*  297 */     return $$0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/*  311 */     return ($$0.canBeReplaced() && ($$1.getItemInHand().isEmpty() || !$$1.getItemInHand().is(asItem())));
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean canBeReplaced(BlockState $$0, Fluid $$1) {
/*  317 */     return ($$0.canBeReplaced() || !$$0.isSolid());
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public List<ItemStack> getDrops(BlockState $$0, LootParams.Builder $$1) {
/*  323 */     ResourceLocation $$2 = getLootTable();
/*  324 */     if ($$2 == BuiltInLootTables.EMPTY) {
/*  325 */       return Collections.emptyList();
/*      */     }
/*  327 */     LootParams $$3 = $$1.withParameter(LootContextParams.BLOCK_STATE, $$0).create(LootContextParamSets.BLOCK);
/*  328 */     ServerLevel $$4 = $$3.getLevel();
/*  329 */     LootTable $$5 = $$4.getServer().getLootData().getLootTable($$2);
/*  330 */     return (List<ItemStack>)$$5.getRandomItems($$3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public long getSeed(BlockState $$0, BlockPos $$1) {
/*  337 */     return Mth.getSeed((Vec3i)$$1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  345 */     return $$0.getShape($$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  351 */     return getCollisionShape($$0, $$1, $$2, CollisionContext.empty());
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public VoxelShape getInteractionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  357 */     return Shapes.empty();
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getLightBlock(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  363 */     if ($$0.isSolidRender($$1, $$2)) {
/*  364 */       return $$1.getMaxLightLevel();
/*      */     }
/*  366 */     return $$0.propagatesSkylightDown($$1, $$2) ? 0 : 1;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   @Deprecated
/*      */   public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
/*  373 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  379 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public float getShadeBrightness(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  385 */     return $$0.isCollisionShapeFullBlock($$1, $$2) ? 0.2F : 1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/*  391 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  399 */     return Shapes.block();
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  405 */     return this.hasCollision ? $$0.getShape($$1, $$2) : Shapes.empty();
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isCollisionShapeFullBlock(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  411 */     return Block.isShapeFullBlock($$0.getCollisionShape($$1, $$2));
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isOcclusionShapeFullBlock(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  417 */     return Block.isShapeFullBlock($$0.getOcclusionShape($$1, $$2));
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public VoxelShape getVisualShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  423 */     return getCollisionShape($$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {}
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {}
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public float getDestroyProgress(BlockState $$0, Player $$1, BlockGetter $$2, BlockPos $$3) {
/*  439 */     float $$4 = $$0.getDestroySpeed($$2, $$3);
/*  440 */     if ($$4 == -1.0F) {
/*  441 */       return 0.0F;
/*      */     }
/*  443 */     int $$5 = $$1.hasCorrectToolForDrops($$0) ? 30 : 100;
/*  444 */     return $$1.getDestroySpeed($$0) / $$4 / $$5;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void spawnAfterBreak(BlockState $$0, ServerLevel $$1, BlockPos $$2, ItemStack $$3, boolean $$4) {}
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void attack(BlockState $$0, Level $$1, BlockPos $$2, Player $$3) {}
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  460 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  475 */     return 0;
/*      */   }
/*      */   
/*      */   public final ResourceLocation getLootTable() {
/*  479 */     if (this.drops == null) {
/*  480 */       ResourceLocation $$0 = BuiltInRegistries.BLOCK.getKey(asBlock());
/*      */       
/*  482 */       this.drops = $$0.withPrefix("blocks/");
/*      */     } 
/*  484 */     return this.drops;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public enum OffsetType
/*      */   {
/*  497 */     NONE,
/*  498 */     XZ,
/*  499 */     XYZ;
/*      */   }
/*      */   
/*      */   public MapColor defaultMapColor() {
/*  503 */     return this.properties.mapColor.apply(asBlock().defaultBlockState());
/*      */   }
/*      */   
/*      */   public float defaultDestroyTime() {
/*  507 */     return this.properties.destroyTime;
/*      */   }
/*      */   protected abstract MapCodec<? extends Block> codec();
/*      */   public abstract Item asItem();
/*      */   protected abstract Block asBlock();
/*  512 */   public static class Properties { public static final Codec<Properties> CODEC = Codec.unit(() -> of());
/*      */     
/*      */     Function<BlockState, MapColor> mapColor = $$0 -> MapColor.NONE;
/*      */     
/*      */     boolean hasCollision = true;
/*  517 */     SoundType soundType = SoundType.STONE;
/*      */     ToIntFunction<BlockState> lightEmission = $$0 -> 0;
/*      */     float explosionResistance;
/*      */     float destroyTime;
/*      */     boolean requiresCorrectToolForDrops;
/*      */     boolean isRandomlyTicking;
/*  523 */     float friction = 0.6F;
/*  524 */     float speedFactor = 1.0F;
/*  525 */     float jumpFactor = 1.0F;
/*      */     ResourceLocation drops;
/*      */     boolean canOcclude = true;
/*      */     boolean isAir;
/*      */     boolean ignitedByLava;
/*      */     @Deprecated
/*      */     boolean liquid;
/*      */     @Deprecated
/*      */     boolean forceSolidOff;
/*      */     boolean forceSolidOn;
/*  535 */     PushReaction pushReaction = PushReaction.NORMAL;
/*      */     boolean spawnTerrainParticles = true;
/*  537 */     NoteBlockInstrument instrument = NoteBlockInstrument.HARP; boolean replaceable; BlockBehaviour.StateArgumentPredicate<EntityType<?>> isValidSpawn; BlockBehaviour.StatePredicate isRedstoneConductor; BlockBehaviour.StatePredicate isSuffocating; BlockBehaviour.StatePredicate isViewBlocking;
/*      */     
/*      */     private Properties() {
/*  540 */       this.isValidSpawn = (($$0, $$1, $$2, $$3) -> 
/*  541 */         ($$0.isFaceSturdy($$1, $$2, Direction.UP) && $$0.getLightEmission() < 14));
/*      */       
/*  543 */       this.isRedstoneConductor = (($$0, $$1, $$2) -> $$0.isCollisionShapeFullBlock($$1, $$2));
/*      */ 
/*      */       
/*  546 */       this.isSuffocating = (($$0, $$1, $$2) -> 
/*  547 */         ($$0.blocksMotion() && $$0.isCollisionShapeFullBlock($$1, $$2)));
/*      */       
/*  549 */       this.isViewBlocking = this.isSuffocating;
/*  550 */       this.hasPostProcess = (($$0, $$1, $$2) -> false);
/*  551 */       this.emissiveRendering = (($$0, $$1, $$2) -> false);
/*      */ 
/*      */       
/*  554 */       this.requiredFeatures = FeatureFlags.VANILLA_SET;
/*      */       
/*  556 */       this.offsetFunction = Optional.empty();
/*      */     }
/*      */     BlockBehaviour.StatePredicate hasPostProcess; BlockBehaviour.StatePredicate emissiveRendering; boolean dynamicShape; FeatureFlagSet requiredFeatures;
/*      */     Optional<BlockBehaviour.OffsetFunction> offsetFunction;
/*      */     
/*      */     public static Properties of() {
/*  562 */       return new Properties();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Properties ofFullCopy(BlockBehaviour $$0) {
/*  573 */       Properties $$1 = ofLegacyCopy($$0);
/*  574 */       Properties $$2 = $$0.properties;
/*      */       
/*  576 */       $$1.jumpFactor = $$2.jumpFactor;
/*  577 */       $$1.isRedstoneConductor = $$2.isRedstoneConductor;
/*  578 */       $$1.isValidSpawn = $$2.isValidSpawn;
/*  579 */       $$1.hasPostProcess = $$2.hasPostProcess;
/*  580 */       $$1.isSuffocating = $$2.isSuffocating;
/*  581 */       $$1.isViewBlocking = $$2.isViewBlocking;
/*  582 */       $$1.drops = $$2.drops;
/*      */       
/*  584 */       return $$1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public static Properties ofLegacyCopy(BlockBehaviour $$0) {
/*  593 */       Properties $$1 = new Properties();
/*  594 */       Properties $$2 = $$0.properties;
/*      */       
/*  596 */       $$1.destroyTime = $$2.destroyTime;
/*  597 */       $$1.explosionResistance = $$2.explosionResistance;
/*  598 */       $$1.hasCollision = $$2.hasCollision;
/*  599 */       $$1.isRandomlyTicking = $$2.isRandomlyTicking;
/*  600 */       $$1.lightEmission = $$2.lightEmission;
/*  601 */       $$1.mapColor = $$2.mapColor;
/*  602 */       $$1.soundType = $$2.soundType;
/*  603 */       $$1.friction = $$2.friction;
/*  604 */       $$1.speedFactor = $$2.speedFactor;
/*  605 */       $$1.dynamicShape = $$2.dynamicShape;
/*  606 */       $$1.canOcclude = $$2.canOcclude;
/*  607 */       $$1.isAir = $$2.isAir;
/*  608 */       $$1.ignitedByLava = $$2.ignitedByLava;
/*  609 */       $$1.liquid = $$2.liquid;
/*  610 */       $$1.forceSolidOff = $$2.forceSolidOff;
/*  611 */       $$1.forceSolidOn = $$2.forceSolidOn;
/*  612 */       $$1.pushReaction = $$2.pushReaction;
/*  613 */       $$1.requiresCorrectToolForDrops = $$2.requiresCorrectToolForDrops;
/*  614 */       $$1.offsetFunction = $$2.offsetFunction;
/*  615 */       $$1.spawnTerrainParticles = $$2.spawnTerrainParticles;
/*  616 */       $$1.requiredFeatures = $$2.requiredFeatures;
/*  617 */       $$1.emissiveRendering = $$2.emissiveRendering;
/*  618 */       $$1.instrument = $$2.instrument;
/*  619 */       $$1.replaceable = $$2.replaceable;
/*      */       
/*  621 */       return $$1;
/*      */     }
/*      */     
/*      */     public Properties mapColor(DyeColor $$0) {
/*  625 */       this.mapColor = ($$1 -> $$0.getMapColor());
/*  626 */       return this;
/*      */     }
/*      */     
/*      */     public Properties mapColor(MapColor $$0) {
/*  630 */       this.mapColor = ($$1 -> $$0);
/*  631 */       return this;
/*      */     }
/*      */     
/*      */     public Properties mapColor(Function<BlockState, MapColor> $$0) {
/*  635 */       this.mapColor = $$0;
/*  636 */       return this;
/*      */     }
/*      */     
/*      */     public Properties noCollission() {
/*  640 */       this.hasCollision = false;
/*  641 */       this.canOcclude = false;
/*  642 */       return this;
/*      */     }
/*      */     
/*      */     public Properties noOcclusion() {
/*  646 */       this.canOcclude = false;
/*  647 */       return this;
/*      */     }
/*      */     
/*      */     public Properties friction(float $$0) {
/*  651 */       this.friction = $$0;
/*  652 */       return this;
/*      */     }
/*      */     
/*      */     public Properties speedFactor(float $$0) {
/*  656 */       this.speedFactor = $$0;
/*  657 */       return this;
/*      */     }
/*      */     
/*      */     public Properties jumpFactor(float $$0) {
/*  661 */       this.jumpFactor = $$0;
/*  662 */       return this;
/*      */     }
/*      */     
/*      */     public Properties sound(SoundType $$0) {
/*  666 */       this.soundType = $$0;
/*  667 */       return this;
/*      */     }
/*      */     
/*      */     public Properties lightLevel(ToIntFunction<BlockState> $$0) {
/*  671 */       this.lightEmission = $$0;
/*  672 */       return this;
/*      */     }
/*      */     
/*      */     public Properties strength(float $$0, float $$1) {
/*  676 */       return destroyTime($$0).explosionResistance($$1);
/*      */     }
/*      */     
/*      */     public Properties instabreak() {
/*  680 */       return strength(0.0F);
/*      */     }
/*      */     
/*      */     public Properties strength(float $$0) {
/*  684 */       strength($$0, $$0);
/*  685 */       return this;
/*      */     }
/*      */     
/*      */     public Properties randomTicks() {
/*  689 */       this.isRandomlyTicking = true;
/*  690 */       return this;
/*      */     }
/*      */     
/*      */     public Properties dynamicShape() {
/*  694 */       this.dynamicShape = true;
/*  695 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Properties noLootTable() {
/*  703 */       this.drops = BuiltInLootTables.EMPTY;
/*  704 */       return this;
/*      */     }
/*      */     
/*      */     public Properties dropsLike(Block $$0) {
/*  708 */       this.drops = $$0.getLootTable();
/*  709 */       return this;
/*      */     }
/*      */     
/*      */     public Properties ignitedByLava() {
/*  713 */       this.ignitedByLava = true;
/*  714 */       return this;
/*      */     }
/*      */     
/*      */     public Properties liquid() {
/*  718 */       this.liquid = true;
/*  719 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Properties forceSolidOn() {
/*  726 */       this.forceSolidOn = true;
/*  727 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Properties forceSolidOff() {
/*  736 */       this.forceSolidOff = true;
/*  737 */       return this;
/*      */     }
/*      */     
/*      */     public Properties pushReaction(PushReaction $$0) {
/*  741 */       this.pushReaction = $$0;
/*  742 */       return this;
/*      */     }
/*      */     
/*      */     public Properties air() {
/*  746 */       this.isAir = true;
/*  747 */       return this;
/*      */     }
/*      */     
/*      */     public Properties isValidSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> $$0) {
/*  751 */       this.isValidSpawn = $$0;
/*  752 */       return this;
/*      */     }
/*      */     
/*      */     public Properties isRedstoneConductor(BlockBehaviour.StatePredicate $$0) {
/*  756 */       this.isRedstoneConductor = $$0;
/*  757 */       return this;
/*      */     }
/*      */     
/*      */     public Properties isSuffocating(BlockBehaviour.StatePredicate $$0) {
/*  761 */       this.isSuffocating = $$0;
/*  762 */       return this;
/*      */     }
/*      */     
/*      */     public Properties isViewBlocking(BlockBehaviour.StatePredicate $$0) {
/*  766 */       this.isViewBlocking = $$0;
/*  767 */       return this;
/*      */     }
/*      */     
/*      */     public Properties hasPostProcess(BlockBehaviour.StatePredicate $$0) {
/*  771 */       this.hasPostProcess = $$0;
/*  772 */       return this;
/*      */     }
/*      */     
/*      */     public Properties emissiveRendering(BlockBehaviour.StatePredicate $$0) {
/*  776 */       this.emissiveRendering = $$0;
/*  777 */       return this;
/*      */     }
/*      */     
/*      */     public Properties requiresCorrectToolForDrops() {
/*  781 */       this.requiresCorrectToolForDrops = true;
/*  782 */       return this;
/*      */     }
/*      */     
/*      */     public Properties destroyTime(float $$0) {
/*  786 */       this.destroyTime = $$0;
/*  787 */       return this;
/*      */     }
/*      */     
/*      */     public Properties explosionResistance(float $$0) {
/*  791 */       this.explosionResistance = Math.max(0.0F, $$0);
/*  792 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Properties offsetType(BlockBehaviour.OffsetType $$0) {
/*      */       // Byte code:
/*      */       //   0: getstatic net/minecraft/world/level/block/state/BlockBehaviour$1.$SwitchMap$net$minecraft$world$level$block$state$BlockBehaviour$OffsetType : [I
/*      */       //   3: aload_1
/*      */       //   4: invokevirtual ordinal : ()I
/*      */       //   7: iaload
/*      */       //   8: lookupswitch default -> 36, 1 -> 46, 2 -> 61
/*      */       //   36: aload_0
/*      */       //   37: invokestatic empty : ()Ljava/util/Optional;
/*      */       //   40: putfield offsetFunction : Ljava/util/Optional;
/*      */       //   43: goto -> 73
/*      */       //   46: aload_0
/*      */       //   47: <illegal opcode> evaluate : ()Lnet/minecraft/world/level/block/state/BlockBehaviour$OffsetFunction;
/*      */       //   52: invokestatic of : (Ljava/lang/Object;)Ljava/util/Optional;
/*      */       //   55: putfield offsetFunction : Ljava/util/Optional;
/*      */       //   58: goto -> 73
/*      */       //   61: aload_0
/*      */       //   62: <illegal opcode> evaluate : ()Lnet/minecraft/world/level/block/state/BlockBehaviour$OffsetFunction;
/*      */       //   67: invokestatic of : (Ljava/lang/Object;)Ljava/util/Optional;
/*      */       //   70: putfield offsetFunction : Ljava/util/Optional;
/*      */       //   73: aload_0
/*      */       //   74: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #796	-> 0
/*      */       //   #797	-> 36
/*      */       //   #798	-> 46
/*      */       //   #808	-> 61
/*      */       //   #818	-> 73
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	75	0	this	Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;
/*      */       //   0	75	1	$$0	Lnet/minecraft/world/level/block/state/BlockBehaviour$OffsetType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Properties noTerrainParticles() {
/*  822 */       this.spawnTerrainParticles = false;
/*  823 */       return this;
/*      */     }
/*      */     
/*      */     public Properties requiredFeatures(FeatureFlag... $$0) {
/*  827 */       this.requiredFeatures = FeatureFlags.REGISTRY.subset($$0);
/*  828 */       return this;
/*      */     }
/*      */     
/*      */     public Properties instrument(NoteBlockInstrument $$0) {
/*  832 */       this.instrument = $$0;
/*  833 */       return this;
/*      */     }
/*      */     
/*      */     public Properties replaceable() {
/*  837 */       this.replaceable = true;
/*  838 */       return this;
/*      */     } }
/*      */ 
/*      */   
/*      */   public static abstract class BlockStateBase
/*      */     extends StateHolder<Block, BlockState> {
/*      */     private final int lightEmission;
/*      */     private final boolean useShapeForLightOcclusion;
/*      */     private final boolean isAir;
/*      */     private final boolean ignitedByLava;
/*      */     @Deprecated
/*      */     private final boolean liquid;
/*      */     @Deprecated
/*      */     private boolean legacySolid;
/*      */     private final PushReaction pushReaction;
/*      */     private final MapColor mapColor;
/*      */     private final float destroySpeed;
/*      */     private final boolean requiresCorrectToolForDrops;
/*      */     private final boolean canOcclude;
/*      */     private final BlockBehaviour.StatePredicate isRedstoneConductor;
/*      */     private final BlockBehaviour.StatePredicate isSuffocating;
/*      */     private final BlockBehaviour.StatePredicate isViewBlocking;
/*      */     private final BlockBehaviour.StatePredicate hasPostProcess;
/*      */     private final BlockBehaviour.StatePredicate emissiveRendering;
/*      */     private final Optional<BlockBehaviour.OffsetFunction> offsetFunction;
/*      */     private final boolean spawnTerrainParticles;
/*      */     private final NoteBlockInstrument instrument;
/*      */     private final boolean replaceable;
/*      */     @Nullable
/*      */     protected Cache cache;
/*  868 */     private FluidState fluidState = Fluids.EMPTY.defaultFluidState();
/*      */     private boolean isRandomlyTicking;
/*      */     
/*      */     protected BlockStateBase(Block $$0, ImmutableMap<Property<?>, Comparable<?>> $$1, MapCodec<BlockState> $$2) {
/*  872 */       super($$0, $$1, $$2);
/*  873 */       BlockBehaviour.Properties $$3 = $$0.properties;
/*      */       
/*  875 */       this.lightEmission = $$3.lightEmission.applyAsInt(asState());
/*  876 */       this.useShapeForLightOcclusion = $$0.useShapeForLightOcclusion(asState());
/*  877 */       this.isAir = $$3.isAir;
/*  878 */       this.ignitedByLava = $$3.ignitedByLava;
/*  879 */       this.liquid = $$3.liquid;
/*  880 */       this.pushReaction = $$3.pushReaction;
/*  881 */       this.mapColor = $$3.mapColor.apply(asState());
/*  882 */       this.destroySpeed = $$3.destroyTime;
/*  883 */       this.requiresCorrectToolForDrops = $$3.requiresCorrectToolForDrops;
/*  884 */       this.canOcclude = $$3.canOcclude;
/*  885 */       this.isRedstoneConductor = $$3.isRedstoneConductor;
/*  886 */       this.isSuffocating = $$3.isSuffocating;
/*  887 */       this.isViewBlocking = $$3.isViewBlocking;
/*  888 */       this.hasPostProcess = $$3.hasPostProcess;
/*  889 */       this.emissiveRendering = $$3.emissiveRendering;
/*  890 */       this.offsetFunction = $$3.offsetFunction;
/*  891 */       this.spawnTerrainParticles = $$3.spawnTerrainParticles;
/*  892 */       this.instrument = $$3.instrument;
/*  893 */       this.replaceable = $$3.replaceable;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean calculateSolid() {
/*  902 */       if (this.owner.properties.forceSolidOn) {
/*  903 */         return true;
/*      */       }
/*  905 */       if (this.owner.properties.forceSolidOff) {
/*  906 */         return false;
/*      */       }
/*  908 */       if (this.cache == null) {
/*  909 */         return false;
/*      */       }
/*  911 */       VoxelShape $$0 = this.cache.collisionShape;
/*  912 */       if ($$0.isEmpty()) {
/*  913 */         return false;
/*      */       }
/*  915 */       AABB $$1 = $$0.bounds();
/*  916 */       if ($$1.getSize() >= 0.7291666666666666D) {
/*  917 */         return true;
/*      */       }
/*  919 */       if ($$1.getYsize() >= 1.0D) {
/*  920 */         return true;
/*      */       }
/*  922 */       return false;
/*      */     }
/*      */     
/*      */     public void initCache() {
/*  926 */       this.fluidState = this.owner.getFluidState(asState());
/*  927 */       this.isRandomlyTicking = this.owner.isRandomlyTicking(asState());
/*  928 */       if (!getBlock().hasDynamicShape()) {
/*  929 */         this.cache = new Cache(asState());
/*      */       }
/*  931 */       this.legacySolid = calculateSolid();
/*      */     }
/*      */     
/*      */     public Block getBlock() {
/*  935 */       return this.owner;
/*      */     }
/*      */     
/*      */     public Holder<Block> getBlockHolder() {
/*  939 */       return (Holder<Block>)this.owner.builtInRegistryHolder();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean blocksMotion() {
/*  955 */       Block $$0 = getBlock();
/*  956 */       return ($$0 != Blocks.COBWEB && $$0 != Blocks.BAMBOO_SAPLING && isSolid());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean isSolid() {
/*  974 */       return this.legacySolid;
/*      */     }
/*      */     
/*      */     public boolean isValidSpawn(BlockGetter $$0, BlockPos $$1, EntityType<?> $$2) {
/*  978 */       return (getBlock()).properties.isValidSpawn.test(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public boolean propagatesSkylightDown(BlockGetter $$0, BlockPos $$1) {
/*  982 */       if (this.cache != null) {
/*  983 */         return this.cache.propagatesSkylightDown;
/*      */       }
/*  985 */       return getBlock().propagatesSkylightDown(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public int getLightBlock(BlockGetter $$0, BlockPos $$1) {
/*  989 */       if (this.cache != null) {
/*  990 */         return this.cache.lightBlock;
/*      */       }
/*  992 */       return getBlock().getLightBlock(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public VoxelShape getFaceOcclusionShape(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/*  996 */       if (this.cache != null && this.cache.occlusionShapes != null) {
/*  997 */         return this.cache.occlusionShapes[$$2.ordinal()];
/*      */       }
/*      */       
/* 1000 */       return Shapes.getFaceShape(getOcclusionShape($$0, $$1), $$2);
/*      */     }
/*      */     
/*      */     public VoxelShape getOcclusionShape(BlockGetter $$0, BlockPos $$1) {
/* 1004 */       return getBlock().getOcclusionShape(asState(), $$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasLargeCollisionShape() {
/* 1009 */       return (this.cache == null || this.cache.largeCollisionShape);
/*      */     }
/*      */     
/*      */     public boolean useShapeForLightOcclusion() {
/* 1013 */       return this.useShapeForLightOcclusion;
/*      */     }
/*      */     
/*      */     public int getLightEmission() {
/* 1017 */       return this.lightEmission;
/*      */     }
/*      */     
/*      */     public boolean isAir() {
/* 1021 */       return this.isAir;
/*      */     }
/*      */     
/*      */     public boolean ignitedByLava() {
/* 1025 */       return this.ignitedByLava;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean liquid() {
/* 1031 */       return this.liquid;
/*      */     }
/*      */     
/*      */     public MapColor getMapColor(BlockGetter $$0, BlockPos $$1) {
/* 1035 */       return this.mapColor;
/*      */     }
/*      */     
/*      */     public BlockState rotate(Rotation $$0) {
/* 1039 */       return getBlock().rotate(asState(), $$0);
/*      */     }
/*      */     
/*      */     public BlockState mirror(Mirror $$0) {
/* 1043 */       return getBlock().mirror(asState(), $$0);
/*      */     }
/*      */     
/*      */     public RenderShape getRenderShape() {
/* 1047 */       return getBlock().getRenderShape(asState());
/*      */     }
/*      */     
/*      */     public boolean emissiveRendering(BlockGetter $$0, BlockPos $$1) {
/* 1051 */       return this.emissiveRendering.test(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public float getShadeBrightness(BlockGetter $$0, BlockPos $$1) {
/* 1055 */       return getBlock().getShadeBrightness(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public boolean isRedstoneConductor(BlockGetter $$0, BlockPos $$1) {
/* 1059 */       return this.isRedstoneConductor.test(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public boolean isSignalSource() {
/* 1063 */       return getBlock().isSignalSource(asState());
/*      */     }
/*      */     
/*      */     public int getSignal(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/* 1067 */       return getBlock().getSignal(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public boolean hasAnalogOutputSignal() {
/* 1071 */       return getBlock().hasAnalogOutputSignal(asState());
/*      */     }
/*      */     
/*      */     public int getAnalogOutputSignal(Level $$0, BlockPos $$1) {
/* 1075 */       return getBlock().getAnalogOutputSignal(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public float getDestroySpeed(BlockGetter $$0, BlockPos $$1) {
/* 1079 */       return this.destroySpeed;
/*      */     }
/*      */     
/*      */     public float getDestroyProgress(Player $$0, BlockGetter $$1, BlockPos $$2) {
/* 1083 */       return getBlock().getDestroyProgress(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public int getDirectSignal(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/* 1087 */       return getBlock().getDirectSignal(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public PushReaction getPistonPushReaction() {
/* 1091 */       return this.pushReaction;
/*      */     }
/*      */     
/*      */     public boolean isSolidRender(BlockGetter $$0, BlockPos $$1) {
/* 1095 */       if (this.cache != null) {
/* 1096 */         return this.cache.solidRender;
/*      */       }
/* 1098 */       BlockState $$2 = asState();
/* 1099 */       if ($$2.canOcclude()) {
/* 1100 */         return Block.isShapeFullBlock($$2.getOcclusionShape($$0, $$1));
/*      */       }
/* 1102 */       return false;
/*      */     }
/*      */     
/*      */     public boolean canOcclude() {
/* 1106 */       return this.canOcclude;
/*      */     }
/*      */     
/*      */     public boolean skipRendering(BlockState $$0, Direction $$1) {
/* 1110 */       return getBlock().skipRendering(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public VoxelShape getShape(BlockGetter $$0, BlockPos $$1) {
/* 1114 */       return getShape($$0, $$1, CollisionContext.empty());
/*      */     }
/*      */     
/*      */     public VoxelShape getShape(BlockGetter $$0, BlockPos $$1, CollisionContext $$2) {
/* 1118 */       return getBlock().getShape(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public VoxelShape getCollisionShape(BlockGetter $$0, BlockPos $$1) {
/* 1122 */       if (this.cache != null) {
/* 1123 */         return this.cache.collisionShape;
/*      */       }
/* 1125 */       return getCollisionShape($$0, $$1, CollisionContext.empty());
/*      */     }
/*      */     
/*      */     public VoxelShape getCollisionShape(BlockGetter $$0, BlockPos $$1, CollisionContext $$2) {
/* 1129 */       return getBlock().getCollisionShape(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public VoxelShape getBlockSupportShape(BlockGetter $$0, BlockPos $$1) {
/* 1133 */       return getBlock().getBlockSupportShape(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public VoxelShape getVisualShape(BlockGetter $$0, BlockPos $$1, CollisionContext $$2) {
/* 1137 */       return getBlock().getVisualShape(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public VoxelShape getInteractionShape(BlockGetter $$0, BlockPos $$1) {
/* 1141 */       return getBlock().getInteractionShape(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public final boolean entityCanStandOn(BlockGetter $$0, BlockPos $$1, Entity $$2) {
/* 1145 */       return entityCanStandOnFace($$0, $$1, $$2, Direction.UP);
/*      */     }
/*      */     
/*      */     public final boolean entityCanStandOnFace(BlockGetter $$0, BlockPos $$1, Entity $$2, Direction $$3) {
/* 1149 */       return Block.isFaceFull(getCollisionShape($$0, $$1, CollisionContext.of($$2)), $$3);
/*      */     }
/*      */     
/*      */     public Vec3 getOffset(BlockGetter $$0, BlockPos $$1) {
/* 1153 */       return this.offsetFunction.<Vec3>map($$2 -> $$2.evaluate(asState(), $$0, $$1)).orElse(Vec3.ZERO);
/*      */     }
/*      */     
/*      */     public boolean hasOffsetFunction() {
/* 1157 */       return this.offsetFunction.isPresent();
/*      */     }
/*      */     
/*      */     public boolean triggerEvent(Level $$0, BlockPos $$1, int $$2, int $$3) {
/* 1161 */       return getBlock().triggerEvent(asState(), $$0, $$1, $$2, $$3);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void neighborChanged(Level $$0, BlockPos $$1, Block $$2, BlockPos $$3, boolean $$4) {
/* 1167 */       getBlock().neighborChanged(asState(), $$0, $$1, $$2, $$3, $$4);
/*      */     }
/*      */     
/*      */     public final void updateNeighbourShapes(LevelAccessor $$0, BlockPos $$1, int $$2) {
/* 1171 */       updateNeighbourShapes($$0, $$1, $$2, 512);
/*      */     }
/*      */     
/*      */     public final void updateNeighbourShapes(LevelAccessor $$0, BlockPos $$1, int $$2, int $$3) {
/* 1175 */       BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/* 1176 */       for (Direction $$5 : BlockBehaviour.UPDATE_SHAPE_ORDER) {
/* 1177 */         $$4.setWithOffset((Vec3i)$$1, $$5);
/* 1178 */         $$0.neighborShapeChanged($$5.getOpposite(), asState(), (BlockPos)$$4, $$1, $$2, $$3);
/*      */       } 
/*      */     }
/*      */     
/*      */     public final void updateIndirectNeighbourShapes(LevelAccessor $$0, BlockPos $$1, int $$2) {
/* 1183 */       updateIndirectNeighbourShapes($$0, $$1, $$2, 512);
/*      */     }
/*      */     
/*      */     public void updateIndirectNeighbourShapes(LevelAccessor $$0, BlockPos $$1, int $$2, int $$3) {
/* 1187 */       getBlock().updateIndirectNeighbourShapes(asState(), $$0, $$1, $$2, $$3);
/*      */     }
/*      */     
/*      */     public void onPlace(Level $$0, BlockPos $$1, BlockState $$2, boolean $$3) {
/* 1191 */       getBlock().onPlace(asState(), $$0, $$1, $$2, $$3);
/*      */     }
/*      */     
/*      */     public void onRemove(Level $$0, BlockPos $$1, BlockState $$2, boolean $$3) {
/* 1195 */       getBlock().onRemove(asState(), $$0, $$1, $$2, $$3);
/*      */     }
/*      */     
/*      */     public void onExplosionHit(Level $$0, BlockPos $$1, Explosion $$2, BiConsumer<ItemStack, BlockPos> $$3) {
/* 1199 */       getBlock().onExplosionHit(asState(), $$0, $$1, $$2, $$3);
/*      */     }
/*      */     
/*      */     public void tick(ServerLevel $$0, BlockPos $$1, RandomSource $$2) {
/* 1203 */       getBlock().tick(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public void randomTick(ServerLevel $$0, BlockPos $$1, RandomSource $$2) {
/* 1207 */       getBlock().randomTick(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public void entityInside(Level $$0, BlockPos $$1, Entity $$2) {
/* 1211 */       getBlock().entityInside(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public void spawnAfterBreak(ServerLevel $$0, BlockPos $$1, ItemStack $$2, boolean $$3) {
/* 1215 */       getBlock().spawnAfterBreak(asState(), $$0, $$1, $$2, $$3);
/*      */     }
/*      */     
/*      */     public List<ItemStack> getDrops(LootParams.Builder $$0) {
/* 1219 */       return getBlock().getDrops(asState(), $$0);
/*      */     }
/*      */     
/*      */     public InteractionResult use(Level $$0, Player $$1, InteractionHand $$2, BlockHitResult $$3) {
/* 1223 */       return getBlock().use(asState(), $$0, $$3.getBlockPos(), $$1, $$2, $$3);
/*      */     }
/*      */     
/*      */     public void attack(Level $$0, BlockPos $$1, Player $$2) {
/* 1227 */       getBlock().attack(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public boolean isSuffocating(BlockGetter $$0, BlockPos $$1) {
/* 1231 */       return this.isSuffocating.test(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public boolean isViewBlocking(BlockGetter $$0, BlockPos $$1) {
/* 1235 */       return this.isViewBlocking.test(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public BlockState updateShape(Direction $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3, BlockPos $$4) {
/* 1239 */       return getBlock().updateShape(asState(), $$0, $$1, $$2, $$3, $$4);
/*      */     }
/*      */     
/*      */     public boolean isPathfindable(BlockGetter $$0, BlockPos $$1, PathComputationType $$2) {
/* 1243 */       return getBlock().isPathfindable(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public boolean canBeReplaced(BlockPlaceContext $$0) {
/* 1247 */       return getBlock().canBeReplaced(asState(), $$0);
/*      */     }
/*      */     
/*      */     public boolean canBeReplaced(Fluid $$0) {
/* 1251 */       return getBlock().canBeReplaced(asState(), $$0);
/*      */     }
/*      */     
/*      */     public boolean canBeReplaced() {
/* 1255 */       return this.replaceable;
/*      */     }
/*      */     
/*      */     public boolean canSurvive(LevelReader $$0, BlockPos $$1) {
/* 1259 */       return getBlock().canSurvive(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public boolean hasPostProcess(BlockGetter $$0, BlockPos $$1) {
/* 1263 */       return this.hasPostProcess.test(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public MenuProvider getMenuProvider(Level $$0, BlockPos $$1) {
/* 1268 */       return getBlock().getMenuProvider(asState(), $$0, $$1);
/*      */     }
/*      */     
/*      */     public boolean is(TagKey<Block> $$0) {
/* 1272 */       return getBlock().builtInRegistryHolder().is($$0);
/*      */     }
/*      */     
/*      */     public boolean is(TagKey<Block> $$0, Predicate<BlockStateBase> $$1) {
/* 1276 */       return (is($$0) && $$1.test(this));
/*      */     }
/*      */     
/*      */     public boolean is(HolderSet<Block> $$0) {
/* 1280 */       return $$0.contains((Holder)getBlock().builtInRegistryHolder());
/*      */     }
/*      */     
/*      */     public boolean is(Holder<Block> $$0) {
/* 1284 */       return is((Block)$$0.value());
/*      */     }
/*      */     
/*      */     public Stream<TagKey<Block>> getTags() {
/* 1288 */       return getBlock().builtInRegistryHolder().tags();
/*      */     }
/*      */     
/*      */     public boolean hasBlockEntity() {
/* 1292 */       return getBlock() instanceof EntityBlock;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockEntityType<T> $$1) {
/* 1297 */       if (getBlock() instanceof EntityBlock) {
/* 1298 */         return ((EntityBlock)getBlock()).getTicker($$0, asState(), $$1);
/*      */       }
/* 1300 */       return null;
/*      */     }
/*      */     
/*      */     public boolean is(Block $$0) {
/* 1304 */       return (getBlock() == $$0);
/*      */     }
/*      */     
/*      */     public boolean is(ResourceKey<Block> $$0) {
/* 1308 */       return getBlock().builtInRegistryHolder().is($$0);
/*      */     }
/*      */     
/*      */     public FluidState getFluidState() {
/* 1312 */       return this.fluidState;
/*      */     }
/*      */     
/*      */     public boolean isRandomlyTicking() {
/* 1316 */       return this.isRandomlyTicking;
/*      */     }
/*      */     
/*      */     public long getSeed(BlockPos $$0) {
/* 1320 */       return getBlock().getSeed(asState(), $$0);
/*      */     }
/*      */     
/*      */     public SoundType getSoundType() {
/* 1324 */       return getBlock().getSoundType(asState());
/*      */     }
/*      */     
/*      */     public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 1328 */       getBlock().onProjectileHit($$0, $$1, $$2, $$3);
/*      */     }
/*      */     
/*      */     public boolean isFaceSturdy(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/* 1332 */       return isFaceSturdy($$0, $$1, $$2, SupportType.FULL);
/*      */     }
/*      */     
/*      */     public boolean isFaceSturdy(BlockGetter $$0, BlockPos $$1, Direction $$2, SupportType $$3) {
/* 1336 */       if (this.cache != null) {
/* 1337 */         return this.cache.isFaceSturdy($$2, $$3);
/*      */       }
/* 1339 */       return $$3.isSupporting(asState(), $$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public boolean isCollisionShapeFullBlock(BlockGetter $$0, BlockPos $$1) {
/* 1343 */       if (this.cache != null) {
/* 1344 */         return this.cache.isCollisionShapeFullBlock;
/*      */       }
/* 1346 */       return getBlock().isCollisionShapeFullBlock(asState(), $$0, $$1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean requiresCorrectToolForDrops() {
/* 1352 */       return this.requiresCorrectToolForDrops;
/*      */     }
/*      */     
/*      */     public boolean shouldSpawnTerrainParticles() {
/* 1356 */       return this.spawnTerrainParticles;
/*      */     }
/*      */     
/*      */     public NoteBlockInstrument instrument() {
/* 1360 */       return this.instrument;
/*      */     }
/*      */     protected abstract BlockState asState();
/*      */     
/* 1364 */     private static final class Cache { private static final Direction[] DIRECTIONS = Direction.values();
/* 1365 */       private static final int SUPPORT_TYPE_COUNT = (SupportType.values()).length;
/*      */       protected final boolean solidRender;
/*      */       final boolean propagatesSkylightDown;
/*      */       final int lightBlock;
/*      */       @Nullable
/*      */       final VoxelShape[] occlusionShapes;
/*      */       protected final VoxelShape collisionShape;
/*      */       protected final boolean largeCollisionShape;
/*      */       private final boolean[] faceSturdy;
/*      */       protected final boolean isCollisionShapeFullBlock;
/*      */       
/*      */       Cache(BlockState $$0) {
/* 1377 */         Block $$1 = $$0.getBlock();
/* 1378 */         this.solidRender = $$0.isSolidRender((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/* 1379 */         this.propagatesSkylightDown = $$1.propagatesSkylightDown($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/* 1380 */         this.lightBlock = $$1.getLightBlock($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/* 1381 */         if (!$$0.canOcclude()) {
/* 1382 */           this.occlusionShapes = null;
/*      */         } else {
/* 1384 */           this.occlusionShapes = new VoxelShape[DIRECTIONS.length];
/* 1385 */           VoxelShape $$2 = $$1.getOcclusionShape($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/* 1386 */           for (Direction $$3 : DIRECTIONS) {
/* 1387 */             this.occlusionShapes[$$3.ordinal()] = Shapes.getFaceShape($$2, $$3);
/*      */           }
/*      */         } 
/*      */         
/* 1391 */         this.collisionShape = $$1.getCollisionShape($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
/* 1392 */         if (!this.collisionShape.isEmpty() && $$0.hasOffsetFunction()) {
/* 1393 */           throw new IllegalStateException(String.format(Locale.ROOT, "%s has a collision shape and an offset type, but is not marked as dynamicShape in its properties.", new Object[] { BuiltInRegistries.BLOCK.getKey($$1) }));
/*      */         }
/* 1395 */         this.largeCollisionShape = Arrays.<Direction.Axis>stream(Direction.Axis.values()).anyMatch($$0 -> (this.collisionShape.min($$0) < 0.0D || this.collisionShape.max($$0) > 1.0D));
/* 1396 */         this.faceSturdy = new boolean[DIRECTIONS.length * SUPPORT_TYPE_COUNT];
/* 1397 */         for (Direction $$4 : DIRECTIONS) {
/* 1398 */           for (SupportType $$5 : SupportType.values()) {
/* 1399 */             this.faceSturdy[getFaceSupportIndex($$4, $$5)] = $$5.isSupporting($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO, $$4);
/*      */           }
/*      */         } 
/* 1402 */         this.isCollisionShapeFullBlock = Block.isShapeFullBlock($$0.getCollisionShape((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO));
/*      */       }
/*      */       
/*      */       public boolean isFaceSturdy(Direction $$0, SupportType $$1) {
/* 1406 */         return this.faceSturdy[getFaceSupportIndex($$0, $$1)];
/*      */       }
/*      */       
/*      */       private static int getFaceSupportIndex(Direction $$0, SupportType $$1) {
/* 1410 */         return $$0.ordinal() * SUPPORT_TYPE_COUNT + $$1.ordinal(); } } } private static final class Cache { private static int getFaceSupportIndex(Direction $$0, SupportType $$1) { return $$0.ordinal() * SUPPORT_TYPE_COUNT + $$1.ordinal(); }
/*      */ 
/*      */     
/*      */     private static final Direction[] DIRECTIONS = Direction.values();
/*      */     private static final int SUPPORT_TYPE_COUNT = (SupportType.values()).length;
/*      */     protected final boolean solidRender;
/*      */     final boolean propagatesSkylightDown;
/*      */     final int lightBlock;
/*      */     @Nullable
/*      */     final VoxelShape[] occlusionShapes;
/*      */     protected final VoxelShape collisionShape;
/*      */     protected final boolean largeCollisionShape;
/*      */     private final boolean[] faceSturdy;
/*      */     protected final boolean isCollisionShapeFullBlock;
/*      */     
/*      */     Cache(BlockState $$0) {
/*      */       Block $$1 = $$0.getBlock();
/*      */       this.solidRender = $$0.isSolidRender((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/*      */       this.propagatesSkylightDown = $$1.propagatesSkylightDown($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/*      */       this.lightBlock = $$1.getLightBlock($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/*      */       if (!$$0.canOcclude()) {
/*      */         this.occlusionShapes = null;
/*      */       } else {
/*      */         this.occlusionShapes = new VoxelShape[DIRECTIONS.length];
/*      */         VoxelShape $$2 = $$1.getOcclusionShape($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/*      */         for (Direction $$3 : DIRECTIONS)
/*      */           this.occlusionShapes[$$3.ordinal()] = Shapes.getFaceShape($$2, $$3); 
/*      */       } 
/*      */       this.collisionShape = $$1.getCollisionShape($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
/*      */       if (!this.collisionShape.isEmpty() && $$0.hasOffsetFunction())
/*      */         throw new IllegalStateException(String.format(Locale.ROOT, "%s has a collision shape and an offset type, but is not marked as dynamicShape in its properties.", new Object[] { BuiltInRegistries.BLOCK.getKey($$1) })); 
/*      */       this.largeCollisionShape = Arrays.<Direction.Axis>stream(Direction.Axis.values()).anyMatch($$0 -> (this.collisionShape.min($$0) < 0.0D || this.collisionShape.max($$0) > 1.0D));
/*      */       this.faceSturdy = new boolean[DIRECTIONS.length * SUPPORT_TYPE_COUNT];
/*      */       for (Direction $$4 : DIRECTIONS) {
/*      */         for (SupportType $$5 : SupportType.values())
/*      */           this.faceSturdy[getFaceSupportIndex($$4, $$5)] = $$5.isSupporting($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO, $$4); 
/*      */       } 
/*      */       this.isCollisionShapeFullBlock = Block.isShapeFullBlock($$0.getCollisionShape((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO));
/*      */     }
/*      */     
/*      */     public boolean isFaceSturdy(Direction $$0, SupportType $$1) {
/*      */       return this.faceSturdy[getFaceSupportIndex($$0, $$1)];
/*      */     } }
/*      */ 
/*      */   
/*      */   public static interface StateArgumentPredicate<A> {
/*      */     boolean test(BlockState param1BlockState, BlockGetter param1BlockGetter, BlockPos param1BlockPos, A param1A);
/*      */   }
/*      */   
/*      */   public static interface OffsetFunction {
/*      */     Vec3 evaluate(BlockState param1BlockState, BlockGetter param1BlockGetter, BlockPos param1BlockPos);
/*      */   }
/*      */   
/*      */   public static interface StatePredicate {
/*      */     boolean test(BlockState param1BlockState, BlockGetter param1BlockGetter, BlockPos param1BlockPos);
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\BlockBehaviour.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */