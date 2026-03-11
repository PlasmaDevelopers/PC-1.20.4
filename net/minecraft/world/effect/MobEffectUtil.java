/*    */ package net.minecraft.world.effect;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.StringUtil;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public final class MobEffectUtil {
/*    */   public static Component formatDuration(MobEffectInstance $$0, float $$1, float $$2) {
/* 17 */     if ($$0.isInfiniteDuration()) {
/* 18 */       return (Component)Component.translatable("effect.duration.infinite");
/*    */     }
/* 20 */     int $$3 = Mth.floor($$0.getDuration() * $$1);
/* 21 */     return (Component)Component.literal(StringUtil.formatTickDuration($$3, $$2));
/*    */   }
/*    */   
/*    */   public static boolean hasDigSpeed(LivingEntity $$0) {
/* 25 */     return ($$0.hasEffect(MobEffects.DIG_SPEED) || $$0.hasEffect(MobEffects.CONDUIT_POWER));
/*    */   }
/*    */   
/*    */   public static int getDigSpeedAmplification(LivingEntity $$0) {
/* 29 */     int $$1 = 0, $$2 = 0;
/* 30 */     if ($$0.hasEffect(MobEffects.DIG_SPEED)) {
/* 31 */       $$1 = $$0.getEffect(MobEffects.DIG_SPEED).getAmplifier();
/*    */     }
/* 33 */     if ($$0.hasEffect(MobEffects.CONDUIT_POWER)) {
/* 34 */       $$2 = $$0.getEffect(MobEffects.CONDUIT_POWER).getAmplifier();
/*    */     }
/*    */     
/* 37 */     return Math.max($$1, $$2);
/*    */   }
/*    */   
/*    */   public static boolean hasWaterBreathing(LivingEntity $$0) {
/* 41 */     return ($$0.hasEffect(MobEffects.WATER_BREATHING) || $$0.hasEffect(MobEffects.CONDUIT_POWER));
/*    */   }
/*    */   
/*    */   public static List<ServerPlayer> addEffectToPlayersAround(ServerLevel $$0, @Nullable Entity $$1, Vec3 $$2, double $$3, MobEffectInstance $$4, int $$5) {
/* 45 */     MobEffect $$6 = $$4.getEffect();
/* 46 */     List<ServerPlayer> $$7 = $$0.getPlayers($$6 -> 
/* 47 */         ($$6.gameMode.isSurvival() && ($$0 == null || !$$0.isAlliedTo((Entity)$$6)) && $$1.closerThan((Position)$$6.position(), $$2) && (!$$6.hasEffect($$3) || $$6.getEffect($$3).getAmplifier() < $$4.getAmplifier() || $$6.getEffect($$3).endsWithin($$5 - 1))));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     $$7.forEach($$2 -> $$2.addEffect(new MobEffectInstance($$0), $$1));
/*    */     
/* 59 */     return $$7;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\MobEffectUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */