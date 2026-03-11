/*    */ package net.minecraft.world.damagesource;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum DamageScaling implements StringRepresentable {
/*  7 */   NEVER("never"),
/*  8 */   WHEN_CAUSED_BY_LIVING_NON_PLAYER("when_caused_by_living_non_player"),
/*  9 */   ALWAYS("always"); public static final Codec<DamageScaling> CODEC;
/*    */   
/*    */   static {
/* 12 */     CODEC = (Codec<DamageScaling>)StringRepresentable.fromEnum(DamageScaling::values);
/*    */   }
/*    */   private final String id;
/*    */   
/*    */   DamageScaling(String $$0) {
/* 17 */     this.id = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 22 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\damagesource\DamageScaling.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */