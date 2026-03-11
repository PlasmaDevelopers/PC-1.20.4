/*     */ package net.minecraft.client.gui.screens;
/*     */ import com.ibm.icu.text.Collator;
/*     */ import java.util.Comparator;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ 
/*     */ public class CreateBuffetWorldScreen extends Screen {
/*  25 */   private static final Component BIOME_SELECT_INFO = (Component)Component.translatable("createWorld.customize.buffet.biome");
/*     */   
/*     */   private final Screen parent;
/*     */   
/*     */   private final Consumer<Holder<Biome>> applySettings;
/*     */   final Registry<Biome> biomes;
/*     */   private BiomeList list;
/*     */   Holder<Biome> biome;
/*     */   private Button doneButton;
/*     */   
/*     */   public CreateBuffetWorldScreen(Screen $$0, WorldCreationContext $$1, Consumer<Holder<Biome>> $$2) {
/*  36 */     super((Component)Component.translatable("createWorld.customize.buffet.title"));
/*  37 */     this.parent = $$0;
/*  38 */     this.applySettings = $$2;
/*     */     
/*  40 */     this.biomes = $$1.worldgenLoadContext().registryOrThrow(Registries.BIOME);
/*     */ 
/*     */     
/*  43 */     Holder<Biome> $$3 = this.biomes.getHolder(Biomes.PLAINS).or(() -> this.biomes.holders().findAny()).orElseThrow();
/*  44 */     this.biome = $$1.selectedDimensions().overworld().getBiomeSource().possibleBiomes().stream().findFirst().orElse($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  49 */     this.minecraft.setScreen(this.parent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  54 */     this.list = addRenderableWidget(new BiomeList());
/*     */     
/*  56 */     this.doneButton = addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $$0 -> {
/*     */             this.applySettings.accept(this.biome);
/*     */             this.minecraft.setScreen(this.parent);
/*  59 */           }).bounds(this.width / 2 - 155, this.height - 28, 150, 20).build());
/*     */     
/*  61 */     addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $$0 -> this.minecraft.setScreen(this.parent)).bounds(this.width / 2 + 5, this.height - 28, 150, 20).build());
/*     */     
/*  63 */     this.list.setSelected(this.list.children().stream().filter($$0 -> Objects.equals($$0.biome, this.biome)).findFirst().orElse(null));
/*     */   }
/*     */   
/*     */   void updateButtonValidity() {
/*  67 */     this.doneButton.active = (this.list.getSelected() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  72 */     super.render($$0, $$1, $$2, $$3);
/*  73 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
/*  74 */     $$0.drawCenteredString(this.font, BIOME_SELECT_INFO, this.width / 2, 28, 10526880);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  79 */     renderDirtBackground($$0);
/*     */   }
/*     */   
/*     */   private class BiomeList extends ObjectSelectionList<BiomeList.Entry> {
/*     */     BiomeList() {
/*  84 */       super(CreateBuffetWorldScreen.this.minecraft, CreateBuffetWorldScreen.this.width, CreateBuffetWorldScreen.this.height - 77, 40, 16);
/*     */       
/*  86 */       Collator $$0 = Collator.getInstance(Locale.getDefault());
/*     */       
/*  88 */       CreateBuffetWorldScreen.this.biomes.holders()
/*  89 */         .map($$0 -> new Entry($$0))
/*  90 */         .sorted(Comparator.comparing($$0 -> $$0.name.getString(), (Comparator<?>)$$0))
/*  91 */         .forEach($$1 -> $$0.addEntry($$1));
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(@Nullable Entry $$0) {
/*  96 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/*     */       
/*  98 */       if ($$0 != null) {
/*  99 */         CreateBuffetWorldScreen.this.biome = (Holder<Biome>)$$0.biome;
/*     */       }
/* 101 */       CreateBuffetWorldScreen.this.updateButtonValidity();
/*     */     }
/*     */     
/*     */     private class Entry extends ObjectSelectionList.Entry<Entry> {
/*     */       final Holder.Reference<Biome> biome;
/*     */       final Component name;
/*     */       
/*     */       public Entry(Holder.Reference<Biome> $$0) {
/* 109 */         this.biome = $$0;
/* 110 */         ResourceLocation $$1 = $$0.key().location();
/*     */         
/* 112 */         String $$2 = $$1.toLanguageKey("biome");
/* 113 */         if (Language.getInstance().has($$2)) {
/* 114 */           this.name = (Component)Component.translatable($$2);
/*     */         } else {
/* 116 */           this.name = (Component)Component.literal($$1.toString());
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public Component getNarration() {
/* 122 */         return (Component)Component.translatable("narrator.select", new Object[] { this.name });
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 127 */         $$0.drawString(CreateBuffetWorldScreen.this.font, this.name, $$3 + 5, $$2 + 2, 16777215);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean mouseClicked(double $$0, double $$1, int $$2)
/*     */       {
/* 134 */         CreateBuffetWorldScreen.BiomeList.this.setSelected(this);
/* 135 */         return true; } } } private class Entry extends ObjectSelectionList.Entry<BiomeList.Entry> { public boolean mouseClicked(double $$0, double $$1, int $$2) { this.this$1.setSelected(this); return true; }
/*     */ 
/*     */     
/*     */     final Holder.Reference<Biome> biome;
/*     */     final Component name;
/*     */     
/*     */     public Entry(Holder.Reference<Biome> $$0) {
/*     */       this.biome = $$0;
/*     */       ResourceLocation $$1 = $$0.key().location();
/*     */       String $$2 = $$1.toLanguageKey("biome");
/*     */       if (Language.getInstance().has($$2)) {
/*     */         this.name = (Component)Component.translatable($$2);
/*     */       } else {
/*     */         this.name = (Component)Component.literal($$1.toString());
/*     */       } 
/*     */     }
/*     */     
/*     */     public Component getNarration() {
/*     */       return (Component)Component.translatable("narrator.select", new Object[] { this.name });
/*     */     }
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       $$0.drawString(CreateBuffetWorldScreen.this.font, this.name, $$3 + 5, $$2 + 2, 16777215);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\CreateBuffetWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */