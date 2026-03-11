/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ContainerObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ 
/*     */ public class EditGameRulesScreen extends Screen {
/*     */   private final Consumer<Optional<GameRules>> exitCallback;
/*     */   private RuleList rules;
/*  37 */   private final Set<RuleEntry> invalidEntries = Sets.newHashSet();
/*     */   private Button doneButton;
/*     */   @Nullable
/*     */   private List<FormattedCharSequence> tooltip;
/*     */   private final GameRules gameRules;
/*     */   
/*     */   public EditGameRulesScreen(GameRules $$0, Consumer<Optional<GameRules>> $$1) {
/*  44 */     super((Component)Component.translatable("editGamerule.title"));
/*  45 */     this.gameRules = $$0;
/*  46 */     this.exitCallback = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  51 */     this.rules = (RuleList)addRenderableWidget((GuiEventListener)new RuleList(this.gameRules));
/*     */ 
/*     */ 
/*     */     
/*  55 */     GridLayout.RowHelper $$0 = (new GridLayout()).columnSpacing(10).createRowHelper(2);
/*     */     
/*  57 */     this.doneButton = (Button)$$0.addChild(
/*  58 */         (LayoutElement)Button.builder(CommonComponents.GUI_DONE, $$0 -> this.exitCallback.accept(Optional.of(this.gameRules)))
/*  59 */         .build());
/*     */     
/*  61 */     $$0.addChild(
/*  62 */         (LayoutElement)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> this.exitCallback.accept(Optional.empty()))
/*  63 */         .build());
/*     */     
/*  65 */     $$0.getGrid().visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*  66 */     $$0.getGrid().setPosition(this.width / 2 - 155, this.height - 28);
/*  67 */     $$0.getGrid().arrangeElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  72 */     this.exitCallback.accept(Optional.empty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  77 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/*  79 */     this.tooltip = null;
/*  80 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
/*     */   }
/*     */   
/*     */   private void updateDoneButton() {
/*  84 */     this.doneButton.active = this.invalidEntries.isEmpty();
/*     */   }
/*     */   
/*     */   void markInvalid(RuleEntry $$0) {
/*  88 */     this.invalidEntries.add($$0);
/*  89 */     updateDoneButton();
/*     */   }
/*     */   
/*     */   void clearInvalid(RuleEntry $$0) {
/*  93 */     this.invalidEntries.remove($$0);
/*  94 */     updateDoneButton();
/*     */   }
/*     */   
/*     */   public static abstract class RuleEntry extends ContainerObjectSelectionList.Entry<RuleEntry> {
/*     */     @Nullable
/*     */     final List<FormattedCharSequence> tooltip;
/*     */     
/*     */     public RuleEntry(@Nullable List<FormattedCharSequence> $$0) {
/* 102 */       this.tooltip = $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   public class CategoryRuleEntry extends RuleEntry {
/*     */     final Component label;
/*     */     
/*     */     public CategoryRuleEntry(Component $$1) {
/* 110 */       super(null);
/* 111 */       this.label = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 116 */       $$0.drawCenteredString(EditGameRulesScreen.this.minecraft.font, this.label, $$3 + $$4 / 2, $$2 + 5, 16777215);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<? extends GuiEventListener> children() {
/* 121 */       return (List<? extends GuiEventListener>)ImmutableList.of();
/*     */     }
/*     */     
/*     */     public List<? extends NarratableEntry> narratables()
/*     */     {
/* 126 */       return (List<? extends NarratableEntry>)ImmutableList.of(new NarratableEntry()
/*     */           {
/*     */             public NarratableEntry.NarrationPriority narrationPriority() {
/* 129 */               return NarratableEntry.NarrationPriority.HOVERED;
/*     */             }
/*     */             
/*     */             public void updateNarration(NarrationElementOutput $$0)
/*     */             {
/* 134 */               $$0.add(NarratedElementType.TITLE, EditGameRulesScreen.CategoryRuleEntry.this.label); } }); } } class null implements NarratableEntry { public void updateNarration(NarrationElementOutput $$0) { $$0.add(NarratedElementType.TITLE, EditGameRulesScreen.CategoryRuleEntry.this.label); }
/*     */ 
/*     */ 
/*     */     
/*     */     public NarratableEntry.NarrationPriority narrationPriority() {
/*     */       return NarratableEntry.NarrationPriority.HOVERED;
/*     */     } }
/*     */ 
/*     */   
/*     */   public abstract class GameRuleEntry
/*     */     extends RuleEntry
/*     */   {
/*     */     private final List<FormattedCharSequence> label;
/* 147 */     protected final List<AbstractWidget> children = Lists.newArrayList();
/*     */     
/*     */     public GameRuleEntry(List<FormattedCharSequence> $$1, Component $$2) {
/* 150 */       super($$1);
/* 151 */       this.label = $$0.minecraft.font.split((FormattedText)$$2, 175);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<? extends GuiEventListener> children() {
/* 156 */       return (List)this.children;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<? extends NarratableEntry> narratables() {
/* 161 */       return (List)this.children;
/*     */     }
/*     */     
/*     */     protected void renderLabel(GuiGraphics $$0, int $$1, int $$2) {
/* 165 */       if (this.label.size() == 1) {
/* 166 */         $$0.drawString(EditGameRulesScreen.this.minecraft.font, this.label.get(0), $$2, $$1 + 5, 16777215, false);
/* 167 */       } else if (this.label.size() >= 2) {
/* 168 */         $$0.drawString(EditGameRulesScreen.this.minecraft.font, this.label.get(0), $$2, $$1, 16777215, false);
/* 169 */         $$0.drawString(EditGameRulesScreen.this.minecraft.font, this.label.get(1), $$2, $$1 + 10, 16777215, false);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public class BooleanRuleEntry extends GameRuleEntry {
/*     */     private final CycleButton<Boolean> checkbox;
/*     */     
/*     */     public BooleanRuleEntry(Component $$1, List<FormattedCharSequence> $$2, String $$3, GameRules.BooleanValue $$4) {
/* 178 */       super($$2, $$1);
/* 179 */       this
/*     */ 
/*     */         
/* 182 */         .checkbox = CycleButton.onOffBuilder($$4.get()).displayOnlyValue().withCustomNarration($$1 -> $$1.createDefaultNarrationMessage().append("\n").append($$0)).create(10, 5, 44, 20, $$1, ($$1, $$2) -> $$0.set($$2.booleanValue(), null));
/* 183 */       this.children.add(this.checkbox);
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 188 */       renderLabel($$0, $$2, $$3);
/* 189 */       this.checkbox.setX($$3 + $$4 - 45);
/* 190 */       this.checkbox.setY($$2);
/* 191 */       this.checkbox.render($$0, $$6, $$7, $$9);
/*     */     }
/*     */   }
/*     */   
/*     */   public class IntegerRuleEntry extends GameRuleEntry {
/*     */     private final EditBox input;
/*     */     
/*     */     public IntegerRuleEntry(Component $$1, List<FormattedCharSequence> $$2, String $$3, GameRules.IntegerValue $$4) {
/* 199 */       super($$2, $$1);
/*     */       
/* 201 */       this.input = new EditBox($$0.minecraft.font, 10, 5, 44, 20, (Component)$$1.copy().append("\n").append($$3).append("\n"));
/* 202 */       this.input.setValue(Integer.toString($$4.get()));
/* 203 */       this.input.setResponder($$1 -> {
/*     */             if ($$0.tryDeserialize($$1)) {
/*     */               this.input.setTextColor(14737632);
/*     */               EditGameRulesScreen.this.clearInvalid(this);
/*     */             } else {
/*     */               this.input.setTextColor(16711680);
/*     */               EditGameRulesScreen.this.markInvalid(this);
/*     */             } 
/*     */           });
/* 212 */       this.children.add(this.input);
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 217 */       renderLabel($$0, $$2, $$3);
/* 218 */       this.input.setX($$3 + $$4 - 45);
/* 219 */       this.input.setY($$2);
/* 220 */       this.input.render($$0, $$6, $$7, $$9);
/*     */     }
/*     */   }
/*     */   
/*     */   public class RuleList extends ContainerObjectSelectionList<RuleEntry> {
/*     */     public RuleList(final GameRules gameRules) {
/* 226 */       super($$0.minecraft, $$0.width, $$0.height - 75, 43, 24);
/*     */       
/* 228 */       final Map<GameRules.Category, Map<GameRules.Key<?>, EditGameRulesScreen.RuleEntry>> entries = Maps.newHashMap();
/*     */       
/* 230 */       GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor()
/*     */           {
/*     */             public void visitBoolean(GameRules.Key<GameRules.BooleanValue> $$0, GameRules.Type<GameRules.BooleanValue> $$1) {
/* 233 */               addEntry($$0, ($$0, $$1, $$2, $$3) -> new EditGameRulesScreen.BooleanRuleEntry($$0, $$1, $$2, $$3));
/*     */             }
/*     */ 
/*     */             
/*     */             public void visitInteger(GameRules.Key<GameRules.IntegerValue> $$0, GameRules.Type<GameRules.IntegerValue> $$1) {
/* 238 */               addEntry($$0, ($$0, $$1, $$2, $$3) -> new EditGameRulesScreen.IntegerRuleEntry($$0, $$1, $$2, $$3));
/*     */             } private <T extends GameRules.Value<T>> void addEntry(GameRules.Key<T> $$0, EditGameRulesScreen.EntryFactory<T> $$1) {
/*     */               ImmutableList immutableList;
/*     */               String $$13;
/* 242 */               MutableComponent mutableComponent1 = Component.translatable($$0.getDescriptionId());
/* 243 */               MutableComponent mutableComponent2 = Component.literal($$0.getId()).withStyle(ChatFormatting.YELLOW);
/*     */               
/* 245 */               GameRules.Value value = gameRules.getRule($$0);
/* 246 */               String $$5 = value.serialize();
/* 247 */               MutableComponent mutableComponent3 = Component.translatable("editGamerule.default", new Object[] { Component.literal($$5) }).withStyle(ChatFormatting.GRAY);
/* 248 */               String $$7 = $$0.getDescriptionId() + ".description";
/*     */ 
/*     */ 
/*     */               
/* 252 */               if (I18n.exists($$7)) {
/* 253 */                 ImmutableList.Builder<FormattedCharSequence> $$8 = ImmutableList.builder().add(mutableComponent2.getVisualOrderText());
/* 254 */                 MutableComponent mutableComponent = Component.translatable($$7);
/* 255 */                 Objects.requireNonNull($$8); EditGameRulesScreen.this.font.split((FormattedText)mutableComponent, 150).forEach($$8::add);
/* 256 */                 immutableList = $$8.add(mutableComponent3.getVisualOrderText()).build();
/* 257 */                 String $$11 = mutableComponent.getString() + "\n" + mutableComponent.getString();
/*     */               } else {
/* 259 */                 immutableList = ImmutableList.of(mutableComponent2.getVisualOrderText(), mutableComponent3.getVisualOrderText());
/* 260 */                 $$13 = mutableComponent3.getString();
/*     */               } 
/*     */               
/* 263 */               ((Map<GameRules.Key<T>, EditGameRulesScreen.RuleEntry>)entries.computeIfAbsent($$0.getCategory(), $$0 -> Maps.newHashMap())).put($$0, $$1.create((Component)mutableComponent1, (List<FormattedCharSequence>)immutableList, $$13, (T)value));
/*     */             }
/*     */           });
/*     */       
/* 267 */       $$2.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach($$0 -> {
/*     */             addEntry((AbstractSelectionList.Entry)new EditGameRulesScreen.CategoryRuleEntry((Component)Component.translatable(((GameRules.Category)$$0.getKey()).getDescriptionId()).withStyle(new ChatFormatting[] { ChatFormatting.BOLD, ChatFormatting.YELLOW })));
/*     */             ((Map)$$0.getValue()).entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.comparing(GameRules.Key::getId))).forEach(());
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 275 */       super.renderWidget($$0, $$1, $$2, $$3);
/* 276 */       EditGameRulesScreen.RuleEntry $$4 = (EditGameRulesScreen.RuleEntry)getHovered();
/* 277 */       if ($$4 != null && $$4.tooltip != null)
/* 278 */         EditGameRulesScreen.this.setTooltipForNextRenderPass($$4.tooltip); 
/*     */     }
/*     */   }
/*     */   
/*     */   class null implements GameRules.GameRuleTypeVisitor {
/*     */     public void visitBoolean(GameRules.Key<GameRules.BooleanValue> $$0, GameRules.Type<GameRules.BooleanValue> $$1) {
/*     */       addEntry($$0, ($$0, $$1, $$2, $$3) -> new EditGameRulesScreen.BooleanRuleEntry($$0, $$1, $$2, $$3));
/*     */     }
/*     */     
/*     */     public void visitInteger(GameRules.Key<GameRules.IntegerValue> $$0, GameRules.Type<GameRules.IntegerValue> $$1) {
/*     */       addEntry($$0, ($$0, $$1, $$2, $$3) -> new EditGameRulesScreen.IntegerRuleEntry($$0, $$1, $$2, $$3));
/*     */     }
/*     */     
/*     */     private <T extends GameRules.Value<T>> void addEntry(GameRules.Key<T> $$0, EditGameRulesScreen.EntryFactory<T> $$1) {
/*     */       ImmutableList immutableList;
/*     */       String $$13;
/*     */       MutableComponent mutableComponent1 = Component.translatable($$0.getDescriptionId());
/*     */       MutableComponent mutableComponent2 = Component.literal($$0.getId()).withStyle(ChatFormatting.YELLOW);
/*     */       GameRules.Value value = gameRules.getRule($$0);
/*     */       String $$5 = value.serialize();
/*     */       MutableComponent mutableComponent3 = Component.translatable("editGamerule.default", new Object[] { Component.literal($$5) }).withStyle(ChatFormatting.GRAY);
/*     */       String $$7 = $$0.getDescriptionId() + ".description";
/*     */       if (I18n.exists($$7)) {
/*     */         ImmutableList.Builder<FormattedCharSequence> $$8 = ImmutableList.builder().add(mutableComponent2.getVisualOrderText());
/*     */         MutableComponent mutableComponent = Component.translatable($$7);
/*     */         Objects.requireNonNull($$8);
/*     */         EditGameRulesScreen.this.font.split((FormattedText)mutableComponent, 150).forEach($$8::add);
/*     */         immutableList = $$8.add(mutableComponent3.getVisualOrderText()).build();
/*     */         String $$11 = mutableComponent.getString() + "\n" + mutableComponent.getString();
/*     */       } else {
/*     */         immutableList = ImmutableList.of(mutableComponent2.getVisualOrderText(), mutableComponent3.getVisualOrderText());
/*     */         $$13 = mutableComponent3.getString();
/*     */       } 
/*     */       ((Map<GameRules.Key<T>, EditGameRulesScreen.RuleEntry>)entries.computeIfAbsent($$0.getCategory(), $$0 -> Maps.newHashMap())).put($$0, $$1.create((Component)mutableComponent1, (List<FormattedCharSequence>)immutableList, $$13, (T)value));
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface EntryFactory<T extends GameRules.Value<T>> {
/*     */     EditGameRulesScreen.RuleEntry create(Component param1Component, List<FormattedCharSequence> param1List, String param1String, T param1T);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\EditGameRulesScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */