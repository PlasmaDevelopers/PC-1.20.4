/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Bound
/*     */   extends Record
/*     */ {
/*     */   private final ChatType chatType;
/*     */   private final Component name;
/*     */   @Nullable
/*     */   private final Component targetName;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/ChatType$Bound;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #88	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/chat/ChatType$Bound;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/ChatType$Bound;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #88	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/chat/ChatType$Bound;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/ChatType$Bound;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #88	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/network/chat/ChatType$Bound;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public Bound(ChatType $$0, Component $$1, @Nullable Component $$2) {
/*  88 */     this.chatType = $$0; this.name = $$1; this.targetName = $$2; } public ChatType chatType() { return this.chatType; } public Component name() { return this.name; } @Nullable public Component targetName() { return this.targetName; }
/*     */    Bound(ChatType $$0, Component $$1) {
/*  90 */     this($$0, $$1, null);
/*     */   }
/*     */   
/*     */   public Component decorate(Component $$0) {
/*  94 */     return this.chatType.chat().decorate($$0, this);
/*     */   }
/*     */   
/*     */   public Component decorateNarration(Component $$0) {
/*  98 */     return this.chatType.narration().decorate($$0, this);
/*     */   }
/*     */   
/*     */   public Bound withTargetName(Component $$0) {
/* 102 */     return new Bound(this.chatType, this.name, $$0);
/*     */   }
/*     */   
/*     */   public ChatType.BoundNetwork toNetwork(RegistryAccess $$0) {
/* 106 */     Registry<ChatType> $$1 = $$0.registryOrThrow(Registries.CHAT_TYPE);
/* 107 */     return new ChatType.BoundNetwork($$1.getId(this.chatType), this.name, this.targetName);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ChatType$Bound.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */