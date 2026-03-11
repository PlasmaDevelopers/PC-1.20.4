/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Type
/*    */   implements StringRepresentable
/*    */ {
/* 51 */   WARM("warm"),
/* 52 */   COLD("cold"); public static final Codec<Type> CODEC;
/*    */   
/*    */   static {
/* 55 */     CODEC = (Codec<Type>)StringRepresentable.fromEnum(Type::values);
/*    */   }
/*    */   private final String name;
/*    */   
/*    */   Type(String $$0) {
/* 60 */     this.name = $$0;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 64 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 69 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\OceanRuinStructure$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */