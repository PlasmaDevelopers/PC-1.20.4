/*    */ package net.minecraft.world.level.levelgen.structure.placement;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum RandomSpreadType implements StringRepresentable {
/*  8 */   LINEAR("linear"),
/*  9 */   TRIANGULAR("triangular"); public static final Codec<RandomSpreadType> CODEC; private final String id;
/*    */   static {
/* 11 */     CODEC = (Codec<RandomSpreadType>)StringRepresentable.fromEnum(RandomSpreadType::values);
/*    */   }
/*    */ 
/*    */   
/*    */   RandomSpreadType(String $$0) {
/* 16 */     this.id = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 21 */     return this.id;
/*    */   }
/*    */   
/*    */   public int evaluate(RandomSource $$0, int $$1) {
/* 25 */     switch (this) { default: throw new IncompatibleClassChangeError();case LINEAR: case TRIANGULAR: break; }  return (
/*    */       
/* 27 */       $$0.nextInt($$1) + $$0.nextInt($$1)) / 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\placement\RandomSpreadType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */