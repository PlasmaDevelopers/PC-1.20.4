/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function5;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChargeCursor
/*     */ {
/*     */   private static final ObjectArrayList<Vec3i> NON_CORNER_NEIGHBOURS;
/*     */   public static final int MAX_CURSOR_DECAY_DELAY = 1;
/*     */   private BlockPos pos;
/*     */   int charge;
/*     */   private int updateDelay;
/*     */   private int decayDelay;
/*     */   @Nullable
/*     */   private Set<Direction> facings;
/*     */   private static final Codec<Set<Direction>> DIRECTION_SET;
/*     */   public static final Codec<ChargeCursor> CODEC;
/*     */   
/*     */   static {
/* 153 */     NON_CORNER_NEIGHBOURS = (ObjectArrayList<Vec3i>)Util.make(new ObjectArrayList(18), $$0 -> {
/*     */           Objects.requireNonNull($$0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           BlockPos.betweenClosedStream(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1)).filter(()).map(BlockPos::immutable).forEach($$0::add);
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     DIRECTION_SET = Direction.CODEC.listOf().xmap($$0 -> Sets.newEnumSet($$0, Direction.class), Lists::newArrayList);
/*     */     
/* 173 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockPos.CODEC.fieldOf("pos").forGetter(ChargeCursor::getPos), (App)Codec.intRange(0, 1000).fieldOf("charge").orElse(Integer.valueOf(0)).forGetter(ChargeCursor::getCharge), (App)Codec.intRange(0, 1).fieldOf("decay_delay").orElse(Integer.valueOf(1)).forGetter(ChargeCursor::getDecayDelay), (App)Codec.intRange(0, 2147483647).fieldOf("update_delay").orElse(Integer.valueOf(0)).forGetter(()), (App)DIRECTION_SET.optionalFieldOf("facings").forGetter(())).apply((Applicative)$$0, ChargeCursor::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChargeCursor(BlockPos $$0, int $$1, int $$2, int $$3, Optional<Set<Direction>> $$4) {
/* 182 */     this.pos = $$0;
/* 183 */     this.charge = $$1;
/* 184 */     this.decayDelay = $$2;
/* 185 */     this.updateDelay = $$3;
/* 186 */     this.facings = $$4.orElse(null);
/*     */   }
/*     */   
/*     */   public ChargeCursor(BlockPos $$0, int $$1) {
/* 190 */     this($$0, $$1, 1, 0, Optional.empty());
/*     */   }
/*     */   
/*     */   public BlockPos getPos() {
/* 194 */     return this.pos;
/*     */   }
/*     */   
/*     */   public int getCharge() {
/* 198 */     return this.charge;
/*     */   }
/*     */   
/*     */   public int getDecayDelay() {
/* 202 */     return this.decayDelay;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Set<Direction> getFacingData() {
/* 207 */     return this.facings;
/*     */   }
/*     */   
/*     */   private boolean shouldUpdate(LevelAccessor $$0, BlockPos $$1, boolean $$2) {
/* 211 */     if (this.charge <= 0) {
/* 212 */       return false;
/*     */     }
/* 214 */     if ($$2) {
/* 215 */       return true;
/*     */     }
/* 217 */     if ($$0 instanceof ServerLevel) { ServerLevel $$3 = (ServerLevel)$$0;
/* 218 */       return $$3.shouldTickBlocksAt($$1); }
/*     */     
/* 220 */     return false;
/*     */   }
/*     */   
/*     */   public void update(LevelAccessor $$0, BlockPos $$1, RandomSource $$2, SculkSpreader $$3, boolean $$4) {
/* 224 */     if (!shouldUpdate($$0, $$1, $$3.isWorldGeneration)) {
/*     */       return;
/*     */     }
/*     */     
/* 228 */     if (this.updateDelay > 0) {
/* 229 */       this.updateDelay--;
/*     */       
/*     */       return;
/*     */     } 
/* 233 */     BlockState $$5 = $$0.getBlockState(this.pos);
/* 234 */     SculkBehaviour $$6 = getBlockBehaviour($$5);
/*     */ 
/*     */     
/* 237 */     if ($$4 && $$6.attemptSpreadVein($$0, this.pos, $$5, this.facings, $$3.isWorldGeneration())) {
/* 238 */       if ($$6.canChangeBlockStateOnSpread()) {
/* 239 */         $$5 = $$0.getBlockState(this.pos);
/* 240 */         $$6 = getBlockBehaviour($$5);
/*     */       } 
/* 242 */       $$0.playSound(null, this.pos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */     } 
/*     */ 
/*     */     
/* 246 */     this.charge = $$6.attemptUseCharge(this, $$0, $$1, $$2, $$3, $$4);
/*     */     
/* 248 */     if (this.charge <= 0) {
/* 249 */       $$6.onDischarged($$0, $$5, this.pos, $$2);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 254 */     BlockPos $$7 = getValidMovementPos($$0, this.pos, $$2);
/* 255 */     if ($$7 != null) {
/* 256 */       $$6.onDischarged($$0, $$5, this.pos, $$2);
/* 257 */       this.pos = $$7.immutable();
/* 258 */       if ($$3.isWorldGeneration() && !this.pos.closerThan(new Vec3i($$1.getX(), this.pos.getY(), $$1.getZ()), 15.0D)) {
/* 259 */         this.charge = 0;
/*     */         return;
/*     */       } 
/* 262 */       $$5 = $$0.getBlockState($$7);
/*     */     } 
/*     */     
/* 265 */     if ($$5.getBlock() instanceof SculkBehaviour) {
/* 266 */       this.facings = MultifaceBlock.availableFaces($$5);
/*     */     }
/* 268 */     this.decayDelay = $$6.updateDecayDelay(this.decayDelay);
/* 269 */     this.updateDelay = $$6.getSculkSpreadDelay();
/*     */   }
/*     */   
/*     */   void mergeWith(ChargeCursor $$0) {
/* 273 */     this.charge += $$0.charge;
/* 274 */     $$0.charge = 0;
/* 275 */     this.updateDelay = Math.min(this.updateDelay, $$0.updateDelay);
/*     */   }
/*     */   
/*     */   private static SculkBehaviour getBlockBehaviour(BlockState $$0) {
/* 279 */     Block block = $$0.getBlock(); SculkBehaviour $$1 = (SculkBehaviour)block; return (block instanceof SculkBehaviour) ? $$1 : SculkBehaviour.DEFAULT;
/*     */   }
/*     */   
/*     */   private static List<Vec3i> getRandomizedNonCornerNeighbourOffsets(RandomSource $$0) {
/* 283 */     return Util.shuffledCopy(NON_CORNER_NEIGHBOURS, $$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BlockPos getValidMovementPos(LevelAccessor $$0, BlockPos $$1, RandomSource $$2) {
/* 288 */     BlockPos.MutableBlockPos $$3 = $$1.mutable();
/* 289 */     BlockPos.MutableBlockPos $$4 = $$1.mutable();
/*     */     
/* 291 */     for (Vec3i $$5 : getRandomizedNonCornerNeighbourOffsets($$2)) {
/* 292 */       $$4.setWithOffset((Vec3i)$$1, $$5);
/* 293 */       BlockState $$6 = $$0.getBlockState((BlockPos)$$4);
/*     */       
/* 295 */       if ($$6.getBlock() instanceof SculkBehaviour && isMovementUnobstructed($$0, $$1, (BlockPos)$$4)) {
/* 296 */         $$3.set((Vec3i)$$4);
/*     */         
/* 298 */         if (SculkVeinBlock.hasSubstrateAccess($$0, $$6, (BlockPos)$$4)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 303 */     return $$3.equals($$1) ? null : (BlockPos)$$3;
/*     */   }
/*     */   
/*     */   private static boolean isMovementUnobstructed(LevelAccessor $$0, BlockPos $$1, BlockPos $$2) {
/* 307 */     if ($$1.distManhattan((Vec3i)$$2) == 1) {
/* 308 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 312 */     BlockPos $$3 = $$2.subtract((Vec3i)$$1);
/* 313 */     Direction $$4 = Direction.fromAxisAndDirection(Direction.Axis.X, ($$3.getX() < 0) ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
/* 314 */     Direction $$5 = Direction.fromAxisAndDirection(Direction.Axis.Y, ($$3.getY() < 0) ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
/* 315 */     Direction $$6 = Direction.fromAxisAndDirection(Direction.Axis.Z, ($$3.getZ() < 0) ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
/*     */     
/* 317 */     if ($$3.getX() == 0)
/* 318 */       return (isUnobstructed($$0, $$1, $$5) || isUnobstructed($$0, $$1, $$6)); 
/* 319 */     if ($$3.getY() == 0) {
/* 320 */       return (isUnobstructed($$0, $$1, $$4) || isUnobstructed($$0, $$1, $$6));
/*     */     }
/* 322 */     return (isUnobstructed($$0, $$1, $$4) || isUnobstructed($$0, $$1, $$5));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isUnobstructed(LevelAccessor $$0, BlockPos $$1, Direction $$2) {
/* 327 */     BlockPos $$3 = $$1.relative($$2);
/* 328 */     return !$$0.getBlockState($$3).isFaceSturdy((BlockGetter)$$0, $$3, $$2.getOpposite());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SculkSpreader$ChargeCursor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */