/*     */ package net.minecraft.world.entity.monster.warden;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class WardenSpawnTracker {
/*     */   static {
/*  23 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("ticks_since_last_warning").orElse(Integer.valueOf(0)).forGetter(()), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("warning_level").orElse(Integer.valueOf(0)).forGetter(()), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("cooldown_ticks").orElse(Integer.valueOf(0)).forGetter(())).apply((Applicative)$$0, WardenSpawnTracker::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<WardenSpawnTracker> CODEC;
/*     */   
/*     */   public static final int MAX_WARNING_LEVEL = 4;
/*     */   
/*     */   private static final double PLAYER_SEARCH_RADIUS = 16.0D;
/*     */   
/*     */   private static final int WARNING_CHECK_DIAMETER = 48;
/*     */   private static final int DECREASE_WARNING_LEVEL_EVERY_INTERVAL = 12000;
/*     */   private static final int WARNING_LEVEL_INCREASE_COOLDOWN = 200;
/*     */   private int ticksSinceLastWarning;
/*     */   private int warningLevel;
/*     */   private int cooldownTicks;
/*     */   
/*     */   public WardenSpawnTracker(int $$0, int $$1, int $$2) {
/*  41 */     this.ticksSinceLastWarning = $$0;
/*  42 */     this.warningLevel = $$1;
/*  43 */     this.cooldownTicks = $$2;
/*     */   }
/*     */   
/*     */   public void tick() {
/*  47 */     if (this.ticksSinceLastWarning >= 12000) {
/*  48 */       decreaseWarningLevel();
/*  49 */       this.ticksSinceLastWarning = 0;
/*     */     } else {
/*  51 */       this.ticksSinceLastWarning++;
/*     */     } 
/*     */     
/*  54 */     if (this.cooldownTicks > 0) {
/*  55 */       this.cooldownTicks--;
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/*  60 */     this.ticksSinceLastWarning = 0;
/*  61 */     this.warningLevel = 0;
/*  62 */     this.cooldownTicks = 0;
/*     */   }
/*     */   
/*     */   public static OptionalInt tryWarn(ServerLevel $$0, BlockPos $$1, ServerPlayer $$2) {
/*  66 */     if (hasNearbyWarden($$0, $$1)) {
/*  67 */       return OptionalInt.empty();
/*     */     }
/*     */     
/*  70 */     List<ServerPlayer> $$3 = getNearbyPlayers($$0, $$1);
/*     */     
/*  72 */     if (!$$3.contains($$2)) {
/*  73 */       $$3.add($$2);
/*     */     }
/*     */ 
/*     */     
/*  77 */     if ($$3.stream().anyMatch($$0 -> ((Boolean)$$0.getWardenSpawnTracker().map(WardenSpawnTracker::onCooldown).orElse(Boolean.valueOf(false))).booleanValue())) {
/*  78 */       return OptionalInt.empty();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     Optional<WardenSpawnTracker> $$4 = $$3.stream().flatMap($$0 -> $$0.getWardenSpawnTracker().stream()).max(Comparator.comparingInt(WardenSpawnTracker::getWarningLevel));
/*     */     
/*  86 */     if ($$4.isPresent()) {
/*  87 */       WardenSpawnTracker $$5 = $$4.get();
/*     */       
/*  89 */       $$5.increaseWarningLevel();
/*     */ 
/*     */       
/*  92 */       $$3.forEach($$1 -> $$1.getWardenSpawnTracker().ifPresent(()));
/*     */       
/*  94 */       return OptionalInt.of($$5.warningLevel);
/*     */     } 
/*  96 */     return OptionalInt.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onCooldown() {
/* 101 */     return (this.cooldownTicks > 0);
/*     */   }
/*     */   
/*     */   private static boolean hasNearbyWarden(ServerLevel $$0, BlockPos $$1) {
/* 105 */     AABB $$2 = AABB.ofSize(Vec3.atCenterOf((Vec3i)$$1), 48.0D, 48.0D, 48.0D);
/* 106 */     return !$$0.getEntitiesOfClass(Warden.class, $$2).isEmpty();
/*     */   }
/*     */   
/*     */   private static List<ServerPlayer> getNearbyPlayers(ServerLevel $$0, BlockPos $$1) {
/* 110 */     Vec3 $$2 = Vec3.atCenterOf((Vec3i)$$1);
/*     */     
/* 112 */     Predicate<ServerPlayer> $$3 = $$1 -> $$1.position().closerThan((Position)$$0, 16.0D);
/* 113 */     return $$0.getPlayers($$3.and(LivingEntity::isAlive).and(EntitySelector.NO_SPECTATORS));
/*     */   }
/*     */   
/*     */   private void increaseWarningLevel() {
/* 117 */     if (!onCooldown()) {
/* 118 */       this.ticksSinceLastWarning = 0;
/* 119 */       this.cooldownTicks = 200;
/* 120 */       setWarningLevel(getWarningLevel() + 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void decreaseWarningLevel() {
/* 125 */     setWarningLevel(getWarningLevel() - 1);
/*     */   }
/*     */   
/*     */   public void setWarningLevel(int $$0) {
/* 129 */     this.warningLevel = Mth.clamp($$0, 0, 4);
/*     */   }
/*     */   
/*     */   public int getWarningLevel() {
/* 133 */     return this.warningLevel;
/*     */   }
/*     */   
/*     */   private void copyData(WardenSpawnTracker $$0) {
/* 137 */     this.warningLevel = $$0.warningLevel;
/* 138 */     this.cooldownTicks = $$0.cooldownTicks;
/* 139 */     this.ticksSinceLastWarning = $$0.ticksSinceLastWarning;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\warden\WardenSpawnTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */