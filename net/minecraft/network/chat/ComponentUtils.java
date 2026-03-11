/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.network.chat.contents.TranslatableContents;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ 
/*     */ public class ComponentUtils
/*     */ {
/*     */   public static final String DEFAULT_SEPARATOR_TEXT = ", ";
/*  21 */   public static final Component DEFAULT_SEPARATOR = Component.literal(", ").withStyle(ChatFormatting.GRAY);
/*  22 */   public static final Component DEFAULT_NO_STYLE_SEPARATOR = Component.literal(", ");
/*     */   
/*     */   public static MutableComponent mergeStyles(MutableComponent $$0, Style $$1) {
/*  25 */     if ($$1.isEmpty()) {
/*  26 */       return $$0;
/*     */     }
/*     */     
/*  29 */     Style $$2 = $$0.getStyle();
/*  30 */     if ($$2.isEmpty()) {
/*  31 */       return $$0.setStyle($$1);
/*     */     }
/*     */     
/*  34 */     if ($$2.equals($$1)) {
/*  35 */       return $$0;
/*     */     }
/*     */     
/*  38 */     return $$0.setStyle($$2.applyTo($$1));
/*     */   }
/*     */   
/*     */   public static Optional<MutableComponent> updateForEntity(@Nullable CommandSourceStack $$0, Optional<Component> $$1, @Nullable Entity $$2, int $$3) throws CommandSyntaxException {
/*  42 */     return $$1.isPresent() ? Optional.<MutableComponent>of(updateForEntity($$0, $$1.get(), $$2, $$3)) : Optional.<MutableComponent>empty();
/*     */   }
/*     */   
/*     */   public static MutableComponent updateForEntity(@Nullable CommandSourceStack $$0, Component $$1, @Nullable Entity $$2, int $$3) throws CommandSyntaxException {
/*  46 */     if ($$3 > 100) {
/*  47 */       return $$1.copy();
/*     */     }
/*     */     
/*  50 */     MutableComponent $$4 = $$1.getContents().resolve($$0, $$2, $$3 + 1);
/*     */     
/*  52 */     for (Component $$5 : $$1.getSiblings()) {
/*  53 */       $$4.append(updateForEntity($$0, $$5, $$2, $$3 + 1));
/*     */     }
/*     */     
/*  56 */     return $$4.withStyle(resolveStyle($$0, $$1.getStyle(), $$2, $$3));
/*     */   }
/*     */   
/*     */   private static Style resolveStyle(@Nullable CommandSourceStack $$0, Style $$1, @Nullable Entity $$2, int $$3) throws CommandSyntaxException {
/*  60 */     HoverEvent $$4 = $$1.getHoverEvent();
/*  61 */     if ($$4 != null) {
/*  62 */       Component $$5 = $$4.<Component>getValue(HoverEvent.Action.SHOW_TEXT);
/*  63 */       if ($$5 != null) {
/*  64 */         HoverEvent $$6 = new HoverEvent((HoverEvent.Action)HoverEvent.Action.SHOW_TEXT, (T)updateForEntity($$0, $$5, $$2, $$3 + 1));
/*  65 */         return $$1.withHoverEvent($$6);
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     return $$1;
/*     */   }
/*     */   
/*     */   public static Component formatList(Collection<String> $$0) {
/*  73 */     return formatAndSortList($$0, $$0 -> Component.literal($$0).withStyle(ChatFormatting.GREEN));
/*     */   }
/*     */   
/*     */   public static <T extends Comparable<T>> Component formatAndSortList(Collection<T> $$0, Function<T, Component> $$1) {
/*  77 */     if ($$0.isEmpty())
/*  78 */       return CommonComponents.EMPTY; 
/*  79 */     if ($$0.size() == 1) {
/*  80 */       return $$1.apply((T)$$0.iterator().next());
/*     */     }
/*     */     
/*  83 */     List<T> $$2 = Lists.newArrayList($$0);
/*  84 */     $$2.sort(Comparable::compareTo);
/*  85 */     return formatList($$2, $$1);
/*     */   }
/*     */   
/*     */   public static <T> Component formatList(Collection<? extends T> $$0, Function<T, Component> $$1) {
/*  89 */     return formatList($$0, DEFAULT_SEPARATOR, $$1);
/*     */   }
/*     */   
/*     */   public static <T> MutableComponent formatList(Collection<? extends T> $$0, Optional<? extends Component> $$1, Function<T, Component> $$2) {
/*  93 */     return formatList($$0, (Component)DataFixUtils.orElse($$1, DEFAULT_SEPARATOR), $$2);
/*     */   }
/*     */   
/*     */   public static Component formatList(Collection<? extends Component> $$0, Component $$1) {
/*  97 */     return formatList($$0, $$1, Function.identity());
/*     */   }
/*     */   
/*     */   public static <T> MutableComponent formatList(Collection<? extends T> $$0, Component $$1, Function<T, Component> $$2) {
/* 101 */     if ($$0.isEmpty())
/* 102 */       return Component.empty(); 
/* 103 */     if ($$0.size() == 1) {
/* 104 */       return ((Component)$$2.apply($$0.iterator().next())).copy();
/*     */     }
/*     */     
/* 107 */     MutableComponent $$3 = Component.empty();
/* 108 */     boolean $$4 = true;
/* 109 */     for (T $$5 : $$0) {
/* 110 */       if (!$$4) {
/* 111 */         $$3.append($$1);
/*     */       }
/* 113 */       $$3.append($$2.apply($$5));
/* 114 */       $$4 = false;
/*     */     } 
/*     */     
/* 117 */     return $$3;
/*     */   }
/*     */   
/*     */   public static MutableComponent wrapInSquareBrackets(Component $$0) {
/* 121 */     return Component.translatable("chat.square_brackets", new Object[] { $$0 });
/*     */   }
/*     */   
/*     */   public static Component fromMessage(Message $$0) {
/* 125 */     if ($$0 instanceof Component) { Component $$1 = (Component)$$0;
/* 126 */       return $$1; }
/*     */     
/* 128 */     return Component.literal($$0.getString());
/*     */   }
/*     */   
/*     */   public static boolean isTranslationResolvable(@Nullable Component $$0) {
/* 132 */     if ($$0 != null) { ComponentContents componentContents = $$0.getContents(); if (componentContents instanceof TranslatableContents) { TranslatableContents $$1 = (TranslatableContents)componentContents;
/* 133 */         String $$2 = $$1.getKey();
/* 134 */         String $$3 = $$1.getFallback();
/* 135 */         return ($$3 != null || Language.getInstance().has($$2)); }
/*     */        }
/* 137 */      return true;
/*     */   }
/*     */   
/*     */   public static MutableComponent copyOnClickText(String $$0) {
/* 141 */     return wrapInSquareBrackets(Component.literal($$0).withStyle($$1 -> $$1.withColor(ChatFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, $$0)).withHoverEvent(new HoverEvent((HoverEvent.Action)HoverEvent.Action.SHOW_TEXT, (T)Component.translatable("chat.copy.click"))).withInsertion($$0)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ComponentUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */