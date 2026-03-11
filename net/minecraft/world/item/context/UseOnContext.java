/*    */ package net.minecraft.world.item.context;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ 
/*    */ public class UseOnContext
/*    */ {
/*    */   @Nullable
/*    */   private final Player player;
/*    */   private final InteractionHand hand;
/*    */   private final BlockHitResult hitResult;
/*    */   private final Level level;
/*    */   private final ItemStack itemStack;
/*    */   
/*    */   public UseOnContext(Player $$0, InteractionHand $$1, BlockHitResult $$2) {
/* 24 */     this($$0.level(), $$0, $$1, $$0.getItemInHand($$1), $$2);
/*    */   }
/*    */   
/*    */   protected UseOnContext(Level $$0, @Nullable Player $$1, InteractionHand $$2, ItemStack $$3, BlockHitResult $$4) {
/* 28 */     this.player = $$1;
/* 29 */     this.hand = $$2;
/* 30 */     this.hitResult = $$4;
/*    */     
/* 32 */     this.itemStack = $$3;
/* 33 */     this.level = $$0;
/*    */   }
/*    */   
/*    */   protected final BlockHitResult getHitResult() {
/* 37 */     return this.hitResult;
/*    */   }
/*    */   
/*    */   public BlockPos getClickedPos() {
/* 41 */     return this.hitResult.getBlockPos();
/*    */   }
/*    */   
/*    */   public Direction getClickedFace() {
/* 45 */     return this.hitResult.getDirection();
/*    */   }
/*    */   
/*    */   public Vec3 getClickLocation() {
/* 49 */     return this.hitResult.getLocation();
/*    */   }
/*    */   
/*    */   public boolean isInside() {
/* 53 */     return this.hitResult.isInside();
/*    */   }
/*    */   
/*    */   public ItemStack getItemInHand() {
/* 57 */     return this.itemStack;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Player getPlayer() {
/* 62 */     return this.player;
/*    */   }
/*    */   
/*    */   public InteractionHand getHand() {
/* 66 */     return this.hand;
/*    */   }
/*    */   
/*    */   public Level getLevel() {
/* 70 */     return this.level;
/*    */   }
/*    */   
/*    */   public Direction getHorizontalDirection() {
/* 74 */     return (this.player == null) ? Direction.NORTH : this.player.getDirection();
/*    */   }
/*    */   
/*    */   public boolean isSecondaryUseActive() {
/* 78 */     return (this.player != null && this.player.isSecondaryUseActive());
/*    */   }
/*    */   
/*    */   public float getRotation() {
/* 82 */     return (this.player == null) ? 0.0F : this.player.getYRot();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\context\UseOnContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */