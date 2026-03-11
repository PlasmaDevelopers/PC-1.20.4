/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class EntityPaintingItemFrameDirectionFix extends DataFix {
/* 12 */   private static final int[][] DIRECTIONS = new int[][] { { 0, 0, 1 }, { -1, 0, 0 }, { 0, 0, -1 }, { 1, 0, 0 } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityPaintingItemFrameDirectionFix(Schema $$0, boolean $$1) {
/* 20 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   private Dynamic<?> doFix(Dynamic<?> $$0, boolean $$1, boolean $$2) {
/* 24 */     if (($$1 || $$2) && $$0.get("Facing").asNumber().result().isEmpty()) {
/*    */       int $$5;
/* 26 */       if ($$0.get("Direction").asNumber().result().isPresent()) {
/* 27 */         int $$3 = $$0.get("Direction").asByte((byte)0) % DIRECTIONS.length;
/* 28 */         int[] $$4 = DIRECTIONS[$$3];
/*    */         
/* 30 */         $$0 = $$0.set("TileX", $$0.createInt($$0.get("TileX").asInt(0) + $$4[0]));
/* 31 */         $$0 = $$0.set("TileY", $$0.createInt($$0.get("TileY").asInt(0) + $$4[1]));
/* 32 */         $$0 = $$0.set("TileZ", $$0.createInt($$0.get("TileZ").asInt(0) + $$4[2]));
/*    */         
/* 34 */         $$0 = $$0.remove("Direction");
/*    */         
/* 36 */         if ($$2 && $$0.get("ItemRotation").asNumber().result().isPresent()) {
/* 37 */           $$0 = $$0.set("ItemRotation", $$0.createByte((byte)($$0.get("ItemRotation").asByte((byte)0) * 2)));
/*    */         }
/*    */       } else {
/* 40 */         $$5 = $$0.get("Dir").asByte((byte)0) % DIRECTIONS.length;
/* 41 */         $$0 = $$0.remove("Dir");
/*    */       } 
/* 43 */       $$0 = $$0.set("Facing", $$0.createByte((byte)$$5));
/*    */     } 
/*    */     
/* 46 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 51 */     Type<?> $$0 = getInputSchema().getChoiceType(References.ENTITY, "Painting");
/* 52 */     OpticFinder<?> $$1 = DSL.namedChoice("Painting", $$0);
/*    */     
/* 54 */     Type<?> $$2 = getInputSchema().getChoiceType(References.ENTITY, "ItemFrame");
/* 55 */     OpticFinder<?> $$3 = DSL.namedChoice("ItemFrame", $$2);
/*    */     
/* 57 */     Type<?> $$4 = getInputSchema().getType(References.ENTITY);
/*    */     
/* 59 */     TypeRewriteRule $$5 = fixTypeEverywhereTyped("EntityPaintingFix", $$4, $$2 -> $$2.updateTyped($$0, $$1, ()));
/*    */ 
/*    */     
/* 62 */     TypeRewriteRule $$6 = fixTypeEverywhereTyped("EntityItemFrameFix", $$4, $$2 -> $$2.updateTyped($$0, $$1, ()));
/*    */ 
/*    */ 
/*    */     
/* 66 */     return TypeRewriteRule.seq($$5, $$6);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityPaintingItemFrameDirectionFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */