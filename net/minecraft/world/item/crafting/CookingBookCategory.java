/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum CookingBookCategory implements StringRepresentable {
/*  6 */   FOOD("food"),
/*  7 */   BLOCKS("blocks"),
/*  8 */   MISC("misc"); public static final StringRepresentable.EnumCodec<CookingBookCategory> CODEC;
/*    */   
/*    */   static {
/* 11 */     CODEC = StringRepresentable.fromEnum(CookingBookCategory::values);
/*    */   }
/*    */   private final String name;
/*    */   
/*    */   CookingBookCategory(String $$0) {
/* 16 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 21 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\CookingBookCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */