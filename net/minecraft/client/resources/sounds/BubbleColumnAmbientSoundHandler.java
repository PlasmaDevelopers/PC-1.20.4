/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.BubbleColumnBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class BubbleColumnAmbientSoundHandler implements AmbientSoundHandler {
/*    */   private final LocalPlayer player;
/*    */   private boolean wasInBubbleColumn;
/*    */   private boolean firstTick = true;
/*    */   
/*    */   public BubbleColumnAmbientSoundHandler(LocalPlayer $$0) {
/* 17 */     this.player = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 22 */     Level $$0 = this.player.level();
/* 23 */     BlockState $$1 = $$0.getBlockStatesIfLoaded(this.player.getBoundingBox().inflate(0.0D, -0.4000000059604645D, 0.0D).deflate(1.0E-6D)).filter($$0 -> $$0.is(Blocks.BUBBLE_COLUMN)).findFirst().orElse(null);
/* 24 */     if ($$1 != null) {
/* 25 */       if (!this.wasInBubbleColumn && !this.firstTick && 
/* 26 */         $$1.is(Blocks.BUBBLE_COLUMN) && !this.player.isSpectator()) {
/* 27 */         boolean $$2 = ((Boolean)$$1.getValue((Property)BubbleColumnBlock.DRAG_DOWN)).booleanValue();
/* 28 */         if ($$2) {
/* 29 */           this.player.playSound(SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1.0F, 1.0F);
/*    */         } else {
/* 31 */           this.player.playSound(SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, 1.0F, 1.0F);
/*    */         } 
/*    */       } 
/*    */       
/* 35 */       this.wasInBubbleColumn = true;
/*    */     } else {
/* 37 */       this.wasInBubbleColumn = false;
/*    */     } 
/* 39 */     this.firstTick = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\BubbleColumnAmbientSoundHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */