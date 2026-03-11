/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.TaggedChoice;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class EntityMinecartIdentifiersFix extends DataFix {
/*    */   public EntityMinecartIdentifiersFix(Schema $$0, boolean $$1) {
/* 18 */     super($$0, $$1);
/*    */   }
/*    */   
/* 21 */   private static final List<String> MINECART_BY_ID = Lists.newArrayList((Object[])new String[] { "MinecartRideable", "MinecartChest", "MinecartFurnace" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 30 */     TaggedChoice.TaggedChoiceType<String> $$0 = getInputSchema().findChoiceType(References.ENTITY);
/* 31 */     TaggedChoice.TaggedChoiceType<String> $$1 = getOutputSchema().findChoiceType(References.ENTITY);
/*    */     
/* 33 */     return fixTypeEverywhere("EntityMinecartIdentifiersFix", (Type)$$0, (Type)$$1, $$2 -> ());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityMinecartIdentifiersFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */