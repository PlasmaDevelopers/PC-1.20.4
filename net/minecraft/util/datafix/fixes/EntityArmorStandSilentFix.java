/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class EntityArmorStandSilentFix extends NamedEntityFix {
/*    */   public EntityArmorStandSilentFix(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1, "EntityArmorStandSilentFix", References.ENTITY, "ArmorStand");
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 14 */     if ($$0.get("Silent").asBoolean(false) && !$$0.get("Marker").asBoolean(false)) {
/* 15 */       return $$0.remove("Silent");
/*    */     }
/* 17 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 22 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityArmorStandSilentFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */