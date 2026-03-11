/*     */ package net.minecraft.world.entity.monster.warden;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Streams;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ 
/*     */ public class AngerManagement {
/*     */   @VisibleForTesting
/*     */   protected static final int CONVERSION_DELAY = 2;
/*     */   @VisibleForTesting
/*     */   protected static final int MAX_ANGER = 150;
/*     */   private static final int DEFAULT_ANGER_DECREASE = 1;
/*  37 */   private int conversionDelay = Mth.randomBetweenInclusive(RandomSource.create(), 0, 2); int highestAnger; private static final Codec<Pair<UUID, Integer>> SUSPECT_ANGER_PAIR; private final Predicate<Entity> filter;
/*     */   
/*     */   static {
/*  40 */     SUSPECT_ANGER_PAIR = RecordCodecBuilder.create($$0 -> $$0.group((App)UUIDUtil.CODEC.fieldOf("uuid").forGetter(Pair::getFirst), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("anger").forGetter(Pair::getSecond)).apply((Applicative)$$0, Pair::of));
/*     */   } @VisibleForTesting
/*     */   protected final ArrayList<Entity> suspects; private final Sorter suspectSorter; @VisibleForTesting
/*     */   protected final Object2IntMap<Entity> angerBySuspect; @VisibleForTesting
/*     */   protected final Object2IntMap<UUID> angerByUuid;
/*     */   public static Codec<AngerManagement> codec(Predicate<Entity> $$0) {
/*  46 */     return RecordCodecBuilder.create($$1 -> $$1.group((App)SUSPECT_ANGER_PAIR.listOf().fieldOf("suspects").orElse(Collections.emptyList()).forGetter(AngerManagement::createUuidAngerPairs)).apply((Applicative)$$1, ()));
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   protected static final class Sorter extends Record implements Comparator<Entity> {
/*     */     private final AngerManagement angerManagement;
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #58	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;
/*     */     }
/*     */     
/*     */     public AngerManagement angerManagement() {
/*  58 */       return this.angerManagement;
/*     */     } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #58	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #58	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;
/*  59 */       //   0	8	1	$$0	Ljava/lang/Object; } protected Sorter(AngerManagement $$0) { this.angerManagement = $$0; }
/*     */     
/*     */     public int compare(Entity $$0, Entity $$1) {
/*  62 */       if ($$0.equals($$1)) {
/*  63 */         return 0;
/*     */       }
/*     */       
/*  66 */       int $$2 = this.angerManagement.angerBySuspect.getOrDefault($$0, 0);
/*  67 */       int $$3 = this.angerManagement.angerBySuspect.getOrDefault($$1, 0);
/*     */ 
/*     */       
/*  70 */       this.angerManagement.highestAnger = Math.max(this.angerManagement.highestAnger, Math.max($$2, $$3));
/*     */       
/*  72 */       boolean $$4 = AngerLevel.byAnger($$2).isAngry();
/*  73 */       boolean $$5 = AngerLevel.byAnger($$3).isAngry();
/*  74 */       if ($$4 != $$5) {
/*  75 */         return $$4 ? -1 : 1;
/*     */       }
/*     */ 
/*     */       
/*  79 */       boolean $$6 = $$0 instanceof net.minecraft.world.entity.player.Player;
/*  80 */       boolean $$7 = $$1 instanceof net.minecraft.world.entity.player.Player;
/*  81 */       if ($$6 != $$7) {
/*  82 */         return $$6 ? -1 : 1;
/*     */       }
/*  84 */       return Integer.compare($$3, $$2);
/*     */     }
/*     */   }
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
/*     */   public AngerManagement(Predicate<Entity> $$0, List<Pair<UUID, Integer>> $$1) {
/*  98 */     this.filter = $$0;
/*  99 */     this.suspects = new ArrayList<>();
/* 100 */     this.suspectSorter = new Sorter(this);
/* 101 */     this.angerBySuspect = (Object2IntMap<Entity>)new Object2IntOpenHashMap();
/*     */     
/* 103 */     this.angerByUuid = (Object2IntMap<UUID>)new Object2IntOpenHashMap($$1.size());
/* 104 */     $$1.forEach($$0 -> this.angerByUuid.put($$0.getFirst(), (Integer)$$0.getSecond()));
/*     */   }
/*     */   
/*     */   private List<Pair<UUID, Integer>> createUuidAngerPairs() {
/* 108 */     return (List<Pair<UUID, Integer>>)Streams.concat(new Stream[] { this.suspects
/* 109 */           .stream().map($$0 -> Pair.of($$0.getUUID(), Integer.valueOf(this.angerBySuspect.getInt($$0)))), this.angerByUuid
/* 110 */           .object2IntEntrySet().stream().map($$0 -> Pair.of($$0.getKey(), Integer.valueOf($$0.getIntValue())))
/* 111 */         }).collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public void tick(ServerLevel $$0, Predicate<Entity> $$1) {
/* 115 */     this.conversionDelay--;
/* 116 */     if (this.conversionDelay <= 0) {
/* 117 */       convertFromUuids($$0);
/* 118 */       this.conversionDelay = 2;
/*     */     } 
/*     */ 
/*     */     
/* 122 */     ObjectIterator<Object2IntMap.Entry<UUID>> $$2 = this.angerByUuid.object2IntEntrySet().iterator();
/* 123 */     while ($$2.hasNext()) {
/* 124 */       Object2IntMap.Entry<UUID> $$3 = (Object2IntMap.Entry<UUID>)$$2.next();
/* 125 */       int $$4 = $$3.getIntValue();
/*     */       
/* 127 */       if ($$4 <= 1) {
/* 128 */         $$2.remove(); continue;
/*     */       } 
/* 130 */       $$3.setValue($$4 - 1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 135 */     ObjectIterator<Object2IntMap.Entry<Entity>> $$5 = this.angerBySuspect.object2IntEntrySet().iterator();
/* 136 */     while ($$5.hasNext()) {
/* 137 */       Object2IntMap.Entry<Entity> $$6 = (Object2IntMap.Entry<Entity>)$$5.next();
/* 138 */       int $$7 = $$6.getIntValue();
/* 139 */       Entity $$8 = (Entity)$$6.getKey();
/* 140 */       Entity.RemovalReason $$9 = $$8.getRemovalReason();
/* 141 */       if ($$7 <= 1 || !$$1.test($$8) || $$9 != null) {
/* 142 */         this.suspects.remove($$8);
/* 143 */         $$5.remove();
/*     */ 
/*     */         
/* 146 */         if ($$7 > 1 && $$9 != null)
/* 147 */           switch ($$9) { case CHANGED_DIMENSION: case UNLOADED_TO_CHUNK: case UNLOADED_WITH_PLAYER:
/* 148 */               this.angerByUuid.put($$8.getUUID(), $$7 - 1); continue; }
/*     */            
/*     */         continue;
/*     */       } 
/* 152 */       $$6.setValue($$7 - 1);
/*     */     } 
/*     */ 
/*     */     
/* 156 */     sortAndUpdateHighestAnger();
/*     */   }
/*     */   
/*     */   private void sortAndUpdateHighestAnger() {
/* 160 */     this.highestAnger = 0;
/* 161 */     this.suspects.sort(this.suspectSorter);
/*     */ 
/*     */     
/* 164 */     if (this.suspects.size() == 1) {
/* 165 */       this.highestAnger = this.angerBySuspect.getInt(this.suspects.get(0));
/*     */     }
/*     */   }
/*     */   
/*     */   private void convertFromUuids(ServerLevel $$0) {
/* 170 */     ObjectIterator<Object2IntMap.Entry<UUID>> $$1 = this.angerByUuid.object2IntEntrySet().iterator();
/* 171 */     while ($$1.hasNext()) {
/* 172 */       Object2IntMap.Entry<UUID> $$2 = (Object2IntMap.Entry<UUID>)$$1.next();
/* 173 */       int $$3 = $$2.getIntValue();
/*     */       
/* 175 */       Entity $$4 = $$0.getEntity((UUID)$$2.getKey());
/* 176 */       if ($$4 != null) {
/* 177 */         this.angerBySuspect.put($$4, $$3);
/* 178 */         this.suspects.add($$4);
/* 179 */         $$1.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int increaseAnger(Entity $$0, int $$1) {
/* 185 */     boolean $$2 = !this.angerBySuspect.containsKey($$0);
/* 186 */     int $$3 = this.angerBySuspect.computeInt($$0, ($$1, $$2) -> Integer.valueOf(Math.min(150, (($$2 == null) ? 0 : $$2.intValue()) + $$0)));
/*     */     
/* 188 */     if ($$2) {
/* 189 */       int $$4 = this.angerByUuid.removeInt($$0.getUUID());
/* 190 */       $$3 += $$4;
/* 191 */       this.angerBySuspect.put($$0, $$3);
/* 192 */       this.suspects.add($$0);
/*     */     } 
/* 194 */     sortAndUpdateHighestAnger();
/* 195 */     return $$3;
/*     */   }
/*     */   
/*     */   public void clearAnger(Entity $$0) {
/* 199 */     this.angerBySuspect.removeInt($$0);
/* 200 */     this.suspects.remove($$0);
/* 201 */     sortAndUpdateHighestAnger();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Entity getTopSuspect() {
/* 206 */     return this.suspects.stream().filter(this.filter).findFirst().orElse(null);
/*     */   }
/*     */   
/*     */   public int getActiveAnger(@Nullable Entity $$0) {
/* 210 */     return ($$0 == null) ? this.highestAnger : this.angerBySuspect.getInt($$0);
/*     */   }
/*     */   
/*     */   public Optional<LivingEntity> getActiveEntity() {
/* 214 */     return Optional.<Entity>ofNullable(getTopSuspect())
/* 215 */       .filter($$0 -> $$0 instanceof LivingEntity)
/* 216 */       .map($$0 -> (LivingEntity)$$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\warden\AngerManagement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */