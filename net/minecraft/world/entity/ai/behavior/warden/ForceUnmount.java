/*    */ package net.minecraft.world.entity.ai.behavior.warden;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*    */ 
/*    */ public class ForceUnmount extends Behavior<LivingEntity> {
/*    */   public ForceUnmount() {
/* 10 */     super((Map)ImmutableMap.of());
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, LivingEntity $$1) {
/* 15 */     return $$1.isPassenger();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, LivingEntity $$1, long $$2) {
/* 20 */     $$1.unRide();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\warden\ForceUnmount.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */