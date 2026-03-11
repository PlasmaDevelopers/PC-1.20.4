/*     */ package net.minecraft.client.gui.screens;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
/*     */ 
/*     */ public class CreateFlatWorldScreen extends Screen {
/*  23 */   static final ResourceLocation SLOT_SPRITE = new ResourceLocation("container/slot");
/*     */   
/*     */   private static final int SLOT_BG_SIZE = 18;
/*     */   private static final int SLOT_STAT_HEIGHT = 20;
/*     */   private static final int SLOT_BG_X = 1;
/*     */   private static final int SLOT_BG_Y = 1;
/*     */   private static final int SLOT_FG_X = 2;
/*     */   private static final int SLOT_FG_Y = 2;
/*     */   protected final CreateWorldScreen parent;
/*     */   private final Consumer<FlatLevelGeneratorSettings> applySettings;
/*     */   FlatLevelGeneratorSettings generator;
/*     */   private Component columnType;
/*     */   private Component columnHeight;
/*     */   private DetailsList list;
/*     */   private Button deleteLayerButton;
/*     */   
/*     */   public CreateFlatWorldScreen(CreateWorldScreen $$0, Consumer<FlatLevelGeneratorSettings> $$1, FlatLevelGeneratorSettings $$2) {
/*  40 */     super((Component)Component.translatable("createWorld.customize.flat.title"));
/*  41 */     this.parent = $$0;
/*  42 */     this.applySettings = $$1;
/*  43 */     this.generator = $$2;
/*     */   }
/*     */   
/*     */   public FlatLevelGeneratorSettings settings() {
/*  47 */     return this.generator;
/*     */   }
/*     */   
/*     */   public void setConfig(FlatLevelGeneratorSettings $$0) {
/*  51 */     this.generator = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  56 */     this.columnType = (Component)Component.translatable("createWorld.customize.flat.tile");
/*  57 */     this.columnHeight = (Component)Component.translatable("createWorld.customize.flat.height");
/*     */     
/*  59 */     this.list = addRenderableWidget(new DetailsList());
/*     */     
/*  61 */     this.deleteLayerButton = addRenderableWidget(Button.builder((Component)Component.translatable("createWorld.customize.flat.removeLayer"), $$0 -> {
/*     */             if (!hasValidSelection()) {
/*     */               return;
/*     */             }
/*     */             
/*     */             List<FlatLayerInfo> $$1 = this.generator.getLayersInfo();
/*     */             int $$2 = this.list.children().indexOf(this.list.getSelected());
/*     */             int $$3 = $$1.size() - $$2 - 1;
/*     */             $$1.remove($$3);
/*     */             this.list.setSelected($$1.isEmpty() ? null : this.list.children().get(Math.min($$2, $$1.size() - 1)));
/*     */             this.generator.updateLayers();
/*     */             this.list.resetRows();
/*     */             updateButtonValidity();
/*  74 */           }).bounds(this.width / 2 - 155, this.height - 52, 150, 20).build());
/*     */     
/*  76 */     addRenderableWidget(Button.builder((Component)Component.translatable("createWorld.customize.presets"), $$0 -> {
/*     */             this.minecraft.setScreen(new PresetFlatWorldScreen(this));
/*     */             this.generator.updateLayers();
/*     */             updateButtonValidity();
/*  80 */           }).bounds(this.width / 2 + 5, this.height - 52, 150, 20).build());
/*     */     
/*  82 */     addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $$0 -> {
/*     */             this.applySettings.accept(this.generator);
/*     */             this.minecraft.setScreen((Screen)this.parent);
/*     */             this.generator.updateLayers();
/*  86 */           }).bounds(this.width / 2 - 155, this.height - 28, 150, 20).build());
/*     */     
/*  88 */     addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $$0 -> {
/*     */             this.minecraft.setScreen((Screen)this.parent);
/*     */             this.generator.updateLayers();
/*  91 */           }).bounds(this.width / 2 + 5, this.height - 28, 150, 20).build());
/*     */     
/*  93 */     this.generator.updateLayers();
/*  94 */     updateButtonValidity();
/*     */   }
/*     */   
/*     */   void updateButtonValidity() {
/*  98 */     this.deleteLayerButton.active = hasValidSelection();
/*     */   }
/*     */   
/*     */   private boolean hasValidSelection() {
/* 102 */     return (this.list.getSelected() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 107 */     this.minecraft.setScreen((Screen)this.parent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 112 */     super.render($$0, $$1, $$2, $$3);
/* 113 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
/*     */     
/* 115 */     int $$4 = this.width / 2 - 92 - 16;
/* 116 */     $$0.drawString(this.font, this.columnType, $$4, 32, 16777215);
/* 117 */     $$0.drawString(this.font, this.columnHeight, $$4 + 2 + 213 - this.font.width((FormattedText)this.columnHeight), 32, 16777215);
/*     */   }
/*     */   
/*     */   private class DetailsList extends ObjectSelectionList<DetailsList.Entry> {
/*     */     public DetailsList() {
/* 122 */       super(CreateFlatWorldScreen.this.minecraft, CreateFlatWorldScreen.this.width, CreateFlatWorldScreen.this.height - 103, 43, 24);
/*     */       
/* 124 */       for (int $$0 = 0; $$0 < CreateFlatWorldScreen.this.generator.getLayersInfo().size(); $$0++) {
/* 125 */         addEntry((AbstractSelectionList.Entry)new Entry());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(@Nullable Entry $$0) {
/* 131 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/* 132 */       CreateFlatWorldScreen.this.updateButtonValidity();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollbarPosition() {
/* 137 */       return this.width - 70;
/*     */     }
/*     */     
/*     */     public void resetRows() {
/* 141 */       int $$0 = children().indexOf(getSelected());
/* 142 */       clearEntries();
/* 143 */       for (int $$1 = 0; $$1 < CreateFlatWorldScreen.this.generator.getLayersInfo().size(); $$1++) {
/* 144 */         addEntry((AbstractSelectionList.Entry)new Entry());
/*     */       }
/*     */       
/* 147 */       List<Entry> $$2 = children();
/* 148 */       if ($$0 >= 0 && $$0 < $$2.size())
/* 149 */         setSelected($$2.get($$0)); 
/*     */     }
/*     */     
/*     */     private class Entry
/*     */       extends ObjectSelectionList.Entry<Entry> {
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */         MutableComponent mutableComponent;
/* 156 */         FlatLayerInfo $$10 = CreateFlatWorldScreen.this.generator.getLayersInfo().get(CreateFlatWorldScreen.this.generator.getLayersInfo().size() - $$1 - 1);
/* 157 */         BlockState $$11 = $$10.getBlockState();
/*     */         
/* 159 */         ItemStack $$12 = getDisplayItem($$11);
/* 160 */         blitSlot($$0, $$3, $$2, $$12);
/* 161 */         $$0.drawString(CreateFlatWorldScreen.this.font, $$12.getHoverName(), $$3 + 18 + 5, $$2 + 3, 16777215, false);
/*     */ 
/*     */ 
/*     */         
/* 165 */         if ($$1 == 0) {
/* 166 */           mutableComponent = Component.translatable("createWorld.customize.flat.layer.top", new Object[] { Integer.valueOf($$10.getHeight()) });
/* 167 */         } else if ($$1 == CreateFlatWorldScreen.this.generator.getLayersInfo().size() - 1) {
/* 168 */           mutableComponent = Component.translatable("createWorld.customize.flat.layer.bottom", new Object[] { Integer.valueOf($$10.getHeight()) });
/*     */         } else {
/* 170 */           mutableComponent = Component.translatable("createWorld.customize.flat.layer", new Object[] { Integer.valueOf($$10.getHeight()) });
/*     */         } 
/*     */         
/* 173 */         $$0.drawString(CreateFlatWorldScreen.this.font, (Component)mutableComponent, $$3 + 2 + 213 - CreateFlatWorldScreen.this.font.width((FormattedText)mutableComponent), $$2 + 3, 16777215, false);
/*     */       }
/*     */       
/*     */       private ItemStack getDisplayItem(BlockState $$0) {
/* 177 */         Item $$1 = $$0.getBlock().asItem();
/* 178 */         if ($$1 == Items.AIR) {
/* 179 */           if ($$0.is(Blocks.WATER)) {
/* 180 */             $$1 = Items.WATER_BUCKET;
/* 181 */           } else if ($$0.is(Blocks.LAVA)) {
/* 182 */             $$1 = Items.LAVA_BUCKET;
/*     */           } 
/*     */         }
/* 185 */         return new ItemStack((ItemLike)$$1);
/*     */       }
/*     */ 
/*     */       
/*     */       public Component getNarration() {
/* 190 */         FlatLayerInfo $$0 = CreateFlatWorldScreen.this.generator.getLayersInfo().get(CreateFlatWorldScreen.this.generator.getLayersInfo().size() - CreateFlatWorldScreen.DetailsList.this.children().indexOf(this) - 1);
/* 191 */         ItemStack $$1 = getDisplayItem($$0.getBlockState());
/* 192 */         if (!$$1.isEmpty()) {
/* 193 */           return (Component)Component.translatable("narrator.select", new Object[] { $$1.getHoverName() });
/*     */         }
/* 195 */         return CommonComponents.EMPTY;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 201 */         CreateFlatWorldScreen.DetailsList.this.setSelected(this);
/* 202 */         return true;
/*     */       }
/*     */       
/*     */       private void blitSlot(GuiGraphics $$0, int $$1, int $$2, ItemStack $$3) {
/* 206 */         blitSlotBg($$0, $$1 + 1, $$2 + 1);
/*     */         
/* 208 */         if (!$$3.isEmpty()) {
/* 209 */           $$0.renderFakeItem($$3, $$1 + 2, $$2 + 2);
/*     */         }
/*     */       }
/*     */       
/*     */       private void blitSlotBg(GuiGraphics $$0, int $$1, int $$2) {
/* 214 */         $$0.blitSprite(CreateFlatWorldScreen.SLOT_SPRITE, $$1, $$2, 0, 18, 18); } } } private class Entry extends ObjectSelectionList.Entry<DetailsList.Entry> { private void blitSlotBg(GuiGraphics $$0, int $$1, int $$2) { $$0.blitSprite(CreateFlatWorldScreen.SLOT_SPRITE, $$1, $$2, 0, 18, 18); }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       MutableComponent mutableComponent;
/*     */       FlatLayerInfo $$10 = CreateFlatWorldScreen.this.generator.getLayersInfo().get(CreateFlatWorldScreen.this.generator.getLayersInfo().size() - $$1 - 1);
/*     */       BlockState $$11 = $$10.getBlockState();
/*     */       ItemStack $$12 = getDisplayItem($$11);
/*     */       blitSlot($$0, $$3, $$2, $$12);
/*     */       $$0.drawString(CreateFlatWorldScreen.this.font, $$12.getHoverName(), $$3 + 18 + 5, $$2 + 3, 16777215, false);
/*     */       if ($$1 == 0) {
/*     */         mutableComponent = Component.translatable("createWorld.customize.flat.layer.top", new Object[] { Integer.valueOf($$10.getHeight()) });
/*     */       } else if ($$1 == CreateFlatWorldScreen.this.generator.getLayersInfo().size() - 1) {
/*     */         mutableComponent = Component.translatable("createWorld.customize.flat.layer.bottom", new Object[] { Integer.valueOf($$10.getHeight()) });
/*     */       } else {
/*     */         mutableComponent = Component.translatable("createWorld.customize.flat.layer", new Object[] { Integer.valueOf($$10.getHeight()) });
/*     */       } 
/*     */       $$0.drawString(CreateFlatWorldScreen.this.font, (Component)mutableComponent, $$3 + 2 + 213 - CreateFlatWorldScreen.this.font.width((FormattedText)mutableComponent), $$2 + 3, 16777215, false);
/*     */     }
/*     */     
/*     */     private ItemStack getDisplayItem(BlockState $$0) {
/*     */       Item $$1 = $$0.getBlock().asItem();
/*     */       if ($$1 == Items.AIR)
/*     */         if ($$0.is(Blocks.WATER)) {
/*     */           $$1 = Items.WATER_BUCKET;
/*     */         } else if ($$0.is(Blocks.LAVA)) {
/*     */           $$1 = Items.LAVA_BUCKET;
/*     */         }  
/*     */       return new ItemStack((ItemLike)$$1);
/*     */     }
/*     */     
/*     */     public Component getNarration() {
/*     */       FlatLayerInfo $$0 = CreateFlatWorldScreen.this.generator.getLayersInfo().get(CreateFlatWorldScreen.this.generator.getLayersInfo().size() - this.this$1.children().indexOf(this) - 1);
/*     */       ItemStack $$1 = getDisplayItem($$0.getBlockState());
/*     */       if (!$$1.isEmpty())
/*     */         return (Component)Component.translatable("narrator.select", new Object[] { $$1.getHoverName() }); 
/*     */       return CommonComponents.EMPTY;
/*     */     }
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*     */       this.this$1.setSelected(this);
/*     */       return true;
/*     */     }
/*     */     
/*     */     private void blitSlot(GuiGraphics $$0, int $$1, int $$2, ItemStack $$3) {
/*     */       blitSlotBg($$0, $$1 + 1, $$2 + 1);
/*     */       if (!$$3.isEmpty())
/*     */         $$0.renderFakeItem($$3, $$1 + 2, $$2 + 2); 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\CreateFlatWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */