/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class BlockEntityBlockStateFix extends NamedEntityFix {
/*    */   public BlockEntityBlockStateFix(Schema $$0, boolean $$1) {
/* 12 */     super($$0, $$1, "BlockEntityBlockStateFix", References.BLOCK_ENTITY, "minecraft:piston");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 17 */     Type<?> $$1 = getOutputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:piston");
/*    */     
/* 19 */     Type<?> $$2 = $$1.findFieldType("blockState");
/* 20 */     OpticFinder<?> $$3 = DSL.fieldFinder("blockState", $$2);
/* 21 */     Dynamic<?> $$4 = (Dynamic)$$0.get(DSL.remainderFinder());
/*    */     
/* 23 */     int $$5 = $$4.get("blockId").asInt(0);
/* 24 */     $$4 = $$4.remove("blockId");
/* 25 */     int $$6 = $$4.get("blockData").asInt(0) & 0xF;
/* 26 */     $$4 = $$4.remove("blockData");
/*    */     
/* 28 */     Dynamic<?> $$7 = BlockStateData.getTag($$5 << 4 | $$6);
/* 29 */     Typed<?> $$8 = (Typed)$$1.pointTyped($$0.getOps()).orElseThrow(() -> new IllegalStateException("Could not create new piston block entity."));
/* 30 */     return $$8.set(DSL.remainderFinder(), $$4).set($$3, (Typed)((Pair)$$2.readTyped($$7).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created block state tag."))).getFirst());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntityBlockStateFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */