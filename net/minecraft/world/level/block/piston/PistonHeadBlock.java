/*     */ package net.minecraft.world.level.block.piston;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.PistonType;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class PistonHeadBlock extends DirectionalBlock {
/*  31 */   public static final MapCodec<PistonHeadBlock> CODEC = simpleCodec(PistonHeadBlock::new);
/*     */ 
/*     */   
/*     */   protected MapCodec<PistonHeadBlock> codec() {
/*  35 */     return CODEC;
/*     */   }
/*     */   
/*  38 */   public static final EnumProperty<PistonType> TYPE = BlockStateProperties.PISTON_TYPE;
/*  39 */   public static final BooleanProperty SHORT = BlockStateProperties.SHORT;
/*     */   
/*     */   public static final float PLATFORM = 4.0F;
/*     */   
/*  43 */   protected static final VoxelShape EAST_AABB = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  44 */   protected static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
/*  45 */   protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
/*  46 */   protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
/*  47 */   protected static final VoxelShape UP_AABB = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  48 */   protected static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
/*     */   
/*     */   protected static final float AABB_OFFSET = 2.0F;
/*     */   
/*     */   protected static final float EDGE_MIN = 6.0F;
/*     */   protected static final float EDGE_MAX = 10.0F;
/*  54 */   protected static final VoxelShape UP_ARM_AABB = Block.box(6.0D, -4.0D, 6.0D, 10.0D, 12.0D, 10.0D);
/*  55 */   protected static final VoxelShape DOWN_ARM_AABB = Block.box(6.0D, 4.0D, 6.0D, 10.0D, 20.0D, 10.0D);
/*  56 */   protected static final VoxelShape SOUTH_ARM_AABB = Block.box(6.0D, 6.0D, -4.0D, 10.0D, 10.0D, 12.0D);
/*  57 */   protected static final VoxelShape NORTH_ARM_AABB = Block.box(6.0D, 6.0D, 4.0D, 10.0D, 10.0D, 20.0D);
/*  58 */   protected static final VoxelShape EAST_ARM_AABB = Block.box(-4.0D, 6.0D, 6.0D, 12.0D, 10.0D, 10.0D);
/*  59 */   protected static final VoxelShape WEST_ARM_AABB = Block.box(4.0D, 6.0D, 6.0D, 20.0D, 10.0D, 10.0D);
/*     */   
/*  61 */   protected static final VoxelShape SHORT_UP_ARM_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 12.0D, 10.0D);
/*  62 */   protected static final VoxelShape SHORT_DOWN_ARM_AABB = Block.box(6.0D, 4.0D, 6.0D, 10.0D, 16.0D, 10.0D);
/*  63 */   protected static final VoxelShape SHORT_SOUTH_ARM_AABB = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 12.0D);
/*  64 */   protected static final VoxelShape SHORT_NORTH_ARM_AABB = Block.box(6.0D, 6.0D, 4.0D, 10.0D, 10.0D, 16.0D);
/*  65 */   protected static final VoxelShape SHORT_EAST_ARM_AABB = Block.box(0.0D, 6.0D, 6.0D, 12.0D, 10.0D, 10.0D);
/*  66 */   protected static final VoxelShape SHORT_WEST_ARM_AABB = Block.box(4.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
/*     */   
/*  68 */   private static final VoxelShape[] SHAPES_SHORT = makeShapes(true);
/*  69 */   private static final VoxelShape[] SHAPES_LONG = makeShapes(false);
/*     */   
/*     */   private static VoxelShape[] makeShapes(boolean $$0) {
/*  72 */     return (VoxelShape[])Arrays.<Direction>stream(Direction.values()).map($$1 -> calculateShape($$1, $$0)).toArray($$0 -> new VoxelShape[$$0]);
/*     */   }
/*     */   
/*     */   private static VoxelShape calculateShape(Direction $$0, boolean $$1) {
/*  76 */     switch ($$0)
/*     */     
/*     */     { default:
/*  79 */         return Shapes.or(DOWN_AABB, $$1 ? SHORT_DOWN_ARM_AABB : DOWN_ARM_AABB);
/*     */       case UP:
/*  81 */         return Shapes.or(UP_AABB, $$1 ? SHORT_UP_ARM_AABB : UP_ARM_AABB);
/*     */       case NORTH:
/*  83 */         return Shapes.or(NORTH_AABB, $$1 ? SHORT_NORTH_ARM_AABB : NORTH_ARM_AABB);
/*     */       case SOUTH:
/*  85 */         return Shapes.or(SOUTH_AABB, $$1 ? SHORT_SOUTH_ARM_AABB : SOUTH_ARM_AABB);
/*     */       case WEST:
/*  87 */         return Shapes.or(WEST_AABB, $$1 ? SHORT_WEST_ARM_AABB : WEST_ARM_AABB);
/*     */       case EAST:
/*  89 */         break; }  return Shapes.or(EAST_AABB, $$1 ? SHORT_EAST_ARM_AABB : EAST_ARM_AABB);
/*     */   }
/*     */ 
/*     */   
/*     */   public PistonHeadBlock(BlockBehaviour.Properties $$0) {
/*  94 */     super($$0);
/*  95 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)TYPE, (Comparable)PistonType.DEFAULT)).setValue((Property)SHORT, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 105 */     return (((Boolean)$$0.getValue((Property)SHORT)).booleanValue() ? SHAPES_SHORT : SHAPES_LONG)[((Direction)$$0.getValue((Property)FACING)).ordinal()];
/*     */   }
/*     */   
/*     */   private boolean isFittingBase(BlockState $$0, BlockState $$1) {
/* 109 */     Block $$2 = ($$0.getValue((Property)TYPE) == PistonType.DEFAULT) ? Blocks.PISTON : Blocks.STICKY_PISTON;
/* 110 */     return ($$1.is($$2) && ((Boolean)$$1.getValue((Property)PistonBaseBlock.EXTENDED)).booleanValue() && $$1.getValue((Property)FACING) == $$0.getValue((Property)FACING));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 115 */     if (!$$0.isClientSide && ($$3.getAbilities()).instabuild) {
/* 116 */       BlockPos $$4 = $$1.relative(((Direction)$$2.getValue((Property)FACING)).getOpposite());
/* 117 */       if (isFittingBase($$2, $$0.getBlockState($$4))) {
/* 118 */         $$0.destroyBlock($$4, false);
/*     */       }
/*     */     } 
/* 121 */     return super.playerWillDestroy($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 126 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/* 129 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */ 
/*     */     
/* 132 */     BlockPos $$5 = $$2.relative(((Direction)$$0.getValue((Property)FACING)).getOpposite());
/* 133 */     if (isFittingBase($$0, $$1.getBlockState($$5))) {
/* 134 */       $$1.destroyBlock($$5, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 140 */     if ($$1.getOpposite() == $$0.getValue((Property)FACING) && 
/* 141 */       !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 142 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 145 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 150 */     BlockState $$3 = $$1.getBlockState($$2.relative(((Direction)$$0.getValue((Property)FACING)).getOpposite()));
/*     */     
/* 152 */     return (isFittingBase($$0, $$3) || ($$3.is(Blocks.MOVING_PISTON) && $$3.getValue((Property)FACING) == $$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 157 */     if ($$0.canSurvive((LevelReader)$$1, $$2)) {
/* 158 */       $$1.neighborChanged($$2.relative(((Direction)$$0.getValue((Property)FACING)).getOpposite()), $$3, $$4);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 164 */     return new ItemStack(($$2.getValue((Property)TYPE) == PistonType.STICKY) ? (ItemLike)Blocks.STICKY_PISTON : (ItemLike)Blocks.PISTON);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 169 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 174 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 179 */     $$0.add(new Property[] { (Property)FACING, (Property)TYPE, (Property)SHORT });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 184 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\piston\PistonHeadBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */