/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.decoration.ItemFrame;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.SignalGetter;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.ComparatorMode;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.ticks.TickPriority;
/*     */ 
/*     */ public class ComparatorBlock extends DiodeBlock implements EntityBlock {
/*  33 */   public static final MapCodec<ComparatorBlock> CODEC = simpleCodec(ComparatorBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<ComparatorBlock> codec() {
/*  37 */     return CODEC;
/*     */   }
/*     */   
/*  40 */   public static final EnumProperty<ComparatorMode> MODE = BlockStateProperties.MODE_COMPARATOR;
/*     */   
/*     */   public ComparatorBlock(BlockBehaviour.Properties $$0) {
/*  43 */     super($$0);
/*  44 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)MODE, (Comparable)ComparatorMode.COMPARE));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getDelay(BlockState $$0) {
/*  49 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  54 */     if ($$1 == Direction.DOWN && !canSurviveOn((LevelReader)$$3, $$5, $$2)) {
/*  55 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*  57 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getOutputSignal(BlockGetter $$0, BlockPos $$1, BlockState $$2) {
/*  62 */     BlockEntity $$3 = $$0.getBlockEntity($$1);
/*  63 */     if ($$3 instanceof ComparatorBlockEntity) {
/*  64 */       return ((ComparatorBlockEntity)$$3).getOutputSignal();
/*     */     }
/*     */     
/*  67 */     return 0;
/*     */   }
/*     */   
/*     */   private int calculateOutputSignal(Level $$0, BlockPos $$1, BlockState $$2) {
/*  71 */     int $$3 = getInputSignal($$0, $$1, $$2);
/*  72 */     if ($$3 == 0) {
/*  73 */       return 0;
/*     */     }
/*     */     
/*  76 */     int $$4 = getAlternateSignal((SignalGetter)$$0, $$1, $$2);
/*  77 */     if ($$4 > $$3) {
/*  78 */       return 0;
/*     */     }
/*     */     
/*  81 */     if ($$2.getValue((Property)MODE) == ComparatorMode.SUBTRACT) {
/*  82 */       return $$3 - $$4;
/*     */     }
/*     */     
/*  85 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldTurnOn(Level $$0, BlockPos $$1, BlockState $$2) {
/*  90 */     int $$3 = getInputSignal($$0, $$1, $$2);
/*  91 */     if ($$3 == 0) {
/*  92 */       return false;
/*     */     }
/*     */     
/*  95 */     int $$4 = getAlternateSignal((SignalGetter)$$0, $$1, $$2);
/*  96 */     if ($$3 > $$4) {
/*  97 */       return true;
/*     */     }
/*     */     
/* 100 */     return ($$3 == $$4 && $$2.getValue((Property)MODE) == ComparatorMode.COMPARE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getInputSignal(Level $$0, BlockPos $$1, BlockState $$2) {
/* 105 */     int $$3 = super.getInputSignal($$0, $$1, $$2);
/*     */     
/* 107 */     Direction $$4 = (Direction)$$2.getValue((Property)FACING);
/* 108 */     BlockPos $$5 = $$1.relative($$4);
/* 109 */     BlockState $$6 = $$0.getBlockState($$5);
/*     */     
/* 111 */     if ($$6.hasAnalogOutputSignal()) {
/* 112 */       $$3 = $$6.getAnalogOutputSignal($$0, $$5);
/* 113 */     } else if ($$3 < 15 && $$6.isRedstoneConductor((BlockGetter)$$0, $$5)) {
/* 114 */       $$5 = $$5.relative($$4);
/* 115 */       $$6 = $$0.getBlockState($$5);
/* 116 */       ItemFrame $$7 = getItemFrame($$0, $$4, $$5);
/*     */       
/* 118 */       int $$8 = Math.max(
/* 119 */           ($$7 == null) ? Integer.MIN_VALUE : $$7.getAnalogOutput(), 
/* 120 */           $$6.hasAnalogOutputSignal() ? $$6.getAnalogOutputSignal($$0, $$5) : Integer.MIN_VALUE);
/*     */ 
/*     */       
/* 123 */       if ($$8 != Integer.MIN_VALUE) {
/* 124 */         $$3 = $$8;
/*     */       }
/*     */     } 
/*     */     
/* 128 */     return $$3;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ItemFrame getItemFrame(Level $$0, Direction $$1, BlockPos $$2) {
/* 133 */     List<ItemFrame> $$3 = $$0.getEntitiesOfClass(ItemFrame.class, new AABB($$2.getX(), $$2.getY(), $$2.getZ(), ($$2.getX() + 1), ($$2.getY() + 1), ($$2.getZ() + 1)), $$1 -> ($$1 != null && $$1.getDirection() == $$0));
/*     */     
/* 135 */     if ($$3.size() == 1) {
/* 136 */       return $$3.get(0);
/*     */     }
/*     */     
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 144 */     if (!($$3.getAbilities()).mayBuild) {
/* 145 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 148 */     $$0 = (BlockState)$$0.cycle((Property)MODE);
/* 149 */     float $$6 = ($$0.getValue((Property)MODE) == ComparatorMode.SUBTRACT) ? 0.55F : 0.5F;
/* 150 */     $$1.playSound($$3, $$2, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, $$6);
/*     */     
/* 152 */     $$1.setBlock($$2, $$0, 2);
/* 153 */     refreshOutputState($$1, $$2, $$0);
/* 154 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkTickOnNeighbor(Level $$0, BlockPos $$1, BlockState $$2) {
/* 159 */     if ($$0.getBlockTicks().willTickThisTick($$1, this)) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     int $$3 = calculateOutputSignal($$0, $$1, $$2);
/* 164 */     BlockEntity $$4 = $$0.getBlockEntity($$1);
/* 165 */     int $$5 = ($$4 instanceof ComparatorBlockEntity) ? ((ComparatorBlockEntity)$$4).getOutputSignal() : 0;
/*     */     
/* 167 */     if ($$3 != $$5 || ((Boolean)$$2.getValue((Property)POWERED)).booleanValue() != shouldTurnOn($$0, $$1, $$2)) {
/*     */       
/* 169 */       TickPriority $$6 = shouldPrioritize((BlockGetter)$$0, $$1, $$2) ? TickPriority.HIGH : TickPriority.NORMAL;
/* 170 */       $$0.scheduleTick($$1, this, 2, $$6);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void refreshOutputState(Level $$0, BlockPos $$1, BlockState $$2) {
/* 175 */     int $$3 = calculateOutputSignal($$0, $$1, $$2);
/*     */     
/* 177 */     BlockEntity $$4 = $$0.getBlockEntity($$1);
/* 178 */     int $$5 = 0;
/* 179 */     if ($$4 instanceof ComparatorBlockEntity) { ComparatorBlockEntity $$6 = (ComparatorBlockEntity)$$4;
/* 180 */       $$5 = $$6.getOutputSignal();
/* 181 */       $$6.setOutputSignal($$3); }
/*     */ 
/*     */     
/* 184 */     if ($$5 != $$3 || $$2.getValue((Property)MODE) == ComparatorMode.COMPARE) {
/* 185 */       boolean $$7 = shouldTurnOn($$0, $$1, $$2);
/* 186 */       boolean $$8 = ((Boolean)$$2.getValue((Property)POWERED)).booleanValue();
/*     */       
/* 188 */       if ($$8 && !$$7) {
/* 189 */         $$0.setBlock($$1, (BlockState)$$2.setValue((Property)POWERED, Boolean.valueOf(false)), 2);
/* 190 */       } else if (!$$8 && $$7) {
/* 191 */         $$0.setBlock($$1, (BlockState)$$2.setValue((Property)POWERED, Boolean.valueOf(true)), 2);
/*     */       } 
/*     */       
/* 194 */       updateNeighborsInFront($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 200 */     refreshOutputState((Level)$$1, $$2, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean triggerEvent(BlockState $$0, Level $$1, BlockPos $$2, int $$3, int $$4) {
/* 205 */     super.triggerEvent($$0, $$1, $$2, $$3, $$4);
/*     */     
/* 207 */     BlockEntity $$5 = $$1.getBlockEntity($$2);
/* 208 */     return ($$5 != null && $$5.triggerEvent($$3, $$4));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 213 */     return (BlockEntity)new ComparatorBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 218 */     $$0.add(new Property[] { (Property)FACING, (Property)MODE, (Property)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ComparatorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */