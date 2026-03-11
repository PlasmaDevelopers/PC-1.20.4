/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.raid.Raid;
/*    */ import net.minecraft.world.entity.schedule.Activity;
/*    */ 
/*    */ public class SetRaidStatus {
/*    */   public static BehaviorControl<LivingEntity> create() {
/* 11 */     return BehaviorBuilder.create($$0 -> $$0.point(()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SetRaidStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */