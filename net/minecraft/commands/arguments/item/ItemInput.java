/*    */ package net.minecraft.commands.arguments.item;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ItemInput implements Predicate<ItemStack> {
/*    */   static {
/* 17 */     ERROR_STACK_TOO_BIG = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("arguments.item.overstacked", new Object[] { $$0, $$1 }));
/*    */   }
/*    */   private static final Dynamic2CommandExceptionType ERROR_STACK_TOO_BIG; private final Holder<Item> item;
/*    */   @Nullable
/*    */   private final CompoundTag tag;
/*    */   
/*    */   public ItemInput(Holder<Item> $$0, @Nullable CompoundTag $$1) {
/* 24 */     this.item = $$0;
/* 25 */     this.tag = $$1;
/*    */   }
/*    */   
/*    */   public Item getItem() {
/* 29 */     return (Item)this.item.value();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(ItemStack $$0) {
/* 34 */     return ($$0.is(this.item) && NbtUtils.compareNbt((Tag)this.tag, (Tag)$$0.getTag(), true));
/*    */   }
/*    */   
/*    */   public ItemStack createItemStack(int $$0, boolean $$1) throws CommandSyntaxException {
/* 38 */     ItemStack $$2 = new ItemStack(this.item, $$0);
/* 39 */     if (this.tag != null) {
/* 40 */       $$2.setTag(this.tag);
/*    */     }
/* 42 */     if ($$1 && $$0 > $$2.getMaxStackSize()) {
/* 43 */       throw ERROR_STACK_TOO_BIG.create(getItemName(), Integer.valueOf($$2.getMaxStackSize()));
/*    */     }
/* 45 */     return $$2;
/*    */   }
/*    */   
/*    */   public String serialize() {
/* 49 */     StringBuilder $$0 = new StringBuilder(getItemName());
/* 50 */     if (this.tag != null) {
/* 51 */       $$0.append(this.tag);
/*    */     }
/* 53 */     return $$0.toString();
/*    */   }
/*    */   
/*    */   private String getItemName() {
/* 57 */     return this.item.unwrapKey().map(ResourceKey::location).orElseGet(() -> "unknown[" + this.item + "]").toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\item\ItemInput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */