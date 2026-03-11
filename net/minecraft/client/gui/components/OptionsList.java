/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.OptionInstance;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ 
/*     */ public class OptionsList
/*     */   extends ContainerObjectSelectionList<OptionsList.Entry> {
/*     */   public OptionsList(Minecraft $$0, int $$1, int $$2, int $$3, int $$4) {
/*  19 */     super($$0, $$1, $$2, $$3, $$4);
/*  20 */     this.centerListVertically = false;
/*     */   }
/*     */   
/*     */   public int addBig(OptionInstance<?> $$0) {
/*  24 */     return addEntry(Entry.big(this.minecraft.options, this.width, $$0));
/*     */   }
/*     */   
/*     */   public void addSmall(OptionInstance<?> $$0, @Nullable OptionInstance<?> $$1) {
/*  28 */     addEntry(Entry.small(this.minecraft.options, this.width, $$0, $$1));
/*     */   }
/*     */   
/*     */   public void addSmall(OptionInstance<?>[] $$0) {
/*  32 */     for (int $$1 = 0; $$1 < $$0.length; $$1 += 2) {
/*  33 */       addSmall($$0[$$1], ($$1 < $$0.length - 1) ? $$0[$$1 + 1] : null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/*  39 */     return 400;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollbarPosition() {
/*  44 */     return super.getScrollbarPosition() + 32;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AbstractWidget findOption(OptionInstance<?> $$0) {
/*  49 */     for (Entry $$1 : children()) {
/*  50 */       AbstractWidget $$2 = $$1.options.get($$0);
/*  51 */       if ($$2 != null) {
/*  52 */         return $$2;
/*     */       }
/*     */     } 
/*  55 */     return null;
/*     */   }
/*     */   
/*     */   public Optional<AbstractWidget> getMouseOver(double $$0, double $$1) {
/*  59 */     for (Entry $$2 : children()) {
/*  60 */       for (AbstractWidget $$3 : $$2.children) {
/*  61 */         if ($$3.isMouseOver($$0, $$1)) {
/*  62 */           return Optional.of($$3);
/*     */         }
/*     */       } 
/*     */     } 
/*  66 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   protected static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
/*     */     final Map<OptionInstance<?>, AbstractWidget> options;
/*     */     final List<AbstractWidget> children;
/*     */     
/*     */     private Entry(Map<OptionInstance<?>, AbstractWidget> $$0) {
/*  74 */       this.options = $$0;
/*  75 */       this.children = (List<AbstractWidget>)ImmutableList.copyOf($$0.values());
/*     */     }
/*     */     
/*     */     public static Entry big(Options $$0, int $$1, OptionInstance<?> $$2) {
/*  79 */       return new Entry((Map<OptionInstance<?>, AbstractWidget>)ImmutableMap.of($$2, $$2.createButton($$0, $$1 / 2 - 155, 0, 310)));
/*     */     }
/*     */     
/*     */     public static Entry small(Options $$0, int $$1, OptionInstance<?> $$2, @Nullable OptionInstance<?> $$3) {
/*  83 */       AbstractWidget $$4 = $$2.createButton($$0, $$1 / 2 - 155, 0, 150);
/*  84 */       if ($$3 == null) {
/*  85 */         return new Entry((Map<OptionInstance<?>, AbstractWidget>)ImmutableMap.of($$2, $$4));
/*     */       }
/*  87 */       return new Entry((Map<OptionInstance<?>, AbstractWidget>)ImmutableMap.of($$2, $$4, $$3, $$3.createButton($$0, $$1 / 2 - 155 + 160, 0, 150)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*  92 */       this.children.forEach($$5 -> {
/*     */             $$5.setY($$0);
/*     */             $$5.render($$1, $$2, $$3, $$4);
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public List<? extends GuiEventListener> children() {
/* 100 */       return (List)this.children;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<? extends NarratableEntry> narratables() {
/* 105 */       return (List)this.children;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\OptionsList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */