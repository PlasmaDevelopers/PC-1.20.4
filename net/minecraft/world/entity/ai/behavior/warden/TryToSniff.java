/*    */ package net.minecraft.world.entity.ai.behavior.warden;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.Unit;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.BehaviorControl;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class TryToSniff {
/* 14 */   private static final IntProvider SNIFF_COOLDOWN = (IntProvider)UniformInt.of(100, 200);
/*    */   
/*    */   public static BehaviorControl<LivingEntity> create() {
/* 17 */     return (BehaviorControl<LivingEntity>)BehaviorBuilder.create($$0 -> $$0.group((App)$$0.registered(MemoryModuleType.IS_SNIFFING), (App)$$0.registered(MemoryModuleType.WALK_TARGET), (App)$$0.absent(MemoryModuleType.SNIFF_COOLDOWN), (App)$$0.present(MemoryModuleType.NEAREST_ATTACKABLE), (App)$$0.absent(MemoryModuleType.DISTURBANCE_LOCATION)).apply((Applicative)$$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\warden\TryToSniff.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */