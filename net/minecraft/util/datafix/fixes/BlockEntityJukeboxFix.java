/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class BlockEntityJukeboxFix extends NamedEntityFix {
/*    */   public BlockEntityJukeboxFix(Schema $$0, boolean $$1) {
/* 12 */     super($$0, $$1, "BlockEntityJukeboxFix", References.BLOCK_ENTITY, "minecraft:jukebox");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 17 */     Type<?> $$1 = getInputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:jukebox");
/* 18 */     Type<?> $$2 = $$1.findFieldType("RecordItem");
/* 19 */     OpticFinder<?> $$3 = DSL.fieldFinder("RecordItem", $$2);
/* 20 */     Dynamic<?> $$4 = (Dynamic)$$0.get(DSL.remainderFinder());
/* 21 */     int $$5 = $$4.get("Record").asInt(0);
/* 22 */     if ($$5 > 0) {
/* 23 */       $$4.remove("Record");
/*    */       
/* 25 */       String $$6 = ItemStackTheFlatteningFix.updateItem(ItemIdFix.getItem($$5), 0);
/* 26 */       if ($$6 != null) {
/* 27 */         Dynamic<?> $$7 = $$4.emptyMap();
/* 28 */         $$7 = $$7.set("id", $$7.createString($$6));
/* 29 */         $$7 = $$7.set("Count", $$7.createByte((byte)1));
/* 30 */         return $$0.set($$3, (Typed)((Pair)$$2.readTyped($$7).result().orElseThrow(() -> new IllegalStateException("Could not create record item stack."))).getFirst()).set(DSL.remainderFinder(), $$4);
/*    */       } 
/*    */     } 
/* 33 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntityJukeboxFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */