/*    */ package net.minecraft.world.damagesource;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum DeathMessageType implements StringRepresentable {
/*  7 */   DEFAULT("default"),
/*  8 */   FALL_VARIANTS("fall_variants"),
/*  9 */   INTENTIONAL_GAME_DESIGN("intentional_game_design"); public static final Codec<DeathMessageType> CODEC;
/*    */   
/*    */   static {
/* 12 */     CODEC = (Codec<DeathMessageType>)StringRepresentable.fromEnum(DeathMessageType::values);
/*    */   }
/*    */   private final String id;
/*    */   
/*    */   DeathMessageType(String $$0) {
/* 17 */     this.id = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 22 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\damagesource\DeathMessageType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */