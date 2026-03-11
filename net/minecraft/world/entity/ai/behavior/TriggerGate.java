/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.List;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ 
/*    */ 
/*    */ public class TriggerGate
/*    */ {
/*    */   public static <E extends LivingEntity> OneShot<E> triggerOneShuffled(List<Pair<? extends Trigger<? super E>, Integer>> $$0) {
/* 15 */     return triggerGate($$0, GateBehavior.OrderPolicy.SHUFFLED, GateBehavior.RunningPolicy.RUN_ONE);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <E extends LivingEntity> OneShot<E> triggerGate(List<Pair<? extends Trigger<? super E>, Integer>> $$0, GateBehavior.OrderPolicy $$1, GateBehavior.RunningPolicy $$2) {
/* 21 */     ShufflingList<Trigger<? super E>> $$3 = new ShufflingList<>();
/* 22 */     $$0.forEach($$1 -> $$0.add((Trigger)$$1.getFirst(), ((Integer)$$1.getSecond()).intValue()));
/*    */     
/* 24 */     return BehaviorBuilder.create($$3 -> $$3.point(()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\TriggerGate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */