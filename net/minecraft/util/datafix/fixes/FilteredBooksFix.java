/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class FilteredBooksFix extends ItemStackTagFix {
/*    */   public FilteredBooksFix(Schema $$0) {
/*  8 */     super($$0, "Remove filtered text from books", $$0 -> ($$0.equals("minecraft:writable_book") || $$0.equals("minecraft:written_book")));
/*    */   }
/*    */ 
/*    */   
/*    */   protected <T> Dynamic<T> fixItemStackTag(Dynamic<T> $$0) {
/* 13 */     return $$0.remove("filtered_title").remove("filtered_pages");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\FilteredBooksFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */