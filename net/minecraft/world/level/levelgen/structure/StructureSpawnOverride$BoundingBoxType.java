/*    */ package net.minecraft.world.level.levelgen.structure;
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
/*    */ public enum BoundingBoxType
/*    */   implements StringRepresentable
/*    */ {
/* 19 */   PIECE("piece"),
/* 20 */   STRUCTURE("full"); public static final Codec<BoundingBoxType> CODEC;
/*    */   static {
/* 22 */     CODEC = (Codec<BoundingBoxType>)StringRepresentable.fromEnum(BoundingBoxType::values);
/*    */   }
/*    */   private final String id;
/*    */   
/*    */   BoundingBoxType(String $$0) {
/* 27 */     this.id = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 32 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\StructureSpawnOverride$BoundingBoxType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */