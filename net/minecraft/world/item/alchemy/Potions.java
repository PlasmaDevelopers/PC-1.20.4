/*    */ package net.minecraft.world.item.alchemy;
/*    */ 
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ 
/*    */ public class Potions {
/* 12 */   public static ResourceKey<Potion> EMPTY_ID = ResourceKey.create(Registries.POTION, new ResourceLocation("empty"));
/*    */   
/* 14 */   public static final Potion EMPTY = register(EMPTY_ID, new Potion(new MobEffectInstance[0]));
/* 15 */   public static final Potion WATER = register("water", new Potion(new MobEffectInstance[0]));
/* 16 */   public static final Potion MUNDANE = register("mundane", new Potion(new MobEffectInstance[0]));
/* 17 */   public static final Potion THICK = register("thick", new Potion(new MobEffectInstance[0]));
/* 18 */   public static final Potion AWKWARD = register("awkward", new Potion(new MobEffectInstance[0]));
/*    */   
/* 20 */   public static final Potion NIGHT_VISION = register("night_vision", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.NIGHT_VISION, 3600) }));
/* 21 */   public static final Potion LONG_NIGHT_VISION = register("long_night_vision", new Potion("night_vision", new MobEffectInstance[] { new MobEffectInstance(MobEffects.NIGHT_VISION, 9600) }));
/*    */   
/* 23 */   public static final Potion INVISIBILITY = register("invisibility", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.INVISIBILITY, 3600) }));
/* 24 */   public static final Potion LONG_INVISIBILITY = register("long_invisibility", new Potion("invisibility", new MobEffectInstance[] { new MobEffectInstance(MobEffects.INVISIBILITY, 9600) }));
/*    */   
/* 26 */   public static final Potion LEAPING = register("leaping", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.JUMP, 3600) }));
/* 27 */   public static final Potion LONG_LEAPING = register("long_leaping", new Potion("leaping", new MobEffectInstance[] { new MobEffectInstance(MobEffects.JUMP, 9600) }));
/* 28 */   public static final Potion STRONG_LEAPING = register("strong_leaping", new Potion("leaping", new MobEffectInstance[] { new MobEffectInstance(MobEffects.JUMP, 1800, 1) }));
/*    */   
/* 30 */   public static final Potion FIRE_RESISTANCE = register("fire_resistance", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 3600) }));
/* 31 */   public static final Potion LONG_FIRE_RESISTANCE = register("long_fire_resistance", new Potion("fire_resistance", new MobEffectInstance[] { new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 9600) }));
/*    */   
/* 33 */   public static final Potion SWIFTNESS = register("swiftness", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600) }));
/* 34 */   public static final Potion LONG_SWIFTNESS = register("long_swiftness", new Potion("swiftness", new MobEffectInstance[] { new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 9600) }));
/* 35 */   public static final Potion STRONG_SWIFTNESS = register("strong_swiftness", new Potion("swiftness", new MobEffectInstance[] { new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1800, 1) }));
/*    */   
/* 37 */   public static final Potion SLOWNESS = register("slowness", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1800) }));
/* 38 */   public static final Potion LONG_SLOWNESS = register("long_slowness", new Potion("slowness", new MobEffectInstance[] { new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 4800) }));
/* 39 */   public static final Potion STRONG_SLOWNESS = register("strong_slowness", new Potion("slowness", new MobEffectInstance[] { new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 3) }));
/*    */   
/* 41 */   public static final Potion TURTLE_MASTER = register("turtle_master", new Potion("turtle_master", new MobEffectInstance[] { new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 3), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 2) }));
/* 42 */   public static final Potion LONG_TURTLE_MASTER = register("long_turtle_master", new Potion("turtle_master", new MobEffectInstance[] { new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 800, 3), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 800, 2) }));
/* 43 */   public static final Potion STRONG_TURTLE_MASTER = register("strong_turtle_master", new Potion("turtle_master", new MobEffectInstance[] { new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 5), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 3) }));
/*    */   
/* 45 */   public static final Potion WATER_BREATHING = register("water_breathing", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.WATER_BREATHING, 3600) }));
/* 46 */   public static final Potion LONG_WATER_BREATHING = register("long_water_breathing", new Potion("water_breathing", new MobEffectInstance[] { new MobEffectInstance(MobEffects.WATER_BREATHING, 9600) }));
/*    */   
/* 48 */   public static final Potion HEALING = register("healing", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.HEAL, 1) }));
/* 49 */   public static final Potion STRONG_HEALING = register("strong_healing", new Potion("healing", new MobEffectInstance[] { new MobEffectInstance(MobEffects.HEAL, 1, 1) }));
/*    */   
/* 51 */   public static final Potion HARMING = register("harming", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.HARM, 1) }));
/* 52 */   public static final Potion STRONG_HARMING = register("strong_harming", new Potion("harming", new MobEffectInstance[] { new MobEffectInstance(MobEffects.HARM, 1, 1) }));
/*    */   
/* 54 */   public static final Potion POISON = register("poison", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.POISON, 900) }));
/* 55 */   public static final Potion LONG_POISON = register("long_poison", new Potion("poison", new MobEffectInstance[] { new MobEffectInstance(MobEffects.POISON, 1800) }));
/* 56 */   public static final Potion STRONG_POISON = register("strong_poison", new Potion("poison", new MobEffectInstance[] { new MobEffectInstance(MobEffects.POISON, 432, 1) }));
/*    */   
/* 58 */   public static final Potion REGENERATION = register("regeneration", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.REGENERATION, 900) }));
/* 59 */   public static final Potion LONG_REGENERATION = register("long_regeneration", new Potion("regeneration", new MobEffectInstance[] { new MobEffectInstance(MobEffects.REGENERATION, 1800) }));
/* 60 */   public static final Potion STRONG_REGENERATION = register("strong_regeneration", new Potion("regeneration", new MobEffectInstance[] { new MobEffectInstance(MobEffects.REGENERATION, 450, 1) }));
/*    */   
/* 62 */   public static final Potion STRENGTH = register("strength", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600) }));
/* 63 */   public static final Potion LONG_STRENGTH = register("long_strength", new Potion("strength", new MobEffectInstance[] { new MobEffectInstance(MobEffects.DAMAGE_BOOST, 9600) }));
/* 64 */   public static final Potion STRONG_STRENGTH = register("strong_strength", new Potion("strength", new MobEffectInstance[] { new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1800, 1) }));
/*    */   
/* 66 */   public static final Potion WEAKNESS = register("weakness", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.WEAKNESS, 1800) }));
/* 67 */   public static final Potion LONG_WEAKNESS = register("long_weakness", new Potion("weakness", new MobEffectInstance[] { new MobEffectInstance(MobEffects.WEAKNESS, 4800) }));
/*    */   
/* 69 */   public static final Potion LUCK = register("luck", new Potion("luck", new MobEffectInstance[] { new MobEffectInstance(MobEffects.LUCK, 6000) }));
/*    */   
/* 71 */   public static final Potion SLOW_FALLING = register("slow_falling", new Potion(new MobEffectInstance[] { new MobEffectInstance(MobEffects.SLOW_FALLING, 1800) }));
/* 72 */   public static final Potion LONG_SLOW_FALLING = register("long_slow_falling", new Potion("slow_falling", new MobEffectInstance[] { new MobEffectInstance(MobEffects.SLOW_FALLING, 4800) }));
/*    */   
/*    */   private static Potion register(String $$0, Potion $$1) {
/* 75 */     return (Potion)Registry.register((Registry)BuiltInRegistries.POTION, $$0, $$1);
/*    */   }
/*    */   
/*    */   private static Potion register(ResourceKey<Potion> $$0, Potion $$1) {
/* 79 */     return (Potion)Registry.register((Registry)BuiltInRegistries.POTION, $$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\alchemy\Potions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */