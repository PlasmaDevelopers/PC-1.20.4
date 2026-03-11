/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.OptionInstance;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
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
/*     */ public class Builder<T>
/*     */ {
/*     */   private int initialIndex;
/*     */   @Nullable
/*     */   private T initialValue;
/*     */   private final Function<T, Component> valueStringifier;
/*     */   private OptionInstance.TooltipSupplier<T> tooltipSupplier = $$0 -> null;
/* 166 */   private Function<CycleButton<T>, MutableComponent> narrationProvider = CycleButton::createDefaultNarrationMessage;
/*     */   
/* 168 */   private CycleButton.ValueListSupplier<T> values = CycleButton.ValueListSupplier.create((Collection<T>)ImmutableList.of());
/*     */   private boolean displayOnlyValue;
/*     */   
/*     */   public Builder(Function<T, Component> $$0) {
/* 172 */     this.valueStringifier = $$0;
/*     */   }
/*     */   
/*     */   public Builder<T> withValues(Collection<T> $$0) {
/* 176 */     return withValues(CycleButton.ValueListSupplier.create($$0));
/*     */   }
/*     */   
/*     */   @SafeVarargs
/*     */   public final Builder<T> withValues(T... $$0) {
/* 181 */     return withValues((Collection<T>)ImmutableList.copyOf((Object[])$$0));
/*     */   }
/*     */   
/*     */   public Builder<T> withValues(List<T> $$0, List<T> $$1) {
/* 185 */     return withValues(CycleButton.ValueListSupplier.create(CycleButton.DEFAULT_ALT_LIST_SELECTOR, $$0, $$1));
/*     */   }
/*     */   
/*     */   public Builder<T> withValues(BooleanSupplier $$0, List<T> $$1, List<T> $$2) {
/* 189 */     return withValues(CycleButton.ValueListSupplier.create($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public Builder<T> withValues(CycleButton.ValueListSupplier<T> $$0) {
/* 193 */     this.values = $$0;
/* 194 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> withTooltip(OptionInstance.TooltipSupplier<T> $$0) {
/* 198 */     this.tooltipSupplier = $$0;
/* 199 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> withInitialValue(T $$0) {
/* 203 */     this.initialValue = $$0;
/* 204 */     int $$1 = this.values.getDefaultList().indexOf($$0);
/* 205 */     if ($$1 != -1) {
/* 206 */       this.initialIndex = $$1;
/*     */     }
/* 208 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> withCustomNarration(Function<CycleButton<T>, MutableComponent> $$0) {
/* 212 */     this.narrationProvider = $$0;
/* 213 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> displayOnlyValue() {
/* 217 */     this.displayOnlyValue = true;
/* 218 */     return this;
/*     */   }
/*     */   
/*     */   public CycleButton<T> create(int $$0, int $$1, int $$2, int $$3, Component $$4) {
/* 222 */     return create($$0, $$1, $$2, $$3, $$4, ($$0, $$1) -> {
/*     */         
/*     */         });
/*     */   } public CycleButton<T> create(int $$0, int $$1, int $$2, int $$3, Component $$4, CycleButton.OnValueChange<T> $$5) {
/* 226 */     List<T> $$6 = this.values.getDefaultList();
/* 227 */     if ($$6.isEmpty()) {
/* 228 */       throw new IllegalStateException("No values for cycle button");
/*     */     }
/*     */     
/* 231 */     T $$7 = (this.initialValue != null) ? this.initialValue : $$6.get(this.initialIndex);
/* 232 */     Component $$8 = this.valueStringifier.apply($$7);
/* 233 */     Component $$9 = this.displayOnlyValue ? $$8 : (Component)CommonComponents.optionNameValue($$4, $$8);
/*     */     
/* 235 */     return new CycleButton<>($$0, $$1, $$2, $$3, $$9, $$4, this.initialIndex, $$7, this.values, this.valueStringifier, this.narrationProvider, $$5, this.tooltipSupplier, this.displayOnlyValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\CycleButton$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */