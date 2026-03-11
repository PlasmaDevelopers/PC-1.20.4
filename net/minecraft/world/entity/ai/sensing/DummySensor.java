/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DummySensor
/*    */   extends Sensor<LivingEntity>
/*    */ {
/*    */   protected void doTick(ServerLevel $$0, LivingEntity $$1) {}
/*    */   
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 17 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\DummySensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */