/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class WallBannerBlock extends AbstractBannerBlock {
/*     */   static {
/*  23 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DyeColor.CODEC.fieldOf("color").forGetter(AbstractBannerBlock::getColor), (App)propertiesCodec()).apply((Applicative)$$0, WallBannerBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<WallBannerBlock> CODEC;
/*     */   
/*     */   public MapCodec<WallBannerBlock> codec() {
/*  30 */     return CODEC;
/*     */   }
/*     */   
/*  33 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*     */   
/*  35 */   private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap((Map)ImmutableMap.of(Direction.NORTH, 
/*  36 */         Block.box(0.0D, 0.0D, 14.0D, 16.0D, 12.5D, 16.0D), Direction.SOUTH, 
/*  37 */         Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.5D, 2.0D), Direction.WEST, 
/*  38 */         Block.box(14.0D, 0.0D, 0.0D, 16.0D, 12.5D, 16.0D), Direction.EAST, 
/*  39 */         Block.box(0.0D, 0.0D, 0.0D, 2.0D, 12.5D, 16.0D)));
/*     */ 
/*     */   
/*     */   public WallBannerBlock(DyeColor $$0, BlockBehaviour.Properties $$1) {
/*  43 */     super($$0, $$1);
/*  44 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionId() {
/*  49 */     return asItem().getDescriptionId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  55 */     return $$1.getBlockState($$2.relative(((Direction)$$0.getValue((Property)FACING)).getOpposite())).isSolid();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  60 */     if ($$1 == ((Direction)$$0.getValue((Property)FACING)).getOpposite() && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  61 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/*  64 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  69 */     return SHAPES.get($$0.getValue((Property)FACING));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  74 */     BlockState $$1 = defaultBlockState();
/*     */     
/*  76 */     Level level = $$0.getLevel();
/*  77 */     BlockPos $$3 = $$0.getClickedPos();
/*     */     
/*  79 */     Direction[] $$4 = $$0.getNearestLookingDirections();
/*  80 */     for (Direction $$5 : $$4) {
/*  81 */       if ($$5.getAxis().isHorizontal()) {
/*     */ 
/*     */ 
/*     */         
/*  85 */         Direction $$6 = $$5.getOpposite();
/*     */         
/*  87 */         $$1 = (BlockState)$$1.setValue((Property)FACING, (Comparable)$$6);
/*  88 */         if ($$1.canSurvive((LevelReader)level, $$3)) {
/*  89 */           return $$1;
/*     */         }
/*     */       } 
/*     */     } 
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  98 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 103 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 108 */     $$0.add(new Property[] { (Property)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WallBannerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */