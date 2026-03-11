/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RotationSegment;
/*     */ import net.minecraft.world.level.block.state.properties.WoodType;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class CeilingHangingSignBlock extends SignBlock {
/*     */   static {
/*  43 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)WoodType.CODEC.fieldOf("wood_type").forGetter(SignBlock::type), (App)propertiesCodec()).apply((Applicative)$$0, CeilingHangingSignBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<CeilingHangingSignBlock> CODEC;
/*     */   
/*     */   public MapCodec<CeilingHangingSignBlock> codec() {
/*  50 */     return CODEC;
/*     */   }
/*     */   
/*  53 */   public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
/*  54 */   public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
/*     */   protected static final float AABB_OFFSET = 5.0F;
/*  56 */   protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
/*  57 */   private static final Map<Integer, VoxelShape> AABBS = Maps.newHashMap((Map)ImmutableMap.of(
/*  58 */         Integer.valueOf(0), Block.box(1.0D, 0.0D, 7.0D, 15.0D, 10.0D, 9.0D), 
/*  59 */         Integer.valueOf(4), Block.box(7.0D, 0.0D, 1.0D, 9.0D, 10.0D, 15.0D), 
/*  60 */         Integer.valueOf(8), Block.box(1.0D, 0.0D, 7.0D, 15.0D, 10.0D, 9.0D), 
/*  61 */         Integer.valueOf(12), Block.box(7.0D, 0.0D, 1.0D, 9.0D, 10.0D, 15.0D)));
/*     */ 
/*     */   
/*     */   public CeilingHangingSignBlock(WoodType $$0, BlockBehaviour.Properties $$1) {
/*  65 */     super($$0, $$1.sound($$0.hangingSignSoundType()));
/*  66 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)ROTATION, Integer.valueOf(0))).setValue((Property)ATTACHED, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  71 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof SignBlockEntity) { SignBlockEntity $$6 = (SignBlockEntity)blockEntity;
/*  72 */       ItemStack $$7 = $$3.getItemInHand($$4);
/*     */       
/*  74 */       if (shouldTryToChainAnotherHangingSign($$3, $$5, $$6, $$7)) {
/*  75 */         return InteractionResult.PASS;
/*     */       } }
/*     */     
/*  78 */     return super.use($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private boolean shouldTryToChainAnotherHangingSign(Player $$0, BlockHitResult $$1, SignBlockEntity $$2, ItemStack $$3) {
/*  82 */     return (!$$2.canExecuteClickCommands($$2.isFacingFrontText($$0), $$0) && $$3
/*  83 */       .getItem() instanceof net.minecraft.world.item.HangingSignItem && $$1.getDirection().equals(Direction.DOWN));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  88 */     return $$1.getBlockState($$2.above()).isFaceSturdy((BlockGetter)$$1, $$2.above(), Direction.DOWN, SupportType.CENTER);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  93 */     Level $$1 = $$0.getLevel();
/*  94 */     FluidState $$2 = $$1.getFluidState($$0.getClickedPos());
/*  95 */     BlockPos $$3 = $$0.getClickedPos().above();
/*  96 */     BlockState $$4 = $$1.getBlockState($$3);
/*  97 */     boolean $$5 = $$4.is(BlockTags.ALL_HANGING_SIGNS);
/*  98 */     Direction $$6 = Direction.fromYRot($$0.getRotation());
/*  99 */     boolean $$7 = (!Block.isFaceFull($$4.getCollisionShape((BlockGetter)$$1, $$3), Direction.DOWN) || $$0.isSecondaryUseActive());
/*     */     
/* 101 */     if ($$5 && !$$0.isSecondaryUseActive()) {
/* 102 */       if ($$4.hasProperty((Property)WallHangingSignBlock.FACING)) {
/* 103 */         Direction $$8 = (Direction)$$4.getValue((Property)WallHangingSignBlock.FACING);
/* 104 */         if ($$8.getAxis().test($$6)) {
/* 105 */           $$7 = false;
/*     */         }
/* 107 */       } else if ($$4.hasProperty((Property)ROTATION)) {
/* 108 */         Optional<Direction> $$9 = RotationSegment.convertToDirection(((Integer)$$4.getValue((Property)ROTATION)).intValue());
/* 109 */         if ($$9.isPresent() && ((Direction)$$9.get()).getAxis().test($$6)) {
/* 110 */           $$7 = false;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 115 */     int $$10 = !$$7 ? RotationSegment.convertToSegment($$6.getOpposite()) : RotationSegment.convertToSegment($$0.getRotation() + 180.0F);
/* 116 */     return (BlockState)((BlockState)((BlockState)defaultBlockState().setValue((Property)ATTACHED, Boolean.valueOf($$7))).setValue((Property)ROTATION, Integer.valueOf($$10))).setValue((Property)WATERLOGGED, Boolean.valueOf(($$2.getType() == Fluids.WATER)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 121 */     VoxelShape $$4 = AABBS.get($$0.getValue((Property)ROTATION));
/* 122 */     return ($$4 == null) ? SHAPE : $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 127 */     return getShape($$0, $$1, $$2, CollisionContext.empty());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 132 */     if ($$1 == Direction.UP && !canSurvive($$0, (LevelReader)$$3, $$4)) {
/* 133 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/* 135 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getYRotationDegrees(BlockState $$0) {
/* 140 */     return RotationSegment.convertToDegrees(((Integer)$$0.getValue((Property)ROTATION)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 145 */     return (BlockState)$$0.setValue((Property)ROTATION, Integer.valueOf($$1.rotate(((Integer)$$0.getValue((Property)ROTATION)).intValue(), 16)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 150 */     return (BlockState)$$0.setValue((Property)ROTATION, Integer.valueOf($$1.mirror(((Integer)$$0.getValue((Property)ROTATION)).intValue(), 16)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 155 */     $$0.add(new Property[] { (Property)ROTATION, (Property)ATTACHED, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 160 */     return (BlockEntity)new HangingSignBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 166 */     return createTickerHelper($$2, BlockEntityType.HANGING_SIGN, SignBlockEntity::tick);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CeilingHangingSignBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */