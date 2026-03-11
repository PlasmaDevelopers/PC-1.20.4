/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntitySelector;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class PlayerSensor extends Sensor<LivingEntity> {
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 20 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, LivingEntity $$1) {
/* 32 */     Objects.requireNonNull($$1);
/* 33 */     List<Player> $$2 = (List<Player>)$$0.players().stream().filter(EntitySelector.NO_SPECTATORS).filter($$1 -> $$0.closerThan((Entity)$$1, 16.0D)).sorted(Comparator.comparingDouble($$1::distanceToSqr)).collect(Collectors.toList());
/*    */     
/* 35 */     Brain<?> $$3 = $$1.getBrain();
/* 36 */     $$3.setMemory(MemoryModuleType.NEAREST_PLAYERS, $$2);
/*    */ 
/*    */     
/* 39 */     List<Player> $$4 = (List<Player>)$$2.stream().filter($$1 -> isEntityTargetable($$0, (LivingEntity)$$1)).collect(Collectors.toList());
/* 40 */     $$3.setMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER, $$4.isEmpty() ? null : $$4.get(0));
/*    */ 
/*    */     
/* 43 */     Optional<Player> $$5 = $$4.stream().filter($$1 -> isEntityAttackable($$0, (LivingEntity)$$1)).findFirst();
/* 44 */     $$3.setMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, $$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\PlayerSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */