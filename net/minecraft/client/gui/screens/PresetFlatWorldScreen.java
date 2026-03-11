/*     */ package net.minecraft.client.gui.screens;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.navigation.CommonInputs;
/*     */ import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.FlatLevelGeneratorPresetTags;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PresetFlatWorldScreen extends Screen {
/*  45 */   static final ResourceLocation SLOT_SPRITE = new ResourceLocation("container/slot");
/*  46 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int SLOT_BG_SIZE = 18;
/*     */   
/*     */   private static final int SLOT_STAT_HEIGHT = 20;
/*     */   private static final int SLOT_BG_X = 1;
/*     */   private static final int SLOT_BG_Y = 1;
/*     */   private static final int SLOT_FG_X = 2;
/*     */   private static final int SLOT_FG_Y = 2;
/*  55 */   private static final ResourceKey<Biome> DEFAULT_BIOME = Biomes.PLAINS;
/*  56 */   public static final Component UNKNOWN_PRESET = (Component)Component.translatable("flat_world_preset.unknown");
/*     */   private final CreateFlatWorldScreen parent;
/*     */   private Component shareText;
/*     */   private Component listText;
/*     */   private PresetsList list;
/*     */   private Button selectButton;
/*     */   EditBox export;
/*     */   FlatLevelGeneratorSettings settings;
/*     */   
/*     */   public PresetFlatWorldScreen(CreateFlatWorldScreen $$0)
/*     */   {
/*  67 */     super((Component)Component.translatable("createWorld.customize.presets.title"));
/*  68 */     this.parent = $$0; } @Nullable
/*     */   private static FlatLayerInfo getLayerInfoFromString(HolderGetter<Block> $$0, String $$1, int $$2) {
/*     */     int $$8;
/*     */     String $$7;
/*     */     Optional<Holder.Reference<Block>> $$11;
/*  73 */     List<String> $$3 = Splitter.on('*').limit(2).splitToList($$1);
/*     */ 
/*     */ 
/*     */     
/*  77 */     if ($$3.size() == 2) {
/*  78 */       String $$4 = $$3.get(1);
/*     */       try {
/*  80 */         int $$5 = Math.max(Integer.parseInt($$3.get(0)), 0);
/*  81 */       } catch (NumberFormatException $$6) {
/*  82 */         LOGGER.error("Error while parsing flat world string", $$6);
/*  83 */         return null;
/*     */       } 
/*     */     } else {
/*     */       
/*  87 */       $$7 = $$3.get(0);
/*  88 */       $$8 = 1;
/*     */     } 
/*     */     
/*  91 */     int $$9 = Math.min($$2 + $$8, DimensionType.Y_SIZE);
/*  92 */     int $$10 = $$9 - $$2;
/*     */ 
/*     */     
/*     */     try {
/*  96 */       $$11 = $$0.get(ResourceKey.create(Registries.BLOCK, new ResourceLocation($$7)));
/*  97 */     } catch (Exception $$12) {
/*  98 */       LOGGER.error("Error while parsing flat world string", $$12);
/*  99 */       return null;
/*     */     } 
/*     */     
/* 102 */     if ($$11.isEmpty()) {
/* 103 */       LOGGER.error("Error while parsing flat world string => Unknown block, {}", $$7);
/* 104 */       return null;
/*     */     } 
/*     */     
/* 107 */     return new FlatLayerInfo($$10, (Block)((Holder.Reference)$$11.get()).value());
/*     */   }
/*     */   
/*     */   private static List<FlatLayerInfo> getLayersInfoFromString(HolderGetter<Block> $$0, String $$1) {
/* 111 */     List<FlatLayerInfo> $$2 = Lists.newArrayList();
/* 112 */     String[] $$3 = $$1.split(",");
/* 113 */     int $$4 = 0;
/*     */     
/* 115 */     for (String $$5 : $$3) {
/* 116 */       FlatLayerInfo $$6 = getLayerInfoFromString($$0, $$5, $$4);
/* 117 */       if ($$6 == null) {
/* 118 */         return Collections.emptyList();
/*     */       }
/* 120 */       $$2.add($$6);
/* 121 */       $$4 += $$6.getHeight();
/*     */     } 
/*     */     
/* 124 */     return $$2;
/*     */   }
/*     */   public static FlatLevelGeneratorSettings fromString(HolderGetter<Block> $$0, HolderGetter<Biome> $$1, HolderGetter<StructureSet> $$2, HolderGetter<PlacedFeature> $$3, String $$4, FlatLevelGeneratorSettings $$5) {
/*     */     Holder holder;
/* 128 */     Iterator<String> $$6 = Splitter.on(';').split($$4).iterator();
/* 129 */     if (!$$6.hasNext()) {
/* 130 */       return FlatLevelGeneratorSettings.getDefault($$1, $$2, $$3);
/*     */     }
/*     */     
/* 133 */     List<FlatLayerInfo> $$7 = getLayersInfoFromString($$0, $$6.next());
/*     */     
/* 135 */     if ($$7.isEmpty()) {
/* 136 */       return FlatLevelGeneratorSettings.getDefault($$1, $$2, $$3);
/*     */     }
/*     */     
/* 139 */     Holder.Reference<Biome> $$8 = $$1.getOrThrow(DEFAULT_BIOME);
/* 140 */     Holder.Reference<Biome> reference1 = $$8;
/* 141 */     if ($$6.hasNext()) {
/* 142 */       String $$10 = $$6.next();
/*     */ 
/*     */       
/* 145 */       Objects.requireNonNull($$1);
/* 146 */       holder = Optional.<ResourceLocation>ofNullable(ResourceLocation.tryParse($$10)).map($$0 -> ResourceKey.create(Registries.BIOME, $$0)).flatMap($$1::get).orElseGet(() -> {
/*     */             LOGGER.warn("Invalid biome: {}", $$0);
/*     */             
/*     */             return $$1;
/*     */           });
/*     */     } 
/* 152 */     return $$5.withBiomeAndLayers($$7, $$5.structureOverrides(), holder);
/*     */   }
/*     */   
/*     */   static String save(FlatLevelGeneratorSettings $$0) {
/* 156 */     StringBuilder $$1 = new StringBuilder();
/*     */     
/* 158 */     for (int $$2 = 0; $$2 < $$0.getLayersInfo().size(); $$2++) {
/* 159 */       if ($$2 > 0) {
/* 160 */         $$1.append(",");
/*     */       }
/* 162 */       $$1.append($$0.getLayersInfo().get($$2));
/*     */     } 
/*     */     
/* 165 */     $$1.append(";");
/* 166 */     $$1.append($$0.getBiome().unwrapKey().map(ResourceKey::location).orElseThrow(() -> new IllegalStateException("Biome not registered")));
/*     */     
/* 168 */     return $$1.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 173 */     this.shareText = (Component)Component.translatable("createWorld.customize.presets.share");
/* 174 */     this.listText = (Component)Component.translatable("createWorld.customize.presets.list");
/*     */     
/* 176 */     this.export = new EditBox(this.font, 50, 40, this.width - 100, 20, this.shareText);
/* 177 */     this.export.setMaxLength(1230);
/* 178 */     WorldCreationContext $$0 = this.parent.parent.getUiState().getSettings();
/* 179 */     RegistryAccess.Frozen frozen = $$0.worldgenLoadContext();
/* 180 */     FeatureFlagSet $$2 = $$0.dataConfiguration().enabledFeatures();
/*     */     
/* 182 */     HolderLookup.RegistryLookup registryLookup1 = frozen.lookupOrThrow(Registries.BIOME);
/* 183 */     HolderLookup.RegistryLookup registryLookup2 = frozen.lookupOrThrow(Registries.STRUCTURE_SET);
/* 184 */     HolderLookup.RegistryLookup registryLookup3 = frozen.lookupOrThrow(Registries.PLACED_FEATURE);
/* 185 */     HolderLookup holderLookup = frozen.lookupOrThrow(Registries.BLOCK).filterFeatures($$2);
/* 186 */     this.export.setValue(save(this.parent.settings()));
/* 187 */     this.settings = this.parent.settings();
/* 188 */     addWidget(this.export);
/*     */     
/* 190 */     this.list = addRenderableWidget(new PresetsList((RegistryAccess)frozen, $$2));
/*     */     
/* 192 */     this.selectButton = addRenderableWidget(Button.builder((Component)Component.translatable("createWorld.customize.presets.select"), $$4 -> {
/*     */             FlatLevelGeneratorSettings $$5 = fromString($$0, $$1, $$2, $$3, this.export.getValue(), this.settings);
/*     */             this.parent.setConfig($$5);
/*     */             this.minecraft.setScreen(this.parent);
/* 196 */           }).bounds(this.width / 2 - 155, this.height - 28, 150, 20).build());
/* 197 */     addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $$0 -> this.minecraft.setScreen(this.parent)).bounds(this.width / 2 + 5, this.height - 28, 150, 20).build());
/*     */     
/* 199 */     updateButtonValidity((this.list.getSelected() != null));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/* 205 */     return this.list.mouseScrolled($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(Minecraft $$0, int $$1, int $$2) {
/* 210 */     String $$3 = this.export.getValue();
/* 211 */     init($$0, $$1, $$2);
/* 212 */     this.export.setValue($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 217 */     this.minecraft.setScreen(this.parent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 222 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 224 */     $$0.pose().pushPose();
/* 225 */     $$0.pose().translate(0.0F, 0.0F, 400.0F);
/*     */     
/* 227 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
/* 228 */     $$0.drawString(this.font, this.shareText, 51, 30, 10526880);
/* 229 */     $$0.drawString(this.font, this.listText, 51, 70, 10526880);
/*     */     
/* 231 */     $$0.pose().popPose();
/*     */     
/* 233 */     this.export.render($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void updateButtonValidity(boolean $$0) {
/* 237 */     this.selectButton.active = ($$0 || this.export.getValue().length() > 1);
/*     */   }
/*     */   
/*     */   private class PresetsList extends ObjectSelectionList<PresetsList.Entry> {
/*     */     public PresetsList(RegistryAccess $$0, FeatureFlagSet $$1) {
/* 242 */       super(PresetFlatWorldScreen.this.minecraft, PresetFlatWorldScreen.this.width, PresetFlatWorldScreen.this.height - 117, 80, 24);
/* 243 */       for (Holder<FlatLevelGeneratorPreset> $$2 : (Iterable<Holder<FlatLevelGeneratorPreset>>)$$0.registryOrThrow(Registries.FLAT_LEVEL_GENERATOR_PRESET).getTagOrEmpty(FlatLevelGeneratorPresetTags.VISIBLE)) {
/* 244 */         Set<Block> $$3 = (Set<Block>)((FlatLevelGeneratorPreset)$$2.value()).settings().getLayersInfo().stream().map($$0 -> $$0.getBlockState().getBlock()).filter($$1 -> !$$1.isEnabled($$0)).collect(Collectors.toSet());
/* 245 */         if (!$$3.isEmpty()) {
/* 246 */           PresetFlatWorldScreen.LOGGER.info("Discarding flat world preset {} since it contains experimental blocks {}", $$2.unwrapKey().map($$0 -> $$0.location().toString()).orElse("<unknown>"), $$3); continue;
/*     */         } 
/* 248 */         addEntry((AbstractSelectionList.Entry)new Entry($$2));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setSelected(@Nullable Entry $$0) {
/* 255 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/* 256 */       PresetFlatWorldScreen.this.updateButtonValidity(($$0 != null));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 261 */       if (super.keyPressed($$0, $$1, $$2)) {
/* 262 */         return true;
/*     */       }
/* 264 */       if (CommonInputs.selected($$0) && 
/* 265 */         getSelected() != null) {
/* 266 */         ((Entry)getSelected()).select();
/*     */       }
/*     */       
/* 269 */       return false;
/*     */     }
/*     */     
/*     */     public class Entry extends ObjectSelectionList.Entry<Entry> {
/* 273 */       private static final ResourceLocation STATS_ICON_LOCATION = new ResourceLocation("textures/gui/container/stats_icons.png");
/*     */       
/*     */       private final FlatLevelGeneratorPreset preset;
/*     */       private final Component name;
/*     */       
/*     */       public Entry(Holder<FlatLevelGeneratorPreset> $$1) {
/* 279 */         this.preset = (FlatLevelGeneratorPreset)$$1.value();
/* 280 */         this
/*     */           
/* 282 */           .name = $$1.unwrapKey().map($$0 -> Component.translatable($$0.location().toLanguageKey("flat_world_preset"))).orElse(PresetFlatWorldScreen.UNKNOWN_PRESET);
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 287 */         blitSlot($$0, $$3, $$2, (Item)this.preset.displayItem().value());
/* 288 */         $$0.drawString(PresetFlatWorldScreen.this.font, this.name, $$3 + 18 + 5, $$2 + 6, 16777215, false);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 293 */         select();
/* 294 */         return true;
/*     */       }
/*     */       
/*     */       void select() {
/* 298 */         PresetFlatWorldScreen.PresetsList.this.setSelected(this);
/* 299 */         PresetFlatWorldScreen.this.settings = this.preset.settings();
/* 300 */         PresetFlatWorldScreen.this.export.setValue(PresetFlatWorldScreen.save(PresetFlatWorldScreen.this.settings));
/* 301 */         PresetFlatWorldScreen.this.export.moveCursorToStart(false);
/*     */       }
/*     */       
/*     */       private void blitSlot(GuiGraphics $$0, int $$1, int $$2, Item $$3) {
/* 305 */         blitSlotBg($$0, $$1 + 1, $$2 + 1);
/*     */         
/* 307 */         $$0.renderFakeItem(new ItemStack((ItemLike)$$3), $$1 + 2, $$2 + 2);
/*     */       }
/*     */       
/*     */       private void blitSlotBg(GuiGraphics $$0, int $$1, int $$2) {
/* 311 */         $$0.blitSprite(PresetFlatWorldScreen.SLOT_SPRITE, $$1, $$2, 0, 18, 18);
/*     */       }
/*     */       
/*     */       public Component getNarration()
/*     */       {
/* 316 */         return (Component)Component.translatable("narrator.select", new Object[] { this.name }); } } } public class Entry extends ObjectSelectionList.Entry<PresetsList.Entry> { public Component getNarration() { return (Component)Component.translatable("narrator.select", new Object[] { this.name }); }
/*     */ 
/*     */     
/*     */     private static final ResourceLocation STATS_ICON_LOCATION = new ResourceLocation("textures/gui/container/stats_icons.png");
/*     */     private final FlatLevelGeneratorPreset preset;
/*     */     private final Component name;
/*     */     
/*     */     public Entry(Holder<FlatLevelGeneratorPreset> $$1) {
/*     */       this.preset = (FlatLevelGeneratorPreset)$$1.value();
/*     */       this.name = $$1.unwrapKey().map($$0 -> Component.translatable($$0.location().toLanguageKey("flat_world_preset"))).orElse(PresetFlatWorldScreen.UNKNOWN_PRESET);
/*     */     }
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       blitSlot($$0, $$3, $$2, (Item)this.preset.displayItem().value());
/*     */       $$0.drawString(PresetFlatWorldScreen.this.font, this.name, $$3 + 18 + 5, $$2 + 6, 16777215, false);
/*     */     }
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*     */       select();
/*     */       return true;
/*     */     }
/*     */     
/*     */     void select() {
/*     */       PresetFlatWorldScreen.PresetsList.this.setSelected(this);
/*     */       PresetFlatWorldScreen.this.settings = this.preset.settings();
/*     */       PresetFlatWorldScreen.this.export.setValue(PresetFlatWorldScreen.save(PresetFlatWorldScreen.this.settings));
/*     */       PresetFlatWorldScreen.this.export.moveCursorToStart(false);
/*     */     }
/*     */     
/*     */     private void blitSlot(GuiGraphics $$0, int $$1, int $$2, Item $$3) {
/*     */       blitSlotBg($$0, $$1 + 1, $$2 + 1);
/*     */       $$0.renderFakeItem(new ItemStack((ItemLike)$$3), $$1 + 2, $$2 + 2);
/*     */     }
/*     */     
/*     */     private void blitSlotBg(GuiGraphics $$0, int $$1, int $$2) {
/*     */       $$0.blitSprite(PresetFlatWorldScreen.SLOT_SPRITE, $$1, $$2, 0, 18, 18);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\PresetFlatWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */