/*    */ package net.minecraft.world.level.levelgen.structure;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ 
/*    */ public enum TerrainAdjustment
/*    */   implements StringRepresentable
/*    */ {
/* 10 */   NONE("none"),
/* 11 */   BURY("bury"),
/* 12 */   BEARD_THIN("beard_thin"),
/* 13 */   BEARD_BOX("beard_box"); public static final Codec<TerrainAdjustment> CODEC;
/*    */   
/*    */   static {
/* 16 */     CODEC = (Codec<TerrainAdjustment>)StringRepresentable.fromEnum(TerrainAdjustment::values);
/*    */   }
/*    */   private final String id;
/*    */   
/*    */   TerrainAdjustment(String $$0) {
/* 21 */     this.id = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 26 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\TerrainAdjustment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */