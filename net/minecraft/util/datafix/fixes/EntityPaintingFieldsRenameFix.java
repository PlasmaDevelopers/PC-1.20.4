/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class EntityPaintingFieldsRenameFix
/*    */   extends NamedEntityFix {
/*    */   public EntityPaintingFieldsRenameFix(Schema $$0) {
/* 13 */     super($$0, false, "EntityPaintingFieldsRenameFix", References.ENTITY, "minecraft:painting");
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 17 */     return renameField(renameField($$0, "Motive", "variant"), "Facing", "facing");
/*    */   }
/*    */   
/*    */   private Dynamic<?> renameField(Dynamic<?> $$0, String $$1, String $$2) {
/* 21 */     Optional<? extends Dynamic<?>> $$3 = $$0.get($$1).result();
/* 22 */     Optional<? extends Dynamic<?>> $$4 = $$3.map($$3 -> $$0.remove($$1).set($$2, $$3));
/* 23 */     return (Dynamic)DataFixUtils.orElse($$4, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 28 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityPaintingFieldsRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */