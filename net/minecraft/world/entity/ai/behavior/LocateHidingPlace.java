/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class LocateHidingPlace {
/*    */   public static OneShot<LivingEntity> create(int $$0, float $$1, int $$2) {
/* 13 */     return BehaviorBuilder.create($$3 -> $$3.group((App)$$3.absent(MemoryModuleType.WALK_TARGET), (App)$$3.registered(MemoryModuleType.HOME), (App)$$3.registered(MemoryModuleType.HIDING_PLACE), (App)$$3.registered(MemoryModuleType.PATH), (App)$$3.registered(MemoryModuleType.LOOK_TARGET), (App)$$3.registered(MemoryModuleType.BREED_TARGET), (App)$$3.registered(MemoryModuleType.INTERACTION_TARGET)).apply((Applicative)$$3, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\LocateHidingPlace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */