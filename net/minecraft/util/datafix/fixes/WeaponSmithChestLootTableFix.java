/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class WeaponSmithChestLootTableFix extends NamedEntityFix {
/*    */   public WeaponSmithChestLootTableFix(Schema $$0, boolean $$1) {
/*  9 */     super($$0, $$1, "WeaponSmithChestLootTableFix", References.BLOCK_ENTITY, "minecraft:chest");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 14 */     return $$0.update(DSL.remainderFinder(), $$0 -> {
/*    */           String $$1 = $$0.get("LootTable").asString("");
/*    */           return $$1.equals("minecraft:chests/village_blacksmith") ? $$0.set("LootTable", $$0.createString("minecraft:chests/village/village_weaponsmith")) : $$0;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\WeaponSmithChestLootTableFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */