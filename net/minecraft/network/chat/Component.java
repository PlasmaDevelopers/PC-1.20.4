/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.io.StringReader;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.network.chat.contents.DataSource;
/*     */ import net.minecraft.network.chat.contents.KeybindContents;
/*     */ import net.minecraft.network.chat.contents.NbtContents;
/*     */ import net.minecraft.network.chat.contents.PlainTextContents;
/*     */ import net.minecraft.network.chat.contents.ScoreContents;
/*     */ import net.minecraft.network.chat.contents.SelectorContents;
/*     */ import net.minecraft.network.chat.contents.TranslatableContents;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ 
/*     */ 
/*     */ public interface Component
/*     */   extends Message, FormattedText
/*     */ {
/*     */   default String getString() {
/*  44 */     return super.getString();
/*     */   }
/*     */   
/*     */   default String getString(int $$0) {
/*  48 */     StringBuilder $$1 = new StringBuilder();
/*  49 */     visit($$2 -> {
/*     */           int $$3 = $$0 - $$1.length();
/*     */           if ($$3 <= 0) {
/*     */             return STOP_ITERATION;
/*     */           }
/*     */           $$1.append(($$2.length() <= $$3) ? $$2 : $$2.substring(0, $$3));
/*     */           return Optional.empty();
/*     */         });
/*  57 */     return $$1.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default String tryCollapseToString() {
/*  64 */     ComponentContents componentContents = getContents(); if (componentContents instanceof PlainTextContents) { PlainTextContents $$0 = (PlainTextContents)componentContents; if (getSiblings().isEmpty() && getStyle().isEmpty())
/*  65 */         return $$0.text();  }
/*     */     
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default MutableComponent plainCopy() {
/*  77 */     return MutableComponent.create(getContents());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default MutableComponent copy() {
/*  87 */     return new MutableComponent(getContents(), new ArrayList<>(getSiblings()), getStyle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/*  94 */     Style $$2 = getStyle().applyTo($$1);
/*     */     
/*  96 */     Optional<T> $$3 = getContents().visit($$0, $$2);
/*  97 */     if ($$3.isPresent()) {
/*  98 */       return $$3;
/*     */     }
/*     */     
/* 101 */     for (Component $$4 : getSiblings()) {
/* 102 */       Optional<T> $$5 = $$4.visit($$0, $$2);
/* 103 */       if ($$5.isPresent()) {
/* 104 */         return $$5;
/*     */       }
/*     */     } 
/*     */     
/* 108 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   default <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 113 */     Optional<T> $$1 = getContents().visit($$0);
/* 114 */     if ($$1.isPresent()) {
/* 115 */       return $$1;
/*     */     }
/*     */     
/* 118 */     for (Component $$2 : getSiblings()) {
/* 119 */       Optional<T> $$3 = $$2.visit($$0);
/* 120 */       if ($$3.isPresent()) {
/* 121 */         return $$3;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   default List<Component> toFlatList() {
/* 129 */     return toFlatList(Style.EMPTY);
/*     */   }
/*     */   
/*     */   default List<Component> toFlatList(Style $$0) {
/* 133 */     List<Component> $$1 = Lists.newArrayList();
/* 134 */     visit(($$1, $$2) -> { if (!$$2.isEmpty()) $$0.add(literal($$2).withStyle($$1));  return Optional.empty(); }$$0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     return $$1;
/*     */   }
/*     */   
/*     */   default boolean contains(Component $$0) {
/* 144 */     if (equals($$0)) {
/* 145 */       return true;
/*     */     }
/*     */     
/* 148 */     List<Component> $$1 = toFlatList();
/* 149 */     List<Component> $$2 = $$0.toFlatList(getStyle());
/* 150 */     return (Collections.indexOfSubList($$1, $$2) != -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Serializer
/*     */   {
/*     */     static MutableComponent deserialize(JsonElement $$0) {
/* 159 */       return (MutableComponent)Util.getOrThrow(ComponentSerialization.CODEC.parse((DynamicOps)JsonOps.INSTANCE, $$0), JsonParseException::new);
/*     */     }
/*     */     
/*     */     static JsonElement serialize(Component $$0) {
/* 163 */       return (JsonElement)Util.getOrThrow(ComponentSerialization.CODEC.encodeStart((DynamicOps)JsonOps.INSTANCE, $$0), JsonParseException::new);
/*     */     }
/*     */     
/* 166 */     private static final Gson GSON = (new GsonBuilder()).disableHtmlEscaping().create();
/*     */     
/*     */     public static String toJson(Component $$0) {
/* 169 */       return GSON.toJson(serialize($$0));
/*     */     }
/*     */     
/*     */     public static JsonElement toJsonTree(Component $$0) {
/* 173 */       return serialize($$0);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public static MutableComponent fromJson(String $$0) {
/* 178 */       JsonElement $$1 = JsonParser.parseString($$0);
/* 179 */       if ($$1 == null) {
/* 180 */         return null;
/*     */       }
/* 182 */       return deserialize($$1);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public static MutableComponent fromJson(@Nullable JsonElement $$0) {
/* 187 */       if ($$0 == null) {
/* 188 */         return null;
/*     */       }
/* 190 */       return deserialize($$0);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public static MutableComponent fromJsonLenient(String $$0) {
/* 195 */       JsonReader $$1 = new JsonReader(new StringReader($$0));
/* 196 */       $$1.setLenient(true);
/* 197 */       JsonElement $$2 = JsonParser.parseReader($$1);
/* 198 */       if ($$2 == null) {
/* 199 */         return null;
/*     */       }
/* 201 */       return deserialize($$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SerializerAdapter
/*     */     implements JsonDeserializer<MutableComponent>, JsonSerializer<Component> {
/*     */     public MutableComponent deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 208 */       return Component.Serializer.deserialize($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(Component $$0, Type $$1, JsonSerializationContext $$2) {
/* 213 */       return Component.Serializer.serialize($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   static Component nullToEmpty(@Nullable String $$0) {
/* 218 */     return ($$0 != null) ? literal($$0) : CommonComponents.EMPTY;
/*     */   }
/*     */   
/*     */   static MutableComponent literal(String $$0) {
/* 222 */     return MutableComponent.create((ComponentContents)PlainTextContents.create($$0));
/*     */   }
/*     */   
/*     */   static MutableComponent translatable(String $$0) {
/* 226 */     return MutableComponent.create((ComponentContents)new TranslatableContents($$0, null, TranslatableContents.NO_ARGS));
/*     */   }
/*     */   
/*     */   static MutableComponent translatable(String $$0, Object... $$1) {
/* 230 */     return MutableComponent.create((ComponentContents)new TranslatableContents($$0, null, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MutableComponent translatableEscape(String $$0, Object... $$1) {
/* 237 */     for (int $$2 = 0; $$2 < $$1.length; $$2++) {
/* 238 */       Object $$3 = $$1[$$2];
/* 239 */       if (!TranslatableContents.isAllowedPrimitiveArgument($$3) && !($$3 instanceof Component)) {
/* 240 */         $$1[$$2] = String.valueOf($$3);
/*     */       }
/*     */     } 
/* 243 */     return translatable($$0, $$1);
/*     */   }
/*     */   
/*     */   static MutableComponent translatableWithFallback(String $$0, @Nullable String $$1) {
/* 247 */     return MutableComponent.create((ComponentContents)new TranslatableContents($$0, $$1, TranslatableContents.NO_ARGS));
/*     */   }
/*     */   
/*     */   static MutableComponent translatableWithFallback(String $$0, @Nullable String $$1, Object... $$2) {
/* 251 */     return MutableComponent.create((ComponentContents)new TranslatableContents($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   static MutableComponent empty() {
/* 255 */     return MutableComponent.create((ComponentContents)PlainTextContents.EMPTY);
/*     */   }
/*     */   
/*     */   static MutableComponent keybind(String $$0) {
/* 259 */     return MutableComponent.create((ComponentContents)new KeybindContents($$0));
/*     */   }
/*     */   
/*     */   static MutableComponent nbt(String $$0, boolean $$1, Optional<Component> $$2, DataSource $$3) {
/* 263 */     return MutableComponent.create((ComponentContents)new NbtContents($$0, $$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   static MutableComponent score(String $$0, String $$1) {
/* 267 */     return MutableComponent.create((ComponentContents)new ScoreContents($$0, $$1));
/*     */   }
/*     */   
/*     */   static MutableComponent selector(String $$0, Optional<Component> $$1) {
/* 271 */     return MutableComponent.create((ComponentContents)new SelectorContents($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   static Component translationArg(Date $$0) {
/* 276 */     return literal($$0.toString());
/*     */   }
/*     */   
/*     */   static Component translationArg(Message $$0) {
/* 280 */     Component $$1 = (Component)$$0; return ($$0 instanceof Component) ? $$1 : literal($$0.getString());
/*     */   }
/*     */   
/*     */   static Component translationArg(UUID $$0) {
/* 284 */     return literal($$0.toString());
/*     */   }
/*     */   
/*     */   static Component translationArg(ResourceLocation $$0) {
/* 288 */     return literal($$0.toString());
/*     */   }
/*     */   
/*     */   static Component translationArg(ChunkPos $$0) {
/* 292 */     return literal($$0.toString());
/*     */   }
/*     */   
/*     */   Style getStyle();
/*     */   
/*     */   ComponentContents getContents();
/*     */   
/*     */   List<Component> getSiblings();
/*     */   
/*     */   FormattedCharSequence getVisualOrderText();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\Component.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */