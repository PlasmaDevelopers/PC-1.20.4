/*    */ package net.minecraft.world.entity.monster.piglin;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.BehaviorControl;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class RememberIfHoglinWasKilled {
/*    */   public static BehaviorControl<LivingEntity> create() {
/* 11 */     return (BehaviorControl<LivingEntity>)BehaviorBuilder.create($$0 -> $$0.group((App)$$0.present(MemoryModuleType.ATTACK_TARGET), (App)$$0.registered(MemoryModuleType.HUNTED_RECENTLY)).apply((Applicative)$$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\RememberIfHoglinWasKilled.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */