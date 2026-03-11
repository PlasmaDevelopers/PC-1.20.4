/*     */ package net.minecraft.util.datafix.fixes;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.datafix.schemas.V1451_6;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class StatsCounterFix extends DataFix {
/*     */   public StatsCounterFix(Schema $$0, boolean $$1) {
/*  24 */     super($$0, $$1);
/*     */   }
/*     */   
/*  27 */   private static final Set<String> SPECIAL_OBJECTIVE_CRITERIA = Set.of(new String[] { "dummy", "trigger", "deathCount", "playerKillCount", "totalKillCount", "health", "food", "air", "armor", "xp", "level", "killedByTeam.aqua", "killedByTeam.black", "killedByTeam.blue", "killedByTeam.dark_aqua", "killedByTeam.dark_blue", "killedByTeam.dark_gray", "killedByTeam.dark_green", "killedByTeam.dark_purple", "killedByTeam.dark_red", "killedByTeam.gold", "killedByTeam.gray", "killedByTeam.green", "killedByTeam.light_purple", "killedByTeam.red", "killedByTeam.white", "killedByTeam.yellow", "teamkill.aqua", "teamkill.black", "teamkill.blue", "teamkill.dark_aqua", "teamkill.dark_blue", "teamkill.dark_gray", "teamkill.dark_green", "teamkill.dark_purple", "teamkill.dark_red", "teamkill.gold", "teamkill.gray", "teamkill.green", "teamkill.light_purple", "teamkill.red", "teamkill.white", "teamkill.yellow" });
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
/*  75 */   private static final Set<String> SKIP = (Set<String>)ImmutableSet.builder()
/*  76 */     .add("stat.craftItem.minecraft.spawn_egg")
/*  77 */     .add("stat.useItem.minecraft.spawn_egg")
/*  78 */     .add("stat.breakItem.minecraft.spawn_egg")
/*  79 */     .add("stat.pickup.minecraft.spawn_egg")
/*  80 */     .add("stat.drop.minecraft.spawn_egg")
/*  81 */     .build();
/*     */   
/*  83 */   private static final Map<String, String> CUSTOM_MAP = (Map<String, String>)ImmutableMap.builder()
/*  84 */     .put("stat.leaveGame", "minecraft:leave_game")
/*  85 */     .put("stat.playOneMinute", "minecraft:play_one_minute")
/*  86 */     .put("stat.timeSinceDeath", "minecraft:time_since_death")
/*  87 */     .put("stat.sneakTime", "minecraft:sneak_time")
/*  88 */     .put("stat.walkOneCm", "minecraft:walk_one_cm")
/*  89 */     .put("stat.crouchOneCm", "minecraft:crouch_one_cm")
/*  90 */     .put("stat.sprintOneCm", "minecraft:sprint_one_cm")
/*  91 */     .put("stat.swimOneCm", "minecraft:swim_one_cm")
/*  92 */     .put("stat.fallOneCm", "minecraft:fall_one_cm")
/*  93 */     .put("stat.climbOneCm", "minecraft:climb_one_cm")
/*  94 */     .put("stat.flyOneCm", "minecraft:fly_one_cm")
/*  95 */     .put("stat.diveOneCm", "minecraft:dive_one_cm")
/*  96 */     .put("stat.minecartOneCm", "minecraft:minecart_one_cm")
/*  97 */     .put("stat.boatOneCm", "minecraft:boat_one_cm")
/*  98 */     .put("stat.pigOneCm", "minecraft:pig_one_cm")
/*  99 */     .put("stat.horseOneCm", "minecraft:horse_one_cm")
/* 100 */     .put("stat.aviateOneCm", "minecraft:aviate_one_cm")
/* 101 */     .put("stat.jump", "minecraft:jump")
/* 102 */     .put("stat.drop", "minecraft:drop")
/* 103 */     .put("stat.damageDealt", "minecraft:damage_dealt")
/* 104 */     .put("stat.damageTaken", "minecraft:damage_taken")
/* 105 */     .put("stat.deaths", "minecraft:deaths")
/* 106 */     .put("stat.mobKills", "minecraft:mob_kills")
/* 107 */     .put("stat.animalsBred", "minecraft:animals_bred")
/* 108 */     .put("stat.playerKills", "minecraft:player_kills")
/* 109 */     .put("stat.fishCaught", "minecraft:fish_caught")
/* 110 */     .put("stat.talkedToVillager", "minecraft:talked_to_villager")
/* 111 */     .put("stat.tradedWithVillager", "minecraft:traded_with_villager")
/* 112 */     .put("stat.cakeSlicesEaten", "minecraft:eat_cake_slice")
/* 113 */     .put("stat.cauldronFilled", "minecraft:fill_cauldron")
/* 114 */     .put("stat.cauldronUsed", "minecraft:use_cauldron")
/* 115 */     .put("stat.armorCleaned", "minecraft:clean_armor")
/* 116 */     .put("stat.bannerCleaned", "minecraft:clean_banner")
/* 117 */     .put("stat.brewingstandInteraction", "minecraft:interact_with_brewingstand")
/* 118 */     .put("stat.beaconInteraction", "minecraft:interact_with_beacon")
/* 119 */     .put("stat.dropperInspected", "minecraft:inspect_dropper")
/* 120 */     .put("stat.hopperInspected", "minecraft:inspect_hopper")
/* 121 */     .put("stat.dispenserInspected", "minecraft:inspect_dispenser")
/* 122 */     .put("stat.noteblockPlayed", "minecraft:play_noteblock")
/* 123 */     .put("stat.noteblockTuned", "minecraft:tune_noteblock")
/* 124 */     .put("stat.flowerPotted", "minecraft:pot_flower")
/* 125 */     .put("stat.trappedChestTriggered", "minecraft:trigger_trapped_chest")
/* 126 */     .put("stat.enderchestOpened", "minecraft:open_enderchest")
/* 127 */     .put("stat.itemEnchanted", "minecraft:enchant_item")
/* 128 */     .put("stat.recordPlayed", "minecraft:play_record")
/* 129 */     .put("stat.furnaceInteraction", "minecraft:interact_with_furnace")
/* 130 */     .put("stat.craftingTableInteraction", "minecraft:interact_with_crafting_table")
/* 131 */     .put("stat.chestOpened", "minecraft:open_chest")
/* 132 */     .put("stat.sleepInBed", "minecraft:sleep_in_bed")
/* 133 */     .put("stat.shulkerBoxOpened", "minecraft:open_shulker_box")
/* 134 */     .build();
/*     */   
/*     */   private static final String BLOCK_KEY = "stat.mineBlock";
/*     */   private static final String NEW_BLOCK_KEY = "minecraft:mined";
/* 138 */   private static final Map<String, String> ITEM_KEYS = (Map<String, String>)ImmutableMap.builder()
/* 139 */     .put("stat.craftItem", "minecraft:crafted")
/* 140 */     .put("stat.useItem", "minecraft:used")
/* 141 */     .put("stat.breakItem", "minecraft:broken")
/* 142 */     .put("stat.pickup", "minecraft:picked_up")
/* 143 */     .put("stat.drop", "minecraft:dropped")
/* 144 */     .build();
/*     */   
/* 146 */   private static final Map<String, String> ENTITY_KEYS = (Map<String, String>)ImmutableMap.builder()
/* 147 */     .put("stat.entityKilledBy", "minecraft:killed_by")
/* 148 */     .put("stat.killEntity", "minecraft:killed")
/* 149 */     .build();
/*     */   
/* 151 */   private static final Map<String, String> ENTITIES = (Map<String, String>)ImmutableMap.builder()
/* 152 */     .put("Bat", "minecraft:bat")
/* 153 */     .put("Blaze", "minecraft:blaze")
/* 154 */     .put("CaveSpider", "minecraft:cave_spider")
/* 155 */     .put("Chicken", "minecraft:chicken")
/* 156 */     .put("Cow", "minecraft:cow")
/* 157 */     .put("Creeper", "minecraft:creeper")
/* 158 */     .put("Donkey", "minecraft:donkey")
/* 159 */     .put("ElderGuardian", "minecraft:elder_guardian")
/* 160 */     .put("Enderman", "minecraft:enderman")
/* 161 */     .put("Endermite", "minecraft:endermite")
/* 162 */     .put("EvocationIllager", "minecraft:evocation_illager")
/* 163 */     .put("Ghast", "minecraft:ghast")
/* 164 */     .put("Guardian", "minecraft:guardian")
/* 165 */     .put("Horse", "minecraft:horse")
/* 166 */     .put("Husk", "minecraft:husk")
/* 167 */     .put("Llama", "minecraft:llama")
/* 168 */     .put("LavaSlime", "minecraft:magma_cube")
/* 169 */     .put("MushroomCow", "minecraft:mooshroom")
/* 170 */     .put("Mule", "minecraft:mule")
/* 171 */     .put("Ozelot", "minecraft:ocelot")
/* 172 */     .put("Parrot", "minecraft:parrot")
/* 173 */     .put("Pig", "minecraft:pig")
/* 174 */     .put("PolarBear", "minecraft:polar_bear")
/* 175 */     .put("Rabbit", "minecraft:rabbit")
/* 176 */     .put("Sheep", "minecraft:sheep")
/* 177 */     .put("Shulker", "minecraft:shulker")
/* 178 */     .put("Silverfish", "minecraft:silverfish")
/* 179 */     .put("SkeletonHorse", "minecraft:skeleton_horse")
/* 180 */     .put("Skeleton", "minecraft:skeleton")
/* 181 */     .put("Slime", "minecraft:slime")
/* 182 */     .put("Spider", "minecraft:spider")
/* 183 */     .put("Squid", "minecraft:squid")
/* 184 */     .put("Stray", "minecraft:stray")
/* 185 */     .put("Vex", "minecraft:vex")
/* 186 */     .put("Villager", "minecraft:villager")
/* 187 */     .put("VindicationIllager", "minecraft:vindication_illager")
/* 188 */     .put("Witch", "minecraft:witch")
/* 189 */     .put("WitherSkeleton", "minecraft:wither_skeleton")
/* 190 */     .put("Wolf", "minecraft:wolf")
/* 191 */     .put("ZombieHorse", "minecraft:zombie_horse")
/* 192 */     .put("PigZombie", "minecraft:zombie_pigman")
/* 193 */     .put("ZombieVillager", "minecraft:zombie_villager")
/* 194 */     .put("Zombie", "minecraft:zombie")
/* 195 */     .build(); private static final String NEW_CUSTOM_KEY = "minecraft:custom";
/*     */   private static final class StatType extends Record { final String type;
/*     */     final String typeKey;
/*     */     
/* 199 */     StatType(String $$0, String $$1) { this.type = $$0; this.typeKey = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #199	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 199 */       //   0	7	0	this	Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType; } public String type() { return this.type; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #199	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 199 */       //   0	7	0	this	Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType; } public String typeKey() { return this.typeKey; } public final boolean equals(Object $$0) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #199	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/util/datafix/fixes/StatsCounterFix$StatType;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */     } } @Nullable
/*     */   private static StatType unpackLegacyKey(String $$0) {
/* 203 */     if (SKIP.contains($$0)) {
/* 204 */       return null;
/*     */     }
/*     */     
/* 207 */     String $$1 = CUSTOM_MAP.get($$0);
/* 208 */     if ($$1 != null) {
/* 209 */       return new StatType("minecraft:custom", $$1);
/*     */     }
/*     */     
/* 212 */     int $$2 = StringUtils.ordinalIndexOf($$0, ".", 2);
/* 213 */     if ($$2 < 0) {
/* 214 */       return null;
/*     */     }
/*     */     
/* 217 */     String $$3 = $$0.substring(0, $$2);
/* 218 */     if ("stat.mineBlock".equals($$3)) {
/* 219 */       String $$4 = upgradeBlock($$0.substring($$2 + 1).replace('.', ':'));
/* 220 */       return new StatType("minecraft:mined", $$4);
/*     */     } 
/*     */     
/* 223 */     String $$5 = ITEM_KEYS.get($$3);
/* 224 */     if ($$5 != null) {
/* 225 */       String $$6 = $$0.substring($$2 + 1).replace('.', ':');
/* 226 */       String $$7 = upgradeItem($$6);
/* 227 */       String $$8 = ($$7 == null) ? $$6 : $$7;
/* 228 */       return new StatType($$5, $$8);
/*     */     } 
/*     */     
/* 231 */     String $$9 = ENTITY_KEYS.get($$3);
/* 232 */     if ($$9 != null) {
/* 233 */       String $$10 = $$0.substring($$2 + 1).replace('.', ':');
/* 234 */       String $$11 = ENTITIES.getOrDefault($$10, $$10);
/* 235 */       return new StatType($$9, $$11);
/*     */     } 
/*     */     
/* 238 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeRewriteRule makeRule() {
/* 243 */     return TypeRewriteRule.seq(
/* 244 */         makeStatFixer(), 
/* 245 */         makeObjectiveFixer());
/*     */   }
/*     */ 
/*     */   
/*     */   private TypeRewriteRule makeStatFixer() {
/* 250 */     Type<?> $$0 = getInputSchema().getType(References.STATS);
/* 251 */     Type<?> $$1 = getOutputSchema().getType(References.STATS);
/* 252 */     return fixTypeEverywhereTyped("StatsCounterFix", $$0, $$1, $$1 -> {
/*     */           Dynamic<?> $$2 = (Dynamic)$$1.get(DSL.remainderFinder());
/*     */           Map<Dynamic<?>, Dynamic<?>> $$3 = Maps.newHashMap();
/*     */           Optional<? extends Map<? extends Dynamic<?>, ? extends Dynamic<?>>> $$4 = $$2.getMapValues().result();
/*     */           if ($$4.isPresent()) {
/*     */             for (Map.Entry<? extends Dynamic<?>, ? extends Dynamic<?>> $$5 : (Iterable<Map.Entry<? extends Dynamic<?>, ? extends Dynamic<?>>>)((Map)$$4.get()).entrySet()) {
/*     */               if (((Dynamic)$$5.getValue()).asNumber().result().isPresent()) {
/*     */                 String $$6 = ((Dynamic)$$5.getKey()).asString("");
/*     */                 StatType $$7 = unpackLegacyKey($$6);
/*     */                 if ($$7 != null) {
/*     */                   Dynamic<?> $$8 = $$2.createString($$7.type());
/*     */                   Dynamic<?> $$9 = $$3.computeIfAbsent($$8, ());
/*     */                   $$3.put($$8, $$9.set($$7.typeKey(), $$5.getValue()));
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */           return Util.readTypedOrThrow($$0, $$2.emptyMap().set("stats", $$2.createMap($$3)));
/*     */         });
/*     */   }
/*     */   
/*     */   private TypeRewriteRule makeObjectiveFixer() {
/* 274 */     Type<?> $$0 = getInputSchema().getType(References.OBJECTIVE);
/* 275 */     Type<?> $$1 = getOutputSchema().getType(References.OBJECTIVE);
/* 276 */     return fixTypeEverywhereTyped("ObjectiveStatFix", $$0, $$1, $$1 -> {
/*     */           Dynamic<?> $$2 = (Dynamic)$$1.get(DSL.remainderFinder());
/*     */           Dynamic<?> $$3 = $$2.update("CriteriaName", ());
/*     */           return Util.readTypedOrThrow($$0, $$3);
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
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String upgradeItem(String $$0) {
/* 298 */     return ItemStackTheFlatteningFix.updateItem($$0, 0);
/*     */   }
/*     */   
/*     */   private static String upgradeBlock(String $$0) {
/* 302 */     return BlockStateData.upgradeBlock($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\StatsCounterFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */