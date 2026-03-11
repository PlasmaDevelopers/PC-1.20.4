/*     */ package net.minecraft.advancements;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.time.Instant;
/*     */ import java.time.ZoneId;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ 
/*     */ public class AdvancementProgress implements Comparable<AdvancementProgress> {
/*  25 */   private static final DateTimeFormatter OBTAINED_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT); static {
/*  26 */     OBTAINED_TIME_CODEC = ExtraCodecs.temporalCodec(OBTAINED_TIME_FORMAT).xmap(Instant::from, $$0 -> $$0.atZone(ZoneId.systemDefault()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  32 */     CRITERIA_CODEC = Codec.unboundedMap((Codec)Codec.STRING, OBTAINED_TIME_CODEC).xmap($$0 -> (Map)$$0.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, ())), $$0 -> (Map)$$0.entrySet().stream().filter(()).collect(Collectors.toMap(Map.Entry::getKey, ())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(CRITERIA_CODEC, "criteria", Map.of()).forGetter(()), (App)Codec.BOOL.fieldOf("done").orElse(Boolean.valueOf(true)).forGetter(AdvancementProgress::isDone)).apply((Applicative)$$0, ()));
/*     */   }
/*     */   
/*     */   private static final Codec<Instant> OBTAINED_TIME_CODEC;
/*     */   private static final Codec<Map<String, CriterionProgress>> CRITERIA_CODEC;
/*     */   public static final Codec<AdvancementProgress> CODEC;
/*     */   private final Map<String, CriterionProgress> criteria;
/*  46 */   private AdvancementRequirements requirements = AdvancementRequirements.EMPTY;
/*     */   
/*     */   private AdvancementProgress(Map<String, CriterionProgress> $$0) {
/*  49 */     this.criteria = $$0;
/*     */   }
/*     */   
/*     */   public AdvancementProgress() {
/*  53 */     this.criteria = Maps.newHashMap();
/*     */   }
/*     */   
/*     */   public void update(AdvancementRequirements $$0) {
/*  57 */     Set<String> $$1 = $$0.names();
/*  58 */     this.criteria.entrySet().removeIf($$1 -> !$$0.contains($$1.getKey()));
/*  59 */     for (String $$2 : $$1) {
/*  60 */       this.criteria.putIfAbsent($$2, new CriterionProgress());
/*     */     }
/*  62 */     this.requirements = $$0;
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/*  66 */     return this.requirements.test(this::isCriterionDone);
/*     */   }
/*     */   
/*     */   public boolean hasProgress() {
/*  70 */     for (CriterionProgress $$0 : this.criteria.values()) {
/*  71 */       if ($$0.isDone()) {
/*  72 */         return true;
/*     */       }
/*     */     } 
/*  75 */     return false;
/*     */   }
/*     */   
/*     */   public boolean grantProgress(String $$0) {
/*  79 */     CriterionProgress $$1 = this.criteria.get($$0);
/*  80 */     if ($$1 != null && !$$1.isDone()) {
/*  81 */       $$1.grant();
/*  82 */       return true;
/*     */     } 
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   public boolean revokeProgress(String $$0) {
/*  88 */     CriterionProgress $$1 = this.criteria.get($$0);
/*  89 */     if ($$1 != null && $$1.isDone()) {
/*  90 */       $$1.revoke();
/*  91 */       return true;
/*     */     } 
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  98 */     return "AdvancementProgress{criteria=" + this.criteria + ", requirements=" + this.requirements + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeToNetwork(FriendlyByteBuf $$0) {
/* 105 */     $$0.writeMap(this.criteria, FriendlyByteBuf::writeUtf, ($$0, $$1) -> $$1.serializeToNetwork($$0));
/*     */   }
/*     */   
/*     */   public static AdvancementProgress fromNetwork(FriendlyByteBuf $$0) {
/* 109 */     Map<String, CriterionProgress> $$1 = $$0.readMap(FriendlyByteBuf::readUtf, CriterionProgress::fromNetwork);
/* 110 */     return new AdvancementProgress($$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CriterionProgress getCriterion(String $$0) {
/* 115 */     return this.criteria.get($$0);
/*     */   }
/*     */   
/*     */   private boolean isCriterionDone(String $$0) {
/* 119 */     CriterionProgress $$1 = getCriterion($$0);
/* 120 */     return ($$1 != null && $$1.isDone());
/*     */   }
/*     */   
/*     */   public float getPercent() {
/* 124 */     if (this.criteria.isEmpty()) {
/* 125 */       return 0.0F;
/*     */     }
/* 127 */     float $$0 = this.requirements.size();
/* 128 */     float $$1 = countCompletedRequirements();
/* 129 */     return $$1 / $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component getProgressText() {
/* 134 */     if (this.criteria.isEmpty()) {
/* 135 */       return null;
/*     */     }
/*     */     
/* 138 */     int $$0 = this.requirements.size();
/* 139 */     if ($$0 <= 1) {
/* 140 */       return null;
/*     */     }
/*     */     
/* 143 */     int $$1 = countCompletedRequirements();
/* 144 */     return (Component)Component.translatable("advancements.progress", new Object[] { Integer.valueOf($$1), Integer.valueOf($$0) });
/*     */   }
/*     */   
/*     */   private int countCompletedRequirements() {
/* 148 */     return this.requirements.count(this::isCriterionDone);
/*     */   }
/*     */   
/*     */   public Iterable<String> getRemainingCriteria() {
/* 152 */     List<String> $$0 = Lists.newArrayList();
/* 153 */     for (Map.Entry<String, CriterionProgress> $$1 : this.criteria.entrySet()) {
/* 154 */       if (!((CriterionProgress)$$1.getValue()).isDone()) {
/* 155 */         $$0.add($$1.getKey());
/*     */       }
/*     */     } 
/* 158 */     return $$0;
/*     */   }
/*     */   
/*     */   public Iterable<String> getCompletedCriteria() {
/* 162 */     List<String> $$0 = Lists.newArrayList();
/* 163 */     for (Map.Entry<String, CriterionProgress> $$1 : this.criteria.entrySet()) {
/* 164 */       if (((CriterionProgress)$$1.getValue()).isDone()) {
/* 165 */         $$0.add($$1.getKey());
/*     */       }
/*     */     } 
/* 168 */     return $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Instant getFirstProgressDate() {
/* 173 */     return this.criteria.values().stream()
/* 174 */       .map(CriterionProgress::getObtained)
/* 175 */       .filter(Objects::nonNull)
/* 176 */       .min(Comparator.naturalOrder())
/* 177 */       .orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(AdvancementProgress $$0) {
/* 182 */     Instant $$1 = getFirstProgressDate();
/* 183 */     Instant $$2 = $$0.getFirstProgressDate();
/*     */     
/* 185 */     if ($$1 == null && $$2 != null) {
/* 186 */       return 1;
/*     */     }
/* 188 */     if ($$1 != null && $$2 == null) {
/* 189 */       return -1;
/*     */     }
/* 191 */     if ($$1 == null && $$2 == null) {
/* 192 */       return 0;
/*     */     }
/*     */     
/* 195 */     return $$1.compareTo($$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\AdvancementProgress.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */