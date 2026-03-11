/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ 
/*    */ public class SetWalkTargetFromAttackTargetIfTargetOutOfReach {
/*    */   private static final int PROJECTILE_ATTACK_RANGE_BUFFER = 1;
/*    */   
/*    */   public static BehaviorControl<Mob> create(float $$0) {
/* 22 */     return create($$1 -> Float.valueOf($$0));
/*    */   }
/*    */   
/*    */   public static BehaviorControl<Mob> create(Function<LivingEntity, Float> $$0) {
/* 26 */     return BehaviorBuilder.create($$1 -> $$1.group((App)$$1.registered(MemoryModuleType.WALK_TARGET), (App)$$1.registered(MemoryModuleType.LOOK_TARGET), (App)$$1.present(MemoryModuleType.ATTACK_TARGET), (App)$$1.registered(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply((Applicative)$$1, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SetWalkTargetFromAttackTargetIfTargetOutOfReach.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */