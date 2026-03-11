/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.TagParser;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
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
/*     */ public class ItemStackInfo
/*     */ {
/*     */   public static final Codec<ItemStackInfo> FULL_CODEC;
/*     */   public static final Codec<ItemStackInfo> CODEC;
/*     */   private final Item item;
/*     */   private final int count;
/*     */   private final Optional<CompoundTag> tag;
/*     */   @Nullable
/*     */   private ItemStack itemStack;
/*     */   
/*     */   static {
/* 148 */     FULL_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.ITEM.byNameCodec().fieldOf("id").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.INT, "count", Integer.valueOf(1)).forGetter(()), (App)ExtraCodecs.strictOptionalField(TagParser.AS_CODEC, "tag").forGetter(())).apply((Applicative)$$0, ItemStackInfo::new));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     CODEC = Codec.either(BuiltInRegistries.ITEM.byNameCodec(), FULL_CODEC).xmap($$0 -> (ItemStackInfo)$$0.map((), ()), Either::right);
/*     */   }
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
/*     */   ItemStackInfo(Item $$0, int $$1, @Nullable CompoundTag $$2) {
/* 170 */     this($$0, $$1, Optional.ofNullable($$2));
/*     */   }
/*     */   
/*     */   ItemStackInfo(Item $$0, int $$1, Optional<CompoundTag> $$2) {
/* 174 */     this.item = $$0;
/* 175 */     this.count = $$1;
/* 176 */     this.tag = $$2;
/*     */   }
/*     */   
/*     */   public ItemStackInfo(ItemStack $$0) {
/* 180 */     this($$0.getItem(), $$0.getCount(), ($$0.getTag() != null) ? Optional.<CompoundTag>of($$0.getTag().copy()) : Optional.<CompoundTag>empty());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 185 */     if (this == $$0) {
/* 186 */       return true;
/*     */     }
/* 188 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 189 */       return false;
/*     */     }
/*     */     
/* 192 */     ItemStackInfo $$1 = (ItemStackInfo)$$0;
/* 193 */     return (this.count == $$1.count && this.item.equals($$1.item) && this.tag.equals($$1.tag));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 198 */     int $$0 = this.item.hashCode();
/* 199 */     $$0 = 31 * $$0 + this.count;
/* 200 */     $$0 = 31 * $$0 + this.tag.hashCode();
/* 201 */     return $$0;
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack() {
/* 205 */     if (this.itemStack == null) {
/* 206 */       this.itemStack = new ItemStack((ItemLike)this.item, this.count);
/* 207 */       Objects.requireNonNull(this.itemStack); this.tag.ifPresent(this.itemStack::setTag);
/*     */     } 
/* 209 */     return this.itemStack;
/*     */   }
/*     */   
/*     */   private static DataResult<ItemStackInfo> legacyCreate(Component $$0) {
/*     */     try {
/* 214 */       CompoundTag $$1 = TagParser.parseTag($$0.getString());
/* 215 */       return DataResult.success(new ItemStackInfo(ItemStack.of($$1)));
/* 216 */     } catch (CommandSyntaxException $$2) {
/* 217 */       return DataResult.error(() -> "Failed to parse item tag: " + $$0.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\HoverEvent$ItemStackInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */