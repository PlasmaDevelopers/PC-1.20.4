/*    */ package net.minecraft.world.damagesource;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum DamageEffects implements StringRepresentable {
/*    */   public static final Codec<DamageEffects> CODEC;
/*  9 */   HURT("hurt", SoundEvents.PLAYER_HURT),
/* 10 */   THORNS("thorns", SoundEvents.THORNS_HIT),
/* 11 */   DROWNING("drowning", SoundEvents.PLAYER_HURT_DROWN),
/* 12 */   BURNING("burning", SoundEvents.PLAYER_HURT_ON_FIRE),
/* 13 */   POKING("poking", SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH),
/* 14 */   FREEZING("freezing", SoundEvents.PLAYER_HURT_FREEZE);
/*    */   
/*    */   static {
/* 17 */     CODEC = (Codec<DamageEffects>)StringRepresentable.fromEnum(DamageEffects::values);
/*    */   }
/*    */   private final String id;
/*    */   private final SoundEvent sound;
/*    */   
/*    */   DamageEffects(String $$0, SoundEvent $$1) {
/* 23 */     this.id = $$0;
/* 24 */     this.sound = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 29 */     return this.id;
/*    */   }
/*    */   
/*    */   public SoundEvent sound() {
/* 33 */     return this.sound;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\damagesource\DamageEffects.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */