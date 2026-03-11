/*    */ package net.minecraft.world.entity.monster.piglin;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.BehaviorControl;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class StopAdmiringIfTiredOfTryingToReachItem {
/*    */   public static BehaviorControl<LivingEntity> create(int $$0, int $$1) {
/* 12 */     return (BehaviorControl<LivingEntity>)BehaviorBuilder.create($$2 -> $$2.group((App)$$2.present(MemoryModuleType.ADMIRING_ITEM), (App)$$2.present(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM), (App)$$2.registered(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM), (App)$$2.registered(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM)).apply((Applicative)$$2, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\StopAdmiringIfTiredOfTryingToReachItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */