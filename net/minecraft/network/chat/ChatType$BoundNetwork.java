/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BoundNetwork
/*     */   extends Record
/*     */ {
/*     */   private final int chatType;
/*     */   private final Component name;
/*     */   @Nullable
/*     */   private final Component targetName;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/ChatType$BoundNetwork;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #111	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/chat/ChatType$BoundNetwork;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/ChatType$BoundNetwork;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #111	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/chat/ChatType$BoundNetwork;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/ChatType$BoundNetwork;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #111	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/network/chat/ChatType$BoundNetwork;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public BoundNetwork(int $$0, Component $$1, @Nullable Component $$2) {
/* 111 */     this.chatType = $$0; this.name = $$1; this.targetName = $$2; } public int chatType() { return this.chatType; } public Component name() { return this.name; } @Nullable public Component targetName() { return this.targetName; }
/*     */    public BoundNetwork(FriendlyByteBuf $$0) {
/* 113 */     this($$0.readVarInt(), $$0.readComponentTrusted(), (Component)$$0.readNullable(FriendlyByteBuf::readComponentTrusted));
/*     */   }
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/* 117 */     $$0.writeVarInt(this.chatType);
/* 118 */     $$0.writeComponent(this.name);
/* 119 */     $$0.writeNullable(this.targetName, FriendlyByteBuf::writeComponent);
/*     */   }
/*     */   
/*     */   public Optional<ChatType.Bound> resolve(RegistryAccess $$0) {
/* 123 */     Registry<ChatType> $$1 = $$0.registryOrThrow(Registries.CHAT_TYPE);
/* 124 */     ChatType $$2 = (ChatType)$$1.byId(this.chatType);
/* 125 */     return Optional.<ChatType>ofNullable($$2).map($$0 -> new ChatType.Bound($$0, this.name, this.targetName));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ChatType$BoundNetwork.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */