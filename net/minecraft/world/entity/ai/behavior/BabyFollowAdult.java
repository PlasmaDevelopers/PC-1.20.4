/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.valueproviders.UniformInt;
/*    */ import net.minecraft.world.entity.AgeableMob;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ 
/*    */ public class BabyFollowAdult {
/*    */   public static OneShot<AgeableMob> create(UniformInt $$0, float $$1) {
/* 14 */     return create($$0, $$1 -> Float.valueOf($$0));
/*    */   }
/*    */   
/*    */   public static OneShot<AgeableMob> create(UniformInt $$0, Function<LivingEntity, Float> $$1) {
/* 18 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.present(MemoryModuleType.NEAREST_VISIBLE_ADULT), (App)$$2.registered(MemoryModuleType.LOOK_TARGET), (App)$$2.absent(MemoryModuleType.WALK_TARGET)).apply((Applicative)$$2, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\BabyFollowAdult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */