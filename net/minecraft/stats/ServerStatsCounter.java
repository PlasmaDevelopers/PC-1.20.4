/*     */ package net.minecraft.stats;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.internal.Streams;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerStatsCounter extends StatsCounter {
/*  38 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final MinecraftServer server;
/*     */   private final File file;
/*  42 */   private final Set<Stat<?>> dirty = Sets.newHashSet();
/*     */   
/*     */   public ServerStatsCounter(MinecraftServer $$0, File $$1) {
/*  45 */     this.server = $$0;
/*  46 */     this.file = $$1;
/*  47 */     if ($$1.isFile()) {
/*     */       try {
/*  49 */         parseLocal($$0.getFixerUpper(), FileUtils.readFileToString($$1));
/*  50 */       } catch (IOException $$2) {
/*  51 */         LOGGER.error("Couldn't read statistics file {}", $$1, $$2);
/*  52 */       } catch (JsonParseException $$3) {
/*  53 */         LOGGER.error("Couldn't parse statistics file {}", $$1, $$3);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void save() {
/*     */     try {
/*  60 */       FileUtils.writeStringToFile(this.file, toJson());
/*  61 */     } catch (IOException $$0) {
/*  62 */       LOGGER.error("Couldn't save stats", $$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(Player $$0, Stat<?> $$1, int $$2) {
/*  68 */     super.setValue($$0, $$1, $$2);
/*  69 */     this.dirty.add($$1);
/*     */   }
/*     */   
/*     */   private Set<Stat<?>> getDirty() {
/*  73 */     Set<Stat<?>> $$0 = Sets.newHashSet(this.dirty);
/*  74 */     this.dirty.clear();
/*  75 */     return $$0;
/*     */   }
/*     */   public void parseLocal(DataFixer $$0, String $$1) {
/*     */     
/*  79 */     try { JsonReader $$2 = new JsonReader(new StringReader($$1)); 
/*  80 */       try { $$2.setLenient(false);
/*  81 */         JsonElement $$3 = Streams.parse($$2);
/*     */         
/*  83 */         if ($$3.isJsonNull())
/*  84 */         { LOGGER.error("Unable to parse Stat data from {}", this.file);
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
/* 114 */           $$2.close(); return; }  CompoundTag $$4 = fromJson($$3.getAsJsonObject()); $$4 = DataFixTypes.STATS.updateToCurrentVersion($$0, $$4, NbtUtils.getDataVersion($$4, 1343)); if ($$4.contains("stats", 10)) { CompoundTag $$5 = $$4.getCompound("stats"); for (String $$6 : $$5.getAllKeys()) { if ($$5.contains($$6, 10)) Util.ifElse(BuiltInRegistries.STAT_TYPE.getOptional(new ResourceLocation($$6)), $$2 -> { CompoundTag $$3 = $$0.getCompound($$1); for (String $$4 : $$3.getAllKeys()) { if ($$3.contains($$4, 99)) { Util.ifElse(getStat($$2, $$4), (), ()); continue; }  LOGGER.warn("Invalid statistic value in {}: Don't know what {} is for key {}", new Object[] { this.file, $$3.get($$4), $$4 }); }  }() -> LOGGER.warn("Invalid statistic type in {}: Don't know what {} is", this.file, $$0));  }  }  $$2.close(); } catch (Throwable throwable) { try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (JsonParseException|IOException $$7)
/* 115 */     { LOGGER.error("Unable to parse Stat data from {}", this.file, $$7); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> Optional<Stat<T>> getStat(StatType<T> $$0, String $$1) {
/* 121 */     Objects.requireNonNull($$0.getRegistry());
/* 122 */     Objects.requireNonNull($$0); return Optional.<ResourceLocation>ofNullable(ResourceLocation.tryParse($$1)).flatMap($$0.getRegistry()::getOptional).map($$0::get);
/*     */   }
/*     */   
/*     */   private static CompoundTag fromJson(JsonObject $$0) {
/* 126 */     CompoundTag $$1 = new CompoundTag();
/* 127 */     for (Map.Entry<String, JsonElement> $$2 : (Iterable<Map.Entry<String, JsonElement>>)$$0.entrySet()) {
/* 128 */       JsonElement $$3 = $$2.getValue();
/* 129 */       if ($$3.isJsonObject()) {
/* 130 */         $$1.put($$2.getKey(), (Tag)fromJson($$3.getAsJsonObject())); continue;
/* 131 */       }  if ($$3.isJsonPrimitive()) {
/* 132 */         JsonPrimitive $$4 = $$3.getAsJsonPrimitive();
/* 133 */         if ($$4.isNumber()) {
/* 134 */           $$1.putInt($$2.getKey(), $$4.getAsInt());
/*     */         }
/*     */       } 
/*     */     } 
/* 138 */     return $$1;
/*     */   }
/*     */   
/*     */   protected String toJson() {
/* 142 */     Map<StatType<?>, JsonObject> $$0 = Maps.newHashMap();
/* 143 */     for (ObjectIterator<Object2IntMap.Entry<Stat<?>>> objectIterator = this.stats.object2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<Stat<?>> $$1 = objectIterator.next();
/* 144 */       Stat<?> $$2 = (Stat)$$1.getKey();
/* 145 */       ((JsonObject)$$0.computeIfAbsent($$2.getType(), $$0 -> new JsonObject())).addProperty(getKey($$2).toString(), Integer.valueOf($$1.getIntValue())); }
/*     */ 
/*     */     
/* 148 */     JsonObject $$3 = new JsonObject();
/* 149 */     for (Map.Entry<StatType<?>, JsonObject> $$4 : $$0.entrySet()) {
/* 150 */       $$3.add(BuiltInRegistries.STAT_TYPE.getKey($$4.getKey()).toString(), (JsonElement)$$4.getValue());
/*     */     }
/*     */     
/* 153 */     JsonObject $$5 = new JsonObject();
/* 154 */     $$5.add("stats", (JsonElement)$$3);
/* 155 */     $$5.addProperty("DataVersion", Integer.valueOf(SharedConstants.getCurrentVersion().getDataVersion().getVersion()));
/*     */     
/* 157 */     return $$5.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> ResourceLocation getKey(Stat<T> $$0) {
/* 163 */     return $$0.getType().getRegistry().getKey($$0.getValue());
/*     */   }
/*     */   
/*     */   public void markAllDirty() {
/* 167 */     this.dirty.addAll((Collection<? extends Stat<?>>)this.stats.keySet());
/*     */   }
/*     */   
/*     */   public void sendStats(ServerPlayer $$0) {
/* 171 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/*     */     
/* 173 */     for (Stat<?> $$2 : getDirty()) {
/* 174 */       object2IntOpenHashMap.put($$2, getValue($$2));
/*     */     }
/*     */     
/* 177 */     $$0.connection.send((Packet)new ClientboundAwardStatsPacket((Object2IntMap)object2IntOpenHashMap));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\stats\ServerStatsCounter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */