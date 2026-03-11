/*     */ package net.minecraft.server.dedicated;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Objects;
/*     */ import java.util.Properties;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.function.UnaryOperator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class Settings<T extends Settings<T>> {
/*     */   public class MutableValue<V>
/*     */     implements Supplier<V> {
/*     */     private final String key;
/*     */     private final V value;
/*     */     private final Function<V, String> serializer;
/*     */     
/*     */     MutableValue(String $$1, V $$2, Function<V, String> $$3) {
/*  34 */       this.key = $$1;
/*  35 */       this.value = $$2;
/*  36 */       this.serializer = $$3;
/*     */     }
/*     */ 
/*     */     
/*     */     public V get() {
/*  41 */       return this.value;
/*     */     }
/*     */     
/*     */     public T update(RegistryAccess $$0, V $$1) {
/*  45 */       Properties $$2 = Settings.this.cloneProperties();
/*  46 */       $$2.put(this.key, this.serializer.apply($$1));
/*  47 */       return Settings.this.reload($$0, $$2);
/*     */     }
/*     */   }
/*     */   
/*  51 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   protected final Properties properties;
/*     */   
/*     */   public Settings(Properties $$0) {
/*  56 */     this.properties = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Properties loadFromFile(Path $$0) {
/*     */     
/*  62 */     try { InputStream $$1 = Files.newInputStream($$0, new java.nio.file.OpenOption[0]);
/*     */ 
/*     */       
/*  65 */       try { CharsetDecoder $$2 = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
/*     */         
/*  67 */         Properties $$3 = new Properties();
/*  68 */         $$3.load(new InputStreamReader($$1, $$2));
/*  69 */         Properties properties1 = $$3;
/*  70 */         if ($$1 != null) $$1.close();  return properties1; } catch (Throwable throwable) { if ($$1 != null)
/*  71 */           try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (CharacterCodingException $$4)
/*  72 */     { LOGGER.info("Failed to load properties as UTF-8 from file {}, trying ISO_8859_1", $$0);
/*  73 */       Reader $$5 = Files.newBufferedReader($$0, StandardCharsets.ISO_8859_1); 
/*  74 */       try { Properties $$6 = new Properties();
/*  75 */         $$6.load($$5);
/*  76 */         Properties properties1 = $$6;
/*  77 */         if ($$5 != null) $$5.close();  return properties1; } catch (Throwable throwable) { if ($$5 != null)
/*     */           try { $$5.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  }
/*  79 */     catch (IOException $$7)
/*  80 */     { LOGGER.error("Failed to load properties from file: {}", $$0, $$7);
/*     */       
/*  82 */       return new Properties(); }
/*     */   
/*     */   } public void store(Path $$0) {
/*     */     
/*  86 */     try { Writer $$1 = Files.newBufferedWriter($$0, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]); 
/*  87 */       try { this.properties.store($$1, "Minecraft server properties");
/*  88 */         if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null) try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$2)
/*  89 */     { LOGGER.error("Failed to store properties to file: {}", $$0); }
/*     */   
/*     */   }
/*     */   
/*     */   private static <V extends Number> Function<String, V> wrapNumberDeserializer(Function<String, V> $$0) {
/*  94 */     return $$1 -> {
/*     */         try {
/*     */           return $$0.apply($$1);
/*  97 */         } catch (NumberFormatException $$2) {
/*     */           return null;
/*     */         } 
/*     */       };
/*     */   }
/*     */   
/*     */   protected static <V> Function<String, V> dispatchNumberOrString(IntFunction<V> $$0, Function<String, V> $$1) {
/* 104 */     return $$2 -> {
/*     */         try {
/*     */           return $$0.apply(Integer.parseInt($$2));
/* 107 */         } catch (NumberFormatException $$3) {
/*     */           return $$1.apply($$2);
/*     */         } 
/*     */       };
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private String getStringRaw(String $$0) {
/* 115 */     return (String)this.properties.get($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected <V> V getLegacy(String $$0, Function<String, V> $$1) {
/* 120 */     String $$2 = getStringRaw($$0);
/* 121 */     if ($$2 == null) {
/* 122 */       return null;
/*     */     }
/* 124 */     this.properties.remove($$0);
/* 125 */     return $$1.apply($$2);
/*     */   }
/*     */   
/*     */   protected <V> V get(String $$0, Function<String, V> $$1, Function<V, String> $$2, V $$3) {
/* 129 */     String $$4 = getStringRaw($$0);
/* 130 */     V $$5 = (V)MoreObjects.firstNonNull(($$4 != null) ? $$1.apply($$4) : null, $$3);
/* 131 */     this.properties.put($$0, $$2.apply($$5));
/* 132 */     return $$5;
/*     */   }
/*     */   
/*     */   protected <V> MutableValue<V> getMutable(String $$0, Function<String, V> $$1, Function<V, String> $$2, V $$3) {
/* 136 */     String $$4 = getStringRaw($$0);
/* 137 */     V $$5 = (V)MoreObjects.firstNonNull(($$4 != null) ? $$1.apply($$4) : null, $$3);
/* 138 */     this.properties.put($$0, $$2.apply($$5));
/* 139 */     return new MutableValue<>($$0, $$5, $$2);
/*     */   }
/*     */   
/*     */   protected <V> V get(String $$0, Function<String, V> $$1, UnaryOperator<V> $$2, Function<V, String> $$3, V $$4) {
/* 143 */     return get($$0, $$2 -> { V $$3 = $$0.apply($$2); return ($$3 != null) ? $$1.apply($$3) : null; }$$3, $$4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <V> V get(String $$0, Function<String, V> $$1, V $$2) {
/* 150 */     return get($$0, $$1, Objects::toString, $$2);
/*     */   }
/*     */   
/*     */   protected <V> MutableValue<V> getMutable(String $$0, Function<String, V> $$1, V $$2) {
/* 154 */     return getMutable($$0, $$1, Objects::toString, $$2);
/*     */   }
/*     */   
/*     */   protected String get(String $$0, String $$1) {
/* 158 */     return get($$0, Function.identity(), Function.identity(), $$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected String getLegacyString(String $$0) {
/* 163 */     return getLegacy($$0, Function.identity());
/*     */   }
/*     */   
/*     */   protected int get(String $$0, int $$1) {
/* 167 */     return ((Integer)get($$0, wrapNumberDeserializer(Integer::parseInt), Integer.valueOf($$1))).intValue();
/*     */   }
/*     */   
/*     */   protected MutableValue<Integer> getMutable(String $$0, int $$1) {
/* 171 */     return getMutable($$0, wrapNumberDeserializer(Integer::parseInt), Integer.valueOf($$1));
/*     */   }
/*     */   
/*     */   protected int get(String $$0, UnaryOperator<Integer> $$1, int $$2) {
/* 175 */     return ((Integer)get($$0, wrapNumberDeserializer(Integer::parseInt), $$1, Objects::toString, Integer.valueOf($$2))).intValue();
/*     */   }
/*     */   
/*     */   protected long get(String $$0, long $$1) {
/* 179 */     return ((Long)get($$0, wrapNumberDeserializer(Long::parseLong), Long.valueOf($$1))).longValue();
/*     */   }
/*     */   
/*     */   protected boolean get(String $$0, boolean $$1) {
/* 183 */     return ((Boolean)get($$0, Boolean::valueOf, Boolean.valueOf($$1))).booleanValue();
/*     */   }
/*     */   
/*     */   protected MutableValue<Boolean> getMutable(String $$0, boolean $$1) {
/* 187 */     return getMutable($$0, Boolean::valueOf, Boolean.valueOf($$1));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Boolean getLegacyBoolean(String $$0) {
/* 192 */     return getLegacy($$0, Boolean::valueOf);
/*     */   }
/*     */   
/*     */   protected Properties cloneProperties() {
/* 196 */     Properties $$0 = new Properties();
/* 197 */     $$0.putAll(this.properties);
/* 198 */     return $$0;
/*     */   }
/*     */   
/*     */   protected abstract T reload(RegistryAccess paramRegistryAccess, Properties paramProperties);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\dedicated\Settings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */