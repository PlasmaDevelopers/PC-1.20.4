/*     */ package net.minecraft.data.models.model;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ public class TexturedModel
/*     */ {
/*  13 */   public static final Provider CUBE = createDefault(TextureMapping::cube, ModelTemplates.CUBE_ALL);
/*  14 */   public static final Provider CUBE_INNER_FACES = createDefault(TextureMapping::cube, ModelTemplates.CUBE_ALL_INNER_FACES);
/*  15 */   public static final Provider CUBE_MIRRORED = createDefault(TextureMapping::cube, ModelTemplates.CUBE_MIRRORED_ALL);
/*  16 */   public static final Provider COLUMN = createDefault(TextureMapping::column, ModelTemplates.CUBE_COLUMN);
/*  17 */   public static final Provider COLUMN_HORIZONTAL = createDefault(TextureMapping::column, ModelTemplates.CUBE_COLUMN_HORIZONTAL);
/*  18 */   public static final Provider CUBE_TOP_BOTTOM = createDefault(TextureMapping::cubeBottomTop, ModelTemplates.CUBE_BOTTOM_TOP);
/*  19 */   public static final Provider CUBE_TOP = createDefault(TextureMapping::cubeTop, ModelTemplates.CUBE_TOP);
/*     */   
/*  21 */   public static final Provider ORIENTABLE_ONLY_TOP = createDefault(TextureMapping::orientableCubeOnlyTop, ModelTemplates.CUBE_ORIENTABLE);
/*  22 */   public static final Provider ORIENTABLE = createDefault(TextureMapping::orientableCube, ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM);
/*     */   
/*  24 */   public static final Provider CARPET = createDefault(TextureMapping::wool, ModelTemplates.CARPET);
/*     */   
/*  26 */   public static final Provider FLOWERBED_1 = createDefault(TextureMapping::flowerbed, ModelTemplates.FLOWERBED_1);
/*  27 */   public static final Provider FLOWERBED_2 = createDefault(TextureMapping::flowerbed, ModelTemplates.FLOWERBED_2);
/*  28 */   public static final Provider FLOWERBED_3 = createDefault(TextureMapping::flowerbed, ModelTemplates.FLOWERBED_3);
/*  29 */   public static final Provider FLOWERBED_4 = createDefault(TextureMapping::flowerbed, ModelTemplates.FLOWERBED_4);
/*     */   
/*  31 */   public static final Provider GLAZED_TERRACOTTA = createDefault(TextureMapping::pattern, ModelTemplates.GLAZED_TERRACOTTA);
/*  32 */   public static final Provider CORAL_FAN = createDefault(TextureMapping::fan, ModelTemplates.CORAL_FAN);
/*  33 */   public static final Provider PARTICLE_ONLY = createDefault(TextureMapping::particle, ModelTemplates.PARTICLE_ONLY);
/*  34 */   public static final Provider ANVIL = createDefault(TextureMapping::top, ModelTemplates.ANVIL);
/*  35 */   public static final Provider LEAVES = createDefault(TextureMapping::cube, ModelTemplates.LEAVES);
/*  36 */   public static final Provider LANTERN = createDefault(TextureMapping::lantern, ModelTemplates.LANTERN);
/*  37 */   public static final Provider HANGING_LANTERN = createDefault(TextureMapping::lantern, ModelTemplates.HANGING_LANTERN);
/*  38 */   public static final Provider SEAGRASS = createDefault(TextureMapping::defaultTexture, ModelTemplates.SEAGRASS);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public static final Provider COLUMN_ALT = createDefault(TextureMapping::logColumn, ModelTemplates.CUBE_COLUMN);
/*  44 */   public static final Provider COLUMN_HORIZONTAL_ALT = createDefault(TextureMapping::logColumn, ModelTemplates.CUBE_COLUMN_HORIZONTAL);
/*     */ 
/*     */   
/*  47 */   public static final Provider TOP_BOTTOM_WITH_WALL = createDefault(TextureMapping::cubeBottomTopWithWall, ModelTemplates.CUBE_BOTTOM_TOP);
/*     */ 
/*     */   
/*  50 */   public static final Provider COLUMN_WITH_WALL = createDefault(TextureMapping::columnWithWall, ModelTemplates.CUBE_COLUMN);
/*     */   
/*     */   private final TextureMapping mapping;
/*     */   private final ModelTemplate template;
/*     */   
/*     */   private TexturedModel(TextureMapping $$0, ModelTemplate $$1) {
/*  56 */     this.mapping = $$0;
/*  57 */     this.template = $$1;
/*     */   }
/*     */   
/*     */   public ModelTemplate getTemplate() {
/*  61 */     return this.template;
/*     */   }
/*     */   
/*     */   public TextureMapping getMapping() {
/*  65 */     return this.mapping;
/*     */   }
/*     */   
/*     */   public TexturedModel updateTextures(Consumer<TextureMapping> $$0) {
/*  69 */     $$0.accept(this.mapping);
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public ResourceLocation create(Block $$0, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$1) {
/*  74 */     return this.template.create($$0, this.mapping, $$1);
/*     */   }
/*     */   
/*     */   public ResourceLocation createWithSuffix(Block $$0, String $$1, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$2) {
/*  78 */     return this.template.createWithSuffix($$0, $$1, this.mapping, $$2);
/*     */   }
/*     */   
/*     */   private static Provider createDefault(Function<Block, TextureMapping> $$0, ModelTemplate $$1) {
/*  82 */     return $$2 -> new TexturedModel($$0.apply($$2), $$1);
/*     */   }
/*     */   
/*     */   public static TexturedModel createAllSame(ResourceLocation $$0) {
/*  86 */     return new TexturedModel(TextureMapping.cube($$0), ModelTemplates.CUBE_ALL);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Provider {
/*     */     TexturedModel get(Block param1Block);
/*     */     
/*     */     default ResourceLocation create(Block $$0, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$1) {
/*  94 */       return get($$0).create($$0, $$1);
/*     */     }
/*     */     
/*     */     default ResourceLocation createWithSuffix(Block $$0, String $$1, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$2) {
/*  98 */       return get($$0).createWithSuffix($$0, $$1, $$2);
/*     */     }
/*     */     
/*     */     default Provider updateTexture(Consumer<TextureMapping> $$0) {
/* 102 */       return $$1 -> get($$1).updateTextures($$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\model\TexturedModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */