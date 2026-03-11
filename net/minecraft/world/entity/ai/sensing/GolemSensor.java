/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GolemSensor
/*    */   extends Sensor<LivingEntity>
/*    */ {
/*    */   private static final int GOLEM_SCAN_RATE = 200;
/*    */   private static final int MEMORY_TIME_TO_LIVE = 599;
/*    */   
/*    */   public GolemSensor() {
/* 22 */     this(200);
/*    */   }
/*    */   
/*    */   public GolemSensor(int $$0) {
/* 26 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, LivingEntity $$1) {
/* 31 */     checkForNearbyGolem($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 36 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.NEAREST_LIVING_ENTITIES);
/*    */   }
/*    */   
/*    */   public static void checkForNearbyGolem(LivingEntity $$0) {
/* 40 */     Optional<List<LivingEntity>> $$1 = $$0.getBrain().getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES);
/* 41 */     if ($$1.isEmpty()) {
/*    */       return;
/*    */     }
/*    */     
/* 45 */     boolean $$2 = ((List)$$1.get()).stream().anyMatch($$0 -> $$0.getType().equals(EntityType.IRON_GOLEM));
/*    */     
/* 47 */     if ($$2) {
/* 48 */       golemDetected($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void golemDetected(LivingEntity $$0) {
/* 53 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.GOLEM_DETECTED_RECENTLY, Boolean.valueOf(true), 599L);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\GolemSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */