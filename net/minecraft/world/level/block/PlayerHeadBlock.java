/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.SkullBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class PlayerHeadBlock extends SkullBlock {
/* 20 */   public static final MapCodec<PlayerHeadBlock> CODEC = simpleCodec(PlayerHeadBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<PlayerHeadBlock> codec() {
/* 24 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected PlayerHeadBlock(BlockBehaviour.Properties $$0) {
/* 28 */     super(SkullBlock.Types.PLAYER, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {
/* 33 */     super.setPlacedBy($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 35 */     BlockEntity $$5 = $$0.getBlockEntity($$1);
/*    */     
/* 37 */     if ($$5 instanceof SkullBlockEntity) { SkullBlockEntity $$6 = (SkullBlockEntity)$$5;
/* 38 */       GameProfile $$7 = null;
/* 39 */       if ($$4.hasTag()) {
/* 40 */         CompoundTag $$8 = $$4.getTag();
/*    */ 
/*    */         
/* 43 */         if ($$8.contains("SkullOwner", 10)) {
/* 44 */           $$7 = NbtUtils.readGameProfile($$8.getCompound("SkullOwner"));
/* 45 */         } else if ($$8.contains("SkullOwner", 8) && !Util.isBlank($$8.getString("SkullOwner"))) {
/* 46 */           $$7 = new GameProfile(Util.NIL_UUID, $$8.getString("SkullOwner"));
/*    */         } 
/*    */       } 
/*    */       
/* 50 */       $$6.setOwner($$7); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PlayerHeadBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */