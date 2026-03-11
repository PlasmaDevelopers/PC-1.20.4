/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.suggestion.Suggestion;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.Rect2i;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.phys.Vec2;
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
/*     */ public class SuggestionsList
/*     */ {
/*     */   private final Rect2i rect;
/*     */   private final String originalContents;
/*     */   private final List<Suggestion> suggestionList;
/*     */   private int offset;
/*     */   private int current;
/* 408 */   private Vec2 lastMouse = Vec2.ZERO;
/*     */   boolean tabCycles;
/*     */   private int lastNarratedEntry;
/*     */   
/*     */   SuggestionsList(int $$1, int $$2, int $$3, List<Suggestion> $$4, boolean $$5) {
/* 413 */     int $$6 = $$1 - ($$0.input.isBordered() ? 0 : 1);
/* 414 */     int $$7 = $$0.anchorToBottom ? ($$2 - 3 - Math.min($$4.size(), $$0.suggestionLineLimit) * 12) : ($$2 - ($$0.input.isBordered() ? 1 : 0));
/* 415 */     this.rect = new Rect2i($$6, $$7, $$3 + 1, Math.min($$4.size(), $$0.suggestionLineLimit) * 12);
/* 416 */     this.originalContents = $$0.input.getValue();
/* 417 */     this.lastNarratedEntry = $$5 ? -1 : 0;
/* 418 */     this.suggestionList = $$4;
/* 419 */     select(0);
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2) {
/* 423 */     int $$3 = Math.min(this.suggestionList.size(), CommandSuggestions.this.suggestionLineLimit);
/* 424 */     int $$4 = -5592406;
/* 425 */     boolean $$5 = (this.offset > 0);
/* 426 */     boolean $$6 = (this.suggestionList.size() > this.offset + $$3);
/* 427 */     boolean $$7 = ($$5 || $$6);
/* 428 */     boolean $$8 = (this.lastMouse.x != $$1 || this.lastMouse.y != $$2);
/*     */     
/* 430 */     if ($$8) {
/* 431 */       this.lastMouse = new Vec2($$1, $$2);
/*     */     }
/*     */     
/* 434 */     if ($$7) {
/* 435 */       $$0.fill(this.rect.getX(), this.rect.getY() - 1, this.rect.getX() + this.rect.getWidth(), this.rect.getY(), CommandSuggestions.this.fillColor);
/* 436 */       $$0.fill(this.rect.getX(), this.rect.getY() + this.rect.getHeight(), this.rect.getX() + this.rect.getWidth(), this.rect.getY() + this.rect.getHeight() + 1, CommandSuggestions.this.fillColor);
/* 437 */       if ($$5) {
/* 438 */         for (int $$9 = 0; $$9 < this.rect.getWidth(); $$9++) {
/* 439 */           if ($$9 % 2 == 0) {
/* 440 */             $$0.fill(this.rect.getX() + $$9, this.rect.getY() - 1, this.rect.getX() + $$9 + 1, this.rect.getY(), -1);
/*     */           }
/*     */         } 
/*     */       }
/* 444 */       if ($$6) {
/* 445 */         for (int $$10 = 0; $$10 < this.rect.getWidth(); $$10++) {
/* 446 */           if ($$10 % 2 == 0) {
/* 447 */             $$0.fill(this.rect.getX() + $$10, this.rect.getY() + this.rect.getHeight(), this.rect.getX() + $$10 + 1, this.rect.getY() + this.rect.getHeight() + 1, -1);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 453 */     boolean $$11 = false;
/* 454 */     for (int $$12 = 0; $$12 < $$3; $$12++) {
/* 455 */       Suggestion $$13 = this.suggestionList.get($$12 + this.offset);
/* 456 */       $$0.fill(this.rect.getX(), this.rect.getY() + 12 * $$12, this.rect.getX() + this.rect.getWidth(), this.rect.getY() + 12 * $$12 + 12, CommandSuggestions.this.fillColor);
/* 457 */       if ($$1 > this.rect.getX() && $$1 < this.rect.getX() + this.rect.getWidth() && $$2 > this.rect.getY() + 12 * $$12 && $$2 < this.rect.getY() + 12 * $$12 + 12) {
/* 458 */         if ($$8) {
/* 459 */           select($$12 + this.offset);
/*     */         }
/* 461 */         $$11 = true;
/*     */       } 
/* 463 */       $$0.drawString(CommandSuggestions.this.font, $$13.getText(), this.rect.getX() + 1, this.rect.getY() + 2 + 12 * $$12, ($$12 + this.offset == this.current) ? -256 : -5592406);
/*     */     } 
/*     */     
/* 466 */     if ($$11) {
/* 467 */       Message $$14 = ((Suggestion)this.suggestionList.get(this.current)).getTooltip();
/* 468 */       if ($$14 != null) {
/* 469 */         $$0.renderTooltip(CommandSuggestions.this.font, ComponentUtils.fromMessage($$14), $$1, $$2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean mouseClicked(int $$0, int $$1, int $$2) {
/* 475 */     if (!this.rect.contains($$0, $$1)) {
/* 476 */       return false;
/*     */     }
/*     */     
/* 479 */     int $$3 = ($$1 - this.rect.getY()) / 12 + this.offset;
/* 480 */     if ($$3 >= 0 && $$3 < this.suggestionList.size()) {
/* 481 */       select($$3);
/* 482 */       useSuggestion();
/*     */     } 
/*     */     
/* 485 */     return true;
/*     */   }
/*     */   
/*     */   public boolean mouseScrolled(double $$0) {
/* 489 */     int $$1 = (int)(CommandSuggestions.this.minecraft.mouseHandler.xpos() * CommandSuggestions.this.minecraft.getWindow().getGuiScaledWidth() / CommandSuggestions.this.minecraft.getWindow().getScreenWidth());
/* 490 */     int $$2 = (int)(CommandSuggestions.this.minecraft.mouseHandler.ypos() * CommandSuggestions.this.minecraft.getWindow().getGuiScaledHeight() / CommandSuggestions.this.minecraft.getWindow().getScreenHeight());
/*     */     
/* 492 */     if (this.rect.contains($$1, $$2)) {
/* 493 */       this.offset = Mth.clamp((int)(this.offset - $$0), 0, Math.max(this.suggestionList.size() - CommandSuggestions.this.suggestionLineLimit, 0));
/* 494 */       return true;
/*     */     } 
/*     */     
/* 497 */     return false;
/*     */   }
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 501 */     if ($$0 == 265) {
/* 502 */       cycle(-1);
/* 503 */       this.tabCycles = false;
/* 504 */       return true;
/* 505 */     }  if ($$0 == 264) {
/* 506 */       cycle(1);
/* 507 */       this.tabCycles = false;
/* 508 */       return true;
/* 509 */     }  if ($$0 == 258) {
/* 510 */       if (this.tabCycles) {
/* 511 */         cycle(Screen.hasShiftDown() ? -1 : 1);
/*     */       }
/* 513 */       useSuggestion();
/* 514 */       return true;
/* 515 */     }  if ($$0 == 256) {
/* 516 */       CommandSuggestions.this.hide();
/* 517 */       CommandSuggestions.this.input.setSuggestion(null);
/*     */       
/* 519 */       return true;
/*     */     } 
/*     */     
/* 522 */     return false;
/*     */   }
/*     */   
/*     */   public void cycle(int $$0) {
/* 526 */     select(this.current + $$0);
/* 527 */     int $$1 = this.offset;
/* 528 */     int $$2 = this.offset + CommandSuggestions.this.suggestionLineLimit - 1;
/* 529 */     if (this.current < $$1) {
/* 530 */       this.offset = Mth.clamp(this.current, 0, Math.max(this.suggestionList.size() - CommandSuggestions.this.suggestionLineLimit, 0));
/* 531 */     } else if (this.current > $$2) {
/* 532 */       this.offset = Mth.clamp(this.current + CommandSuggestions.this.lineStartOffset - CommandSuggestions.this.suggestionLineLimit, 0, Math.max(this.suggestionList.size() - CommandSuggestions.this.suggestionLineLimit, 0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void select(int $$0) {
/* 537 */     this.current = $$0;
/*     */     
/* 539 */     if (this.current < 0) {
/* 540 */       this.current += this.suggestionList.size();
/*     */     }
/* 542 */     if (this.current >= this.suggestionList.size()) {
/* 543 */       this.current -= this.suggestionList.size();
/*     */     }
/*     */     
/* 546 */     Suggestion $$1 = this.suggestionList.get(this.current);
/* 547 */     CommandSuggestions.this.input.setSuggestion(CommandSuggestions.calculateSuggestionSuffix(CommandSuggestions.this.input.getValue(), $$1.apply(this.originalContents)));
/*     */     
/* 549 */     if (this.lastNarratedEntry != this.current) {
/* 550 */       CommandSuggestions.this.minecraft.getNarrator().sayNow(getNarrationMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public void useSuggestion() {
/* 555 */     Suggestion $$0 = this.suggestionList.get(this.current);
/* 556 */     CommandSuggestions.this.keepSuggestions = true;
/* 557 */     CommandSuggestions.this.input.setValue($$0.apply(this.originalContents));
/* 558 */     int $$1 = $$0.getRange().getStart() + $$0.getText().length();
/* 559 */     CommandSuggestions.this.input.setCursorPosition($$1);
/* 560 */     CommandSuggestions.this.input.setHighlightPos($$1);
/* 561 */     select(this.current);
/* 562 */     CommandSuggestions.this.keepSuggestions = false;
/* 563 */     this.tabCycles = true;
/*     */   }
/*     */   
/*     */   Component getNarrationMessage() {
/* 567 */     this.lastNarratedEntry = this.current;
/* 568 */     Suggestion $$0 = this.suggestionList.get(this.current);
/* 569 */     Message $$1 = $$0.getTooltip();
/* 570 */     if ($$1 != null) {
/* 571 */       return (Component)Component.translatable("narration.suggestion.tooltip", new Object[] { Integer.valueOf(this.current + 1), Integer.valueOf(this.suggestionList.size()), $$0.getText(), Component.translationArg($$1) });
/*     */     }
/* 573 */     return (Component)Component.translatable("narration.suggestion", new Object[] { Integer.valueOf(this.current + 1), Integer.valueOf(this.suggestionList.size()), $$0.getText() });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\CommandSuggestions$SuggestionsList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */