/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.random.WeightedEntry;
/*     */ import net.minecraft.util.random.WeightedRandom;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class LongJumpToRandomPos<E extends Mob> extends Behavior<E> {
/*     */   protected static final int FIND_JUMP_TRIES = 20;
/*  37 */   private static final List<Integer> ALLOWED_ANGLES = Lists.newArrayList((Object[])new Integer[] { Integer.valueOf(65), Integer.valueOf(70), Integer.valueOf(75), Integer.valueOf(80) }); private static final int PREPARE_JUMP_DURATION = 40;
/*     */   protected static final int MIN_PATHFIND_DISTANCE_TO_VALID_JUMP = 8;
/*     */   private static final int TIME_OUT_DURATION = 200;
/*     */   private final UniformInt timeBetweenLongJumps;
/*     */   protected final int maxLongJumpHeight;
/*     */   protected final int maxLongJumpWidth;
/*     */   protected final float maxJumpVelocity;
/*  44 */   protected List<PossibleJump> jumpCandidates = Lists.newArrayList();
/*  45 */   protected Optional<Vec3> initialPosition = Optional.empty();
/*     */   @Nullable
/*     */   protected Vec3 chosenJump;
/*     */   protected int findJumpTries;
/*     */   protected long prepareJumpStart;
/*     */   private final Function<E, SoundEvent> getJumpSound;
/*     */   private final BiPredicate<E, BlockPos> acceptableLandingSpot;
/*     */   
/*     */   public LongJumpToRandomPos(UniformInt $$0, int $$1, int $$2, float $$3, Function<E, SoundEvent> $$4) {
/*  54 */     this($$0, $$1, $$2, $$3, $$4, LongJumpToRandomPos::defaultAcceptableLandingSpot);
/*     */   }
/*     */   
/*     */   public static <E extends Mob> boolean defaultAcceptableLandingSpot(E $$0, BlockPos $$1) {
/*  58 */     Level $$2 = $$0.level();
/*  59 */     BlockPos $$3 = $$1.below();
/*  60 */     return ($$2.getBlockState($$3).isSolidRender((BlockGetter)$$2, $$3) && $$0
/*  61 */       .getPathfindingMalus(WalkNodeEvaluator.getBlockPathTypeStatic((BlockGetter)$$2, $$1.mutable())) == 0.0F);
/*     */   }
/*     */   
/*     */   public LongJumpToRandomPos(UniformInt $$0, int $$1, int $$2, float $$3, Function<E, SoundEvent> $$4, BiPredicate<E, BlockPos> $$5) {
/*  65 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_ABSENT), 200);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     this.timeBetweenLongJumps = $$0;
/*  72 */     this.maxLongJumpHeight = $$1;
/*  73 */     this.maxLongJumpWidth = $$2;
/*  74 */     this.maxJumpVelocity = $$3;
/*  75 */     this.getJumpSound = $$4;
/*  76 */     this.acceptableLandingSpot = $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Mob $$1) {
/*  81 */     boolean $$2 = ($$1.onGround() && !$$1.isInWater() && !$$1.isInLava() && !$$0.getBlockState($$1.blockPosition()).is(Blocks.HONEY_BLOCK));
/*  82 */     if (!$$2) {
/*  83 */       $$1.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, Integer.valueOf(this.timeBetweenLongJumps.sample($$0.random) / 2));
/*     */     }
/*  85 */     return $$2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Mob $$1, long $$2) {
/*  94 */     boolean $$3 = (this.initialPosition.isPresent() && ((Vec3)this.initialPosition.get()).equals($$1.position()) && this.findJumpTries > 0 && !$$1.isInWaterOrBubble() && (this.chosenJump != null || !this.jumpCandidates.isEmpty()));
/*     */     
/*  96 */     if (!$$3 && $$1.getBrain().getMemory(MemoryModuleType.LONG_JUMP_MID_JUMP).isEmpty()) {
/*  97 */       $$1.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, Integer.valueOf(this.timeBetweenLongJumps.sample($$0.random) / 2));
/*  98 */       $$1.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
/*     */     } 
/* 100 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, E $$1, long $$2) {
/* 105 */     this.chosenJump = null;
/* 106 */     this.findJumpTries = 20;
/* 107 */     this.initialPosition = Optional.of($$1.position());
/*     */     
/* 109 */     BlockPos $$3 = $$1.blockPosition();
/* 110 */     int $$4 = $$3.getX();
/* 111 */     int $$5 = $$3.getY();
/* 112 */     int $$6 = $$3.getZ();
/*     */     
/* 114 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 124 */       .jumpCandidates = (List<PossibleJump>)BlockPos.betweenClosedStream($$4 - this.maxLongJumpWidth, $$5 - this.maxLongJumpHeight, $$6 - this.maxLongJumpWidth, $$4 + this.maxLongJumpWidth, $$5 + this.maxLongJumpHeight, $$6 + this.maxLongJumpWidth).filter($$1 -> !$$1.equals($$0)).map($$1 -> new PossibleJump($$1.immutable(), Mth.ceil($$0.distSqr((Vec3i)$$1)))).collect(Collectors.toCollection(Lists::newArrayList));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, E $$1, long $$2) {
/* 129 */     if (this.chosenJump != null) {
/* 130 */       if ($$2 - this.prepareJumpStart >= 40L) {
/* 131 */         $$1.setYRot(((Mob)$$1).yBodyRot);
/* 132 */         $$1.setDiscardFriction(true);
/* 133 */         double $$3 = this.chosenJump.length();
/*     */ 
/*     */         
/* 136 */         double $$4 = $$3 + $$1.getJumpBoostPower();
/* 137 */         $$1.setDeltaMovement(this.chosenJump.scale($$4 / $$3));
/*     */         
/* 139 */         $$1.getBrain().setMemory(MemoryModuleType.LONG_JUMP_MID_JUMP, Boolean.valueOf(true));
/* 140 */         $$0.playSound(null, (Entity)$$1, this.getJumpSound.apply($$1), SoundSource.NEUTRAL, 1.0F, 1.0F);
/*     */       } 
/*     */     } else {
/* 143 */       this.findJumpTries--;
/* 144 */       pickCandidate($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void pickCandidate(ServerLevel $$0, E $$1, long $$2) {
/* 149 */     while (!this.jumpCandidates.isEmpty()) {
/* 150 */       Optional<PossibleJump> $$3 = getJumpCandidate($$0);
/* 151 */       if ($$3.isEmpty()) {
/*     */         continue;
/*     */       }
/*     */       
/* 155 */       PossibleJump $$4 = $$3.get();
/*     */       
/* 157 */       BlockPos $$5 = $$4.getJumpTarget();
/* 158 */       if (!isAcceptableLandingPosition($$0, $$1, $$5)) {
/*     */         continue;
/*     */       }
/*     */       
/* 162 */       Vec3 $$6 = Vec3.atCenterOf((Vec3i)$$5);
/* 163 */       Vec3 $$7 = calculateOptimalJumpVector((Mob)$$1, $$6);
/* 164 */       if ($$7 == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 168 */       $$1.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker($$5));
/*     */ 
/*     */       
/* 171 */       PathNavigation $$8 = $$1.getNavigation();
/* 172 */       Path $$9 = $$8.createPath($$5, 0, 8);
/* 173 */       if ($$9 == null || !$$9.canReach()) {
/* 174 */         this.chosenJump = $$7;
/* 175 */         this.prepareJumpStart = $$2;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Optional<PossibleJump> getJumpCandidate(ServerLevel $$0) {
/* 182 */     Optional<PossibleJump> $$1 = WeightedRandom.getRandomItem($$0.random, this.jumpCandidates);
/* 183 */     Objects.requireNonNull(this.jumpCandidates); $$1.ifPresent(this.jumpCandidates::remove);
/* 184 */     return $$1;
/*     */   }
/*     */   
/*     */   private boolean isAcceptableLandingPosition(ServerLevel $$0, E $$1, BlockPos $$2) {
/* 188 */     BlockPos $$3 = $$1.blockPosition();
/* 189 */     int $$4 = $$3.getX();
/* 190 */     int $$5 = $$3.getZ();
/*     */     
/* 192 */     if ($$4 == $$2.getX() && $$5 == $$2.getZ()) {
/* 193 */       return false;
/*     */     }
/* 195 */     return this.acceptableLandingSpot.test($$1, $$2);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Vec3 calculateOptimalJumpVector(Mob $$0, Vec3 $$1) {
/* 200 */     List<Integer> $$2 = Lists.newArrayList(ALLOWED_ANGLES);
/* 201 */     Collections.shuffle($$2);
/*     */     
/* 203 */     for (Iterator<Integer> iterator = $$2.iterator(); iterator.hasNext(); ) { int $$3 = ((Integer)iterator.next()).intValue();
/*     */       
/* 205 */       Optional<Vec3> $$4 = LongJumpUtil.calculateJumpVectorForAngle($$0, $$1, this.maxJumpVelocity, $$3, true);
/* 206 */       if ($$4.isPresent()) {
/* 207 */         return $$4.get();
/*     */       } }
/*     */ 
/*     */     
/* 211 */     return null;
/*     */   }
/*     */   
/*     */   public static class PossibleJump extends WeightedEntry.IntrusiveBase {
/*     */     private final BlockPos jumpTarget;
/*     */     
/*     */     public PossibleJump(BlockPos $$0, int $$1) {
/* 218 */       super($$1);
/* 219 */       this.jumpTarget = $$0;
/*     */     }
/*     */     
/*     */     public BlockPos getJumpTarget() {
/* 223 */       return this.jumpTarget;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\LongJumpToRandomPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */