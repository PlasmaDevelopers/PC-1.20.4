/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import org.apache.commons.lang3.mutable.MutableLong;
/*     */ 
/*     */ 
/*     */ public class AcquirePoi
/*     */ {
/*     */   public static final int SCAN_RANGE = 48;
/*     */   
/*     */   public static BehaviorControl<PathfinderMob> create(Predicate<Holder<PoiType>> $$0, MemoryModuleType<GlobalPos> $$1, boolean $$2, Optional<Byte> $$3) {
/*  38 */     return create($$0, $$1, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static BehaviorControl<PathfinderMob> create(Predicate<Holder<PoiType>> $$0, MemoryModuleType<GlobalPos> $$1, MemoryModuleType<GlobalPos> $$2, boolean $$3, Optional<Byte> $$4) {
/*  42 */     int $$5 = 5;
/*  43 */     int $$6 = 20;
/*     */ 
/*     */     
/*  46 */     MutableLong $$7 = new MutableLong(0L);
/*  47 */     Long2ObjectOpenHashMap long2ObjectOpenHashMap = new Long2ObjectOpenHashMap();
/*     */     
/*  49 */     OneShot<PathfinderMob> $$9 = BehaviorBuilder.create($$6 -> $$6.group((App)$$6.absent($$0)).apply((Applicative)$$6, ()));
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
/* 111 */     if ($$2 == $$1) {
/* 112 */       return $$9;
/*     */     }
/*     */     
/* 115 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.absent($$0)).apply((Applicative)$$2, ()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Path findPathToPois(Mob $$0, Set<Pair<Holder<PoiType>, BlockPos>> $$1) {
/* 122 */     if ($$1.isEmpty()) {
/* 123 */       return null;
/*     */     }
/* 125 */     Set<BlockPos> $$2 = new HashSet<>();
/* 126 */     int $$3 = 1;
/* 127 */     for (Pair<Holder<PoiType>, BlockPos> $$4 : $$1) {
/* 128 */       $$3 = Math.max($$3, ((PoiType)((Holder)$$4.getFirst()).value()).validRange());
/* 129 */       $$2.add((BlockPos)$$4.getSecond());
/*     */     } 
/* 131 */     return $$0.getNavigation().createPath($$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class JitteredLinearRetry
/*     */   {
/*     */     private static final int MIN_INTERVAL_INCREASE = 40;
/*     */     private static final int MAX_INTERVAL_INCREASE = 80;
/*     */     private static final int MAX_RETRY_PATHFINDING_INTERVAL = 400;
/*     */     private final RandomSource random;
/*     */     private long previousAttemptTimestamp;
/*     */     private long nextScheduledAttemptTimestamp;
/*     */     private int currentDelay;
/*     */     
/*     */     JitteredLinearRetry(RandomSource $$0, long $$1) {
/* 146 */       this.random = $$0;
/* 147 */       markAttempt($$1);
/*     */     }
/*     */     
/*     */     public void markAttempt(long $$0) {
/* 151 */       this.previousAttemptTimestamp = $$0;
/* 152 */       int $$1 = this.currentDelay + this.random.nextInt(40) + 40;
/* 153 */       this.currentDelay = Math.min($$1, 400);
/* 154 */       this.nextScheduledAttemptTimestamp = $$0 + this.currentDelay;
/*     */     }
/*     */     
/*     */     public boolean isStillValid(long $$0) {
/* 158 */       return ($$0 - this.previousAttemptTimestamp < 400L);
/*     */     }
/*     */     
/*     */     public boolean shouldRetry(long $$0) {
/* 162 */       return ($$0 >= this.nextScheduledAttemptTimestamp);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 167 */       return "RetryMarker{, previousAttemptAt=" + this.previousAttemptTimestamp + ", nextScheduledAttemptAt=" + this.nextScheduledAttemptTimestamp + ", currentDelay=" + this.currentDelay + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\AcquirePoi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */