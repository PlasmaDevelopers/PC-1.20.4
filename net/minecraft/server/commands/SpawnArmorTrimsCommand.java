/*     */ package net.minecraft.server.commands;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.ToIntFunction;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.decoration.ArmorStand;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ArmorItem;
/*     */ import net.minecraft.world.item.ArmorMaterial;
/*     */ import net.minecraft.world.item.ArmorMaterials;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.armortrim.ArmorTrim;
/*     */ import net.minecraft.world.item.armortrim.TrimMaterial;
/*     */ import net.minecraft.world.item.armortrim.TrimMaterials;
/*     */ import net.minecraft.world.item.armortrim.TrimPattern;
/*     */ import net.minecraft.world.item.armortrim.TrimPatterns;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class SpawnArmorTrimsCommand {
/*     */   static {
/*  39 */     MATERIAL_AND_SLOT_TO_ITEM = (Map<Pair<ArmorMaterial, EquipmentSlot>, Item>)Util.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put(Pair.of(ArmorMaterials.CHAIN, EquipmentSlot.HEAD), Items.CHAINMAIL_HELMET);
/*     */           $$0.put(Pair.of(ArmorMaterials.CHAIN, EquipmentSlot.CHEST), Items.CHAINMAIL_CHESTPLATE);
/*     */           $$0.put(Pair.of(ArmorMaterials.CHAIN, EquipmentSlot.LEGS), Items.CHAINMAIL_LEGGINGS);
/*     */           $$0.put(Pair.of(ArmorMaterials.CHAIN, EquipmentSlot.FEET), Items.CHAINMAIL_BOOTS);
/*     */           $$0.put(Pair.of(ArmorMaterials.IRON, EquipmentSlot.HEAD), Items.IRON_HELMET);
/*     */           $$0.put(Pair.of(ArmorMaterials.IRON, EquipmentSlot.CHEST), Items.IRON_CHESTPLATE);
/*     */           $$0.put(Pair.of(ArmorMaterials.IRON, EquipmentSlot.LEGS), Items.IRON_LEGGINGS);
/*     */           $$0.put(Pair.of(ArmorMaterials.IRON, EquipmentSlot.FEET), Items.IRON_BOOTS);
/*     */           $$0.put(Pair.of(ArmorMaterials.GOLD, EquipmentSlot.HEAD), Items.GOLDEN_HELMET);
/*     */           $$0.put(Pair.of(ArmorMaterials.GOLD, EquipmentSlot.CHEST), Items.GOLDEN_CHESTPLATE);
/*     */           $$0.put(Pair.of(ArmorMaterials.GOLD, EquipmentSlot.LEGS), Items.GOLDEN_LEGGINGS);
/*     */           $$0.put(Pair.of(ArmorMaterials.GOLD, EquipmentSlot.FEET), Items.GOLDEN_BOOTS);
/*     */           $$0.put(Pair.of(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD), Items.NETHERITE_HELMET);
/*     */           $$0.put(Pair.of(ArmorMaterials.NETHERITE, EquipmentSlot.CHEST), Items.NETHERITE_CHESTPLATE);
/*     */           $$0.put(Pair.of(ArmorMaterials.NETHERITE, EquipmentSlot.LEGS), Items.NETHERITE_LEGGINGS);
/*     */           $$0.put(Pair.of(ArmorMaterials.NETHERITE, EquipmentSlot.FEET), Items.NETHERITE_BOOTS);
/*     */           $$0.put(Pair.of(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD), Items.DIAMOND_HELMET);
/*     */           $$0.put(Pair.of(ArmorMaterials.DIAMOND, EquipmentSlot.CHEST), Items.DIAMOND_CHESTPLATE);
/*     */           $$0.put(Pair.of(ArmorMaterials.DIAMOND, EquipmentSlot.LEGS), Items.DIAMOND_LEGGINGS);
/*     */           $$0.put(Pair.of(ArmorMaterials.DIAMOND, EquipmentSlot.FEET), Items.DIAMOND_BOOTS);
/*     */           $$0.put(Pair.of(ArmorMaterials.TURTLE, EquipmentSlot.HEAD), Items.TURTLE_HELMET);
/*     */         });
/*     */   }
/*  63 */   private static final Map<Pair<ArmorMaterial, EquipmentSlot>, Item> MATERIAL_AND_SLOT_TO_ITEM; private static final List<ResourceKey<TrimPattern>> VANILLA_TRIM_PATTERNS = List.of((ResourceKey<TrimPattern>[])new ResourceKey[] { TrimPatterns.SENTRY, TrimPatterns.DUNE, TrimPatterns.COAST, TrimPatterns.WILD, TrimPatterns.WARD, TrimPatterns.EYE, TrimPatterns.VEX, TrimPatterns.TIDE, TrimPatterns.SNOUT, TrimPatterns.RIB, TrimPatterns.SPIRE, TrimPatterns.WAYFINDER, TrimPatterns.SHAPER, TrimPatterns.SILENCE, TrimPatterns.RAISER, TrimPatterns.HOST });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final List<ResourceKey<TrimMaterial>> VANILLA_TRIM_MATERIALS = List.of(TrimMaterials.QUARTZ, TrimMaterials.IRON, TrimMaterials.NETHERITE, TrimMaterials.REDSTONE, TrimMaterials.COPPER, TrimMaterials.GOLD, TrimMaterials.EMERALD, TrimMaterials.DIAMOND, TrimMaterials.LAPIS, TrimMaterials.AMETHYST);
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final ToIntFunction<ResourceKey<TrimPattern>> TRIM_PATTERN_ORDER = Util.createIndexLookup(VANILLA_TRIM_PATTERNS);
/*  74 */   private static final ToIntFunction<ResourceKey<TrimMaterial>> TRIM_MATERIAL_ORDER = Util.createIndexLookup(VANILLA_TRIM_MATERIALS);
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  77 */     $$0.register(
/*  78 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("spawn_armor_trims")
/*  79 */         .requires($$0 -> $$0.hasPermission(2)))
/*  80 */         .executes($$0 -> spawnArmorTrims((CommandSourceStack)$$0.getSource(), (Player)((CommandSourceStack)$$0.getSource()).getPlayerOrException())));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int spawnArmorTrims(CommandSourceStack $$0, Player $$1) {
/*  85 */     Level $$2 = $$1.level();
/*  86 */     NonNullList<ArmorTrim> $$3 = NonNullList.create();
/*     */     
/*  88 */     Registry<TrimPattern> $$4 = $$2.registryAccess().registryOrThrow(Registries.TRIM_PATTERN);
/*  89 */     Registry<TrimMaterial> $$5 = $$2.registryAccess().registryOrThrow(Registries.TRIM_MATERIAL);
/*  90 */     $$4.stream().sorted(Comparator.comparing($$1 -> Integer.valueOf(TRIM_PATTERN_ORDER.applyAsInt($$0.getResourceKey($$1).orElse(null))))).forEachOrdered($$3 -> $$0.stream().sorted(Comparator.comparing(())).forEachOrdered(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     BlockPos $$6 = $$1.blockPosition().relative($$1.getDirection(), 5);
/*     */     
/*  98 */     int $$7 = (ArmorMaterials.values()).length - 1;
/*  99 */     double $$8 = 3.0D;
/* 100 */     int $$9 = 0;
/* 101 */     int $$10 = 0;
/* 102 */     for (ArmorTrim $$11 : $$3) {
/* 103 */       for (ArmorMaterials armorMaterials : ArmorMaterials.values()) {
/* 104 */         if (armorMaterials != ArmorMaterials.LEATHER) {
/*     */ 
/*     */           
/* 107 */           double $$13 = $$6.getX() + 0.5D - ($$9 % $$5.size()) * 3.0D;
/* 108 */           double $$14 = $$6.getY() + 0.5D + ($$10 % $$7) * 3.0D;
/* 109 */           double $$15 = $$6.getZ() + 0.5D + ($$9 / $$5.size() * 10);
/* 110 */           ArmorStand $$16 = new ArmorStand($$2, $$13, $$14, $$15);
/* 111 */           $$16.setYRot(180.0F);
/* 112 */           $$16.setNoGravity(true);
/*     */           
/* 114 */           for (EquipmentSlot $$17 : EquipmentSlot.values()) {
/* 115 */             Item $$18 = MATERIAL_AND_SLOT_TO_ITEM.get(Pair.of(armorMaterials, $$17));
/* 116 */             if ($$18 == null) {
/*     */               continue;
/*     */             }
/* 119 */             ItemStack $$19 = new ItemStack((ItemLike)$$18);
/* 120 */             ArmorTrim.setTrim($$2.registryAccess(), $$19, $$11);
/* 121 */             $$16.setItemSlot($$17, $$19);
/* 122 */             if ($$18 instanceof ArmorItem) { ArmorItem $$20 = (ArmorItem)$$18; if ($$20.getMaterial() == ArmorMaterials.TURTLE) {
/* 123 */                 $$16.setCustomName((Component)((TrimPattern)$$11.pattern().value()).copyWithStyle($$11.material()).copy().append(" ").append(((TrimMaterial)$$11.material().value()).description()));
/* 124 */                 $$16.setCustomNameVisible(true); continue;
/*     */               }  }
/* 126 */              $$16.setInvisible(true);
/*     */             
/*     */             continue;
/*     */           } 
/* 130 */           $$2.addFreshEntity((Entity)$$16);
/* 131 */           $$10++;
/*     */         } 
/* 133 */       }  $$9++;
/*     */     } 
/*     */     
/* 136 */     $$0.sendSuccess(() -> Component.literal("Armorstands with trimmed armor spawned around you"), true);
/*     */     
/* 138 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SpawnArmorTrimsCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */