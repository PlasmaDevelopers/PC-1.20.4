/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.TaggedChoice;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class BlockEntityIdFix extends DataFix {
/*    */   public BlockEntityIdFix(Schema $$0, boolean $$1) {
/* 16 */     super($$0, $$1);
/*    */   } private static final Map<String, String> ID_MAP;
/*    */   static {
/* 19 */     ID_MAP = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), $$0 -> {
/*    */           $$0.put("Airportal", "minecraft:end_portal");
/*    */           $$0.put("Banner", "minecraft:banner");
/*    */           $$0.put("Beacon", "minecraft:beacon");
/*    */           $$0.put("Cauldron", "minecraft:brewing_stand");
/*    */           $$0.put("Chest", "minecraft:chest");
/*    */           $$0.put("Comparator", "minecraft:comparator");
/*    */           $$0.put("Control", "minecraft:command_block");
/*    */           $$0.put("DLDetector", "minecraft:daylight_detector");
/*    */           $$0.put("Dropper", "minecraft:dropper");
/*    */           $$0.put("EnchantTable", "minecraft:enchanting_table");
/*    */           $$0.put("EndGateway", "minecraft:end_gateway");
/*    */           $$0.put("EnderChest", "minecraft:ender_chest");
/*    */           $$0.put("FlowerPot", "minecraft:flower_pot");
/*    */           $$0.put("Furnace", "minecraft:furnace");
/*    */           $$0.put("Hopper", "minecraft:hopper");
/*    */           $$0.put("MobSpawner", "minecraft:mob_spawner");
/*    */           $$0.put("Music", "minecraft:noteblock");
/*    */           $$0.put("Piston", "minecraft:piston");
/*    */           $$0.put("RecordPlayer", "minecraft:jukebox");
/*    */           $$0.put("Sign", "minecraft:sign");
/*    */           $$0.put("Skull", "minecraft:skull");
/*    */           $$0.put("Structure", "minecraft:structure_block");
/*    */           $$0.put("Trap", "minecraft:dispenser");
/*    */         });
/*    */   }
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 47 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/* 48 */     Type<?> $$1 = getOutputSchema().getType(References.ITEM_STACK);
/*    */     
/* 50 */     TaggedChoice.TaggedChoiceType<String> $$2 = getInputSchema().findChoiceType(References.BLOCK_ENTITY);
/* 51 */     TaggedChoice.TaggedChoiceType<String> $$3 = getOutputSchema().findChoiceType(References.BLOCK_ENTITY);
/*    */     
/* 53 */     return TypeRewriteRule.seq(
/* 54 */         convertUnchecked("item stack block entity name hook converter", $$0, $$1), 
/* 55 */         fixTypeEverywhere("BlockEntityIdFix", (Type)$$2, (Type)$$3, $$0 -> ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntityIdFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */