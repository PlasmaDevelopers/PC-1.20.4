/*     */ package net.minecraft.data.models.model;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.block.Block;
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
/*     */ @FunctionalInterface
/*     */ public interface Provider
/*     */ {
/*     */   TexturedModel get(Block paramBlock);
/*     */   
/*     */   default ResourceLocation create(Block $$0, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$1) {
/*  94 */     return get($$0).create($$0, $$1);
/*     */   }
/*     */   
/*     */   default ResourceLocation createWithSuffix(Block $$0, String $$1, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$2) {
/*  98 */     return get($$0).createWithSuffix($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   default Provider updateTexture(Consumer<TextureMapping> $$0) {
/* 102 */     return $$1 -> get($$1).updateTextures($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\model\TexturedModel$Provider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */