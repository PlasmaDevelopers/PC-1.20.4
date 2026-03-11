/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdventureModeCheck
/*    */ {
/*    */   private final String tagName;
/*    */   @Nullable
/*    */   private BlockInWorld lastCheckedBlock;
/*    */   private boolean lastResult;
/*    */   private boolean checksBlockEntity;
/*    */   
/*    */   public AdventureModeCheck(String $$0) {
/* 26 */     this.tagName = $$0;
/*    */   }
/*    */   
/*    */   private static boolean areSameBlocks(BlockInWorld $$0, @Nullable BlockInWorld $$1, boolean $$2) {
/* 30 */     if ($$1 == null || $$0.getState() != $$1.getState()) {
/* 31 */       return false;
/*    */     }
/* 33 */     if (!$$2) {
/* 34 */       return true;
/*    */     }
/* 36 */     if ($$0.getEntity() == null && $$1.getEntity() == null) {
/* 37 */       return true;
/*    */     }
/* 39 */     if ($$0.getEntity() == null || $$1.getEntity() == null) {
/* 40 */       return false;
/*    */     }
/* 42 */     return Objects.equals($$0.getEntity().saveWithId(), $$1.getEntity().saveWithId());
/*    */   }
/*    */   
/*    */   public boolean test(ItemStack $$0, Registry<Block> $$1, BlockInWorld $$2) {
/* 46 */     if (areSameBlocks($$2, this.lastCheckedBlock, this.checksBlockEntity)) {
/* 47 */       return this.lastResult;
/*    */     }
/*    */     
/* 50 */     this.lastCheckedBlock = $$2;
/* 51 */     this.checksBlockEntity = false;
/* 52 */     CompoundTag $$3 = $$0.getTag();
/* 53 */     if ($$3 != null && $$3.contains(this.tagName, 9)) {
/* 54 */       ListTag $$4 = $$3.getList(this.tagName, 8);
/* 55 */       for (int $$5 = 0; $$5 < $$4.size(); $$5++) {
/* 56 */         String $$6 = $$4.getString($$5);
/*    */         try {
/* 58 */           BlockPredicateArgument.Result $$7 = BlockPredicateArgument.parse((HolderLookup)$$1.asLookup(), new StringReader($$6));
/* 59 */           this.checksBlockEntity |= $$7.requiresNbt();
/* 60 */           if ($$7.test($$2)) {
/* 61 */             this.lastResult = true;
/* 62 */             return true;
/*    */           } 
/* 64 */         } catch (CommandSyntaxException commandSyntaxException) {}
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 69 */     this.lastResult = false;
/* 70 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\AdventureModeCheck.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */