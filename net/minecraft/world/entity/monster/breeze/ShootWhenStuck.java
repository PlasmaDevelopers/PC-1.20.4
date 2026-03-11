/*    */ package net.minecraft.world.entity.monster.breeze;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.Unit;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ 
/*    */ public class ShootWhenStuck
/*    */   extends Behavior<Breeze>
/*    */ {
/*    */   public ShootWhenStuck() {
/* 16 */     super(Map.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.BREEZE_JUMP_INHALING, MemoryStatus.VALUE_ABSENT, MemoryModuleType.BREEZE_JUMP_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.BREEZE_SHOOT, MemoryStatus.VALUE_ABSENT));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, Breeze $$1) {
/* 27 */     return ($$1.isPassenger() || $$1.isInWater() || $$1.getEffect(MobEffects.LEVITATION) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Breeze $$1, long $$2) {
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, Breeze $$1, long $$2) {
/* 37 */     $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 60L);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\breeze\ShootWhenStuck.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */