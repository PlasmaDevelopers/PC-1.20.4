/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class FilteredSignsFix extends NamedEntityFix {
/*    */   public FilteredSignsFix(Schema $$0) {
/*  9 */     super($$0, false, "Remove filtered text from signs", References.BLOCK_ENTITY, "minecraft:sign");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 14 */     return $$0.update(DSL.remainderFinder(), $$0 -> $$0.remove("FilteredText1").remove("FilteredText2").remove("FilteredText3").remove("FilteredText4"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\FilteredSignsFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */