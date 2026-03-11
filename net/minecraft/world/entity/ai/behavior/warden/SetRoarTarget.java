/*    */ package net.minecraft.world.entity.ai.behavior.warden;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.BehaviorControl;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.monster.warden.Warden;
/*    */ 
/*    */ public class SetRoarTarget {
/*    */   public static <E extends Warden> BehaviorControl<E> create(Function<E, Optional<? extends LivingEntity>> $$0) {
/* 17 */     return (BehaviorControl<E>)BehaviorBuilder.create($$1 -> $$1.group((App)$$1.absent(MemoryModuleType.ROAR_TARGET), (App)$$1.absent(MemoryModuleType.ATTACK_TARGET), (App)$$1.registered(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)).apply((Applicative)$$1, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\warden\SetRoarTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */