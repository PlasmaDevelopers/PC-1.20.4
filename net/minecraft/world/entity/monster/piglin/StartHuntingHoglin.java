/*    */ package net.minecraft.world.entity.monster.piglin;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.monster.hoglin.Hoglin;
/*    */ 
/*    */ public class StartHuntingHoglin {
/*    */   public static OneShot<Piglin> create() {
/* 10 */     return BehaviorBuilder.create($$0 -> $$0.group((App)$$0.present(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN), (App)$$0.absent(MemoryModuleType.ANGRY_AT), (App)$$0.absent(MemoryModuleType.HUNTED_RECENTLY), (App)$$0.registered(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS)).apply((Applicative)$$0, ()));
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean hasHuntedRecently(AbstractPiglin $$0) {
/* 33 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.HUNTED_RECENTLY);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\StartHuntingHoglin.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */