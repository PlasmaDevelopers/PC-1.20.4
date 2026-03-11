/*    */ package net.minecraft.world.entity.monster;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.effect.MobEffectUtil;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityDimensions;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*    */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*    */ import net.minecraft.world.level.Level;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public class ElderGuardian
/*    */   extends Guardian {
/* 24 */   public static final float ELDER_SIZE_SCALE = EntityType.ELDER_GUARDIAN.getWidth() / EntityType.GUARDIAN.getWidth();
/*    */   
/*    */   private static final int EFFECT_INTERVAL = 1200;
/*    */   private static final int EFFECT_RADIUS = 50;
/*    */   private static final int EFFECT_DURATION = 6000;
/*    */   private static final int EFFECT_AMPLIFIER = 2;
/*    */   private static final int EFFECT_DISPLAY_LIMIT = 1200;
/*    */   
/*    */   public ElderGuardian(EntityType<? extends ElderGuardian> $$0, Level $$1) {
/* 33 */     super((EntityType)$$0, $$1);
/*    */     
/* 35 */     setPersistenceRequired();
/*    */ 
/*    */     
/* 38 */     if (this.randomStrollGoal != null) {
/* 39 */       this.randomStrollGoal.setInterval(400);
/*    */     }
/*    */   }
/*    */   
/*    */   public static AttributeSupplier.Builder createAttributes() {
/* 44 */     return Guardian.createAttributes()
/* 45 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D)
/* 46 */       .add(Attributes.ATTACK_DAMAGE, 8.0D)
/* 47 */       .add(Attributes.MAX_HEALTH, 80.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAttackDuration() {
/* 52 */     return 60;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 57 */     return isInWaterOrBubble() ? SoundEvents.ELDER_GUARDIAN_AMBIENT : SoundEvents.ELDER_GUARDIAN_AMBIENT_LAND;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 62 */     return isInWaterOrBubble() ? SoundEvents.ELDER_GUARDIAN_HURT : SoundEvents.ELDER_GUARDIAN_HURT_LAND;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 67 */     return isInWaterOrBubble() ? SoundEvents.ELDER_GUARDIAN_DEATH : SoundEvents.ELDER_GUARDIAN_DEATH_LAND;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getFlopSound() {
/* 72 */     return SoundEvents.ELDER_GUARDIAN_FLOP;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void customServerAiStep() {
/* 77 */     super.customServerAiStep();
/*    */ 
/*    */     
/* 80 */     if ((this.tickCount + getId()) % 1200 == 0) {
/* 81 */       MobEffectInstance $$0 = new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 6000, 2);
/* 82 */       List<ServerPlayer> $$1 = MobEffectUtil.addEffectToPlayersAround((ServerLevel)level(), (Entity)this, position(), 50.0D, $$0, 1200);
/* 83 */       $$1.forEach($$0 -> $$0.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.GUARDIAN_ELDER_EFFECT, isSilent() ? 0.0F : 1.0F)));
/*    */     } 
/*    */ 
/*    */     
/* 87 */     if (!hasRestriction()) {
/* 88 */       restrictTo(blockPosition(), 16);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 94 */     return new Vector3f(0.0F, $$1.height + 0.353125F * $$2, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\ElderGuardian.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */