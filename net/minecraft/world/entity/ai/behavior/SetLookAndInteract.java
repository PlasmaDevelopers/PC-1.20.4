/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class SetLookAndInteract {
/*    */   public static BehaviorControl<LivingEntity> create(EntityType<?> $$0, int $$1) {
/* 12 */     int $$2 = $$1 * $$1;
/* 13 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.registered(MemoryModuleType.LOOK_TARGET), (App)$$2.absent(MemoryModuleType.INTERACTION_TARGET), (App)$$2.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply((Applicative)$$2, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SetLookAndInteract.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */