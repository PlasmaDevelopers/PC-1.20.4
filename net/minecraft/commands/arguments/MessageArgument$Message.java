/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.List;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelectorParser;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Message
/*     */ {
/*     */   final String text;
/*     */   private final MessageArgument.Part[] parts;
/*     */   
/*     */   public Message(String $$0, MessageArgument.Part[] $$1) {
/*  96 */     this.text = $$0;
/*  97 */     this.parts = $$1;
/*     */   }
/*     */   
/*     */   public String getText() {
/* 101 */     return this.text;
/*     */   }
/*     */   
/*     */   public MessageArgument.Part[] getParts() {
/* 105 */     return this.parts;
/*     */   }
/*     */   
/*     */   Component resolveComponent(CommandSourceStack $$0) throws CommandSyntaxException {
/* 109 */     return toComponent($$0, $$0.hasPermission(2));
/*     */   }
/*     */   
/*     */   public Component toComponent(CommandSourceStack $$0, boolean $$1) throws CommandSyntaxException {
/* 113 */     if (this.parts.length == 0 || !$$1) {
/* 114 */       return (Component)Component.literal(this.text);
/*     */     }
/*     */     
/* 117 */     MutableComponent $$2 = Component.literal(this.text.substring(0, this.parts[0].getStart()));
/* 118 */     int $$3 = this.parts[0].getStart();
/*     */     
/* 120 */     for (MessageArgument.Part $$4 : this.parts) {
/* 121 */       Component $$5 = $$4.toComponent($$0);
/* 122 */       if ($$3 < $$4.getStart()) {
/* 123 */         $$2.append(this.text.substring($$3, $$4.getStart()));
/*     */       }
/* 125 */       if ($$5 != null) {
/* 126 */         $$2.append($$5);
/*     */       }
/* 128 */       $$3 = $$4.getEnd();
/*     */     } 
/*     */     
/* 131 */     if ($$3 < this.text.length()) {
/* 132 */       $$2.append(this.text.substring($$3));
/*     */     }
/*     */     
/* 135 */     return (Component)$$2;
/*     */   }
/*     */   
/*     */   public static Message parseText(StringReader $$0, boolean $$1) throws CommandSyntaxException {
/* 139 */     String $$2 = $$0.getString().substring($$0.getCursor(), $$0.getTotalLength());
/*     */     
/* 141 */     if (!$$1) {
/* 142 */       $$0.setCursor($$0.getTotalLength());
/* 143 */       return new Message($$2, new MessageArgument.Part[0]);
/*     */     } 
/*     */     
/* 146 */     List<MessageArgument.Part> $$3 = Lists.newArrayList();
/* 147 */     int $$4 = $$0.getCursor();
/*     */     
/* 149 */     while ($$0.canRead()) {
/* 150 */       if ($$0.peek() == '@') {
/* 151 */         EntitySelector $$7; int $$5 = $$0.getCursor();
/*     */         
/*     */         try {
/* 154 */           EntitySelectorParser $$6 = new EntitySelectorParser($$0);
/* 155 */           $$7 = $$6.parse();
/* 156 */         } catch (CommandSyntaxException $$8) {
/* 157 */           if ($$8.getType() == EntitySelectorParser.ERROR_MISSING_SELECTOR_TYPE || $$8.getType() == EntitySelectorParser.ERROR_UNKNOWN_SELECTOR_TYPE) {
/* 158 */             $$0.setCursor($$5 + 1);
/*     */             continue;
/*     */           } 
/* 161 */           throw $$8;
/*     */         } 
/* 163 */         $$3.add(new MessageArgument.Part($$5 - $$4, $$0.getCursor() - $$4, $$7)); continue;
/*     */       } 
/* 165 */       $$0.skip();
/*     */     } 
/*     */ 
/*     */     
/* 169 */     return new Message($$2, $$3.<MessageArgument.Part>toArray(new MessageArgument.Part[0]));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\MessageArgument$Message.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */