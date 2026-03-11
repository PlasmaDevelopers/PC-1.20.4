/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ import net.minecraft.util.valueproviders.ConstantFloat;
/*    */ import net.minecraft.util.valueproviders.FloatProvider;
/*    */ import net.minecraft.util.valueproviders.SampledFloat;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class SoundEventRegistrationSerializer implements JsonDeserializer<SoundEventRegistration> {
/* 19 */   private static final FloatProvider DEFAULT_FLOAT = (FloatProvider)ConstantFloat.of(1.0F);
/*    */ 
/*    */   
/*    */   public SoundEventRegistration deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 23 */     JsonObject $$3 = GsonHelper.convertToJsonObject($$0, "entry");
/*    */     
/* 25 */     boolean $$4 = GsonHelper.getAsBoolean($$3, "replace", false);
/* 26 */     String $$5 = GsonHelper.getAsString($$3, "subtitle", null);
/* 27 */     List<Sound> $$6 = getSounds($$3);
/*    */     
/* 29 */     return new SoundEventRegistration($$6, $$4, $$5);
/*    */   }
/*    */   
/*    */   private List<Sound> getSounds(JsonObject $$0) {
/* 33 */     List<Sound> $$1 = Lists.newArrayList();
/*    */     
/* 35 */     if ($$0.has("sounds")) {
/* 36 */       JsonArray $$2 = GsonHelper.getAsJsonArray($$0, "sounds");
/* 37 */       for (int $$3 = 0; $$3 < $$2.size(); $$3++) {
/* 38 */         JsonElement $$4 = $$2.get($$3);
/*    */         
/* 40 */         if (GsonHelper.isStringValue($$4)) {
/* 41 */           String $$5 = GsonHelper.convertToString($$4, "sound");
/* 42 */           $$1.add(new Sound($$5, (SampledFloat)DEFAULT_FLOAT, (SampledFloat)DEFAULT_FLOAT, 1, Sound.Type.FILE, false, false, 16));
/*    */         } else {
/* 44 */           $$1.add(getSound(GsonHelper.convertToJsonObject($$4, "sound")));
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     return $$1;
/*    */   }
/*    */   
/*    */   private Sound getSound(JsonObject $$0) {
/* 53 */     String $$1 = GsonHelper.getAsString($$0, "name");
/*    */     
/* 55 */     Sound.Type $$2 = getType($$0, Sound.Type.FILE);
/*    */     
/* 57 */     float $$3 = GsonHelper.getAsFloat($$0, "volume", 1.0F);
/* 58 */     Validate.isTrue(($$3 > 0.0F), "Invalid volume", new Object[0]);
/*    */     
/* 60 */     float $$4 = GsonHelper.getAsFloat($$0, "pitch", 1.0F);
/* 61 */     Validate.isTrue(($$4 > 0.0F), "Invalid pitch", new Object[0]);
/*    */     
/* 63 */     int $$5 = GsonHelper.getAsInt($$0, "weight", 1);
/* 64 */     Validate.isTrue(($$5 > 0), "Invalid weight", new Object[0]);
/*    */     
/* 66 */     boolean $$6 = GsonHelper.getAsBoolean($$0, "preload", false);
/*    */     
/* 68 */     boolean $$7 = GsonHelper.getAsBoolean($$0, "stream", false);
/*    */     
/* 70 */     int $$8 = GsonHelper.getAsInt($$0, "attenuation_distance", 16);
/*    */     
/* 72 */     return new Sound($$1, (SampledFloat)ConstantFloat.of($$3), (SampledFloat)ConstantFloat.of($$4), $$5, $$2, $$7, $$6, $$8);
/*    */   }
/*    */   
/*    */   private Sound.Type getType(JsonObject $$0, Sound.Type $$1) {
/* 76 */     Sound.Type $$2 = $$1;
/* 77 */     if ($$0.has("type")) {
/* 78 */       $$2 = Sound.Type.getByName(GsonHelper.getAsString($$0, "type"));
/* 79 */       Validate.notNull($$2, "Invalid type", new Object[0]);
/*    */     } 
/* 81 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\SoundEventRegistrationSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */