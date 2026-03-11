/*    */ package net.minecraft.world.entity.monster.piglin;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.BehaviorControl;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ 
/*    */ public class StopAdmiringIfItemTooFarAway<E extends Piglin> {
/*    */   public static BehaviorControl<LivingEntity> create(int $$0) {
/* 13 */     return (BehaviorControl<LivingEntity>)BehaviorBuilder.create($$1 -> $$1.group((App)$$1.present(MemoryModuleType.ADMIRING_ITEM), (App)$$1.registered(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM)).apply((Applicative)$$1, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\StopAdmiringIfItemTooFarAway.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */