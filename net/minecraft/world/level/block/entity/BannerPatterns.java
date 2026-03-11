/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class BannerPatterns {
/*  9 */   public static final ResourceKey<BannerPattern> BASE = create("base");
/* 10 */   public static final ResourceKey<BannerPattern> SQUARE_BOTTOM_LEFT = create("square_bottom_left");
/* 11 */   public static final ResourceKey<BannerPattern> SQUARE_BOTTOM_RIGHT = create("square_bottom_right");
/* 12 */   public static final ResourceKey<BannerPattern> SQUARE_TOP_LEFT = create("square_top_left");
/* 13 */   public static final ResourceKey<BannerPattern> SQUARE_TOP_RIGHT = create("square_top_right");
/* 14 */   public static final ResourceKey<BannerPattern> STRIPE_BOTTOM = create("stripe_bottom");
/* 15 */   public static final ResourceKey<BannerPattern> STRIPE_TOP = create("stripe_top");
/* 16 */   public static final ResourceKey<BannerPattern> STRIPE_LEFT = create("stripe_left");
/* 17 */   public static final ResourceKey<BannerPattern> STRIPE_RIGHT = create("stripe_right");
/* 18 */   public static final ResourceKey<BannerPattern> STRIPE_CENTER = create("stripe_center");
/* 19 */   public static final ResourceKey<BannerPattern> STRIPE_MIDDLE = create("stripe_middle");
/* 20 */   public static final ResourceKey<BannerPattern> STRIPE_DOWNRIGHT = create("stripe_downright");
/* 21 */   public static final ResourceKey<BannerPattern> STRIPE_DOWNLEFT = create("stripe_downleft");
/* 22 */   public static final ResourceKey<BannerPattern> STRIPE_SMALL = create("small_stripes");
/* 23 */   public static final ResourceKey<BannerPattern> CROSS = create("cross");
/* 24 */   public static final ResourceKey<BannerPattern> STRAIGHT_CROSS = create("straight_cross");
/* 25 */   public static final ResourceKey<BannerPattern> TRIANGLE_BOTTOM = create("triangle_bottom");
/* 26 */   public static final ResourceKey<BannerPattern> TRIANGLE_TOP = create("triangle_top");
/* 27 */   public static final ResourceKey<BannerPattern> TRIANGLES_BOTTOM = create("triangles_bottom");
/* 28 */   public static final ResourceKey<BannerPattern> TRIANGLES_TOP = create("triangles_top");
/* 29 */   public static final ResourceKey<BannerPattern> DIAGONAL_LEFT = create("diagonal_left");
/* 30 */   public static final ResourceKey<BannerPattern> DIAGONAL_RIGHT = create("diagonal_up_right");
/* 31 */   public static final ResourceKey<BannerPattern> DIAGONAL_LEFT_MIRROR = create("diagonal_up_left");
/* 32 */   public static final ResourceKey<BannerPattern> DIAGONAL_RIGHT_MIRROR = create("diagonal_right");
/* 33 */   public static final ResourceKey<BannerPattern> CIRCLE_MIDDLE = create("circle");
/* 34 */   public static final ResourceKey<BannerPattern> RHOMBUS_MIDDLE = create("rhombus");
/* 35 */   public static final ResourceKey<BannerPattern> HALF_VERTICAL = create("half_vertical");
/* 36 */   public static final ResourceKey<BannerPattern> HALF_HORIZONTAL = create("half_horizontal");
/* 37 */   public static final ResourceKey<BannerPattern> HALF_VERTICAL_MIRROR = create("half_vertical_right");
/* 38 */   public static final ResourceKey<BannerPattern> HALF_HORIZONTAL_MIRROR = create("half_horizontal_bottom");
/* 39 */   public static final ResourceKey<BannerPattern> BORDER = create("border");
/* 40 */   public static final ResourceKey<BannerPattern> CURLY_BORDER = create("curly_border");
/* 41 */   public static final ResourceKey<BannerPattern> GRADIENT = create("gradient");
/* 42 */   public static final ResourceKey<BannerPattern> GRADIENT_UP = create("gradient_up");
/* 43 */   public static final ResourceKey<BannerPattern> BRICKS = create("bricks");
/* 44 */   public static final ResourceKey<BannerPattern> GLOBE = create("globe");
/* 45 */   public static final ResourceKey<BannerPattern> CREEPER = create("creeper");
/* 46 */   public static final ResourceKey<BannerPattern> SKULL = create("skull");
/* 47 */   public static final ResourceKey<BannerPattern> FLOWER = create("flower");
/* 48 */   public static final ResourceKey<BannerPattern> MOJANG = create("mojang");
/* 49 */   public static final ResourceKey<BannerPattern> PIGLIN = create("piglin");
/*    */   
/*    */   private static ResourceKey<BannerPattern> create(String $$0) {
/* 52 */     return ResourceKey.create(Registries.BANNER_PATTERN, new ResourceLocation($$0));
/*    */   }
/*    */   
/*    */   public static BannerPattern bootstrap(Registry<BannerPattern> $$0) {
/* 56 */     Registry.register($$0, BASE, new BannerPattern("b"));
/* 57 */     Registry.register($$0, SQUARE_BOTTOM_LEFT, new BannerPattern("bl"));
/* 58 */     Registry.register($$0, SQUARE_BOTTOM_RIGHT, new BannerPattern("br"));
/* 59 */     Registry.register($$0, SQUARE_TOP_LEFT, new BannerPattern("tl"));
/* 60 */     Registry.register($$0, SQUARE_TOP_RIGHT, new BannerPattern("tr"));
/* 61 */     Registry.register($$0, STRIPE_BOTTOM, new BannerPattern("bs"));
/* 62 */     Registry.register($$0, STRIPE_TOP, new BannerPattern("ts"));
/* 63 */     Registry.register($$0, STRIPE_LEFT, new BannerPattern("ls"));
/* 64 */     Registry.register($$0, STRIPE_RIGHT, new BannerPattern("rs"));
/* 65 */     Registry.register($$0, STRIPE_CENTER, new BannerPattern("cs"));
/* 66 */     Registry.register($$0, STRIPE_MIDDLE, new BannerPattern("ms"));
/* 67 */     Registry.register($$0, STRIPE_DOWNRIGHT, new BannerPattern("drs"));
/* 68 */     Registry.register($$0, STRIPE_DOWNLEFT, new BannerPattern("dls"));
/* 69 */     Registry.register($$0, STRIPE_SMALL, new BannerPattern("ss"));
/* 70 */     Registry.register($$0, CROSS, new BannerPattern("cr"));
/* 71 */     Registry.register($$0, STRAIGHT_CROSS, new BannerPattern("sc"));
/* 72 */     Registry.register($$0, TRIANGLE_BOTTOM, new BannerPattern("bt"));
/* 73 */     Registry.register($$0, TRIANGLE_TOP, new BannerPattern("tt"));
/* 74 */     Registry.register($$0, TRIANGLES_BOTTOM, new BannerPattern("bts"));
/* 75 */     Registry.register($$0, TRIANGLES_TOP, new BannerPattern("tts"));
/* 76 */     Registry.register($$0, DIAGONAL_LEFT, new BannerPattern("ld"));
/* 77 */     Registry.register($$0, DIAGONAL_RIGHT, new BannerPattern("rd"));
/* 78 */     Registry.register($$0, DIAGONAL_LEFT_MIRROR, new BannerPattern("lud"));
/* 79 */     Registry.register($$0, DIAGONAL_RIGHT_MIRROR, new BannerPattern("rud"));
/* 80 */     Registry.register($$0, CIRCLE_MIDDLE, new BannerPattern("mc"));
/* 81 */     Registry.register($$0, RHOMBUS_MIDDLE, new BannerPattern("mr"));
/* 82 */     Registry.register($$0, HALF_VERTICAL, new BannerPattern("vh"));
/* 83 */     Registry.register($$0, HALF_HORIZONTAL, new BannerPattern("hh"));
/* 84 */     Registry.register($$0, HALF_VERTICAL_MIRROR, new BannerPattern("vhr"));
/* 85 */     Registry.register($$0, HALF_HORIZONTAL_MIRROR, new BannerPattern("hhb"));
/* 86 */     Registry.register($$0, BORDER, new BannerPattern("bo"));
/* 87 */     Registry.register($$0, CURLY_BORDER, new BannerPattern("cbo"));
/* 88 */     Registry.register($$0, GRADIENT, new BannerPattern("gra"));
/* 89 */     Registry.register($$0, GRADIENT_UP, new BannerPattern("gru"));
/* 90 */     Registry.register($$0, BRICKS, new BannerPattern("bri"));
/*    */     
/* 92 */     Registry.register($$0, GLOBE, new BannerPattern("glb"));
/* 93 */     Registry.register($$0, CREEPER, new BannerPattern("cre"));
/* 94 */     Registry.register($$0, SKULL, new BannerPattern("sku"));
/* 95 */     Registry.register($$0, FLOWER, new BannerPattern("flo"));
/* 96 */     Registry.register($$0, MOJANG, new BannerPattern("moj"));
/* 97 */     return (BannerPattern)Registry.register($$0, PIGLIN, new BannerPattern("pig"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BannerPatterns.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */