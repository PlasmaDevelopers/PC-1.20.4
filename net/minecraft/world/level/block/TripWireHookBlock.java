/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class TripWireHookBlock extends Block {
/*  32 */   public static final MapCodec<TripWireHookBlock> CODEC = simpleCodec(TripWireHookBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<TripWireHookBlock> codec() {
/*  36 */     return CODEC;
/*     */   }
/*     */   
/*  39 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  40 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*  41 */   public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
/*     */   
/*     */   protected static final int WIRE_DIST_MIN = 1;
/*     */   
/*     */   protected static final int WIRE_DIST_MAX = 42;
/*     */   private static final int RECHECK_PERIOD = 10;
/*     */   protected static final int AABB_OFFSET = 3;
/*  48 */   protected static final VoxelShape NORTH_AABB = Block.box(5.0D, 0.0D, 10.0D, 11.0D, 10.0D, 16.0D);
/*  49 */   protected static final VoxelShape SOUTH_AABB = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 10.0D, 6.0D);
/*  50 */   protected static final VoxelShape WEST_AABB = Block.box(10.0D, 0.0D, 5.0D, 16.0D, 10.0D, 11.0D);
/*  51 */   protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 5.0D, 6.0D, 10.0D, 11.0D);
/*     */   
/*     */   public TripWireHookBlock(BlockBehaviour.Properties $$0) {
/*  54 */     super($$0);
/*  55 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)ATTACHED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  60 */     switch ((Direction)$$0.getValue((Property)FACING))
/*     */     
/*     */     { default:
/*  63 */         return EAST_AABB;
/*     */       case WEST:
/*  65 */         return WEST_AABB;
/*     */       case SOUTH:
/*  67 */         return SOUTH_AABB;
/*     */       case NORTH:
/*  69 */         break; }  return NORTH_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  75 */     Direction $$3 = (Direction)$$0.getValue((Property)FACING);
/*  76 */     BlockPos $$4 = $$2.relative($$3.getOpposite());
/*  77 */     BlockState $$5 = $$1.getBlockState($$4);
/*  78 */     return ($$3.getAxis().isHorizontal() && $$5.isFaceSturdy((BlockGetter)$$1, $$4, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  83 */     if ($$1.getOpposite() == $$0.getValue((Property)FACING) && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  84 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*  86 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  92 */     BlockState $$1 = (BlockState)((BlockState)defaultBlockState().setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)ATTACHED, Boolean.valueOf(false));
/*     */     
/*  94 */     Level level = $$0.getLevel();
/*  95 */     BlockPos $$3 = $$0.getClickedPos();
/*     */     
/*  97 */     Direction[] $$4 = $$0.getNearestLookingDirections();
/*  98 */     for (Direction $$5 : $$4) {
/*  99 */       if ($$5.getAxis().isHorizontal()) {
/*     */ 
/*     */ 
/*     */         
/* 103 */         Direction $$6 = $$5.getOpposite();
/*     */         
/* 105 */         $$1 = (BlockState)$$1.setValue((Property)FACING, (Comparable)$$6);
/* 106 */         if ($$1.canSurvive((LevelReader)level, $$3)) {
/* 107 */           return $$1;
/*     */         }
/*     */       } 
/*     */     } 
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 116 */     calculateState($$0, $$1, $$2, false, false, -1, (BlockState)null);
/*     */   }
/*     */   public static void calculateState(Level $$0, BlockPos $$1, BlockState $$2, boolean $$3, boolean $$4, int $$5, @Nullable BlockState $$6) {
/*     */     int j;
/* 120 */     Optional<Direction> $$7 = $$2.getOptionalValue((Property)FACING);
/* 121 */     if (!$$7.isPresent()) {
/*     */       return;
/*     */     }
/*     */     
/* 125 */     Direction $$8 = $$7.get();
/* 126 */     int $$9 = ((Boolean)$$2.getOptionalValue((Property)ATTACHED).orElse(Boolean.valueOf(false))).booleanValue();
/* 127 */     boolean $$10 = ((Boolean)$$2.getOptionalValue((Property)POWERED).orElse(Boolean.valueOf(false))).booleanValue();
/*     */     
/* 129 */     Block $$11 = $$2.getBlock();
/* 130 */     boolean $$12 = !$$3;
/* 131 */     boolean $$13 = false;
/* 132 */     int $$14 = 0;
/*     */     
/* 134 */     BlockState[] $$15 = new BlockState[42];
/* 135 */     for (int $$16 = 1; $$16 < 42; $$16++) {
/* 136 */       BlockPos $$17 = $$1.relative($$8, $$16);
/* 137 */       BlockState $$18 = $$0.getBlockState($$17);
/*     */       
/* 139 */       if ($$18.is(Blocks.TRIPWIRE_HOOK)) {
/* 140 */         if ($$18.getValue((Property)FACING) == $$8.getOpposite()) {
/* 141 */           $$14 = $$16;
/*     */         }
/*     */         break;
/*     */       } 
/* 145 */       if ($$18.is(Blocks.TRIPWIRE) || $$16 == $$5) {
/* 146 */         if ($$16 == $$5) {
/* 147 */           $$18 = (BlockState)MoreObjects.firstNonNull($$6, $$18);
/*     */         }
/* 149 */         boolean $$19 = !((Boolean)$$18.getValue((Property)TripWireBlock.DISARMED)).booleanValue();
/* 150 */         boolean $$20 = ((Boolean)$$18.getValue((Property)TripWireBlock.POWERED)).booleanValue();
/* 151 */         j = $$13 | (($$19 && $$20) ? 1 : 0);
/*     */         
/* 153 */         $$15[$$16] = $$18;
/*     */         
/* 155 */         if ($$16 == $$5) {
/* 156 */           $$0.scheduleTick($$1, $$11, 10);
/* 157 */           $$12 &= $$19;
/*     */         } 
/*     */       } else {
/* 160 */         $$15[$$16] = null;
/* 161 */         $$12 = false;
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     int i = $$12 & (($$14 > 1) ? 1 : 0);
/* 166 */     j &= i;
/* 167 */     BlockState $$21 = (BlockState)((BlockState)$$11.defaultBlockState().trySetValue((Property)ATTACHED, Boolean.valueOf(i))).trySetValue((Property)POWERED, Boolean.valueOf(j));
/*     */     
/* 169 */     if ($$14 > 0) {
/* 170 */       BlockPos $$22 = $$1.relative($$8, $$14);
/* 171 */       Direction $$23 = $$8.getOpposite();
/* 172 */       $$0.setBlock($$22, (BlockState)$$21.setValue((Property)FACING, (Comparable)$$23), 3);
/* 173 */       notifyNeighbors($$11, $$0, $$22, $$23);
/*     */       
/* 175 */       emitState($$0, $$22, i, j, $$9, $$10);
/*     */     } 
/*     */     
/* 178 */     emitState($$0, $$1, i, j, $$9, $$10);
/*     */     
/* 180 */     if (!$$3) {
/* 181 */       $$0.setBlock($$1, (BlockState)$$21.setValue((Property)FACING, (Comparable)$$8), 3);
/* 182 */       if ($$4) {
/* 183 */         notifyNeighbors($$11, $$0, $$1, $$8);
/*     */       }
/*     */     } 
/*     */     
/* 187 */     if ($$9 != i) {
/* 188 */       for (int $$24 = 1; $$24 < $$14; $$24++) {
/* 189 */         BlockPos $$25 = $$1.relative($$8, $$24);
/* 190 */         BlockState $$26 = $$15[$$24];
/* 191 */         if ($$26 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 196 */           $$0.setBlock($$25, (BlockState)$$26.trySetValue((Property)ATTACHED, Boolean.valueOf(i)), 3);
/* 197 */           if (!$$0.getBlockState($$25).isAir());
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 205 */     calculateState((Level)$$1, $$2, $$0, false, true, -1, (BlockState)null);
/*     */   }
/*     */   
/*     */   private static void emitState(Level $$0, BlockPos $$1, boolean $$2, boolean $$3, boolean $$4, boolean $$5) {
/* 209 */     if ($$3 && !$$5) {
/* 210 */       $$0.playSound(null, $$1, SoundEvents.TRIPWIRE_CLICK_ON, SoundSource.BLOCKS, 0.4F, 0.6F);
/* 211 */       $$0.gameEvent(null, GameEvent.BLOCK_ACTIVATE, $$1);
/* 212 */     } else if (!$$3 && $$5) {
/* 213 */       $$0.playSound(null, $$1, SoundEvents.TRIPWIRE_CLICK_OFF, SoundSource.BLOCKS, 0.4F, 0.5F);
/* 214 */       $$0.gameEvent(null, GameEvent.BLOCK_DEACTIVATE, $$1);
/* 215 */     } else if ($$2 && !$$4) {
/* 216 */       $$0.playSound(null, $$1, SoundEvents.TRIPWIRE_ATTACH, SoundSource.BLOCKS, 0.4F, 0.7F);
/* 217 */       $$0.gameEvent(null, GameEvent.BLOCK_ATTACH, $$1);
/* 218 */     } else if (!$$2 && $$4) {
/* 219 */       $$0.playSound(null, $$1, SoundEvents.TRIPWIRE_DETACH, SoundSource.BLOCKS, 0.4F, 1.2F / ($$0.random.nextFloat() * 0.2F + 0.9F));
/* 220 */       $$0.gameEvent(null, GameEvent.BLOCK_DETACH, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void notifyNeighbors(Block $$0, Level $$1, BlockPos $$2, Direction $$3) {
/* 225 */     $$1.updateNeighborsAt($$2, $$0);
/* 226 */     $$1.updateNeighborsAt($$2.relative($$3.getOpposite()), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 231 */     if ($$4 || $$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/* 234 */     boolean $$5 = ((Boolean)$$0.getValue((Property)ATTACHED)).booleanValue();
/* 235 */     boolean $$6 = ((Boolean)$$0.getValue((Property)POWERED)).booleanValue();
/*     */     
/* 237 */     if ($$5 || $$6) {
/* 238 */       calculateState($$1, $$2, $$0, true, false, -1, (BlockState)null);
/*     */     }
/*     */     
/* 241 */     if ($$6) {
/* 242 */       $$1.updateNeighborsAt($$2, this);
/* 243 */       $$1.updateNeighborsAt($$2.relative(((Direction)$$0.getValue((Property)FACING)).getOpposite()), this);
/*     */     } 
/*     */     
/* 246 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 251 */     return ((Boolean)$$0.getValue((Property)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 256 */     if (!((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 257 */       return 0;
/*     */     }
/*     */     
/* 260 */     if ($$0.getValue((Property)FACING) == $$3) {
/* 261 */       return 15;
/*     */     }
/*     */     
/* 264 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 269 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 274 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 279 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 284 */     $$0.add(new Property[] { (Property)FACING, (Property)POWERED, (Property)ATTACHED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TripWireHookBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */