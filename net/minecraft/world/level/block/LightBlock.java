/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToIntFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class LightBlock extends Block implements SimpleWaterloggedBlock {
/*  32 */   public static final MapCodec<LightBlock> CODEC = simpleCodec(LightBlock::new);
/*     */   public static final int MAX_LEVEL = 15;
/*     */   
/*     */   public MapCodec<LightBlock> codec() {
/*  36 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  40 */   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;
/*  41 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED; public static final ToIntFunction<BlockState> LIGHT_EMISSION; static {
/*  42 */     LIGHT_EMISSION = ($$0 -> ((Integer)$$0.getValue((Property)LEVEL)).intValue());
/*     */   }
/*     */   public LightBlock(BlockBehaviour.Properties $$0) {
/*  45 */     super($$0);
/*  46 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)LEVEL, Integer.valueOf(15))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  51 */     $$0.add(new Property[] { (Property)LEVEL, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  56 */     if (!$$1.isClientSide && $$3.canUseGameMasterBlocks()) {
/*  57 */       $$1.setBlock($$2, (BlockState)$$0.cycle((Property)LEVEL), 2);
/*  58 */       return InteractionResult.SUCCESS;
/*     */     } 
/*  60 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  65 */     return $$3.isHoldingItem(Items.LIGHT) ? Shapes.block() : Shapes.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  75 */     return RenderShape.INVISIBLE;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getShadeBrightness(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  80 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  85 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  86 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*  88 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/*  93 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  94 */       return Fluids.WATER.getSource(false);
/*     */     }
/*  96 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 101 */     return setLightOnStack(super.getCloneItemStack($$0, $$1, $$2), ((Integer)$$2.getValue((Property)LEVEL)).intValue());
/*     */   }
/*     */   
/*     */   public static ItemStack setLightOnStack(ItemStack $$0, int $$1) {
/* 105 */     if ($$1 != 15) {
/* 106 */       CompoundTag $$2 = new CompoundTag();
/* 107 */       $$2.putString(LEVEL.getName(), String.valueOf($$1));
/* 108 */       $$0.addTagElement("BlockStateTag", (Tag)$$2);
/*     */     } 
/* 110 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LightBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */