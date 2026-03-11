/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*     */ import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RailShape;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ public class DetectorRailBlock extends BaseRailBlock {
/*  31 */   public static final MapCodec<DetectorRailBlock> CODEC = simpleCodec(DetectorRailBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<DetectorRailBlock> codec() {
/*  35 */     return CODEC;
/*     */   }
/*     */   
/*  38 */   public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
/*  39 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*     */   private static final int PRESSED_CHECK_PERIOD = 20;
/*     */   
/*     */   public DetectorRailBlock(BlockBehaviour.Properties $$0) {
/*  43 */     super(true, $$0);
/*  44 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)SHAPE, (Comparable)RailShape.NORTH_SOUTH)).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/*  54 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/*  58 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  62 */     checkPressed($$1, $$2, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  67 */     if (!((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  71 */     checkPressed((Level)$$1, $$2, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  76 */     return ((Boolean)$$0.getValue((Property)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  81 */     if (!((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*  82 */       return 0;
/*     */     }
/*  84 */     return ($$3 == Direction.UP) ? 15 : 0;
/*     */   }
/*     */   
/*     */   private void checkPressed(Level $$0, BlockPos $$1, BlockState $$2) {
/*  88 */     if (!canSurvive($$2, (LevelReader)$$0, $$1)) {
/*     */       return;
/*     */     }
/*     */     
/*  92 */     boolean $$3 = ((Boolean)$$2.getValue((Property)POWERED)).booleanValue();
/*  93 */     boolean $$4 = false;
/*     */     
/*  95 */     List<AbstractMinecart> $$5 = getInteractingMinecartOfType($$0, $$1, AbstractMinecart.class, $$0 -> true);
/*  96 */     if (!$$5.isEmpty()) {
/*  97 */       $$4 = true;
/*     */     }
/*     */     
/* 100 */     if ($$4 && !$$3) {
/* 101 */       BlockState $$6 = (BlockState)$$2.setValue((Property)POWERED, Boolean.valueOf(true));
/* 102 */       $$0.setBlock($$1, $$6, 3);
/* 103 */       updatePowerToConnected($$0, $$1, $$6, true);
/* 104 */       $$0.updateNeighborsAt($$1, this);
/* 105 */       $$0.updateNeighborsAt($$1.below(), this);
/* 106 */       $$0.setBlocksDirty($$1, $$2, $$6);
/*     */     } 
/*     */     
/* 109 */     if (!$$4 && $$3) {
/* 110 */       BlockState $$7 = (BlockState)$$2.setValue((Property)POWERED, Boolean.valueOf(false));
/* 111 */       $$0.setBlock($$1, $$7, 3);
/* 112 */       updatePowerToConnected($$0, $$1, $$7, false);
/* 113 */       $$0.updateNeighborsAt($$1, this);
/* 114 */       $$0.updateNeighborsAt($$1.below(), this);
/* 115 */       $$0.setBlocksDirty($$1, $$2, $$7);
/*     */     } 
/*     */     
/* 118 */     if ($$4) {
/* 119 */       $$0.scheduleTick($$1, this, 20);
/*     */     }
/*     */     
/* 122 */     $$0.updateNeighbourForOutputSignal($$1, this);
/*     */   }
/*     */   
/*     */   protected void updatePowerToConnected(Level $$0, BlockPos $$1, BlockState $$2, boolean $$3) {
/* 126 */     RailState $$4 = new RailState($$0, $$1, $$2);
/* 127 */     List<BlockPos> $$5 = $$4.getConnections();
/*     */     
/* 129 */     for (BlockPos $$6 : $$5) {
/* 130 */       BlockState $$7 = $$0.getBlockState($$6);
/* 131 */       $$0.neighborChanged($$7, $$6, $$7.getBlock(), $$1, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 137 */     if ($$3.is($$0.getBlock())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     BlockState $$5 = updateState($$0, $$1, $$2, $$4);
/*     */ 
/*     */     
/* 149 */     checkPressed($$1, $$2, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public Property<RailShape> getShapeProperty() {
/* 154 */     return (Property<RailShape>)SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 164 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 165 */       List<MinecartCommandBlock> $$3 = getInteractingMinecartOfType($$1, $$2, MinecartCommandBlock.class, $$0 -> true);
/* 166 */       if (!$$3.isEmpty()) {
/* 167 */         return ((MinecartCommandBlock)$$3.get(0)).getCommandBlock().getSuccessCount();
/*     */       }
/*     */       
/* 170 */       List<AbstractMinecart> $$4 = getInteractingMinecartOfType($$1, $$2, AbstractMinecart.class, EntitySelector.CONTAINER_ENTITY_SELECTOR);
/* 171 */       if (!$$4.isEmpty()) {
/* 172 */         return AbstractContainerMenu.getRedstoneSignalFromContainer((Container)$$4.get(0));
/*     */       }
/*     */     } 
/*     */     
/* 176 */     return 0;
/*     */   }
/*     */   
/*     */   private <T extends AbstractMinecart> List<T> getInteractingMinecartOfType(Level $$0, BlockPos $$1, Class<T> $$2, Predicate<Entity> $$3) {
/* 180 */     return $$0.getEntitiesOfClass($$2, getSearchBB($$1), $$3);
/*     */   }
/*     */   
/*     */   private AABB getSearchBB(BlockPos $$0) {
/* 184 */     double $$1 = 0.2D;
/*     */     
/* 186 */     return new AABB($$0.getX() + 0.2D, $$0.getY(), $$0.getZ() + 0.2D, ($$0.getX() + 1) - 0.2D, ($$0.getY() + 1) - 0.2D, ($$0.getZ() + 1) - 0.2D);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 191 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 193 */         switch ((RailShape)$$0.getValue((Property)SHAPE)) {
/*     */           case LEFT_RIGHT:
/* 195 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_WEST);
/*     */           case FRONT_BACK:
/* 197 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_EAST);
/*     */           case null:
/* 199 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_SOUTH);
/*     */           case null:
/* 201 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_NORTH);
/*     */           case null:
/* 203 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */           case null:
/* 205 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 207 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */           case null:
/* 209 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */         } 
/*     */       case FRONT_BACK:
/* 212 */         switch ((RailShape)$$0.getValue((Property)SHAPE)) {
/*     */           case null:
/* 214 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.EAST_WEST);
/*     */           case null:
/* 216 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_SOUTH);
/*     */           case LEFT_RIGHT:
/* 218 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_NORTH);
/*     */           case FRONT_BACK:
/* 220 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_SOUTH);
/*     */           case null:
/* 222 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_WEST);
/*     */           case null:
/* 224 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_EAST);
/*     */           case null:
/* 226 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 228 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */           case null:
/* 230 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */           case null:
/* 232 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */         } 
/*     */       case null:
/* 235 */         switch ((RailShape)$$0.getValue((Property)SHAPE)) {
/*     */           case null:
/* 237 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.EAST_WEST);
/*     */           case null:
/* 239 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_SOUTH);
/*     */           case LEFT_RIGHT:
/* 241 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_SOUTH);
/*     */           case FRONT_BACK:
/* 243 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_NORTH);
/*     */           case null:
/* 245 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_EAST);
/*     */           case null:
/* 247 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_WEST);
/*     */           case null:
/* 249 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */           case null:
/* 251 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */           case null:
/* 253 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 255 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */         }  break;
/*     */     } 
/* 258 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 264 */     RailShape $$2 = (RailShape)$$0.getValue((Property)SHAPE);
/* 265 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 267 */         switch ($$2) {
/*     */           case null:
/* 269 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_SOUTH);
/*     */           case null:
/* 271 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_NORTH);
/*     */           case null:
/* 273 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 275 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */           case null:
/* 277 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */           case null:
/* 279 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */         } 
/*     */         
/*     */         break;
/*     */       
/*     */       case FRONT_BACK:
/* 285 */         switch ($$2) {
/*     */           case LEFT_RIGHT:
/* 287 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_WEST);
/*     */           case FRONT_BACK:
/* 289 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_EAST);
/*     */           case null:
/* 291 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */           case null:
/* 293 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */           case null:
/* 295 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 297 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */         } 
/*     */ 
/*     */         
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 305 */     return super.mirror($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 310 */     $$0.add(new Property[] { (Property)SHAPE, (Property)POWERED, (Property)WATERLOGGED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DetectorRailBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */