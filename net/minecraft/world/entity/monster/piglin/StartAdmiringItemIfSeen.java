/*    */ package net.minecraft.world.entity.monster.piglin;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ 
/*    */ public class StartAdmiringItemIfSeen {
/*    */   public static BehaviorControl<LivingEntity> create(int $$0) {
/* 11 */     return (BehaviorControl<LivingEntity>)BehaviorBuilder.create($$1 -> $$1.group((App)$$1.present(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM), (App)$$1.absent(MemoryModuleType.ADMIRING_ITEM), (App)$$1.absent(MemoryModuleType.ADMIRING_DISABLED), (App)$$1.absent(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM)).apply((Applicative)$$1, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\StartAdmiringItemIfSeen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */