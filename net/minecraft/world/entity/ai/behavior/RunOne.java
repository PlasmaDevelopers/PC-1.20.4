/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RunOne<E extends LivingEntity>
/*    */   extends GateBehavior<E>
/*    */ {
/*    */   public RunOne(List<Pair<? extends BehaviorControl<? super E>, Integer>> $$0) {
/* 19 */     this(
/* 20 */         (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RunOne(Map<MemoryModuleType<?>, MemoryStatus> $$0, List<Pair<? extends BehaviorControl<? super E>, Integer>> $$1) {
/* 26 */     super($$0, 
/*    */         
/* 28 */         (Set<MemoryModuleType<?>>)ImmutableSet.of(), GateBehavior.OrderPolicy.SHUFFLED, GateBehavior.RunningPolicy.RUN_ONE, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\RunOne.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */