/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.entity.SkullBlockEntity;
/*    */ 
/*    */ public class PlayerHeadItem
/*    */   extends StandingAndWallBlockItem {
/*    */   public static final String TAG_SKULL_OWNER = "SkullOwner";
/*    */   
/*    */   public PlayerHeadItem(Block $$0, Block $$1, Item.Properties $$2) {
/* 14 */     super($$0, $$1, $$2, Direction.DOWN);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getName(ItemStack $$0) {
/* 19 */     if ($$0.is(Items.PLAYER_HEAD) && $$0.hasTag()) {
/* 20 */       String $$1 = null;
/* 21 */       CompoundTag $$2 = $$0.getTag();
/* 22 */       if ($$2.contains("SkullOwner", 8)) {
/* 23 */         $$1 = $$2.getString("SkullOwner");
/* 24 */       } else if ($$2.contains("SkullOwner", 10)) {
/* 25 */         CompoundTag $$3 = $$2.getCompound("SkullOwner");
/* 26 */         if ($$3.contains("Name", 8)) {
/* 27 */           $$1 = $$3.getString("Name");
/*    */         }
/*    */       } 
/* 30 */       if ($$1 != null) {
/* 31 */         return (Component)Component.translatable(getDescriptionId() + ".named", new Object[] { $$1 });
/*    */       }
/*    */     } 
/* 34 */     return super.getName($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void verifyTagAfterLoad(CompoundTag $$0) {
/* 39 */     super.verifyTagAfterLoad($$0);
/* 40 */     SkullBlockEntity.resolveGameProfile($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\PlayerHeadItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */