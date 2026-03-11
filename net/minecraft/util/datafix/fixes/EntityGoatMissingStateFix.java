/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class EntityGoatMissingStateFix extends NamedEntityFix {
/*    */   public EntityGoatMissingStateFix(Schema $$0) {
/* 10 */     super($$0, false, "EntityGoatMissingStateFix", References.ENTITY, "minecraft:goat");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 15 */     return $$0.update(DSL.remainderFinder(), $$0 -> $$0.set("HasLeftHorn", $$0.createBoolean(true)).set("HasRightHorn", $$0.createBoolean(true)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityGoatMissingStateFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */