/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Iterables;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.monster.breeze.Breeze;
/*    */ 
/*    */ public class BreezeAttackEntitySensor
/*    */   extends NearestLivingEntitySensor<Breeze>
/*    */ {
/*    */   public static final int BREEZE_SENSOR_RADIUS = 24;
/*    */   
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 19 */     return (Set<MemoryModuleType<?>>)ImmutableSet.copyOf(Iterables.concat(super
/* 20 */           .requires(), 
/* 21 */           List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, Breeze $$1) {
/* 27 */     super.doTick($$0, $$1);
/*    */     
/* 29 */     $$1.getBrain().getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).stream()
/* 30 */       .flatMap(Collection::stream)
/* 31 */       .filter($$1 -> Sensor.isEntityAttackable((LivingEntity)$$0, $$1)).findFirst()
/* 32 */       .ifPresentOrElse($$1 -> $$0.getBrain().setMemory(MemoryModuleType.NEAREST_ATTACKABLE, $$1), () -> $$0.getBrain().eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int radiusXZ() {
/* 40 */     return 24;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int radiusY() {
/* 45 */     return 24;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\BreezeAttackEntitySensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */