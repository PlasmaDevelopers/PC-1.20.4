/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.ResourceLocationException;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundRecipePacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.crafting.RecipeManager;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerRecipeBook extends RecipeBook {
/*     */   public static final String RECIPE_BOOK_TAG = "recipeBook";
/*  26 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addRecipes(Collection<RecipeHolder<?>> $$0, ServerPlayer $$1) {
/*  32 */     List<ResourceLocation> $$2 = Lists.newArrayList();
/*  33 */     int $$3 = 0;
/*     */     
/*  35 */     for (RecipeHolder<?> $$4 : $$0) {
/*  36 */       ResourceLocation $$5 = $$4.id();
/*  37 */       if (!this.known.contains($$5) && !$$4.value().isSpecial()) {
/*  38 */         add($$5);
/*  39 */         addHighlight($$5);
/*  40 */         $$2.add($$5);
/*  41 */         CriteriaTriggers.RECIPE_UNLOCKED.trigger($$1, $$4);
/*  42 */         $$3++;
/*     */       } 
/*     */     } 
/*     */     
/*  46 */     if ($$2.size() > 0) {
/*  47 */       sendRecipes(ClientboundRecipePacket.State.ADD, $$1, $$2);
/*     */     }
/*  49 */     return $$3;
/*     */   }
/*     */   
/*     */   public int removeRecipes(Collection<RecipeHolder<?>> $$0, ServerPlayer $$1) {
/*  53 */     List<ResourceLocation> $$2 = Lists.newArrayList();
/*  54 */     int $$3 = 0;
/*     */     
/*  56 */     for (RecipeHolder<?> $$4 : $$0) {
/*  57 */       ResourceLocation $$5 = $$4.id();
/*  58 */       if (this.known.contains($$5)) {
/*  59 */         remove($$5);
/*  60 */         $$2.add($$5);
/*  61 */         $$3++;
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     sendRecipes(ClientboundRecipePacket.State.REMOVE, $$1, $$2);
/*  66 */     return $$3;
/*     */   }
/*     */   
/*     */   private void sendRecipes(ClientboundRecipePacket.State $$0, ServerPlayer $$1, List<ResourceLocation> $$2) {
/*  70 */     $$1.connection.send((Packet)new ClientboundRecipePacket($$0, $$2, Collections.emptyList(), getBookSettings()));
/*     */   }
/*     */   
/*     */   public CompoundTag toNbt() {
/*  74 */     CompoundTag $$0 = new CompoundTag();
/*     */     
/*  76 */     getBookSettings().write($$0);
/*     */     
/*  78 */     ListTag $$1 = new ListTag();
/*  79 */     for (ResourceLocation $$2 : this.known) {
/*  80 */       $$1.add(StringTag.valueOf($$2.toString()));
/*     */     }
/*  82 */     $$0.put("recipes", (Tag)$$1);
/*     */     
/*  84 */     ListTag $$3 = new ListTag();
/*  85 */     for (ResourceLocation $$4 : this.highlight) {
/*  86 */       $$3.add(StringTag.valueOf($$4.toString()));
/*     */     }
/*  88 */     $$0.put("toBeDisplayed", (Tag)$$3);
/*     */     
/*  90 */     return $$0;
/*     */   }
/*     */   
/*     */   public void fromNbt(CompoundTag $$0, RecipeManager $$1) {
/*  94 */     setBookSettings(RecipeBookSettings.read($$0));
/*     */     
/*  96 */     ListTag $$2 = $$0.getList("recipes", 8);
/*  97 */     loadRecipes($$2, this::add, $$1);
/*     */     
/*  99 */     ListTag $$3 = $$0.getList("toBeDisplayed", 8);
/* 100 */     loadRecipes($$3, this::addHighlight, $$1);
/*     */   }
/*     */   
/*     */   private void loadRecipes(ListTag $$0, Consumer<RecipeHolder<?>> $$1, RecipeManager $$2) {
/* 104 */     for (int $$3 = 0; $$3 < $$0.size(); $$3++) {
/* 105 */       String $$4 = $$0.getString($$3);
/*     */       try {
/* 107 */         ResourceLocation $$5 = new ResourceLocation($$4);
/* 108 */         Optional<RecipeHolder<?>> $$6 = $$2.byKey($$5);
/* 109 */         if ($$6.isEmpty())
/* 110 */         { LOGGER.error("Tried to load unrecognized recipe: {} removed now.", $$5); }
/*     */         else
/*     */         
/* 113 */         { $$1.accept($$6.get()); } 
/* 114 */       } catch (ResourceLocationException $$7) {
/* 115 */         LOGGER.error("Tried to load improperly formatted recipe: {} removed now.", $$4);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendInitialRecipeBook(ServerPlayer $$0) {
/* 121 */     $$0.connection.send((Packet)new ClientboundRecipePacket(ClientboundRecipePacket.State.INIT, this.known, this.highlight, getBookSettings()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\stats\ServerRecipeBook.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */