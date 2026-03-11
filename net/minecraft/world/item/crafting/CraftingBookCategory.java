/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum CraftingBookCategory implements StringRepresentable {
/*  7 */   BUILDING("building"),
/*  8 */   REDSTONE("redstone"),
/*  9 */   EQUIPMENT("equipment"),
/* 10 */   MISC("misc"); public static final Codec<CraftingBookCategory> CODEC;
/*    */   
/*    */   static {
/* 13 */     CODEC = (Codec<CraftingBookCategory>)StringRepresentable.fromEnum(CraftingBookCategory::values);
/*    */   }
/*    */   private final String name;
/*    */   
/*    */   CraftingBookCategory(String $$0) {
/* 18 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 23 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\CraftingBookCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */