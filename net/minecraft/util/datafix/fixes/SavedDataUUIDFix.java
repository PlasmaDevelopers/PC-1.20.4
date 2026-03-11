/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class SavedDataUUIDFix extends AbstractUUIDFix {
/* 10 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public SavedDataUUIDFix(Schema $$0) {
/* 13 */     super($$0, References.SAVED_DATA_RAIDS);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 18 */     return fixTypeEverywhereTyped("SavedDataUUIDFix", getInputSchema().getType(this.typeReference), $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\SavedDataUUIDFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */