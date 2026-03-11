/*      */ package net.minecraft.world.level.block.state;
/*      */ 
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.mojang.serialization.MapCodec;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Optional;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.HolderSet;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.tags.TagKey;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.MenuProvider;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.projectile.Projectile;
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
/*      */ import net.minecraft.world.level.storage.loot.LootParams;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.BlockHitResult;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.phys.shapes.CollisionContext;
/*      */ import net.minecraft.world.phys.shapes.Shapes;
/*      */ import net.minecraft.world.phys.shapes.VoxelShape;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class BlockStateBase
/*      */   extends StateHolder<Block, BlockState>
/*      */ {
/*      */   private final int lightEmission;
/*      */   private final boolean useShapeForLightOcclusion;
/*      */   private final boolean isAir;
/*      */   private final boolean ignitedByLava;
/*      */   @Deprecated
/*      */   private final boolean liquid;
/*      */   @Deprecated
/*      */   private boolean legacySolid;
/*      */   private final PushReaction pushReaction;
/*      */   private final MapColor mapColor;
/*      */   private final float destroySpeed;
/*      */   private final boolean requiresCorrectToolForDrops;
/*      */   private final boolean canOcclude;
/*      */   private final BlockBehaviour.StatePredicate isRedstoneConductor;
/*      */   private final BlockBehaviour.StatePredicate isSuffocating;
/*      */   private final BlockBehaviour.StatePredicate isViewBlocking;
/*      */   private final BlockBehaviour.StatePredicate hasPostProcess;
/*      */   private final BlockBehaviour.StatePredicate emissiveRendering;
/*      */   private final Optional<BlockBehaviour.OffsetFunction> offsetFunction;
/*      */   private final boolean spawnTerrainParticles;
/*      */   private final NoteBlockInstrument instrument;
/*      */   private final boolean replaceable;
/*      */   @Nullable
/*      */   protected Cache cache;
/*  868 */   private FluidState fluidState = Fluids.EMPTY.defaultFluidState();
/*      */   private boolean isRandomlyTicking;
/*      */   
/*      */   protected BlockStateBase(Block $$0, ImmutableMap<Property<?>, Comparable<?>> $$1, MapCodec<BlockState> $$2) {
/*  872 */     super($$0, $$1, $$2);
/*  873 */     BlockBehaviour.Properties $$3 = $$0.properties;
/*      */     
/*  875 */     this.lightEmission = $$3.lightEmission.applyAsInt(asState());
/*  876 */     this.useShapeForLightOcclusion = $$0.useShapeForLightOcclusion(asState());
/*  877 */     this.isAir = $$3.isAir;
/*  878 */     this.ignitedByLava = $$3.ignitedByLava;
/*  879 */     this.liquid = $$3.liquid;
/*  880 */     this.pushReaction = $$3.pushReaction;
/*  881 */     this.mapColor = $$3.mapColor.apply(asState());
/*  882 */     this.destroySpeed = $$3.destroyTime;
/*  883 */     this.requiresCorrectToolForDrops = $$3.requiresCorrectToolForDrops;
/*  884 */     this.canOcclude = $$3.canOcclude;
/*  885 */     this.isRedstoneConductor = $$3.isRedstoneConductor;
/*  886 */     this.isSuffocating = $$3.isSuffocating;
/*  887 */     this.isViewBlocking = $$3.isViewBlocking;
/*  888 */     this.hasPostProcess = $$3.hasPostProcess;
/*  889 */     this.emissiveRendering = $$3.emissiveRendering;
/*  890 */     this.offsetFunction = $$3.offsetFunction;
/*  891 */     this.spawnTerrainParticles = $$3.spawnTerrainParticles;
/*  892 */     this.instrument = $$3.instrument;
/*  893 */     this.replaceable = $$3.replaceable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean calculateSolid() {
/*  902 */     if (this.owner.properties.forceSolidOn) {
/*  903 */       return true;
/*      */     }
/*  905 */     if (this.owner.properties.forceSolidOff) {
/*  906 */       return false;
/*      */     }
/*  908 */     if (this.cache == null) {
/*  909 */       return false;
/*      */     }
/*  911 */     VoxelShape $$0 = this.cache.collisionShape;
/*  912 */     if ($$0.isEmpty()) {
/*  913 */       return false;
/*      */     }
/*  915 */     AABB $$1 = $$0.bounds();
/*  916 */     if ($$1.getSize() >= 0.7291666666666666D) {
/*  917 */       return true;
/*      */     }
/*  919 */     if ($$1.getYsize() >= 1.0D) {
/*  920 */       return true;
/*      */     }
/*  922 */     return false;
/*      */   }
/*      */   
/*      */   public void initCache() {
/*  926 */     this.fluidState = this.owner.getFluidState(asState());
/*  927 */     this.isRandomlyTicking = this.owner.isRandomlyTicking(asState());
/*  928 */     if (!getBlock().hasDynamicShape()) {
/*  929 */       this.cache = new Cache(asState());
/*      */     }
/*  931 */     this.legacySolid = calculateSolid();
/*      */   }
/*      */   
/*      */   public Block getBlock() {
/*  935 */     return this.owner;
/*      */   }
/*      */   
/*      */   public Holder<Block> getBlockHolder() {
/*  939 */     return (Holder<Block>)this.owner.builtInRegistryHolder();
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
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean blocksMotion() {
/*  955 */     Block $$0 = getBlock();
/*  956 */     return ($$0 != Blocks.COBWEB && $$0 != Blocks.BAMBOO_SAPLING && isSolid());
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isSolid() {
/*  974 */     return this.legacySolid;
/*      */   }
/*      */   
/*      */   public boolean isValidSpawn(BlockGetter $$0, BlockPos $$1, EntityType<?> $$2) {
/*  978 */     return (getBlock()).properties.isValidSpawn.test(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public boolean propagatesSkylightDown(BlockGetter $$0, BlockPos $$1) {
/*  982 */     if (this.cache != null) {
/*  983 */       return this.cache.propagatesSkylightDown;
/*      */     }
/*  985 */     return getBlock().propagatesSkylightDown(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public int getLightBlock(BlockGetter $$0, BlockPos $$1) {
/*  989 */     if (this.cache != null) {
/*  990 */       return this.cache.lightBlock;
/*      */     }
/*  992 */     return getBlock().getLightBlock(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public VoxelShape getFaceOcclusionShape(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/*  996 */     if (this.cache != null && this.cache.occlusionShapes != null) {
/*  997 */       return this.cache.occlusionShapes[$$2.ordinal()];
/*      */     }
/*      */     
/* 1000 */     return Shapes.getFaceShape(getOcclusionShape($$0, $$1), $$2);
/*      */   }
/*      */   
/*      */   public VoxelShape getOcclusionShape(BlockGetter $$0, BlockPos $$1) {
/* 1004 */     return getBlock().getOcclusionShape(asState(), $$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasLargeCollisionShape() {
/* 1009 */     return (this.cache == null || this.cache.largeCollisionShape);
/*      */   }
/*      */   
/*      */   public boolean useShapeForLightOcclusion() {
/* 1013 */     return this.useShapeForLightOcclusion;
/*      */   }
/*      */   
/*      */   public int getLightEmission() {
/* 1017 */     return this.lightEmission;
/*      */   }
/*      */   
/*      */   public boolean isAir() {
/* 1021 */     return this.isAir;
/*      */   }
/*      */   
/*      */   public boolean ignitedByLava() {
/* 1025 */     return this.ignitedByLava;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean liquid() {
/* 1031 */     return this.liquid;
/*      */   }
/*      */   
/*      */   public MapColor getMapColor(BlockGetter $$0, BlockPos $$1) {
/* 1035 */     return this.mapColor;
/*      */   }
/*      */   
/*      */   public BlockState rotate(Rotation $$0) {
/* 1039 */     return getBlock().rotate(asState(), $$0);
/*      */   }
/*      */   
/*      */   public BlockState mirror(Mirror $$0) {
/* 1043 */     return getBlock().mirror(asState(), $$0);
/*      */   }
/*      */   
/*      */   public RenderShape getRenderShape() {
/* 1047 */     return getBlock().getRenderShape(asState());
/*      */   }
/*      */   
/*      */   public boolean emissiveRendering(BlockGetter $$0, BlockPos $$1) {
/* 1051 */     return this.emissiveRendering.test(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public float getShadeBrightness(BlockGetter $$0, BlockPos $$1) {
/* 1055 */     return getBlock().getShadeBrightness(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public boolean isRedstoneConductor(BlockGetter $$0, BlockPos $$1) {
/* 1059 */     return this.isRedstoneConductor.test(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public boolean isSignalSource() {
/* 1063 */     return getBlock().isSignalSource(asState());
/*      */   }
/*      */   
/*      */   public int getSignal(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/* 1067 */     return getBlock().getSignal(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public boolean hasAnalogOutputSignal() {
/* 1071 */     return getBlock().hasAnalogOutputSignal(asState());
/*      */   }
/*      */   
/*      */   public int getAnalogOutputSignal(Level $$0, BlockPos $$1) {
/* 1075 */     return getBlock().getAnalogOutputSignal(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public float getDestroySpeed(BlockGetter $$0, BlockPos $$1) {
/* 1079 */     return this.destroySpeed;
/*      */   }
/*      */   
/*      */   public float getDestroyProgress(Player $$0, BlockGetter $$1, BlockPos $$2) {
/* 1083 */     return getBlock().getDestroyProgress(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public int getDirectSignal(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/* 1087 */     return getBlock().getDirectSignal(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public PushReaction getPistonPushReaction() {
/* 1091 */     return this.pushReaction;
/*      */   }
/*      */   
/*      */   public boolean isSolidRender(BlockGetter $$0, BlockPos $$1) {
/* 1095 */     if (this.cache != null) {
/* 1096 */       return this.cache.solidRender;
/*      */     }
/* 1098 */     BlockState $$2 = asState();
/* 1099 */     if ($$2.canOcclude()) {
/* 1100 */       return Block.isShapeFullBlock($$2.getOcclusionShape($$0, $$1));
/*      */     }
/* 1102 */     return false;
/*      */   }
/*      */   
/*      */   public boolean canOcclude() {
/* 1106 */     return this.canOcclude;
/*      */   }
/*      */   
/*      */   public boolean skipRendering(BlockState $$0, Direction $$1) {
/* 1110 */     return getBlock().skipRendering(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public VoxelShape getShape(BlockGetter $$0, BlockPos $$1) {
/* 1114 */     return getShape($$0, $$1, CollisionContext.empty());
/*      */   }
/*      */   
/*      */   public VoxelShape getShape(BlockGetter $$0, BlockPos $$1, CollisionContext $$2) {
/* 1118 */     return getBlock().getShape(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public VoxelShape getCollisionShape(BlockGetter $$0, BlockPos $$1) {
/* 1122 */     if (this.cache != null) {
/* 1123 */       return this.cache.collisionShape;
/*      */     }
/* 1125 */     return getCollisionShape($$0, $$1, CollisionContext.empty());
/*      */   }
/*      */   
/*      */   public VoxelShape getCollisionShape(BlockGetter $$0, BlockPos $$1, CollisionContext $$2) {
/* 1129 */     return getBlock().getCollisionShape(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public VoxelShape getBlockSupportShape(BlockGetter $$0, BlockPos $$1) {
/* 1133 */     return getBlock().getBlockSupportShape(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public VoxelShape getVisualShape(BlockGetter $$0, BlockPos $$1, CollisionContext $$2) {
/* 1137 */     return getBlock().getVisualShape(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public VoxelShape getInteractionShape(BlockGetter $$0, BlockPos $$1) {
/* 1141 */     return getBlock().getInteractionShape(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public final boolean entityCanStandOn(BlockGetter $$0, BlockPos $$1, Entity $$2) {
/* 1145 */     return entityCanStandOnFace($$0, $$1, $$2, Direction.UP);
/*      */   }
/*      */   
/*      */   public final boolean entityCanStandOnFace(BlockGetter $$0, BlockPos $$1, Entity $$2, Direction $$3) {
/* 1149 */     return Block.isFaceFull(getCollisionShape($$0, $$1, CollisionContext.of($$2)), $$3);
/*      */   }
/*      */   
/*      */   public Vec3 getOffset(BlockGetter $$0, BlockPos $$1) {
/* 1153 */     return this.offsetFunction.<Vec3>map($$2 -> $$2.evaluate(asState(), $$0, $$1)).orElse(Vec3.ZERO);
/*      */   }
/*      */   
/*      */   public boolean hasOffsetFunction() {
/* 1157 */     return this.offsetFunction.isPresent();
/*      */   }
/*      */   
/*      */   public boolean triggerEvent(Level $$0, BlockPos $$1, int $$2, int $$3) {
/* 1161 */     return getBlock().triggerEvent(asState(), $$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void neighborChanged(Level $$0, BlockPos $$1, Block $$2, BlockPos $$3, boolean $$4) {
/* 1167 */     getBlock().neighborChanged(asState(), $$0, $$1, $$2, $$3, $$4);
/*      */   }
/*      */   
/*      */   public final void updateNeighbourShapes(LevelAccessor $$0, BlockPos $$1, int $$2) {
/* 1171 */     updateNeighbourShapes($$0, $$1, $$2, 512);
/*      */   }
/*      */   
/*      */   public final void updateNeighbourShapes(LevelAccessor $$0, BlockPos $$1, int $$2, int $$3) {
/* 1175 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/* 1176 */     for (Direction $$5 : BlockBehaviour.UPDATE_SHAPE_ORDER) {
/* 1177 */       $$4.setWithOffset((Vec3i)$$1, $$5);
/* 1178 */       $$0.neighborShapeChanged($$5.getOpposite(), asState(), (BlockPos)$$4, $$1, $$2, $$3);
/*      */     } 
/*      */   }
/*      */   
/*      */   public final void updateIndirectNeighbourShapes(LevelAccessor $$0, BlockPos $$1, int $$2) {
/* 1183 */     updateIndirectNeighbourShapes($$0, $$1, $$2, 512);
/*      */   }
/*      */   
/*      */   public void updateIndirectNeighbourShapes(LevelAccessor $$0, BlockPos $$1, int $$2, int $$3) {
/* 1187 */     getBlock().updateIndirectNeighbourShapes(asState(), $$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public void onPlace(Level $$0, BlockPos $$1, BlockState $$2, boolean $$3) {
/* 1191 */     getBlock().onPlace(asState(), $$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public void onRemove(Level $$0, BlockPos $$1, BlockState $$2, boolean $$3) {
/* 1195 */     getBlock().onRemove(asState(), $$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public void onExplosionHit(Level $$0, BlockPos $$1, Explosion $$2, BiConsumer<ItemStack, BlockPos> $$3) {
/* 1199 */     getBlock().onExplosionHit(asState(), $$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public void tick(ServerLevel $$0, BlockPos $$1, RandomSource $$2) {
/* 1203 */     getBlock().tick(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public void randomTick(ServerLevel $$0, BlockPos $$1, RandomSource $$2) {
/* 1207 */     getBlock().randomTick(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public void entityInside(Level $$0, BlockPos $$1, Entity $$2) {
/* 1211 */     getBlock().entityInside(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public void spawnAfterBreak(ServerLevel $$0, BlockPos $$1, ItemStack $$2, boolean $$3) {
/* 1215 */     getBlock().spawnAfterBreak(asState(), $$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public List<ItemStack> getDrops(LootParams.Builder $$0) {
/* 1219 */     return getBlock().getDrops(asState(), $$0);
/*      */   }
/*      */   
/*      */   public InteractionResult use(Level $$0, Player $$1, InteractionHand $$2, BlockHitResult $$3) {
/* 1223 */     return getBlock().use(asState(), $$0, $$3.getBlockPos(), $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public void attack(Level $$0, BlockPos $$1, Player $$2) {
/* 1227 */     getBlock().attack(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public boolean isSuffocating(BlockGetter $$0, BlockPos $$1) {
/* 1231 */     return this.isSuffocating.test(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public boolean isViewBlocking(BlockGetter $$0, BlockPos $$1) {
/* 1235 */     return this.isViewBlocking.test(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public BlockState updateShape(Direction $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3, BlockPos $$4) {
/* 1239 */     return getBlock().updateShape(asState(), $$0, $$1, $$2, $$3, $$4);
/*      */   }
/*      */   
/*      */   public boolean isPathfindable(BlockGetter $$0, BlockPos $$1, PathComputationType $$2) {
/* 1243 */     return getBlock().isPathfindable(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public boolean canBeReplaced(BlockPlaceContext $$0) {
/* 1247 */     return getBlock().canBeReplaced(asState(), $$0);
/*      */   }
/*      */   
/*      */   public boolean canBeReplaced(Fluid $$0) {
/* 1251 */     return getBlock().canBeReplaced(asState(), $$0);
/*      */   }
/*      */   
/*      */   public boolean canBeReplaced() {
/* 1255 */     return this.replaceable;
/*      */   }
/*      */   
/*      */   public boolean canSurvive(LevelReader $$0, BlockPos $$1) {
/* 1259 */     return getBlock().canSurvive(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public boolean hasPostProcess(BlockGetter $$0, BlockPos $$1) {
/* 1263 */     return this.hasPostProcess.test(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MenuProvider getMenuProvider(Level $$0, BlockPos $$1) {
/* 1268 */     return getBlock().getMenuProvider(asState(), $$0, $$1);
/*      */   }
/*      */   
/*      */   public boolean is(TagKey<Block> $$0) {
/* 1272 */     return getBlock().builtInRegistryHolder().is($$0);
/*      */   }
/*      */   
/*      */   public boolean is(TagKey<Block> $$0, Predicate<BlockStateBase> $$1) {
/* 1276 */     return (is($$0) && $$1.test(this));
/*      */   }
/*      */   
/*      */   public boolean is(HolderSet<Block> $$0) {
/* 1280 */     return $$0.contains((Holder)getBlock().builtInRegistryHolder());
/*      */   }
/*      */   
/*      */   public boolean is(Holder<Block> $$0) {
/* 1284 */     return is((Block)$$0.value());
/*      */   }
/*      */   
/*      */   public Stream<TagKey<Block>> getTags() {
/* 1288 */     return getBlock().builtInRegistryHolder().tags();
/*      */   }
/*      */   
/*      */   public boolean hasBlockEntity() {
/* 1292 */     return getBlock() instanceof EntityBlock;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public <T extends net.minecraft.world.level.block.entity.BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockEntityType<T> $$1) {
/* 1297 */     if (getBlock() instanceof EntityBlock) {
/* 1298 */       return ((EntityBlock)getBlock()).getTicker($$0, asState(), $$1);
/*      */     }
/* 1300 */     return null;
/*      */   }
/*      */   
/*      */   public boolean is(Block $$0) {
/* 1304 */     return (getBlock() == $$0);
/*      */   }
/*      */   
/*      */   public boolean is(ResourceKey<Block> $$0) {
/* 1308 */     return getBlock().builtInRegistryHolder().is($$0);
/*      */   }
/*      */   
/*      */   public FluidState getFluidState() {
/* 1312 */     return this.fluidState;
/*      */   }
/*      */   
/*      */   public boolean isRandomlyTicking() {
/* 1316 */     return this.isRandomlyTicking;
/*      */   }
/*      */   
/*      */   public long getSeed(BlockPos $$0) {
/* 1320 */     return getBlock().getSeed(asState(), $$0);
/*      */   }
/*      */   
/*      */   public SoundType getSoundType() {
/* 1324 */     return getBlock().getSoundType(asState());
/*      */   }
/*      */   
/*      */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 1328 */     getBlock().onProjectileHit($$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public boolean isFaceSturdy(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/* 1332 */     return isFaceSturdy($$0, $$1, $$2, SupportType.FULL);
/*      */   }
/*      */   
/*      */   public boolean isFaceSturdy(BlockGetter $$0, BlockPos $$1, Direction $$2, SupportType $$3) {
/* 1336 */     if (this.cache != null) {
/* 1337 */       return this.cache.isFaceSturdy($$2, $$3);
/*      */     }
/* 1339 */     return $$3.isSupporting(asState(), $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public boolean isCollisionShapeFullBlock(BlockGetter $$0, BlockPos $$1) {
/* 1343 */     if (this.cache != null) {
/* 1344 */       return this.cache.isCollisionShapeFullBlock;
/*      */     }
/* 1346 */     return getBlock().isCollisionShapeFullBlock(asState(), $$0, $$1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requiresCorrectToolForDrops() {
/* 1352 */     return this.requiresCorrectToolForDrops;
/*      */   }
/*      */   
/*      */   public boolean shouldSpawnTerrainParticles() {
/* 1356 */     return this.spawnTerrainParticles;
/*      */   }
/*      */   protected abstract BlockState asState();
/*      */   public NoteBlockInstrument instrument() {
/* 1360 */     return this.instrument;
/*      */   }
/*      */   
/*      */   private static final class Cache {
/* 1364 */     private static final Direction[] DIRECTIONS = Direction.values();
/* 1365 */     private static final int SUPPORT_TYPE_COUNT = (SupportType.values()).length;
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
/* 1377 */       Block $$1 = $$0.getBlock();
/* 1378 */       this.solidRender = $$0.isSolidRender((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/* 1379 */       this.propagatesSkylightDown = $$1.propagatesSkylightDown($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/* 1380 */       this.lightBlock = $$1.getLightBlock($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/* 1381 */       if (!$$0.canOcclude()) {
/* 1382 */         this.occlusionShapes = null;
/*      */       } else {
/* 1384 */         this.occlusionShapes = new VoxelShape[DIRECTIONS.length];
/* 1385 */         VoxelShape $$2 = $$1.getOcclusionShape($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
/* 1386 */         for (Direction $$3 : DIRECTIONS) {
/* 1387 */           this.occlusionShapes[$$3.ordinal()] = Shapes.getFaceShape($$2, $$3);
/*      */         }
/*      */       } 
/*      */       
/* 1391 */       this.collisionShape = $$1.getCollisionShape($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
/* 1392 */       if (!this.collisionShape.isEmpty() && $$0.hasOffsetFunction()) {
/* 1393 */         throw new IllegalStateException(String.format(Locale.ROOT, "%s has a collision shape and an offset type, but is not marked as dynamicShape in its properties.", new Object[] { BuiltInRegistries.BLOCK.getKey($$1) }));
/*      */       }
/* 1395 */       this.largeCollisionShape = Arrays.<Direction.Axis>stream(Direction.Axis.values()).anyMatch($$0 -> (this.collisionShape.min($$0) < 0.0D || this.collisionShape.max($$0) > 1.0D));
/* 1396 */       this.faceSturdy = new boolean[DIRECTIONS.length * SUPPORT_TYPE_COUNT];
/* 1397 */       for (Direction $$4 : DIRECTIONS) {
/* 1398 */         for (SupportType $$5 : SupportType.values()) {
/* 1399 */           this.faceSturdy[getFaceSupportIndex($$4, $$5)] = $$5.isSupporting($$0, (BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO, $$4);
/*      */         }
/*      */       } 
/* 1402 */       this.isCollisionShapeFullBlock = Block.isShapeFullBlock($$0.getCollisionShape((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO));
/*      */     }
/*      */     
/*      */     public boolean isFaceSturdy(Direction $$0, SupportType $$1) {
/* 1406 */       return this.faceSturdy[getFaceSupportIndex($$0, $$1)];
/*      */     }
/*      */     
/*      */     private static int getFaceSupportIndex(Direction $$0, SupportType $$1) {
/* 1410 */       return $$0.ordinal() * SUPPORT_TYPE_COUNT + $$1.ordinal();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\BlockBehaviour$BlockStateBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */