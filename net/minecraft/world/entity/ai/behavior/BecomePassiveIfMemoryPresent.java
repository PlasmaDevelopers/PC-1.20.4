/*   */ package net.minecraft.world.entity.ai.behavior;
/*   */ import com.mojang.datafixers.kinds.App;
/*   */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*   */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*   */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*   */ 
/*   */ public class BecomePassiveIfMemoryPresent {
/*   */   public static BehaviorControl<LivingEntity> create(MemoryModuleType<?> $$0, int $$1) {
/* 9 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.registered(MemoryModuleType.ATTACK_TARGET), (App)$$2.absent(MemoryModuleType.PACIFIED), (App)$$2.present($$0)).apply((Applicative)$$2, (App)$$2.point((), ())));
/*   */   }
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\BecomePassiveIfMemoryPresent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */