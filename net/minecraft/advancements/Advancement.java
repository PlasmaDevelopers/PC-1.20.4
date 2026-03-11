/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.advancements.critereon.CriterionValidator;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ 
/*     */ public final class Advancement extends Record {
/*     */   private final Optional<ResourceLocation> parent;
/*     */   private final Optional<DisplayInfo> display;
/*     */   private final AdvancementRewards rewards;
/*     */   private final Map<String, Criterion<?>> criteria;
/*     */   private final AdvancementRequirements requirements;
/*     */   private final boolean sendsTelemetryEvent;
/*     */   private final Optional<Component> name;
/*     */   private static final Codec<Map<String, Criterion<?>>> CRITERIA_CODEC;
/*     */   public static final Codec<Advancement> CODEC;
/*     */   
/*  26 */   public Advancement(Optional<ResourceLocation> $$0, Optional<DisplayInfo> $$1, AdvancementRewards $$2, Map<String, Criterion<?>> $$3, AdvancementRequirements $$4, boolean $$5, Optional<Component> $$6) { this.parent = $$0; this.display = $$1; this.rewards = $$2; this.criteria = $$3; this.requirements = $$4; this.sendsTelemetryEvent = $$5; this.name = $$6; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/Advancement;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #26	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  26 */     //   0	7	0	this	Lnet/minecraft/advancements/Advancement; } public Optional<ResourceLocation> parent() { return this.parent; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/Advancement;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #26	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/Advancement; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/Advancement;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #26	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/Advancement;
/*  26 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<DisplayInfo> display() { return this.display; } public AdvancementRewards rewards() { return this.rewards; } public Map<String, Criterion<?>> criteria() { return this.criteria; } public AdvancementRequirements requirements() { return this.requirements; } public boolean sendsTelemetryEvent() { return this.sendsTelemetryEvent; } public Optional<Component> name() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  35 */     CRITERIA_CODEC = ExtraCodecs.validate(
/*  36 */         (Codec)Codec.unboundedMap((Codec)Codec.STRING, Criterion.CODEC), $$0 -> $$0.isEmpty() ? DataResult.error(()) : DataResult.success($$0));
/*     */ 
/*     */ 
/*     */     
/*  40 */     CODEC = ExtraCodecs.validate(
/*  41 */         RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(ResourceLocation.CODEC, "parent").forGetter(Advancement::parent), (App)ExtraCodecs.strictOptionalField(DisplayInfo.CODEC, "display").forGetter(Advancement::display), (App)ExtraCodecs.strictOptionalField(AdvancementRewards.CODEC, "rewards", AdvancementRewards.EMPTY).forGetter(Advancement::rewards), (App)CRITERIA_CODEC.fieldOf("criteria").forGetter(Advancement::criteria), (App)ExtraCodecs.strictOptionalField(AdvancementRequirements.CODEC, "requirements").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "sends_telemetry_event", Boolean.valueOf(false)).forGetter(Advancement::sendsTelemetryEvent)).apply((Applicative)$$0, ())), Advancement::validate);
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
/*     */ 
/*     */   
/*     */   private static DataResult<Advancement> validate(Advancement $$0) {
/*  56 */     return $$0.requirements().validate($$0.criteria().keySet()).map($$1 -> $$0);
/*     */   }
/*     */   
/*     */   public Advancement(Optional<ResourceLocation> $$0, Optional<DisplayInfo> $$1, AdvancementRewards $$2, Map<String, Criterion<?>> $$3, AdvancementRequirements $$4, boolean $$5) {
/*  60 */     this($$0, $$1, $$2, Map.copyOf($$3), $$4, $$5, $$1.map(Advancement::decorateName));
/*     */   }
/*     */   
/*     */   private static Component decorateName(DisplayInfo $$0) {
/*  64 */     Component $$1 = $$0.getTitle();
/*  65 */     ChatFormatting $$2 = $$0.getType().getChatColor();
/*     */     
/*  67 */     MutableComponent mutableComponent1 = ComponentUtils.mergeStyles($$1.copy(), Style.EMPTY.withColor($$2)).append("\n").append($$0.getDescription());
/*  68 */     MutableComponent mutableComponent2 = $$1.copy().withStyle($$1 -> $$1.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, $$0)));
/*     */     
/*  70 */     return (Component)ComponentUtils.wrapInSquareBrackets((Component)mutableComponent2).withStyle($$2);
/*     */   }
/*     */   
/*     */   public static Component name(AdvancementHolder $$0) {
/*  74 */     return $$0.value().name().orElseGet(() -> Component.literal($$0.id().toString()));
/*     */   }
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  78 */     $$0.writeOptional(this.parent, FriendlyByteBuf::writeResourceLocation);
/*  79 */     $$0.writeOptional(this.display, ($$0, $$1) -> $$1.serializeToNetwork($$0));
/*  80 */     this.requirements.write($$0);
/*  81 */     $$0.writeBoolean(this.sendsTelemetryEvent);
/*     */   }
/*     */   
/*     */   public static Advancement read(FriendlyByteBuf $$0) {
/*  85 */     return new Advancement($$0
/*  86 */         .readOptional(FriendlyByteBuf::readResourceLocation), $$0
/*  87 */         .readOptional(DisplayInfo::fromNetwork), AdvancementRewards.EMPTY, 
/*     */         
/*  89 */         Map.of(), new AdvancementRequirements($$0), $$0
/*     */         
/*  91 */         .readBoolean());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRoot() {
/*  96 */     return this.parent.isEmpty();
/*     */   }
/*     */   
/*     */   public void validate(ProblemReporter $$0, LootDataResolver $$1) {
/* 100 */     this.criteria.forEach(($$2, $$3) -> {
/*     */           CriterionValidator $$4 = new CriterionValidator($$0.forChild($$2), $$1);
/*     */           $$3.triggerInstance().validate($$4);
/*     */         });
/*     */   }
/*     */   
/*     */   public static class Builder {
/* 107 */     private Optional<ResourceLocation> parent = Optional.empty();
/* 108 */     private Optional<DisplayInfo> display = Optional.empty();
/* 109 */     private AdvancementRewards rewards = AdvancementRewards.EMPTY;
/* 110 */     private final ImmutableMap.Builder<String, Criterion<?>> criteria = ImmutableMap.builder();
/* 111 */     private Optional<AdvancementRequirements> requirements = Optional.empty();
/* 112 */     private AdvancementRequirements.Strategy requirementsStrategy = AdvancementRequirements.Strategy.AND;
/*     */     private boolean sendsTelemetryEvent;
/*     */     
/*     */     public static Builder advancement() {
/* 116 */       return (new Builder()).sendsTelemetryEvent();
/*     */     }
/*     */     
/*     */     public static Builder recipeAdvancement() {
/* 120 */       return new Builder();
/*     */     }
/*     */     
/*     */     public Builder parent(AdvancementHolder $$0) {
/* 124 */       this.parent = Optional.of($$0.id());
/* 125 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated(forRemoval = true)
/*     */     public Builder parent(ResourceLocation $$0) {
/* 131 */       this.parent = Optional.of($$0);
/* 132 */       return this;
/*     */     }
/*     */     
/*     */     public Builder display(ItemStack $$0, Component $$1, Component $$2, @Nullable ResourceLocation $$3, AdvancementType $$4, boolean $$5, boolean $$6, boolean $$7) {
/* 136 */       return display(new DisplayInfo($$0, $$1, $$2, Optional.ofNullable($$3), $$4, $$5, $$6, $$7));
/*     */     }
/*     */     
/*     */     public Builder display(ItemLike $$0, Component $$1, Component $$2, @Nullable ResourceLocation $$3, AdvancementType $$4, boolean $$5, boolean $$6, boolean $$7) {
/* 140 */       return display(new DisplayInfo(new ItemStack((ItemLike)$$0.asItem()), $$1, $$2, Optional.ofNullable($$3), $$4, $$5, $$6, $$7));
/*     */     }
/*     */     
/*     */     public Builder display(DisplayInfo $$0) {
/* 144 */       this.display = Optional.of($$0);
/* 145 */       return this;
/*     */     }
/*     */     
/*     */     public Builder rewards(AdvancementRewards.Builder $$0) {
/* 149 */       return rewards($$0.build());
/*     */     }
/*     */     
/*     */     public Builder rewards(AdvancementRewards $$0) {
/* 153 */       this.rewards = $$0;
/* 154 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addCriterion(String $$0, Criterion<?> $$1) {
/* 158 */       this.criteria.put($$0, $$1);
/* 159 */       return this;
/*     */     }
/*     */     
/*     */     public Builder requirements(AdvancementRequirements.Strategy $$0) {
/* 163 */       this.requirementsStrategy = $$0;
/* 164 */       return this;
/*     */     }
/*     */     
/*     */     public Builder requirements(AdvancementRequirements $$0) {
/* 168 */       this.requirements = Optional.of($$0);
/* 169 */       return this;
/*     */     }
/*     */     
/*     */     public Builder sendsTelemetryEvent() {
/* 173 */       this.sendsTelemetryEvent = true;
/* 174 */       return this;
/*     */     }
/*     */     
/*     */     public AdvancementHolder build(ResourceLocation $$0) {
/* 178 */       ImmutableMap immutableMap = this.criteria.buildOrThrow();
/* 179 */       AdvancementRequirements $$2 = this.requirements.orElseGet(() -> this.requirementsStrategy.create($$0.keySet()));
/* 180 */       return new AdvancementHolder($$0, new Advancement(this.parent, this.display, this.rewards, (Map<String, Criterion<?>>)immutableMap, $$2, this.sendsTelemetryEvent));
/*     */     }
/*     */     
/*     */     public AdvancementHolder save(Consumer<AdvancementHolder> $$0, String $$1) {
/* 184 */       AdvancementHolder $$2 = build(new ResourceLocation($$1));
/* 185 */       $$0.accept($$2);
/* 186 */       return $$2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\Advancement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */