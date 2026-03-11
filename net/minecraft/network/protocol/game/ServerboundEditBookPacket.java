/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerboundEditBookPacket
/*    */   implements Packet<ServerGamePacketListener>
/*    */ {
/*    */   public static final int MAX_BYTES_PER_CHAR = 4;
/*    */   private static final int TITLE_MAX_CHARS = 128;
/*    */   private static final int PAGE_MAX_CHARS = 8192;
/*    */   private static final int MAX_PAGES_COUNT = 200;
/*    */   private final int slot;
/*    */   private final List<String> pages;
/*    */   private final Optional<String> title;
/*    */   
/*    */   public ServerboundEditBookPacket(int $$0, List<String> $$1, Optional<String> $$2) {
/* 25 */     this.slot = $$0;
/* 26 */     this.pages = (List<String>)ImmutableList.copyOf($$1);
/* 27 */     this.title = $$2;
/*    */   }
/*    */   
/*    */   public ServerboundEditBookPacket(FriendlyByteBuf $$0) {
/* 31 */     this.slot = $$0.readVarInt();
/* 32 */     this.pages = (List<String>)$$0.readCollection(FriendlyByteBuf.limitValue(Lists::newArrayListWithCapacity, 200), $$0 -> $$0.readUtf(8192));
/* 33 */     this.title = $$0.readOptional($$0 -> $$0.readUtf(128));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 38 */     $$0.writeVarInt(this.slot);
/* 39 */     $$0.writeCollection(this.pages, ($$0, $$1) -> $$0.writeUtf($$1, 8192));
/* 40 */     $$0.writeOptional(this.title, ($$0, $$1) -> $$0.writeUtf($$1, 128));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 45 */     $$0.handleEditBook(this);
/*    */   }
/*    */   
/*    */   public List<String> getPages() {
/* 49 */     return this.pages;
/*    */   }
/*    */   
/*    */   public Optional<String> getTitle() {
/* 53 */     return this.title;
/*    */   }
/*    */   
/*    */   public int getSlot() {
/* 57 */     return this.slot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundEditBookPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */