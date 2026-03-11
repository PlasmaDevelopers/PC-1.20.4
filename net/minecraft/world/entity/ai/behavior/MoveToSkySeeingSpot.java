/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class MoveToSkySeeingSpot {
/*    */   public static OneShot<LivingEntity> create(float $$0) {
/* 18 */     return BehaviorBuilder.create($$1 -> $$1.group((App)$$1.absent(MemoryModuleType.WALK_TARGET)).apply((Applicative)$$1, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private static Vec3 getOutdoorPosition(ServerLevel $$0, LivingEntity $$1) {
/* 34 */     RandomSource $$2 = $$1.getRandom();
/* 35 */     BlockPos $$3 = $$1.blockPosition();
/*    */     
/* 37 */     for (int $$4 = 0; $$4 < 10; $$4++) {
/* 38 */       BlockPos $$5 = $$3.offset($$2.nextInt(20) - 10, $$2.nextInt(6) - 3, $$2.nextInt(20) - 10);
/*    */       
/* 40 */       if (hasNoBlocksAbove($$0, $$1, $$5)) {
/* 41 */         return Vec3.atBottomCenterOf((Vec3i)$$5);
/*    */       }
/*    */     } 
/* 44 */     return null;
/*    */   }
/*    */   
/*    */   public static boolean hasNoBlocksAbove(ServerLevel $$0, LivingEntity $$1, BlockPos $$2) {
/* 48 */     return ($$0.canSeeSky($$2) && $$0.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, $$2).getY() <= $$1.getY());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\MoveToSkySeeingSpot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */