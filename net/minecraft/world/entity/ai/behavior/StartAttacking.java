/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class StartAttacking {
/*    */   public static <E extends Mob> BehaviorControl<E> create(Function<E, Optional<? extends LivingEntity>> $$0) {
/* 17 */     return create($$0 -> true, $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <E extends Mob> BehaviorControl<E> create(Predicate<E> $$0, Function<E, Optional<? extends LivingEntity>> $$1) {
/* 24 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.absent(MemoryModuleType.ATTACK_TARGET), (App)$$2.registered(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)).apply((Applicative)$$2, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\StartAttacking.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */