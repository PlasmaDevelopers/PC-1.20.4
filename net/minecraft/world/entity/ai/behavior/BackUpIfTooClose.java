/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class BackUpIfTooClose {
/*    */   public static OneShot<Mob> create(int $$0, float $$1) {
/* 14 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.absent(MemoryModuleType.WALK_TARGET), (App)$$2.registered(MemoryModuleType.LOOK_TARGET), (App)$$2.present(MemoryModuleType.ATTACK_TARGET), (App)$$2.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply((Applicative)$$2, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\BackUpIfTooClose.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */