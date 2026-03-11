/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Sensor<E extends LivingEntity>
/*    */ {
/* 18 */   private static final RandomSource RANDOM = RandomSource.createThreadSafe();
/*    */   
/*    */   private static final int DEFAULT_SCAN_RATE = 20;
/*    */   
/*    */   protected static final int TARGETING_RANGE = 16;
/* 23 */   private static final TargetingConditions TARGET_CONDITIONS = TargetingConditions.forNonCombat().range(16.0D);
/* 24 */   private static final TargetingConditions TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING = TargetingConditions.forNonCombat().range(16.0D).ignoreInvisibilityTesting();
/* 25 */   private static final TargetingConditions ATTACK_TARGET_CONDITIONS = TargetingConditions.forCombat().range(16.0D);
/* 26 */   private static final TargetingConditions ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING = TargetingConditions.forCombat().range(16.0D).ignoreInvisibilityTesting();
/* 27 */   private static final TargetingConditions ATTACK_TARGET_CONDITIONS_IGNORE_LINE_OF_SIGHT = TargetingConditions.forCombat().range(16.0D).ignoreLineOfSight();
/* 28 */   private static final TargetingConditions ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY_AND_LINE_OF_SIGHT = TargetingConditions.forCombat().range(16.0D).ignoreLineOfSight().ignoreInvisibilityTesting();
/*    */   
/*    */   private final int scanRate;
/*    */   private long timeToTick;
/*    */   
/*    */   public Sensor(int $$0) {
/* 34 */     this.scanRate = $$0;
/* 35 */     this.timeToTick = RANDOM.nextInt($$0);
/*    */   }
/*    */   
/*    */   public Sensor() {
/* 39 */     this(20);
/*    */   }
/*    */   
/*    */   public final void tick(ServerLevel $$0, E $$1) {
/* 43 */     if (--this.timeToTick <= 0L) {
/* 44 */       this.timeToTick = this.scanRate;
/* 45 */       doTick($$0, $$1);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void doTick(ServerLevel paramServerLevel, E paramE);
/*    */   
/*    */   public abstract Set<MemoryModuleType<?>> requires();
/*    */   
/*    */   public static boolean isEntityTargetable(LivingEntity $$0, LivingEntity $$1) {
/* 54 */     if ($$0.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, $$1))
/*    */     {
/* 56 */       return TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING.test($$0, $$1);
/*    */     }
/* 58 */     return TARGET_CONDITIONS.test($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isEntityAttackable(LivingEntity $$0, LivingEntity $$1) {
/* 63 */     if ($$0.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, $$1))
/*    */     {
/* 65 */       return ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING.test($$0, $$1);
/*    */     }
/* 67 */     return ATTACK_TARGET_CONDITIONS.test($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isEntityAttackableIgnoringLineOfSight(LivingEntity $$0, LivingEntity $$1) {
/* 72 */     if ($$0.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, $$1))
/*    */     {
/* 74 */       return ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY_AND_LINE_OF_SIGHT.test($$0, $$1);
/*    */     }
/* 76 */     return ATTACK_TARGET_CONDITIONS_IGNORE_LINE_OF_SIGHT.test($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\Sensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */