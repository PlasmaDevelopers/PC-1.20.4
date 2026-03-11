/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.EntityModel;
/*     */ import net.minecraft.client.model.HeadedModel;
/*     */ import net.minecraft.client.model.SkullModelBase;
/*     */ import net.minecraft.client.model.geom.EntityModelSet;
/*     */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.level.block.SkullBlock;
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
/*     */ public class CustomHeadLayer<T extends LivingEntity, M extends EntityModel<T> & HeadedModel>
/*     */   extends RenderLayer<T, M>
/*     */ {
/*     */   private final float scaleX;
/*     */   private final float scaleY;
/*     */   private final float scaleZ;
/*     */   private final Map<SkullBlock.Type, SkullModelBase> skullModels;
/*     */   private final ItemInHandRenderer itemInHandRenderer;
/*     */   
/*     */   public CustomHeadLayer(RenderLayerParent<T, M> $$0, EntityModelSet $$1, ItemInHandRenderer $$2) {
/*  43 */     this($$0, $$1, 1.0F, 1.0F, 1.0F, $$2);
/*     */   }
/*     */   
/*     */   public CustomHeadLayer(RenderLayerParent<T, M> $$0, EntityModelSet $$1, float $$2, float $$3, float $$4, ItemInHandRenderer $$5) {
/*  47 */     super($$0);
/*  48 */     this.scaleX = $$2;
/*  49 */     this.scaleY = $$3;
/*  50 */     this.scaleZ = $$4;
/*  51 */     this.skullModels = SkullBlockRenderer.createSkullRenderers($$1);
/*  52 */     this.itemInHandRenderer = $$5;
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
/*     */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/*     */     // Byte code:
/*     */     //   0: aload #4
/*     */     //   2: getstatic net/minecraft/world/entity/EquipmentSlot.HEAD : Lnet/minecraft/world/entity/EquipmentSlot;
/*     */     //   5: invokevirtual getItemBySlot : (Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;
/*     */     //   8: astore #11
/*     */     //   10: aload #11
/*     */     //   12: invokevirtual isEmpty : ()Z
/*     */     //   15: ifeq -> 19
/*     */     //   18: return
/*     */     //   19: aload #11
/*     */     //   21: invokevirtual getItem : ()Lnet/minecraft/world/item/Item;
/*     */     //   24: astore #12
/*     */     //   26: aload_1
/*     */     //   27: invokevirtual pushPose : ()V
/*     */     //   30: aload_1
/*     */     //   31: aload_0
/*     */     //   32: getfield scaleX : F
/*     */     //   35: aload_0
/*     */     //   36: getfield scaleY : F
/*     */     //   39: aload_0
/*     */     //   40: getfield scaleZ : F
/*     */     //   43: invokevirtual scale : (FFF)V
/*     */     //   46: aload #4
/*     */     //   48: instanceof net/minecraft/world/entity/npc/Villager
/*     */     //   51: ifne -> 62
/*     */     //   54: aload #4
/*     */     //   56: instanceof net/minecraft/world/entity/monster/ZombieVillager
/*     */     //   59: ifeq -> 66
/*     */     //   62: iconst_1
/*     */     //   63: goto -> 67
/*     */     //   66: iconst_0
/*     */     //   67: istore #13
/*     */     //   69: aload #4
/*     */     //   71: invokevirtual isBaby : ()Z
/*     */     //   74: ifeq -> 117
/*     */     //   77: aload #4
/*     */     //   79: instanceof net/minecraft/world/entity/npc/Villager
/*     */     //   82: ifne -> 117
/*     */     //   85: fconst_2
/*     */     //   86: fstore #14
/*     */     //   88: ldc 1.4
/*     */     //   90: fstore #15
/*     */     //   92: aload_1
/*     */     //   93: fconst_0
/*     */     //   94: ldc 0.03125
/*     */     //   96: fconst_0
/*     */     //   97: invokevirtual translate : (FFF)V
/*     */     //   100: aload_1
/*     */     //   101: ldc 0.7
/*     */     //   103: ldc 0.7
/*     */     //   105: ldc 0.7
/*     */     //   107: invokevirtual scale : (FFF)V
/*     */     //   110: aload_1
/*     */     //   111: fconst_0
/*     */     //   112: fconst_1
/*     */     //   113: fconst_0
/*     */     //   114: invokevirtual translate : (FFF)V
/*     */     //   117: aload_0
/*     */     //   118: invokevirtual getParentModel : ()Lnet/minecraft/client/model/EntityModel;
/*     */     //   121: checkcast net/minecraft/client/model/HeadedModel
/*     */     //   124: invokeinterface getHead : ()Lnet/minecraft/client/model/geom/ModelPart;
/*     */     //   129: aload_1
/*     */     //   130: invokevirtual translateAndRotate : (Lcom/mojang/blaze3d/vertex/PoseStack;)V
/*     */     //   133: aload #12
/*     */     //   135: instanceof net/minecraft/world/item/BlockItem
/*     */     //   138: ifeq -> 342
/*     */     //   141: aload #12
/*     */     //   143: checkcast net/minecraft/world/item/BlockItem
/*     */     //   146: invokevirtual getBlock : ()Lnet/minecraft/world/level/block/Block;
/*     */     //   149: instanceof net/minecraft/world/level/block/AbstractSkullBlock
/*     */     //   152: ifeq -> 342
/*     */     //   155: ldc 1.1875
/*     */     //   157: fstore #15
/*     */     //   159: aload_1
/*     */     //   160: ldc 1.1875
/*     */     //   162: ldc -1.1875
/*     */     //   164: ldc -1.1875
/*     */     //   166: invokevirtual scale : (FFF)V
/*     */     //   169: iload #13
/*     */     //   171: ifeq -> 182
/*     */     //   174: aload_1
/*     */     //   175: fconst_0
/*     */     //   176: ldc 0.0625
/*     */     //   178: fconst_0
/*     */     //   179: invokevirtual translate : (FFF)V
/*     */     //   182: aconst_null
/*     */     //   183: astore #16
/*     */     //   185: aload #11
/*     */     //   187: invokevirtual hasTag : ()Z
/*     */     //   190: ifeq -> 224
/*     */     //   193: aload #11
/*     */     //   195: invokevirtual getTag : ()Lnet/minecraft/nbt/CompoundTag;
/*     */     //   198: astore #17
/*     */     //   200: aload #17
/*     */     //   202: ldc 'SkullOwner'
/*     */     //   204: bipush #10
/*     */     //   206: invokevirtual contains : (Ljava/lang/String;I)Z
/*     */     //   209: ifeq -> 224
/*     */     //   212: aload #17
/*     */     //   214: ldc 'SkullOwner'
/*     */     //   216: invokevirtual getCompound : (Ljava/lang/String;)Lnet/minecraft/nbt/CompoundTag;
/*     */     //   219: invokestatic readGameProfile : (Lnet/minecraft/nbt/CompoundTag;)Lcom/mojang/authlib/GameProfile;
/*     */     //   222: astore #16
/*     */     //   224: aload_1
/*     */     //   225: ldc2_w -0.5
/*     */     //   228: dconst_0
/*     */     //   229: ldc2_w -0.5
/*     */     //   232: invokevirtual translate : (DDD)V
/*     */     //   235: aload #12
/*     */     //   237: checkcast net/minecraft/world/item/BlockItem
/*     */     //   240: invokevirtual getBlock : ()Lnet/minecraft/world/level/block/Block;
/*     */     //   243: checkcast net/minecraft/world/level/block/AbstractSkullBlock
/*     */     //   246: invokevirtual getType : ()Lnet/minecraft/world/level/block/SkullBlock$Type;
/*     */     //   249: astore #17
/*     */     //   251: aload_0
/*     */     //   252: getfield skullModels : Ljava/util/Map;
/*     */     //   255: aload #17
/*     */     //   257: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   262: checkcast net/minecraft/client/model/SkullModelBase
/*     */     //   265: astore #18
/*     */     //   267: aload #17
/*     */     //   269: aload #16
/*     */     //   271: invokestatic getRenderType : (Lnet/minecraft/world/level/block/SkullBlock$Type;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/client/renderer/RenderType;
/*     */     //   274: astore #19
/*     */     //   276: aload #4
/*     */     //   278: invokevirtual getVehicle : ()Lnet/minecraft/world/entity/Entity;
/*     */     //   281: astore #22
/*     */     //   283: aload #22
/*     */     //   285: instanceof net/minecraft/world/entity/LivingEntity
/*     */     //   288: ifeq -> 308
/*     */     //   291: aload #22
/*     */     //   293: checkcast net/minecraft/world/entity/LivingEntity
/*     */     //   296: astore #21
/*     */     //   298: aload #21
/*     */     //   300: getfield walkAnimation : Lnet/minecraft/world/entity/WalkAnimationState;
/*     */     //   303: astore #20
/*     */     //   305: goto -> 315
/*     */     //   308: aload #4
/*     */     //   310: getfield walkAnimation : Lnet/minecraft/world/entity/WalkAnimationState;
/*     */     //   313: astore #20
/*     */     //   315: aload #20
/*     */     //   317: fload #7
/*     */     //   319: invokevirtual position : (F)F
/*     */     //   322: fstore #21
/*     */     //   324: aconst_null
/*     */     //   325: ldc 180.0
/*     */     //   327: fload #21
/*     */     //   329: aload_1
/*     */     //   330: aload_2
/*     */     //   331: iload_3
/*     */     //   332: aload #18
/*     */     //   334: aload #19
/*     */     //   336: invokestatic renderSkull : (Lnet/minecraft/core/Direction;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/SkullModelBase;Lnet/minecraft/client/renderer/RenderType;)V
/*     */     //   339: goto -> 392
/*     */     //   342: aload #12
/*     */     //   344: instanceof net/minecraft/world/item/ArmorItem
/*     */     //   347: ifeq -> 368
/*     */     //   350: aload #12
/*     */     //   352: checkcast net/minecraft/world/item/ArmorItem
/*     */     //   355: astore #14
/*     */     //   357: aload #14
/*     */     //   359: invokevirtual getEquipmentSlot : ()Lnet/minecraft/world/entity/EquipmentSlot;
/*     */     //   362: getstatic net/minecraft/world/entity/EquipmentSlot.HEAD : Lnet/minecraft/world/entity/EquipmentSlot;
/*     */     //   365: if_acmpeq -> 392
/*     */     //   368: aload_1
/*     */     //   369: iload #13
/*     */     //   371: invokestatic translateToHead : (Lcom/mojang/blaze3d/vertex/PoseStack;Z)V
/*     */     //   374: aload_0
/*     */     //   375: getfield itemInHandRenderer : Lnet/minecraft/client/renderer/ItemInHandRenderer;
/*     */     //   378: aload #4
/*     */     //   380: aload #11
/*     */     //   382: getstatic net/minecraft/world/item/ItemDisplayContext.HEAD : Lnet/minecraft/world/item/ItemDisplayContext;
/*     */     //   385: iconst_0
/*     */     //   386: aload_1
/*     */     //   387: aload_2
/*     */     //   388: iload_3
/*     */     //   389: invokevirtual renderItem : (Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V
/*     */     //   392: aload_1
/*     */     //   393: invokevirtual popPose : ()V
/*     */     //   396: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #57	-> 0
/*     */     //   #58	-> 10
/*     */     //   #59	-> 18
/*     */     //   #62	-> 19
/*     */     //   #64	-> 26
/*     */     //   #65	-> 30
/*     */     //   #67	-> 46
/*     */     //   #69	-> 69
/*     */     //   #70	-> 85
/*     */     //   #71	-> 88
/*     */     //   #72	-> 92
/*     */     //   #73	-> 100
/*     */     //   #74	-> 110
/*     */     //   #77	-> 117
/*     */     //   #79	-> 133
/*     */     //   #80	-> 155
/*     */     //   #81	-> 159
/*     */     //   #82	-> 169
/*     */     //   #83	-> 174
/*     */     //   #86	-> 182
/*     */     //   #87	-> 185
/*     */     //   #88	-> 193
/*     */     //   #89	-> 200
/*     */     //   #90	-> 212
/*     */     //   #94	-> 224
/*     */     //   #95	-> 235
/*     */     //   #96	-> 251
/*     */     //   #97	-> 267
/*     */     //   #100	-> 276
/*     */     //   #101	-> 298
/*     */     //   #103	-> 308
/*     */     //   #105	-> 315
/*     */     //   #107	-> 324
/*     */     //   #108	-> 339
/*     */     //   #109	-> 368
/*     */     //   #111	-> 374
/*     */     //   #113	-> 392
/*     */     //   #114	-> 396
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	397	0	this	Lnet/minecraft/client/renderer/entity/layers/CustomHeadLayer;
/*     */     //   0	397	1	$$0	Lcom/mojang/blaze3d/vertex/PoseStack;
/*     */     //   0	397	2	$$1	Lnet/minecraft/client/renderer/MultiBufferSource;
/*     */     //   0	397	3	$$2	I
/*     */     //   0	397	4	$$3	Lnet/minecraft/world/entity/LivingEntity;
/*     */     //   0	397	5	$$4	F
/*     */     //   0	397	6	$$5	F
/*     */     //   0	397	7	$$6	F
/*     */     //   0	397	8	$$7	F
/*     */     //   0	397	9	$$8	F
/*     */     //   0	397	10	$$9	F
/*     */     //   10	387	11	$$10	Lnet/minecraft/world/item/ItemStack;
/*     */     //   26	371	12	$$11	Lnet/minecraft/world/item/Item;
/*     */     //   69	328	13	$$12	Z
/*     */     //   88	29	14	$$13	F
/*     */     //   92	25	15	$$14	F
/*     */     //   159	180	15	$$15	F
/*     */     //   185	154	16	$$16	Lcom/mojang/authlib/GameProfile;
/*     */     //   200	24	17	$$17	Lnet/minecraft/nbt/CompoundTag;
/*     */     //   251	88	17	$$18	Lnet/minecraft/world/level/block/SkullBlock$Type;
/*     */     //   267	72	18	$$19	Lnet/minecraft/client/model/SkullModelBase;
/*     */     //   276	63	19	$$20	Lnet/minecraft/client/renderer/RenderType;
/*     */     //   298	10	21	$$21	Lnet/minecraft/world/entity/LivingEntity;
/*     */     //   305	3	20	$$22	Lnet/minecraft/world/entity/WalkAnimationState;
/*     */     //   315	24	20	$$23	Lnet/minecraft/world/entity/WalkAnimationState;
/*     */     //   324	15	21	$$24	F
/*     */     //   357	11	14	$$25	Lnet/minecraft/world/item/ArmorItem;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	397	0	this	Lnet/minecraft/client/renderer/entity/layers/CustomHeadLayer<TT;TM;>;
/*     */     //   0	397	4	$$3	TT;
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
/*     */   public static void translateToHead(PoseStack $$0, boolean $$1) {
/* 117 */     float $$2 = 0.625F;
/* 118 */     $$0.translate(0.0F, -0.25F, 0.0F);
/* 119 */     $$0.mulPose(Axis.YP.rotationDegrees(180.0F));
/* 120 */     $$0.scale(0.625F, -0.625F, -0.625F);
/* 121 */     if ($$1)
/* 122 */       $$0.translate(0.0F, 0.1875F, 0.0F); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\CustomHeadLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */