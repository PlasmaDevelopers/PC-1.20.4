/*     */ package net.minecraft.data.tags;
/*     */ 
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.PackOutput;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.world.damagesource.DamageType;
/*     */ import net.minecraft.world.damagesource.DamageTypes;
/*     */ 
/*     */ public class DamageTypeTagsProvider extends TagsProvider<DamageType> {
/*     */   public DamageTypeTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/*  14 */     super($$0, Registries.DAMAGE_TYPE, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addTags(HolderLookup.Provider $$0) {
/*  19 */     tag(DamageTypeTags.DAMAGES_HELMET).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.FALLING_ANVIL, DamageTypes.FALLING_BLOCK, DamageTypes.FALLING_STALACTITE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  25 */     tag(DamageTypeTags.BYPASSES_ARMOR).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.ON_FIRE, DamageTypes.IN_WALL, DamageTypes.CRAMMING, DamageTypes.DROWN, DamageTypes.FLY_INTO_WALL, DamageTypes.GENERIC, DamageTypes.WITHER, DamageTypes.DRAGON_BREATH, DamageTypes.STARVE, DamageTypes.FALL, DamageTypes.FREEZE, DamageTypes.STALAGMITE, DamageTypes.MAGIC, DamageTypes.INDIRECT_MAGIC, DamageTypes.FELL_OUT_OF_WORLD, DamageTypes.GENERIC_KILL, DamageTypes.SONIC_BOOM, DamageTypes.OUTSIDE_BORDER });
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
/*  46 */     tag(DamageTypeTags.BYPASSES_SHIELD).addTag(DamageTypeTags.BYPASSES_ARMOR).add(new ResourceKey[] { DamageTypes.FALLING_ANVIL, DamageTypes.FALLING_STALACTITE });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  51 */     tag(DamageTypeTags.BYPASSES_INVULNERABILITY).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.FELL_OUT_OF_WORLD, DamageTypes.GENERIC_KILL });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     tag(DamageTypeTags.BYPASSES_EFFECTS).add(DamageTypes.STARVE);
/*     */ 
/*     */ 
/*     */     
/*  60 */     tag(DamageTypeTags.BYPASSES_RESISTANCE).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.FELL_OUT_OF_WORLD, DamageTypes.GENERIC_KILL });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(DamageTypes.SONIC_BOOM);
/*     */ 
/*     */ 
/*     */     
/*  69 */     tag(DamageTypeTags.IS_FIRE).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.IN_FIRE, DamageTypes.ON_FIRE, DamageTypes.LAVA, DamageTypes.HOT_FLOOR, DamageTypes.UNATTRIBUTED_FIREBALL, DamageTypes.FIREBALL });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     tag(DamageTypeTags.IS_PROJECTILE).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.ARROW, DamageTypes.TRIDENT, DamageTypes.MOB_PROJECTILE, DamageTypes.UNATTRIBUTED_FIREBALL, DamageTypes.FIREBALL, DamageTypes.WITHER_SKULL, DamageTypes.THROWN });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     tag(DamageTypeTags.WITCH_RESISTANT_TO).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.MAGIC, DamageTypes.INDIRECT_MAGIC, DamageTypes.SONIC_BOOM, DamageTypes.THORNS });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     tag(DamageTypeTags.IS_EXPLOSION).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.FIREWORKS, DamageTypes.EXPLOSION, DamageTypes.PLAYER_EXPLOSION, DamageTypes.BAD_RESPAWN_POINT });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     tag(DamageTypeTags.IS_FALL).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.FALL, DamageTypes.STALAGMITE });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     tag(DamageTypeTags.IS_DROWNING).add(DamageTypes.DROWN);
/*     */ 
/*     */ 
/*     */     
/* 111 */     tag(DamageTypeTags.IS_FREEZING).add(DamageTypes.FREEZE);
/*     */ 
/*     */ 
/*     */     
/* 115 */     tag(DamageTypeTags.IS_LIGHTNING).add(DamageTypes.LIGHTNING_BOLT);
/*     */ 
/*     */ 
/*     */     
/* 119 */     tag(DamageTypeTags.NO_ANGER).add(DamageTypes.MOB_ATTACK_NO_AGGRO);
/*     */ 
/*     */ 
/*     */     
/* 123 */     tag(DamageTypeTags.NO_IMPACT).add(DamageTypes.DROWN);
/*     */ 
/*     */ 
/*     */     
/* 127 */     tag(DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL).add(DamageTypes.FELL_OUT_OF_WORLD);
/*     */ 
/*     */ 
/*     */     
/* 131 */     tag(DamageTypeTags.WITHER_IMMUNE_TO).add(DamageTypes.DROWN);
/*     */ 
/*     */ 
/*     */     
/* 135 */     tag(DamageTypeTags.IGNITES_ARMOR_STANDS).add(DamageTypes.IN_FIRE);
/*     */ 
/*     */ 
/*     */     
/* 139 */     tag(DamageTypeTags.BURNS_ARMOR_STANDS).add(DamageTypes.ON_FIRE);
/*     */ 
/*     */ 
/*     */     
/* 143 */     tag(DamageTypeTags.AVOIDS_GUARDIAN_THORNS).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.MAGIC, DamageTypes.THORNS
/*     */ 
/*     */         
/* 146 */         }).addTag(DamageTypeTags.IS_EXPLOSION);
/*     */     
/* 148 */     tag(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH).add(DamageTypes.MAGIC);
/*     */ 
/*     */ 
/*     */     
/* 152 */     tag(DamageTypeTags.ALWAYS_HURTS_ENDER_DRAGONS).addTag(DamageTypeTags.IS_EXPLOSION);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     tag(DamageTypeTags.NO_KNOCKBACK).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.EXPLOSION, DamageTypes.PLAYER_EXPLOSION, DamageTypes.BAD_RESPAWN_POINT });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     tag(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.ARROW, DamageTypes.TRIDENT, DamageTypes.MOB_PROJECTILE, DamageTypes.FIREBALL, DamageTypes.WITHER_SKULL });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     tag(DamageTypeTags.CAN_BREAK_ARMOR_STAND).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.PLAYER_ATTACK, DamageTypes.PLAYER_EXPLOSION });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\DamageTypeTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */