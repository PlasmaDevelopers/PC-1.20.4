/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VillagerFollowRangeFix
/*    */   extends NamedEntityFix
/*    */ {
/*    */   private static final double ORIGINAL_VALUE = 16.0D;
/*    */   private static final double NEW_BASE_VALUE = 48.0D;
/*    */   
/*    */   public VillagerFollowRangeFix(Schema $$0) {
/* 17 */     super($$0, false, "Villager Follow Range Fix", References.ENTITY, "minecraft:villager");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 22 */     return $$0.update(DSL.remainderFinder(), VillagerFollowRangeFix::fixValue);
/*    */   }
/*    */   
/*    */   private static Dynamic<?> fixValue(Dynamic<?> $$0) {
/* 26 */     return $$0.update("Attributes", $$1 -> $$0.createList($$1.asStream().map(())));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\VillagerFollowRangeFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */