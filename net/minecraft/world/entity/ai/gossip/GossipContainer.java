/*     */ package net.minecraft.world.entity.ai.gossip;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.DoublePredicate;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class GossipContainer {
/*  32 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final int DISCARD_THRESHOLD = 2;
/*     */   private static final class GossipEntry extends Record { final UUID target; final GossipType type;
/*     */     final int value;
/*     */     public static final Codec<GossipEntry> CODEC;
/*     */     
/*  37 */     GossipEntry(UUID $$0, GossipType $$1, int $$2) { this.target = $$0; this.type = $$1; this.value = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #37	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  37 */       //   0	7	0	this	Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry; } public UUID target() { return this.target; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #37	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #37	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry;
/*  37 */       //   0	8	1	$$0	Ljava/lang/Object; } public GossipType type() { return this.type; } public int value() { return this.value; } static {
/*  38 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)UUIDUtil.CODEC.fieldOf("Target").forGetter(GossipEntry::target), (App)GossipType.CODEC.fieldOf("Type").forGetter(GossipEntry::type), (App)ExtraCodecs.POSITIVE_INT.fieldOf("Value").forGetter(GossipEntry::value)).apply((Applicative)$$0, GossipEntry::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  44 */     public static final Codec<List<GossipEntry>> LIST_CODEC = CODEC.listOf();
/*     */     
/*     */     public int weightedValue() {
/*  47 */       return this.value * this.type.weight;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class EntityGossips {
/*  52 */     final Object2IntMap<GossipType> entries = (Object2IntMap<GossipType>)new Object2IntOpenHashMap();
/*     */     
/*     */     public int weightedValue(Predicate<GossipType> $$0) {
/*  55 */       return this.entries.object2IntEntrySet()
/*  56 */         .stream()
/*  57 */         .filter($$1 -> $$0.test((GossipType)$$1.getKey()))
/*  58 */         .mapToInt($$0 -> $$0.getIntValue() * ((GossipType)$$0.getKey()).weight)
/*  59 */         .sum();
/*     */     }
/*     */     
/*     */     public Stream<GossipContainer.GossipEntry> unpack(UUID $$0) {
/*  63 */       return this.entries.object2IntEntrySet().stream().map($$1 -> new GossipContainer.GossipEntry($$0, (GossipType)$$1.getKey(), $$1.getIntValue()));
/*     */     }
/*     */     
/*     */     public void decay() {
/*  67 */       ObjectIterator<Object2IntMap.Entry<GossipType>> $$0 = this.entries.object2IntEntrySet().iterator();
/*  68 */       while ($$0.hasNext()) {
/*  69 */         Object2IntMap.Entry<GossipType> $$1 = (Object2IntMap.Entry<GossipType>)$$0.next();
/*  70 */         int $$2 = $$1.getIntValue() - ((GossipType)$$1.getKey()).decayPerDay;
/*  71 */         if ($$2 < 2) {
/*  72 */           $$0.remove(); continue;
/*     */         } 
/*  74 */         $$1.setValue($$2);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/*  80 */       return this.entries.isEmpty();
/*     */     }
/*     */     
/*     */     public void makeSureValueIsntTooLowOrTooHigh(GossipType $$0) {
/*  84 */       int $$1 = this.entries.getInt($$0);
/*  85 */       if ($$1 > $$0.max) {
/*  86 */         this.entries.put($$0, $$0.max);
/*     */       }
/*  88 */       if ($$1 < 2) {
/*  89 */         remove($$0);
/*     */       }
/*     */     }
/*     */     
/*     */     public void remove(GossipType $$0) {
/*  94 */       this.entries.removeInt($$0);
/*     */     }
/*     */   }
/*     */   
/*  98 */   private final Map<UUID, EntityGossips> gossips = Maps.newHashMap();
/*     */   
/*     */   @VisibleForDebug
/*     */   public Map<UUID, Object2IntMap<GossipType>> getGossipEntries() {
/* 102 */     Map<UUID, Object2IntMap<GossipType>> $$0 = Maps.newHashMap();
/* 103 */     this.gossips.keySet().forEach($$1 -> {
/*     */           EntityGossips $$2 = this.gossips.get($$1);
/*     */           $$0.put($$1, $$2.entries);
/*     */         });
/* 107 */     return $$0;
/*     */   }
/*     */   
/*     */   public void decay() {
/* 111 */     Iterator<EntityGossips> $$0 = this.gossips.values().iterator();
/* 112 */     while ($$0.hasNext()) {
/* 113 */       EntityGossips $$1 = $$0.next();
/* 114 */       $$1.decay();
/* 115 */       if ($$1.isEmpty())
/*     */       {
/* 117 */         $$0.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private Stream<GossipEntry> unpack() {
/* 123 */     return this.gossips.entrySet().stream().flatMap($$0 -> ((EntityGossips)$$0.getValue()).unpack((UUID)$$0.getKey()));
/*     */   }
/*     */   
/*     */   private Collection<GossipEntry> selectGossipsForTransfer(RandomSource $$0, int $$1) {
/* 127 */     List<GossipEntry> $$2 = unpack().toList();
/* 128 */     if ($$2.isEmpty()) {
/* 129 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 132 */     int[] $$3 = new int[$$2.size()];
/* 133 */     int $$4 = 0;
/* 134 */     for (int $$5 = 0; $$5 < $$2.size(); $$5++) {
/* 135 */       GossipEntry $$6 = $$2.get($$5);
/* 136 */       $$4 += Math.abs($$6.weightedValue());
/* 137 */       $$3[$$5] = $$4 - 1;
/*     */     } 
/*     */     
/* 140 */     Set<GossipEntry> $$7 = Sets.newIdentityHashSet();
/* 141 */     for (int $$8 = 0; $$8 < $$1; $$8++) {
/* 142 */       int $$9 = $$0.nextInt($$4);
/* 143 */       int $$10 = Arrays.binarySearch($$3, $$9);
/* 144 */       $$7.add($$2.get(($$10 < 0) ? (-$$10 - 1) : $$10));
/*     */     } 
/* 146 */     return $$7;
/*     */   }
/*     */   
/*     */   private EntityGossips getOrCreate(UUID $$0) {
/* 150 */     return this.gossips.computeIfAbsent($$0, $$0 -> new EntityGossips());
/*     */   }
/*     */   
/*     */   public void transferFrom(GossipContainer $$0, RandomSource $$1, int $$2) {
/* 154 */     Collection<GossipEntry> $$3 = $$0.selectGossipsForTransfer($$1, $$2);
/*     */     
/* 156 */     $$3.forEach($$0 -> {
/*     */           int $$1 = $$0.value - $$0.type.decayPerTransfer;
/*     */           if ($$1 >= 2) {
/*     */             (getOrCreate($$0.target)).entries.mergeInt($$0.type, $$1, GossipContainer::mergeValuesForTransfer);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReputation(UUID $$0, Predicate<GossipType> $$1) {
/* 169 */     EntityGossips $$2 = this.gossips.get($$0);
/* 170 */     return ($$2 != null) ? $$2.weightedValue($$1) : 0;
/*     */   }
/*     */   
/*     */   public long getCountForType(GossipType $$0, DoublePredicate $$1) {
/* 174 */     return this.gossips.values().stream().filter($$2 -> $$0.test(($$2.entries.getOrDefault($$1, 0) * $$1.weight))).count();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(UUID $$0, GossipType $$1, int $$2) {
/* 181 */     EntityGossips $$3 = getOrCreate($$0);
/* 182 */     $$3.entries.mergeInt($$1, $$2, ($$1, $$2) -> mergeValuesForAddition($$0, $$1, $$2));
/* 183 */     $$3.makeSureValueIsntTooLowOrTooHigh($$1);
/* 184 */     if ($$3.isEmpty()) {
/* 185 */       this.gossips.remove($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(UUID $$0, GossipType $$1, int $$2) {
/* 194 */     add($$0, $$1, -$$2);
/*     */   }
/*     */   
/*     */   public void remove(UUID $$0, GossipType $$1) {
/* 198 */     EntityGossips $$2 = this.gossips.get($$0);
/* 199 */     if ($$2 != null) {
/* 200 */       $$2.remove($$1);
/* 201 */       if ($$2.isEmpty()) {
/* 202 */         this.gossips.remove($$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(GossipType $$0) {
/* 208 */     Iterator<EntityGossips> $$1 = this.gossips.values().iterator();
/* 209 */     while ($$1.hasNext()) {
/* 210 */       EntityGossips $$2 = $$1.next();
/* 211 */       $$2.remove($$0);
/* 212 */       if ($$2.isEmpty()) {
/* 213 */         $$1.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T store(DynamicOps<T> $$0) {
/* 219 */     Objects.requireNonNull($$0); return GossipEntry.LIST_CODEC.encodeStart($$0, unpack().toList()).resultOrPartial($$0 -> LOGGER.warn("Failed to serialize gossips: {}", $$0)).orElseGet($$0::emptyList);
/*     */   }
/*     */   
/*     */   public void update(Dynamic<?> $$0) {
/* 223 */     GossipEntry.LIST_CODEC.decode($$0).resultOrPartial($$0 -> LOGGER.warn("Failed to deserialize gossips: {}", $$0))
/* 224 */       .stream().flatMap($$0 -> ((List)$$0.getFirst()).stream())
/* 225 */       .forEach($$0 -> (getOrCreate($$0.target)).entries.put($$0.type, $$0.value));
/*     */   }
/*     */   
/*     */   private static int mergeValuesForTransfer(int $$0, int $$1) {
/* 229 */     return Math.max($$0, $$1);
/*     */   }
/*     */   
/*     */   private int mergeValuesForAddition(GossipType $$0, int $$1, int $$2) {
/* 233 */     int $$3 = $$1 + $$2;
/* 234 */     return ($$3 > $$0.max) ? Math.max($$0.max, $$1) : $$3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\gossip\GossipContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */