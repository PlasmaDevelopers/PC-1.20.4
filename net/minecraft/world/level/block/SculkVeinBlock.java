/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
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
/*     */ 
/*     */ public class SculkVeinBlock extends MultifaceBlock implements SculkBehaviour, SimpleWaterloggedBlock {
/*  26 */   public static final MapCodec<SculkVeinBlock> CODEC = simpleCodec(SculkVeinBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<SculkVeinBlock> codec() {
/*  30 */     return CODEC;
/*     */   }
/*     */   
/*  33 */   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*  35 */   private final MultifaceSpreader veinSpreader = new MultifaceSpreader(new SculkVeinSpreaderConfig(MultifaceSpreader.DEFAULT_SPREAD_ORDER));
/*     */ 
/*     */   
/*  38 */   private final MultifaceSpreader sameSpaceSpreader = new MultifaceSpreader(new SculkVeinSpreaderConfig(new MultifaceSpreader.SpreadType[] { MultifaceSpreader.SpreadType.SAME_POSITION }));
/*     */   
/*     */   public SculkVeinBlock(BlockBehaviour.Properties $$0) {
/*  41 */     super($$0);
/*  42 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public MultifaceSpreader getSpreader() {
/*  47 */     return this.veinSpreader;
/*     */   }
/*     */   
/*     */   public MultifaceSpreader getSameSpaceSpreader() {
/*  51 */     return this.sameSpaceSpreader;
/*     */   }
/*     */   
/*     */   public static boolean regrow(LevelAccessor $$0, BlockPos $$1, BlockState $$2, Collection<Direction> $$3) {
/*  55 */     boolean $$4 = false;
/*  56 */     BlockState $$5 = Blocks.SCULK_VEIN.defaultBlockState();
/*     */     
/*  58 */     for (Direction $$6 : $$3) {
/*  59 */       BlockPos $$7 = $$1.relative($$6);
/*  60 */       if (canAttachTo((BlockGetter)$$0, $$6, $$7, $$0.getBlockState($$7))) {
/*  61 */         $$5 = (BlockState)$$5.setValue((Property)getFaceProperty($$6), Boolean.valueOf(true));
/*  62 */         $$4 = true;
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     if (!$$4) {
/*  67 */       return false;
/*     */     }
/*     */     
/*  70 */     if (!$$2.getFluidState().isEmpty()) {
/*  71 */       $$5 = (BlockState)$$5.setValue((Property)WATERLOGGED, Boolean.valueOf(true));
/*     */     }
/*  73 */     $$0.setBlock($$1, $$5, 3);
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDischarged(LevelAccessor $$0, BlockState $$1, BlockPos $$2, RandomSource $$3) {
/*  79 */     if (!$$1.is(this)) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     for (Direction $$4 : DIRECTIONS) {
/*  84 */       BooleanProperty $$5 = getFaceProperty($$4);
/*  85 */       if (((Boolean)$$1.getValue((Property)$$5)).booleanValue() && $$0.getBlockState($$2.relative($$4)).is(Blocks.SCULK)) {
/*  86 */         $$1 = (BlockState)$$1.setValue((Property)$$5, Boolean.valueOf(false));
/*     */       }
/*     */     } 
/*  89 */     if (!hasAnyFace($$1)) {
/*  90 */       FluidState $$6 = $$0.getFluidState($$2);
/*  91 */       $$1 = ($$6.isEmpty() ? Blocks.AIR : Blocks.WATER).defaultBlockState();
/*     */     } 
/*  93 */     $$0.setBlock($$2, $$1, 3);
/*  94 */     super.onDischarged($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int attemptUseCharge(SculkSpreader.ChargeCursor $$0, LevelAccessor $$1, BlockPos $$2, RandomSource $$3, SculkSpreader $$4, boolean $$5) {
/*  99 */     if ($$5 && attemptPlaceSculk($$4, $$1, $$0.getPos(), $$3)) {
/* 100 */       return $$0.getCharge() - 1;
/*     */     }
/*     */     
/* 103 */     return ($$3.nextInt($$4.chargeDecayRate()) == 0) ? Mth.floor($$0.getCharge() * 0.5F) : $$0.getCharge();
/*     */   }
/*     */   
/*     */   private boolean attemptPlaceSculk(SculkSpreader $$0, LevelAccessor $$1, BlockPos $$2, RandomSource $$3) {
/* 107 */     BlockState $$4 = $$1.getBlockState($$2);
/* 108 */     TagKey<Block> $$5 = $$0.replaceableBlocks();
/* 109 */     for (Direction $$6 : Direction.allShuffled($$3)) {
/* 110 */       if (!hasFace($$4, $$6)) {
/*     */         continue;
/*     */       }
/*     */       
/* 114 */       BlockPos $$7 = $$2.relative($$6);
/* 115 */       BlockState $$8 = $$1.getBlockState($$7);
/* 116 */       if (!$$8.is($$5)) {
/*     */         continue;
/*     */       }
/*     */       
/* 120 */       BlockState $$9 = Blocks.SCULK.defaultBlockState();
/* 121 */       $$1.setBlock($$7, $$9, 3);
/* 122 */       Block.pushEntitiesUp($$8, $$9, $$1, $$7);
/* 123 */       $$1.playSound(null, $$7, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */ 
/*     */       
/* 126 */       this.veinSpreader.spreadAll($$9, $$1, $$7, $$0.isWorldGeneration());
/*     */ 
/*     */       
/* 129 */       Direction $$10 = $$6.getOpposite();
/* 130 */       for (Direction $$11 : DIRECTIONS) {
/* 131 */         if ($$11 != $$10) {
/*     */ 
/*     */ 
/*     */           
/* 135 */           BlockPos $$12 = $$7.relative($$11);
/* 136 */           BlockState $$13 = $$1.getBlockState($$12);
/*     */           
/* 138 */           if ($$13.is(this))
/* 139 */             onDischarged($$1, $$13, $$12, $$3); 
/*     */         } 
/*     */       } 
/* 142 */       return true;
/*     */     } 
/* 144 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean hasSubstrateAccess(LevelAccessor $$0, BlockState $$1, BlockPos $$2) {
/* 148 */     if (!$$1.is(Blocks.SCULK_VEIN)) {
/* 149 */       return false;
/*     */     }
/*     */     
/* 152 */     for (Direction $$3 : DIRECTIONS) {
/* 153 */       if (hasFace($$1, $$3) && $$0.getBlockState($$2.relative($$3)).is(BlockTags.SCULK_REPLACEABLE)) {
/* 154 */         return true;
/*     */       }
/*     */     } 
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 162 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 163 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 165 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 170 */     super.createBlockStateDefinition($$0);
/* 171 */     $$0.add(new Property[] { (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/* 176 */     return (!$$1.getItemInHand().is(Items.SCULK_VEIN) || super.canBeReplaced($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 181 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 182 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 184 */     return super.getFluidState($$0);
/*     */   }
/*     */   
/*     */   private class SculkVeinSpreaderConfig extends MultifaceSpreader.DefaultSpreaderConfig {
/*     */     private final MultifaceSpreader.SpreadType[] spreadTypes;
/*     */     
/*     */     public SculkVeinSpreaderConfig(MultifaceSpreader.SpreadType... $$0) {
/* 191 */       super(SculkVeinBlock.this);
/* 192 */       this.spreadTypes = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean stateCanBeReplaced(BlockGetter $$0, BlockPos $$1, BlockPos $$2, Direction $$3, BlockState $$4) {
/* 197 */       BlockState $$5 = $$0.getBlockState($$2.relative($$3));
/*     */ 
/*     */ 
/*     */       
/* 201 */       if ($$5.is(Blocks.SCULK) || $$5.is(Blocks.SCULK_CATALYST) || $$5.is(Blocks.MOVING_PISTON)) {
/* 202 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 207 */       if ($$1.distManhattan((Vec3i)$$2) == 2) {
/* 208 */         BlockPos $$6 = $$1.relative($$3.getOpposite());
/* 209 */         if ($$0.getBlockState($$6).isFaceSturdy($$0, $$6, $$3)) {
/* 210 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 214 */       FluidState $$7 = $$4.getFluidState();
/* 215 */       if (!$$7.isEmpty() && !$$7.is((Fluid)Fluids.WATER)) {
/* 216 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 220 */       if ($$4.is(BlockTags.FIRE)) {
/* 221 */         return false;
/*     */       }
/*     */       
/* 224 */       return ($$4.canBeReplaced() || super.stateCanBeReplaced($$0, $$1, $$2, $$3, $$4));
/*     */     }
/*     */ 
/*     */     
/*     */     public MultifaceSpreader.SpreadType[] getSpreadTypes() {
/* 229 */       return this.spreadTypes;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isOtherBlockValidAsSource(BlockState $$0) {
/* 234 */       return !$$0.is(Blocks.SCULK_VEIN);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SculkVeinBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */