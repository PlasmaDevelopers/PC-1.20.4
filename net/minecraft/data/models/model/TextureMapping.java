/*     */ package net.minecraft.data.models.model;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ public class TextureMapping
/*     */ {
/*  16 */   private final Map<TextureSlot, ResourceLocation> slots = Maps.newHashMap();
/*  17 */   private final Set<TextureSlot> forcedSlots = Sets.newHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureMapping put(TextureSlot $$0, ResourceLocation $$1) {
/*  23 */     this.slots.put($$0, $$1);
/*  24 */     return this;
/*     */   }
/*     */   
/*     */   public TextureMapping putForced(TextureSlot $$0, ResourceLocation $$1) {
/*  28 */     this.slots.put($$0, $$1);
/*  29 */     this.forcedSlots.add($$0);
/*  30 */     return this;
/*     */   }
/*     */   
/*     */   public Stream<TextureSlot> getForced() {
/*  34 */     return this.forcedSlots.stream();
/*     */   }
/*     */   
/*     */   public TextureMapping copySlot(TextureSlot $$0, TextureSlot $$1) {
/*  38 */     this.slots.put($$1, this.slots.get($$0));
/*  39 */     return this;
/*     */   }
/*     */   
/*     */   public TextureMapping copyForced(TextureSlot $$0, TextureSlot $$1) {
/*  43 */     this.slots.put($$1, this.slots.get($$0));
/*  44 */     this.forcedSlots.add($$1);
/*  45 */     return this;
/*     */   }
/*     */   
/*     */   public ResourceLocation get(TextureSlot $$0) {
/*  49 */     TextureSlot $$1 = $$0;
/*  50 */     while ($$1 != null) {
/*  51 */       ResourceLocation $$2 = this.slots.get($$1);
/*  52 */       if ($$2 != null) {
/*  53 */         return $$2;
/*     */       }
/*  55 */       $$1 = $$1.getParent();
/*     */     } 
/*  57 */     throw new IllegalStateException("Can't find texture for slot " + $$0);
/*     */   }
/*     */   
/*     */   public TextureMapping copyAndUpdate(TextureSlot $$0, ResourceLocation $$1) {
/*  61 */     TextureMapping $$2 = new TextureMapping();
/*  62 */     $$2.slots.putAll(this.slots);
/*  63 */     $$2.forcedSlots.addAll(this.forcedSlots);
/*  64 */     $$2.put($$0, $$1);
/*  65 */     return $$2;
/*     */   }
/*     */   
/*     */   public static TextureMapping cube(Block $$0) {
/*  69 */     ResourceLocation $$1 = getBlockTexture($$0);
/*  70 */     return cube($$1);
/*     */   }
/*     */   
/*     */   public static TextureMapping defaultTexture(Block $$0) {
/*  74 */     ResourceLocation $$1 = getBlockTexture($$0);
/*  75 */     return defaultTexture($$1);
/*     */   }
/*     */   
/*     */   public static TextureMapping defaultTexture(ResourceLocation $$0) {
/*  79 */     return (new TextureMapping()).put(TextureSlot.TEXTURE, $$0);
/*     */   }
/*     */   
/*     */   public static TextureMapping cube(ResourceLocation $$0) {
/*  83 */     return (new TextureMapping()).put(TextureSlot.ALL, $$0);
/*     */   }
/*     */   
/*     */   public static TextureMapping cross(Block $$0) {
/*  87 */     return singleSlot(TextureSlot.CROSS, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping cross(ResourceLocation $$0) {
/*  91 */     return singleSlot(TextureSlot.CROSS, $$0);
/*     */   }
/*     */   
/*     */   public static TextureMapping plant(Block $$0) {
/*  95 */     return singleSlot(TextureSlot.PLANT, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping plant(ResourceLocation $$0) {
/*  99 */     return singleSlot(TextureSlot.PLANT, $$0);
/*     */   }
/*     */   
/*     */   public static TextureMapping rail(Block $$0) {
/* 103 */     return singleSlot(TextureSlot.RAIL, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping rail(ResourceLocation $$0) {
/* 107 */     return singleSlot(TextureSlot.RAIL, $$0);
/*     */   }
/*     */   
/*     */   public static TextureMapping wool(Block $$0) {
/* 111 */     return singleSlot(TextureSlot.WOOL, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping flowerbed(Block $$0) {
/* 115 */     return (new TextureMapping())
/* 116 */       .put(TextureSlot.FLOWERBED, getBlockTexture($$0))
/* 117 */       .put(TextureSlot.STEM, getBlockTexture($$0, "_stem"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMapping wool(ResourceLocation $$0) {
/* 122 */     return singleSlot(TextureSlot.WOOL, $$0);
/*     */   }
/*     */   
/*     */   public static TextureMapping stem(Block $$0) {
/* 126 */     return singleSlot(TextureSlot.STEM, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping attachedStem(Block $$0, Block $$1) {
/* 130 */     return (new TextureMapping())
/* 131 */       .put(TextureSlot.STEM, getBlockTexture($$0))
/* 132 */       .put(TextureSlot.UPPER_STEM, getBlockTexture($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMapping pattern(Block $$0) {
/* 137 */     return singleSlot(TextureSlot.PATTERN, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping fan(Block $$0) {
/* 141 */     return singleSlot(TextureSlot.FAN, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping crop(ResourceLocation $$0) {
/* 145 */     return singleSlot(TextureSlot.CROP, $$0);
/*     */   }
/*     */   
/*     */   public static TextureMapping pane(Block $$0, Block $$1) {
/* 149 */     return (new TextureMapping()).put(TextureSlot.PANE, getBlockTexture($$0)).put(TextureSlot.EDGE, getBlockTexture($$1, "_top"));
/*     */   }
/*     */   
/*     */   public static TextureMapping singleSlot(TextureSlot $$0, ResourceLocation $$1) {
/* 153 */     return (new TextureMapping()).put($$0, $$1);
/*     */   }
/*     */   
/*     */   public static TextureMapping column(Block $$0) {
/* 157 */     return (new TextureMapping())
/* 158 */       .put(TextureSlot.SIDE, getBlockTexture($$0, "_side"))
/* 159 */       .put(TextureSlot.END, getBlockTexture($$0, "_top"));
/*     */   }
/*     */   
/*     */   public static TextureMapping cubeTop(Block $$0) {
/* 163 */     return (new TextureMapping())
/* 164 */       .put(TextureSlot.SIDE, getBlockTexture($$0, "_side"))
/* 165 */       .put(TextureSlot.TOP, getBlockTexture($$0, "_top"));
/*     */   }
/*     */   
/*     */   public static TextureMapping pottedAzalea(Block $$0) {
/* 169 */     return (new TextureMapping())
/* 170 */       .put(TextureSlot.PLANT, getBlockTexture($$0, "_plant"))
/* 171 */       .put(TextureSlot.SIDE, getBlockTexture($$0, "_side"))
/* 172 */       .put(TextureSlot.TOP, getBlockTexture($$0, "_top"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMapping logColumn(Block $$0) {
/* 177 */     return (new TextureMapping()).put(TextureSlot.SIDE, getBlockTexture($$0)).put(TextureSlot.END, getBlockTexture($$0, "_top")).put(TextureSlot.PARTICLE, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping column(ResourceLocation $$0, ResourceLocation $$1) {
/* 181 */     return (new TextureMapping()).put(TextureSlot.SIDE, $$0).put(TextureSlot.END, $$1);
/*     */   }
/*     */   
/*     */   public static TextureMapping fence(Block $$0) {
/* 185 */     return (new TextureMapping()).put(TextureSlot.TEXTURE, getBlockTexture($$0)).put(TextureSlot.SIDE, getBlockTexture($$0, "_side")).put(TextureSlot.TOP, getBlockTexture($$0, "_top"));
/*     */   }
/*     */   
/*     */   public static TextureMapping customParticle(Block $$0) {
/* 189 */     return (new TextureMapping()).put(TextureSlot.TEXTURE, getBlockTexture($$0)).put(TextureSlot.PARTICLE, getBlockTexture($$0, "_particle"));
/*     */   }
/*     */   
/*     */   public static TextureMapping cubeBottomTop(Block $$0) {
/* 193 */     return (new TextureMapping())
/* 194 */       .put(TextureSlot.SIDE, getBlockTexture($$0, "_side"))
/* 195 */       .put(TextureSlot.TOP, getBlockTexture($$0, "_top"))
/* 196 */       .put(TextureSlot.BOTTOM, getBlockTexture($$0, "_bottom"));
/*     */   }
/*     */   
/*     */   public static TextureMapping cubeBottomTopWithWall(Block $$0) {
/* 200 */     ResourceLocation $$1 = getBlockTexture($$0);
/* 201 */     return (new TextureMapping())
/* 202 */       .put(TextureSlot.WALL, $$1)
/* 203 */       .put(TextureSlot.SIDE, $$1)
/* 204 */       .put(TextureSlot.TOP, getBlockTexture($$0, "_top"))
/* 205 */       .put(TextureSlot.BOTTOM, getBlockTexture($$0, "_bottom"));
/*     */   }
/*     */   
/*     */   public static TextureMapping columnWithWall(Block $$0) {
/* 209 */     ResourceLocation $$1 = getBlockTexture($$0);
/* 210 */     return (new TextureMapping())
/* 211 */       .put(TextureSlot.TEXTURE, $$1)
/* 212 */       .put(TextureSlot.WALL, $$1)
/* 213 */       .put(TextureSlot.SIDE, $$1)
/* 214 */       .put(TextureSlot.END, getBlockTexture($$0, "_top"));
/*     */   }
/*     */   
/*     */   public static TextureMapping door(ResourceLocation $$0, ResourceLocation $$1) {
/* 218 */     return (new TextureMapping()).put(TextureSlot.TOP, $$0).put(TextureSlot.BOTTOM, $$1);
/*     */   }
/*     */   
/*     */   public static TextureMapping door(Block $$0) {
/* 222 */     return (new TextureMapping()).put(TextureSlot.TOP, getBlockTexture($$0, "_top")).put(TextureSlot.BOTTOM, getBlockTexture($$0, "_bottom"));
/*     */   }
/*     */   
/*     */   public static TextureMapping particle(Block $$0) {
/* 226 */     return (new TextureMapping()).put(TextureSlot.PARTICLE, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping particle(ResourceLocation $$0) {
/* 230 */     return (new TextureMapping()).put(TextureSlot.PARTICLE, $$0);
/*     */   }
/*     */   
/*     */   public static TextureMapping fire0(Block $$0) {
/* 234 */     return (new TextureMapping()).put(TextureSlot.FIRE, getBlockTexture($$0, "_0"));
/*     */   }
/*     */   
/*     */   public static TextureMapping fire1(Block $$0) {
/* 238 */     return (new TextureMapping()).put(TextureSlot.FIRE, getBlockTexture($$0, "_1"));
/*     */   }
/*     */   
/*     */   public static TextureMapping lantern(Block $$0) {
/* 242 */     return (new TextureMapping()).put(TextureSlot.LANTERN, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping torch(Block $$0) {
/* 246 */     return (new TextureMapping()).put(TextureSlot.TORCH, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping torch(ResourceLocation $$0) {
/* 250 */     return (new TextureMapping()).put(TextureSlot.TORCH, $$0);
/*     */   }
/*     */   
/*     */   public static TextureMapping trialSpawner(Block $$0, String $$1, String $$2) {
/* 254 */     return (new TextureMapping())
/* 255 */       .put(TextureSlot.SIDE, getBlockTexture($$0, $$1))
/* 256 */       .put(TextureSlot.TOP, getBlockTexture($$0, $$2))
/* 257 */       .put(TextureSlot.BOTTOM, getBlockTexture($$0, "_bottom"));
/*     */   }
/*     */   
/*     */   public static TextureMapping particleFromItem(Item $$0) {
/* 261 */     return (new TextureMapping()).put(TextureSlot.PARTICLE, getItemTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping commandBlock(Block $$0) {
/* 265 */     return (new TextureMapping())
/* 266 */       .put(TextureSlot.SIDE, getBlockTexture($$0, "_side"))
/* 267 */       .put(TextureSlot.FRONT, getBlockTexture($$0, "_front"))
/* 268 */       .put(TextureSlot.BACK, getBlockTexture($$0, "_back"));
/*     */   }
/*     */   
/*     */   public static TextureMapping orientableCube(Block $$0) {
/* 272 */     return (new TextureMapping())
/* 273 */       .put(TextureSlot.SIDE, getBlockTexture($$0, "_side"))
/* 274 */       .put(TextureSlot.FRONT, getBlockTexture($$0, "_front"))
/* 275 */       .put(TextureSlot.TOP, getBlockTexture($$0, "_top"))
/* 276 */       .put(TextureSlot.BOTTOM, getBlockTexture($$0, "_bottom"));
/*     */   }
/*     */   
/*     */   public static TextureMapping orientableCubeOnlyTop(Block $$0) {
/* 280 */     return (new TextureMapping())
/* 281 */       .put(TextureSlot.SIDE, getBlockTexture($$0, "_side"))
/* 282 */       .put(TextureSlot.FRONT, getBlockTexture($$0, "_front"))
/* 283 */       .put(TextureSlot.TOP, getBlockTexture($$0, "_top"));
/*     */   }
/*     */   
/*     */   public static TextureMapping orientableCubeSameEnds(Block $$0) {
/* 287 */     return (new TextureMapping())
/* 288 */       .put(TextureSlot.SIDE, getBlockTexture($$0, "_side"))
/* 289 */       .put(TextureSlot.FRONT, getBlockTexture($$0, "_front"))
/* 290 */       .put(TextureSlot.END, getBlockTexture($$0, "_end"));
/*     */   }
/*     */   
/*     */   public static TextureMapping top(Block $$0) {
/* 294 */     return (new TextureMapping()).put(TextureSlot.TOP, getBlockTexture($$0, "_top"));
/*     */   }
/*     */   
/*     */   public static TextureMapping craftingTable(Block $$0, Block $$1) {
/* 298 */     return (new TextureMapping())
/* 299 */       .put(TextureSlot.PARTICLE, getBlockTexture($$0, "_front"))
/* 300 */       .put(TextureSlot.DOWN, getBlockTexture($$1))
/* 301 */       .put(TextureSlot.UP, getBlockTexture($$0, "_top"))
/* 302 */       .put(TextureSlot.NORTH, getBlockTexture($$0, "_front"))
/* 303 */       .put(TextureSlot.EAST, getBlockTexture($$0, "_side"))
/* 304 */       .put(TextureSlot.SOUTH, getBlockTexture($$0, "_side"))
/* 305 */       .put(TextureSlot.WEST, getBlockTexture($$0, "_front"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMapping fletchingTable(Block $$0, Block $$1) {
/* 310 */     return (new TextureMapping())
/* 311 */       .put(TextureSlot.PARTICLE, getBlockTexture($$0, "_front"))
/* 312 */       .put(TextureSlot.DOWN, getBlockTexture($$1))
/* 313 */       .put(TextureSlot.UP, getBlockTexture($$0, "_top"))
/* 314 */       .put(TextureSlot.NORTH, getBlockTexture($$0, "_front"))
/* 315 */       .put(TextureSlot.SOUTH, getBlockTexture($$0, "_front"))
/* 316 */       .put(TextureSlot.EAST, getBlockTexture($$0, "_side"))
/* 317 */       .put(TextureSlot.WEST, getBlockTexture($$0, "_side"));
/*     */   }
/*     */   
/*     */   public static TextureMapping snifferEgg(String $$0) {
/* 321 */     return (new TextureMapping())
/* 322 */       .put(TextureSlot.PARTICLE, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_north"))
/* 323 */       .put(TextureSlot.BOTTOM, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_bottom"))
/* 324 */       .put(TextureSlot.TOP, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_top"))
/* 325 */       .put(TextureSlot.NORTH, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_north"))
/* 326 */       .put(TextureSlot.SOUTH, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_south"))
/* 327 */       .put(TextureSlot.EAST, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_east"))
/* 328 */       .put(TextureSlot.WEST, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_west"));
/*     */   }
/*     */   
/*     */   public static TextureMapping campfire(Block $$0) {
/* 332 */     return (new TextureMapping())
/* 333 */       .put(TextureSlot.LIT_LOG, getBlockTexture($$0, "_log_lit"))
/* 334 */       .put(TextureSlot.FIRE, getBlockTexture($$0, "_fire"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMapping candleCake(Block $$0, boolean $$1) {
/* 339 */     return (new TextureMapping())
/* 340 */       .put(TextureSlot.PARTICLE, getBlockTexture(Blocks.CAKE, "_side"))
/* 341 */       .put(TextureSlot.BOTTOM, getBlockTexture(Blocks.CAKE, "_bottom"))
/* 342 */       .put(TextureSlot.TOP, getBlockTexture(Blocks.CAKE, "_top"))
/* 343 */       .put(TextureSlot.SIDE, getBlockTexture(Blocks.CAKE, "_side"))
/* 344 */       .put(TextureSlot.CANDLE, getBlockTexture($$0, $$1 ? "_lit" : ""));
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMapping cauldron(ResourceLocation $$0) {
/* 349 */     return (new TextureMapping())
/* 350 */       .put(TextureSlot.PARTICLE, getBlockTexture(Blocks.CAULDRON, "_side"))
/* 351 */       .put(TextureSlot.SIDE, getBlockTexture(Blocks.CAULDRON, "_side"))
/* 352 */       .put(TextureSlot.TOP, getBlockTexture(Blocks.CAULDRON, "_top"))
/* 353 */       .put(TextureSlot.BOTTOM, getBlockTexture(Blocks.CAULDRON, "_bottom"))
/* 354 */       .put(TextureSlot.INSIDE, getBlockTexture(Blocks.CAULDRON, "_inner"))
/* 355 */       .put(TextureSlot.CONTENT, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMapping sculkShrieker(boolean $$0) {
/* 360 */     String $$1 = $$0 ? "_can_summon" : "";
/* 361 */     return (new TextureMapping())
/* 362 */       .put(TextureSlot.PARTICLE, getBlockTexture(Blocks.SCULK_SHRIEKER, "_bottom"))
/* 363 */       .put(TextureSlot.SIDE, getBlockTexture(Blocks.SCULK_SHRIEKER, "_side"))
/* 364 */       .put(TextureSlot.TOP, getBlockTexture(Blocks.SCULK_SHRIEKER, "_top"))
/* 365 */       .put(TextureSlot.INNER_TOP, getBlockTexture(Blocks.SCULK_SHRIEKER, $$1 + "_inner_top"))
/* 366 */       .put(TextureSlot.BOTTOM, getBlockTexture(Blocks.SCULK_SHRIEKER, "_bottom"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMapping layer0(Item $$0) {
/* 371 */     return (new TextureMapping()).put(TextureSlot.LAYER0, getItemTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping layer0(Block $$0) {
/* 375 */     return (new TextureMapping()).put(TextureSlot.LAYER0, getBlockTexture($$0));
/*     */   }
/*     */   
/*     */   public static TextureMapping layer0(ResourceLocation $$0) {
/* 379 */     return (new TextureMapping()).put(TextureSlot.LAYER0, $$0);
/*     */   }
/*     */   
/*     */   public static TextureMapping layered(ResourceLocation $$0, ResourceLocation $$1) {
/* 383 */     return (new TextureMapping()).put(TextureSlot.LAYER0, $$0).put(TextureSlot.LAYER1, $$1);
/*     */   }
/*     */   
/*     */   public static TextureMapping layered(ResourceLocation $$0, ResourceLocation $$1, ResourceLocation $$2) {
/* 387 */     return (new TextureMapping()).put(TextureSlot.LAYER0, $$0).put(TextureSlot.LAYER1, $$1).put(TextureSlot.LAYER2, $$2);
/*     */   }
/*     */   
/*     */   public static ResourceLocation getBlockTexture(Block $$0) {
/* 391 */     ResourceLocation $$1 = BuiltInRegistries.BLOCK.getKey($$0);
/* 392 */     return $$1.withPrefix("block/");
/*     */   }
/*     */   
/*     */   public static ResourceLocation getBlockTexture(Block $$0, String $$1) {
/* 396 */     ResourceLocation $$2 = BuiltInRegistries.BLOCK.getKey($$0);
/* 397 */     return $$2.withPath($$1 -> "block/" + $$1 + $$0);
/*     */   }
/*     */   
/*     */   public static ResourceLocation getItemTexture(Item $$0) {
/* 401 */     ResourceLocation $$1 = BuiltInRegistries.ITEM.getKey($$0);
/* 402 */     return $$1.withPrefix("item/");
/*     */   }
/*     */   
/*     */   public static ResourceLocation getItemTexture(Item $$0, String $$1) {
/* 406 */     ResourceLocation $$2 = BuiltInRegistries.ITEM.getKey($$0);
/* 407 */     return $$2.withPath($$1 -> "item/" + $$1 + $$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\model\TextureMapping.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */