/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSource;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Clearable;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ContainerData;
/*     */ import net.minecraft.world.inventory.LecternMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.WrittenBookItem;
/*     */ import net.minecraft.world.level.block.LecternBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class LecternBlockEntity
/*     */   extends BlockEntity implements Clearable, MenuProvider {
/*     */   public static final int DATA_PAGE = 0;
/*     */   public static final int NUM_DATA = 1;
/*     */   public static final int SLOT_BOOK = 0;
/*     */   public static final int NUM_SLOTS = 1;
/*     */   
/*  38 */   private final Container bookAccess = new Container()
/*     */     {
/*     */       public int getContainerSize() {
/*  41 */         return 1;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isEmpty() {
/*  46 */         return LecternBlockEntity.this.book.isEmpty();
/*     */       }
/*     */ 
/*     */       
/*     */       public ItemStack getItem(int $$0) {
/*  51 */         return ($$0 == 0) ? LecternBlockEntity.this.book : ItemStack.EMPTY;
/*     */       }
/*     */ 
/*     */       
/*     */       public ItemStack removeItem(int $$0, int $$1) {
/*  56 */         if ($$0 == 0) {
/*  57 */           ItemStack $$2 = LecternBlockEntity.this.book.split($$1);
/*  58 */           if (LecternBlockEntity.this.book.isEmpty()) {
/*  59 */             LecternBlockEntity.this.onBookItemRemove();
/*     */           }
/*  61 */           return $$2;
/*     */         } 
/*  63 */         return ItemStack.EMPTY;
/*     */       }
/*     */ 
/*     */       
/*     */       public ItemStack removeItemNoUpdate(int $$0) {
/*  68 */         if ($$0 == 0) {
/*  69 */           ItemStack $$1 = LecternBlockEntity.this.book;
/*  70 */           LecternBlockEntity.this.book = ItemStack.EMPTY;
/*  71 */           LecternBlockEntity.this.onBookItemRemove();
/*  72 */           return $$1;
/*     */         } 
/*  74 */         return ItemStack.EMPTY;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void setItem(int $$0, ItemStack $$1) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public int getMaxStackSize() {
/*  84 */         return 1;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setChanged() {
/*  89 */         LecternBlockEntity.this.setChanged();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean stillValid(Player $$0) {
/*  94 */         return (Container.stillValidBlockEntity(LecternBlockEntity.this, $$0) && LecternBlockEntity.this.hasBook());
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean canPlaceItem(int $$0, ItemStack $$1) {
/*  99 */         return false;
/*     */       }
/*     */ 
/*     */       
/*     */       public void clearContent() {}
/*     */     };
/*     */ 
/*     */   
/* 107 */   private final ContainerData dataAccess = new ContainerData()
/*     */     {
/*     */       public int get(int $$0) {
/* 110 */         return ($$0 == 0) ? LecternBlockEntity.this.page : 0;
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(int $$0, int $$1) {
/* 115 */         if ($$0 == 0) {
/* 116 */           LecternBlockEntity.this.setPage($$1);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public int getCount() {
/* 122 */         return 1;
/*     */       }
/*     */     };
/*     */   
/* 126 */   ItemStack book = ItemStack.EMPTY;
/*     */   int page;
/*     */   private int pageCount;
/*     */   
/*     */   public LecternBlockEntity(BlockPos $$0, BlockState $$1) {
/* 131 */     super(BlockEntityType.LECTERN, $$0, $$1);
/*     */   }
/*     */   
/*     */   public ItemStack getBook() {
/* 135 */     return this.book;
/*     */   }
/*     */   
/*     */   public boolean hasBook() {
/* 139 */     return (this.book.is(Items.WRITABLE_BOOK) || this.book.is(Items.WRITTEN_BOOK));
/*     */   }
/*     */   
/*     */   public void setBook(ItemStack $$0) {
/* 143 */     setBook($$0, (Player)null);
/*     */   }
/*     */   
/*     */   void onBookItemRemove() {
/* 147 */     this.page = 0;
/* 148 */     this.pageCount = 0;
/* 149 */     LecternBlock.resetBookState(null, getLevel(), getBlockPos(), getBlockState(), false);
/*     */   }
/*     */   
/*     */   public void setBook(ItemStack $$0, @Nullable Player $$1) {
/* 153 */     this.book = resolveBook($$0, $$1);
/* 154 */     this.page = 0;
/* 155 */     this.pageCount = WrittenBookItem.getPageCount(this.book);
/* 156 */     setChanged();
/*     */   }
/*     */   
/*     */   void setPage(int $$0) {
/* 160 */     int $$1 = Mth.clamp($$0, 0, this.pageCount - 1);
/* 161 */     if ($$1 != this.page) {
/* 162 */       this.page = $$1;
/* 163 */       setChanged();
/* 164 */       LecternBlock.signalPageChange(getLevel(), getBlockPos(), getBlockState());
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getPage() {
/* 169 */     return this.page;
/*     */   }
/*     */   
/*     */   public int getRedstoneSignal() {
/* 173 */     float $$0 = (this.pageCount > 1) ? (getPage() / (this.pageCount - 1.0F)) : 1.0F;
/* 174 */     return Mth.floor($$0 * 14.0F) + (hasBook() ? 1 : 0);
/*     */   }
/*     */   
/*     */   private ItemStack resolveBook(ItemStack $$0, @Nullable Player $$1) {
/* 178 */     if (this.level instanceof ServerLevel && $$0.is(Items.WRITTEN_BOOK)) {
/* 179 */       WrittenBookItem.resolveBookComponents($$0, createCommandSourceStack($$1), $$1);
/*     */     }
/* 181 */     return $$0;
/*     */   }
/*     */   
/*     */   private CommandSourceStack createCommandSourceStack(@Nullable Player $$0) {
/*     */     String $$3;
/*     */     Component $$4;
/* 187 */     if ($$0 == null) {
/* 188 */       String $$1 = "Lectern";
/* 189 */       MutableComponent mutableComponent = Component.literal("Lectern");
/*     */     } else {
/* 191 */       $$3 = $$0.getName().getString();
/* 192 */       $$4 = $$0.getDisplayName();
/*     */     } 
/* 194 */     Vec3 $$5 = Vec3.atCenterOf((Vec3i)this.worldPosition);
/* 195 */     return new CommandSourceStack(CommandSource.NULL, $$5, Vec2.ZERO, (ServerLevel)this.level, 2, $$3, $$4, this.level.getServer(), (Entity)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onlyOpCanSetNbt() {
/* 200 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 205 */     super.load($$0);
/*     */     
/* 207 */     if ($$0.contains("Book", 10)) {
/* 208 */       this.book = resolveBook(ItemStack.of($$0.getCompound("Book")), (Player)null);
/*     */     } else {
/* 210 */       this.book = ItemStack.EMPTY;
/*     */     } 
/*     */     
/* 213 */     this.pageCount = WrittenBookItem.getPageCount(this.book);
/* 214 */     this.page = Mth.clamp($$0.getInt("Page"), 0, this.pageCount - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 219 */     super.saveAdditional($$0);
/*     */     
/* 221 */     if (!getBook().isEmpty()) {
/* 222 */       $$0.put("Book", (Tag)getBook().save(new CompoundTag()));
/* 223 */       $$0.putInt("Page", this.page);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 229 */     setBook(ItemStack.EMPTY);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractContainerMenu createMenu(int $$0, Inventory $$1, Player $$2) {
/* 234 */     return (AbstractContainerMenu)new LecternMenu($$0, this.bookAccess, this.dataAccess);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getDisplayName() {
/* 239 */     return (Component)Component.translatable("container.lectern");
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\LecternBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */