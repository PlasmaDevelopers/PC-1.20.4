/*     */ package net.minecraft.advancements.critereon;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.Cat;
/*     */ import net.minecraft.world.entity.animal.CatVariant;
/*     */ import net.minecraft.world.entity.animal.Fox;
/*     */ import net.minecraft.world.entity.animal.FrogVariant;
/*     */ import net.minecraft.world.entity.animal.MushroomCow;
/*     */ import net.minecraft.world.entity.animal.Parrot;
/*     */ import net.minecraft.world.entity.animal.Rabbit;
/*     */ import net.minecraft.world.entity.animal.TropicalFish;
/*     */ import net.minecraft.world.entity.animal.axolotl.Axolotl;
/*     */ import net.minecraft.world.entity.animal.frog.Frog;
/*     */ import net.minecraft.world.entity.animal.horse.Horse;
/*     */ import net.minecraft.world.entity.animal.horse.Llama;
/*     */ import net.minecraft.world.entity.animal.horse.Variant;
/*     */ import net.minecraft.world.entity.decoration.Painting;
/*     */ import net.minecraft.world.entity.npc.VillagerDataHolder;
/*     */ import net.minecraft.world.entity.npc.VillagerType;
/*     */ import net.minecraft.world.entity.vehicle.Boat;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public interface EntitySubPredicate {
/*     */   public static final Codec<EntitySubPredicate> CODEC;
/*     */   
/*     */   static {
/*  36 */     CODEC = Types.TYPE_CODEC.dispatch(EntitySubPredicate::type, $$0 -> $$0.codec().codec());
/*     */   } public static final class Type extends Record { private final MapCodec<? extends EntitySubPredicate> codec;
/*  38 */     public Type(MapCodec<? extends EntitySubPredicate> $$0) { this.codec = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/EntitySubPredicate$Type;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #38	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  38 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntitySubPredicate$Type; } public MapCodec<? extends EntitySubPredicate> codec() { return this.codec; } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/EntitySubPredicate$Type;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #38	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntitySubPredicate$Type;
/*     */     } public final boolean equals(Object $$0) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/EntitySubPredicate$Type;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #38	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/EntitySubPredicate$Type;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*  42 */     } } public static final class Types { public static final EntitySubPredicate.Type ANY = new EntitySubPredicate.Type(MapCodec.unit(new EntitySubPredicate()
/*     */           {
/*     */             public boolean matches(Entity $$0, ServerLevel $$1, @Nullable Vec3 $$2) {
/*  45 */               return true;
/*     */             }
/*     */ 
/*     */             
/*     */             public EntitySubPredicate.Type type() {
/*  50 */               return EntitySubPredicate.Types.ANY;
/*     */             }
/*     */           }));
/*  53 */     public static final EntitySubPredicate.Type LIGHTNING = new EntitySubPredicate.Type((MapCodec)LightningBoltPredicate.CODEC);
/*  54 */     public static final EntitySubPredicate.Type FISHING_HOOK = new EntitySubPredicate.Type((MapCodec)FishingHookPredicate.CODEC);
/*  55 */     public static final EntitySubPredicate.Type PLAYER = new EntitySubPredicate.Type((MapCodec)PlayerPredicate.CODEC);
/*  56 */     public static final EntitySubPredicate.Type SLIME = new EntitySubPredicate.Type((MapCodec)SlimePredicate.CODEC); public static final EntityVariantPredicate<CatVariant> CAT; public static final EntityVariantPredicate<FrogVariant> FROG; public static final EntityVariantPredicate<Axolotl.Variant> AXOLOTL; public static final EntityVariantPredicate<Boat.Type> BOAT; public static final EntityVariantPredicate<Fox.Type> FOX; public static final EntityVariantPredicate<MushroomCow.MushroomType> MOOSHROOM; public static final EntityVariantPredicate<Holder<PaintingVariant>> PAINTING; public static final EntityVariantPredicate<Rabbit.Variant> RABBIT; public static final EntityVariantPredicate<Variant> HORSE; public static final EntityVariantPredicate<Llama.Variant> LLAMA; public static final EntityVariantPredicate<VillagerType> VILLAGER; public static final EntityVariantPredicate<Parrot.Variant> PARROT; public static final EntityVariantPredicate<TropicalFish.Pattern> TROPICAL_FISH;
/*  57 */     static { CAT = EntityVariantPredicate.create(BuiltInRegistries.CAT_VARIANT, $$0 -> { Cat $$1 = (Cat)$$0; return (Function)(($$0 instanceof Cat) ? Optional.<CatVariant>of($$1.getVariant()) : Optional.empty());
/*  58 */           }); FROG = EntityVariantPredicate.create(BuiltInRegistries.FROG_VARIANT, $$0 -> { Frog $$1 = (Frog)$$0; return ($$0 instanceof Frog) ? Optional.<FrogVariant>of($$1.getVariant()) : Optional.empty();
/*  59 */           }); AXOLOTL = EntityVariantPredicate.create(Axolotl.Variant.CODEC, $$0 -> { Axolotl $$1 = (Axolotl)$$0; return ($$0 instanceof Axolotl) ? Optional.<Axolotl.Variant>of($$1.getVariant()) : Optional.empty();
/*  60 */           }); BOAT = EntityVariantPredicate.create((Codec<Boat.Type>)Boat.Type.CODEC, $$0 -> { Boat $$1 = (Boat)$$0; return ($$0 instanceof Boat) ? Optional.<Boat.Type>of($$1.getVariant()) : Optional.empty();
/*  61 */           }); FOX = EntityVariantPredicate.create((Codec<Fox.Type>)Fox.Type.CODEC, $$0 -> { Fox $$1 = (Fox)$$0; return ($$0 instanceof Fox) ? Optional.<Fox.Type>of($$1.getVariant()) : Optional.empty();
/*  62 */           }); MOOSHROOM = EntityVariantPredicate.create((Codec<MushroomCow.MushroomType>)MushroomCow.MushroomType.CODEC, $$0 -> { MushroomCow $$1 = (MushroomCow)$$0; return ($$0 instanceof MushroomCow) ? Optional.<MushroomCow.MushroomType>of($$1.getVariant()) : Optional.empty();
/*  63 */           }); PAINTING = EntityVariantPredicate.create(BuiltInRegistries.PAINTING_VARIANT.holderByNameCodec(), $$0 -> { Painting $$1 = (Painting)$$0; return ($$0 instanceof Painting) ? Optional.<Holder>of($$1.getVariant()) : Optional.empty();
/*  64 */           }); RABBIT = EntityVariantPredicate.create(Rabbit.Variant.CODEC, $$0 -> { Rabbit $$1 = (Rabbit)$$0; return ($$0 instanceof Rabbit) ? Optional.<Rabbit.Variant>of($$1.getVariant()) : Optional.empty();
/*  65 */           }); HORSE = EntityVariantPredicate.create(Variant.CODEC, $$0 -> { Horse $$1 = (Horse)$$0; return ($$0 instanceof Horse) ? Optional.<Variant>of($$1.getVariant()) : Optional.empty();
/*  66 */           }); LLAMA = EntityVariantPredicate.create(Llama.Variant.CODEC, $$0 -> { Llama $$1 = (Llama)$$0; return ($$0 instanceof Llama) ? Optional.<Llama.Variant>of($$1.getVariant()) : Optional.empty();
/*  67 */           }); VILLAGER = EntityVariantPredicate.create(BuiltInRegistries.VILLAGER_TYPE.byNameCodec(), $$0 -> { VillagerDataHolder $$1 = (VillagerDataHolder)$$0; return ($$0 instanceof VillagerDataHolder) ? Optional.<VillagerType>of($$1.getVariant()) : Optional.empty();
/*  68 */           }); PARROT = EntityVariantPredicate.create(Parrot.Variant.CODEC, $$0 -> { Parrot $$1 = (Parrot)$$0; return ($$0 instanceof Parrot) ? Optional.<Parrot.Variant>of($$1.getVariant()) : Optional.empty();
/*  69 */           }); TROPICAL_FISH = EntityVariantPredicate.create(TropicalFish.Pattern.CODEC, $$0 -> {
/*     */             TropicalFish $$1 = (TropicalFish)$$0; return ($$0 instanceof TropicalFish) ? Optional.<TropicalFish.Pattern>of($$1.getVariant()) : Optional.empty();
/*  71 */           }); } public static final BiMap<String, EntitySubPredicate.Type> TYPES = (BiMap<String, EntitySubPredicate.Type>)ImmutableBiMap.builder()
/*  72 */       .put("any", ANY)
/*  73 */       .put("lightning", LIGHTNING)
/*  74 */       .put("fishing_hook", FISHING_HOOK)
/*  75 */       .put("player", PLAYER)
/*  76 */       .put("slime", SLIME)
/*  77 */       .put("cat", CAT.type())
/*  78 */       .put("frog", FROG.type())
/*  79 */       .put("axolotl", AXOLOTL.type())
/*  80 */       .put("boat", BOAT.type())
/*  81 */       .put("fox", FOX.type())
/*  82 */       .put("mooshroom", MOOSHROOM.type())
/*  83 */       .put("painting", PAINTING.type())
/*  84 */       .put("rabbit", RABBIT.type())
/*  85 */       .put("horse", HORSE.type())
/*  86 */       .put("llama", LLAMA.type())
/*  87 */       .put("villager", VILLAGER.type())
/*  88 */       .put("parrot", PARROT.type())
/*  89 */       .put("tropical_fish", TROPICAL_FISH.type())
/*  90 */       .buildOrThrow();
/*     */     
/*  92 */     public static final Codec<EntitySubPredicate.Type> TYPE_CODEC = ExtraCodecs.stringResolverCodec(TYPES.inverse()::get, TYPES::get); static { Objects.requireNonNull(TYPES.inverse()); Objects.requireNonNull(TYPES); }
/*     */      } class null implements EntitySubPredicate { public boolean matches(Entity $$0, ServerLevel $$1, @Nullable Vec3 $$2) {
/*     */       return true;
/*     */     }
/*     */     public EntitySubPredicate.Type type() {
/*     */       return EntitySubPredicate.Types.ANY;
/*     */     } }
/*     */   static EntitySubPredicate variant(CatVariant $$0) {
/* 100 */     return Types.CAT.createPredicate($$0);
/*     */   }
/*     */   
/*     */   static EntitySubPredicate variant(FrogVariant $$0) {
/* 104 */     return Types.FROG.createPredicate($$0);
/*     */   }
/*     */   
/*     */   boolean matches(Entity paramEntity, ServerLevel paramServerLevel, @Nullable Vec3 paramVec3);
/*     */   
/*     */   Type type();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntitySubPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */