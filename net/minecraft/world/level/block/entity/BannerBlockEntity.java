/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.world.Nameable;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.AbstractBannerBlock;
/*     */ import net.minecraft.world.level.block.BannerBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class BannerBlockEntity
/*     */   extends BlockEntity
/*     */   implements Nameable {
/*     */   public static final int MAX_PATTERNS = 6;
/*     */   public static final String TAG_PATTERNS = "Patterns";
/*     */   public static final String TAG_PATTERN = "Pattern";
/*     */   public static final String TAG_COLOR = "Color";
/*     */   @Nullable
/*     */   private Component name;
/*     */   private DyeColor baseColor;
/*     */   @Nullable
/*     */   private ListTag itemPatterns;
/*     */   @Nullable
/*     */   private List<Pair<Holder<BannerPattern>, DyeColor>> patterns;
/*     */   
/*     */   public BannerBlockEntity(BlockPos $$0, BlockState $$1) {
/*  41 */     super(BlockEntityType.BANNER, $$0, $$1);
/*  42 */     this.baseColor = ((AbstractBannerBlock)$$1.getBlock()).getColor();
/*     */   }
/*     */   
/*     */   public BannerBlockEntity(BlockPos $$0, BlockState $$1, DyeColor $$2) {
/*  46 */     this($$0, $$1);
/*  47 */     this.baseColor = $$2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ListTag getItemPatterns(ItemStack $$0) {
/*  52 */     ListTag $$1 = null;
/*  53 */     CompoundTag $$2 = BlockItem.getBlockEntityData($$0);
/*  54 */     if ($$2 != null && $$2.contains("Patterns", 9)) {
/*  55 */       $$1 = $$2.getList("Patterns", 10).copy();
/*     */     }
/*  57 */     return $$1;
/*     */   }
/*     */   
/*     */   public void fromItem(ItemStack $$0, DyeColor $$1) {
/*  61 */     this.baseColor = $$1;
/*  62 */     fromItem($$0);
/*     */   }
/*     */   
/*     */   public void fromItem(ItemStack $$0) {
/*  66 */     this.itemPatterns = getItemPatterns($$0);
/*  67 */     this.patterns = null;
/*  68 */     this.name = $$0.hasCustomHoverName() ? $$0.getHoverName() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getName() {
/*  73 */     if (this.name != null) {
/*  74 */       return this.name;
/*     */     }
/*  76 */     return (Component)Component.translatable("block.minecraft.banner");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component getCustomName() {
/*  82 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setCustomName(Component $$0) {
/*  86 */     this.name = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  91 */     super.saveAdditional($$0);
/*     */     
/*  93 */     if (this.itemPatterns != null) {
/*  94 */       $$0.put("Patterns", (Tag)this.itemPatterns);
/*     */     }
/*     */     
/*  97 */     if (this.name != null) {
/*  98 */       $$0.putString("CustomName", Component.Serializer.toJson(this.name));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 104 */     super.load($$0);
/*     */     
/* 106 */     if ($$0.contains("CustomName", 8)) {
/* 107 */       this.name = (Component)Component.Serializer.fromJson($$0.getString("CustomName"));
/*     */     }
/*     */     
/* 110 */     this.itemPatterns = $$0.getList("Patterns", 10);
/*     */     
/* 112 */     this.patterns = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 117 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/* 122 */     return saveWithoutMetadata();
/*     */   }
/*     */   
/*     */   public static int getPatternCount(ItemStack $$0) {
/* 126 */     CompoundTag $$1 = BlockItem.getBlockEntityData($$0);
/* 127 */     if ($$1 != null && $$1.contains("Patterns")) {
/* 128 */       return $$1.getList("Patterns", 10).size();
/*     */     }
/* 130 */     return 0;
/*     */   }
/*     */   
/*     */   public List<Pair<Holder<BannerPattern>, DyeColor>> getPatterns() {
/* 134 */     if (this.patterns == null) {
/* 135 */       this.patterns = createPatterns(this.baseColor, this.itemPatterns);
/*     */     }
/*     */     
/* 138 */     return this.patterns;
/*     */   }
/*     */   
/*     */   public static List<Pair<Holder<BannerPattern>, DyeColor>> createPatterns(DyeColor $$0, @Nullable ListTag $$1) {
/* 142 */     List<Pair<Holder<BannerPattern>, DyeColor>> $$2 = Lists.newArrayList();
/*     */     
/* 144 */     $$2.add(Pair.of(BuiltInRegistries.BANNER_PATTERN.getHolderOrThrow(BannerPatterns.BASE), $$0));
/*     */     
/* 146 */     if ($$1 != null) {
/* 147 */       for (int $$3 = 0; $$3 < $$1.size(); $$3++) {
/* 148 */         CompoundTag $$4 = $$1.getCompound($$3);
/* 149 */         Holder<BannerPattern> $$5 = BannerPattern.byHash($$4.getString("Pattern"));
/* 150 */         if ($$5 != null) {
/* 151 */           int $$6 = $$4.getInt("Color");
/* 152 */           $$2.add(Pair.of($$5, DyeColor.byId($$6)));
/*     */         } 
/*     */       } 
/*     */     }
/* 156 */     return $$2;
/*     */   }
/*     */   
/*     */   public static void removeLastPattern(ItemStack $$0) {
/* 160 */     CompoundTag $$1 = BlockItem.getBlockEntityData($$0);
/* 161 */     if ($$1 == null || !$$1.contains("Patterns", 9)) {
/*     */       return;
/*     */     }
/*     */     
/* 165 */     ListTag $$2 = $$1.getList("Patterns", 10);
/* 166 */     if (!$$2.isEmpty()) {
/* 167 */       $$2.remove($$2.size() - 1);
/* 168 */       if ($$2.isEmpty()) {
/* 169 */         $$1.remove("Patterns");
/*     */       }
/*     */     } 
/*     */     
/* 173 */     $$1.remove("id");
/*     */     
/* 175 */     BlockItem.setBlockEntityData($$0, BlockEntityType.BANNER, $$1);
/*     */   }
/*     */   
/*     */   public ItemStack getItem() {
/* 179 */     ItemStack $$0 = new ItemStack((ItemLike)BannerBlock.byColor(this.baseColor));
/* 180 */     if (this.itemPatterns != null && !this.itemPatterns.isEmpty()) {
/* 181 */       CompoundTag $$1 = new CompoundTag();
/* 182 */       $$1.put("Patterns", (Tag)this.itemPatterns.copy());
/* 183 */       BlockItem.setBlockEntityData($$0, getType(), $$1);
/*     */     } 
/*     */     
/* 186 */     if (this.name != null) {
/* 187 */       $$0.setHoverName(this.name);
/*     */     }
/* 189 */     return $$0;
/*     */   }
/*     */   
/*     */   public DyeColor getBaseColor() {
/* 193 */     return this.baseColor;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BannerBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */