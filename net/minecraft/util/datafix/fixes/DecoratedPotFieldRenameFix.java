/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ 
/*    */ public class DecoratedPotFieldRenameFix
/*    */   extends DataFix {
/*    */   private static final String DECORATED_POT_ID = "minecraft:decorated_pot";
/*    */   
/*    */   public DecoratedPotFieldRenameFix(Schema $$0) {
/* 13 */     super($$0, true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 18 */     Type<?> $$0 = getInputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:decorated_pot");
/* 19 */     Type<?> $$1 = getOutputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:decorated_pot");
/*    */     
/* 21 */     return convertUnchecked("DecoratedPotFieldRenameFix", $$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\DecoratedPotFieldRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */