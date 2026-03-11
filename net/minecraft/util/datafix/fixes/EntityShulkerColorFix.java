/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class EntityShulkerColorFix extends NamedEntityFix {
/*    */   public EntityShulkerColorFix(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1, "EntityShulkerColorFix", References.ENTITY, "minecraft:shulker");
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 14 */     if ($$0.get("Color").map(Dynamic::asNumber).result().isEmpty()) {
/* 15 */       return $$0.set("Color", $$0.createByte((byte)10));
/*    */     }
/* 17 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 22 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityShulkerColorFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */