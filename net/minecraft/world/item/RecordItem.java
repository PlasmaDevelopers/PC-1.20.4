/*    */ package net.minecraft.world.item;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.JukeboxBlock;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class RecordItem extends Item {
/* 26 */   private static final Map<SoundEvent, RecordItem> BY_NAME = Maps.newHashMap();
/*    */   
/*    */   private final int analogOutput;
/*    */   private final SoundEvent sound;
/*    */   private final int lengthInTicks;
/*    */   
/*    */   protected RecordItem(int $$0, SoundEvent $$1, Item.Properties $$2, int $$3) {
/* 33 */     super($$2);
/*    */     
/* 35 */     this.analogOutput = $$0;
/* 36 */     this.sound = $$1;
/* 37 */     this.lengthInTicks = $$3 * 20;
/* 38 */     BY_NAME.put(this.sound, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 43 */     Level $$1 = $$0.getLevel();
/* 44 */     BlockPos $$2 = $$0.getClickedPos();
/*    */     
/* 46 */     BlockState $$3 = $$1.getBlockState($$2);
/* 47 */     if (!$$3.is(Blocks.JUKEBOX) || ((Boolean)$$3.getValue((Property)JukeboxBlock.HAS_RECORD)).booleanValue()) {
/* 48 */       return InteractionResult.PASS;
/*    */     }
/*    */     
/* 51 */     ItemStack $$4 = $$0.getItemInHand();
/* 52 */     if (!$$1.isClientSide) {
/* 53 */       Player $$5 = $$0.getPlayer();
/*    */       
/* 55 */       BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof JukeboxBlockEntity) { JukeboxBlockEntity $$6 = (JukeboxBlockEntity)blockEntity;
/* 56 */         $$6.setTheItem($$4.copy());
/* 57 */         $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of((Entity)$$5, $$3)); }
/*    */ 
/*    */       
/* 60 */       $$4.shrink(1);
/*    */       
/* 62 */       if ($$5 != null) {
/* 63 */         $$5.awardStat(Stats.PLAY_RECORD);
/*    */       }
/*    */     } 
/* 66 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*    */   }
/*    */   
/*    */   public int getAnalogOutput() {
/* 70 */     return this.analogOutput;
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 75 */     $$2.add(getDisplayName().withStyle(ChatFormatting.GRAY));
/*    */   }
/*    */   
/*    */   public MutableComponent getDisplayName() {
/* 79 */     return Component.translatable(getDescriptionId() + ".desc");
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static RecordItem getBySound(SoundEvent $$0) {
/* 84 */     return BY_NAME.get($$0);
/*    */   }
/*    */   
/*    */   public SoundEvent getSound() {
/* 88 */     return this.sound;
/*    */   }
/*    */   
/*    */   public int getLengthInTicks() {
/* 92 */     return this.lengthInTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\RecordItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */