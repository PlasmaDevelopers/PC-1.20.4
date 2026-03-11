/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class BlockEntityKeepPacked extends NamedEntityFix {
/*    */   public BlockEntityKeepPacked(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1, "BlockEntityKeepPacked", References.BLOCK_ENTITY, "DUMMY");
/*    */   }
/*    */   
/*    */   private static Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 14 */     return $$0.set("keepPacked", $$0.createBoolean(true));
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 19 */     return $$0.update(DSL.remainderFinder(), BlockEntityKeepPacked::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntityKeepPacked.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */