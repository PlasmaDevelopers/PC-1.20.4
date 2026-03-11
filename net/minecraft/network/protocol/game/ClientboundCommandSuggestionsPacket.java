/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.context.StringRange;
/*    */ import com.mojang.brigadier.suggestion.Suggestion;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentUtils;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundCommandSuggestionsPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int id;
/*    */   
/*    */   public ClientboundCommandSuggestionsPacket(int $$0, Suggestions $$1) {
/* 18 */     this.id = $$0;
/* 19 */     this.suggestions = $$1;
/*    */   }
/*    */   private final Suggestions suggestions;
/*    */   public ClientboundCommandSuggestionsPacket(FriendlyByteBuf $$0) {
/* 23 */     this.id = $$0.readVarInt();
/* 24 */     int $$1 = $$0.readVarInt();
/* 25 */     int $$2 = $$0.readVarInt();
/* 26 */     StringRange $$3 = StringRange.between($$1, $$1 + $$2);
/*    */     
/* 28 */     List<Suggestion> $$4 = $$0.readList($$1 -> {
/*    */           String $$2 = $$1.readUtf();
/*    */           Component $$3 = (Component)$$1.readNullable(FriendlyByteBuf::readComponentTrusted);
/*    */           return new Suggestion($$0, $$2, (Message)$$3);
/*    */         });
/* 33 */     this.suggestions = new Suggestions($$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 38 */     $$0.writeVarInt(this.id);
/* 39 */     $$0.writeVarInt(this.suggestions.getRange().getStart());
/* 40 */     $$0.writeVarInt(this.suggestions.getRange().getLength());
/*    */     
/* 42 */     $$0.writeCollection(this.suggestions.getList(), ($$0, $$1) -> {
/*    */           $$0.writeUtf($$1.getText());
/*    */           $$0.writeNullable($$1.getTooltip(), ());
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 50 */     $$0.handleCommandSuggestions(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 54 */     return this.id;
/*    */   }
/*    */   
/*    */   public Suggestions getSuggestions() {
/* 58 */     return this.suggestions;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundCommandSuggestionsPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */