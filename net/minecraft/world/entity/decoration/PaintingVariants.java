/*    */ package net.minecraft.world.entity.decoration;
/*    */ 
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class PaintingVariants {
/*  9 */   public static final ResourceKey<PaintingVariant> KEBAB = create("kebab");
/* 10 */   public static final ResourceKey<PaintingVariant> AZTEC = create("aztec");
/* 11 */   public static final ResourceKey<PaintingVariant> ALBAN = create("alban");
/* 12 */   public static final ResourceKey<PaintingVariant> AZTEC2 = create("aztec2");
/* 13 */   public static final ResourceKey<PaintingVariant> BOMB = create("bomb");
/* 14 */   public static final ResourceKey<PaintingVariant> PLANT = create("plant");
/* 15 */   public static final ResourceKey<PaintingVariant> WASTELAND = create("wasteland");
/* 16 */   public static final ResourceKey<PaintingVariant> POOL = create("pool");
/* 17 */   public static final ResourceKey<PaintingVariant> COURBET = create("courbet");
/* 18 */   public static final ResourceKey<PaintingVariant> SEA = create("sea");
/* 19 */   public static final ResourceKey<PaintingVariant> SUNSET = create("sunset");
/* 20 */   public static final ResourceKey<PaintingVariant> CREEBET = create("creebet");
/* 21 */   public static final ResourceKey<PaintingVariant> WANDERER = create("wanderer");
/* 22 */   public static final ResourceKey<PaintingVariant> GRAHAM = create("graham");
/* 23 */   public static final ResourceKey<PaintingVariant> MATCH = create("match");
/* 24 */   public static final ResourceKey<PaintingVariant> BUST = create("bust");
/* 25 */   public static final ResourceKey<PaintingVariant> STAGE = create("stage");
/* 26 */   public static final ResourceKey<PaintingVariant> VOID = create("void");
/* 27 */   public static final ResourceKey<PaintingVariant> SKULL_AND_ROSES = create("skull_and_roses");
/* 28 */   public static final ResourceKey<PaintingVariant> WITHER = create("wither");
/* 29 */   public static final ResourceKey<PaintingVariant> FIGHTERS = create("fighters");
/* 30 */   public static final ResourceKey<PaintingVariant> POINTER = create("pointer");
/* 31 */   public static final ResourceKey<PaintingVariant> PIGSCENE = create("pigscene");
/* 32 */   public static final ResourceKey<PaintingVariant> BURNING_SKULL = create("burning_skull");
/* 33 */   public static final ResourceKey<PaintingVariant> SKELETON = create("skeleton");
/* 34 */   public static final ResourceKey<PaintingVariant> DONKEY_KONG = create("donkey_kong");
/* 35 */   public static final ResourceKey<PaintingVariant> EARTH = create("earth");
/* 36 */   public static final ResourceKey<PaintingVariant> WIND = create("wind");
/* 37 */   public static final ResourceKey<PaintingVariant> WATER = create("water");
/* 38 */   public static final ResourceKey<PaintingVariant> FIRE = create("fire");
/*    */   
/*    */   public static PaintingVariant bootstrap(Registry<PaintingVariant> $$0) {
/* 41 */     Registry.register($$0, KEBAB, new PaintingVariant(16, 16));
/* 42 */     Registry.register($$0, AZTEC, new PaintingVariant(16, 16));
/* 43 */     Registry.register($$0, ALBAN, new PaintingVariant(16, 16));
/* 44 */     Registry.register($$0, AZTEC2, new PaintingVariant(16, 16));
/* 45 */     Registry.register($$0, BOMB, new PaintingVariant(16, 16));
/* 46 */     Registry.register($$0, PLANT, new PaintingVariant(16, 16));
/* 47 */     Registry.register($$0, WASTELAND, new PaintingVariant(16, 16));
/* 48 */     Registry.register($$0, POOL, new PaintingVariant(32, 16));
/* 49 */     Registry.register($$0, COURBET, new PaintingVariant(32, 16));
/* 50 */     Registry.register($$0, SEA, new PaintingVariant(32, 16));
/* 51 */     Registry.register($$0, SUNSET, new PaintingVariant(32, 16));
/* 52 */     Registry.register($$0, CREEBET, new PaintingVariant(32, 16));
/* 53 */     Registry.register($$0, WANDERER, new PaintingVariant(16, 32));
/* 54 */     Registry.register($$0, GRAHAM, new PaintingVariant(16, 32));
/* 55 */     Registry.register($$0, MATCH, new PaintingVariant(32, 32));
/* 56 */     Registry.register($$0, BUST, new PaintingVariant(32, 32));
/* 57 */     Registry.register($$0, STAGE, new PaintingVariant(32, 32));
/* 58 */     Registry.register($$0, VOID, new PaintingVariant(32, 32));
/* 59 */     Registry.register($$0, SKULL_AND_ROSES, new PaintingVariant(32, 32));
/* 60 */     Registry.register($$0, WITHER, new PaintingVariant(32, 32));
/* 61 */     Registry.register($$0, FIGHTERS, new PaintingVariant(64, 32));
/* 62 */     Registry.register($$0, POINTER, new PaintingVariant(64, 64));
/* 63 */     Registry.register($$0, PIGSCENE, new PaintingVariant(64, 64));
/* 64 */     Registry.register($$0, BURNING_SKULL, new PaintingVariant(64, 64));
/* 65 */     Registry.register($$0, SKELETON, new PaintingVariant(64, 48));
/* 66 */     Registry.register($$0, EARTH, new PaintingVariant(32, 32));
/* 67 */     Registry.register($$0, WIND, new PaintingVariant(32, 32));
/* 68 */     Registry.register($$0, WATER, new PaintingVariant(32, 32));
/* 69 */     Registry.register($$0, FIRE, new PaintingVariant(32, 32));
/* 70 */     return (PaintingVariant)Registry.register($$0, DONKEY_KONG, new PaintingVariant(64, 48));
/*    */   }
/*    */   
/*    */   private static ResourceKey<PaintingVariant> create(String $$0) {
/* 74 */     return ResourceKey.create(Registries.PAINTING_VARIANT, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\decoration\PaintingVariants.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */