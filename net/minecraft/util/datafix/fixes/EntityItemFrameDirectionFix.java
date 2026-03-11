/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class EntityItemFrameDirectionFix extends NamedEntityFix {
/*    */   public EntityItemFrameDirectionFix(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1, "EntityItemFrameDirectionFix", References.ENTITY, "minecraft:item_frame");
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 14 */     return $$0.set("Facing", $$0.createByte(direction2dTo3d($$0.get("Facing").asByte((byte)0))));
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 19 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */   
/*    */   private static byte direction2dTo3d(byte $$0) {
/* 23 */     switch ($$0)
/*    */     
/*    */     { default:
/* 26 */         return 2;
/*    */       case 0:
/* 28 */         return 3;
/*    */       case 1:
/* 30 */         return 4;
/*    */       case 3:
/* 32 */         break; }  return 5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityItemFrameDirectionFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */