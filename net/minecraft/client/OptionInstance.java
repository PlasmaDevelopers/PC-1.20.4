/*     */ package net.minecraft.client;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntSupplier;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import java.util.function.ToIntFunction;
/*     */ import java.util.stream.IntStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.components.AbstractOptionSliderButton;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.OptionEnum;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public final class OptionInstance<T>
/*     */ {
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  35 */   public static final Enum<Boolean> BOOLEAN_VALUES = new Enum<>((List<Boolean>)ImmutableList.of(Boolean.TRUE, Boolean.FALSE), (Codec<Boolean>)Codec.BOOL); public static final CaptionBasedToString<Boolean> BOOLEAN_TO_STRING; private final TooltipSupplier<T> tooltip; final Function<T, Component> toString; private final ValueSet<T> values; private final Codec<T> codec; private final T initialValue; private final Consumer<T> onValueUpdate; final Component caption; T value; static {
/*  36 */     BOOLEAN_TO_STRING = (($$0, $$1) -> $$1.booleanValue() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF);
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
/*     */   public static OptionInstance<Boolean> createBoolean(String $$0, boolean $$1, Consumer<Boolean> $$2) {
/*  49 */     return createBoolean($$0, noTooltip(), $$1, $$2);
/*     */   }
/*     */   
/*     */   public static OptionInstance<Boolean> createBoolean(String $$0, boolean $$1) {
/*  53 */     return createBoolean($$0, noTooltip(), $$1, $$0 -> {
/*     */         
/*     */         });
/*     */   } public static OptionInstance<Boolean> createBoolean(String $$0, TooltipSupplier<Boolean> $$1, boolean $$2) {
/*  57 */     return createBoolean($$0, $$1, $$2, $$0 -> {
/*     */         
/*     */         });
/*     */   } public static OptionInstance<Boolean> createBoolean(String $$0, TooltipSupplier<Boolean> $$1, boolean $$2, Consumer<Boolean> $$3) {
/*  61 */     return createBoolean($$0, $$1, BOOLEAN_TO_STRING, $$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OptionInstance<Boolean> createBoolean(String $$0, TooltipSupplier<Boolean> $$1, CaptionBasedToString<Boolean> $$2, boolean $$3, Consumer<Boolean> $$4) {
/*  71 */     return new OptionInstance<>($$0, $$1, $$2, BOOLEAN_VALUES, 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  76 */         Boolean.valueOf($$3), $$4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionInstance(String $$0, TooltipSupplier<T> $$1, CaptionBasedToString<T> $$2, ValueSet<T> $$3, T $$4, Consumer<T> $$5) {
/*  82 */     this($$0, $$1, $$2, $$3, $$3.codec(), $$4, $$5);
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
/*     */   public OptionInstance(String $$0, TooltipSupplier<T> $$1, CaptionBasedToString<T> $$2, ValueSet<T> $$3, Codec<T> $$4, T $$5, Consumer<T> $$6) {
/*  94 */     this.caption = (Component)Component.translatable($$0);
/*  95 */     this.tooltip = $$1;
/*  96 */     this.toString = ($$1 -> $$0.toString(this.caption, $$1));
/*  97 */     this.values = $$3;
/*  98 */     this.codec = $$4;
/*  99 */     this.initialValue = $$5;
/* 100 */     this.onValueUpdate = $$6;
/* 101 */     this.value = this.initialValue;
/*     */   }
/*     */   
/*     */   public static <T> TooltipSupplier<T> noTooltip() {
/* 105 */     return $$0 -> null;
/*     */   }
/*     */   
/*     */   public static <T> TooltipSupplier<T> cachedConstantTooltip(Component $$0) {
/* 109 */     return $$1 -> Tooltip.create($$0);
/*     */   }
/*     */   
/*     */   public static <T extends OptionEnum> CaptionBasedToString<T> forOptionEnum() {
/* 113 */     return ($$0, $$1) -> $$1.getCaption();
/*     */   }
/*     */   
/*     */   public AbstractWidget createButton(Options $$0, int $$1, int $$2, int $$3) {
/* 117 */     return createButton($$0, $$1, $$2, $$3, $$0 -> {
/*     */         
/*     */         });
/*     */   } public AbstractWidget createButton(Options $$0, int $$1, int $$2, int $$3, Consumer<T> $$4) {
/* 121 */     return this.values.createButton(this.tooltip, $$0, $$1, $$2, $$3, $$4).apply(this);
/*     */   }
/*     */   
/*     */   public T get() {
/* 125 */     return this.value;
/*     */   }
/*     */   
/*     */   public Codec<T> codec() {
/* 129 */     return this.codec;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 134 */     return this.caption.getString();
/*     */   }
/*     */   
/*     */   public void set(T $$0) {
/* 138 */     T $$1 = this.values.validateValue($$0).orElseGet(() -> {
/*     */           LOGGER.error("Illegal option value " + $$0 + " for " + this.caption);
/*     */           return (Supplier)this.initialValue;
/*     */         });
/* 142 */     if (!Minecraft.getInstance().isRunning()) {
/*     */       
/* 144 */       this.value = $$1;
/*     */       return;
/*     */     } 
/* 147 */     if (!Objects.equals(this.value, $$1)) {
/* 148 */       this.value = $$1;
/* 149 */       this.onValueUpdate.accept(this.value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ValueSet<T> values() {
/* 154 */     return this.values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static interface SliderableValueSet<T>
/*     */     extends ValueSet<T>
/*     */   {
/*     */     double toSliderValue(T param1T);
/*     */ 
/*     */ 
/*     */     
/*     */     T fromSliderValue(double param1Double);
/*     */ 
/*     */ 
/*     */     
/*     */     default Function<OptionInstance<T>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<T> $$0, Options $$1, int $$2, int $$3, int $$4, Consumer<T> $$5) {
/* 172 */       return $$6 -> new OptionInstance.OptionInstanceSliderButton<>($$0, $$1, $$2, $$3, 20, $$6, this, $$4, $$5);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static interface CycleableValueSet<T>
/*     */     extends ValueSet<T>
/*     */   {
/*     */     CycleButton.ValueListSupplier<T> valueListSupplier();
/*     */ 
/*     */     
/*     */     default ValueSetter<T> valueSetter() {
/* 184 */       return OptionInstance::set;
/*     */     }
/*     */ 
/*     */     
/*     */     default Function<OptionInstance<T>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<T> $$0, Options $$1, int $$2, int $$3, int $$4, Consumer<T> $$5) {
/* 189 */       return $$6 -> CycleButton.builder($$6.toString).withValues(valueListSupplier()).withTooltip($$0).withInitialValue($$6.value).create($$1, $$2, $$3, 20, $$6.caption, ());
/*     */     }
/*     */ 
/*     */     
/*     */     public static interface ValueSetter<T>
/*     */     {
/*     */       void set(OptionInstance<T> param2OptionInstance, T param2T);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static interface SliderableOrCyclableValueSet<T>
/*     */     extends CycleableValueSet<T>, SliderableValueSet<T>
/*     */   {
/*     */     boolean createCycleButton();
/*     */     
/*     */     default Function<OptionInstance<T>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<T> $$0, Options $$1, int $$2, int $$3, int $$4, Consumer<T> $$5) {
/* 206 */       if (createCycleButton()) {
/* 207 */         return super.createButton($$0, $$1, $$2, $$3, $$4, $$5);
/*     */       }
/* 209 */       return super.createButton($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class AltEnum<T> extends Record implements CycleableValueSet<T> { private final List<T> values;
/*     */     private final List<T> altValues;
/*     */     private final BooleanSupplier altCondition;
/*     */     private final OptionInstance.CycleableValueSet.ValueSetter<T> valueSetter;
/*     */     private final Codec<T> codec;
/*     */     
/* 219 */     public AltEnum(List<T> $$0, List<T> $$1, BooleanSupplier $$2, OptionInstance.CycleableValueSet.ValueSetter<T> $$3, Codec<T> $$4) { this.values = $$0; this.altValues = $$1; this.altCondition = $$2; this.valueSetter = $$3; this.codec = $$4; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/OptionInstance$AltEnum;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #219	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$AltEnum;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$AltEnum<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/OptionInstance$AltEnum;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #219	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$AltEnum;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$AltEnum<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/OptionInstance$AltEnum;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #219	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/OptionInstance$AltEnum;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 219 */       //   0	8	0	this	Lnet/minecraft/client/OptionInstance$AltEnum<TT;>; } public List<T> values() { return this.values; } public List<T> altValues() { return this.altValues; } public BooleanSupplier altCondition() { return this.altCondition; } public OptionInstance.CycleableValueSet.ValueSetter<T> valueSetter() { return this.valueSetter; } public Codec<T> codec() { return this.codec; }
/*     */     
/*     */     public CycleButton.ValueListSupplier<T> valueListSupplier() {
/* 222 */       return CycleButton.ValueListSupplier.create(this.altCondition, this.values, this.altValues);
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<T> validateValue(T $$0) {
/* 227 */       return (this.altCondition.getAsBoolean() ? this.altValues : this.values).contains($$0) ? Optional.<T>of($$0) : Optional.<T>empty();
/*     */     } }
/*     */   public static final class Enum<T> extends Record implements CycleableValueSet<T> { private final List<T> values; private final Codec<T> codec;
/*     */     
/* 231 */     public Enum(List<T> $$0, Codec<T> $$1) { this.values = $$0; this.codec = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/OptionInstance$Enum;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #231	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$Enum;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$Enum<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/OptionInstance$Enum;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #231	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$Enum;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$Enum<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/OptionInstance$Enum;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #231	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/OptionInstance$Enum;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 231 */       //   0	8	0	this	Lnet/minecraft/client/OptionInstance$Enum<TT;>; } public List<T> values() { return this.values; } public Codec<T> codec() { return this.codec; }
/*     */     
/*     */     public Optional<T> validateValue(T $$0) {
/* 234 */       return this.values.contains($$0) ? Optional.<T>of($$0) : Optional.<T>empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public CycleButton.ValueListSupplier<T> valueListSupplier() {
/* 239 */       return CycleButton.ValueListSupplier.create(this.values);
/*     */     } }
/*     */   public static final class LazyEnum<T> extends Record implements CycleableValueSet<T> { private final Supplier<List<T>> values; private final Function<T, Optional<T>> validateValue; private final Codec<T> codec;
/*     */     
/* 243 */     public LazyEnum(Supplier<List<T>> $$0, Function<T, Optional<T>> $$1, Codec<T> $$2) { this.values = $$0; this.validateValue = $$1; this.codec = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/OptionInstance$LazyEnum;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #243	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$LazyEnum;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$LazyEnum<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/OptionInstance$LazyEnum;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #243	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$LazyEnum;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$LazyEnum<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/OptionInstance$LazyEnum;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #243	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/OptionInstance$LazyEnum;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 243 */       //   0	8	0	this	Lnet/minecraft/client/OptionInstance$LazyEnum<TT;>; } public Supplier<List<T>> values() { return this.values; } public Function<T, Optional<T>> validateValue() { return this.validateValue; } public Codec<T> codec() { return this.codec; }
/*     */     
/*     */     public Optional<T> validateValue(T $$0) {
/* 246 */       return this.validateValue.apply($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public CycleButton.ValueListSupplier<T> valueListSupplier() {
/* 251 */       return CycleButton.ValueListSupplier.create(this.values.get());
/*     */     } }
/*     */ 
/*     */   
/*     */   private static final class OptionInstanceSliderButton<N> extends AbstractOptionSliderButton {
/*     */     private final OptionInstance<N> instance;
/*     */     private final OptionInstance.SliderableValueSet<N> values;
/*     */     private final OptionInstance.TooltipSupplier<N> tooltipSupplier;
/*     */     private final Consumer<N> onValueChanged;
/*     */     
/*     */     OptionInstanceSliderButton(Options $$0, int $$1, int $$2, int $$3, int $$4, OptionInstance<N> $$5, OptionInstance.SliderableValueSet<N> $$6, OptionInstance.TooltipSupplier<N> $$7, Consumer<N> $$8) {
/* 262 */       super($$0, $$1, $$2, $$3, $$4, $$6.toSliderValue($$5.get()));
/* 263 */       this.instance = $$5;
/* 264 */       this.values = $$6;
/* 265 */       this.tooltipSupplier = $$7;
/* 266 */       this.onValueChanged = $$8;
/* 267 */       updateMessage();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void updateMessage() {
/* 272 */       setMessage(this.instance.toString.apply(this.instance.get()));
/* 273 */       setTooltip(this.tooltipSupplier.apply(this.values.fromSliderValue(this.value)));
/*     */     }
/*     */ 
/*     */     
/*     */     protected void applyValue() {
/* 278 */       this.instance.set(this.values.fromSliderValue(this.value));
/* 279 */       this.options.save();
/* 280 */       this.onValueChanged.accept(this.instance.get());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static interface IntRangeBase
/*     */     extends SliderableValueSet<Integer>
/*     */   {
/*     */     default double toSliderValue(Integer $$0) {
/* 291 */       return Mth.map($$0.intValue(), minInclusive(), maxInclusive(), 0.0F, 1.0F);
/*     */     }
/*     */ 
/*     */     
/*     */     default Integer fromSliderValue(double $$0) {
/* 296 */       return Integer.valueOf(Mth.floor(Mth.map($$0, 0.0D, 1.0D, minInclusive(), maxInclusive())));
/*     */     }
/*     */     
/*     */     default <R> OptionInstance.SliderableValueSet<R> xmap(final IntFunction<? extends R> to, final ToIntFunction<? super R> from) {
/* 300 */       return new OptionInstance.SliderableValueSet<R>()
/*     */         {
/*     */           public Optional<R> validateValue(R $$0) {
/* 303 */             Objects.requireNonNull(to); return OptionInstance.IntRangeBase.this.validateValue(Integer.valueOf(from.applyAsInt($$0))).map(to::apply);
/*     */           }
/*     */ 
/*     */           
/*     */           public double toSliderValue(R $$0) {
/* 308 */             return OptionInstance.IntRangeBase.this.toSliderValue(Integer.valueOf(from.applyAsInt($$0)));
/*     */           }
/*     */ 
/*     */           
/*     */           public R fromSliderValue(double $$0) {
/* 313 */             return to.apply(OptionInstance.IntRangeBase.this.fromSliderValue($$0).intValue());
/*     */           }
/*     */           
/*     */           public Codec<R> codec()
/*     */           {
/* 318 */             Objects.requireNonNull(to); Objects.requireNonNull(from); return OptionInstance.IntRangeBase.this.codec().xmap(to::apply, from::applyAsInt); } }; } int minInclusive(); int maxInclusive(); } class null implements SliderableValueSet<R> { public Optional<R> validateValue(R $$0) { Objects.requireNonNull(to); return OptionInstance.IntRangeBase.this.validateValue(Integer.valueOf(from.applyAsInt($$0))).map(to::apply); } public Codec<R> codec() { Objects.requireNonNull(to); Objects.requireNonNull(from); return OptionInstance.IntRangeBase.this.codec().xmap(to::apply, from::applyAsInt); }
/*     */      public double toSliderValue(R $$0) {
/*     */       return OptionInstance.IntRangeBase.this.toSliderValue(Integer.valueOf(from.applyAsInt($$0)));
/*     */     } public R fromSliderValue(double $$0) {
/*     */       return to.apply(OptionInstance.IntRangeBase.this.fromSliderValue($$0).intValue());
/*     */     } } public static final class IntRange extends Record implements IntRangeBase { private final int minInclusive; private final int maxInclusive; public IntRange(int $$0, int $$1) {
/* 324 */       this.minInclusive = $$0; this.maxInclusive = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/OptionInstance$IntRange;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #324	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$IntRange; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/OptionInstance$IntRange;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #324	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$IntRange; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/OptionInstance$IntRange;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #324	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/OptionInstance$IntRange;
/* 324 */       //   0	8	1	$$0	Ljava/lang/Object; } public int minInclusive() { return this.minInclusive; } public int maxInclusive() { return this.maxInclusive; }
/*     */     
/*     */     public Optional<Integer> validateValue(Integer $$0) {
/* 327 */       return ($$0.compareTo(Integer.valueOf(minInclusive())) >= 0 && $$0.compareTo(Integer.valueOf(maxInclusive())) <= 0) ? Optional.<Integer>of($$0) : Optional.<Integer>empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public Codec<Integer> codec() {
/* 332 */       return Codec.intRange(this.minInclusive, this.maxInclusive + 1);
/*     */     } }
/*     */   public static final class ClampingLazyMaxIntRange extends Record implements IntRangeBase, SliderableOrCyclableValueSet<Integer> { private final int minInclusive; private final IntSupplier maxSupplier; private final int encodableMaxInclusive;
/*     */     
/* 336 */     public ClampingLazyMaxIntRange(int $$0, IntSupplier $$1, int $$2) { this.minInclusive = $$0; this.maxSupplier = $$1; this.encodableMaxInclusive = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #336	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #336	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #336	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;
/* 336 */       //   0	8	1	$$0	Ljava/lang/Object; } public int minInclusive() { return this.minInclusive; } public IntSupplier maxSupplier() { return this.maxSupplier; } public int encodableMaxInclusive() { return this.encodableMaxInclusive; }
/*     */     
/*     */     public Optional<Integer> validateValue(Integer $$0) {
/* 339 */       return Optional.of(Integer.valueOf(Mth.clamp($$0.intValue(), minInclusive(), maxInclusive())));
/*     */     }
/*     */ 
/*     */     
/*     */     public int maxInclusive() {
/* 344 */       return this.maxSupplier.getAsInt();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Codec<Integer> codec() {
/* 350 */       return ExtraCodecs.validate((Codec)Codec.INT, $$0 -> {
/*     */             int $$1 = this.encodableMaxInclusive + 1;
/* 352 */             return ($$0.compareTo(Integer.valueOf(this.minInclusive)) >= 0 && $$0.compareTo(Integer.valueOf($$1)) <= 0) ? DataResult.success($$0) : DataResult.error((), $$0);
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean createCycleButton() {
/* 361 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public CycleButton.ValueListSupplier<Integer> valueListSupplier() {
/* 366 */       return CycleButton.ValueListSupplier.create(IntStream.range(this.minInclusive, maxInclusive() + 1).boxed().toList());
/*     */     } }
/*     */ 
/*     */   
/*     */   public enum UnitDouble implements SliderableValueSet<Double> {
/* 371 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public Optional<Double> validateValue(Double $$0) {
/* 375 */       return ($$0.doubleValue() >= 0.0D && $$0.doubleValue() <= 1.0D) ? Optional.<Double>of($$0) : Optional.<Double>empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public double toSliderValue(Double $$0) {
/* 380 */       return $$0.doubleValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public Double fromSliderValue(double $$0) {
/* 385 */       return Double.valueOf($$0);
/*     */     }
/*     */     
/*     */     public <R> OptionInstance.SliderableValueSet<R> xmap(final DoubleFunction<? extends R> to, final ToDoubleFunction<? super R> from) {
/* 389 */       return new OptionInstance.SliderableValueSet<R>()
/*     */         {
/*     */           public Optional<R> validateValue(R $$0) {
/* 392 */             Objects.requireNonNull(to); return OptionInstance.UnitDouble.this.validateValue(Double.valueOf(from.applyAsDouble($$0))).map(to::apply);
/*     */           }
/*     */ 
/*     */           
/*     */           public double toSliderValue(R $$0) {
/* 397 */             return OptionInstance.UnitDouble.this.toSliderValue(Double.valueOf(from.applyAsDouble($$0)));
/*     */           }
/*     */ 
/*     */           
/*     */           public R fromSliderValue(double $$0) {
/* 402 */             return to.apply(OptionInstance.UnitDouble.this.fromSliderValue($$0).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Codec<R> codec() {
/* 407 */             Objects.requireNonNull(to); Objects.requireNonNull(from); return OptionInstance.UnitDouble.this.codec().xmap(to::apply, from::applyAsDouble);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Codec<Double> codec() {
/* 415 */       return ExtraCodecs.withAlternative(
/* 416 */           Codec.doubleRange(0.0D, 1.0D), (Codec)Codec.BOOL, $$0 -> Double.valueOf($$0.booleanValue() ? 1.0D : 0.0D));
/*     */     }
/*     */   }
/*     */   
/*     */   class null implements SliderableValueSet<R> {
/*     */     public Optional<R> validateValue(R $$0) {
/*     */       Objects.requireNonNull(to);
/*     */       return OptionInstance.UnitDouble.this.validateValue(Double.valueOf(from.applyAsDouble($$0))).map(to::apply);
/*     */     }
/*     */     
/*     */     public double toSliderValue(R $$0) {
/*     */       return OptionInstance.UnitDouble.this.toSliderValue(Double.valueOf(from.applyAsDouble($$0)));
/*     */     }
/*     */     
/*     */     public R fromSliderValue(double $$0) {
/*     */       return to.apply(OptionInstance.UnitDouble.this.fromSliderValue($$0).doubleValue());
/*     */     }
/*     */     
/*     */     public Codec<R> codec() {
/*     */       Objects.requireNonNull(to);
/*     */       Objects.requireNonNull(from);
/*     */       return OptionInstance.UnitDouble.this.codec().xmap(to::apply, from::applyAsDouble);
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface TooltipSupplier<T> {
/*     */     @Nullable
/*     */     Tooltip apply(T param1T);
/*     */   }
/*     */   
/*     */   public static interface CaptionBasedToString<T> {
/*     */     Component toString(Component param1Component, T param1T);
/*     */   }
/*     */   
/*     */   static interface ValueSet<T> {
/*     */     Function<OptionInstance<T>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<T> param1TooltipSupplier, Options param1Options, int param1Int1, int param1Int2, int param1Int3, Consumer<T> param1Consumer);
/*     */     
/*     */     Optional<T> validateValue(T param1T);
/*     */     
/*     */     Codec<T> codec();
/*     */   }
/*     */   
/*     */   public static interface ValueSetter<T> {
/*     */     void set(OptionInstance<T> param1OptionInstance, T param1T);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\OptionInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */