/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.util.valueproviders.UniformInt;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class CopyMemoryWithExpiry {
/*    */   public static <E extends LivingEntity, T> BehaviorControl<E> create(Predicate<E> $$0, MemoryModuleType<? extends T> $$1, MemoryModuleType<T> $$2, UniformInt $$3) {
/* 12 */     return BehaviorBuilder.create($$4 -> $$4.group((App)$$4.present($$0), (App)$$4.absent($$1)).apply((Applicative)$$4, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\CopyMemoryWithExpiry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */