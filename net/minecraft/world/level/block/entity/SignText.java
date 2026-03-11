/*     */ package net.minecraft.world.level.block.entity;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ 
/*     */ public class SignText {
/*     */   static {
/*  22 */     LINES_CODEC = ComponentSerialization.FLAT_CODEC.listOf().comapFlatMap($$0 -> Util.fixedSize($$0, 4).map(()), $$0 -> List.of($$0[0], $$0[1], $$0[2], $$0[3]));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  27 */     DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)LINES_CODEC.fieldOf("messages").forGetter(()), (App)LINES_CODEC.optionalFieldOf("filtered_messages").forGetter(SignText::filteredMessages), (App)DyeColor.CODEC.fieldOf("color").orElse(DyeColor.BLACK).forGetter(()), (App)Codec.BOOL.fieldOf("has_glowing_text").orElse(Boolean.valueOf(false)).forGetter(())).apply((Applicative)$$0, SignText::load));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final Codec<Component[]> LINES_CODEC;
/*     */   
/*     */   public static final Codec<SignText> DIRECT_CODEC;
/*     */   public static final int LINES = 4;
/*     */   private final Component[] messages;
/*     */   private final Component[] filteredMessages;
/*     */   private final DyeColor color;
/*     */   private final boolean hasGlowingText;
/*     */   @Nullable
/*     */   private FormattedCharSequence[] renderMessages;
/*     */   private boolean renderMessagedFiltered;
/*     */   
/*     */   public SignText() {
/*  44 */     this(emptyMessages(), emptyMessages(), DyeColor.BLACK, false);
/*     */   }
/*     */   
/*     */   public SignText(Component[] $$0, Component[] $$1, DyeColor $$2, boolean $$3) {
/*  48 */     this.messages = $$0;
/*  49 */     this.filteredMessages = $$1;
/*  50 */     this.color = $$2;
/*  51 */     this.hasGlowingText = $$3;
/*     */   }
/*     */   
/*     */   private static Component[] emptyMessages() {
/*  55 */     return new Component[] { CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY };
/*     */   }
/*     */   
/*     */   private static SignText load(Component[] $$0, Optional<Component[]> $$1, DyeColor $$2, boolean $$3) {
/*  59 */     return new SignText($$0, $$1.orElse(Arrays.<Component>copyOf($$0, $$0.length)), $$2, $$3);
/*     */   }
/*     */   
/*     */   public boolean hasGlowingText() {
/*  63 */     return this.hasGlowingText;
/*     */   }
/*     */   
/*     */   public SignText setHasGlowingText(boolean $$0) {
/*  67 */     if ($$0 == this.hasGlowingText) {
/*  68 */       return this;
/*     */     }
/*  70 */     return new SignText(this.messages, this.filteredMessages, this.color, $$0);
/*     */   }
/*     */   
/*     */   public DyeColor getColor() {
/*  74 */     return this.color;
/*     */   }
/*     */   
/*     */   public SignText setColor(DyeColor $$0) {
/*  78 */     if ($$0 == getColor()) {
/*  79 */       return this;
/*     */     }
/*  81 */     return new SignText(this.messages, this.filteredMessages, $$0, this.hasGlowingText);
/*     */   }
/*     */   
/*     */   public Component getMessage(int $$0, boolean $$1) {
/*  85 */     return getMessages($$1)[$$0];
/*     */   }
/*     */   
/*     */   public SignText setMessage(int $$0, Component $$1) {
/*  89 */     return setMessage($$0, $$1, $$1);
/*     */   }
/*     */   
/*     */   public SignText setMessage(int $$0, Component $$1, Component $$2) {
/*  93 */     Component[] $$3 = Arrays.<Component>copyOf(this.messages, this.messages.length);
/*  94 */     Component[] $$4 = Arrays.<Component>copyOf(this.filteredMessages, this.filteredMessages.length);
/*  95 */     $$3[$$0] = $$1;
/*  96 */     $$4[$$0] = $$2;
/*  97 */     return new SignText($$3, $$4, this.color, this.hasGlowingText);
/*     */   }
/*     */   
/*     */   public boolean hasMessage(Player $$0) {
/* 101 */     return Arrays.<Component>stream(getMessages($$0.isTextFilteringEnabled())).anyMatch($$0 -> !$$0.getString().isEmpty());
/*     */   }
/*     */   
/*     */   public Component[] getMessages(boolean $$0) {
/* 105 */     return $$0 ? this.filteredMessages : this.messages;
/*     */   }
/*     */   
/*     */   public FormattedCharSequence[] getRenderMessages(boolean $$0, Function<Component, FormattedCharSequence> $$1) {
/* 109 */     if (this.renderMessages == null || this.renderMessagedFiltered != $$0) {
/* 110 */       this.renderMessagedFiltered = $$0;
/* 111 */       this.renderMessages = new FormattedCharSequence[4];
/* 112 */       for (int $$2 = 0; $$2 < 4; $$2++) {
/* 113 */         this.renderMessages[$$2] = $$1.apply(getMessage($$2, $$0));
/*     */       }
/*     */     } 
/* 116 */     return this.renderMessages;
/*     */   }
/*     */   
/*     */   private Optional<Component[]> filteredMessages() {
/* 120 */     for (int $$0 = 0; $$0 < 4; $$0++) {
/* 121 */       if (!this.filteredMessages[$$0].equals(this.messages[$$0])) {
/* 122 */         return (Optional)Optional.of(this.filteredMessages);
/*     */       }
/*     */     } 
/* 125 */     return (Optional)Optional.empty();
/*     */   }
/*     */   
/*     */   public boolean hasAnyClickCommands(Player $$0) {
/* 129 */     for (Component $$1 : getMessages($$0.isTextFilteringEnabled())) {
/* 130 */       Style $$2 = $$1.getStyle();
/* 131 */       ClickEvent $$3 = $$2.getClickEvent();
/* 132 */       if ($$3 != null && $$3.getAction() == ClickEvent.Action.RUN_COMMAND) {
/* 133 */         return true;
/*     */       }
/*     */     } 
/* 136 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SignText.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */