/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class ColorlessShulkerEntityFix extends NamedEntityFix {
/*    */   public ColorlessShulkerEntityFix(Schema $$0, boolean $$1) {
/*  9 */     super($$0, $$1, "Colorless shulker entity fix", References.ENTITY, "minecraft:shulker");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 14 */     return $$0.update(DSL.remainderFinder(), $$0 -> ($$0.get("Color").asInt(0) == 10) ? $$0.set("Color", $$0.createByte((byte)16)) : $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ColorlessShulkerEntityFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */