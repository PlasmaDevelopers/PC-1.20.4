/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.OptionInstance;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class CycleButton<T>
/*     */   extends AbstractButton {
/*  20 */   public static final BooleanSupplier DEFAULT_ALT_LIST_SELECTOR = Screen::hasAltDown;
/*  21 */   private static final List<Boolean> BOOLEAN_OPTIONS = (List<Boolean>)ImmutableList.of(Boolean.TRUE, Boolean.FALSE);
/*     */   
/*     */   private final Component name;
/*     */   
/*     */   private int index;
/*     */   
/*     */   private T value;
/*     */   
/*     */   private final ValueListSupplier<T> values;
/*     */   
/*     */   private final Function<T, Component> valueStringifier;
/*     */   
/*     */   private final Function<CycleButton<T>, MutableComponent> narrationProvider;
/*     */   private final OnValueChange<T> onValueChange;
/*     */   private final boolean displayOnlyValue;
/*     */   private final OptionInstance.TooltipSupplier<T> tooltipSupplier;
/*     */   
/*     */   CycleButton(int $$0, int $$1, int $$2, int $$3, Component $$4, Component $$5, int $$6, T $$7, ValueListSupplier<T> $$8, Function<T, Component> $$9, Function<CycleButton<T>, MutableComponent> $$10, OnValueChange<T> $$11, OptionInstance.TooltipSupplier<T> $$12, boolean $$13) {
/*  39 */     super($$0, $$1, $$2, $$3, $$4);
/*  40 */     this.name = $$5;
/*  41 */     this.index = $$6;
/*  42 */     this.value = $$7;
/*  43 */     this.values = $$8;
/*  44 */     this.valueStringifier = $$9;
/*  45 */     this.narrationProvider = $$10;
/*  46 */     this.onValueChange = $$11;
/*  47 */     this.displayOnlyValue = $$13;
/*  48 */     this.tooltipSupplier = $$12;
/*  49 */     updateTooltip();
/*     */   }
/*     */   
/*     */   private void updateTooltip() {
/*  53 */     setTooltip(this.tooltipSupplier.apply(this.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPress() {
/*  58 */     if (Screen.hasShiftDown()) {
/*  59 */       cycleValue(-1);
/*     */     } else {
/*  61 */       cycleValue(1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cycleValue(int $$0) {
/*  66 */     List<T> $$1 = this.values.getSelectedList();
/*  67 */     this.index = Mth.positiveModulo(this.index + $$0, $$1.size());
/*  68 */     T $$2 = $$1.get(this.index);
/*     */     
/*  70 */     updateValue($$2);
/*  71 */     this.onValueChange.onValueChange(this, $$2);
/*     */   }
/*     */   
/*     */   private T getCycledValue(int $$0) {
/*  75 */     List<T> $$1 = this.values.getSelectedList();
/*  76 */     return $$1.get(Mth.positiveModulo(this.index + $$0, $$1.size()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/*  81 */     if ($$3 > 0.0D) {
/*  82 */       cycleValue(-1);
/*  83 */     } else if ($$3 < 0.0D) {
/*  84 */       cycleValue(1);
/*     */     } 
/*  86 */     return true;
/*     */   }
/*     */   
/*     */   public void setValue(T $$0) {
/*  90 */     List<T> $$1 = this.values.getSelectedList();
/*  91 */     int $$2 = $$1.indexOf($$0);
/*  92 */     if ($$2 != -1) {
/*  93 */       this.index = $$2;
/*     */     }
/*     */ 
/*     */     
/*  97 */     updateValue($$0);
/*     */   }
/*     */   
/*     */   private void updateValue(T $$0) {
/* 101 */     Component $$1 = createLabelForValue($$0);
/* 102 */     setMessage($$1);
/* 103 */     this.value = $$0;
/* 104 */     updateTooltip();
/*     */   }
/*     */   
/*     */   private Component createLabelForValue(T $$0) {
/* 108 */     return this.displayOnlyValue ? this.valueStringifier.apply($$0) : (Component)createFullName($$0);
/*     */   }
/*     */   
/*     */   private MutableComponent createFullName(T $$0) {
/* 112 */     return CommonComponents.optionNameValue(this.name, this.valueStringifier.apply($$0));
/*     */   }
/*     */   
/*     */   public T getValue() {
/* 116 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MutableComponent createNarrationMessage() {
/* 121 */     return this.narrationProvider.apply(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 127 */     $$0.add(NarratedElementType.TITLE, (Component)createNarrationMessage());
/* 128 */     if (this.active) {
/* 129 */       T $$1 = getCycledValue(1);
/* 130 */       Component $$2 = createLabelForValue($$1);
/* 131 */       if (isFocused()) {
/* 132 */         $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.cycle_button.usage.focused", new Object[] { $$2 }));
/*     */       } else {
/* 134 */         $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.cycle_button.usage.hovered", new Object[] { $$2 }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MutableComponent createDefaultNarrationMessage() {
/* 141 */     return wrapDefaultNarrationMessage(this.displayOnlyValue ? (Component)createFullName(this.value) : getMessage());
/*     */   }
/*     */   
/*     */   public static <T> Builder<T> builder(Function<T, Component> $$0) {
/* 145 */     return new Builder<>($$0);
/*     */   }
/*     */   
/*     */   public static Builder<Boolean> booleanBuilder(Component $$0, Component $$1) {
/* 149 */     return (new Builder<>($$2 -> $$2.booleanValue() ? $$0 : $$1)).withValues(BOOLEAN_OPTIONS);
/*     */   }
/*     */   
/*     */   public static Builder<Boolean> onOffBuilder() {
/* 153 */     return (new Builder<>($$0 -> $$0.booleanValue() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF)).withValues(BOOLEAN_OPTIONS);
/*     */   }
/*     */   
/*     */   public static Builder<Boolean> onOffBuilder(boolean $$0) {
/* 157 */     return onOffBuilder().withInitialValue(Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public static class Builder<T> {
/*     */     private int initialIndex;
/*     */     @Nullable
/*     */     private T initialValue;
/*     */     private final Function<T, Component> valueStringifier;
/*     */     private OptionInstance.TooltipSupplier<T> tooltipSupplier = $$0 -> null;
/* 166 */     private Function<CycleButton<T>, MutableComponent> narrationProvider = CycleButton::createDefaultNarrationMessage;
/*     */     
/* 168 */     private CycleButton.ValueListSupplier<T> values = CycleButton.ValueListSupplier.create((Collection<T>)ImmutableList.of());
/*     */     private boolean displayOnlyValue;
/*     */     
/*     */     public Builder(Function<T, Component> $$0) {
/* 172 */       this.valueStringifier = $$0;
/*     */     }
/*     */     
/*     */     public Builder<T> withValues(Collection<T> $$0) {
/* 176 */       return withValues(CycleButton.ValueListSupplier.create($$0));
/*     */     }
/*     */     
/*     */     @SafeVarargs
/*     */     public final Builder<T> withValues(T... $$0) {
/* 181 */       return withValues((Collection<T>)ImmutableList.copyOf((Object[])$$0));
/*     */     }
/*     */     
/*     */     public Builder<T> withValues(List<T> $$0, List<T> $$1) {
/* 185 */       return withValues(CycleButton.ValueListSupplier.create(CycleButton.DEFAULT_ALT_LIST_SELECTOR, $$0, $$1));
/*     */     }
/*     */     
/*     */     public Builder<T> withValues(BooleanSupplier $$0, List<T> $$1, List<T> $$2) {
/* 189 */       return withValues(CycleButton.ValueListSupplier.create($$0, $$1, $$2));
/*     */     }
/*     */     
/*     */     public Builder<T> withValues(CycleButton.ValueListSupplier<T> $$0) {
/* 193 */       this.values = $$0;
/* 194 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> withTooltip(OptionInstance.TooltipSupplier<T> $$0) {
/* 198 */       this.tooltipSupplier = $$0;
/* 199 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> withInitialValue(T $$0) {
/* 203 */       this.initialValue = $$0;
/* 204 */       int $$1 = this.values.getDefaultList().indexOf($$0);
/* 205 */       if ($$1 != -1) {
/* 206 */         this.initialIndex = $$1;
/*     */       }
/* 208 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> withCustomNarration(Function<CycleButton<T>, MutableComponent> $$0) {
/* 212 */       this.narrationProvider = $$0;
/* 213 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> displayOnlyValue() {
/* 217 */       this.displayOnlyValue = true;
/* 218 */       return this;
/*     */     }
/*     */     
/*     */     public CycleButton<T> create(int $$0, int $$1, int $$2, int $$3, Component $$4) {
/* 222 */       return create($$0, $$1, $$2, $$3, $$4, ($$0, $$1) -> {
/*     */           
/*     */           });
/*     */     } public CycleButton<T> create(int $$0, int $$1, int $$2, int $$3, Component $$4, CycleButton.OnValueChange<T> $$5) {
/* 226 */       List<T> $$6 = this.values.getDefaultList();
/* 227 */       if ($$6.isEmpty()) {
/* 228 */         throw new IllegalStateException("No values for cycle button");
/*     */       }
/*     */       
/* 231 */       T $$7 = (this.initialValue != null) ? this.initialValue : $$6.get(this.initialIndex);
/* 232 */       Component $$8 = this.valueStringifier.apply($$7);
/* 233 */       Component $$9 = this.displayOnlyValue ? $$8 : (Component)CommonComponents.optionNameValue($$4, $$8);
/*     */       
/* 235 */       return new CycleButton<>($$0, $$1, $$2, $$3, $$9, $$4, this.initialIndex, $$7, this.values, this.valueStringifier, this.narrationProvider, $$5, this.tooltipSupplier, this.displayOnlyValue);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface ValueListSupplier<T> {
/*     */     List<T> getSelectedList();
/*     */     
/*     */     List<T> getDefaultList();
/*     */     
/*     */     static <T> ValueListSupplier<T> create(Collection<T> $$0) {
/* 245 */       final ImmutableList copy = ImmutableList.copyOf($$0);
/* 246 */       return new ValueListSupplier<T>()
/*     */         {
/*     */           public List<T> getSelectedList() {
/* 249 */             return copy;
/*     */           }
/*     */ 
/*     */           
/*     */           public List<T> getDefaultList() {
/* 254 */             return copy;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     static <T> ValueListSupplier<T> create(final BooleanSupplier altSelector, List<T> $$1, List<T> $$2) {
/* 260 */       final ImmutableList defaultCopy = ImmutableList.copyOf($$1);
/* 261 */       final ImmutableList altCopy = ImmutableList.copyOf($$2);
/* 262 */       return new ValueListSupplier<T>()
/*     */         {
/*     */           public List<T> getSelectedList() {
/* 265 */             return altSelector.getAsBoolean() ? altCopy : defaultCopy;
/*     */           }
/*     */           
/*     */           public List<T> getDefaultList()
/*     */           {
/* 270 */             return defaultCopy; } }; } } class null implements ValueListSupplier<T> { public List<T> getSelectedList() { return copy; } public List<T> getDefaultList() { return copy; } } class null implements ValueListSupplier<T> { public List<T> getDefaultList() { return defaultCopy; }
/*     */ 
/*     */     
/*     */     public List<T> getSelectedList() {
/*     */       return altSelector.getAsBoolean() ? altCopy : defaultCopy;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static interface OnValueChange<T> {
/*     */     void onValueChange(CycleButton<T> param1CycleButton, T param1T);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\CycleButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */