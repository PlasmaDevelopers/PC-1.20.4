/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class BlockEntityShulkerBoxColorFix extends NamedEntityFix {
/*    */   public BlockEntityShulkerBoxColorFix(Schema $$0, boolean $$1) {
/*  9 */     super($$0, $$1, "BlockEntityShulkerBoxColorFix", References.BLOCK_ENTITY, "minecraft:shulker_box");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 14 */     return $$0.update(DSL.remainderFinder(), $$0 -> $$0.remove("Color"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntityShulkerBoxColorFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */