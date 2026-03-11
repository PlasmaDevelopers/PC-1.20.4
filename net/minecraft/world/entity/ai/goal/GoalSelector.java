/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class GoalSelector
/*     */ {
/*  19 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  20 */   private static final WrappedGoal NO_GOAL = new WrappedGoal(2147483647, new Goal()
/*     */       {
/*     */         public boolean canUse() {
/*  23 */           return false;
/*     */         }
/*     */       })
/*     */     {
/*     */       public boolean isRunning() {
/*  28 */         return false;
/*     */       }
/*     */     };
/*     */   
/*  32 */   private final Map<Goal.Flag, WrappedGoal> lockedFlags = new EnumMap<>(Goal.Flag.class);
/*  33 */   private final Set<WrappedGoal> availableGoals = Sets.newLinkedHashSet();
/*     */   private final Supplier<ProfilerFiller> profiler;
/*  35 */   private final EnumSet<Goal.Flag> disabledFlags = EnumSet.noneOf(Goal.Flag.class);
/*     */   private int tickCount;
/*  37 */   private int newGoalRate = 3;
/*     */   
/*     */   public GoalSelector(Supplier<ProfilerFiller> $$0) {
/*  40 */     this.profiler = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGoal(int $$0, Goal $$1) {
/*  48 */     this.availableGoals.add(new WrappedGoal($$0, $$1));
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void removeAllGoals(Predicate<Goal> $$0) {
/*  53 */     this.availableGoals.removeIf($$1 -> $$0.test($$1.getGoal()));
/*     */   }
/*     */   
/*     */   public void removeGoal(Goal $$0) {
/*  57 */     this.availableGoals.stream().filter($$1 -> ($$1.getGoal() == $$0)).filter(WrappedGoal::isRunning).forEach(WrappedGoal::stop);
/*  58 */     this.availableGoals.removeIf($$1 -> ($$1.getGoal() == $$0));
/*     */   }
/*     */   
/*     */   private static boolean goalContainsAnyFlags(WrappedGoal $$0, EnumSet<Goal.Flag> $$1) {
/*  62 */     for (Goal.Flag $$2 : $$0.getFlags()) {
/*  63 */       if ($$1.contains($$2)) {
/*  64 */         return true;
/*     */       }
/*     */     } 
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean goalCanBeReplacedForAllFlags(WrappedGoal $$0, Map<Goal.Flag, WrappedGoal> $$1) {
/*  71 */     for (Goal.Flag $$2 : $$0.getFlags()) {
/*  72 */       if (!((WrappedGoal)$$1.getOrDefault($$2, NO_GOAL)).canBeReplacedBy($$0)) {
/*  73 */         return false;
/*     */       }
/*     */     } 
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  81 */     ProfilerFiller $$0 = this.profiler.get();
/*     */     
/*  83 */     $$0.push("goalCleanup");
/*  84 */     for (WrappedGoal $$1 : this.availableGoals) {
/*  85 */       if ($$1.isRunning() && (goalContainsAnyFlags($$1, this.disabledFlags) || !$$1.canContinueToUse())) {
/*  86 */         $$1.stop();
/*     */       }
/*     */     } 
/*  89 */     for ($$2 = this.lockedFlags.entrySet().iterator(); $$2.hasNext(); ) {
/*  90 */       Map.Entry<Goal.Flag, WrappedGoal> $$3 = $$2.next();
/*  91 */       if (!((WrappedGoal)$$3.getValue()).isRunning()) {
/*  92 */         $$2.remove();
/*     */       }
/*     */     } 
/*  95 */     $$0.pop();
/*     */     
/*  97 */     $$0.push("goalUpdate");
/*  98 */     for (WrappedGoal $$4 : this.availableGoals) {
/*  99 */       if ($$4.isRunning() || goalContainsAnyFlags($$4, this.disabledFlags) || !goalCanBeReplacedForAllFlags($$4, this.lockedFlags) || !$$4.canUse()) {
/*     */         continue;
/*     */       }
/* 102 */       for (Goal.Flag $$5 : $$4.getFlags()) {
/* 103 */         WrappedGoal $$6 = this.lockedFlags.getOrDefault($$5, NO_GOAL);
/* 104 */         $$6.stop();
/* 105 */         this.lockedFlags.put($$5, $$4);
/*     */       } 
/* 107 */       $$4.start();
/*     */     } 
/* 109 */     $$0.pop();
/*     */     
/* 111 */     tickRunningGoals(true);
/*     */   }
/*     */   
/*     */   public void tickRunningGoals(boolean $$0) {
/* 115 */     ProfilerFiller $$1 = this.profiler.get();
/*     */     
/* 117 */     $$1.push("goalTick");
/* 118 */     for (WrappedGoal $$2 : this.availableGoals) {
/* 119 */       if ($$2.isRunning() && ($$0 || $$2.requiresUpdateEveryTick())) {
/* 120 */         $$2.tick();
/*     */       }
/*     */     } 
/* 123 */     $$1.pop();
/*     */   }
/*     */   
/*     */   public Set<WrappedGoal> getAvailableGoals() {
/* 127 */     return this.availableGoals;
/*     */   }
/*     */   
/*     */   public Stream<WrappedGoal> getRunningGoals() {
/* 131 */     return this.availableGoals.stream().filter(WrappedGoal::isRunning);
/*     */   }
/*     */   
/*     */   public void setNewGoalRate(int $$0) {
/* 135 */     this.newGoalRate = $$0;
/*     */   }
/*     */   
/*     */   public void disableControlFlag(Goal.Flag $$0) {
/* 139 */     this.disabledFlags.add($$0);
/*     */   }
/*     */   
/*     */   public void enableControlFlag(Goal.Flag $$0) {
/* 143 */     this.disabledFlags.remove($$0);
/*     */   }
/*     */   
/*     */   public void setControlFlag(Goal.Flag $$0, boolean $$1) {
/* 147 */     if ($$1) {
/* 148 */       enableControlFlag($$0);
/*     */     } else {
/* 150 */       disableControlFlag($$0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\GoalSelector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */