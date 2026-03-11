/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public class GenerationStep
/*    */ {
/*    */   public enum Decoration
/*    */     implements StringRepresentable {
/* 10 */     RAW_GENERATION("raw_generation"),
/*    */     
/* 12 */     LAKES("lakes"),
/*    */     
/* 14 */     LOCAL_MODIFICATIONS("local_modifications"),
/*    */     
/* 16 */     UNDERGROUND_STRUCTURES("underground_structures"),
/*    */     
/* 18 */     SURFACE_STRUCTURES("surface_structures"),
/*    */     
/* 20 */     STRONGHOLDS("strongholds"),
/*    */     
/* 22 */     UNDERGROUND_ORES("underground_ores"),
/*    */     
/* 24 */     UNDERGROUND_DECORATION("underground_decoration"),
/*    */     
/* 26 */     FLUID_SPRINGS("fluid_springs"),
/*    */     
/* 28 */     VEGETAL_DECORATION("vegetal_decoration"),
/*    */     
/* 30 */     TOP_LAYER_MODIFICATION("top_layer_modification");
/*    */ 
/*    */     
/* 33 */     public static final Codec<Decoration> CODEC = (Codec<Decoration>)StringRepresentable.fromEnum(Decoration::values);
/*    */     
/*    */     private final String name;
/*    */     
/*    */     Decoration(String $$0) {
/* 38 */       this.name = $$0;
/*    */     } static {
/*    */     
/*    */     } public String getName() {
/* 42 */       return this.name;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getSerializedName() {
/* 47 */       return this.name;
/*    */     }
/*    */   }
/*    */   
/*    */   public enum Carving
/*    */     implements StringRepresentable {
/* 53 */     AIR("air"),
/* 54 */     LIQUID("liquid");
/*    */ 
/*    */     
/* 57 */     public static final Codec<Carving> CODEC = (Codec<Carving>)StringRepresentable.fromEnum(Carving::values); private final String name;
/*    */     static {
/*    */     
/*    */     }
/*    */     Carving(String $$0) {
/* 62 */       this.name = $$0;
/*    */     }
/*    */     
/*    */     public String getName() {
/* 66 */       return this.name;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getSerializedName() {
/* 71 */       return this.name;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\GenerationStep.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */