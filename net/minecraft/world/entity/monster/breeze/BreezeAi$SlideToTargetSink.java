/*     */ package net.minecraft.world.entity.monster.breeze;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlideToTargetSink
/*     */   extends MoveToTargetSink
/*     */ {
/*     */   @VisibleForTesting
/*     */   public SlideToTargetSink(int $$0, int $$1) {
/* 101 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Mob $$1, long $$2) {
/* 106 */     super.start($$0, $$1, $$2);
/* 107 */     $$1.playSound(SoundEvents.BREEZE_SLIDE);
/* 108 */     $$1.setPose(Pose.SLIDING);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Mob $$1, long $$2) {
/* 113 */     super.stop($$0, $$1, $$2);
/* 114 */     $$1.setPose(Pose.STANDING);
/*     */ 
/*     */     
/* 117 */     if ($$1.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET))
/* 118 */       $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 60L); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\breeze\BreezeAi$SlideToTargetSink.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */