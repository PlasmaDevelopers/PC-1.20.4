/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class GoToTargetLocation {
/*    */   private static BlockPos getNearbyPos(Mob $$0, BlockPos $$1) {
/* 11 */     RandomSource $$2 = ($$0.level()).random;
/* 12 */     return $$1.offset(getRandomOffset($$2), 0, getRandomOffset($$2));
/*    */   }
/*    */   
/*    */   private static int getRandomOffset(RandomSource $$0) {
/* 16 */     return $$0.nextInt(3) - 1;
/*    */   }
/*    */   
/*    */   public static <E extends Mob> OneShot<E> create(MemoryModuleType<BlockPos> $$0, int $$1, float $$2) {
/* 20 */     return BehaviorBuilder.create($$3 -> $$3.group((App)$$3.present($$0), (App)$$3.absent(MemoryModuleType.ATTACK_TARGET), (App)$$3.absent(MemoryModuleType.WALK_TARGET), (App)$$3.registered(MemoryModuleType.LOOK_TARGET)).apply((Applicative)$$3, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\GoToTargetLocation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */