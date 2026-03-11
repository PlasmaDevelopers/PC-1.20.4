/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.level.block.BellBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class RingBell {
/*    */   public static BehaviorControl<LivingEntity> create() {
/* 16 */     return BehaviorBuilder.create($$0 -> $$0.group((App)$$0.present(MemoryModuleType.MEETING_POINT)).apply((Applicative)$$0, ()));
/*    */   }
/*    */   
/*    */   private static final float BELL_RING_CHANCE = 0.95F;
/*    */   public static final int RING_BELL_FROM_DISTANCE = 3;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\RingBell.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */