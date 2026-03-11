/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetWalkTargetFromBlockMemory
/*    */ {
/*    */   public static OneShot<Villager> create(MemoryModuleType<GlobalPos> $$0, float $$1, int $$2, int $$3, int $$4) {
/* 26 */     return BehaviorBuilder.create($$5 -> $$5.group((App)$$5.registered(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE), (App)$$5.absent(MemoryModuleType.WALK_TARGET), (App)$$5.present($$0)).apply((Applicative)$$5, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SetWalkTargetFromBlockMemory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */