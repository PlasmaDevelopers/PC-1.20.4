/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.stats.Stat;
/*     */ import net.minecraft.stats.StatType;
/*     */ import net.minecraft.stats.StatsCounter;
/*     */ import net.minecraft.util.ExtraCodecs;
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
/*     */ final class StatMatcher<T>
/*     */   extends Record
/*     */ {
/*     */   private final StatType<T> type;
/*     */   private final Holder<T> value;
/*     */   private final MinMaxBounds.Ints range;
/*     */   private final Supplier<Stat<T>> stat;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #163	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher<TT;>;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #163	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher<TT;>;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #163	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher<TT;>;
/*     */   }
/*     */   
/*     */   private StatMatcher(StatType<T> $$0, Holder<T> $$1, MinMaxBounds.Ints $$2, Supplier<Stat<T>> $$3) {
/* 163 */     this.type = $$0; this.value = $$1; this.range = $$2; this.stat = $$3; } public StatType<T> type() { return this.type; } public Holder<T> value() { return this.value; } public MinMaxBounds.Ints range() { return this.range; } public Supplier<Stat<T>> stat() { return this.stat; }
/* 164 */    public static final Codec<StatMatcher<?>> CODEC = BuiltInRegistries.STAT_TYPE.byNameCodec().dispatch(StatMatcher::type, StatMatcher::createTypedCodec);
/*     */   
/*     */   private static <T> Codec<StatMatcher<T>> createTypedCodec(StatType<T> $$0) {
/* 167 */     return RecordCodecBuilder.create($$1 -> $$1.group((App)$$0.getRegistry().holderByNameCodec().fieldOf("stat").forGetter(StatMatcher::value), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "value", MinMaxBounds.Ints.ANY).forGetter(StatMatcher::range)).apply((Applicative)$$1, ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatMatcher(StatType<T> $$0, Holder<T> $$1, MinMaxBounds.Ints $$2) {
/* 174 */     this($$0, $$1, $$2, (Supplier<Stat<T>>)Suppliers.memoize(() -> $$0.get($$1.value())));
/*     */   }
/*     */   
/*     */   public boolean matches(StatsCounter $$0) {
/* 178 */     return this.range.matches($$0.getValue(this.stat.get()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\PlayerPredicate$StatMatcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */