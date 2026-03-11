/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public abstract class SimpleEntityRenameFix extends EntityRenameFix {
/*    */   public SimpleEntityRenameFix(String $$0, Schema $$1, boolean $$2) {
/* 11 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Pair<String, Typed<?>> fix(String $$0, Typed<?> $$1) {
/* 16 */     Pair<String, Dynamic<?>> $$2 = getNewNameAndTag($$0, (Dynamic)$$1.getOrCreate(DSL.remainderFinder()));
/* 17 */     return Pair.of($$2.getFirst(), $$1.set(DSL.remainderFinder(), $$2.getSecond()));
/*    */   }
/*    */   
/*    */   protected abstract Pair<String, Dynamic<?>> getNewNameAndTag(String paramString, Dynamic<?> paramDynamic);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\SimpleEntityRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */