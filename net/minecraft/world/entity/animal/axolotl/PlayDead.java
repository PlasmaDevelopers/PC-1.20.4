/*    */ package net.minecraft.world.entity.animal.axolotl;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ 
/*    */ public class PlayDead extends Behavior<Axolotl> {
/*    */   public PlayDead() {
/* 15 */     super((Map)ImmutableMap.of(MemoryModuleType.PLAY_DEAD_TICKS, MemoryStatus.VALUE_PRESENT, MemoryModuleType.HURT_BY_ENTITY, MemoryStatus.VALUE_PRESENT), 200);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, Axolotl $$1) {
/* 24 */     return $$1.isInWaterOrBubble();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Axolotl $$1, long $$2) {
/* 29 */     return ($$1.isInWaterOrBubble() && $$1.getBrain().hasMemoryValue(MemoryModuleType.PLAY_DEAD_TICKS));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, Axolotl $$1, long $$2) {
/* 34 */     Brain<Axolotl> $$3 = $$1.getBrain();
/*    */     
/* 36 */     $$3.eraseMemory(MemoryModuleType.WALK_TARGET);
/* 37 */     $$3.eraseMemory(MemoryModuleType.LOOK_TARGET);
/*    */     
/* 39 */     $$1.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\axolotl\PlayDead.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */