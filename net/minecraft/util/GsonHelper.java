/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Item;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ 
/*     */ public class GsonHelper
/*     */ {
/*  38 */   private static final Gson GSON = (new GsonBuilder()).create();
/*     */   
/*     */   public static boolean isStringValue(JsonObject $$0, String $$1) {
/*  41 */     if (!isValidPrimitive($$0, $$1)) {
/*  42 */       return false;
/*     */     }
/*  44 */     return $$0.getAsJsonPrimitive($$1).isString();
/*     */   }
/*     */   
/*     */   public static boolean isStringValue(JsonElement $$0) {
/*  48 */     if (!$$0.isJsonPrimitive()) {
/*  49 */       return false;
/*     */     }
/*  51 */     return $$0.getAsJsonPrimitive().isString();
/*     */   }
/*     */   
/*     */   public static boolean isNumberValue(JsonObject $$0, String $$1) {
/*  55 */     if (!isValidPrimitive($$0, $$1)) {
/*  56 */       return false;
/*     */     }
/*  58 */     return $$0.getAsJsonPrimitive($$1).isNumber();
/*     */   }
/*     */   
/*     */   public static boolean isNumberValue(JsonElement $$0) {
/*  62 */     if (!$$0.isJsonPrimitive()) {
/*  63 */       return false;
/*     */     }
/*  65 */     return $$0.getAsJsonPrimitive().isNumber();
/*     */   }
/*     */   
/*     */   public static boolean isBooleanValue(JsonObject $$0, String $$1) {
/*  69 */     if (!isValidPrimitive($$0, $$1)) {
/*  70 */       return false;
/*     */     }
/*  72 */     return $$0.getAsJsonPrimitive($$1).isBoolean();
/*     */   }
/*     */   
/*     */   public static boolean isBooleanValue(JsonElement $$0) {
/*  76 */     if (!$$0.isJsonPrimitive()) {
/*  77 */       return false;
/*     */     }
/*  79 */     return $$0.getAsJsonPrimitive().isBoolean();
/*     */   }
/*     */   
/*     */   public static boolean isArrayNode(JsonObject $$0, String $$1) {
/*  83 */     if (!isValidNode($$0, $$1)) {
/*  84 */       return false;
/*     */     }
/*  86 */     return $$0.get($$1).isJsonArray();
/*     */   }
/*     */   
/*     */   public static boolean isObjectNode(JsonObject $$0, String $$1) {
/*  90 */     if (!isValidNode($$0, $$1)) {
/*  91 */       return false;
/*     */     }
/*  93 */     return $$0.get($$1).isJsonObject();
/*     */   }
/*     */   
/*     */   public static boolean isValidPrimitive(JsonObject $$0, String $$1) {
/*  97 */     if (!isValidNode($$0, $$1)) {
/*  98 */       return false;
/*     */     }
/* 100 */     return $$0.get($$1).isJsonPrimitive();
/*     */   }
/*     */   
/*     */   public static boolean isValidNode(@Nullable JsonObject $$0, String $$1) {
/* 104 */     if ($$0 == null) {
/* 105 */       return false;
/*     */     }
/* 107 */     return ($$0.get($$1) != null);
/*     */   }
/*     */   
/*     */   public static JsonElement getNonNull(JsonObject $$0, String $$1) {
/* 111 */     JsonElement $$2 = $$0.get($$1);
/* 112 */     if ($$2 == null || $$2.isJsonNull()) {
/* 113 */       throw new JsonSyntaxException("Missing field " + $$1);
/*     */     }
/* 115 */     return $$2;
/*     */   }
/*     */   
/*     */   public static String convertToString(JsonElement $$0, String $$1) {
/* 119 */     if ($$0.isJsonPrimitive()) {
/* 120 */       return $$0.getAsString();
/*     */     }
/* 122 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a string, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getAsString(JsonObject $$0, String $$1) {
/* 127 */     if ($$0.has($$1)) {
/* 128 */       return convertToString($$0.get($$1), $$1);
/*     */     }
/* 130 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a string");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Contract("_,_,!null->!null;_,_,null->_")
/*     */   public static String getAsString(JsonObject $$0, String $$1, @Nullable String $$2) {
/* 137 */     if ($$0.has($$1)) {
/* 138 */       return convertToString($$0.get($$1), $$1);
/*     */     }
/* 140 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Holder<Item> convertToItem(JsonElement $$0, String $$1) {
/* 145 */     if ($$0.isJsonPrimitive()) {
/* 146 */       String $$2 = $$0.getAsString();
/* 147 */       return (Holder<Item>)BuiltInRegistries.ITEM.getHolder(ResourceKey.create(Registries.ITEM, new ResourceLocation($$2)))
/* 148 */         .orElseThrow(() -> new JsonSyntaxException("Expected " + $$0 + " to be an item, was unknown string '" + $$1 + "'"));
/*     */     } 
/* 150 */     throw new JsonSyntaxException("Expected " + $$1 + " to be an item, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Holder<Item> getAsItem(JsonObject $$0, String $$1) {
/* 155 */     if ($$0.has($$1)) {
/* 156 */       return convertToItem($$0.get($$1), $$1);
/*     */     }
/* 158 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find an item");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Contract("_,_,!null->!null;_,_,null->_")
/*     */   public static Holder<Item> getAsItem(JsonObject $$0, String $$1, @Nullable Holder<Item> $$2) {
/* 165 */     if ($$0.has($$1)) {
/* 166 */       return convertToItem($$0.get($$1), $$1);
/*     */     }
/* 168 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean convertToBoolean(JsonElement $$0, String $$1) {
/* 173 */     if ($$0.isJsonPrimitive()) {
/* 174 */       return $$0.getAsBoolean();
/*     */     }
/* 176 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a Boolean, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getAsBoolean(JsonObject $$0, String $$1) {
/* 181 */     if ($$0.has($$1)) {
/* 182 */       return convertToBoolean($$0.get($$1), $$1);
/*     */     }
/* 184 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Boolean");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getAsBoolean(JsonObject $$0, String $$1, boolean $$2) {
/* 189 */     if ($$0.has($$1)) {
/* 190 */       return convertToBoolean($$0.get($$1), $$1);
/*     */     }
/* 192 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double convertToDouble(JsonElement $$0, String $$1) {
/* 197 */     if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
/* 198 */       return $$0.getAsDouble();
/*     */     }
/* 200 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a Double, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getAsDouble(JsonObject $$0, String $$1) {
/* 205 */     if ($$0.has($$1)) {
/* 206 */       return convertToDouble($$0.get($$1), $$1);
/*     */     }
/* 208 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Double");
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getAsDouble(JsonObject $$0, String $$1, double $$2) {
/* 213 */     if ($$0.has($$1)) {
/* 214 */       return convertToDouble($$0.get($$1), $$1);
/*     */     }
/* 216 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float convertToFloat(JsonElement $$0, String $$1) {
/* 221 */     if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
/* 222 */       return $$0.getAsFloat();
/*     */     }
/* 224 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a Float, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getAsFloat(JsonObject $$0, String $$1) {
/* 229 */     if ($$0.has($$1)) {
/* 230 */       return convertToFloat($$0.get($$1), $$1);
/*     */     }
/* 232 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Float");
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getAsFloat(JsonObject $$0, String $$1, float $$2) {
/* 237 */     if ($$0.has($$1)) {
/* 238 */       return convertToFloat($$0.get($$1), $$1);
/*     */     }
/* 240 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static long convertToLong(JsonElement $$0, String $$1) {
/* 245 */     if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
/* 246 */       return $$0.getAsLong();
/*     */     }
/* 248 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a Long, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getAsLong(JsonObject $$0, String $$1) {
/* 253 */     if ($$0.has($$1)) {
/* 254 */       return convertToLong($$0.get($$1), $$1);
/*     */     }
/* 256 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Long");
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getAsLong(JsonObject $$0, String $$1, long $$2) {
/* 261 */     if ($$0.has($$1)) {
/* 262 */       return convertToLong($$0.get($$1), $$1);
/*     */     }
/* 264 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int convertToInt(JsonElement $$0, String $$1) {
/* 269 */     if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
/* 270 */       return $$0.getAsInt();
/*     */     }
/* 272 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a Int, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getAsInt(JsonObject $$0, String $$1) {
/* 277 */     if ($$0.has($$1)) {
/* 278 */       return convertToInt($$0.get($$1), $$1);
/*     */     }
/* 280 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Int");
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getAsInt(JsonObject $$0, String $$1, int $$2) {
/* 285 */     if ($$0.has($$1)) {
/* 286 */       return convertToInt($$0.get($$1), $$1);
/*     */     }
/* 288 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte convertToByte(JsonElement $$0, String $$1) {
/* 293 */     if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
/* 294 */       return $$0.getAsByte();
/*     */     }
/* 296 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a Byte, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte getAsByte(JsonObject $$0, String $$1) {
/* 301 */     if ($$0.has($$1)) {
/* 302 */       return convertToByte($$0.get($$1), $$1);
/*     */     }
/* 304 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Byte");
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte getAsByte(JsonObject $$0, String $$1, byte $$2) {
/* 309 */     if ($$0.has($$1)) {
/* 310 */       return convertToByte($$0.get($$1), $$1);
/*     */     }
/* 312 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static char convertToCharacter(JsonElement $$0, String $$1) {
/* 317 */     if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
/* 318 */       return $$0.getAsCharacter();
/*     */     }
/* 320 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a Character, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static char getAsCharacter(JsonObject $$0, String $$1) {
/* 325 */     if ($$0.has($$1)) {
/* 326 */       return convertToCharacter($$0.get($$1), $$1);
/*     */     }
/* 328 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Character");
/*     */   }
/*     */ 
/*     */   
/*     */   public static char getAsCharacter(JsonObject $$0, String $$1, char $$2) {
/* 333 */     if ($$0.has($$1)) {
/* 334 */       return convertToCharacter($$0.get($$1), $$1);
/*     */     }
/* 336 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BigDecimal convertToBigDecimal(JsonElement $$0, String $$1) {
/* 341 */     if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
/* 342 */       return $$0.getAsBigDecimal();
/*     */     }
/* 344 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a BigDecimal, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static BigDecimal getAsBigDecimal(JsonObject $$0, String $$1) {
/* 349 */     if ($$0.has($$1)) {
/* 350 */       return convertToBigDecimal($$0.get($$1), $$1);
/*     */     }
/* 352 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a BigDecimal");
/*     */   }
/*     */ 
/*     */   
/*     */   public static BigDecimal getAsBigDecimal(JsonObject $$0, String $$1, BigDecimal $$2) {
/* 357 */     if ($$0.has($$1)) {
/* 358 */       return convertToBigDecimal($$0.get($$1), $$1);
/*     */     }
/* 360 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BigInteger convertToBigInteger(JsonElement $$0, String $$1) {
/* 365 */     if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
/* 366 */       return $$0.getAsBigInteger();
/*     */     }
/* 368 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a BigInteger, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static BigInteger getAsBigInteger(JsonObject $$0, String $$1) {
/* 373 */     if ($$0.has($$1)) {
/* 374 */       return convertToBigInteger($$0.get($$1), $$1);
/*     */     }
/* 376 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a BigInteger");
/*     */   }
/*     */ 
/*     */   
/*     */   public static BigInteger getAsBigInteger(JsonObject $$0, String $$1, BigInteger $$2) {
/* 381 */     if ($$0.has($$1)) {
/* 382 */       return convertToBigInteger($$0.get($$1), $$1);
/*     */     }
/* 384 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static short convertToShort(JsonElement $$0, String $$1) {
/* 389 */     if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
/* 390 */       return $$0.getAsShort();
/*     */     }
/* 392 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a Short, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static short getAsShort(JsonObject $$0, String $$1) {
/* 397 */     if ($$0.has($$1)) {
/* 398 */       return convertToShort($$0.get($$1), $$1);
/*     */     }
/* 400 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Short");
/*     */   }
/*     */ 
/*     */   
/*     */   public static short getAsShort(JsonObject $$0, String $$1, short $$2) {
/* 405 */     if ($$0.has($$1)) {
/* 406 */       return convertToShort($$0.get($$1), $$1);
/*     */     }
/* 408 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonObject convertToJsonObject(JsonElement $$0, String $$1) {
/* 413 */     if ($$0.isJsonObject()) {
/* 414 */       return $$0.getAsJsonObject();
/*     */     }
/* 416 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a JsonObject, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonObject getAsJsonObject(JsonObject $$0, String $$1) {
/* 421 */     if ($$0.has($$1)) {
/* 422 */       return convertToJsonObject($$0.get($$1), $$1);
/*     */     }
/* 424 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a JsonObject");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Contract("_,_,!null->!null;_,_,null->_")
/*     */   public static JsonObject getAsJsonObject(JsonObject $$0, String $$1, @Nullable JsonObject $$2) {
/* 431 */     if ($$0.has($$1)) {
/* 432 */       return convertToJsonObject($$0.get($$1), $$1);
/*     */     }
/* 434 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonArray convertToJsonArray(JsonElement $$0, String $$1) {
/* 439 */     if ($$0.isJsonArray()) {
/* 440 */       return $$0.getAsJsonArray();
/*     */     }
/* 442 */     throw new JsonSyntaxException("Expected " + $$1 + " to be a JsonArray, was " + getType($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonArray getAsJsonArray(JsonObject $$0, String $$1) {
/* 447 */     if ($$0.has($$1)) {
/* 448 */       return convertToJsonArray($$0.get($$1), $$1);
/*     */     }
/* 450 */     throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a JsonArray");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Contract("_,_,!null->!null;_,_,null->_")
/*     */   public static JsonArray getAsJsonArray(JsonObject $$0, String $$1, @Nullable JsonArray $$2) {
/* 457 */     if ($$0.has($$1)) {
/* 458 */       return convertToJsonArray($$0.get($$1), $$1);
/*     */     }
/* 460 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> T convertToObject(@Nullable JsonElement $$0, String $$1, JsonDeserializationContext $$2, Class<? extends T> $$3) {
/* 465 */     if ($$0 != null) {
/* 466 */       return (T)$$2.deserialize($$0, $$3);
/*     */     }
/* 468 */     throw new JsonSyntaxException("Missing " + $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> T getAsObject(JsonObject $$0, String $$1, JsonDeserializationContext $$2, Class<? extends T> $$3) {
/* 473 */     if ($$0.has($$1)) {
/* 474 */       return convertToObject($$0.get($$1), $$1, $$2, $$3);
/*     */     }
/* 476 */     throw new JsonSyntaxException("Missing " + $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Contract("_,_,!null,_,_->!null;_,_,null,_,_->_")
/*     */   public static <T> T getAsObject(JsonObject $$0, String $$1, @Nullable T $$2, JsonDeserializationContext $$3, Class<? extends T> $$4) {
/* 483 */     if ($$0.has($$1)) {
/* 484 */       return convertToObject($$0.get($$1), $$1, $$3, $$4);
/*     */     }
/* 486 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getType(@Nullable JsonElement $$0) {
/* 491 */     String $$1 = StringUtils.abbreviateMiddle(String.valueOf($$0), "...", 10);
/* 492 */     if ($$0 == null) {
/* 493 */       return "null (missing)";
/*     */     }
/* 495 */     if ($$0.isJsonNull()) {
/* 496 */       return "null (json)";
/*     */     }
/* 498 */     if ($$0.isJsonArray()) {
/* 499 */       return "an array (" + $$1 + ")";
/*     */     }
/* 501 */     if ($$0.isJsonObject()) {
/* 502 */       return "an object (" + $$1 + ")";
/*     */     }
/* 504 */     if ($$0.isJsonPrimitive()) {
/* 505 */       JsonPrimitive $$2 = $$0.getAsJsonPrimitive();
/* 506 */       if ($$2.isNumber()) {
/* 507 */         return "a number (" + $$1 + ")";
/*     */       }
/* 509 */       if ($$2.isBoolean()) {
/* 510 */         return "a boolean (" + $$1 + ")";
/*     */       }
/*     */     } 
/* 513 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> T fromNullableJson(Gson $$0, Reader $$1, Class<T> $$2, boolean $$3) {
/*     */     try {
/* 525 */       JsonReader $$4 = new JsonReader($$1);
/* 526 */       $$4.setLenient($$3);
/* 527 */       return (T)$$0.getAdapter($$2).read($$4);
/* 528 */     } catch (IOException $$5) {
/* 529 */       throw new JsonParseException($$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static <T> T fromJson(Gson $$0, Reader $$1, Class<T> $$2, boolean $$3) {
/* 534 */     T $$4 = fromNullableJson($$0, $$1, $$2, $$3);
/* 535 */     if ($$4 == null) {
/* 536 */       throw new JsonParseException("JSON data was null or empty");
/*     */     }
/* 538 */     return $$4;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <T> T fromNullableJson(Gson $$0, Reader $$1, TypeToken<T> $$2, boolean $$3) {
/*     */     try {
/* 544 */       JsonReader $$4 = new JsonReader($$1);
/* 545 */       $$4.setLenient($$3);
/* 546 */       return (T)$$0.getAdapter($$2).read($$4);
/* 547 */     } catch (IOException $$5) {
/* 548 */       throw new JsonParseException($$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static <T> T fromJson(Gson $$0, Reader $$1, TypeToken<T> $$2, boolean $$3) {
/* 553 */     T $$4 = fromNullableJson($$0, $$1, $$2, $$3);
/* 554 */     if ($$4 == null) {
/* 555 */       throw new JsonParseException("JSON data was null or empty");
/*     */     }
/* 557 */     return $$4;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <T> T fromNullableJson(Gson $$0, String $$1, TypeToken<T> $$2, boolean $$3) {
/* 562 */     return fromNullableJson($$0, new StringReader($$1), $$2, $$3);
/*     */   }
/*     */   
/*     */   public static <T> T fromJson(Gson $$0, String $$1, Class<T> $$2, boolean $$3) {
/* 566 */     return fromJson($$0, new StringReader($$1), $$2, $$3);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <T> T fromNullableJson(Gson $$0, String $$1, Class<T> $$2, boolean $$3) {
/* 571 */     return fromNullableJson($$0, new StringReader($$1), $$2, $$3);
/*     */   }
/*     */   
/*     */   public static <T> T fromJson(Gson $$0, Reader $$1, TypeToken<T> $$2) {
/* 575 */     return fromJson($$0, $$1, $$2, false);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <T> T fromNullableJson(Gson $$0, String $$1, TypeToken<T> $$2) {
/* 580 */     return fromNullableJson($$0, $$1, $$2, false);
/*     */   }
/*     */   
/*     */   public static <T> T fromJson(Gson $$0, Reader $$1, Class<T> $$2) {
/* 584 */     return fromJson($$0, $$1, $$2, false);
/*     */   }
/*     */   
/*     */   public static <T> T fromJson(Gson $$0, String $$1, Class<T> $$2) {
/* 588 */     return fromJson($$0, $$1, $$2, false);
/*     */   }
/*     */   
/*     */   public static JsonObject parse(String $$0, boolean $$1) {
/* 592 */     return parse(new StringReader($$0), $$1);
/*     */   }
/*     */   
/*     */   public static JsonObject parse(Reader $$0, boolean $$1) {
/* 596 */     return fromJson(GSON, $$0, JsonObject.class, $$1);
/*     */   }
/*     */   
/*     */   public static JsonObject parse(String $$0) {
/* 600 */     return parse($$0, false);
/*     */   }
/*     */   
/*     */   public static JsonObject parse(Reader $$0) {
/* 604 */     return parse($$0, false);
/*     */   }
/*     */   
/*     */   public static JsonArray parseArray(String $$0) {
/* 608 */     return parseArray(new StringReader($$0));
/*     */   }
/*     */   
/*     */   public static JsonArray parseArray(Reader $$0) {
/* 612 */     return fromJson(GSON, $$0, JsonArray.class, false);
/*     */   }
/*     */   
/*     */   public static String toStableString(JsonElement $$0) {
/* 616 */     StringWriter $$1 = new StringWriter();
/* 617 */     JsonWriter $$2 = new JsonWriter($$1);
/*     */     try {
/* 619 */       writeValue($$2, $$0, Comparator.naturalOrder());
/* 620 */     } catch (IOException $$3) {
/*     */       
/* 622 */       throw new AssertionError($$3);
/*     */     } 
/* 624 */     return $$1.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeValue(JsonWriter $$0, @Nullable JsonElement $$1, @Nullable Comparator<String> $$2) throws IOException {
/* 631 */     if ($$1 == null || $$1.isJsonNull()) {
/* 632 */       $$0.nullValue();
/* 633 */     } else if ($$1.isJsonPrimitive()) {
/* 634 */       JsonPrimitive $$3 = $$1.getAsJsonPrimitive();
/* 635 */       if ($$3.isNumber()) {
/* 636 */         $$0.value($$3.getAsNumber());
/* 637 */       } else if ($$3.isBoolean()) {
/* 638 */         $$0.value($$3.getAsBoolean());
/*     */       } else {
/* 640 */         $$0.value($$3.getAsString());
/*     */       } 
/* 642 */     } else if ($$1.isJsonArray()) {
/* 643 */       $$0.beginArray();
/* 644 */       for (JsonElement $$4 : $$1.getAsJsonArray()) {
/* 645 */         writeValue($$0, $$4, $$2);
/*     */       }
/* 647 */       $$0.endArray();
/* 648 */     } else if ($$1.isJsonObject()) {
/* 649 */       $$0.beginObject();
/* 650 */       for (Map.Entry<String, JsonElement> $$5 : sortByKeyIfNeeded($$1.getAsJsonObject().entrySet(), $$2)) {
/* 651 */         $$0.name($$5.getKey());
/* 652 */         writeValue($$0, $$5.getValue(), $$2);
/*     */       } 
/* 654 */       $$0.endObject();
/*     */     } else {
/* 656 */       throw new IllegalArgumentException("Couldn't write " + $$1.getClass());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Collection<Map.Entry<String, JsonElement>> sortByKeyIfNeeded(Collection<Map.Entry<String, JsonElement>> $$0, @Nullable Comparator<String> $$1) {
/* 661 */     if ($$1 == null) {
/* 662 */       return $$0;
/*     */     }
/* 664 */     List<Map.Entry<String, JsonElement>> $$2 = new ArrayList<>($$0);
/* 665 */     $$2.sort((Comparator)Map.Entry.comparingByKey($$1));
/* 666 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\GsonHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */