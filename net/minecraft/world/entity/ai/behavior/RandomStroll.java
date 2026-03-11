/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
/*    */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class RandomStroll
/*    */ {
/*    */   private static final int MAX_XZ_DIST = 10;
/*    */   private static final int MAX_Y_DIST = 7;
/* 27 */   private static final int[][] SWIM_XY_DISTANCE_TIERS = new int[][] { { 1, 1 }, { 3, 3 }, { 5, 5 }, { 6, 5 }, { 7, 7 }, { 10, 7 } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static OneShot<PathfinderMob> stroll(float $$0) {
/* 37 */     return stroll($$0, true);
/*    */   }
/*    */   
/*    */   public static OneShot<PathfinderMob> stroll(float $$0, boolean $$1) {
/* 41 */     return strollFlyOrSwim($$0, $$0 -> LandRandomPos.getPos($$0, 10, 7), $$1 ? ($$0 -> true) : ($$0 -> !$$0.isInWaterOrBubble()));
/*    */   }
/*    */   
/*    */   public static BehaviorControl<PathfinderMob> stroll(float $$0, int $$1, int $$2) {
/* 45 */     return strollFlyOrSwim($$0, $$2 -> LandRandomPos.getPos($$2, $$0, $$1), $$0 -> true);
/*    */   }
/*    */   
/*    */   public static BehaviorControl<PathfinderMob> fly(float $$0) {
/* 49 */     return strollFlyOrSwim($$0, $$0 -> getTargetFlyPos($$0, 10, 7), $$0 -> true);
/*    */   }
/*    */   
/*    */   public static BehaviorControl<PathfinderMob> swim(float $$0) {
/* 53 */     return strollFlyOrSwim($$0, RandomStroll::getTargetSwimPos, Entity::isInWaterOrBubble);
/*    */   }
/*    */   
/*    */   private static OneShot<PathfinderMob> strollFlyOrSwim(float $$0, Function<PathfinderMob, Vec3> $$1, Predicate<PathfinderMob> $$2) {
/* 57 */     return BehaviorBuilder.create($$3 -> $$3.group((App)$$3.absent(MemoryModuleType.WALK_TARGET)).apply((Applicative)$$3, ()));
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
/*    */   @Nullable
/*    */   private static Vec3 getTargetSwimPos(PathfinderMob $$0) {
/* 72 */     Vec3 $$1 = null;
/* 73 */     Vec3 $$2 = null;
/*    */     
/* 75 */     for (int[] $$3 : SWIM_XY_DISTANCE_TIERS) {
/*    */       
/* 77 */       if ($$1 == null) {
/* 78 */         $$2 = BehaviorUtils.getRandomSwimmablePos($$0, $$3[0], $$3[1]);
/*    */       } else {
/* 80 */         $$2 = $$0.position().add($$0.position().vectorTo($$1).normalize().multiply($$3[0], $$3[1], $$3[0]));
/*    */       } 
/*    */       
/* 83 */       if ($$2 == null || $$0.level().getFluidState(BlockPos.containing((Position)$$2)).isEmpty()) {
/* 84 */         return $$1;
/*    */       }
/* 86 */       $$1 = $$2;
/*    */     } 
/*    */ 
/*    */     
/* 90 */     return $$2;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static Vec3 getTargetFlyPos(PathfinderMob $$0, int $$1, int $$2) {
/* 95 */     Vec3 $$3 = $$0.getViewVector(0.0F);
/*    */     
/* 97 */     return AirAndWaterRandomPos.getPos($$0, $$1, $$2, -2, $$3.x, $$3.z, 1.5707963705062866D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\RandomStroll.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */