/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class EntityWolfColorFix extends NamedEntityFix {
/*    */   public EntityWolfColorFix(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1, "EntityWolfColorFix", References.ENTITY, "minecraft:wolf");
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 14 */     return $$0.update("CollarColor", $$0 -> $$0.createByte((byte)(15 - $$0.asInt(0))));
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 19 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityWolfColorFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */