/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntitySelector;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.Ingredient;
/*    */ 
/*    */ public class TemptingSensor extends Sensor<PathfinderMob> {
/* 21 */   private static final TargetingConditions TEMPT_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
/*    */   
/*    */   public static final int TEMPTATION_RANGE = 10;
/*    */   private final Ingredient temptations;
/*    */   
/*    */   public TemptingSensor(Ingredient $$0) {
/* 27 */     this.temptations = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, PathfinderMob $$1) {
/* 32 */     Brain<?> $$2 = $$1.getBrain();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 39 */     Objects.requireNonNull($$1);
/* 40 */     List<Player> $$3 = (List<Player>)$$0.players().stream().filter(EntitySelector.NO_SPECTATORS).filter($$1 -> TEMPT_TARGETING.test((LivingEntity)$$0, (LivingEntity)$$1)).filter($$1 -> $$0.closerThan((Entity)$$1, 10.0D)).filter(this::playerHoldingTemptation).filter($$1 -> !$$0.hasPassenger((Entity)$$1)).sorted(Comparator.comparingDouble($$1::distanceToSqr)).collect(Collectors.toList());
/*    */     
/* 42 */     if (!$$3.isEmpty()) {
/* 43 */       Player $$4 = $$3.get(0);
/* 44 */       $$2.setMemory(MemoryModuleType.TEMPTING_PLAYER, $$4);
/*    */     } else {
/* 46 */       $$2.eraseMemory(MemoryModuleType.TEMPTING_PLAYER);
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean playerHoldingTemptation(Player $$0) {
/* 51 */     return (isTemptation($$0.getMainHandItem()) || isTemptation($$0.getOffhandItem()));
/*    */   }
/*    */   
/*    */   private boolean isTemptation(ItemStack $$0) {
/* 55 */     return this.temptations.test($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 60 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.TEMPTING_PLAYER);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\TemptingSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */