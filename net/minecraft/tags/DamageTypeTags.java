/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.damagesource.DamageType;
/*    */ 
/*    */ public interface DamageTypeTags {
/*  8 */   public static final TagKey<DamageType> DAMAGES_HELMET = create("damages_helmet");
/*  9 */   public static final TagKey<DamageType> BREEZE_IMMUNE_TO = create("breeze_immune_to");
/* 10 */   public static final TagKey<DamageType> BYPASSES_ARMOR = create("bypasses_armor");
/* 11 */   public static final TagKey<DamageType> BYPASSES_SHIELD = create("bypasses_shield");
/* 12 */   public static final TagKey<DamageType> BYPASSES_INVULNERABILITY = create("bypasses_invulnerability");
/* 13 */   public static final TagKey<DamageType> BYPASSES_COOLDOWN = create("bypasses_cooldown");
/* 14 */   public static final TagKey<DamageType> BYPASSES_EFFECTS = create("bypasses_effects");
/* 15 */   public static final TagKey<DamageType> BYPASSES_RESISTANCE = create("bypasses_resistance");
/* 16 */   public static final TagKey<DamageType> BYPASSES_ENCHANTMENTS = create("bypasses_enchantments");
/* 17 */   public static final TagKey<DamageType> IS_FIRE = create("is_fire");
/* 18 */   public static final TagKey<DamageType> IS_PROJECTILE = create("is_projectile");
/* 19 */   public static final TagKey<DamageType> WITCH_RESISTANT_TO = create("witch_resistant_to");
/* 20 */   public static final TagKey<DamageType> IS_EXPLOSION = create("is_explosion");
/* 21 */   public static final TagKey<DamageType> IS_FALL = create("is_fall");
/* 22 */   public static final TagKey<DamageType> IS_DROWNING = create("is_drowning");
/* 23 */   public static final TagKey<DamageType> IS_FREEZING = create("is_freezing");
/* 24 */   public static final TagKey<DamageType> IS_LIGHTNING = create("is_lightning");
/* 25 */   public static final TagKey<DamageType> NO_ANGER = create("no_anger");
/* 26 */   public static final TagKey<DamageType> NO_IMPACT = create("no_impact");
/* 27 */   public static final TagKey<DamageType> ALWAYS_MOST_SIGNIFICANT_FALL = create("always_most_significant_fall");
/* 28 */   public static final TagKey<DamageType> WITHER_IMMUNE_TO = create("wither_immune_to");
/* 29 */   public static final TagKey<DamageType> IGNITES_ARMOR_STANDS = create("ignites_armor_stands");
/* 30 */   public static final TagKey<DamageType> BURNS_ARMOR_STANDS = create("burns_armor_stands");
/* 31 */   public static final TagKey<DamageType> AVOIDS_GUARDIAN_THORNS = create("avoids_guardian_thorns");
/* 32 */   public static final TagKey<DamageType> ALWAYS_TRIGGERS_SILVERFISH = create("always_triggers_silverfish");
/* 33 */   public static final TagKey<DamageType> ALWAYS_HURTS_ENDER_DRAGONS = create("always_hurts_ender_dragons");
/* 34 */   public static final TagKey<DamageType> NO_KNOCKBACK = create("no_knockback");
/* 35 */   public static final TagKey<DamageType> ALWAYS_KILLS_ARMOR_STANDS = create("always_kills_armor_stands");
/* 36 */   public static final TagKey<DamageType> CAN_BREAK_ARMOR_STAND = create("can_break_armor_stand");
/*    */   
/*    */   private static TagKey<DamageType> create(String $$0) {
/* 39 */     return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\DamageTypeTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */