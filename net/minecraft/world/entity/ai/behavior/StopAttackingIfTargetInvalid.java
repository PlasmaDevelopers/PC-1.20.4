/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class StopAttackingIfTargetInvalid
/*    */ {
/*    */   private static final int TIMEOUT_TO_GET_WITHIN_ATTACK_RANGE = 200;
/*    */   
/*    */   public static <E extends Mob> BehaviorControl<E> create(BiConsumer<E, LivingEntity> $$0) {
/* 21 */     return create($$0 -> false, $$0, true);
/*    */   }
/*    */   
/*    */   public static <E extends Mob> BehaviorControl<E> create(Predicate<LivingEntity> $$0) {
/* 25 */     return create($$0, ($$0, $$1) -> {  }true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <E extends Mob> BehaviorControl<E> create() {
/* 33 */     return create($$0 -> false, ($$0, $$1) -> {  }true);
/*    */   }
/*    */   
/*    */   public static <E extends Mob> BehaviorControl<E> create(Predicate<LivingEntity> $$0, BiConsumer<E, LivingEntity> $$1, boolean $$2) {
/* 37 */     return BehaviorBuilder.create($$3 -> $$3.group((App)$$3.present(MemoryModuleType.ATTACK_TARGET), (App)$$3.registered(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)).apply((Applicative)$$3, ()));
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isTiredOfTryingToReachTarget(LivingEntity $$0, Optional<Long> $$1) {
/* 59 */     return ($$1.isPresent() && $$0.level().getGameTime() - ((Long)$$1.get()).longValue() > 200L);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\StopAttackingIfTargetInvalid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */