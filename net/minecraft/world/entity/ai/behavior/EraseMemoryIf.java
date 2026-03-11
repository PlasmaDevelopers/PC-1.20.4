/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class EraseMemoryIf {
/*    */   public static <E extends LivingEntity> BehaviorControl<E> create(Predicate<E> $$0, MemoryModuleType<?> $$1) {
/* 11 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.present($$0)).apply((Applicative)$$2, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\EraseMemoryIf.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */