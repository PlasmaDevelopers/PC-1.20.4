/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class StayCloseToTarget {
/*    */   public static BehaviorControl<LivingEntity> create(Function<LivingEntity, Optional<PositionTracker>> $$0, Predicate<LivingEntity> $$1, int $$2, int $$3, float $$4) {
/* 14 */     return BehaviorBuilder.create($$5 -> $$5.group((App)$$5.registered(MemoryModuleType.LOOK_TARGET), (App)$$5.registered(MemoryModuleType.WALK_TARGET)).apply((Applicative)$$5, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\StayCloseToTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */