/*    */ package net.minecraft.world.entity.ai.behavior.warden;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.BehaviorControl;
/*    */ import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class SetWardenLookTarget {
/*    */   public static BehaviorControl<LivingEntity> create() {
/* 15 */     return (BehaviorControl<LivingEntity>)BehaviorBuilder.create($$0 -> $$0.group((App)$$0.registered(MemoryModuleType.LOOK_TARGET), (App)$$0.registered(MemoryModuleType.DISTURBANCE_LOCATION), (App)$$0.registered(MemoryModuleType.ROAR_TARGET), (App)$$0.absent(MemoryModuleType.ATTACK_TARGET)).apply((Applicative)$$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\warden\SetWardenLookTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */