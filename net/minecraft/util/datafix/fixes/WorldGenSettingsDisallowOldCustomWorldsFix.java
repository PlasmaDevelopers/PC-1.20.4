/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class WorldGenSettingsDisallowOldCustomWorldsFix extends DataFix {
/*    */   public WorldGenSettingsDisallowOldCustomWorldsFix(Schema $$0) {
/* 11 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 16 */     Type<?> $$0 = getInputSchema().getType(References.WORLD_GEN_SETTINGS);
/* 17 */     OpticFinder<?> $$1 = $$0.findField("dimensions");
/*    */     
/* 19 */     return fixTypeEverywhereTyped("WorldGenSettingsDisallowOldCustomWorldsFix_" + getOutputSchema().getVersionKey(), $$0, $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\WorldGenSettingsDisallowOldCustomWorldsFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */