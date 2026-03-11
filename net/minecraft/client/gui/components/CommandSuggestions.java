/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.ParseResults;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.context.CommandContextBuilder;
/*     */ import com.mojang.brigadier.context.ParsedArgument;
/*     */ import com.mojang.brigadier.context.SuggestionContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.suggestion.Suggestion;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.mojang.brigadier.tree.CommandNode;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.Rect2i;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ 
/*     */ 
/*     */ public class CommandSuggestions
/*     */ {
/*  47 */   private static final Pattern WHITESPACE_PATTERN = Pattern.compile("(\\s+)");
/*     */   
/*  49 */   private static final Style UNPARSED_STYLE = Style.EMPTY.withColor(ChatFormatting.RED);
/*  50 */   private static final Style LITERAL_STYLE = Style.EMPTY.withColor(ChatFormatting.GRAY);
/*  51 */   private static final List<Style> ARGUMENT_STYLES = (List<Style>)Stream.<ChatFormatting>of(new ChatFormatting[] { ChatFormatting.AQUA, ChatFormatting.YELLOW, ChatFormatting.GREEN, ChatFormatting.LIGHT_PURPLE, ChatFormatting.GOLD }).map(Style.EMPTY::withColor).collect(ImmutableList.toImmutableList()); static { Objects.requireNonNull(Style.EMPTY); }
/*     */ 
/*     */   
/*     */   final Minecraft minecraft;
/*     */   private final Screen screen;
/*     */   final EditBox input;
/*     */   final Font font;
/*     */   private final boolean commandsOnly;
/*     */   private final boolean onlyShowIfCursorPastError;
/*     */   final int lineStartOffset;
/*     */   final int suggestionLineLimit;
/*     */   final boolean anchorToBottom;
/*     */   final int fillColor;
/*  64 */   private final List<FormattedCharSequence> commandUsage = Lists.newArrayList();
/*     */   private int commandUsagePosition;
/*     */   private int commandUsageWidth;
/*     */   @Nullable
/*     */   private ParseResults<SharedSuggestionProvider> currentParse;
/*     */   @Nullable
/*     */   private CompletableFuture<Suggestions> pendingSuggestions;
/*     */   @Nullable
/*     */   private SuggestionsList suggestions;
/*     */   private boolean allowSuggestions;
/*     */   boolean keepSuggestions;
/*     */   private boolean allowHiding = true;
/*     */   
/*     */   public CommandSuggestions(Minecraft $$0, Screen $$1, EditBox $$2, Font $$3, boolean $$4, boolean $$5, int $$6, int $$7, boolean $$8, int $$9) {
/*  78 */     this.minecraft = $$0;
/*  79 */     this.screen = $$1;
/*  80 */     this.input = $$2;
/*  81 */     this.font = $$3;
/*  82 */     this.commandsOnly = $$4;
/*  83 */     this.onlyShowIfCursorPastError = $$5;
/*  84 */     this.lineStartOffset = $$6;
/*  85 */     this.suggestionLineLimit = $$7;
/*  86 */     this.anchorToBottom = $$8;
/*  87 */     this.fillColor = $$9;
/*     */     
/*  89 */     $$2.setFormatter(this::formatChat);
/*     */   }
/*     */   
/*     */   public void setAllowSuggestions(boolean $$0) {
/*  93 */     this.allowSuggestions = $$0;
/*  94 */     if (!$$0) {
/*  95 */       this.suggestions = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAllowHiding(boolean $$0) {
/* 100 */     this.allowHiding = $$0;
/*     */   }
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 104 */     boolean $$3 = (this.suggestions != null);
/*     */     
/* 106 */     if ($$3 && this.suggestions.keyPressed($$0, $$1, $$2))
/* 107 */       return true; 
/* 108 */     if (this.screen.getFocused() == this.input && $$0 == 258 && (!this.allowHiding || $$3)) {
/* 109 */       showSuggestions(true);
/* 110 */       return true;
/*     */     } 
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   public boolean mouseScrolled(double $$0) {
/* 116 */     return (this.suggestions != null && this.suggestions.mouseScrolled(Mth.clamp($$0, -1.0D, 1.0D)));
/*     */   }
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 120 */     return (this.suggestions != null && this.suggestions.mouseClicked((int)$$0, (int)$$1, $$2));
/*     */   }
/*     */   
/*     */   public void showSuggestions(boolean $$0) {
/* 124 */     if (this.pendingSuggestions != null && this.pendingSuggestions.isDone()) {
/* 125 */       Suggestions $$1 = this.pendingSuggestions.join();
/* 126 */       if (!$$1.isEmpty()) {
/* 127 */         int $$2 = 0;
/* 128 */         for (Suggestion $$3 : $$1.getList()) {
/* 129 */           $$2 = Math.max($$2, this.font.width($$3.getText()));
/*     */         }
/*     */         
/* 132 */         int $$4 = Mth.clamp(this.input.getScreenX($$1.getRange().getStart()), 0, this.input.getScreenX(0) + this.input.getInnerWidth() - $$2);
/* 133 */         int $$5 = this.anchorToBottom ? (this.screen.height - 12) : 72;
/* 134 */         this.suggestions = new SuggestionsList($$4, $$5, $$2, sortSuggestions($$1), $$0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 140 */     return (this.suggestions != null);
/*     */   }
/*     */   
/*     */   public Component getUsageNarration() {
/* 144 */     if (this.suggestions != null && this.suggestions.tabCycles) {
/* 145 */       if (this.allowHiding) {
/* 146 */         return (Component)Component.translatable("narration.suggestion.usage.cycle.hidable");
/*     */       }
/* 148 */       return (Component)Component.translatable("narration.suggestion.usage.cycle.fixed");
/*     */     } 
/*     */     
/* 151 */     if (this.allowHiding) {
/* 152 */       return (Component)Component.translatable("narration.suggestion.usage.fill.hidable");
/*     */     }
/* 154 */     return (Component)Component.translatable("narration.suggestion.usage.fill.fixed");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void hide() {
/* 160 */     this.suggestions = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Suggestion> sortSuggestions(Suggestions $$0) {
/* 165 */     String $$1 = this.input.getValue().substring(0, this.input.getCursorPosition());
/* 166 */     int $$2 = getLastWordIndex($$1);
/* 167 */     String $$3 = $$1.substring($$2).toLowerCase(Locale.ROOT);
/*     */     
/* 169 */     List<Suggestion> $$4 = Lists.newArrayList();
/* 170 */     List<Suggestion> $$5 = Lists.newArrayList();
/* 171 */     for (Suggestion $$6 : $$0.getList()) {
/* 172 */       if ($$6.getText().startsWith($$3) || $$6.getText().startsWith("minecraft:" + $$3)) {
/* 173 */         $$4.add($$6); continue;
/*     */       } 
/* 175 */       $$5.add($$6);
/*     */     } 
/*     */     
/* 178 */     $$4.addAll($$5);
/* 179 */     return $$4;
/*     */   }
/*     */   
/*     */   public void updateCommandInfo() {
/* 183 */     String $$0 = this.input.getValue();
/*     */     
/* 185 */     if (this.currentParse != null && !this.currentParse.getReader().getString().equals($$0)) {
/* 186 */       this.currentParse = null;
/*     */     }
/*     */     
/* 189 */     if (!this.keepSuggestions) {
/* 190 */       this.input.setSuggestion(null);
/* 191 */       this.suggestions = null;
/*     */     } 
/*     */     
/* 194 */     this.commandUsage.clear();
/* 195 */     StringReader $$1 = new StringReader($$0);
/* 196 */     boolean $$2 = ($$1.canRead() && $$1.peek() == '/');
/* 197 */     if ($$2) {
/* 198 */       $$1.skip();
/*     */     }
/* 200 */     boolean $$3 = (this.commandsOnly || $$2);
/*     */     
/* 202 */     int $$4 = this.input.getCursorPosition();
/* 203 */     if ($$3) {
/* 204 */       CommandDispatcher<SharedSuggestionProvider> $$5 = this.minecraft.player.connection.getCommands();
/*     */       
/* 206 */       if (this.currentParse == null) {
/* 207 */         this.currentParse = $$5.parse($$1, this.minecraft.player.connection.getSuggestionsProvider());
/*     */       }
/*     */       
/* 210 */       int $$6 = this.onlyShowIfCursorPastError ? $$1.getCursor() : 1;
/*     */       
/* 212 */       if ($$4 >= $$6 && (this.suggestions == null || !this.keepSuggestions)) {
/* 213 */         this.pendingSuggestions = $$5.getCompletionSuggestions(this.currentParse, $$4);
/* 214 */         this.pendingSuggestions.thenRun(() -> {
/*     */               if (!this.pendingSuggestions.isDone()) {
/*     */                 return;
/*     */               }
/*     */               updateUsageInfo();
/*     */             });
/*     */       } 
/*     */     } else {
/* 222 */       String $$7 = $$0.substring(0, $$4);
/* 223 */       int $$8 = getLastWordIndex($$7);
/* 224 */       Collection<String> $$9 = this.minecraft.player.connection.getSuggestionsProvider().getCustomTabSugggestions();
/* 225 */       this.pendingSuggestions = SharedSuggestionProvider.suggest($$9, new SuggestionsBuilder($$7, $$8));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getLastWordIndex(String $$0) {
/* 230 */     if (Strings.isNullOrEmpty($$0)) {
/* 231 */       return 0;
/*     */     }
/*     */     
/* 234 */     int $$1 = 0;
/*     */     
/* 236 */     Matcher $$2 = WHITESPACE_PATTERN.matcher($$0);
/* 237 */     while ($$2.find()) {
/* 238 */       $$1 = $$2.end();
/*     */     }
/*     */     
/* 241 */     return $$1;
/*     */   }
/*     */   
/*     */   private static FormattedCharSequence getExceptionMessage(CommandSyntaxException $$0) {
/* 245 */     Component $$1 = ComponentUtils.fromMessage($$0.getRawMessage());
/* 246 */     String $$2 = $$0.getContext();
/* 247 */     if ($$2 == null) {
/* 248 */       return $$1.getVisualOrderText();
/*     */     }
/* 250 */     return Component.translatable("command.context.parse_error", new Object[] { $$1, Integer.valueOf($$0.getCursor()), $$2 }).getVisualOrderText();
/*     */   }
/*     */   
/*     */   private void updateUsageInfo() {
/* 254 */     boolean $$0 = false;
/*     */     
/* 256 */     if (this.input.getCursorPosition() == this.input.getValue().length()) {
/* 257 */       if (((Suggestions)this.pendingSuggestions.join()).isEmpty() && !this.currentParse.getExceptions().isEmpty()) {
/* 258 */         int $$1 = 0;
/* 259 */         for (Map.Entry<CommandNode<SharedSuggestionProvider>, CommandSyntaxException> $$2 : (Iterable<Map.Entry<CommandNode<SharedSuggestionProvider>, CommandSyntaxException>>)this.currentParse.getExceptions().entrySet()) {
/* 260 */           CommandSyntaxException $$3 = $$2.getValue();
/* 261 */           if ($$3.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
/* 262 */             $$1++; continue;
/*     */           } 
/* 264 */           this.commandUsage.add(getExceptionMessage($$3));
/*     */         } 
/*     */         
/* 267 */         if ($$1 > 0) {
/* 268 */           this.commandUsage.add(getExceptionMessage(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create()));
/*     */         }
/* 270 */       } else if (this.currentParse.getReader().canRead()) {
/* 271 */         $$0 = true;
/*     */       } 
/*     */     }
/*     */     
/* 275 */     this.commandUsagePosition = 0;
/* 276 */     this.commandUsageWidth = this.screen.width;
/*     */     
/* 278 */     if (this.commandUsage.isEmpty() && 
/* 279 */       !fillNodeUsage(ChatFormatting.GRAY))
/*     */     {
/* 281 */       if ($$0) {
/* 282 */         this.commandUsage.add(getExceptionMessage(Commands.getParseException(this.currentParse)));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 287 */     this.suggestions = null;
/* 288 */     if (this.allowSuggestions && ((Boolean)this.minecraft.options.autoSuggestions().get()).booleanValue()) {
/* 289 */       showSuggestions(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean fillNodeUsage(ChatFormatting $$0) {
/* 294 */     CommandContextBuilder<SharedSuggestionProvider> $$1 = this.currentParse.getContext();
/* 295 */     SuggestionContext<SharedSuggestionProvider> $$2 = $$1.findSuggestionContext(this.input.getCursorPosition());
/* 296 */     Map<CommandNode<SharedSuggestionProvider>, String> $$3 = this.minecraft.player.connection.getCommands().getSmartUsage($$2.parent, this.minecraft.player.connection.getSuggestionsProvider());
/* 297 */     List<FormattedCharSequence> $$4 = Lists.newArrayList();
/* 298 */     int $$5 = 0;
/* 299 */     Style $$6 = Style.EMPTY.withColor($$0);
/*     */     
/* 301 */     for (Map.Entry<CommandNode<SharedSuggestionProvider>, String> $$7 : $$3.entrySet()) {
/* 302 */       if (!($$7.getKey() instanceof com.mojang.brigadier.tree.LiteralCommandNode)) {
/* 303 */         $$4.add(FormattedCharSequence.forward($$7.getValue(), $$6));
/* 304 */         $$5 = Math.max($$5, this.font.width($$7.getValue()));
/*     */       } 
/*     */     } 
/*     */     
/* 308 */     if (!$$4.isEmpty()) {
/* 309 */       this.commandUsage.addAll($$4);
/* 310 */       this.commandUsagePosition = Mth.clamp(this.input.getScreenX($$2.startPos), 0, this.input.getScreenX(0) + this.input.getInnerWidth() - $$5);
/* 311 */       this.commandUsageWidth = $$5;
/* 312 */       return true;
/*     */     } 
/* 314 */     return false;
/*     */   }
/*     */   
/*     */   private FormattedCharSequence formatChat(String $$0, int $$1) {
/* 318 */     if (this.currentParse != null) {
/* 319 */       return formatText(this.currentParse, $$0, $$1);
/*     */     }
/* 321 */     return FormattedCharSequence.forward($$0, Style.EMPTY);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static String calculateSuggestionSuffix(String $$0, String $$1) {
/* 327 */     if ($$1.startsWith($$0)) {
/* 328 */       return $$1.substring($$0.length());
/*     */     }
/*     */     
/* 331 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static FormattedCharSequence formatText(ParseResults<SharedSuggestionProvider> $$0, String $$1, int $$2) {
/* 336 */     List<FormattedCharSequence> $$3 = Lists.newArrayList();
/* 337 */     int $$4 = 0;
/* 338 */     int $$5 = -1;
/*     */     
/* 340 */     CommandContextBuilder<SharedSuggestionProvider> $$6 = $$0.getContext().getLastChild();
/* 341 */     for (ParsedArgument<SharedSuggestionProvider, ?> $$7 : (Iterable<ParsedArgument<SharedSuggestionProvider, ?>>)$$6.getArguments().values()) {
/* 342 */       if (++$$5 >= ARGUMENT_STYLES.size()) {
/* 343 */         $$5 = 0;
/*     */       }
/*     */       
/* 346 */       int $$8 = Math.max($$7.getRange().getStart() - $$2, 0);
/* 347 */       if ($$8 >= $$1.length()) {
/*     */         break;
/*     */       }
/* 350 */       int $$9 = Math.min($$7.getRange().getEnd() - $$2, $$1.length());
/* 351 */       if ($$9 <= 0) {
/*     */         continue;
/*     */       }
/* 354 */       $$3.add(FormattedCharSequence.forward($$1.substring($$4, $$8), LITERAL_STYLE));
/* 355 */       $$3.add(FormattedCharSequence.forward($$1.substring($$8, $$9), ARGUMENT_STYLES.get($$5)));
/* 356 */       $$4 = $$9;
/*     */     } 
/* 358 */     if ($$0.getReader().canRead()) {
/* 359 */       int $$10 = Math.max($$0.getReader().getCursor() - $$2, 0);
/* 360 */       if ($$10 < $$1.length()) {
/* 361 */         int $$11 = Math.min($$10 + $$0.getReader().getRemainingLength(), $$1.length());
/* 362 */         $$3.add(FormattedCharSequence.forward($$1.substring($$4, $$10), LITERAL_STYLE));
/* 363 */         $$3.add(FormattedCharSequence.forward($$1.substring($$10, $$11), UNPARSED_STYLE));
/* 364 */         $$4 = $$11;
/*     */       } 
/*     */     } 
/* 367 */     $$3.add(FormattedCharSequence.forward($$1.substring($$4), LITERAL_STYLE));
/* 368 */     return FormattedCharSequence.composite($$3);
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2) {
/* 372 */     if (!renderSuggestions($$0, $$1, $$2)) {
/* 373 */       renderUsage($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean renderSuggestions(GuiGraphics $$0, int $$1, int $$2) {
/* 378 */     if (this.suggestions != null) {
/* 379 */       this.suggestions.render($$0, $$1, $$2);
/* 380 */       return true;
/*     */     } 
/* 382 */     return false;
/*     */   }
/*     */   
/*     */   public void renderUsage(GuiGraphics $$0) {
/* 386 */     int $$1 = 0;
/* 387 */     for (FormattedCharSequence $$2 : this.commandUsage) {
/* 388 */       int $$3 = this.anchorToBottom ? (this.screen.height - 14 - 13 - 12 * $$1) : (72 + 12 * $$1);
/* 389 */       $$0.fill(this.commandUsagePosition - 1, $$3, this.commandUsagePosition + this.commandUsageWidth + 1, $$3 + 12, this.fillColor);
/* 390 */       $$0.drawString(this.font, $$2, this.commandUsagePosition, $$3 + 2, -1);
/* 391 */       $$1++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Component getNarrationMessage() {
/* 396 */     if (this.suggestions != null) {
/* 397 */       return (Component)CommonComponents.NEW_LINE.copy().append(this.suggestions.getNarrationMessage());
/*     */     }
/* 399 */     return CommonComponents.EMPTY;
/*     */   }
/*     */   
/*     */   public class SuggestionsList {
/*     */     private final Rect2i rect;
/*     */     private final String originalContents;
/*     */     private final List<Suggestion> suggestionList;
/*     */     private int offset;
/*     */     private int current;
/* 408 */     private Vec2 lastMouse = Vec2.ZERO;
/*     */     boolean tabCycles;
/*     */     private int lastNarratedEntry;
/*     */     
/*     */     SuggestionsList(int $$1, int $$2, int $$3, List<Suggestion> $$4, boolean $$5) {
/* 413 */       int $$6 = $$1 - ($$0.input.isBordered() ? 0 : 1);
/* 414 */       int $$7 = $$0.anchorToBottom ? ($$2 - 3 - Math.min($$4.size(), $$0.suggestionLineLimit) * 12) : ($$2 - ($$0.input.isBordered() ? 1 : 0));
/* 415 */       this.rect = new Rect2i($$6, $$7, $$3 + 1, Math.min($$4.size(), $$0.suggestionLineLimit) * 12);
/* 416 */       this.originalContents = $$0.input.getValue();
/* 417 */       this.lastNarratedEntry = $$5 ? -1 : 0;
/* 418 */       this.suggestionList = $$4;
/* 419 */       select(0);
/*     */     }
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2) {
/* 423 */       int $$3 = Math.min(this.suggestionList.size(), CommandSuggestions.this.suggestionLineLimit);
/* 424 */       int $$4 = -5592406;
/* 425 */       boolean $$5 = (this.offset > 0);
/* 426 */       boolean $$6 = (this.suggestionList.size() > this.offset + $$3);
/* 427 */       boolean $$7 = ($$5 || $$6);
/* 428 */       boolean $$8 = (this.lastMouse.x != $$1 || this.lastMouse.y != $$2);
/*     */       
/* 430 */       if ($$8) {
/* 431 */         this.lastMouse = new Vec2($$1, $$2);
/*     */       }
/*     */       
/* 434 */       if ($$7) {
/* 435 */         $$0.fill(this.rect.getX(), this.rect.getY() - 1, this.rect.getX() + this.rect.getWidth(), this.rect.getY(), CommandSuggestions.this.fillColor);
/* 436 */         $$0.fill(this.rect.getX(), this.rect.getY() + this.rect.getHeight(), this.rect.getX() + this.rect.getWidth(), this.rect.getY() + this.rect.getHeight() + 1, CommandSuggestions.this.fillColor);
/* 437 */         if ($$5) {
/* 438 */           for (int $$9 = 0; $$9 < this.rect.getWidth(); $$9++) {
/* 439 */             if ($$9 % 2 == 0) {
/* 440 */               $$0.fill(this.rect.getX() + $$9, this.rect.getY() - 1, this.rect.getX() + $$9 + 1, this.rect.getY(), -1);
/*     */             }
/*     */           } 
/*     */         }
/* 444 */         if ($$6) {
/* 445 */           for (int $$10 = 0; $$10 < this.rect.getWidth(); $$10++) {
/* 446 */             if ($$10 % 2 == 0) {
/* 447 */               $$0.fill(this.rect.getX() + $$10, this.rect.getY() + this.rect.getHeight(), this.rect.getX() + $$10 + 1, this.rect.getY() + this.rect.getHeight() + 1, -1);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 453 */       boolean $$11 = false;
/* 454 */       for (int $$12 = 0; $$12 < $$3; $$12++) {
/* 455 */         Suggestion $$13 = this.suggestionList.get($$12 + this.offset);
/* 456 */         $$0.fill(this.rect.getX(), this.rect.getY() + 12 * $$12, this.rect.getX() + this.rect.getWidth(), this.rect.getY() + 12 * $$12 + 12, CommandSuggestions.this.fillColor);
/* 457 */         if ($$1 > this.rect.getX() && $$1 < this.rect.getX() + this.rect.getWidth() && $$2 > this.rect.getY() + 12 * $$12 && $$2 < this.rect.getY() + 12 * $$12 + 12) {
/* 458 */           if ($$8) {
/* 459 */             select($$12 + this.offset);
/*     */           }
/* 461 */           $$11 = true;
/*     */         } 
/* 463 */         $$0.drawString(CommandSuggestions.this.font, $$13.getText(), this.rect.getX() + 1, this.rect.getY() + 2 + 12 * $$12, ($$12 + this.offset == this.current) ? -256 : -5592406);
/*     */       } 
/*     */       
/* 466 */       if ($$11) {
/* 467 */         Message $$14 = ((Suggestion)this.suggestionList.get(this.current)).getTooltip();
/* 468 */         if ($$14 != null) {
/* 469 */           $$0.renderTooltip(CommandSuggestions.this.font, ComponentUtils.fromMessage($$14), $$1, $$2);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean mouseClicked(int $$0, int $$1, int $$2) {
/* 475 */       if (!this.rect.contains($$0, $$1)) {
/* 476 */         return false;
/*     */       }
/*     */       
/* 479 */       int $$3 = ($$1 - this.rect.getY()) / 12 + this.offset;
/* 480 */       if ($$3 >= 0 && $$3 < this.suggestionList.size()) {
/* 481 */         select($$3);
/* 482 */         useSuggestion();
/*     */       } 
/*     */       
/* 485 */       return true;
/*     */     }
/*     */     
/*     */     public boolean mouseScrolled(double $$0) {
/* 489 */       int $$1 = (int)(CommandSuggestions.this.minecraft.mouseHandler.xpos() * CommandSuggestions.this.minecraft.getWindow().getGuiScaledWidth() / CommandSuggestions.this.minecraft.getWindow().getScreenWidth());
/* 490 */       int $$2 = (int)(CommandSuggestions.this.minecraft.mouseHandler.ypos() * CommandSuggestions.this.minecraft.getWindow().getGuiScaledHeight() / CommandSuggestions.this.minecraft.getWindow().getScreenHeight());
/*     */       
/* 492 */       if (this.rect.contains($$1, $$2)) {
/* 493 */         this.offset = Mth.clamp((int)(this.offset - $$0), 0, Math.max(this.suggestionList.size() - CommandSuggestions.this.suggestionLineLimit, 0));
/* 494 */         return true;
/*     */       } 
/*     */       
/* 497 */       return false;
/*     */     }
/*     */     
/*     */     public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 501 */       if ($$0 == 265) {
/* 502 */         cycle(-1);
/* 503 */         this.tabCycles = false;
/* 504 */         return true;
/* 505 */       }  if ($$0 == 264) {
/* 506 */         cycle(1);
/* 507 */         this.tabCycles = false;
/* 508 */         return true;
/* 509 */       }  if ($$0 == 258) {
/* 510 */         if (this.tabCycles) {
/* 511 */           cycle(Screen.hasShiftDown() ? -1 : 1);
/*     */         }
/* 513 */         useSuggestion();
/* 514 */         return true;
/* 515 */       }  if ($$0 == 256) {
/* 516 */         CommandSuggestions.this.hide();
/* 517 */         CommandSuggestions.this.input.setSuggestion(null);
/*     */         
/* 519 */         return true;
/*     */       } 
/*     */       
/* 522 */       return false;
/*     */     }
/*     */     
/*     */     public void cycle(int $$0) {
/* 526 */       select(this.current + $$0);
/* 527 */       int $$1 = this.offset;
/* 528 */       int $$2 = this.offset + CommandSuggestions.this.suggestionLineLimit - 1;
/* 529 */       if (this.current < $$1) {
/* 530 */         this.offset = Mth.clamp(this.current, 0, Math.max(this.suggestionList.size() - CommandSuggestions.this.suggestionLineLimit, 0));
/* 531 */       } else if (this.current > $$2) {
/* 532 */         this.offset = Mth.clamp(this.current + CommandSuggestions.this.lineStartOffset - CommandSuggestions.this.suggestionLineLimit, 0, Math.max(this.suggestionList.size() - CommandSuggestions.this.suggestionLineLimit, 0));
/*     */       } 
/*     */     }
/*     */     
/*     */     public void select(int $$0) {
/* 537 */       this.current = $$0;
/*     */       
/* 539 */       if (this.current < 0) {
/* 540 */         this.current += this.suggestionList.size();
/*     */       }
/* 542 */       if (this.current >= this.suggestionList.size()) {
/* 543 */         this.current -= this.suggestionList.size();
/*     */       }
/*     */       
/* 546 */       Suggestion $$1 = this.suggestionList.get(this.current);
/* 547 */       CommandSuggestions.this.input.setSuggestion(CommandSuggestions.calculateSuggestionSuffix(CommandSuggestions.this.input.getValue(), $$1.apply(this.originalContents)));
/*     */       
/* 549 */       if (this.lastNarratedEntry != this.current) {
/* 550 */         CommandSuggestions.this.minecraft.getNarrator().sayNow(getNarrationMessage());
/*     */       }
/*     */     }
/*     */     
/*     */     public void useSuggestion() {
/* 555 */       Suggestion $$0 = this.suggestionList.get(this.current);
/* 556 */       CommandSuggestions.this.keepSuggestions = true;
/* 557 */       CommandSuggestions.this.input.setValue($$0.apply(this.originalContents));
/* 558 */       int $$1 = $$0.getRange().getStart() + $$0.getText().length();
/* 559 */       CommandSuggestions.this.input.setCursorPosition($$1);
/* 560 */       CommandSuggestions.this.input.setHighlightPos($$1);
/* 561 */       select(this.current);
/* 562 */       CommandSuggestions.this.keepSuggestions = false;
/* 563 */       this.tabCycles = true;
/*     */     }
/*     */     
/*     */     Component getNarrationMessage() {
/* 567 */       this.lastNarratedEntry = this.current;
/* 568 */       Suggestion $$0 = this.suggestionList.get(this.current);
/* 569 */       Message $$1 = $$0.getTooltip();
/* 570 */       if ($$1 != null) {
/* 571 */         return (Component)Component.translatable("narration.suggestion.tooltip", new Object[] { Integer.valueOf(this.current + 1), Integer.valueOf(this.suggestionList.size()), $$0.getText(), Component.translationArg($$1) });
/*     */       }
/* 573 */       return (Component)Component.translatable("narration.suggestion", new Object[] { Integer.valueOf(this.current + 1), Integer.valueOf(this.suggestionList.size()), $$0.getText() });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\CommandSuggestions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */