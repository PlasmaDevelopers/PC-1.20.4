/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Iterables;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.monster.warden.Warden;
/*    */ 
/*    */ public class WardenEntitySensor
/*    */   extends NearestLivingEntitySensor<Warden> {
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 20 */     return (Set<MemoryModuleType<?>>)ImmutableSet.copyOf(Iterables.concat(super
/* 21 */           .requires(), 
/* 22 */           List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, Warden $$1) {
/* 28 */     super.doTick($$0, $$1);
/*    */     
/* 30 */     getClosest($$1, $$0 -> ($$0.getType() == EntityType.PLAYER))
/* 31 */       .or(() -> getClosest($$0, ()))
/* 32 */       .ifPresentOrElse($$1 -> $$0.getBrain().setMemory(MemoryModuleType.NEAREST_ATTACKABLE, $$1), () -> $$0.getBrain().eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Optional<LivingEntity> getClosest(Warden $$0, Predicate<LivingEntity> $$1) {
/* 41 */     Objects.requireNonNull($$0); return $$0.getBrain().getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).stream().flatMap(Collection::stream).filter($$0::canTargetEntity)
/* 42 */       .filter($$1)
/* 43 */       .findFirst();
/*    */   }
/*    */ 
/*    */   
/*    */   protected int radiusXZ() {
/* 48 */     return 24;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int radiusY() {
/* 53 */     return 24;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\WardenEntitySensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */