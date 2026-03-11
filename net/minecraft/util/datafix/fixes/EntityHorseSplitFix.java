/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ public class EntityHorseSplitFix
/*    */   extends EntityRenameFix {
/*    */   public EntityHorseSplitFix(Schema $$0, boolean $$1) {
/* 15 */     super("EntityHorseSplitFix", $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Pair<String, Typed<?>> fix(String $$0, Typed<?> $$1) {
/* 20 */     Dynamic<?> $$2 = (Dynamic)$$1.get(DSL.remainderFinder());
/* 21 */     if (Objects.equals("EntityHorse", $$0)) {
/*    */       
/* 23 */       int $$3 = $$2.get("Type").asInt(0);
/* 24 */       switch ($$3) { default: 
/*    */         case 1: 
/*    */         case 2: 
/*    */         case 3: 
/*    */         case 4:
/* 29 */           break; }  String $$4 = "SkeletonHorse";
/*    */       
/* 31 */       $$2.remove("Type");
/*    */       
/* 33 */       Type<?> $$5 = (Type)getOutputSchema().findChoiceType(References.ENTITY).types().get($$4);
/* 34 */       return Pair.of($$4, Util.writeAndReadTypedOrThrow($$1, $$5, $$0 -> $$0));
/*    */     } 
/* 36 */     return Pair.of($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityHorseSplitFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */