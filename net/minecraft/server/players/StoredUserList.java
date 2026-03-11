/*     */ package net.minecraft.server.players;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class StoredUserList<K, V extends StoredUserEntry<K>> {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  28 */   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
/*     */   
/*     */   private final File file;
/*  31 */   private final Map<String, V> map = Maps.newHashMap();
/*     */   
/*     */   public StoredUserList(File $$0) {
/*  34 */     this.file = $$0;
/*     */   }
/*     */   
/*     */   public File getFile() {
/*  38 */     return this.file;
/*     */   }
/*     */   
/*     */   public void add(V $$0) {
/*  42 */     this.map.put(getKeyForUser($$0.getUser()), $$0);
/*     */     try {
/*  44 */       save();
/*  45 */     } catch (IOException $$1) {
/*  46 */       LOGGER.warn("Could not save the list after adding a user.", $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public V get(K $$0) {
/*  52 */     removeExpired();
/*  53 */     return this.map.get(getKeyForUser($$0));
/*     */   }
/*     */   
/*     */   public void remove(K $$0) {
/*  57 */     this.map.remove(getKeyForUser($$0));
/*     */     try {
/*  59 */       save();
/*  60 */     } catch (IOException $$1) {
/*  61 */       LOGGER.warn("Could not save the list after removing a user.", $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(StoredUserEntry<K> $$0) {
/*  66 */     remove($$0.getUser());
/*     */   }
/*     */   
/*     */   public String[] getUserList() {
/*  70 */     return (String[])this.map.keySet().toArray((Object[])new String[0]);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  74 */     return (this.map.size() < 1);
/*     */   }
/*     */   
/*     */   protected String getKeyForUser(K $$0) {
/*  78 */     return $$0.toString();
/*     */   }
/*     */   
/*     */   protected boolean contains(K $$0) {
/*  82 */     return this.map.containsKey(getKeyForUser($$0));
/*     */   }
/*     */   
/*     */   private void removeExpired() {
/*  86 */     List<K> $$0 = Lists.newArrayList();
/*  87 */     for (StoredUserEntry<K> storedUserEntry : this.map.values()) {
/*  88 */       if (storedUserEntry.hasExpired()) {
/*  89 */         $$0.add(storedUserEntry.getUser());
/*     */       }
/*     */     } 
/*  92 */     for (K $$2 : $$0) {
/*  93 */       this.map.remove(getKeyForUser($$2));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<V> getEntries() {
/* 100 */     return this.map.values();
/*     */   }
/*     */   
/*     */   public void save() throws IOException {
/* 104 */     JsonArray $$0 = new JsonArray();
/* 105 */     Objects.requireNonNull($$0); this.map.values().stream().map($$0 -> { Objects.requireNonNull($$0); return (JsonObject)Util.make(new JsonObject(), $$0::serialize); }).forEach($$0::add);
/* 106 */     BufferedWriter $$1 = Files.newWriter(this.file, StandardCharsets.UTF_8); 
/* 107 */     try { GSON.toJson((JsonElement)$$0, $$1);
/* 108 */       if ($$1 != null) $$1.close();  }
/*     */     catch (Throwable throwable) { if ($$1 != null)
/*     */         try { $$1.close(); }
/*     */         catch (Throwable throwable1)
/*     */         { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 114 */      } public void load() throws IOException { if (!this.file.exists()) {
/*     */       return;
/*     */     }
/* 117 */     BufferedReader $$0 = Files.newReader(this.file, StandardCharsets.UTF_8); try {
/* 118 */       this.map.clear();
/* 119 */       JsonArray $$1 = (JsonArray)GSON.fromJson($$0, JsonArray.class);
/* 120 */       if ($$1 == null)
/*     */       
/*     */       { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 130 */         if ($$0 != null) $$0.close();  return; }  for (JsonElement $$2 : $$1) { JsonObject $$3 = GsonHelper.convertToJsonObject($$2, "entry"); StoredUserEntry<K> $$4 = createEntry($$3); if ($$4.getUser() != null) this.map.put(getKeyForUser($$4.getUser()), (V)$$4);  }  if ($$0 != null) $$0.close(); 
/*     */     } catch (Throwable throwable) {
/*     */       if ($$0 != null)
/*     */         try {
/*     */           $$0.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }  
/*     */       throw throwable;
/*     */     }  }
/*     */ 
/*     */   
/*     */   protected abstract StoredUserEntry<K> createEntry(JsonObject paramJsonObject);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\StoredUserList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */