/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class TryLaySpawnOnWaterNearLand {
/*    */   public static BehaviorControl<LivingEntity> create(Block $$0) {
/* 17 */     return BehaviorBuilder.create($$1 -> $$1.group((App)$$1.absent(MemoryModuleType.ATTACK_TARGET), (App)$$1.present(MemoryModuleType.WALK_TARGET), (App)$$1.present(MemoryModuleType.IS_PREGNANT)).apply((Applicative)$$1, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\TryLaySpawnOnWaterNearLand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */