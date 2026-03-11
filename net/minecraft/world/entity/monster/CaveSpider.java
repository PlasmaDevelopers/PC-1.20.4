/*    */ package net.minecraft.world.entity.monster;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.Difficulty;
/*    */ import net.minecraft.world.DifficultyInstance;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityDimensions;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.MobSpawnType;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.entity.SpawnGroupData;
/*    */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*    */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public class CaveSpider
/*    */   extends Spider
/*    */ {
/*    */   public CaveSpider(EntityType<? extends CaveSpider> $$0, Level $$1) {
/* 26 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public static AttributeSupplier.Builder createCaveSpider() {
/* 30 */     return Spider.createAttributes()
/* 31 */       .add(Attributes.MAX_HEALTH, 12.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doHurtTarget(Entity $$0) {
/* 36 */     if (super.doHurtTarget($$0)) {
/* 37 */       if ($$0 instanceof LivingEntity) {
/* 38 */         int $$1 = 0;
/* 39 */         if (level().getDifficulty() == Difficulty.NORMAL) {
/* 40 */           $$1 = 7;
/* 41 */         } else if (level().getDifficulty() == Difficulty.HARD) {
/* 42 */           $$1 = 15;
/*    */         } 
/*    */         
/* 45 */         if ($$1 > 0) {
/* 46 */           ((LivingEntity)$$0).addEffect(new MobEffectInstance(MobEffects.POISON, $$1 * 20, 0), (Entity)this);
/*    */         }
/*    */       } 
/*    */       
/* 50 */       return true;
/*    */     } 
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 59 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 64 */     return 0.45F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 69 */     return new Vector3f(0.0F, $$1.height, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float ridingOffset(Entity $$0) {
/* 74 */     return ($$0.getBbWidth() <= getBbWidth()) ? -0.21875F : 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\CaveSpider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */