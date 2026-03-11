/*     */ package net.minecraft.resources;
/*     */ 
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.function.UnaryOperator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ResourceLocationException;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class ResourceLocation implements Comparable<ResourceLocation> {
/*  25 */   public static final Codec<ResourceLocation> CODEC = Codec.STRING.comapFlatMap(ResourceLocation::read, ResourceLocation::toString).stable();
/*  26 */   private static final SimpleCommandExceptionType ERROR_INVALID = new SimpleCommandExceptionType((Message)Component.translatable("argument.id.invalid"));
/*     */ 
/*     */   
/*     */   public static final char NAMESPACE_SEPARATOR = ':';
/*     */   
/*     */   public static final String DEFAULT_NAMESPACE = "minecraft";
/*     */   
/*     */   public static final String REALMS_NAMESPACE = "realms";
/*     */   
/*     */   private final String namespace;
/*     */   
/*     */   private final String path;
/*     */ 
/*     */   
/*     */   protected ResourceLocation(String $$0, String $$1, @Nullable Dummy $$2) {
/*  41 */     this.namespace = $$0;
/*  42 */     this.path = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation(String $$0, String $$1) {
/*  47 */     this(assertValidNamespace($$0, $$1), assertValidPath($$0, $$1), null);
/*     */   }
/*     */   
/*     */   private ResourceLocation(String[] $$0) {
/*  51 */     this($$0[0], $$0[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation(String $$0) {
/*  56 */     this(decompose($$0, ':'));
/*     */   }
/*     */   
/*     */   public static ResourceLocation of(String $$0, char $$1) {
/*  60 */     return new ResourceLocation(decompose($$0, $$1));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ResourceLocation tryParse(String $$0) {
/*     */     try {
/*  66 */       return new ResourceLocation($$0);
/*  67 */     } catch (ResourceLocationException $$1) {
/*  68 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ResourceLocation tryBuild(String $$0, String $$1) {
/*     */     try {
/*  75 */       return new ResourceLocation($$0, $$1);
/*  76 */     } catch (ResourceLocationException $$2) {
/*  77 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static String[] decompose(String $$0, char $$1) {
/*  82 */     String[] $$2 = { "minecraft", $$0 };
/*  83 */     int $$3 = $$0.indexOf($$1);
/*  84 */     if ($$3 >= 0) {
/*  85 */       $$2[1] = $$0.substring($$3 + 1);
/*  86 */       if ($$3 >= 1) {
/*  87 */         $$2[0] = $$0.substring(0, $$3);
/*     */       }
/*     */     } 
/*  90 */     return $$2;
/*     */   }
/*     */   
/*     */   public static DataResult<ResourceLocation> read(String $$0) {
/*     */     try {
/*  95 */       return DataResult.success(new ResourceLocation($$0));
/*  96 */     } catch (ResourceLocationException $$1) {
/*  97 */       return DataResult.error(() -> "Not a valid resource location: " + $$0 + " " + $$1.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getPath() {
/* 102 */     return this.path;
/*     */   }
/*     */   
/*     */   public String getNamespace() {
/* 106 */     return this.namespace;
/*     */   }
/*     */   
/*     */   public ResourceLocation withPath(String $$0) {
/* 110 */     return new ResourceLocation(this.namespace, assertValidPath(this.namespace, $$0), null);
/*     */   }
/*     */   
/*     */   public ResourceLocation withPath(UnaryOperator<String> $$0) {
/* 114 */     return withPath($$0.apply(this.path));
/*     */   }
/*     */   
/*     */   public ResourceLocation withPrefix(String $$0) {
/* 118 */     return withPath($$0 + $$0);
/*     */   }
/*     */   
/*     */   public ResourceLocation withSuffix(String $$0) {
/* 122 */     return withPath(this.path + this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 127 */     return this.namespace + ":" + this.namespace;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 132 */     if (this == $$0) {
/* 133 */       return true;
/*     */     }
/*     */     
/* 136 */     if ($$0 instanceof ResourceLocation) { ResourceLocation $$1 = (ResourceLocation)$$0;
/* 137 */       return (this.namespace.equals($$1.namespace) && this.path.equals($$1.path)); }
/*     */ 
/*     */     
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 145 */     return 31 * this.namespace.hashCode() + this.path.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(ResourceLocation $$0) {
/* 151 */     int $$1 = this.path.compareTo($$0.path);
/* 152 */     if ($$1 == 0) {
/* 153 */       $$1 = this.namespace.compareTo($$0.namespace);
/*     */     }
/* 155 */     return $$1;
/*     */   }
/*     */   
/*     */   public String toDebugFileName() {
/* 159 */     return toString().replace('/', '_').replace(':', '_');
/*     */   }
/*     */   
/*     */   public String toLanguageKey() {
/* 163 */     return this.namespace + "." + this.namespace;
/*     */   }
/*     */   
/*     */   public String toShortLanguageKey() {
/* 167 */     return this.namespace.equals("minecraft") ? this.path : toLanguageKey();
/*     */   }
/*     */   
/*     */   public String toLanguageKey(String $$0) {
/* 171 */     return $$0 + "." + $$0;
/*     */   }
/*     */   
/*     */   public String toLanguageKey(String $$0, String $$1) {
/* 175 */     return $$0 + "." + $$0 + "." + toLanguageKey();
/*     */   }
/*     */   
/*     */   protected static interface Dummy {}
/*     */   
/*     */   public static class Serializer implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation> { public ResourceLocation deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 181 */       return new ResourceLocation(GsonHelper.convertToString($$0, "location"));
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(ResourceLocation $$0, Type $$1, JsonSerializationContext $$2) {
/* 186 */       return (JsonElement)new JsonPrimitive($$0.toString());
/*     */     } }
/*     */ 
/*     */   
/*     */   public static ResourceLocation read(StringReader $$0) throws CommandSyntaxException {
/* 191 */     int $$1 = $$0.getCursor();
/* 192 */     while ($$0.canRead() && isAllowedInResourceLocation($$0.peek())) {
/* 193 */       $$0.skip();
/*     */     }
/* 195 */     String $$2 = $$0.getString().substring($$1, $$0.getCursor());
/*     */     try {
/* 197 */       return new ResourceLocation($$2);
/* 198 */     } catch (ResourceLocationException $$3) {
/* 199 */       $$0.setCursor($$1);
/* 200 */       throw ERROR_INVALID.createWithContext($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isAllowedInResourceLocation(char $$0) {
/* 205 */     return (($$0 >= '0' && $$0 <= '9') || ($$0 >= 'a' && $$0 <= 'z') || $$0 == '_' || $$0 == ':' || $$0 == '/' || $$0 == '.' || $$0 == '-');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidPath(String $$0) {
/* 213 */     for (int $$1 = 0; $$1 < $$0.length(); $$1++) {
/* 214 */       if (!validPathChar($$0.charAt($$1))) {
/* 215 */         return false;
/*     */       }
/*     */     } 
/* 218 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isValidNamespace(String $$0) {
/* 222 */     for (int $$1 = 0; $$1 < $$0.length(); $$1++) {
/* 223 */       if (!validNamespaceChar($$0.charAt($$1))) {
/* 224 */         return false;
/*     */       }
/*     */     } 
/* 227 */     return true;
/*     */   }
/*     */   
/*     */   private static String assertValidNamespace(String $$0, String $$1) {
/* 231 */     if (!isValidNamespace($$0)) {
/* 232 */       throw new ResourceLocationException("Non [a-z0-9_.-] character in namespace of location: " + $$0 + ":" + $$1);
/*     */     }
/* 234 */     return $$0;
/*     */   }
/*     */   
/*     */   public static boolean validPathChar(char $$0) {
/* 238 */     return ($$0 == '_' || $$0 == '-' || ($$0 >= 'a' && $$0 <= 'z') || ($$0 >= '0' && $$0 <= '9') || $$0 == '/' || $$0 == '.');
/*     */   }
/*     */   
/*     */   private static boolean validNamespaceChar(char $$0) {
/* 242 */     return ($$0 == '_' || $$0 == '-' || ($$0 >= 'a' && $$0 <= 'z') || ($$0 >= '0' && $$0 <= '9') || $$0 == '.');
/*     */   }
/*     */   
/*     */   public static boolean isValidResourceLocation(String $$0) {
/* 246 */     String[] $$1 = decompose($$0, ':');
/* 247 */     return (isValidNamespace(StringUtils.isEmpty($$1[0]) ? "minecraft" : $$1[0]) && isValidPath($$1[1]));
/*     */   }
/*     */   
/*     */   private static String assertValidPath(String $$0, String $$1) {
/* 251 */     if (!isValidPath($$1)) {
/* 252 */       throw new ResourceLocationException("Non [a-z0-9/._-] character in path of location: " + $$0 + ":" + $$1);
/*     */     }
/* 254 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\ResourceLocation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */