/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class TripWireBlock extends Block {
/*     */   static {
/*  29 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("hook").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, TripWireBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<TripWireBlock> CODEC;
/*     */   
/*     */   public MapCodec<TripWireBlock> codec() {
/*  36 */     return CODEC;
/*     */   }
/*     */   
/*  39 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*  40 */   public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
/*  41 */   public static final BooleanProperty DISARMED = BlockStateProperties.DISARMED;
/*  42 */   public static final BooleanProperty NORTH = PipeBlock.NORTH;
/*  43 */   public static final BooleanProperty EAST = PipeBlock.EAST;
/*  44 */   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
/*  45 */   public static final BooleanProperty WEST = PipeBlock.WEST;
/*     */   
/*  47 */   private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = CrossCollisionBlock.PROPERTY_BY_DIRECTION;
/*     */   
/*  49 */   protected static final VoxelShape AABB = Block.box(0.0D, 1.0D, 0.0D, 16.0D, 2.5D, 16.0D);
/*  50 */   protected static final VoxelShape NOT_ATTACHED_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*     */   
/*     */   private static final int RECHECK_PERIOD = 10;
/*     */   private final Block hook;
/*     */   
/*     */   public TripWireBlock(Block $$0, BlockBehaviour.Properties $$1) {
/*  56 */     super($$1);
/*  57 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)ATTACHED, Boolean.valueOf(false))).setValue((Property)DISARMED, Boolean.valueOf(false))).setValue((Property)NORTH, Boolean.valueOf(false))).setValue((Property)EAST, Boolean.valueOf(false))).setValue((Property)SOUTH, Boolean.valueOf(false))).setValue((Property)WEST, Boolean.valueOf(false)));
/*  58 */     this.hook = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  63 */     return ((Boolean)$$0.getValue((Property)ATTACHED)).booleanValue() ? AABB : NOT_ATTACHED_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  68 */     Level level = $$0.getLevel();
/*  69 */     BlockPos $$2 = $$0.getClickedPos();
/*     */     
/*  71 */     return (BlockState)((BlockState)((BlockState)((BlockState)defaultBlockState()
/*  72 */       .setValue((Property)NORTH, Boolean.valueOf(shouldConnectTo(level.getBlockState($$2.north()), Direction.NORTH))))
/*  73 */       .setValue((Property)EAST, Boolean.valueOf(shouldConnectTo(level.getBlockState($$2.east()), Direction.EAST))))
/*  74 */       .setValue((Property)SOUTH, Boolean.valueOf(shouldConnectTo(level.getBlockState($$2.south()), Direction.SOUTH))))
/*  75 */       .setValue((Property)WEST, Boolean.valueOf(shouldConnectTo(level.getBlockState($$2.west()), Direction.WEST)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  80 */     if ($$1.getAxis().isHorizontal()) {
/*  81 */       return (BlockState)$$0.setValue((Property)PROPERTY_BY_DIRECTION.get($$1), Boolean.valueOf(shouldConnectTo($$2, $$1)));
/*     */     }
/*  83 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  88 */     if ($$3.is($$0.getBlock())) {
/*     */       return;
/*     */     }
/*  91 */     updateSource($$1, $$2, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  96 */     if ($$4 || $$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*  99 */     updateSource($$1, $$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 104 */     if (!$$0.isClientSide && !$$3.getMainHandItem().isEmpty() && $$3.getMainHandItem().is(Items.SHEARS)) {
/* 105 */       $$0.setBlock($$1, (BlockState)$$2.setValue((Property)DISARMED, Boolean.valueOf(true)), 4);
/* 106 */       $$0.gameEvent((Entity)$$3, GameEvent.SHEAR, $$1);
/*     */     } 
/* 108 */     return super.playerWillDestroy($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private void updateSource(Level $$0, BlockPos $$1, BlockState $$2) {
/* 112 */     for (Direction $$3 : new Direction[] { Direction.SOUTH, Direction.WEST }) {
/* 113 */       for (int $$4 = 1; $$4 < 42; $$4++) {
/* 114 */         BlockPos $$5 = $$1.relative($$3, $$4);
/* 115 */         BlockState $$6 = $$0.getBlockState($$5);
/*     */         
/* 117 */         if ($$6.is(this.hook)) {
/* 118 */           if ($$6.getValue((Property)TripWireHookBlock.FACING) == $$3.getOpposite()) {
/* 119 */             TripWireHookBlock.calculateState($$0, $$5, $$6, false, true, $$4, $$2);
/*     */           }
/*     */           break;
/*     */         } 
/* 123 */         if (!$$6.is(this)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 132 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 136 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 140 */     checkPressed($$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 145 */     if (!((Boolean)$$1.getBlockState($$2).getValue((Property)POWERED)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 149 */     checkPressed((Level)$$1, $$2);
/*     */   }
/*     */   
/*     */   private void checkPressed(Level $$0, BlockPos $$1) {
/* 153 */     BlockState $$2 = $$0.getBlockState($$1);
/* 154 */     boolean $$3 = ((Boolean)$$2.getValue((Property)POWERED)).booleanValue();
/* 155 */     boolean $$4 = false;
/*     */     
/* 157 */     List<? extends Entity> $$5 = $$0.getEntities(null, $$2.getShape((BlockGetter)$$0, $$1).bounds().move($$1));
/* 158 */     if (!$$5.isEmpty()) {
/* 159 */       for (Entity $$6 : $$5) {
/* 160 */         if (!$$6.isIgnoringBlockTriggers()) {
/* 161 */           $$4 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 167 */     if ($$4 != $$3) {
/* 168 */       $$2 = (BlockState)$$2.setValue((Property)POWERED, Boolean.valueOf($$4));
/* 169 */       $$0.setBlock($$1, $$2, 3);
/* 170 */       updateSource($$0, $$1, $$2);
/*     */     } 
/*     */     
/* 173 */     if ($$4) {
/* 174 */       $$0.scheduleTick(new BlockPos((Vec3i)$$1), this, 10);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean shouldConnectTo(BlockState $$0, Direction $$1) {
/* 179 */     if ($$0.is(this.hook)) {
/* 180 */       return ($$0.getValue((Property)TripWireHookBlock.FACING) == $$1.getOpposite());
/*     */     }
/*     */     
/* 183 */     return $$0.is(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 188 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 190 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)SOUTH))).setValue((Property)EAST, $$0.getValue((Property)WEST))).setValue((Property)SOUTH, $$0.getValue((Property)NORTH))).setValue((Property)WEST, $$0.getValue((Property)EAST));
/*     */       case FRONT_BACK:
/* 192 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)EAST))).setValue((Property)EAST, $$0.getValue((Property)SOUTH))).setValue((Property)SOUTH, $$0.getValue((Property)WEST))).setValue((Property)WEST, $$0.getValue((Property)NORTH));
/*     */       case null:
/* 194 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)WEST))).setValue((Property)EAST, $$0.getValue((Property)NORTH))).setValue((Property)SOUTH, $$0.getValue((Property)EAST))).setValue((Property)WEST, $$0.getValue((Property)SOUTH));
/*     */     } 
/* 196 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 202 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 204 */         return (BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)SOUTH))).setValue((Property)SOUTH, $$0.getValue((Property)NORTH));
/*     */       case FRONT_BACK:
/* 206 */         return (BlockState)((BlockState)$$0.setValue((Property)EAST, $$0.getValue((Property)WEST))).setValue((Property)WEST, $$0.getValue((Property)EAST));
/*     */     } 
/*     */ 
/*     */     
/* 210 */     return super.mirror($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 215 */     $$0.add(new Property[] { (Property)POWERED, (Property)ATTACHED, (Property)DISARMED, (Property)NORTH, (Property)EAST, (Property)WEST, (Property)SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TripWireBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */