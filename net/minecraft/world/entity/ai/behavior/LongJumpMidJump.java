/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.util.valueproviders.UniformInt;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ 
/*    */ public class LongJumpMidJump
/*    */   extends Behavior<Mob> {
/*    */   public static final int TIME_OUT_DURATION = 100;
/*    */   
/*    */   public LongJumpMidJump(UniformInt $$0, SoundEvent $$1) {
/* 21 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_PRESENT), 100);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 26 */     this.timeBetweenLongJumps = $$0;
/* 27 */     this.landingSound = $$1;
/*    */   }
/*    */   private final UniformInt timeBetweenLongJumps; private final SoundEvent landingSound;
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Mob $$1, long $$2) {
/* 32 */     return !$$1.onGround();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, Mob $$1, long $$2) {
/* 37 */     $$1.setDiscardFriction(true);
/* 38 */     $$1.setPose(Pose.LONG_JUMPING);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, Mob $$1, long $$2) {
/* 43 */     if ($$1.onGround()) {
/* 44 */       $$1.setDeltaMovement($$1.getDeltaMovement().multiply(0.10000000149011612D, 1.0D, 0.10000000149011612D));
/* 45 */       $$0.playSound(null, (Entity)$$1, this.landingSound, SoundSource.NEUTRAL, 2.0F, 1.0F);
/*    */     } 
/*    */     
/* 48 */     $$1.setDiscardFriction(false);
/* 49 */     $$1.setPose(Pose.STANDING);
/*    */     
/* 51 */     $$1.getBrain().eraseMemory(MemoryModuleType.LONG_JUMP_MID_JUMP);
/* 52 */     $$1.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, Integer.valueOf(this.timeBetweenLongJumps.sample($$0.random)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\LongJumpMidJump.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */