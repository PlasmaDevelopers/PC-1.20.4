/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class VillagerDataFix extends NamedEntityFix {
/*    */   public VillagerDataFix(Schema $$0, String $$1) {
/* 12 */     super($$0, false, "Villager profession data fix (" + $$1 + ")", References.ENTITY, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 17 */     Dynamic<?> $$1 = (Dynamic)$$0.get(DSL.remainderFinder());
/*    */     
/* 19 */     return $$0.set(DSL.remainderFinder(), $$1
/* 20 */         .remove("Profession")
/* 21 */         .remove("Career")
/* 22 */         .remove("CareerLevel")
/* 23 */         .set("VillagerData", $$1
/* 24 */           .createMap((Map)ImmutableMap.of($$1
/* 25 */               .createString("type"), $$1.createString("minecraft:plains"), $$1
/* 26 */               .createString("profession"), $$1.createString(upgradeData($$1.get("Profession").asInt(0), $$1.get("Career").asInt(0))), $$1
/* 27 */               .createString("level"), DataFixUtils.orElse($$1.get("CareerLevel").result(), $$1.createInt(1))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String upgradeData(int $$0, int $$1) {
/* 34 */     if ($$0 == 0) {
/* 35 */       if ($$1 == 2)
/* 36 */         return "minecraft:fisherman"; 
/* 37 */       if ($$1 == 3)
/* 38 */         return "minecraft:shepherd"; 
/* 39 */       if ($$1 == 4) {
/* 40 */         return "minecraft:fletcher";
/*    */       }
/* 42 */       return "minecraft:farmer";
/*    */     } 
/* 44 */     if ($$0 == 1) {
/* 45 */       if ($$1 == 2) {
/* 46 */         return "minecraft:cartographer";
/*    */       }
/* 48 */       return "minecraft:librarian";
/*    */     } 
/* 50 */     if ($$0 == 2)
/* 51 */       return "minecraft:cleric"; 
/* 52 */     if ($$0 == 3) {
/* 53 */       if ($$1 == 2)
/* 54 */         return "minecraft:weaponsmith"; 
/* 55 */       if ($$1 == 3) {
/* 56 */         return "minecraft:toolsmith";
/*    */       }
/* 58 */       return "minecraft:armorer";
/*    */     } 
/* 60 */     if ($$0 == 4) {
/* 61 */       if ($$1 == 2) {
/* 62 */         return "minecraft:leatherworker";
/*    */       }
/* 64 */       return "minecraft:butcher";
/*    */     } 
/* 66 */     if ($$0 == 5) {
/* 67 */       return "minecraft:nitwit";
/*    */     }
/* 69 */     return "minecraft:none";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\VillagerDataFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */