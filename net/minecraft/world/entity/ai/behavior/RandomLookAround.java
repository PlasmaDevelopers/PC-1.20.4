/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class RandomLookAround
/*    */   extends Behavior<Mob>
/*    */ {
/*    */   private final IntProvider interval;
/*    */   private final float maxYaw;
/*    */   private final float minPitch;
/*    */   private final float pitchRange;
/*    */   
/*    */   public RandomLookAround(IntProvider $$0, float $$1, float $$2, float $$3) {
/* 24 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.GAZE_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT));
/* 25 */     if ($$2 > $$3) {
/* 26 */       throw new IllegalArgumentException("Minimum pitch is larger than maximum pitch! " + $$2 + " > " + $$3);
/*    */     }
/* 28 */     this.interval = $$0;
/* 29 */     this.maxYaw = $$1;
/* 30 */     this.minPitch = $$2;
/* 31 */     this.pitchRange = $$3 - $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, Mob $$1, long $$2) {
/* 36 */     RandomSource $$3 = $$1.getRandom();
/*    */     
/* 38 */     float $$4 = Mth.clamp($$3.nextFloat() * this.pitchRange + this.minPitch, -90.0F, 90.0F);
/* 39 */     float $$5 = Mth.wrapDegrees($$1.getYRot() + 2.0F * $$3.nextFloat() * this.maxYaw - this.maxYaw);
/* 40 */     Vec3 $$6 = Vec3.directionFromRotation($$4, $$5);
/*    */     
/* 42 */     $$1.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker($$1.getEyePosition().add($$6)));
/* 43 */     $$1.getBrain().setMemory(MemoryModuleType.GAZE_COOLDOWN_TICKS, Integer.valueOf(this.interval.sample($$3)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\RandomLookAround.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */