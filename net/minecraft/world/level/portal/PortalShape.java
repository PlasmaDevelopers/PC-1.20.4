/*     */ package net.minecraft.world.level.portal;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.BlockUtil;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.NetherPortalBlock;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class PortalShape {
/*     */   private static final int MIN_WIDTH = 2;
/*     */   public static final int MAX_WIDTH = 21;
/*     */   private static final int MIN_HEIGHT = 3;
/*     */   
/*     */   static {
/*  33 */     FRAME = (($$0, $$1, $$2) -> $$0.is(Blocks.OBSIDIAN));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int MAX_HEIGHT = 21;
/*     */   
/*     */   private static final BlockBehaviour.StatePredicate FRAME;
/*     */   
/*     */   private static final float SAFE_TRAVEL_MAX_ENTITY_XY = 4.0F;
/*     */   
/*     */   private static final double SAFE_TRAVEL_MAX_VERTICAL_DELTA = 1.0D;
/*     */   private final LevelAccessor level;
/*     */   
/*     */   public static Optional<PortalShape> findEmptyPortalShape(LevelAccessor $$0, BlockPos $$1, Direction.Axis $$2) {
/*  47 */     return findPortalShape($$0, $$1, $$0 -> ($$0.isValid() && $$0.numPortalBlocks == 0), $$2);
/*     */   } private final Direction.Axis axis; private final Direction rightDir; private int numPortalBlocks; @Nullable
/*     */   private BlockPos bottomLeft; private int height; private final int width;
/*     */   public static Optional<PortalShape> findPortalShape(LevelAccessor $$0, BlockPos $$1, Predicate<PortalShape> $$2, Direction.Axis $$3) {
/*  51 */     Optional<PortalShape> $$4 = Optional.<PortalShape>of(new PortalShape($$0, $$1, $$3)).filter($$2);
/*  52 */     if ($$4.isPresent()) {
/*  53 */       return $$4;
/*     */     }
/*     */     
/*  56 */     Direction.Axis $$5 = ($$3 == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X;
/*  57 */     return Optional.<PortalShape>of(new PortalShape($$0, $$1, $$5)).filter($$2);
/*     */   }
/*     */   
/*     */   public PortalShape(LevelAccessor $$0, BlockPos $$1, Direction.Axis $$2) {
/*  61 */     this.level = $$0;
/*  62 */     this.axis = $$2;
/*  63 */     this.rightDir = ($$2 == Direction.Axis.X) ? Direction.WEST : Direction.SOUTH;
/*     */     
/*  65 */     this.bottomLeft = calculateBottomLeft($$1);
/*  66 */     if (this.bottomLeft == null) {
/*  67 */       this.bottomLeft = $$1;
/*  68 */       this.width = 1;
/*  69 */       this.height = 1;
/*     */     } else {
/*  71 */       this.width = calculateWidth();
/*     */       
/*  73 */       if (this.width > 0) {
/*  74 */         this.height = calculateHeight();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private BlockPos calculateBottomLeft(BlockPos $$0) {
/*  82 */     int $$1 = Math.max(this.level.getMinBuildHeight(), $$0.getY() - 21);
/*  83 */     while ($$0.getY() > $$1 && isEmpty(this.level.getBlockState($$0.below()))) {
/*  84 */       $$0 = $$0.below();
/*     */     }
/*     */     
/*  87 */     Direction $$2 = this.rightDir.getOpposite();
/*  88 */     int $$3 = getDistanceUntilEdgeAboveFrame($$0, $$2) - 1;
/*  89 */     if ($$3 < 0) {
/*  90 */       return null;
/*     */     }
/*  92 */     return $$0.relative($$2, $$3);
/*     */   }
/*     */   
/*     */   private int calculateWidth() {
/*  96 */     int $$0 = getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
/*     */     
/*  98 */     if ($$0 < 2 || $$0 > 21) {
/*  99 */       return 0;
/*     */     }
/*     */     
/* 102 */     return $$0;
/*     */   }
/*     */   
/*     */   private int getDistanceUntilEdgeAboveFrame(BlockPos $$0, Direction $$1) {
/* 106 */     BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
/*     */     
/* 108 */     for (int $$3 = 0; $$3 <= 21; $$3++) {
/* 109 */       $$2.set((Vec3i)$$0).move($$1, $$3);
/*     */       
/* 111 */       BlockState $$4 = this.level.getBlockState((BlockPos)$$2);
/* 112 */       if (!isEmpty($$4)) {
/* 113 */         if (FRAME.test($$4, (BlockGetter)this.level, (BlockPos)$$2)) {
/* 114 */           return $$3;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 120 */       BlockState $$5 = this.level.getBlockState((BlockPos)$$2.move(Direction.DOWN));
/* 121 */       if (!FRAME.test($$5, (BlockGetter)this.level, (BlockPos)$$2)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 126 */     return 0;
/*     */   }
/*     */   
/*     */   private int calculateHeight() {
/* 130 */     BlockPos.MutableBlockPos $$0 = new BlockPos.MutableBlockPos();
/* 131 */     int $$1 = getDistanceUntilTop($$0);
/*     */     
/* 133 */     if ($$1 < 3 || $$1 > 21 || !hasTopFrame($$0, $$1)) {
/* 134 */       return 0;
/*     */     }
/*     */     
/* 137 */     return $$1;
/*     */   }
/*     */   
/*     */   private boolean hasTopFrame(BlockPos.MutableBlockPos $$0, int $$1) {
/* 141 */     for (int $$2 = 0; $$2 < this.width; $$2++) {
/* 142 */       BlockPos.MutableBlockPos $$3 = $$0.set((Vec3i)this.bottomLeft).move(Direction.UP, $$1).move(this.rightDir, $$2);
/* 143 */       if (!FRAME.test(this.level.getBlockState((BlockPos)$$3), (BlockGetter)this.level, (BlockPos)$$3)) {
/* 144 */         return false;
/*     */       }
/*     */     } 
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   private int getDistanceUntilTop(BlockPos.MutableBlockPos $$0) {
/* 151 */     for (int $$1 = 0; $$1 < 21; $$1++) {
/*     */       
/* 153 */       $$0.set((Vec3i)this.bottomLeft).move(Direction.UP, $$1).move(this.rightDir, -1);
/* 154 */       if (!FRAME.test(this.level.getBlockState((BlockPos)$$0), (BlockGetter)this.level, (BlockPos)$$0)) {
/* 155 */         return $$1;
/*     */       }
/*     */ 
/*     */       
/* 159 */       $$0.set((Vec3i)this.bottomLeft).move(Direction.UP, $$1).move(this.rightDir, this.width);
/* 160 */       if (!FRAME.test(this.level.getBlockState((BlockPos)$$0), (BlockGetter)this.level, (BlockPos)$$0)) {
/* 161 */         return $$1;
/*     */       }
/*     */ 
/*     */       
/* 165 */       for (int $$2 = 0; $$2 < this.width; $$2++) {
/* 166 */         $$0.set((Vec3i)this.bottomLeft).move(Direction.UP, $$1).move(this.rightDir, $$2);
/*     */         
/* 168 */         BlockState $$3 = this.level.getBlockState((BlockPos)$$0);
/* 169 */         if (!isEmpty($$3)) {
/* 170 */           return $$1;
/*     */         }
/*     */         
/* 173 */         if ($$3.is(Blocks.NETHER_PORTAL)) {
/* 174 */           this.numPortalBlocks++;
/*     */         }
/*     */       } 
/*     */     } 
/* 178 */     return 21;
/*     */   }
/*     */   
/*     */   private static boolean isEmpty(BlockState $$0) {
/* 182 */     return ($$0.isAir() || $$0.is(BlockTags.FIRE) || $$0.is(Blocks.NETHER_PORTAL));
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 186 */     return (this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21);
/*     */   }
/*     */   
/*     */   public void createPortalBlocks() {
/* 190 */     BlockState $$0 = (BlockState)Blocks.NETHER_PORTAL.defaultBlockState().setValue((Property)NetherPortalBlock.AXIS, (Comparable)this.axis);
/*     */     
/* 192 */     BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach($$1 -> this.level.setBlock($$1, $$0, 18));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/* 198 */     return (isValid() && this.numPortalBlocks == this.width * this.height);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3 getRelativePosition(BlockUtil.FoundRectangle $$0, Direction.Axis $$1, Vec3 $$2, EntityDimensions $$3) {
/* 203 */     double $$9, $$12, $$4 = $$0.axis1Size - $$3.width;
/* 204 */     double $$5 = $$0.axis2Size - $$3.height;
/*     */     
/* 206 */     BlockPos $$6 = $$0.minCorner;
/*     */     
/* 208 */     if ($$4 > 0.0D) {
/* 209 */       double $$7 = $$6.get($$1) + $$3.width / 2.0D;
/* 210 */       double $$8 = Mth.clamp(Mth.inverseLerp($$2.get($$1) - $$7, 0.0D, $$4), 0.0D, 1.0D);
/*     */     } else {
/* 212 */       $$9 = 0.5D;
/*     */     } 
/*     */     
/* 215 */     if ($$5 > 0.0D) {
/* 216 */       Direction.Axis $$10 = Direction.Axis.Y;
/* 217 */       double $$11 = Mth.clamp(Mth.inverseLerp($$2.get($$10) - $$6.get($$10), 0.0D, $$5), 0.0D, 1.0D);
/*     */     } else {
/* 219 */       $$12 = 0.0D;
/*     */     } 
/*     */     
/* 222 */     Direction.Axis $$13 = ($$1 == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X;
/* 223 */     double $$14 = $$2.get($$13) - $$6.get($$13) + 0.5D;
/*     */     
/* 225 */     return new Vec3($$9, $$12, $$14);
/*     */   }
/*     */   
/*     */   public static PortalInfo createPortalInfo(ServerLevel $$0, BlockUtil.FoundRectangle $$1, Direction.Axis $$2, Vec3 $$3, Entity $$4, Vec3 $$5, float $$6, float $$7) {
/* 229 */     BlockPos $$8 = $$1.minCorner;
/* 230 */     BlockState $$9 = $$0.getBlockState($$8);
/* 231 */     Direction.Axis $$10 = $$9.getOptionalValue((Property)BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
/* 232 */     double $$11 = $$1.axis1Size;
/* 233 */     double $$12 = $$1.axis2Size;
/* 234 */     EntityDimensions $$13 = $$4.getDimensions($$4.getPose());
/*     */     
/* 236 */     int $$14 = ($$2 == $$10) ? 0 : 90;
/* 237 */     Vec3 $$15 = ($$2 == $$10) ? $$5 : new Vec3($$5.z, $$5.y, -$$5.x);
/*     */     
/* 239 */     double $$16 = $$13.width / 2.0D + ($$11 - $$13.width) * $$3.x();
/* 240 */     double $$17 = ($$12 - $$13.height) * $$3.y();
/* 241 */     double $$18 = 0.5D + $$3.z();
/*     */     
/* 243 */     boolean $$19 = ($$10 == Direction.Axis.X);
/*     */ 
/*     */ 
/*     */     
/* 247 */     Vec3 $$20 = new Vec3($$8.getX() + ($$19 ? $$16 : $$18), $$8.getY() + $$17, $$8.getZ() + ($$19 ? $$18 : $$16));
/*     */ 
/*     */     
/* 250 */     Vec3 $$21 = findCollisionFreePosition($$20, $$0, $$4, $$13);
/* 251 */     return new PortalInfo($$21, $$15, $$6 + $$14, $$7);
/*     */   }
/*     */   
/*     */   private static Vec3 findCollisionFreePosition(Vec3 $$0, ServerLevel $$1, Entity $$2, EntityDimensions $$3) {
/* 255 */     if ($$3.width > 4.0F || $$3.height > 4.0F) {
/* 256 */       return $$0;
/*     */     }
/*     */     
/* 259 */     double $$4 = $$3.height / 2.0D;
/* 260 */     Vec3 $$5 = $$0.add(0.0D, $$4, 0.0D);
/*     */     
/* 262 */     VoxelShape $$6 = Shapes.create(AABB.ofSize($$5, $$3.width, 0.0D, $$3.width).expandTowards(0.0D, 1.0D, 0.0D).inflate(1.0E-6D));
/* 263 */     Optional<Vec3> $$7 = $$1.findFreePosition($$2, $$6, $$5, $$3.width, $$3.height, $$3.width);
/* 264 */     Optional<Vec3> $$8 = $$7.map($$1 -> $$1.subtract(0.0D, $$0, 0.0D));
/*     */     
/* 266 */     return $$8.orElse($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\portal\PortalShape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */