/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.world.level.GameRules;
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
/*     */ class null
/*     */   implements GameRules.GameRuleTypeVisitor
/*     */ {
/*     */   public void visitBoolean(GameRules.Key<GameRules.BooleanValue> $$0, GameRules.Type<GameRules.BooleanValue> $$1) {
/* 233 */     addEntry($$0, ($$0, $$1, $$2, $$3) -> new EditGameRulesScreen.BooleanRuleEntry(this.this$1.this$0, $$0, $$1, $$2, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInteger(GameRules.Key<GameRules.IntegerValue> $$0, GameRules.Type<GameRules.IntegerValue> $$1) {
/* 238 */     addEntry($$0, ($$0, $$1, $$2, $$3) -> new EditGameRulesScreen.IntegerRuleEntry(this.this$1.this$0, $$0, $$1, $$2, $$3));
/*     */   } private <T extends GameRules.Value<T>> void addEntry(GameRules.Key<T> $$0, EditGameRulesScreen.EntryFactory<T> $$1) {
/*     */     ImmutableList immutableList;
/*     */     String $$13;
/* 242 */     MutableComponent mutableComponent1 = Component.translatable($$0.getDescriptionId());
/* 243 */     MutableComponent mutableComponent2 = Component.literal($$0.getId()).withStyle(ChatFormatting.YELLOW);
/*     */     
/* 245 */     GameRules.Value value = gameRules.getRule($$0);
/* 246 */     String $$5 = value.serialize();
/* 247 */     MutableComponent mutableComponent3 = Component.translatable("editGamerule.default", new Object[] { Component.literal($$5) }).withStyle(ChatFormatting.GRAY);
/* 248 */     String $$7 = $$0.getDescriptionId() + ".description";
/*     */ 
/*     */ 
/*     */     
/* 252 */     if (I18n.exists($$7)) {
/* 253 */       ImmutableList.Builder<FormattedCharSequence> $$8 = ImmutableList.builder().add(mutableComponent2.getVisualOrderText());
/* 254 */       MutableComponent mutableComponent = Component.translatable($$7);
/* 255 */       Objects.requireNonNull($$8); EditGameRulesScreen.access$700(this.this$1.this$0).split((FormattedText)mutableComponent, 150).forEach($$8::add);
/* 256 */       immutableList = $$8.add(mutableComponent3.getVisualOrderText()).build();
/* 257 */       String $$11 = mutableComponent.getString() + "\n" + mutableComponent.getString();
/*     */     } else {
/* 259 */       immutableList = ImmutableList.of(mutableComponent2.getVisualOrderText(), mutableComponent3.getVisualOrderText());
/* 260 */       $$13 = mutableComponent3.getString();
/*     */     } 
/*     */     
/* 263 */     ((Map<GameRules.Key<T>, EditGameRulesScreen.RuleEntry>)entries.computeIfAbsent($$0.getCategory(), $$0 -> Maps.newHashMap())).put($$0, $$1.create((Component)mutableComponent1, (List<FormattedCharSequence>)immutableList, $$13, (T)value));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\EditGameRulesScreen$RuleList$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */