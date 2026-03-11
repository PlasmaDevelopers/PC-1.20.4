/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class WallTorchBlock extends TorchBlock {
/*     */   static {
/*  27 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)PARTICLE_OPTIONS_FIELD.forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, WallTorchBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<WallTorchBlock> CODEC;
/*     */   
/*     */   public MapCodec<WallTorchBlock> codec() {
/*  34 */     return CODEC;
/*     */   }
/*     */   
/*  37 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*     */   
/*     */   protected static final float AABB_OFFSET = 2.5F;
/*  40 */   private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap((Map)ImmutableMap.of(Direction.NORTH, 
/*  41 */         Block.box(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), Direction.SOUTH, 
/*  42 */         Block.box(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D), Direction.WEST, 
/*  43 */         Block.box(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), Direction.EAST, 
/*  44 */         Block.box(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));
/*     */ 
/*     */   
/*     */   protected WallTorchBlock(SimpleParticleType $$0, BlockBehaviour.Properties $$1) {
/*  48 */     super($$0, $$1);
/*  49 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionId() {
/*  54 */     return asItem().getDescriptionId();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  59 */     return getShape($$0);
/*     */   }
/*     */   
/*     */   public static VoxelShape getShape(BlockState $$0) {
/*  63 */     return AABBS.get($$0.getValue((Property)FACING));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  68 */     Direction $$3 = (Direction)$$0.getValue((Property)FACING);
/*  69 */     BlockPos $$4 = $$2.relative($$3.getOpposite());
/*  70 */     BlockState $$5 = $$1.getBlockState($$4);
/*     */     
/*  72 */     return $$5.isFaceSturdy((BlockGetter)$$1, $$4, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  78 */     BlockState $$1 = defaultBlockState();
/*     */     
/*  80 */     Level level = $$0.getLevel();
/*  81 */     BlockPos $$3 = $$0.getClickedPos();
/*     */     
/*  83 */     Direction[] $$4 = $$0.getNearestLookingDirections();
/*  84 */     for (Direction $$5 : $$4) {
/*  85 */       if ($$5.getAxis().isHorizontal()) {
/*     */ 
/*     */ 
/*     */         
/*  89 */         Direction $$6 = $$5.getOpposite();
/*     */         
/*  91 */         $$1 = (BlockState)$$1.setValue((Property)FACING, (Comparable)$$6);
/*  92 */         if ($$1.canSurvive((LevelReader)level, $$3)) {
/*  93 */           return $$1;
/*     */         }
/*     */       } 
/*     */     } 
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 102 */     if ($$1.getOpposite() == $$0.getValue((Property)FACING) && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 103 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/* 105 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 110 */     Direction $$4 = (Direction)$$0.getValue((Property)FACING);
/* 111 */     double $$5 = $$2.getX() + 0.5D;
/* 112 */     double $$6 = $$2.getY() + 0.7D;
/* 113 */     double $$7 = $$2.getZ() + 0.5D;
/* 114 */     double $$8 = 0.22D;
/* 115 */     double $$9 = 0.27D;
/*     */     
/* 117 */     Direction $$10 = $$4.getOpposite();
/* 118 */     $$1.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$5 + 0.27D * $$10.getStepX(), $$6 + 0.22D, $$7 + 0.27D * $$10.getStepZ(), 0.0D, 0.0D, 0.0D);
/* 119 */     $$1.addParticle((ParticleOptions)this.flameParticle, $$5 + 0.27D * $$10.getStepX(), $$6 + 0.22D, $$7 + 0.27D * $$10.getStepZ(), 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 124 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 129 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 134 */     $$0.add(new Property[] { (Property)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WallTorchBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */