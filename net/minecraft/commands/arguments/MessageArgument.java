/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSigningContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelectorParser;
/*     */ import net.minecraft.network.chat.ChatDecorator;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.PlayerChatMessage;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.network.FilteredText;
/*     */ 
/*     */ public class MessageArgument
/*     */   implements SignedArgument<MessageArgument.Message>
/*     */ {
/*  28 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "Hello world!", "foo", "@e", "Hello @p :)" });
/*     */   
/*     */   public static MessageArgument message() {
/*  31 */     return new MessageArgument();
/*     */   }
/*     */   
/*     */   public static Component getMessage(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  35 */     Message $$2 = (Message)$$0.getArgument($$1, Message.class);
/*  36 */     return $$2.resolveComponent((CommandSourceStack)$$0.getSource());
/*     */   }
/*     */   
/*     */   public static void resolveChatMessage(CommandContext<CommandSourceStack> $$0, String $$1, Consumer<PlayerChatMessage> $$2) throws CommandSyntaxException {
/*  40 */     Message $$3 = (Message)$$0.getArgument($$1, Message.class);
/*  41 */     CommandSourceStack $$4 = (CommandSourceStack)$$0.getSource();
/*  42 */     Component $$5 = $$3.resolveComponent($$4);
/*     */     
/*  44 */     CommandSigningContext $$6 = $$4.getSigningContext();
/*  45 */     PlayerChatMessage $$7 = $$6.getArgument($$1);
/*  46 */     if ($$7 != null) {
/*  47 */       resolveSignedMessage($$2, $$4, $$7.withUnsignedContent($$5));
/*     */     } else {
/*  49 */       resolveDisguisedMessage($$2, $$4, PlayerChatMessage.system($$3.text).withUnsignedContent($$5));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void resolveSignedMessage(Consumer<PlayerChatMessage> $$0, CommandSourceStack $$1, PlayerChatMessage $$2) {
/*  54 */     MinecraftServer $$3 = $$1.getServer();
/*  55 */     CompletableFuture<FilteredText> $$4 = filterPlainText($$1, $$2);
/*  56 */     Component $$5 = $$3.getChatDecorator().decorate($$1.getPlayer(), $$2.decoratedContent());
/*     */     
/*  58 */     $$1.getChatMessageChainer().append($$4, $$3 -> {
/*     */           PlayerChatMessage $$4 = $$0.withUnsignedContent($$1).filter($$3.mask());
/*     */           $$2.accept($$4);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void resolveDisguisedMessage(Consumer<PlayerChatMessage> $$0, CommandSourceStack $$1, PlayerChatMessage $$2) {
/*  67 */     ChatDecorator $$3 = $$1.getServer().getChatDecorator();
/*  68 */     Component $$4 = $$3.decorate($$1.getPlayer(), $$2.decoratedContent());
/*  69 */     $$0.accept($$2.withUnsignedContent($$4));
/*     */   }
/*     */   
/*     */   private static CompletableFuture<FilteredText> filterPlainText(CommandSourceStack $$0, PlayerChatMessage $$1) {
/*  73 */     ServerPlayer $$2 = $$0.getPlayer();
/*  74 */     if ($$2 != null && $$1.hasSignatureFrom($$2.getUUID())) {
/*  75 */       return $$2.getTextFilter().processStreamMessage($$1.signedContent());
/*     */     }
/*  77 */     return CompletableFuture.completedFuture(FilteredText.passThrough($$1.signedContent()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Message parse(StringReader $$0) throws CommandSyntaxException {
/*  83 */     return Message.parseText($$0, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/*  88 */     return EXAMPLES;
/*     */   }
/*     */   
/*     */   public static class Message {
/*     */     final String text;
/*     */     private final MessageArgument.Part[] parts;
/*     */     
/*     */     public Message(String $$0, MessageArgument.Part[] $$1) {
/*  96 */       this.text = $$0;
/*  97 */       this.parts = $$1;
/*     */     }
/*     */     
/*     */     public String getText() {
/* 101 */       return this.text;
/*     */     }
/*     */     
/*     */     public MessageArgument.Part[] getParts() {
/* 105 */       return this.parts;
/*     */     }
/*     */     
/*     */     Component resolveComponent(CommandSourceStack $$0) throws CommandSyntaxException {
/* 109 */       return toComponent($$0, $$0.hasPermission(2));
/*     */     }
/*     */     
/*     */     public Component toComponent(CommandSourceStack $$0, boolean $$1) throws CommandSyntaxException {
/* 113 */       if (this.parts.length == 0 || !$$1) {
/* 114 */         return (Component)Component.literal(this.text);
/*     */       }
/*     */       
/* 117 */       MutableComponent $$2 = Component.literal(this.text.substring(0, this.parts[0].getStart()));
/* 118 */       int $$3 = this.parts[0].getStart();
/*     */       
/* 120 */       for (MessageArgument.Part $$4 : this.parts) {
/* 121 */         Component $$5 = $$4.toComponent($$0);
/* 122 */         if ($$3 < $$4.getStart()) {
/* 123 */           $$2.append(this.text.substring($$3, $$4.getStart()));
/*     */         }
/* 125 */         if ($$5 != null) {
/* 126 */           $$2.append($$5);
/*     */         }
/* 128 */         $$3 = $$4.getEnd();
/*     */       } 
/*     */       
/* 131 */       if ($$3 < this.text.length()) {
/* 132 */         $$2.append(this.text.substring($$3));
/*     */       }
/*     */       
/* 135 */       return (Component)$$2;
/*     */     }
/*     */     
/*     */     public static Message parseText(StringReader $$0, boolean $$1) throws CommandSyntaxException {
/* 139 */       String $$2 = $$0.getString().substring($$0.getCursor(), $$0.getTotalLength());
/*     */       
/* 141 */       if (!$$1) {
/* 142 */         $$0.setCursor($$0.getTotalLength());
/* 143 */         return new Message($$2, new MessageArgument.Part[0]);
/*     */       } 
/*     */       
/* 146 */       List<MessageArgument.Part> $$3 = Lists.newArrayList();
/* 147 */       int $$4 = $$0.getCursor();
/*     */       
/* 149 */       while ($$0.canRead()) {
/* 150 */         if ($$0.peek() == '@') {
/* 151 */           EntitySelector $$7; int $$5 = $$0.getCursor();
/*     */           
/*     */           try {
/* 154 */             EntitySelectorParser $$6 = new EntitySelectorParser($$0);
/* 155 */             $$7 = $$6.parse();
/* 156 */           } catch (CommandSyntaxException $$8) {
/* 157 */             if ($$8.getType() == EntitySelectorParser.ERROR_MISSING_SELECTOR_TYPE || $$8.getType() == EntitySelectorParser.ERROR_UNKNOWN_SELECTOR_TYPE) {
/* 158 */               $$0.setCursor($$5 + 1);
/*     */               continue;
/*     */             } 
/* 161 */             throw $$8;
/*     */           } 
/* 163 */           $$3.add(new MessageArgument.Part($$5 - $$4, $$0.getCursor() - $$4, $$7)); continue;
/*     */         } 
/* 165 */         $$0.skip();
/*     */       } 
/*     */ 
/*     */       
/* 169 */       return new Message($$2, $$3.<MessageArgument.Part>toArray(new MessageArgument.Part[0]));
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Part {
/*     */     private final int start;
/*     */     private final int end;
/*     */     private final EntitySelector selector;
/*     */     
/*     */     public Part(int $$0, int $$1, EntitySelector $$2) {
/* 179 */       this.start = $$0;
/* 180 */       this.end = $$1;
/* 181 */       this.selector = $$2;
/*     */     }
/*     */     
/*     */     public int getStart() {
/* 185 */       return this.start;
/*     */     }
/*     */     
/*     */     public int getEnd() {
/* 189 */       return this.end;
/*     */     }
/*     */     
/*     */     public EntitySelector getSelector() {
/* 193 */       return this.selector;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Component toComponent(CommandSourceStack $$0) throws CommandSyntaxException {
/* 198 */       return EntitySelector.joinNames(this.selector.findEntities($$0));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\MessageArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */