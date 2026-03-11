/*     */ package net.minecraft.world.level.storage.loot;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
/*     */ 
/*     */ public class IntRange {
/*     */   static {
/*  22 */     RECORD_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(NumberProviders.CODEC, "min").forGetter(()), (App)ExtraCodecs.strictOptionalField(NumberProviders.CODEC, "max").forGetter(())).apply((Applicative)$$0, IntRange::new));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  27 */     CODEC = Codec.either((Codec)Codec.INT, RECORD_CODEC).xmap($$0 -> (IntRange)$$0.map(IntRange::exact, Function.identity()), $$0 -> {
/*     */           OptionalInt $$1 = $$0.unpackExact();
/*     */           return $$1.isPresent() ? Either.left(Integer.valueOf($$1.getAsInt())) : Either.right($$0);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Codec<IntRange> RECORD_CODEC;
/*     */ 
/*     */   
/*     */   public static final Codec<IntRange> CODEC;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private final NumberProvider min;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private final NumberProvider max;
/*     */ 
/*     */   
/*     */   private final IntLimiter limiter;
/*     */ 
/*     */   
/*     */   private final IntChecker predicate;
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<LootContextParam<?>> getReferencedContextParams() {
/*  58 */     ImmutableSet.Builder<LootContextParam<?>> $$0 = ImmutableSet.builder();
/*  59 */     if (this.min != null) {
/*  60 */       $$0.addAll(this.min.getReferencedContextParams());
/*     */     }
/*  62 */     if (this.max != null) {
/*  63 */       $$0.addAll(this.max.getReferencedContextParams());
/*     */     }
/*  65 */     return (Set<LootContextParam<?>>)$$0.build();
/*     */   }
/*     */   
/*     */   private IntRange(Optional<NumberProvider> $$0, Optional<NumberProvider> $$1) {
/*  69 */     this($$0.orElse(null), $$1.orElse(null));
/*     */   }
/*     */   
/*     */   private IntRange(@Nullable NumberProvider $$0, @Nullable NumberProvider $$1) {
/*  73 */     this.min = $$0;
/*  74 */     this.max = $$1;
/*     */     
/*  76 */     if ($$0 == null) {
/*  77 */       if ($$1 == null) {
/*  78 */         this.limiter = (($$0, $$1) -> $$1);
/*  79 */         this.predicate = (($$0, $$1) -> true);
/*     */       } else {
/*  81 */         this.limiter = (($$1, $$2) -> Math.min($$0.getInt($$1), $$2));
/*  82 */         this.predicate = (($$1, $$2) -> ($$2 <= $$0.getInt($$1)));
/*     */       }
/*     */     
/*  85 */     } else if ($$1 == null) {
/*  86 */       this.limiter = (($$1, $$2) -> Math.max($$0.getInt($$1), $$2));
/*  87 */       this.predicate = (($$1, $$2) -> ($$2 >= $$0.getInt($$1)));
/*     */     } else {
/*  89 */       this.limiter = (($$2, $$3) -> Mth.clamp($$3, $$0.getInt($$2), $$1.getInt($$2)));
/*  90 */       this.predicate = (($$2, $$3) -> ($$3 >= $$0.getInt($$2) && $$3 <= $$1.getInt($$2)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static IntRange exact(int $$0) {
/*  96 */     ConstantValue $$1 = ConstantValue.exactly($$0);
/*  97 */     return new IntRange((Optional)Optional.of($$1), (Optional)Optional.of($$1));
/*     */   }
/*     */   
/*     */   public static IntRange range(int $$0, int $$1) {
/* 101 */     return new IntRange((Optional)Optional.of(ConstantValue.exactly($$0)), (Optional)Optional.of(ConstantValue.exactly($$1)));
/*     */   }
/*     */   
/*     */   public static IntRange lowerBound(int $$0) {
/* 105 */     return new IntRange((Optional)Optional.of(ConstantValue.exactly($$0)), Optional.empty());
/*     */   }
/*     */   
/*     */   public static IntRange upperBound(int $$0) {
/* 109 */     return new IntRange(Optional.empty(), (Optional)Optional.of(ConstantValue.exactly($$0)));
/*     */   }
/*     */   
/*     */   public int clamp(LootContext $$0, int $$1) {
/* 113 */     return this.limiter.apply($$0, $$1);
/*     */   }
/*     */   
/*     */   public boolean test(LootContext $$0, int $$1) {
/* 117 */     return this.predicate.test($$0, $$1);
/*     */   }
/*     */   
/*     */   private OptionalInt unpackExact() {
/* 121 */     if (Objects.equals(this.min, this.max)) { NumberProvider numberProvider = this.min; if (numberProvider instanceof ConstantValue) { ConstantValue $$0 = (ConstantValue)numberProvider;
/* 122 */         if (Math.floor($$0.value()) == $$0.value())
/* 123 */           return OptionalInt.of((int)$$0.value());  }
/*     */        }
/*     */     
/* 126 */     return OptionalInt.empty();
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface IntLimiter {
/*     */     int apply(LootContext param1LootContext, int param1Int);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface IntChecker {
/*     */     boolean test(LootContext param1LootContext, int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\IntRange.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */