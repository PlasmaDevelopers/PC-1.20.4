/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.datafix.ComponentDataFixUtils;
/*    */ 
/*    */ public class TeamDisplayNameFix
/*    */   extends DataFix {
/*    */   public TeamDisplayNameFix(Schema $$0, boolean $$1) {
/* 18 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 23 */     Type<Pair<String, Dynamic<?>>> $$0 = DSL.named(References.TEAM.typeName(), DSL.remainderType());
/*    */     
/* 25 */     if (!Objects.equals($$0, getInputSchema().getType(References.TEAM))) {
/* 26 */       throw new IllegalStateException("Team type is not what was expected.");
/*    */     }
/*    */     
/* 29 */     return fixTypeEverywhere("TeamDisplayNameFix", $$0, $$0 -> ());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\TeamDisplayNameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */