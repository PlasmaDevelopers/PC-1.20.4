/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class EntityShulkerRotationFix extends NamedEntityFix {
/*    */   public EntityShulkerRotationFix(Schema $$0) {
/* 12 */     super($$0, false, "EntityShulkerRotationFix", References.ENTITY, "minecraft:shulker");
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 16 */     List<Double> $$1 = $$0.get("Rotation").asList($$0 -> Double.valueOf($$0.asDouble(180.0D)));
/* 17 */     if (!$$1.isEmpty()) {
/* 18 */       $$1.set(0, Double.valueOf(((Double)$$1.get(0)).doubleValue() - 180.0D));
/* 19 */       Objects.requireNonNull($$0); return $$0.set("Rotation", $$0.createList($$1.stream().map($$0::createDouble)));
/*    */     } 
/* 21 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 26 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityShulkerRotationFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */