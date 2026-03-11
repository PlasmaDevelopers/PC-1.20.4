/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class SetWalkTargetAwayFrom {
/*    */   public static BehaviorControl<PathfinderMob> pos(MemoryModuleType<BlockPos> $$0, float $$1, int $$2, boolean $$3) {
/* 17 */     return create($$0, $$1, $$2, $$3, Vec3::atBottomCenterOf);
/*    */   }
/*    */   
/*    */   public static OneShot<PathfinderMob> entity(MemoryModuleType<? extends Entity> $$0, float $$1, int $$2, boolean $$3) {
/* 21 */     return create($$0, $$1, $$2, $$3, Entity::position);
/*    */   }
/*    */   
/*    */   private static <T> OneShot<PathfinderMob> create(MemoryModuleType<T> $$0, float $$1, int $$2, boolean $$3, Function<T, Vec3> $$4) {
/* 25 */     return BehaviorBuilder.create($$5 -> $$5.group((App)$$5.registered(MemoryModuleType.WALK_TARGET), (App)$$5.present($$0)).apply((Applicative)$$5, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SetWalkTargetAwayFrom.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */