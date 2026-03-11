/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.Unit;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class IsInWaterSensor
/*    */   extends Sensor<LivingEntity>
/*    */ {
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 14 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.IS_IN_WATER);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, LivingEntity $$1) {
/* 19 */     if ($$1.isInWater()) {
/* 20 */       $$1.getBrain().setMemory(MemoryModuleType.IS_IN_WATER, Unit.INSTANCE);
/*    */     } else {
/* 22 */       $$1.getBrain().eraseMemory(MemoryModuleType.IS_IN_WATER);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\IsInWaterSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */