/*     */ package net.minecraft.world.level.block.piston;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.PistonType;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class PistonMovingBlockEntity extends BlockEntity {
/*     */   private static final int TICKS_TO_EXTEND = 2;
/*     */   private static final double PUSH_OFFSET = 0.01D;
/*     */   public static final double TICK_MOVEMENT = 0.51D;
/*  35 */   private BlockState movedState = Blocks.AIR.defaultBlockState();
/*     */   
/*     */   private Direction direction;
/*     */   
/*     */   private boolean extending;
/*     */   private boolean isSourcePiston;
/*  41 */   private static final ThreadLocal<Direction> NOCLIP = ThreadLocal.withInitial(() -> null);
/*     */   
/*     */   private float progress;
/*     */   
/*     */   private float progressO;
/*     */   private long lastTicked;
/*     */   private int deathTicks;
/*     */   
/*     */   public PistonMovingBlockEntity(BlockPos $$0, BlockState $$1) {
/*  50 */     super(BlockEntityType.PISTON, $$0, $$1);
/*     */   }
/*     */   
/*     */   public PistonMovingBlockEntity(BlockPos $$0, BlockState $$1, BlockState $$2, Direction $$3, boolean $$4, boolean $$5) {
/*  54 */     this($$0, $$1);
/*  55 */     this.movedState = $$2;
/*  56 */     this.direction = $$3;
/*  57 */     this.extending = $$4;
/*  58 */     this.isSourcePiston = $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/*  63 */     return saveWithoutMetadata();
/*     */   }
/*     */   
/*     */   public boolean isExtending() {
/*  67 */     return this.extending;
/*     */   }
/*     */   
/*     */   public Direction getDirection() {
/*  71 */     return this.direction;
/*     */   }
/*     */   
/*     */   public boolean isSourcePiston() {
/*  75 */     return this.isSourcePiston;
/*     */   }
/*     */   
/*     */   public float getProgress(float $$0) {
/*  79 */     if ($$0 > 1.0F) {
/*  80 */       $$0 = 1.0F;
/*     */     }
/*  82 */     return Mth.lerp($$0, this.progressO, this.progress);
/*     */   }
/*     */   
/*     */   public float getXOff(float $$0) {
/*  86 */     return this.direction.getStepX() * getExtendedProgress(getProgress($$0));
/*     */   }
/*     */   
/*     */   public float getYOff(float $$0) {
/*  90 */     return this.direction.getStepY() * getExtendedProgress(getProgress($$0));
/*     */   }
/*     */   
/*     */   public float getZOff(float $$0) {
/*  94 */     return this.direction.getStepZ() * getExtendedProgress(getProgress($$0));
/*     */   }
/*     */   
/*     */   private float getExtendedProgress(float $$0) {
/*  98 */     return this.extending ? ($$0 - 1.0F) : (1.0F - $$0);
/*     */   }
/*     */   
/*     */   private BlockState getCollisionRelatedBlockState() {
/* 102 */     if (!isExtending() && isSourcePiston() && this.movedState.getBlock() instanceof PistonBaseBlock) {
/* 103 */       return (BlockState)((BlockState)((BlockState)Blocks.PISTON_HEAD.defaultBlockState()
/* 104 */         .setValue((Property)PistonHeadBlock.SHORT, Boolean.valueOf((this.progress > 0.25F))))
/* 105 */         .setValue((Property)PistonHeadBlock.TYPE, this.movedState.is(Blocks.STICKY_PISTON) ? (Comparable)PistonType.STICKY : (Comparable)PistonType.DEFAULT))
/* 106 */         .setValue((Property)PistonHeadBlock.FACING, this.movedState.getValue((Property)PistonBaseBlock.FACING));
/*     */     }
/* 108 */     return this.movedState;
/*     */   }
/*     */   
/*     */   private static void moveCollidedEntities(Level $$0, BlockPos $$1, float $$2, PistonMovingBlockEntity $$3) {
/* 112 */     Direction $$4 = $$3.getMovementDirection();
/*     */     
/* 114 */     double $$5 = ($$2 - $$3.progress);
/*     */     
/* 116 */     VoxelShape $$6 = $$3.getCollisionRelatedBlockState().getCollisionShape((BlockGetter)$$0, $$1);
/* 117 */     if ($$6.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 121 */     AABB $$7 = moveByPositionAndProgress($$1, $$6.bounds(), $$3);
/* 122 */     List<Entity> $$8 = $$0.getEntities(null, PistonMath.getMovementArea($$7, $$4, $$5).minmax($$7));
/* 123 */     if ($$8.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 127 */     List<AABB> $$9 = $$6.toAabbs();
/* 128 */     boolean $$10 = $$3.movedState.is(Blocks.SLIME_BLOCK);
/* 129 */     for (Entity $$11 : $$8) {
/* 130 */       if ($$11.getPistonPushReaction() == PushReaction.IGNORE) {
/*     */         continue;
/*     */       }
/*     */       
/* 134 */       if ($$10) {
/* 135 */         if ($$11 instanceof net.minecraft.server.level.ServerPlayer) {
/*     */           continue;
/*     */         }
/*     */         
/* 139 */         Vec3 $$12 = $$11.getDeltaMovement();
/* 140 */         double $$13 = $$12.x;
/* 141 */         double $$14 = $$12.y;
/* 142 */         double $$15 = $$12.z;
/* 143 */         switch ($$4.getAxis()) {
/*     */           case EAST:
/* 145 */             $$13 = $$4.getStepX();
/*     */             break;
/*     */           case WEST:
/* 148 */             $$14 = $$4.getStepY();
/*     */             break;
/*     */           case UP:
/* 151 */             $$15 = $$4.getStepZ();
/*     */             break;
/*     */         } 
/*     */         
/* 155 */         $$11.setDeltaMovement($$13, $$14, $$15);
/*     */       } 
/*     */       
/* 158 */       double $$16 = 0.0D;
/* 159 */       for (AABB $$17 : $$9) {
/*     */ 
/*     */         
/* 162 */         AABB $$18 = PistonMath.getMovementArea(moveByPositionAndProgress($$1, $$17, $$3), $$4, $$5);
/*     */         
/* 164 */         AABB $$19 = $$11.getBoundingBox();
/* 165 */         if (!$$18.intersects($$19)) {
/*     */           continue;
/*     */         }
/*     */         
/* 169 */         $$16 = Math.max($$16, getMovement($$18, $$4, $$19));
/*     */ 
/*     */         
/* 172 */         if ($$16 >= $$5) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 177 */       if ($$16 <= 0.0D) {
/*     */         continue;
/*     */       }
/*     */       
/* 181 */       $$16 = Math.min($$16, $$5) + 0.01D;
/* 182 */       moveEntityByPiston($$4, $$11, $$16, $$4);
/*     */       
/* 184 */       if (!$$3.extending && $$3.isSourcePiston) {
/* 185 */         fixEntityWithinPistonBase($$1, $$11, $$4, $$5);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void moveEntityByPiston(Direction $$0, Entity $$1, double $$2, Direction $$3) {
/* 192 */     NOCLIP.set($$0);
/* 193 */     $$1.move(MoverType.PISTON, new Vec3($$2 * $$3.getStepX(), $$2 * $$3.getStepY(), $$2 * $$3.getStepZ()));
/* 194 */     NOCLIP.set(null);
/*     */   }
/*     */   
/*     */   private static void moveStuckEntities(Level $$0, BlockPos $$1, float $$2, PistonMovingBlockEntity $$3) {
/* 198 */     if (!$$3.isStickyForEntities()) {
/*     */       return;
/*     */     }
/*     */     
/* 202 */     Direction $$4 = $$3.getMovementDirection();
/* 203 */     if (!$$4.getAxis().isHorizontal()) {
/*     */       return;
/*     */     }
/*     */     
/* 207 */     double $$5 = $$3.movedState.getCollisionShape((BlockGetter)$$0, $$1).max(Direction.Axis.Y);
/* 208 */     AABB $$6 = moveByPositionAndProgress($$1, new AABB(0.0D, $$5, 0.0D, 1.0D, 1.5000010000000001D, 1.0D), $$3);
/*     */     
/* 210 */     double $$7 = ($$2 - $$3.progress);
/*     */     
/* 212 */     List<Entity> $$8 = $$0.getEntities((Entity)null, $$6, $$2 -> matchesStickyCritera($$0, $$2, $$1));
/* 213 */     for (Entity $$9 : $$8) {
/* 214 */       moveEntityByPiston($$4, $$9, $$7, $$4);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean matchesStickyCritera(AABB $$0, Entity $$1, BlockPos $$2) {
/* 219 */     return ($$1.getPistonPushReaction() == PushReaction.NORMAL && $$1
/* 220 */       .onGround() && ($$1
/* 221 */       .isSupportedBy($$2) || ($$1
/* 222 */       .getX() >= $$0.minX && $$1
/* 223 */       .getX() <= $$0.maxX && $$1
/* 224 */       .getZ() >= $$0.minZ && $$1
/* 225 */       .getZ() <= $$0.maxZ)));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isStickyForEntities() {
/* 230 */     return this.movedState.is(Blocks.HONEY_BLOCK);
/*     */   }
/*     */   
/*     */   public Direction getMovementDirection() {
/* 234 */     return this.extending ? this.direction : this.direction.getOpposite();
/*     */   }
/*     */   
/*     */   private static double getMovement(AABB $$0, Direction $$1, AABB $$2) {
/* 238 */     switch ($$1)
/*     */     { case EAST:
/* 240 */         return $$0.maxX - $$2.minX;
/*     */       case WEST:
/* 242 */         return $$2.maxX - $$0.minX;
/*     */       
/*     */       default:
/* 245 */         return $$0.maxY - $$2.minY;
/*     */       case DOWN:
/* 247 */         return $$2.maxY - $$0.minY;
/*     */       case SOUTH:
/* 249 */         return $$0.maxZ - $$2.minZ;
/*     */       case NORTH:
/* 251 */         break; }  return $$2.maxZ - $$0.minZ;
/*     */   }
/*     */ 
/*     */   
/*     */   private static AABB moveByPositionAndProgress(BlockPos $$0, AABB $$1, PistonMovingBlockEntity $$2) {
/* 256 */     double $$3 = $$2.getExtendedProgress($$2.progress);
/* 257 */     return $$1.move($$0
/* 258 */         .getX() + $$3 * $$2.direction.getStepX(), $$0
/* 259 */         .getY() + $$3 * $$2.direction.getStepY(), $$0
/* 260 */         .getZ() + $$3 * $$2.direction.getStepZ());
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fixEntityWithinPistonBase(BlockPos $$0, Entity $$1, Direction $$2, double $$3) {
/* 265 */     AABB $$4 = $$1.getBoundingBox();
/* 266 */     AABB $$5 = Shapes.block().bounds().move($$0);
/* 267 */     if ($$4.intersects($$5)) {
/* 268 */       Direction $$6 = $$2.getOpposite();
/*     */ 
/*     */       
/* 271 */       double $$7 = getMovement($$5, $$6, $$4) + 0.01D;
/* 272 */       double $$8 = getMovement($$5, $$6, $$4.intersect($$5)) + 0.01D;
/*     */       
/* 274 */       if (Math.abs($$7 - $$8) < 0.01D) {
/* 275 */         $$7 = Math.min($$7, $$3) + 0.01D;
/* 276 */         moveEntityByPiston($$2, $$1, $$7, $$6);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public BlockState getMovedState() {
/* 282 */     return this.movedState;
/*     */   }
/*     */   
/*     */   public void finalTick() {
/* 286 */     if (this.level != null && (this.progressO < 1.0F || this.level.isClientSide)) {
/* 287 */       this.progress = 1.0F;
/* 288 */       this.progressO = this.progress;
/* 289 */       this.level.removeBlockEntity(this.worldPosition);
/* 290 */       setRemoved();
/* 291 */       if (this.level.getBlockState(this.worldPosition).is(Blocks.MOVING_PISTON)) {
/*     */         BlockState $$1;
/* 293 */         if (this.isSourcePiston) {
/* 294 */           BlockState $$0 = Blocks.AIR.defaultBlockState();
/*     */         } else {
/* 296 */           $$1 = Block.updateFromNeighbourShapes(this.movedState, (LevelAccessor)this.level, this.worldPosition);
/*     */         } 
/* 298 */         this.level.setBlock(this.worldPosition, $$1, 3);
/* 299 */         this.level.neighborChanged(this.worldPosition, $$1.getBlock(), this.worldPosition);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void tick(Level $$0, BlockPos $$1, BlockState $$2, PistonMovingBlockEntity $$3) {
/* 305 */     $$3.lastTicked = $$0.getGameTime();
/* 306 */     $$3.progressO = $$3.progress;
/*     */     
/* 308 */     if ($$3.progressO >= 1.0F) {
/* 309 */       if ($$0.isClientSide && $$3.deathTicks < 5) {
/* 310 */         $$3.deathTicks++;
/*     */         return;
/*     */       } 
/* 313 */       $$0.removeBlockEntity($$1);
/* 314 */       $$3.setRemoved();
/* 315 */       if ($$0.getBlockState($$1).is(Blocks.MOVING_PISTON)) {
/* 316 */         BlockState $$4 = Block.updateFromNeighbourShapes($$3.movedState, (LevelAccessor)$$0, $$1);
/* 317 */         if ($$4.isAir()) {
/* 318 */           $$0.setBlock($$1, $$3.movedState, 84);
/* 319 */           Block.updateOrDestroy($$3.movedState, $$4, (LevelAccessor)$$0, $$1, 3);
/*     */         } else {
/* 321 */           if ($$4.hasProperty((Property)BlockStateProperties.WATERLOGGED) && ((Boolean)$$4.getValue((Property)BlockStateProperties.WATERLOGGED)).booleanValue()) {
/* 322 */             $$4 = (BlockState)$$4.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(false));
/*     */           }
/* 324 */           $$0.setBlock($$1, $$4, 67);
/* 325 */           $$0.neighborChanged($$1, $$4.getBlock(), $$1);
/*     */         } 
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 331 */     float $$5 = $$3.progress + 0.5F;
/* 332 */     moveCollidedEntities($$0, $$1, $$5, $$3);
/* 333 */     moveStuckEntities($$0, $$1, $$5, $$3);
/* 334 */     $$3.progress = $$5;
/* 335 */     if ($$3.progress >= 1.0F) {
/* 336 */       $$3.progress = 1.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 342 */     super.load($$0);
/*     */     
/* 344 */     HolderGetter<Block> $$1 = (this.level != null) ? (HolderGetter<Block>)this.level.holderLookup(Registries.BLOCK) : (HolderGetter<Block>)BuiltInRegistries.BLOCK.asLookup();
/* 345 */     this.movedState = NbtUtils.readBlockState($$1, $$0.getCompound("blockState"));
/* 346 */     this.direction = Direction.from3DDataValue($$0.getInt("facing"));
/* 347 */     this.progress = $$0.getFloat("progress");
/* 348 */     this.progressO = this.progress;
/* 349 */     this.extending = $$0.getBoolean("extending");
/* 350 */     this.isSourcePiston = $$0.getBoolean("source");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 355 */     super.saveAdditional($$0);
/*     */     
/* 357 */     $$0.put("blockState", (Tag)NbtUtils.writeBlockState(this.movedState));
/* 358 */     $$0.putInt("facing", this.direction.get3DDataValue());
/* 359 */     $$0.putFloat("progress", this.progressO);
/* 360 */     $$0.putBoolean("extending", this.extending);
/* 361 */     $$0.putBoolean("source", this.isSourcePiston);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockGetter $$0, BlockPos $$1) {
/*     */     VoxelShape $$3;
/*     */     BlockState $$6;
/* 368 */     if (!this.extending && this.isSourcePiston && this.movedState.getBlock() instanceof PistonBaseBlock) {
/* 369 */       VoxelShape $$2 = ((BlockState)this.movedState.setValue((Property)PistonBaseBlock.EXTENDED, Boolean.valueOf(true))).getCollisionShape($$0, $$1);
/*     */     } else {
/* 371 */       $$3 = Shapes.empty();
/*     */     } 
/*     */     
/* 374 */     Direction $$4 = NOCLIP.get();
/* 375 */     if (this.progress < 1.0D && $$4 == getMovementDirection()) {
/* 376 */       return $$3;
/*     */     }
/*     */ 
/*     */     
/* 380 */     if (isSourcePiston()) {
/* 381 */       BlockState $$5 = (BlockState)((BlockState)Blocks.PISTON_HEAD.defaultBlockState().setValue((Property)PistonHeadBlock.FACING, (Comparable)this.direction)).setValue((Property)PistonHeadBlock.SHORT, Boolean.valueOf((this.extending != ((1.0F - this.progress < 0.25F)))));
/*     */     } else {
/* 383 */       $$6 = this.movedState;
/*     */     } 
/* 385 */     float $$7 = getExtendedProgress(this.progress);
/* 386 */     double $$8 = (this.direction.getStepX() * $$7);
/* 387 */     double $$9 = (this.direction.getStepY() * $$7);
/* 388 */     double $$10 = (this.direction.getStepZ() * $$7);
/* 389 */     return Shapes.or($$3, $$6.getCollisionShape($$0, $$1).move($$8, $$9, $$10));
/*     */   }
/*     */   
/*     */   public long getLastTicked() {
/* 393 */     return this.lastTicked;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(Level $$0) {
/* 398 */     super.setLevel($$0);
/*     */     
/* 400 */     if ($$0.holderLookup(Registries.BLOCK).get(this.movedState.getBlock().builtInRegistryHolder().key()).isEmpty())
/* 401 */       this.movedState = Blocks.AIR.defaultBlockState(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\piston\PistonMovingBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */