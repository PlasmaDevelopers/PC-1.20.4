/*    */ package net.minecraft.world.level.levelgen;
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
/*    */ 
/*    */ 
/*    */ public enum Carving
/*    */   implements StringRepresentable
/*    */ {
/* 53 */   AIR("air"),
/* 54 */   LIQUID("liquid"); public static final Codec<Carving> CODEC;
/*    */   
/*    */   static {
/* 57 */     CODEC = (Codec<Carving>)StringRepresentable.fromEnum(Carving::values);
/*    */   }
/*    */   private final String name;
/*    */   
/*    */   Carving(String $$0) {
/* 62 */     this.name = $$0;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 66 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 71 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\GenerationStep$Carving.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */