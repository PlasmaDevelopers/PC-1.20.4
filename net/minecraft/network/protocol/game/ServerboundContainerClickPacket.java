/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.inventory.ClickType;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class ServerboundContainerClickPacket
/*    */   implements Packet<ServerGamePacketListener>
/*    */ {
/*    */   private static final int MAX_SLOT_COUNT = 128;
/*    */   private final int containerId;
/*    */   private final int stateId;
/*    */   private final int slotNum;
/*    */   
/*    */   public ServerboundContainerClickPacket(int $$0, int $$1, int $$2, int $$3, ClickType $$4, ItemStack $$5, Int2ObjectMap<ItemStack> $$6) {
/* 24 */     this.containerId = $$0;
/* 25 */     this.stateId = $$1;
/* 26 */     this.slotNum = $$2;
/* 27 */     this.buttonNum = $$3;
/* 28 */     this.clickType = $$4;
/* 29 */     this.carriedItem = $$5;
/* 30 */     this.changedSlots = Int2ObjectMaps.unmodifiable($$6);
/*    */   }
/*    */   private final int buttonNum; private final ClickType clickType; private final ItemStack carriedItem; private final Int2ObjectMap<ItemStack> changedSlots;
/*    */   public ServerboundContainerClickPacket(FriendlyByteBuf $$0) {
/* 34 */     this.containerId = $$0.readByte();
/* 35 */     this.stateId = $$0.readVarInt();
/* 36 */     this.slotNum = $$0.readShort();
/* 37 */     this.buttonNum = $$0.readByte();
/* 38 */     this.clickType = (ClickType)$$0.readEnum(ClickType.class);
/* 39 */     IntFunction<Int2ObjectOpenHashMap<ItemStack>> $$1 = FriendlyByteBuf.limitValue(Int2ObjectOpenHashMap::new, 128);
/* 40 */     this.changedSlots = Int2ObjectMaps.unmodifiable((Int2ObjectMap)$$0.readMap($$1, $$0 -> Integer.valueOf($$0.readShort()), FriendlyByteBuf::readItem));
/* 41 */     this.carriedItem = $$0.readItem();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 46 */     $$0.writeByte(this.containerId);
/* 47 */     $$0.writeVarInt(this.stateId);
/* 48 */     $$0.writeShort(this.slotNum);
/* 49 */     $$0.writeByte(this.buttonNum);
/* 50 */     $$0.writeEnum((Enum)this.clickType);
/*    */     
/* 52 */     $$0.writeMap((Map)this.changedSlots, FriendlyByteBuf::writeShort, FriendlyByteBuf::writeItem);
/* 53 */     $$0.writeItem(this.carriedItem);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 58 */     $$0.handleContainerClick(this);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 62 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public int getSlotNum() {
/* 66 */     return this.slotNum;
/*    */   }
/*    */   
/*    */   public int getButtonNum() {
/* 70 */     return this.buttonNum;
/*    */   }
/*    */   
/*    */   public ItemStack getCarriedItem() {
/* 74 */     return this.carriedItem;
/*    */   }
/*    */   
/*    */   public Int2ObjectMap<ItemStack> getChangedSlots() {
/* 78 */     return this.changedSlots;
/*    */   }
/*    */   
/*    */   public ClickType getClickType() {
/* 82 */     return this.clickType;
/*    */   }
/*    */   
/*    */   public int getStateId() {
/* 86 */     return this.stateId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundContainerClickPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */