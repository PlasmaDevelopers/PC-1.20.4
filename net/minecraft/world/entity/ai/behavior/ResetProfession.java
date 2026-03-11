/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.entity.npc.VillagerData;
/*    */ import net.minecraft.world.entity.npc.VillagerProfession;
/*    */ 
/*    */ public class ResetProfession {
/*    */   public static BehaviorControl<Villager> create() {
/* 15 */     return BehaviorBuilder.create($$0 -> $$0.group((App)$$0.absent(MemoryModuleType.JOB_SITE)).apply((Applicative)$$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\ResetProfession.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */