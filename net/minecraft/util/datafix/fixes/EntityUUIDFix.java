/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class EntityUUIDFix extends AbstractUUIDFix {
/*  16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  18 */   private static final Set<String> ABSTRACT_HORSES = Sets.newHashSet();
/*  19 */   private static final Set<String> TAMEABLE_ANIMALS = Sets.newHashSet();
/*  20 */   private static final Set<String> ANIMALS = Sets.newHashSet();
/*  21 */   private static final Set<String> MOBS = Sets.newHashSet();
/*  22 */   private static final Set<String> LIVING_ENTITIES = Sets.newHashSet();
/*  23 */   private static final Set<String> PROJECTILES = Sets.newHashSet();
/*     */   
/*     */   static {
/*  26 */     ABSTRACT_HORSES.add("minecraft:donkey");
/*  27 */     ABSTRACT_HORSES.add("minecraft:horse");
/*  28 */     ABSTRACT_HORSES.add("minecraft:llama");
/*  29 */     ABSTRACT_HORSES.add("minecraft:mule");
/*  30 */     ABSTRACT_HORSES.add("minecraft:skeleton_horse");
/*  31 */     ABSTRACT_HORSES.add("minecraft:trader_llama");
/*  32 */     ABSTRACT_HORSES.add("minecraft:zombie_horse");
/*  33 */     TAMEABLE_ANIMALS.add("minecraft:cat");
/*  34 */     TAMEABLE_ANIMALS.add("minecraft:parrot");
/*  35 */     TAMEABLE_ANIMALS.add("minecraft:wolf");
/*  36 */     ANIMALS.add("minecraft:bee");
/*  37 */     ANIMALS.add("minecraft:chicken");
/*  38 */     ANIMALS.add("minecraft:cow");
/*  39 */     ANIMALS.add("minecraft:fox");
/*  40 */     ANIMALS.add("minecraft:mooshroom");
/*  41 */     ANIMALS.add("minecraft:ocelot");
/*  42 */     ANIMALS.add("minecraft:panda");
/*  43 */     ANIMALS.add("minecraft:pig");
/*  44 */     ANIMALS.add("minecraft:polar_bear");
/*  45 */     ANIMALS.add("minecraft:rabbit");
/*  46 */     ANIMALS.add("minecraft:sheep");
/*  47 */     ANIMALS.add("minecraft:turtle");
/*  48 */     ANIMALS.add("minecraft:hoglin");
/*  49 */     MOBS.add("minecraft:bat");
/*  50 */     MOBS.add("minecraft:blaze");
/*  51 */     MOBS.add("minecraft:cave_spider");
/*  52 */     MOBS.add("minecraft:cod");
/*  53 */     MOBS.add("minecraft:creeper");
/*  54 */     MOBS.add("minecraft:dolphin");
/*  55 */     MOBS.add("minecraft:drowned");
/*  56 */     MOBS.add("minecraft:elder_guardian");
/*  57 */     MOBS.add("minecraft:ender_dragon");
/*  58 */     MOBS.add("minecraft:enderman");
/*  59 */     MOBS.add("minecraft:endermite");
/*  60 */     MOBS.add("minecraft:evoker");
/*  61 */     MOBS.add("minecraft:ghast");
/*  62 */     MOBS.add("minecraft:giant");
/*  63 */     MOBS.add("minecraft:guardian");
/*  64 */     MOBS.add("minecraft:husk");
/*  65 */     MOBS.add("minecraft:illusioner");
/*  66 */     MOBS.add("minecraft:magma_cube");
/*  67 */     MOBS.add("minecraft:pufferfish");
/*  68 */     MOBS.add("minecraft:zombified_piglin");
/*  69 */     MOBS.add("minecraft:salmon");
/*  70 */     MOBS.add("minecraft:shulker");
/*  71 */     MOBS.add("minecraft:silverfish");
/*  72 */     MOBS.add("minecraft:skeleton");
/*  73 */     MOBS.add("minecraft:slime");
/*  74 */     MOBS.add("minecraft:snow_golem");
/*  75 */     MOBS.add("minecraft:spider");
/*  76 */     MOBS.add("minecraft:squid");
/*  77 */     MOBS.add("minecraft:stray");
/*  78 */     MOBS.add("minecraft:tropical_fish");
/*  79 */     MOBS.add("minecraft:vex");
/*  80 */     MOBS.add("minecraft:villager");
/*  81 */     MOBS.add("minecraft:iron_golem");
/*  82 */     MOBS.add("minecraft:vindicator");
/*  83 */     MOBS.add("minecraft:pillager");
/*  84 */     MOBS.add("minecraft:wandering_trader");
/*  85 */     MOBS.add("minecraft:witch");
/*  86 */     MOBS.add("minecraft:wither");
/*  87 */     MOBS.add("minecraft:wither_skeleton");
/*  88 */     MOBS.add("minecraft:zombie");
/*  89 */     MOBS.add("minecraft:zombie_villager");
/*  90 */     MOBS.add("minecraft:phantom");
/*  91 */     MOBS.add("minecraft:ravager");
/*  92 */     MOBS.add("minecraft:piglin");
/*  93 */     LIVING_ENTITIES.add("minecraft:armor_stand");
/*  94 */     PROJECTILES.add("minecraft:arrow");
/*  95 */     PROJECTILES.add("minecraft:dragon_fireball");
/*  96 */     PROJECTILES.add("minecraft:firework_rocket");
/*  97 */     PROJECTILES.add("minecraft:fireball");
/*  98 */     PROJECTILES.add("minecraft:llama_spit");
/*  99 */     PROJECTILES.add("minecraft:small_fireball");
/* 100 */     PROJECTILES.add("minecraft:snowball");
/* 101 */     PROJECTILES.add("minecraft:spectral_arrow");
/* 102 */     PROJECTILES.add("minecraft:egg");
/* 103 */     PROJECTILES.add("minecraft:ender_pearl");
/* 104 */     PROJECTILES.add("minecraft:experience_bottle");
/* 105 */     PROJECTILES.add("minecraft:potion");
/* 106 */     PROJECTILES.add("minecraft:trident");
/* 107 */     PROJECTILES.add("minecraft:wither_skull");
/*     */   }
/*     */   
/*     */   public EntityUUIDFix(Schema $$0) {
/* 111 */     super($$0, References.ENTITY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/* 116 */     return fixTypeEverywhereTyped("EntityUUIDFixes", getInputSchema().getType(this.typeReference), $$0 -> {
/*     */           $$0 = $$0.update(DSL.remainderFinder(), EntityUUIDFix::updateEntityUUID);
/*     */           for (String $$1 : ABSTRACT_HORSES) {
/*     */             $$0 = updateNamedChoice($$0, $$1, EntityUUIDFix::updateAnimalOwner);
/*     */           }
/*     */           for (String $$2 : TAMEABLE_ANIMALS) {
/*     */             $$0 = updateNamedChoice($$0, $$2, EntityUUIDFix::updateAnimalOwner);
/*     */           }
/*     */           for (String $$3 : ANIMALS) {
/*     */             $$0 = updateNamedChoice($$0, $$3, EntityUUIDFix::updateAnimal);
/*     */           }
/*     */           for (String $$4 : MOBS) {
/*     */             $$0 = updateNamedChoice($$0, $$4, EntityUUIDFix::updateMob);
/*     */           }
/*     */           for (String $$5 : LIVING_ENTITIES) {
/*     */             $$0 = updateNamedChoice($$0, $$5, EntityUUIDFix::updateLivingEntity);
/*     */           }
/*     */           for (String $$6 : PROJECTILES) {
/*     */             $$0 = updateNamedChoice($$0, $$6, EntityUUIDFix::updateProjectile);
/*     */           }
/*     */           $$0 = updateNamedChoice($$0, "minecraft:bee", EntityUUIDFix::updateHurtBy);
/*     */           $$0 = updateNamedChoice($$0, "minecraft:zombified_piglin", EntityUUIDFix::updateHurtBy);
/*     */           $$0 = updateNamedChoice($$0, "minecraft:fox", EntityUUIDFix::updateFox);
/*     */           $$0 = updateNamedChoice($$0, "minecraft:item", EntityUUIDFix::updateItem);
/*     */           $$0 = updateNamedChoice($$0, "minecraft:shulker_bullet", EntityUUIDFix::updateShulkerBullet);
/*     */           $$0 = updateNamedChoice($$0, "minecraft:area_effect_cloud", EntityUUIDFix::updateAreaEffectCloud);
/*     */           $$0 = updateNamedChoice($$0, "minecraft:zombie_villager", EntityUUIDFix::updateZombieVillager);
/*     */           $$0 = updateNamedChoice($$0, "minecraft:evoker_fangs", EntityUUIDFix::updateEvokerFangs);
/*     */           return updateNamedChoice($$0, "minecraft:piglin", EntityUUIDFix::updatePiglin);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static Dynamic<?> updatePiglin(Dynamic<?> $$0) {
/* 150 */     return $$0.update("Brain", $$0 -> $$0.update("memories", ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Dynamic<?> updateEvokerFangs(Dynamic<?> $$0) {
/* 161 */     return replaceUUIDLeastMost($$0, "OwnerUUID", "Owner").orElse($$0);
/*     */   }
/*     */   
/*     */   private static Dynamic<?> updateZombieVillager(Dynamic<?> $$0) {
/* 165 */     return replaceUUIDLeastMost($$0, "ConversionPlayer", "ConversionPlayer").orElse($$0);
/*     */   }
/*     */   
/*     */   private static Dynamic<?> updateAreaEffectCloud(Dynamic<?> $$0) {
/* 169 */     return replaceUUIDLeastMost($$0, "OwnerUUID", "Owner").orElse($$0);
/*     */   }
/*     */   
/*     */   private static Dynamic<?> updateShulkerBullet(Dynamic<?> $$0) {
/* 173 */     $$0 = replaceUUIDMLTag($$0, "Owner", "Owner").orElse($$0);
/* 174 */     return replaceUUIDMLTag($$0, "Target", "Target").orElse($$0);
/*     */   }
/*     */   
/*     */   private static Dynamic<?> updateItem(Dynamic<?> $$0) {
/* 178 */     $$0 = replaceUUIDMLTag($$0, "Owner", "Owner").orElse($$0);
/* 179 */     return replaceUUIDMLTag($$0, "Thrower", "Thrower").orElse($$0);
/*     */   }
/*     */   
/*     */   private static Dynamic<?> updateFox(Dynamic<?> $$0) {
/* 183 */     Optional<Dynamic<?>> $$1 = $$0.get("TrustedUUIDs").result().map($$1 -> $$0.createList($$1.asStream().map(())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     return (Dynamic)DataFixUtils.orElse($$1.map($$1 -> $$0.remove("TrustedUUIDs").set("Trusted", $$1)), $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Dynamic<?> updateHurtBy(Dynamic<?> $$0) {
/* 197 */     return replaceUUIDString($$0, "HurtBy", "HurtBy").orElse($$0);
/*     */   }
/*     */   
/*     */   private static Dynamic<?> updateAnimalOwner(Dynamic<?> $$0) {
/* 201 */     Dynamic<?> $$1 = updateAnimal($$0);
/* 202 */     return replaceUUIDString($$1, "OwnerUUID", "Owner").orElse($$1);
/*     */   }
/*     */   
/*     */   private static Dynamic<?> updateAnimal(Dynamic<?> $$0) {
/* 206 */     Dynamic<?> $$1 = updateMob($$0);
/* 207 */     return replaceUUIDLeastMost($$1, "LoveCause", "LoveCause").orElse($$1);
/*     */   }
/*     */   
/*     */   private static Dynamic<?> updateMob(Dynamic<?> $$0) {
/* 211 */     return updateLivingEntity($$0).update("Leash", $$0 -> (Dynamic)replaceUUIDLeastMost($$0, "UUID", "UUID").orElse($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dynamic<?> updateLivingEntity(Dynamic<?> $$0) {
/* 217 */     return $$0.update("Attributes", $$1 -> $$0.createList($$1.asStream().map(())));
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
/*     */   private static Dynamic<?> updateProjectile(Dynamic<?> $$0) {
/* 229 */     return (Dynamic)DataFixUtils.orElse($$0.get("OwnerUUID").result().map($$1 -> $$0.remove("OwnerUUID").set("Owner", $$1)), $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dynamic<?> updateEntityUUID(Dynamic<?> $$0) {
/* 235 */     return replaceUUIDLeastMost($$0, "UUID", "UUID").orElse($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityUUIDFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */