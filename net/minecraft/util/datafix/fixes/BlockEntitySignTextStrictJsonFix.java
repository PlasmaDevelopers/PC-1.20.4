/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class BlockEntitySignTextStrictJsonFix
/*     */   extends NamedEntityFix {
/*     */   public BlockEntitySignTextStrictJsonFix(Schema $$0, boolean $$1) {
/*  24 */     super($$0, $$1, "BlockEntitySignTextStrictJsonFix", References.BLOCK_ENTITY, "Sign");
/*     */   }
/*     */   
/*  27 */   public static final Gson GSON = (new GsonBuilder())
/*  28 */     .registerTypeAdapter(Component.class, new JsonDeserializer<Component>()
/*     */       {
/*     */         public MutableComponent deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/*  31 */           if ($$0.isJsonPrimitive())
/*     */           {
/*  33 */             return Component.literal($$0.getAsString()); } 
/*  34 */           if ($$0.isJsonArray()) {
/*     */             
/*  36 */             JsonArray $$3 = $$0.getAsJsonArray();
/*  37 */             MutableComponent $$4 = null;
/*     */             
/*  39 */             for (JsonElement $$5 : $$3) {
/*  40 */               MutableComponent $$6 = deserialize($$5, $$5.getClass(), $$2);
/*  41 */               if ($$4 == null) {
/*  42 */                 $$4 = $$6; continue;
/*     */               } 
/*  44 */               $$4.append((Component)$$6);
/*     */             } 
/*     */ 
/*     */             
/*  48 */             return $$4;
/*     */           } 
/*  50 */           throw new JsonParseException("Don't know how to turn " + $$0 + " into a Component");
/*     */         }
/*  54 */       }).create();
/*     */   private Dynamic<?> updateLine(Dynamic<?> $$0, String $$1) {
/*     */     MutableComponent mutableComponent;
/*  57 */     String $$2 = $$0.get($$1).asString("");
/*     */     
/*  59 */     Component $$3 = null;
/*  60 */     if ("null".equals($$2) || StringUtils.isEmpty($$2)) {
/*  61 */       $$3 = CommonComponents.EMPTY;
/*  62 */     } else if (($$2
/*  63 */       .charAt(0) == '"' && $$2.charAt($$2.length() - 1) == '"') || ($$2
/*  64 */       .charAt(0) == '{' && $$2.charAt($$2.length() - 1) == '}')) {
/*     */       
/*     */       try {
/*  67 */         $$3 = (Component)GsonHelper.fromNullableJson(GSON, $$2, Component.class, true);
/*  68 */         if ($$3 == null) {
/*  69 */           $$3 = CommonComponents.EMPTY;
/*     */         }
/*  71 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/*  74 */       if ($$3 == null) {
/*     */         try {
/*  76 */           mutableComponent = Component.Serializer.fromJson($$2);
/*  77 */         } catch (Exception exception) {}
/*     */       }
/*     */ 
/*     */       
/*  81 */       if (mutableComponent == null) {
/*     */         try {
/*  83 */           mutableComponent = Component.Serializer.fromJsonLenient($$2);
/*  84 */         } catch (Exception exception) {}
/*     */       }
/*     */ 
/*     */       
/*  88 */       if (mutableComponent == null) {
/*  89 */         mutableComponent = Component.literal($$2);
/*     */       }
/*     */     } else {
/*  92 */       mutableComponent = Component.literal($$2);
/*     */     } 
/*     */     
/*  95 */     return $$0.set($$1, $$0.createString(Component.Serializer.toJson((Component)mutableComponent)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Typed<?> fix(Typed<?> $$0) {
/* 100 */     return $$0.update(DSL.remainderFinder(), $$0 -> {
/*     */           $$0 = updateLine($$0, "Text1");
/*     */           $$0 = updateLine($$0, "Text2");
/*     */           $$0 = updateLine($$0, "Text3");
/*     */           return updateLine($$0, "Text4");
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntitySignTextStrictJsonFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */