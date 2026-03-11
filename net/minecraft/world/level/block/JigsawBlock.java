/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.FrontAndTop;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.JigsawBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ 
/*     */ public class JigsawBlock extends Block implements EntityBlock, GameMasterBlock {
/*  22 */   public static final MapCodec<JigsawBlock> CODEC = simpleCodec(JigsawBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<JigsawBlock> codec() {
/*  26 */     return CODEC;
/*     */   }
/*     */   
/*  29 */   public static final EnumProperty<FrontAndTop> ORIENTATION = BlockStateProperties.ORIENTATION;
/*     */   
/*     */   protected JigsawBlock(BlockBehaviour.Properties $$0) {
/*  32 */     super($$0);
/*  33 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)ORIENTATION, (Comparable)FrontAndTop.NORTH_UP));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  38 */     $$0.add(new Property[] { (Property)ORIENTATION });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  43 */     return (BlockState)$$0.setValue((Property)ORIENTATION, (Comparable)$$1.rotation().rotate((FrontAndTop)$$0.getValue((Property)ORIENTATION)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/*  48 */     return (BlockState)$$0.setValue((Property)ORIENTATION, (Comparable)$$1.rotation().rotate((FrontAndTop)$$0.getValue((Property)ORIENTATION)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  53 */     Direction $$3, $$1 = $$0.getClickedFace();
/*     */     
/*  55 */     if ($$1.getAxis() == Direction.Axis.Y) {
/*  56 */       Direction $$2 = $$0.getHorizontalDirection().getOpposite();
/*     */     } else {
/*  58 */       $$3 = Direction.UP;
/*     */     } 
/*     */     
/*  61 */     return (BlockState)defaultBlockState().setValue((Property)ORIENTATION, (Comparable)FrontAndTop.fromFrontAndTop($$1, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/*  66 */     return (BlockEntity)new JigsawBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  71 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/*  72 */     if ($$6 instanceof JigsawBlockEntity && $$3.canUseGameMasterBlocks()) {
/*  73 */       $$3.openJigsawBlock((JigsawBlockEntity)$$6);
/*  74 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */ 
/*     */     
/*  78 */     return InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   public static boolean canAttach(StructureTemplate.StructureBlockInfo $$0, StructureTemplate.StructureBlockInfo $$1) {
/*  82 */     Direction $$2 = getFrontFacing($$0.state());
/*  83 */     Direction $$3 = getFrontFacing($$1.state());
/*  84 */     Direction $$4 = getTopFacing($$0.state());
/*  85 */     Direction $$5 = getTopFacing($$1.state());
/*     */ 
/*     */ 
/*     */     
/*  89 */     JigsawBlockEntity.JointType $$6 = JigsawBlockEntity.JointType.byName($$0.nbt().getString("joint")).orElseGet(() -> $$0.getAxis().isHorizontal() ? JigsawBlockEntity.JointType.ALIGNED : JigsawBlockEntity.JointType.ROLLABLE);
/*  90 */     boolean $$7 = ($$6 == JigsawBlockEntity.JointType.ROLLABLE);
/*     */     
/*  92 */     return ($$2 == $$3.getOpposite() && ($$7 || $$4 == $$5) && $$0
/*     */       
/*  94 */       .nbt().getString("target").equals($$1.nbt().getString("name")));
/*     */   }
/*     */   
/*     */   public static Direction getFrontFacing(BlockState $$0) {
/*  98 */     return ((FrontAndTop)$$0.getValue((Property)ORIENTATION)).front();
/*     */   }
/*     */   
/*     */   public static Direction getTopFacing(BlockState $$0) {
/* 102 */     return ((FrontAndTop)$$0.getValue((Property)ORIENTATION)).top();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\JigsawBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */