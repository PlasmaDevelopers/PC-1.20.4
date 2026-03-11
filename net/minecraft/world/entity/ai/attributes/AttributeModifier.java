/*     */ package net.minecraft.world.entity.ai.attributes;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class AttributeModifier {
/*     */   public static final Codec<AttributeModifier> CODEC;
/*     */   private final double amount;
/*     */   private final Operation operation;
/*  18 */   private static final Logger LOGGER = LogUtils.getLogger(); private final String name;
/*     */   private final UUID id;
/*     */   
/*  21 */   public enum Operation implements StringRepresentable { ADDITION("addition", 0),
/*  22 */     MULTIPLY_BASE("multiply_base", 1),
/*  23 */     MULTIPLY_TOTAL("multiply_total", 2);
/*     */     
/*  25 */     private static final Operation[] OPERATIONS = new Operation[] { ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL };
/*     */     
/*  27 */     public static final Codec<Operation> CODEC = (Codec<Operation>)StringRepresentable.fromEnum(Operation::values); private final String name; private final int value;
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     Operation(String $$0, int $$1) {
/*  33 */       this.name = $$0;
/*  34 */       this.value = $$1;
/*     */     }
/*     */     
/*     */     public int toValue() {
/*  38 */       return this.value;
/*     */     }
/*     */     
/*     */     public static Operation fromValue(int $$0) {
/*  42 */       if ($$0 < 0 || $$0 >= OPERATIONS.length) {
/*  43 */         throw new IllegalArgumentException("No operation with value " + $$0);
/*     */       }
/*     */       
/*  46 */       return OPERATIONS[$$0];
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/*  51 */       return this.name;
/*     */     } }
/*     */   
/*     */   static {
/*  55 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)UUIDUtil.CODEC.fieldOf("UUID").forGetter(AttributeModifier::getId), (App)Codec.STRING.fieldOf("Name").forGetter(()), (App)Codec.DOUBLE.fieldOf("Amount").forGetter(AttributeModifier::getAmount), (App)Operation.CODEC.fieldOf("Operation").forGetter(AttributeModifier::getOperation)).apply((Applicative)$$0, AttributeModifier::new));
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
/*     */   public AttributeModifier(String $$0, double $$1, Operation $$2) {
/*  68 */     this(Mth.createInsecureUUID(RandomSource.createNewThreadLocalInstance()), $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public AttributeModifier(UUID $$0, String $$1, double $$2, Operation $$3) {
/*  72 */     this.id = $$0;
/*  73 */     this.name = $$1;
/*  74 */     this.amount = $$2;
/*  75 */     this.operation = $$3;
/*     */   }
/*     */   
/*     */   public UUID getId() {
/*  79 */     return this.id;
/*     */   }
/*     */   
/*     */   public Operation getOperation() {
/*  83 */     return this.operation;
/*     */   }
/*     */   
/*     */   public double getAmount() {
/*  87 */     return this.amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  92 */     if (this == $$0) {
/*  93 */       return true;
/*     */     }
/*  95 */     if ($$0 == null || getClass() != $$0.getClass()) {
/*  96 */       return false;
/*     */     }
/*     */     
/*  99 */     AttributeModifier $$1 = (AttributeModifier)$$0;
/*     */     
/* 101 */     return Objects.equals(this.id, $$1.id);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     return this.id.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 111 */     return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name='" + this.name + "', id=" + this.id + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompoundTag save() {
/* 120 */     CompoundTag $$0 = new CompoundTag();
/* 121 */     $$0.putString("Name", this.name);
/* 122 */     $$0.putDouble("Amount", this.amount);
/* 123 */     $$0.putInt("Operation", this.operation.toValue());
/* 124 */     $$0.putUUID("UUID", this.id);
/* 125 */     return $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static AttributeModifier load(CompoundTag $$0) {
/*     */     try {
/* 131 */       UUID $$1 = $$0.getUUID("UUID");
/* 132 */       Operation $$2 = Operation.fromValue($$0.getInt("Operation"));
/* 133 */       return new AttributeModifier($$1, $$0.getString("Name"), $$0.getDouble("Amount"), $$2);
/* 134 */     } catch (Exception $$3) {
/* 135 */       LOGGER.warn("Unable to create attribute: {}", $$3.getMessage());
/* 136 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\attributes\AttributeModifier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */