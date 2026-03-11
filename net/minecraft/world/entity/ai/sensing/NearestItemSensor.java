/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ 
/*    */ public class NearestItemSensor
/*    */   extends Sensor<Mob> {
/*    */   private static final long XZ_RANGE = 32L;
/*    */   
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 22 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
/*    */   }
/*    */   private static final long Y_RANGE = 16L;
/*    */   public static final int MAX_DISTANCE_TO_WANTED_ITEM = 32;
/*    */   
/*    */   protected void doTick(ServerLevel $$0, Mob $$1) {
/* 28 */     Brain<?> $$2 = $$1.getBrain();
/*    */     
/* 30 */     List<ItemEntity> $$3 = $$0.getEntitiesOfClass(ItemEntity.class, $$1.getBoundingBox().inflate(32.0D, 16.0D, 32.0D), $$0 -> true);
/* 31 */     Objects.requireNonNull($$1); $$3.sort(Comparator.comparingDouble($$1::distanceToSqr));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 36 */     Objects.requireNonNull($$1);
/* 37 */     Optional<ItemEntity> $$4 = $$3.stream().filter($$1 -> $$0.wantsToPickUp($$1.getItem())).filter($$1 -> $$1.closerThan((Entity)$$0, 32.0D)).filter($$1::hasLineOfSight).findFirst();
/* 38 */     $$2.setMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\NearestItemSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */