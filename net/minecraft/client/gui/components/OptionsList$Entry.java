/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.OptionInstance;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Entry
/*     */   extends ContainerObjectSelectionList.Entry<OptionsList.Entry>
/*     */ {
/*     */   final Map<OptionInstance<?>, AbstractWidget> options;
/*     */   final List<AbstractWidget> children;
/*     */   
/*     */   private Entry(Map<OptionInstance<?>, AbstractWidget> $$0) {
/*  74 */     this.options = $$0;
/*  75 */     this.children = (List<AbstractWidget>)ImmutableList.copyOf($$0.values());
/*     */   }
/*     */   
/*     */   public static Entry big(Options $$0, int $$1, OptionInstance<?> $$2) {
/*  79 */     return new Entry((Map<OptionInstance<?>, AbstractWidget>)ImmutableMap.of($$2, $$2.createButton($$0, $$1 / 2 - 155, 0, 310)));
/*     */   }
/*     */   
/*     */   public static Entry small(Options $$0, int $$1, OptionInstance<?> $$2, @Nullable OptionInstance<?> $$3) {
/*  83 */     AbstractWidget $$4 = $$2.createButton($$0, $$1 / 2 - 155, 0, 150);
/*  84 */     if ($$3 == null) {
/*  85 */       return new Entry((Map<OptionInstance<?>, AbstractWidget>)ImmutableMap.of($$2, $$4));
/*     */     }
/*  87 */     return new Entry((Map<OptionInstance<?>, AbstractWidget>)ImmutableMap.of($$2, $$4, $$3, $$3.createButton($$0, $$1 / 2 - 155 + 160, 0, 150)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*  92 */     this.children.forEach($$5 -> {
/*     */           $$5.setY($$0);
/*     */           $$5.render($$1, $$2, $$3, $$4);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends GuiEventListener> children() {
/* 100 */     return (List)this.children;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends NarratableEntry> narratables() {
/* 105 */     return (List)this.children;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\OptionsList$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */