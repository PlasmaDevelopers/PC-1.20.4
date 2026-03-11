/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.RandomizableContainer;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.ticks.ContainerSingleItem;
/*     */ 
/*     */ public class DecoratedPotBlockEntity extends BlockEntity implements RandomizableContainer, ContainerSingleItem {
/*     */   public static final String TAG_SHERDS = "sherds";
/*     */   public static final String TAG_ITEM = "item";
/*     */   public static final int EVENT_POT_WOBBLES = 1;
/*     */   public long wobbleStartedAtTick;
/*     */   @Nullable
/*     */   public WobbleStyle lastWobbleStyle;
/*     */   private Decorations decorations;
/*  33 */   private ItemStack item = ItemStack.EMPTY;
/*     */   @Nullable
/*     */   protected ResourceLocation lootTable;
/*     */   protected long lootTableSeed;
/*     */   
/*     */   public DecoratedPotBlockEntity(BlockPos $$0, BlockState $$1) {
/*  39 */     super(BlockEntityType.DECORATED_POT, $$0, $$1);
/*  40 */     this.decorations = Decorations.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  45 */     super.saveAdditional($$0);
/*  46 */     this.decorations.save($$0);
/*     */     
/*  48 */     if (!trySaveLootTable($$0) && 
/*  49 */       !this.item.isEmpty()) {
/*  50 */       $$0.put("item", (Tag)this.item.save(new CompoundTag()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  57 */     super.load($$0);
/*  58 */     this.decorations = Decorations.load($$0);
/*     */     
/*  60 */     if (!tryLoadLootTable($$0)) {
/*  61 */       if ($$0.contains("item", 10)) {
/*  62 */         this.item = ItemStack.of($$0.getCompound("item"));
/*     */       } else {
/*  64 */         this.item = ItemStack.EMPTY;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/*  71 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/*  76 */     return saveWithoutMetadata();
/*     */   }
/*     */   
/*     */   public Direction getDirection() {
/*  80 */     return (Direction)getBlockState().getValue((Property)BlockStateProperties.HORIZONTAL_FACING);
/*     */   }
/*     */   
/*     */   public Decorations getDecorations() {
/*  84 */     return this.decorations;
/*     */   }
/*     */   
/*     */   public void setFromItem(ItemStack $$0) {
/*  88 */     this.decorations = Decorations.load(BlockItem.getBlockEntityData($$0));
/*     */   }
/*     */   
/*     */   public ItemStack getPotAsItem() {
/*  92 */     return createDecoratedPotItem(this.decorations);
/*     */   }
/*     */   
/*     */   public static ItemStack createDecoratedPotItem(Decorations $$0) {
/*  96 */     ItemStack $$1 = Items.DECORATED_POT.getDefaultInstance();
/*  97 */     CompoundTag $$2 = $$0.save(new CompoundTag());
/*  98 */     BlockItem.setBlockEntityData($$1, BlockEntityType.DECORATED_POT, $$2);
/*  99 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getLootTable() {
/* 105 */     return this.lootTable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootTable(@Nullable ResourceLocation $$0) {
/* 110 */     this.lootTable = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLootTableSeed() {
/* 115 */     return this.lootTableSeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootTableSeed(long $$0) {
/* 120 */     this.lootTableSeed = $$0;
/*     */   }
/*     */   public static final class Decorations extends Record { private final Item back; private final Item left; private final Item right; private final Item front;
/* 123 */     public Decorations(Item $$0, Item $$1, Item $$2, Item $$3) { this.back = $$0; this.left = $$1; this.right = $$2; this.front = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #123	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 123 */       //   0	7	0	this	Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations; } public Item back() { return this.back; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #123	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #123	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations;
/* 123 */       //   0	8	1	$$0	Ljava/lang/Object; } public Item left() { return this.left; } public Item right() { return this.right; } public Item front() { return this.front; }
/* 124 */      public static final Decorations EMPTY = new Decorations(Items.BRICK, Items.BRICK, Items.BRICK, Items.BRICK);
/*     */     
/*     */     public CompoundTag save(CompoundTag $$0) {
/* 127 */       if (equals(EMPTY)) {
/* 128 */         return $$0;
/*     */       }
/* 130 */       ListTag $$1 = new ListTag();
/* 131 */       sorted().forEach($$1 -> $$0.add(StringTag.valueOf(BuiltInRegistries.ITEM.getKey($$1).toString())));
/* 132 */       $$0.put("sherds", (Tag)$$1);
/* 133 */       return $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<Item> sorted() {
/* 138 */       return Stream.of(new Item[] { this.back, this.left, this.right, this.front });
/*     */     }
/*     */     
/*     */     public static Decorations load(@Nullable CompoundTag $$0) {
/* 142 */       if ($$0 == null || !$$0.contains("sherds", 9)) {
/* 143 */         return EMPTY;
/*     */       }
/*     */       
/* 146 */       ListTag $$1 = $$0.getList("sherds", 8);
/*     */       
/* 148 */       return new Decorations(
/* 149 */           itemFromTag($$1, 0), 
/* 150 */           itemFromTag($$1, 1), 
/* 151 */           itemFromTag($$1, 2), 
/* 152 */           itemFromTag($$1, 3));
/*     */     }
/*     */ 
/*     */     
/*     */     private static Item itemFromTag(ListTag $$0, int $$1) {
/* 157 */       if ($$1 >= $$0.size()) {
/* 158 */         return Items.BRICK;
/*     */       }
/* 160 */       Tag $$2 = $$0.get($$1);
/* 161 */       return (Item)BuiltInRegistries.ITEM.get(ResourceLocation.tryParse($$2.getAsString()));
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getTheItem() {
/* 167 */     unpackLootTable(null);
/* 168 */     return this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitTheItem(int $$0) {
/* 173 */     unpackLootTable(null);
/* 174 */     ItemStack $$1 = this.item.split($$0);
/* 175 */     if (this.item.isEmpty()) {
/* 176 */       this.item = ItemStack.EMPTY;
/*     */     }
/* 178 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTheItem(ItemStack $$0) {
/* 183 */     unpackLootTable(null);
/* 184 */     this.item = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity getContainerBlockEntity() {
/* 189 */     return this;
/*     */   }
/*     */   
/*     */   public void wobble(WobbleStyle $$0) {
/* 193 */     if (this.level == null || this.level.isClientSide()) {
/*     */       return;
/*     */     }
/* 196 */     this.level.blockEvent(getBlockPos(), getBlockState().getBlock(), 1, $$0.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean triggerEvent(int $$0, int $$1) {
/* 201 */     if (this.level != null && $$0 == 1 && $$1 >= 0 && $$1 < (WobbleStyle.values()).length) {
/* 202 */       this.wobbleStartedAtTick = this.level.getGameTime();
/* 203 */       this.lastWobbleStyle = WobbleStyle.values()[$$1];
/* 204 */       return true;
/*     */     } 
/* 206 */     return super.triggerEvent($$0, $$1);
/*     */   }
/*     */   
/*     */   public enum WobbleStyle {
/* 210 */     POSITIVE(7),
/* 211 */     NEGATIVE(10);
/*     */     
/*     */     public final int duration;
/*     */     
/*     */     WobbleStyle(int $$0) {
/* 216 */       this.duration = $$0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\DecoratedPotBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */