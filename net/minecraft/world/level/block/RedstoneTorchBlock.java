/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.DustParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class RedstoneTorchBlock extends BaseTorchBlock {
/*  23 */   public static final MapCodec<RedstoneTorchBlock> CODEC = simpleCodec(RedstoneTorchBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<? extends RedstoneTorchBlock> codec() {
/*  27 */     return CODEC;
/*     */   }
/*     */   
/*  30 */   public static final BooleanProperty LIT = BlockStateProperties.LIT;
/*     */ 
/*     */   
/*  33 */   private static final Map<BlockGetter, List<Toggle>> RECENT_TOGGLES = new WeakHashMap<>();
/*     */   
/*     */   public static final int RECENT_TOGGLE_TIMER = 60;
/*     */   public static final int MAX_RECENT_TOGGLES = 8;
/*     */   public static final int RESTART_DELAY = 160;
/*     */   private static final int TOGGLE_DELAY = 2;
/*     */   
/*     */   protected RedstoneTorchBlock(BlockBehaviour.Properties $$0) {
/*  41 */     super($$0);
/*  42 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)LIT, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  47 */     for (Direction $$5 : Direction.values()) {
/*  48 */       $$1.updateNeighborsAt($$2.relative($$5), this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  54 */     if ($$4) {
/*     */       return;
/*     */     }
/*     */     
/*  58 */     for (Direction $$5 : Direction.values()) {
/*  59 */       $$1.updateNeighborsAt($$2.relative($$5), this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  65 */     if (((Boolean)$$0.getValue((Property)LIT)).booleanValue() && Direction.UP != $$3) {
/*  66 */       return 15;
/*     */     }
/*     */     
/*  69 */     return 0;
/*     */   }
/*     */   
/*     */   protected boolean hasNeighborSignal(Level $$0, BlockPos $$1, BlockState $$2) {
/*  73 */     return $$0.hasSignal($$1.below(), Direction.DOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  78 */     boolean $$4 = hasNeighborSignal((Level)$$1, $$2, $$0);
/*     */     
/*  80 */     List<Toggle> $$5 = RECENT_TOGGLES.get($$1);
/*  81 */     while ($$5 != null && !$$5.isEmpty() && $$1.getGameTime() - ((Toggle)$$5.get(0)).when > 60L) {
/*  82 */       $$5.remove(0);
/*     */     }
/*     */     
/*  85 */     if (((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*  86 */       if ($$4) {
/*  87 */         $$1.setBlock($$2, (BlockState)$$0.setValue((Property)LIT, Boolean.valueOf(false)), 3);
/*     */         
/*  89 */         if (isToggledTooFrequently((Level)$$1, $$2, true)) {
/*  90 */           $$1.levelEvent(1502, $$2, 0);
/*  91 */           $$1.scheduleTick($$2, $$1.getBlockState($$2).getBlock(), 160);
/*     */         }
/*     */       
/*     */       } 
/*  95 */     } else if (!$$4 && !isToggledTooFrequently((Level)$$1, $$2, false)) {
/*  96 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)LIT, Boolean.valueOf(true)), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 103 */     if (((Boolean)$$0.getValue((Property)LIT)).booleanValue() == hasNeighborSignal($$1, $$2, $$0) && !$$1.getBlockTicks().willTickThisTick($$2, this)) {
/* 104 */       $$1.scheduleTick($$2, this, 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 110 */     if ($$3 == Direction.DOWN) {
/* 111 */       return $$0.getSignal($$1, $$2, $$3);
/*     */     }
/* 113 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 123 */     if (!((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 127 */     double $$4 = $$2.getX() + 0.5D + ($$3.nextDouble() - 0.5D) * 0.2D;
/* 128 */     double $$5 = $$2.getY() + 0.7D + ($$3.nextDouble() - 0.5D) * 0.2D;
/* 129 */     double $$6 = $$2.getZ() + 0.5D + ($$3.nextDouble() - 0.5D) * 0.2D;
/*     */     
/* 131 */     $$1.addParticle((ParticleOptions)DustParticleOptions.REDSTONE, $$4, $$5, $$6, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 136 */     $$0.add(new Property[] { (Property)LIT });
/*     */   }
/*     */   
/*     */   public static class Toggle {
/*     */     final BlockPos pos;
/*     */     final long when;
/*     */     
/*     */     public Toggle(BlockPos $$0, long $$1) {
/* 144 */       this.pos = $$0;
/* 145 */       this.when = $$1;
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isToggledTooFrequently(Level $$0, BlockPos $$1, boolean $$2) {
/* 150 */     List<Toggle> $$3 = RECENT_TOGGLES.computeIfAbsent($$0, $$0 -> Lists.newArrayList());
/*     */     
/* 152 */     if ($$2) {
/* 153 */       $$3.add(new Toggle($$1.immutable(), $$0.getGameTime()));
/*     */     }
/*     */     
/* 156 */     int $$4 = 0;
/* 157 */     for (Toggle $$5 : $$3) {
/*     */       
/* 159 */       $$4++;
/* 160 */       if ($$5.pos.equals($$1) && $$4 >= 8) {
/* 161 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 165 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RedstoneTorchBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */