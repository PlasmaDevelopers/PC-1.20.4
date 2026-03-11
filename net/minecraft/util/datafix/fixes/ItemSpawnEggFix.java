/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.OpticFinder;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*     */ 
/*     */ public class ItemSpawnEggFix extends DataFix {
/*     */   private static final String[] ID_TO_ENTITY;
/*     */   
/*     */   public ItemSpawnEggFix(Schema $$0, boolean $$1) {
/*  22 */     super($$0, $$1);
/*     */   }
/*     */   static {
/*  25 */     ID_TO_ENTITY = (String[])DataFixUtils.make(new String[256], $$0 -> {
/*     */           $$0[1] = "Item";
/*     */           $$0[2] = "XPOrb";
/*     */           $$0[7] = "ThrownEgg";
/*     */           $$0[8] = "LeashKnot";
/*     */           $$0[9] = "Painting";
/*     */           $$0[10] = "Arrow";
/*     */           $$0[11] = "Snowball";
/*     */           $$0[12] = "Fireball";
/*     */           $$0[13] = "SmallFireball";
/*     */           $$0[14] = "ThrownEnderpearl";
/*     */           $$0[15] = "EyeOfEnderSignal";
/*     */           $$0[16] = "ThrownPotion";
/*     */           $$0[17] = "ThrownExpBottle";
/*     */           $$0[18] = "ItemFrame";
/*     */           $$0[19] = "WitherSkull";
/*     */           $$0[20] = "PrimedTnt";
/*     */           $$0[21] = "FallingSand";
/*     */           $$0[22] = "FireworksRocketEntity";
/*     */           $$0[23] = "TippedArrow";
/*     */           $$0[24] = "SpectralArrow";
/*     */           $$0[25] = "ShulkerBullet";
/*     */           $$0[26] = "DragonFireball";
/*     */           $$0[30] = "ArmorStand";
/*     */           $$0[41] = "Boat";
/*     */           $$0[42] = "MinecartRideable";
/*     */           $$0[43] = "MinecartChest";
/*     */           $$0[44] = "MinecartFurnace";
/*     */           $$0[45] = "MinecartTNT";
/*     */           $$0[46] = "MinecartHopper";
/*     */           $$0[47] = "MinecartSpawner";
/*     */           $$0[40] = "MinecartCommandBlock";
/*     */           $$0[48] = "Mob";
/*     */           $$0[49] = "Monster";
/*     */           $$0[50] = "Creeper";
/*     */           $$0[51] = "Skeleton";
/*     */           $$0[52] = "Spider";
/*     */           $$0[53] = "Giant";
/*     */           $$0[54] = "Zombie";
/*     */           $$0[55] = "Slime";
/*     */           $$0[56] = "Ghast";
/*     */           $$0[57] = "PigZombie";
/*     */           $$0[58] = "Enderman";
/*     */           $$0[59] = "CaveSpider";
/*     */           $$0[60] = "Silverfish";
/*     */           $$0[61] = "Blaze";
/*     */           $$0[62] = "LavaSlime";
/*     */           $$0[63] = "EnderDragon";
/*     */           $$0[64] = "WitherBoss";
/*     */           $$0[65] = "Bat";
/*     */           $$0[66] = "Witch";
/*     */           $$0[67] = "Endermite";
/*     */           $$0[68] = "Guardian";
/*     */           $$0[69] = "Shulker";
/*     */           $$0[90] = "Pig";
/*     */           $$0[91] = "Sheep";
/*     */           $$0[92] = "Cow";
/*     */           $$0[93] = "Chicken";
/*     */           $$0[94] = "Squid";
/*     */           $$0[95] = "Wolf";
/*     */           $$0[96] = "MushroomCow";
/*     */           $$0[97] = "SnowMan";
/*     */           $$0[98] = "Ozelot";
/*     */           $$0[99] = "VillagerGolem";
/*     */           $$0[100] = "EntityHorse";
/*     */           $$0[101] = "Rabbit";
/*     */           $$0[120] = "Villager";
/*     */           $$0[200] = "EnderCrystal";
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeRewriteRule makeRule() {
/* 108 */     Schema $$0 = getInputSchema();
/* 109 */     Type<?> $$1 = $$0.getType(References.ITEM_STACK);
/*     */     
/* 111 */     OpticFinder<Pair<String, String>> $$2 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/* 112 */     OpticFinder<String> $$3 = DSL.fieldFinder("id", DSL.string());
/* 113 */     OpticFinder<?> $$4 = $$1.findField("tag");
/* 114 */     OpticFinder<?> $$5 = $$4.type().findField("EntityTag");
/* 115 */     OpticFinder<?> $$6 = DSL.typeFinder($$0.getTypeRaw(References.ENTITY));
/*     */     
/* 117 */     Type<?> $$7 = getOutputSchema().getTypeRaw(References.ENTITY);
/*     */     
/* 119 */     return fixTypeEverywhereTyped("ItemSpawnEggFix", $$1, $$6 -> {
/*     */           Optional<Pair<String, String>> $$7 = $$6.getOptional($$0);
/*     */           if ($$7.isPresent() && Objects.equals(((Pair)$$7.get()).getSecond(), "minecraft:spawn_egg")) {
/*     */             Dynamic<?> $$8 = (Dynamic)$$6.get(DSL.remainderFinder());
/*     */             short $$9 = $$8.get("Damage").asShort((short)0);
/*     */             Optional<? extends Typed<?>> $$10 = $$6.getOptionalTyped($$1);
/*     */             Optional<? extends Typed<?>> $$11 = $$10.flatMap(());
/*     */             Optional<? extends Typed<?>> $$12 = $$11.flatMap(());
/*     */             Optional<String> $$13 = $$12.flatMap(());
/*     */             Typed<?> $$14 = $$6;
/*     */             String $$15 = ID_TO_ENTITY[$$9 & 0xFF];
/*     */             if ($$15 != null && ($$13.isEmpty() || !Objects.equals($$13.get(), $$15))) {
/*     */               Typed<?> $$16 = $$6.getOrCreateTyped($$1);
/*     */               Typed<?> $$17 = $$16.getOrCreateTyped($$2);
/*     */               Typed<?> $$18 = $$17.getOrCreateTyped($$3);
/*     */               Dynamic<?> $$19 = $$8;
/*     */               Typed<?> $$20 = Util.writeAndReadTypedOrThrow($$18, $$5, ());
/*     */               $$14 = $$14.set($$1, $$16.set($$2, $$17.set($$3, $$20)));
/*     */             } 
/*     */             if ($$9 != 0) {
/*     */               $$8 = $$8.set("Damage", $$8.createShort((short)0));
/*     */               $$14 = $$14.set(DSL.remainderFinder(), $$8);
/*     */             } 
/*     */             return $$14;
/*     */           } 
/*     */           return $$6;
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemSpawnEggFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */