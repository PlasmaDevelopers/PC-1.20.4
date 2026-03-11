/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ 
/*    */ public class SetWalkTargetFromLookTarget {
/*    */   public static OneShot<LivingEntity> create(float $$0, int $$1) {
/* 16 */     return create($$0 -> true, $$1 -> Float.valueOf($$0), $$1);
/*    */   }
/*    */   
/*    */   public static OneShot<LivingEntity> create(Predicate<LivingEntity> $$0, Function<LivingEntity, Float> $$1, int $$2) {
/* 20 */     return BehaviorBuilder.create($$3 -> $$3.group((App)$$3.absent(MemoryModuleType.WALK_TARGET), (App)$$3.present(MemoryModuleType.LOOK_TARGET)).apply((Applicative)$$3, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SetWalkTargetFromLookTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */