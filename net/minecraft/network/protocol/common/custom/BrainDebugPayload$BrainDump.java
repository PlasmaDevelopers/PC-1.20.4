/*     */ package net.minecraft.network.protocol.common.custom;
/*     */ 
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ 
/*     */ public final class BrainDump extends Record {
/*     */   private final UUID uuid;
/*     */   private final int id;
/*     */   private final String name;
/*     */   private final String profession;
/*     */   private final int xp;
/*     */   private final float health;
/*     */   private final float maxHealth;
/*     */   private final Vec3 pos;
/*     */   private final String inventory;
/*     */   @Nullable
/*     */   private final Path path;
/*     */   private final boolean wantsGolem;
/*     */   private final int angerLevel;
/*     */   private final List<String> activities;
/*     */   private final List<String> behaviors;
/*     */   private final List<String> memories;
/*     */   private final List<String> gossips;
/*     */   private final Set<BlockPos> pois;
/*     */   private final Set<BlockPos> potentialPois;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/BrainDebugPayload$BrainDump;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #34	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/BrainDebugPayload$BrainDump;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/BrainDebugPayload$BrainDump;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #34	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/BrainDebugPayload$BrainDump;
/*     */   }
/*     */   
/*  34 */   public BrainDump(UUID $$0, int $$1, String $$2, String $$3, int $$4, float $$5, float $$6, Vec3 $$7, String $$8, @Nullable Path $$9, boolean $$10, int $$11, List<String> $$12, List<String> $$13, List<String> $$14, List<String> $$15, Set<BlockPos> $$16, Set<BlockPos> $$17) { this.uuid = $$0; this.id = $$1; this.name = $$2; this.profession = $$3; this.xp = $$4; this.health = $$5; this.maxHealth = $$6; this.pos = $$7; this.inventory = $$8; this.path = $$9; this.wantsGolem = $$10; this.angerLevel = $$11; this.activities = $$12; this.behaviors = $$13; this.memories = $$14; this.gossips = $$15; this.pois = $$16; this.potentialPois = $$17; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/BrainDebugPayload$BrainDump;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #34	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/BrainDebugPayload$BrainDump;
/*  34 */     //   0	8	1	$$0	Ljava/lang/Object; } public UUID uuid() { return this.uuid; } public int id() { return this.id; } public String name() { return this.name; } public String profession() { return this.profession; } public int xp() { return this.xp; } public float health() { return this.health; } public float maxHealth() { return this.maxHealth; } public Vec3 pos() { return this.pos; } public String inventory() { return this.inventory; } @Nullable public Path path() { return this.path; } public boolean wantsGolem() { return this.wantsGolem; } public int angerLevel() { return this.angerLevel; } public List<String> activities() { return this.activities; } public List<String> behaviors() { return this.behaviors; } public List<String> memories() { return this.memories; } public List<String> gossips() { return this.gossips; } public Set<BlockPos> pois() { return this.pois; } public Set<BlockPos> potentialPois() { return this.potentialPois; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BrainDump(FriendlyByteBuf $$0) {
/*  55 */     this($$0
/*  56 */         .readUUID(), $$0
/*  57 */         .readInt(), $$0
/*  58 */         .readUtf(), $$0
/*  59 */         .readUtf(), $$0
/*  60 */         .readInt(), $$0
/*  61 */         .readFloat(), $$0
/*  62 */         .readFloat(), $$0
/*  63 */         .readVec3(), $$0
/*  64 */         .readUtf(), (Path)$$0
/*  65 */         .readNullable(Path::createFromStream), $$0
/*  66 */         .readBoolean(), $$0
/*  67 */         .readInt(), $$0
/*  68 */         .readList(FriendlyByteBuf::readUtf), $$0
/*  69 */         .readList(FriendlyByteBuf::readUtf), $$0
/*  70 */         .readList(FriendlyByteBuf::readUtf), $$0
/*  71 */         .readList(FriendlyByteBuf::readUtf), (Set<BlockPos>)$$0
/*  72 */         .readCollection(java.util.HashSet::new, FriendlyByteBuf::readBlockPos), (Set<BlockPos>)$$0
/*  73 */         .readCollection(java.util.HashSet::new, FriendlyByteBuf::readBlockPos));
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  78 */     $$0.writeUUID(this.uuid);
/*  79 */     $$0.writeInt(this.id);
/*  80 */     $$0.writeUtf(this.name);
/*  81 */     $$0.writeUtf(this.profession);
/*  82 */     $$0.writeInt(this.xp);
/*  83 */     $$0.writeFloat(this.health);
/*  84 */     $$0.writeFloat(this.maxHealth);
/*  85 */     $$0.writeVec3(this.pos);
/*  86 */     $$0.writeUtf(this.inventory);
/*  87 */     $$0.writeNullable(this.path, ($$0, $$1) -> $$1.writeToStream($$0));
/*  88 */     $$0.writeBoolean(this.wantsGolem);
/*  89 */     $$0.writeInt(this.angerLevel);
/*  90 */     $$0.writeCollection(this.activities, FriendlyByteBuf::writeUtf);
/*  91 */     $$0.writeCollection(this.behaviors, FriendlyByteBuf::writeUtf);
/*  92 */     $$0.writeCollection(this.memories, FriendlyByteBuf::writeUtf);
/*  93 */     $$0.writeCollection(this.gossips, FriendlyByteBuf::writeUtf);
/*  94 */     $$0.writeCollection(this.pois, FriendlyByteBuf::writeBlockPos);
/*  95 */     $$0.writeCollection(this.potentialPois, FriendlyByteBuf::writeBlockPos);
/*     */   }
/*     */   
/*     */   public boolean hasPoi(BlockPos $$0) {
/*  99 */     return this.pois.contains($$0);
/*     */   }
/*     */   
/*     */   public boolean hasPotentialPoi(BlockPos $$0) {
/* 103 */     return this.potentialPois.contains($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\BrainDebugPayload$BrainDump.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */