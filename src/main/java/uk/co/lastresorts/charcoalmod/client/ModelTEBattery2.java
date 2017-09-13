package uk.co.lastresorts.charcoalmod.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTEBattery2 extends ModelBase
{
    ModelRenderer centre;
    ModelRenderer block1;
    ModelRenderer block2;
    ModelRenderer backInput;
    ModelRenderer leftInput;
    ModelRenderer rightInput;
    ModelRenderer topInput;
    ModelRenderer bottomInput;
    ModelRenderer frontInput;
    ModelRenderer bottomOutput;
    ModelRenderer topOutput;
    ModelRenderer frontOutput;
    ModelRenderer leftOutput;
    ModelRenderer backOutput;
    ModelRenderer rightOutput;
  
  public ModelTEBattery2()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      centre = new ModelRenderer(this, 0, 0);
      centre.addBox(-4F, -4F, -4F, 8, 8, 8);
      centre.setRotationPoint(0F, 0F, 0F);
      centre.setTextureSize(64, 64);
      centre.mirror = true;
      setRotation(centre, 0F, 0F, 0F);
      block1 = new ModelRenderer(this, 0, 34);
      block1.addBox(-6F, -5F, -6F, 12, 10, 5);
      block1.setRotationPoint(0F, 0F, 0F);
      block1.setTextureSize(64, 64);
      block1.mirror = true;
      setRotation(block1, 0F, 0F, 0F);
      block2 = new ModelRenderer(this, 0, 34);
      block2.addBox(-6F, -5F, 1F, 12, 10, 5);
      block2.setRotationPoint(0F, 0F, 0F);
      block2.setTextureSize(64, 64);
      block2.mirror = true;
      setRotation(block2, 0F, 0F, 0F);
      backInput = new ModelRenderer(this, 0, 16);
      backInput.addBox(-2F, -2F, 0F, 4, 4, 4);
      backInput.setRotationPoint(0F, 0F, 4F);
      backInput.setTextureSize(64, 64);
      backInput.mirror = true;
      setRotation(backInput, 0F, 0F, 0F);
      leftInput = new ModelRenderer(this, 0, 16);
      leftInput.addBox(-2F, -2F, 0F, 4, 4, 4);
      leftInput.setRotationPoint(4F, 0F, 0F);
      leftInput.setTextureSize(64, 64);
      leftInput.mirror = true;
      setRotation(leftInput, 0F, 1.579523F, 0F);
      rightInput = new ModelRenderer(this, 0, 16);
      rightInput.addBox(-2F, -2F, 0F, 4, 4, 4);
      rightInput.setRotationPoint(-4F, 0F, 0F);
      rightInput.setTextureSize(64, 64);
      rightInput.mirror = true;
      setRotation(rightInput, 0F, -1.579523F, 0F);
      topInput = new ModelRenderer(this, 0, 16);
      topInput.addBox(-2F, -2F, 0F, 4, 4, 4);
      topInput.setRotationPoint(0F, -4F, 0F);
      topInput.setTextureSize(64, 64);
      topInput.mirror = true;
      setRotation(topInput, 1.579523F, 0F, 0F);
      bottomInput = new ModelRenderer(this, 0, 16);
      bottomInput.addBox(-2F, -2F, 0F, 4, 4, 4);
      bottomInput.setRotationPoint(0F, 4F, 0F);
      bottomInput.setTextureSize(64, 64);
      bottomInput.mirror = true;
      setRotation(bottomInput, -1.579523F, 0F, 0F);
      frontInput = new ModelRenderer(this, 0, 16);
      frontInput.addBox(-2F, -2F, -4F, 4, 4, 4);
      frontInput.setRotationPoint(0F, 0F, -4F);
      frontInput.setTextureSize(64, 64);
      frontInput.mirror = true;
      setRotation(frontInput, 0F, 0F, 0F);
      bottomOutput = new ModelRenderer(this, 0, 24);
      bottomOutput.addBox(-3F, -3F, 0F, 6, 6, 4);
      bottomOutput.setRotationPoint(0F, 4F, 0F);
      bottomOutput.setTextureSize(64, 64);
      bottomOutput.mirror = true;
      setRotation(bottomOutput, -1.579523F, 0F, 0F);
      topOutput = new ModelRenderer(this, 0, 24);
      topOutput.addBox(-3F, -3F, -4F, 6, 6, 4);
      topOutput.setRotationPoint(0F, -4F, 0F);
      topOutput.setTextureSize(64, 64);
      topOutput.mirror = true;
      setRotation(topOutput, -1.579523F, 0F, 0F);
      frontOutput = new ModelRenderer(this, 0, 24);
      frontOutput.addBox(-3F, -3F, -4F, 6, 6, 4);
      frontOutput.setRotationPoint(0F, 0F, -4F);
      frontOutput.setTextureSize(64, 64);
      frontOutput.mirror = true;
      setRotation(frontOutput, 0F, 0F, 0F);
      leftOutput = new ModelRenderer(this, 0, 24);
      leftOutput.addBox(-3F, -3F, 0F, 6, 6, 4);
      leftOutput.setRotationPoint(4F, 0F, 0F);
      leftOutput.setTextureSize(64, 64);
      leftOutput.mirror = true;
      setRotation(leftOutput, 0F, 1.579523F, 0F);
      backOutput = new ModelRenderer(this, 0, 24);
      backOutput.addBox(-3F, -3F, -1F, 6, 6, 4);
      backOutput.setRotationPoint(0F, 0F, 5F);
      backOutput.setTextureSize(64, 64);
      backOutput.mirror = true;
      setRotation(backOutput, 0F, 0F, 0F);
      rightOutput = new ModelRenderer(this, 0, 24);
      rightOutput.addBox(-3F, -3F, 0F, 6, 6, 4);
      rightOutput.setRotationPoint(-4F, 0F, 0F);
      rightOutput.setTextureSize(64, 64);
      rightOutput.mirror = true;
      setRotation(rightOutput, 0F, -1.579523F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int sidesToRender[])
  {
	super.render(entity, f, f1, f2, f3, f4, f5);
	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	centre.render(f5);
	block1.render(f5);
	block2.render(f5);
	if(sidesToRender[0] == 1)	bottomInput.render(f5);
	if(sidesToRender[1] == 1)	topInput.render(f5);
	if(sidesToRender[2] == 1)	frontInput.render(f5);
	if(sidesToRender[3] == 1)	backInput.render(f5);
	if(sidesToRender[4] == 1)	leftInput.render(f5);
	if(sidesToRender[5] == 1)	rightInput.render(f5);
	if(sidesToRender[0] == 2)	bottomOutput.render(f5);
	if(sidesToRender[1] == 2)	topOutput.render(f5);
	if(sidesToRender[2] == 2)	frontOutput.render(f5);
	if(sidesToRender[3] == 2)	backOutput.render(f5);
	if(sidesToRender[4] == 2)	leftOutput.render(f5);
	if(sidesToRender[5] == 2)	rightOutput.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
