/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public class ItemStackEnchantmentNamesFix extends DataFix {
/*    */   static {
/* 17 */     MAP = (Int2ObjectMap<String>)DataFixUtils.make(new Int2ObjectOpenHashMap(), $$0 -> {
/*    */           $$0.put(0, "minecraft:protection");
/*    */           $$0.put(1, "minecraft:fire_protection");
/*    */           $$0.put(2, "minecraft:feather_falling");
/*    */           $$0.put(3, "minecraft:blast_protection");
/*    */           $$0.put(4, "minecraft:projectile_protection");
/*    */           $$0.put(5, "minecraft:respiration");
/*    */           $$0.put(6, "minecraft:aqua_affinity");
/*    */           $$0.put(7, "minecraft:thorns");
/*    */           $$0.put(8, "minecraft:depth_strider");
/*    */           $$0.put(9, "minecraft:frost_walker");
/*    */           $$0.put(10, "minecraft:binding_curse");
/*    */           $$0.put(16, "minecraft:sharpness");
/*    */           $$0.put(17, "minecraft:smite");
/*    */           $$0.put(18, "minecraft:bane_of_arthropods");
/*    */           $$0.put(19, "minecraft:knockback");
/*    */           $$0.put(20, "minecraft:fire_aspect");
/*    */           $$0.put(21, "minecraft:looting");
/*    */           $$0.put(22, "minecraft:sweeping");
/*    */           $$0.put(32, "minecraft:efficiency");
/*    */           $$0.put(33, "minecraft:silk_touch");
/*    */           $$0.put(34, "minecraft:unbreaking");
/*    */           $$0.put(35, "minecraft:fortune");
/*    */           $$0.put(48, "minecraft:power");
/*    */           $$0.put(49, "minecraft:punch");
/*    */           $$0.put(50, "minecraft:flame");
/*    */           $$0.put(51, "minecraft:infinity");
/*    */           $$0.put(61, "minecraft:luck_of_the_sea");
/*    */           $$0.put(62, "minecraft:lure");
/*    */           $$0.put(65, "minecraft:loyalty");
/*    */           $$0.put(66, "minecraft:impaling");
/*    */           $$0.put(67, "minecraft:riptide");
/*    */           $$0.put(68, "minecraft:channeling");
/*    */           $$0.put(70, "minecraft:mending");
/*    */           $$0.put(71, "minecraft:vanishing_curse");
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static final Int2ObjectMap<String> MAP;
/*    */ 
/*    */   
/*    */   public ItemStackEnchantmentNamesFix(Schema $$0, boolean $$1) {
/* 61 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 66 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/* 67 */     OpticFinder<?> $$1 = $$0.findField("tag");
/* 68 */     return fixTypeEverywhereTyped("ItemStackEnchantmentFix", $$0, $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 74 */     Objects.requireNonNull($$0); Optional<? extends Dynamic<?>> $$1 = $$0.get("ench").asStreamOpt().map($$0 -> $$0.map(())).map($$0::createList).result();
/*    */     
/* 76 */     if ($$1.isPresent()) {
/* 77 */       $$0 = $$0.remove("ench").set("Enchantments", $$1.get());
/*    */     }
/*    */     
/* 80 */     return $$0.update("StoredEnchantments", $$0 -> {
/*    */           Objects.requireNonNull($$0);
/*    */           return (Dynamic)DataFixUtils.orElse($$0.asStreamOpt().map(()).map($$0::createList).result(), $$0);
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemStackEnchantmentNamesFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */