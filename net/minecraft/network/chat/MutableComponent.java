/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.UnaryOperator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ 
/*     */ public class MutableComponent
/*     */   implements Component
/*     */ {
/*     */   private final ComponentContents contents;
/*     */   private final List<Component> siblings;
/*     */   private Style style;
/*  18 */   private FormattedCharSequence visualOrderText = FormattedCharSequence.EMPTY;
/*     */   @Nullable
/*     */   private Language decomposedWith;
/*     */   
/*     */   MutableComponent(ComponentContents $$0, List<Component> $$1, Style $$2) {
/*  23 */     this.contents = $$0;
/*  24 */     this.siblings = $$1;
/*  25 */     this.style = $$2;
/*     */   }
/*     */   
/*     */   public static MutableComponent create(ComponentContents $$0) {
/*  29 */     return new MutableComponent($$0, Lists.newArrayList(), Style.EMPTY);
/*     */   }
/*     */ 
/*     */   
/*     */   public ComponentContents getContents() {
/*  34 */     return this.contents;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Component> getSiblings() {
/*  39 */     return this.siblings;
/*     */   }
/*     */   
/*     */   public MutableComponent setStyle(Style $$0) {
/*  43 */     this.style = $$0;
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Style getStyle() {
/*  49 */     return this.style;
/*     */   }
/*     */   
/*     */   public MutableComponent append(String $$0) {
/*  53 */     return append(Component.literal($$0));
/*     */   }
/*     */   
/*     */   public MutableComponent append(Component $$0) {
/*  57 */     this.siblings.add($$0);
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   public MutableComponent withStyle(UnaryOperator<Style> $$0) {
/*  62 */     setStyle($$0.apply(getStyle()));
/*  63 */     return this;
/*     */   }
/*     */   
/*     */   public MutableComponent withStyle(Style $$0) {
/*  67 */     setStyle($$0.applyTo(getStyle()));
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public MutableComponent withStyle(ChatFormatting... $$0) {
/*  72 */     setStyle(getStyle().applyFormats($$0));
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public MutableComponent withStyle(ChatFormatting $$0) {
/*  77 */     setStyle(getStyle().applyFormat($$0));
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   public MutableComponent withColor(int $$0) {
/*  82 */     setStyle(getStyle().withColor($$0));
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FormattedCharSequence getVisualOrderText() {
/*  88 */     Language $$0 = Language.getInstance();
/*  89 */     if (this.decomposedWith != $$0) {
/*  90 */       this.visualOrderText = $$0.getVisualOrder(this);
/*  91 */       this.decomposedWith = $$0;
/*     */     } 
/*  93 */     return this.visualOrderText;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  98 */     if (this == $$0) {
/*  99 */       return true;
/*     */     }
/*     */     
/* 102 */     if ($$0 instanceof MutableComponent) { MutableComponent $$1 = (MutableComponent)$$0;
/* 103 */       return (this.contents.equals($$1.contents) && this.style.equals($$1.style) && this.siblings.equals($$1.siblings)); }
/*     */ 
/*     */     
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return Objects.hash(new Object[] { this.contents, this.style, this.siblings });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     StringBuilder $$0 = new StringBuilder(this.contents.toString());
/* 117 */     boolean $$1 = !this.style.isEmpty();
/* 118 */     boolean $$2 = !this.siblings.isEmpty();
/* 119 */     if ($$1 || $$2) {
/* 120 */       $$0.append('[');
/* 121 */       if ($$1) {
/* 122 */         $$0.append("style=");
/* 123 */         $$0.append(this.style);
/*     */       } 
/* 125 */       if ($$1 && $$2) {
/* 126 */         $$0.append(", ");
/*     */       }
/* 128 */       if ($$2) {
/* 129 */         $$0.append("siblings=");
/* 130 */         $$0.append(this.siblings);
/*     */       } 
/* 132 */       $$0.append(']');
/*     */     } 
/* 134 */     return $$0.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\MutableComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */