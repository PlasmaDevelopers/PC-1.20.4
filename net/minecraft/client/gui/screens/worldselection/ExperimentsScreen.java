/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.packs.repository.PackSource;
/*     */ 
/*     */ public class ExperimentsScreen extends Screen {
/*  28 */   private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this); private static final int MAIN_CONTENT_WIDTH = 310;
/*     */   private final Screen parent;
/*     */   private final PackRepository packRepository;
/*     */   private final Consumer<PackRepository> output;
/*  32 */   private final Object2BooleanMap<Pack> packs = (Object2BooleanMap<Pack>)new Object2BooleanLinkedOpenHashMap();
/*     */   
/*     */   public ExperimentsScreen(Screen $$0, PackRepository $$1, Consumer<PackRepository> $$2) {
/*  35 */     super((Component)Component.translatable("experiments_screen.title"));
/*  36 */     this.parent = $$0;
/*  37 */     this.packRepository = $$1;
/*  38 */     this.output = $$2;
/*  39 */     for (Pack $$3 : $$1.getAvailablePacks()) {
/*  40 */       if ($$3.getPackSource() == PackSource.FEATURE) {
/*  41 */         this.packs.put($$3, $$1.getSelectedPacks().contains($$3));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  48 */     this.layout.addToHeader((LayoutElement)new StringWidget((Component)Component.translatable("selectWorld.experiments"), this.font));
/*     */     
/*  50 */     LinearLayout $$0 = (LinearLayout)this.layout.addToContents((LayoutElement)LinearLayout.vertical());
/*  51 */     $$0.addChild((LayoutElement)(new MultiLineTextWidget((Component)Component.translatable("selectWorld.experiments.info").withStyle(ChatFormatting.RED), this.font)).setMaxWidth(310), $$0 -> $$0.paddingBottom(15));
/*     */     
/*  53 */     SwitchGrid.Builder $$1 = SwitchGrid.builder(310).withInfoUnderneath(2, true).withRowSpacing(4);
/*  54 */     this.packs.forEach(($$1, $$2) -> $$0.addSwitch(getHumanReadableTitle($$1), (), ()).withInfo($$1.getDescription()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     Objects.requireNonNull($$0); $$1.build($$0::addChild);
/*     */     
/*  61 */     GridLayout.RowHelper $$2 = ((GridLayout)this.layout.addToFooter((LayoutElement)(new GridLayout()).columnSpacing(10))).createRowHelper(2);
/*     */     
/*  63 */     $$2.addChild((LayoutElement)Button.builder(CommonComponents.GUI_DONE, $$0 -> onDone()).build());
/*  64 */     $$2.addChild((LayoutElement)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> onClose()).build());
/*     */     
/*  66 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*  67 */     repositionElements();
/*     */   }
/*     */   
/*     */   private static Component getHumanReadableTitle(Pack $$0) {
/*  71 */     String $$1 = "dataPack." + $$0.getId() + ".name";
/*  72 */     return I18n.exists($$1) ? (Component)Component.translatable($$1) : $$0.getTitle();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  77 */     this.minecraft.setScreen(this.parent);
/*     */   }
/*     */   
/*     */   private void onDone() {
/*  81 */     List<Pack> $$0 = new ArrayList<>(this.packRepository.getSelectedPacks());
/*  82 */     List<Pack> $$1 = new ArrayList<>();
/*  83 */     this.packs.forEach(($$2, $$3) -> {
/*     */           $$0.remove($$2);
/*     */           if ($$3.booleanValue()) {
/*     */             $$1.add($$2);
/*     */           }
/*     */         });
/*  89 */     $$0.addAll(Lists.reverse($$1));
/*  90 */     this.packRepository.setSelected($$0.stream().map(Pack::getId).toList());
/*  91 */     this.output.accept(this.packRepository);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/*  96 */     this.layout.arrangeElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 101 */     super.renderBackground($$0, $$1, $$2, $$3);
/*     */     
/* 103 */     $$0.setColor(0.125F, 0.125F, 0.125F, 1.0F);
/* 104 */     int $$4 = 32;
/* 105 */     $$0.blit(BACKGROUND_LOCATION, 0, this.layout.getHeaderHeight(), 0.0F, 0.0F, this.width, this.height - this.layout.getHeaderHeight() - this.layout.getFooterHeight(), 32, 32);
/* 106 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\ExperimentsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */