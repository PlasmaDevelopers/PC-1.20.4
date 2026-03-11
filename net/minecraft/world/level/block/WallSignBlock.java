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
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.WoodType;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class WallSignBlock extends SignBlock {
/*     */   static {
/*  27 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)WoodType.CODEC.fieldOf("wood_type").forGetter(SignBlock::type), (App)propertiesCodec()).apply((Applicative)$$0, WallSignBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<WallSignBlock> CODEC;
/*     */   
/*     */   public MapCodec<WallSignBlock> codec() {
/*  34 */     return CODEC;
/*     */   }
/*     */   
/*  37 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*     */   
/*     */   protected static final float AABB_THICKNESS = 2.0F;
/*     */   protected static final float AABB_BOTTOM = 4.5F;
/*     */   protected static final float AABB_TOP = 12.5F;
/*  42 */   private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap((Map)ImmutableMap.of(Direction.NORTH, 
/*  43 */         Block.box(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D), Direction.SOUTH, 
/*  44 */         Block.box(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D), Direction.EAST, 
/*  45 */         Block.box(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D), Direction.WEST, 
/*  46 */         Block.box(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D)));
/*     */ 
/*     */   
/*     */   public WallSignBlock(WoodType $$0, BlockBehaviour.Properties $$1) {
/*  50 */     super($$0, $$1.sound($$0.soundType()));
/*  51 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionId() {
/*  56 */     return asItem().getDescriptionId();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  61 */     return AABBS.get($$0.getValue((Property)FACING));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  66 */     return $$1.getBlockState($$2.relative(((Direction)$$0.getValue((Property)FACING)).getOpposite())).isSolid();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  72 */     BlockState $$1 = defaultBlockState();
/*  73 */     FluidState $$2 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*     */     
/*  75 */     Level level = $$0.getLevel();
/*  76 */     BlockPos $$4 = $$0.getClickedPos();
/*     */     
/*  78 */     Direction[] $$5 = $$0.getNearestLookingDirections();
/*  79 */     for (Direction $$6 : $$5) {
/*  80 */       if ($$6.getAxis().isHorizontal()) {
/*     */ 
/*     */ 
/*     */         
/*  84 */         Direction $$7 = $$6.getOpposite();
/*     */         
/*  86 */         $$1 = (BlockState)$$1.setValue((Property)FACING, (Comparable)$$7);
/*  87 */         if ($$1.canSurvive((LevelReader)level, $$4)) {
/*  88 */           return (BlockState)$$1.setValue((Property)WATERLOGGED, Boolean.valueOf(($$2.getType() == Fluids.WATER)));
/*     */         }
/*     */       } 
/*     */     } 
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  97 */     if ($$1.getOpposite() == $$0.getValue((Property)FACING) && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  98 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/* 100 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getYRotationDegrees(BlockState $$0) {
/* 105 */     return ((Direction)$$0.getValue((Property)FACING)).toYRot();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getSignHitboxCenterPosition(BlockState $$0) {
/* 110 */     VoxelShape $$1 = AABBS.get($$0.getValue((Property)FACING));
/* 111 */     return $$1.bounds().getCenter();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 116 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 121 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 126 */     $$0.add(new Property[] { (Property)FACING, (Property)WATERLOGGED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WallSignBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */